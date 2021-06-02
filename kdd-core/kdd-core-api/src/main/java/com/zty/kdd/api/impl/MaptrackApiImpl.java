package com.zty.kdd.api.impl;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.ao.MaptrackQueryRespAO;
import com.zty.kdd.api.MaptrackApi;
import com.zty.kdd.enums.StatusCodeEnum;
import com.zty.kdd.enums.TransStatusEnum;
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

    @Resource(name = "Shunfeng_SDK")
    private AbstractSDKService shunfengSDKService;

    @Override
    public MaptrackQueryRespAO singleQuery(MaptrackQueryReqAO reqAO) throws UnsupportedEncodingException {
        MaptrackQueryRespAO maptrackQueryRespAO = new MaptrackQueryRespAO();
        // TODO 扣减余额
        String result = shunfengSDKService.query(
                null,
                shunfengSDKService.getReqBody(new SFThirdQueryDTO(reqAO.getParamObj()))
        );
        ShunFengResp respObj = JSON.parseObject(result, ShunFengResp.class);
        if ("A1000".equals(respObj.getApiResultCode())) {
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
            maptrackQueryRespAO.setCondition(respObj.getApiResultCode());
            maptrackQueryRespAO.setStatus(StatusCodeEnum.SERVER_ERROR.getStringCode());
            maptrackQueryRespAO.setMessage(respObj.getApiErrorMsg());
//            maptrackQueryRespAO.setIscheck("0");
        }
        return maptrackQueryRespAO;
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
        }
        return result;
    }

}
