package com.zty.kdd.api.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.DO.TransQueryLogDO;
import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.ao.MaptrackQueryRespAO;
import com.zty.kdd.api.MaptrackApi;
import com.zty.kdd.enums.CompanyEnum;
import com.zty.kdd.enums.StatusCodeEnum;
import com.zty.kdd.enums.TransStatusEnum;
import com.zty.kdd.limiter.HttpRateLimitPool;
import com.zty.kdd.service.BalanceService;
import com.zty.kdd.service.TransQueryLogService;
import com.zty.kdd.third.dto.SFThirdQueryDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.resp.MsgDataDTO;
import com.zty.kdd.third.resp.ShunFengResp;
import com.zty.kdd.third.service.AbstractSDKService;

/**
 * @author tianyi
 * @date 2021-03-01 22:49
 */
@Service
public class MaptrackApiImpl implements MaptrackApi {

    private static final Logger log = LoggerFactory.getLogger(MaptrackApiImpl.class);

    @Resource(name = "Shunfeng_SDK")
    private AbstractSDKService shunfengSDKService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private TransQueryLogService transQueryLogService;

    @Autowired
    private HttpRateLimitPool httpRateLimitPool;

    @Override
    public MaptrackQueryRespAO singleQuery(MaptrackQueryReqAO reqAO) throws Exception {
        MaptrackQueryRespAO maptrackQueryRespAO = new MaptrackQueryRespAO();
        AccountBalanceDO accountBalanceDO = new AccountBalanceDO().accountId(reqAO.getCurrentUID());
        // 检查并预扣余额
        balanceService.checkAndFrozen(accountBalanceDO);
        String result = null;
        // 拼装请求记录参数
        Map<String, Object> reqParam = shunfengSDKService.getReqBody(new SFThirdQueryDTO(reqAO.getParamObj()));
        TransQueryLogDO queryLogDO = parseQueryLogDO(reqParam, reqAO);
        StopWatch timer = new StopWatch();
        ShunFengResp respObj = null;
        try {
            timer.start();
            result = shunfengSDKService.query(
                    null,
                    reqParam);
            timer.stop();
            // 实扣余额
            balanceService.checkAndCut(accountBalanceDO);
            respObj = JSON.parseObject(result, ShunFengResp.class);
            queryLogDO.setIsError((byte) 0);
            queryLogDO.setMessageId(respObj.getApiResponseID());
        } catch (Exception e) {
            if (timer.isRunning()) {
                timer.stop();
            }
            log.error("物流轨迹查询异常, 入参={}, ", reqAO, e);
            queryLogDO.setIsError((byte) 1);
            result = e.getMessage() == null ? "null" : e.getMessage();
            // 解冻余额
            balanceService.checkAndUnfrozen(accountBalanceDO);
            // 返回通信异常
            maptrackQueryRespAO.setState(TransStatusEnum.UNKNOW.getStringValue());
            maptrackQueryRespAO.setCondition("通信异常");
            maptrackQueryRespAO.setStatus(StatusCodeEnum.SERVER_ERROR.getStringCode());
            maptrackQueryRespAO.setMessage(e.getMessage());
            return maptrackQueryRespAO;
        } finally {
            queryLogDO.setCostTime(timer.getLastTaskTimeMillis());
            queryLogDO.setResponseMsg(result.length() > 512 ? result.substring(0, 512) : result);
            // 异步记录请求
            httpRateLimitPool.dealSupplier(() -> {
                return transQueryLogService.logQuery(Collections.singletonList(queryLogDO));
            });
        }
        if (respObj != null && "A1000".equals(respObj.getApiResultCode())) {
            // 通信成功，再检查一下业务是否成功
            ShunFengResp.ResultData apiResultData = respObj.getApiResultData();
            if (BooleanUtils.isNotTrue(apiResultData.getSuccess())) {
                maptrackQueryRespAO.setState(apiResultData.getErrorCode());
                maptrackQueryRespAO.setCondition("第三方业务响应失败");
                maptrackQueryRespAO.setStatus(StatusCodeEnum.QUERY_FAIL.getStringCode());
                maptrackQueryRespAO.setMessage(JSON.toJSONString(apiResultData));
            }
            // 解析响应报文，拼装出参
            MsgDataDTO respData = apiResultData.getMsgData();
            // 单个查询，只取第一条内容
            MsgDataDTO.RouteRespsDTO transRouteMsg = respData.getRouteResps().get(0);
            // 拼装出参
            maptrackQueryRespAO.setCom(reqAO.getParamObj().getCom());
            maptrackQueryRespAO.setNu(reqAO.getParamObj().getNum());
            // 将顺丰的最新操作码，映射成快递100标准的状态码
            TransStatusEnum transStatusEnum = parseShunFengOpCode(transRouteMsg.getNewestOpCode());
            maptrackQueryRespAO.setState(transStatusEnum.getStringValue());
            maptrackQueryRespAO.setCondition(transStatusEnum.getName());
            maptrackQueryRespAO.setStatus(StatusCodeEnum.OK.getStringCode());
            maptrackQueryRespAO.setMessage("ok");
            maptrackQueryRespAO.setIscheck("0");
            maptrackQueryRespAO.setData(transRouteMsg.getRoutes()
                    .stream()
                    .map(this::parseRespRouteFromShunFeng)
                    .collect(Collectors.toList()));
        } else {
            // 失败响应
            maptrackQueryRespAO.setState(TransStatusEnum.UNKNOW.getStringValue());
            maptrackQueryRespAO.setCondition(respObj != null ? respObj.getApiResultCode() : null);
            maptrackQueryRespAO.setStatus(StatusCodeEnum.SERVER_ERROR.getStringCode());
            maptrackQueryRespAO.setMessage(respObj != null ? respObj.getApiErrorMsg() : "respObj=null");
//            maptrackQueryRespAO.setIscheck("0");
        }
        return maptrackQueryRespAO;
    }

    /**
     * 初始化日志对象
     * @param reqParam 调用第三方的转换后的参数
     * @param reqAO 请求参数
     */
    private TransQueryLogDO parseQueryLogDO(Map<String, Object> reqParam, MaptrackQueryReqAO reqAO) {
        String requestMsg = JSON.toJSONString(reqParam);
        return new TransQueryLogDO()
                .accountId(reqAO.getCurrentUID())
                .thirdApiCompany(CompanyEnum.SHUNFENG.getCode())
                .requestMsg(requestMsg.length() > 512 ? requestMsg.substring(0, 512) : requestMsg)
                .createTime(new Date());
    }

    private MaptrackQueryRespAO.DataDTO parseRespRouteFromShunFeng(MsgDataDTO.RouteRespsDTO.RoutesDTO routesDTO) {
        MaptrackQueryRespAO.DataDTO dataDTO = new MaptrackQueryRespAO.DataDTO();
        dataDTO.setContext(routesDTO.getAcceptAddress() + "/" + parseShunFengOpCode(routesDTO.getOpCode()).getName());
        dataDTO.setTime(routesDTO.getAcceptTime());
        dataDTO.setFtime(routesDTO.getAcceptTime());
        return dataDTO;
    }


    /**
     * 将第三方(顺丰)的 code 转换为内部的code
     * @param opCode
     * @return
     */
    private TransStatusEnum parseShunFengOpCode(String opCode) {
        ThirdTransStatusEnum thirdTransStatusEnum = shunfengSDKService.parseThirdCodeToEnum(opCode);
        TransStatusEnum result = TransStatusEnum.UNKNOW;
        switch (thirdTransStatusEnum) {
            case ON_THE_WAY: result = TransStatusEnum.ON_THE_WAY ;break;
            case COLLECT: result = TransStatusEnum.COLLECT ;break;
            case PUZZLE: result = TransStatusEnum.PUZZLE ;break;
            case RECEIVED: result = TransStatusEnum.RECEIVED ;break;
            case REJECT_FINISH: result = TransStatusEnum.REJECT_FINISH ;break;
            case DISTRIBUTE: result = TransStatusEnum.DISTRIBUTE ;break;
            case REJECT_ON_WAY: result = TransStatusEnum.REJECT_ON_WAY ;break;
            case SWITCHING: result = TransStatusEnum.SWITCHING ;break;
            case WAIT_CLEAR: result = TransStatusEnum.WAIT_CLEAR ;break;
            case CLEARING: result = TransStatusEnum.CLEARING ;break;
            case CLEARED: result = TransStatusEnum.CLEARED ;break;
            case CLEAR_ERROR: result = TransStatusEnum.CLEAR_ERROR ;break;
            case REJECT: result = TransStatusEnum.REJECT ;break;
            case UNKNOW: result = TransStatusEnum.UNKNOW ;break;
            default:break;
        }
        return result;
    }

}
