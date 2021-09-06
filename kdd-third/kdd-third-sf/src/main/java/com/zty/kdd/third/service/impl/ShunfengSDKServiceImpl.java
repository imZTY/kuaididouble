package com.zty.kdd.third.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.csim.express.service.CallExpressServiceTools;
import com.sf.csim.express.service.EspServiceCode;
import com.sf.csim.express.service.HttpClientUtil;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.factory.impl.SFMaptrackQueryRequestFactory;
import com.zty.kdd.third.resp.MsgDataDTO;
import com.zty.kdd.third.resp.SFMaptrackQueryResponse;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zty.kdd.third.request.SFMaptrackQueryRequest;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.springframework.util.CollectionUtils;

/**
 * 顺丰物流查询，SDK对接实现
 * @author tianyi
 * @date 2021-05-05 22:17
 */
@Service("Shunfeng_SDK")
public class ShunfengSDKServiceImpl extends AbstractMaptrackQuerySDKService<SFMaptrackQueryRequestFactory, SFMaptrackQueryResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ShunfengSDKServiceImpl.class);

    //丰桥新沙箱测试顾客编码  Yg4Zf06w_sxZs3A5D
    //校验码                          3Xdk1jqeG1Xod9nUXus8Op7DNOkchTnw
    private static final String CLIENT_CODE = "KMKJPh";  //此处替换为您在丰桥平台获取的顾客编码
    private static final String CHECK_WORD = "DGJiz0VoHeEKnSAuK98sPIwqTQX6xupI";//此处替换为您在丰桥平台获取的校验码

    //沙箱环境的地址
    private static final String CALL_URL_BOX = "https://sfapi-sbox.sf-express.com/std/service";
    //生产环境的地址
    private static final String CALL_URL_PROD = "https://sfapi.sf-express.com/std/service";

    public ShunfengSDKServiceImpl(SFMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 文档：https://qiao.sf-express.com/pages/developDoc/index.html?level2=324185
     * @param headers
     * @param thirdMaptrackQueryRequest 顺丰请求参数{
     * 	"language": "0",   //错误信息语语言
     * 	"trackingType": "1",  //1=运单号查询，2=订单号查询
     * 	"trackingNumber": ["444003077898", "441003077850"],
     * 	"methodType": "1"  //1=标准路由查询，2=定制路由查询
     * }
     * @return 注意，示例里的JSONObject实际全是jo字符串{
     *     "apiErrorMsg": "",
     *     "apiResponseID": "0001793CFE595E3FEB9425417E3E3F3F",
     *     "apiResultCode": "A1000",
     *     "apiResultData": {
     *         "success": true,
     *         "errorCode": "S0000",
     *         "errorMsg": null,
     *         "msgData": {"routeResps": [
     *             {
     *                 "mailNo": "444003077898",
     *                 "routes": [{
     *                     "acceptAddress": "深圳市",
     *                     "acceptTime": "2021-05-05 22:46:12",
     *                     "remark": "顺丰速运 已收取快件",
     *                     "opCode": "50"
     *                 }]
     *             },
     *             {
     *                 "mailNo": "441003077850",
     *                 "routes": [{
     *                     "acceptAddress": "深圳市",
     *                     "acceptTime": "2021-05-05 22:46:12",
     *                     "remark": "顺丰速运 已收取快件",
     *                     "opCode": "50"
     *                 }]
     *             }
     *         ]}
     *     }
     * }
     */
    @Override
    public ThirdMaptrackQueryResponse query(Map<String, Object> headers, ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) {
        Map<String, Object> params = this.getReqBody(thirdMaptrackQueryRequest);
        Map<String, String> reqBody;
        try {
            reqBody = prepareReqBody(params);
            String url = CALL_URL_PROD;
            logger.info("顺丰查询，请求：{}, {}", url, reqBody);
            String respStr = HttpClientUtil.post(url, reqBody);
            logger.info("顺丰查询，响应：{}", respStr);
            SFMaptrackQueryResponse response = JSON.parseObject(respStr, SFMaptrackQueryResponse.class);
            // 通信结果
            response.setCommuniecationResult(this.parseCommunicateResult(response, respStr));
            if (response.isCommunicateSuccess()) {
                // 通信成功才判断业务结果
                response.setBusinessResult(this.parseBusinessResult(response));
            }
            return response;
        } catch (UnsupportedEncodingException e) {
            logger.error("顺丰物流查询，生成请求报文时加密出错", e);
            throw new BusinessException("ShunfengSDKServiceImpl", "顺丰物流查询，生成请求报文时加密出错" + e.getMessage());
        } catch (IOException e) {
            logger.error("顺丰物流查询，网络异常", e);
            throw new NetworkException("顺丰物流查询，网络异常:" + e.getMessage());
        }
    }

    /**
     * 解析并获取第三方通信结果
     * 判断顺丰的第三方通信结果是否成功
     */
    @Override
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(SFMaptrackQueryResponse sfMaptrackQueryResponse, String responseStr) {
        logger.debug("顺丰查询 开始检查是否通信成功, {}", sfMaptrackQueryResponse.getApiResultCode());
        if ("A1000".equals(sfMaptrackQueryResponse.getApiResultCode())) {
            // 通信成功
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, sfMaptrackQueryResponse.getApiResponseID(), responseStr);
        } else {
            // 通信失败
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 1, sfMaptrackQueryResponse.getApiResponseID(), responseStr);
        }
    }

    /**
     * 解析并获取第三方业务结果
     * 判断顺丰的第三方业务结果是否成功
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(SFMaptrackQueryResponse sfMaptrackQueryResponse) {
        SFMaptrackQueryResponse.ResultData apiResultData = sfMaptrackQueryResponse.getApiResultData();
        logger.debug("顺丰查询 开始检查是否业务成功, {}", apiResultData.getSuccess());
        if (BooleanUtils.isNotTrue(apiResultData.getSuccess())) {
            // 业务失败
            logger.warn("顺丰查询 业务失败, {}, {}", apiResultData.getErrorCode(), apiResultData.getErrorMsg());
            return new ThirdMaptrackQueryResponse.BusinessResult(false, apiResultData.getErrorCode(), apiResultData.getErrorMsg());
        } else {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, apiResultData.getErrorCode(), apiResultData.getErrorMsg());
            // 解析响应报文，拼装出参
            MsgDataDTO respData = apiResultData.getMsgData();
            // 取与查询运单号匹配的响应（目前没有批量查询场景，所以获取第一个就行）
            MsgDataDTO.RouteRespsDTO transRouteMsg = respData.getRouteResps().get(0);
            if (CollectionUtils.isEmpty(transRouteMsg.getRoutes())) {
                // 无轨迹
                logger.warn("顺丰查询 无轨迹结果");
                return businessResult;
            }
            // 【重点1】设置物流当前的运输状态
            // 获取最新的state（内含降序排序功能）
            businessResult.setThirdStateCode(transRouteMsg.getNewestOpCode());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(transRouteMsg.getRoutes()
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        }
    }

    /**
     * 将第三方的轨迹节点转换成third模块标准的轨迹节点
     */
    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(MsgDataDTO.RoutesDTO routesDTO) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(routesDTO.getAcceptAddress() + "/" + routesDTO.getRemark());
        thirdTrackDataDTO.setTime(routesDTO.getAcceptTime());
        thirdTrackDataDTO.setFtime(routesDTO.getAcceptTime());
        return thirdTrackDataDTO;
    }

    private Map<String, String> prepareReqBody(Map<String, Object> params) throws UnsupportedEncodingException {
        Map<String, String> reqBody = new HashMap<String, String>();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String msgData = JSONObject.toJSONString(params);

        reqBody.put("partnerID", CLIENT_CODE);  // 顾客编码 ，对应丰桥上获取的clientCode
        reqBody.put("requestID", UUID.randomUUID().toString().replace("-", ""));
        reqBody.put("serviceCode", EspServiceCode.EXP_RECE_SEARCH_ROUTES.getCode());// 接口服务码
        reqBody.put("timestamp", timeStamp);
        reqBody.put("msgData", msgData);
        reqBody.put("msgDigest", CallExpressServiceTools.getMsgDigest(msgData,timeStamp,CHECK_WORD));

        return reqBody;
    }

    @Override
    public Map<String, Object> getReqBody(ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) {
        if (!(thirdMaptrackQueryRequest instanceof SFMaptrackQueryRequest)) {
            logger.warn("入参不是SFMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是SFMaptrackQueryRequest");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("language", "0");
        params.put("trackingType", "1");
        params.put("methodType", "1");
        params.put("trackingNumber", Collections.singletonList(thirdMaptrackQueryRequest.getTransOrderNo()));
        params.put("checkPhoneNo", thirdMaptrackQueryRequest.getPhone().substring(
                thirdMaptrackQueryRequest.getPhone().length() - 4
        ));
        return params;
    }

    /**
     * 将顺丰的code转换成内部的标准第三方响应code
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String transCode) {
        if (null == transCode) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (transCode) {
            case "50":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "30": ;
            case "31": ;
            case "3036": ;
            case "44": ;
            case "130": ;
            case "123":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "33": ;
            case "70": ;
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "80": ;
            case "8000": ;
            case "607":
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "99":
                statusEnum = ThirdTransStatusEnum.REJECT_ON_WAY;
                break;
            case "648":
                statusEnum = ThirdTransStatusEnum.REJECT_FINISH;
                break;
            default: ;break;
        }
        return statusEnum;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, Object> getHeaders(QueryParamDTO thirdQueryDTO) {
        return null;
    }
}
