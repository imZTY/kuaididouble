package com.zty.kdd.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.zty.kdd.DO.TransQueryLogDO;
import com.zty.kdd.request.MaptrackQueryRequest;
import com.zty.kdd.response.MaptrackQueryResponse;
import com.zty.kdd.api.MaptrackApi;
import com.zty.kdd.enums.CompanyEnum;
import com.zty.kdd.enums.StatusCodeEnum;
import com.zty.kdd.enums.TransStatusEnum;
import com.zty.kdd.limiter.AsyncLogRateLimitPool;
import com.zty.kdd.service.TransQueryLogService;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;

/**
 * @author tianyi
 * @date 2021-03-01 22:49
 */
@Service
public class MaptrackApiImpl implements MaptrackApi {

    private static final Logger log = LoggerFactory.getLogger(MaptrackApiImpl.class);

    @Resource(name = "Kdniao_service")
    private AbstractMaptrackQuerySDKService kdniaoService;

    @Resource(name = "Shunfeng_SDK")
    private AbstractMaptrackQuerySDKService shunfengSDKService;

    @Resource(name = "ZTO_SDK")
    private AbstractMaptrackQuerySDKService ztoSDKService;

    @Resource(name = "UCE_SDK")
    private AbstractMaptrackQuerySDKService uceSDKService;

    @Resource(name = "Best_SDK")
    private AbstractMaptrackQuerySDKService bestSDKService;

    @Resource(name = "DBL_SDK")
    private AbstractMaptrackQuerySDKService depponSDKServic;

    @Resource(name = "YTO_service")
    private AbstractMaptrackQuerySDKService ytoServic;

    /**
     * KEY: 在线文档定义的内部码值
     * VALUE: 具体物流服务BEAN
     */
    private static final Map<String, AbstractMaptrackQuerySDKService> THIRD_SERVICE_MAP = new HashMap<>();

    @PostConstruct
    private void init() {
        // 顺丰SDK服务
        THIRD_SERVICE_MAP.put("SF", shunfengSDKService);
        // 中通SDK服务 TODO 审核未通过 测试数据不全 未验证
        THIRD_SERVICE_MAP.put("ZTO", ztoSDKService);
        // 优速SDK服务
        THIRD_SERVICE_MAP.put("UCE", uceSDKService);
        // 百世SDK服务（对外的是 HTKY）
        THIRD_SERVICE_MAP.put("HTKY", bestSDKService);
        // 德邦SDK服务
        THIRD_SERVICE_MAP.put("DBL", depponSDKServic);
        // 圆通服务
        THIRD_SERVICE_MAP.put("YTO", ytoServic);
    }

    @Autowired
    private TransQueryLogService transQueryLogService;

    /**
     * 异步日志记录限频线程池
     */
    @Autowired
    private AsyncLogRateLimitPool asyncLogRateLimitPool;

    @Override
    public MaptrackQueryResponse singleQuery(MaptrackQueryRequest reqAO) throws Exception {
        if (StringUtils.isBlank(reqAO.getKddCompanyCode())) {
            log.warn("快递公司编号不可为空");
            throw new ParamCheckException("快递公司编号不可为空", "kddCompanyCode", reqAO.getKddCompanyCode());
        }
        // 根据入参key选择对应的实现通道
        AbstractMaptrackQuerySDKService thirdService = THIRD_SERVICE_MAP.get(reqAO.getKddCompanyCode());
        String originCom = reqAO.getParamObj().getCom();
        if (thirdService == null) {
            log.warn("找不到{}对应的实现，将选用快递鸟通道进行查询", reqAO.getKddCompanyCode());
            reqAO.getParamObj().setCom(reqAO.getKddCompanyCode());
            reqAO.setKddCompanyCode("Kdniao");
            thirdService = kdniaoService;
            // throw new ParamCheckException("暂不支持该快递公司:" + reqAO.getKddCompanyCode());
        }
        // 拼装请求参数(含参数校验)
        ThirdMaptrackQueryRequest thirdRequest = thirdService.getThirdQueryRequest(reqAO.getParamObj());
        // 日志记录构建器
        TransQueryLogDO.Builder logBuilder = parseQueryLogDOBuilder(thirdRequest, reqAO);
        // 请求计时器，计算耗时
        StopWatch timer = new StopWatch();
        // 第三方响应对象
        ThirdMaptrackQueryResponse thirdRespObj = null;
        try {
            timer.start();
            thirdRespObj = thirdService.query(
                    thirdService.getHeaders(reqAO.getParamObj()),
                    thirdRequest);
            timer.stop();
            // 记录通信结果(是否失败、第三方消息号)
            logBuilder.communicationResult(thirdRespObj.getCommunicationResult());
        } catch (Exception e) {
            if (timer.isRunning()) {
                timer.stop();
            }
            log.warn("物流轨迹查询异常, 入参={}, ", reqAO, e);
            logBuilder.communicationResult(ThirdMaptrackQueryResponse.CommunicateResult.error(
                    e.getMessage() == null ? "null" : e.getMessage()
            ));
            // 继续抛出异常，在controller层统一处理
            throw e;
        } finally {
            logBuilder.costTime(timer.getLastTaskTimeMillis());
            // 异步记录请求
            asyncLogRateLimitPool.dealSupplier(() -> {
                return transQueryLogService.logQuery(Collections.singletonList(logBuilder.build()));
            });
        }
        // 组装响应出参
        MaptrackQueryResponse maptrackQueryResponse = new MaptrackQueryResponse();
        maptrackQueryResponse.setCom(originCom);  // 如果无实现，reqAO.getParamObj()会被替换为快递鸟的公司码，需用originCom
        maptrackQueryResponse.setNu(reqAO.getParamObj().getNum());
        // 通信没报错，继续解析第三方报文里的信息
        ThirdMaptrackQueryResponse.CommunicateResult communicateResult = thirdRespObj.getCommunicationResult();
        if (thirdRespObj != null && thirdRespObj.isCommunicateSuccess()) {
            ThirdMaptrackQueryResponse.BusinessResult businessResult = thirdRespObj.getBusinessResult();
            // 第三方通信成功，再检查一下业务是否成功
            if (thirdRespObj.isBusinessSuccess()) {
                // 业务成功
                maptrackQueryResponse.setMessage("ok");
                maptrackQueryResponse.setStatus(StatusCodeEnum.OK.getStringCode());
                // 第三方状态转换
                maptrackQueryResponse.setState(parseThirdStateCode(businessResult.getThirdStateCode(), thirdService).getStringValue());
                // 物流链路数据拼装
                maptrackQueryResponse.setData(parseThirdTrackData(businessResult.getThirdTrackDataList()));
            } else {
                // 业务失败
                maptrackQueryResponse.setState(TransStatusEnum.UNKNOW.getStringValue());
                maptrackQueryResponse.setMessage(StatusCodeEnum.QUERY_FAIL.getMessage());
                maptrackQueryResponse.setStatus(StatusCodeEnum.QUERY_FAIL.getStringCode());
            }
        } else {
            // 通信失败响应
            maptrackQueryResponse.setState(TransStatusEnum.UNKNOW.getStringValue());
            maptrackQueryResponse.setStatus(StatusCodeEnum.SERVER_BUSY.getStringCode());
            maptrackQueryResponse.setMessage(communicateResult.getResponseStr());
        }
        return maptrackQueryResponse;
    }

    /**
     * 需保持顺序一致
     * @param thirdTrackDataList 第三方标准出参，倒序排列
     * @return 标准出参
     */
    private List<MaptrackQueryResponse.TrackDataDTO> parseThirdTrackData(List<ThirdMaptrackQueryResponse.ThirdTrackDataDTO> thirdTrackDataList) {
        List<MaptrackQueryResponse.TrackDataDTO> trackDataList = new ArrayList<>();
        for (int i = 0; i < thirdTrackDataList.size(); i++) {
            ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackData = thirdTrackDataList.get(i);
            MaptrackQueryResponse.TrackDataDTO trackDataDTO = new MaptrackQueryResponse.TrackDataDTO();
            trackDataDTO.setContext(thirdTrackData.getContext());
            trackDataDTO.setTime(thirdTrackData.getTime());
            trackDataDTO.setFtime(thirdTrackData.getFtime());
            trackDataList.add(trackDataDTO);
        }
        return trackDataList;
    }

    /**
     * 初始化日志对象
     * @param thirdRequest 调用第三方的转换后的参数
     * @param reqAO    请求参数
     * @return
     */
    private TransQueryLogDO.Builder parseQueryLogDOBuilder(ThirdMaptrackQueryRequest thirdRequest, MaptrackQueryRequest reqAO) {
        String requestMsg = JSON.toJSONString(thirdRequest);
        CompanyEnum companyEnum = CompanyEnum.getByApikey(reqAO.getKddCompanyCode(), true);
        return new TransQueryLogDO.Builder()
                .accountId(reqAO.getCurrentUID())
                .thirdApiCompany(companyEnum.getCode()) //传了true，companyEnum不会为null
                .requestMsg(requestMsg.length() > 512 ? requestMsg.substring(0, 512) : requestMsg)
                .createTime(new Date());
    }

    /**
     * 将第三方的 code 转换为内部的code
     * @param thirdStateCode
     * @return
     */
    private TransStatusEnum parseThirdStateCode(String thirdStateCode, AbstractMaptrackQuerySDKService thirdService) {
        ThirdTransStatusEnum thirdTransStatusEnum = thirdService.parseThirdCodeToEnum(thirdStateCode);
        log.info("第三方状态枚举: {}", thirdTransStatusEnum);
        TransStatusEnum result = TransStatusEnum.UNKNOW;
        switch (thirdTransStatusEnum) {
            case ON_THE_WAY:
                result = TransStatusEnum.ON_THE_WAY;
                break;
            case COLLECT:
                result = TransStatusEnum.COLLECT;
                break;
            case PUZZLE:
                result = TransStatusEnum.PUZZLE;
                break;
            case RECEIVED:
                result = TransStatusEnum.RECEIVED;
                break;
            case REJECT_FINISH:
                result = TransStatusEnum.REJECT_FINISH;
                break;
            case DISTRIBUTE:
                result = TransStatusEnum.DISTRIBUTE;
                break;
            case REJECT_ON_WAY:
                result = TransStatusEnum.REJECT_ON_WAY;
                break;
            case SWITCHING:
                result = TransStatusEnum.SWITCHING;
                break;
            case WAIT_CLEAR:
                result = TransStatusEnum.WAIT_CLEAR;
                break;
            case CLEARING:
                result = TransStatusEnum.CLEARING;
                break;
            case CLEARED:
                result = TransStatusEnum.CLEARED;
                break;
            case CLEAR_ERROR:
                result = TransStatusEnum.CLEAR_ERROR;
                break;
            case REJECT:
                result = TransStatusEnum.REJECT;
                break;
            case UNKNOW:
                result = TransStatusEnum.UNKNOW;
                break;
            default:
                break;
        }
        return result;
    }

}
