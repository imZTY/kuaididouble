package com.zty.kdd.third.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uc56.uop.client.exception.ClientException;
import com.uc56.uop.client.util.HttpClientUtil;
import com.uc56.uop.client.util.SecurityUtil;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.impl.UceMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.request.UceMaptrackQueryRequest;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.response.UceMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 30/8/2021 - 下午 3:26
 */
@Service("UCE_SDK")
public class UceSDKServiceImpl extends AbstractMaptrackQuerySDKService<UceMaptrackQueryRequestFactory, UceMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(UceSDKServiceImpl.class);

    /**
     * API网关地址
     */
    public static final String SEND_URL = "http://uop.uc56.com/gateway/gateway.action";
//    public static final String SEND_URL = "http://uop.sit.uc56.com/uce-uop-main/gateway/gateway.action";

    //字符集
    public static final String CHARSET = "GBK";

    //签名方式
    public static final String SIGN_TYPE = "md5";

    //数据格式
    public static final String DATA_TYPE = "json";

    //密钥
    public static final String SECURITY_KEY = "5638725980faa155669d52616064f7f1";
    //合作伙伴编号
    public static final String PARTNER = "80448440";
//    public static final String PARTNER = "80239383";

    //调用的服务(映射的方法名)
    private static final String SERVER_NAME = "query_trace";

    /**
     * 业务异常信息描述MAP
     */
    private static final Map<String, String> BUSINESS_ERROR_MSG_MAP = new HashMap<>();

    static {
        BUSINESS_ERROR_MSG_MAP.put("NULL", "数据不存在");
        BUSINESS_ERROR_MSG_MAP.put("B0101", "产品服务未订购");
        BUSINESS_ERROR_MSG_MAP.put("B0102", "订单不能更新");
        BUSINESS_ERROR_MSG_MAP.put("B0103", "订单已经存在");
        BUSINESS_ERROR_MSG_MAP.put("B0104", "订单不存在");
        BUSINESS_ERROR_MSG_MAP.put("B0107", "字段不能为空，字符超长");
    }

    public UceSDKServiceImpl(UceMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     *
     * @return
     */
    @Override
    public String getUrl() {
        return SEND_URL;
    }

    @Override
    public Map<String, Object> getHeaders(QueryParamDTO thirdQueryDTO) {
        return null;
    }

    /**
     * 获取请求body
     * 转换时，注意Object后续的用法
     *
     * @param thirdMaptrackQueryRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Map<String, Object> getReqBody(ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public ThirdMaptrackQueryResponse query(Map<String, Object> headers, ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) {
        if (!(thirdMaptrackQueryRequest instanceof UceMaptrackQueryRequest)) {
            log.warn("入参不是UceMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是UceMaptrackQueryRequest");
        }
        String jsonData = JSON.toJSONString(thirdMaptrackQueryRequest);
        String url = getUrl();
        Map<String, String> request = null;
        try {
            request = getParam(jsonData);
        } catch (ClientException e) {
            log.error("优速，生成发送的数据失败", e);
            throw new BusinessException("UceSDKServiceImpl", "生成发送的数据失败", e);
        }
        log.info("优速查询，请求：{}, {}", url, request);
        String responseStr = null;
        try {
            responseStr = HttpClientUtil.sendHttpMessage(url, request);
        } catch (ClientException e) {
            log.error("优速，发送轨迹查询请求失败", e);
            throw new BusinessException("UceSDKServiceImpl", "发送轨迹查询请求失败", e);
        }
        log.info("优速查询，响应：{}", responseStr);
        UceMaptrackQueryResponse response = null;
        try {
            response = JSONObject.parseObject(responseStr, UceMaptrackQueryResponse.class);
        } catch (Exception e) {
            log.error("优速物流查询，响应报文解析异常", e);
            throw new BusinessException("UceSDKServiceImpl", "优速物流查询，响应报文解析异常", e);
        }
        // 通信结果
        response.setCommuniecationResult(this.parseCommunicateResult(response, responseStr));
        if (response.isCommunicateSuccess()) {
            // 通信成功才判断业务结果
            response.setBusinessResult(this.parseBusinessResult(response));
        }
        return response;
    }

    private Map<String, String> getParam(String jsonData) throws ClientException {
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("partner", PARTNER);
            param.put("charset", CHARSET);
            param.put("dataType", DATA_TYPE);
            param.put("serviceName", SERVER_NAME);
            param.put("data", jsonData);
            param.put("dataSign", SecurityUtil.sign(param, SIGN_TYPE, SECURITY_KEY,
                    CHARSET));
            return param;
        } catch (Exception e) {
            throw new ClientException("生成发送的数据失败");
        }
    }
    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     *
     * @param optType
     * @return
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String optType) {
        if (null == optType) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (optType) {
            case "收件":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "发件": ;
            case "到件": ;
            case "转件":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "派件":
                statusEnum = ThirdTransStatusEnum.DISTRIBUTE;
                break;
            case "问题件":
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "签收":
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "退件":
                statusEnum = ThirdTransStatusEnum.REJECT_ON_WAY;
                break;
            case "退件完成":
                statusEnum = ThirdTransStatusEnum.REJECT_FINISH;
                break;
            default: ;break;
        }
        return statusEnum;
    }

    /**
     * 解析并获取第三方通信结果
     *
     * @param response
     * @param responseStr
     */
    @Override
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(UceMaptrackQueryResponse response, String responseStr) {
        log.info("检查通信是否成功: isSuccess:{}", response.getResponse().getIsSuccess());
        if ("T".equals(response.getResponse().getIsSuccess())) {
            // 成功
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, "", responseStr);
        } else if (response.getResponse().getIsSuccess().startsWith("B")){
            // 业务处理失败
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, "", responseStr);
        } else if (response.getResponse().getIsSuccess().startsWith("S")){
            // 系统通信异常
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 1, "", responseStr);
        } else {
            // 无法识别
            return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 1, "", responseStr);
        }
    }

    /**
     * 解析并获取第三方业务结果
     *
     * @param response
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(UceMaptrackQueryResponse response) {
        log.info("优速查询 开始检查是否业务响应成功, errorCode:{}", response.getResponse().getErrorCode());
        // 如果是通信异常，不会走到这里
        if ("F".equals(response.getResponse().getIsSuccess())
                && response.getResponse().getErrorCode().startsWith("B")) {
            // 业务响应失败，业务异常
            String errorMessage = BUSINESS_ERROR_MSG_MAP.get(response.getResponse().getErrorCode());
            log.warn("优速查询, 业务失败, {}, {}", response.getResponse().getErrorCode(), errorMessage);
            return new ThirdMaptrackQueryResponse.BusinessResult(false, response.getResponse().getErrorCode(), errorMessage);
        } else if (CollectionUtils.isEmpty(response.getResponse().getTraceList()) || CollectionUtils.isEmpty(response.getResponse().getTraceList().get(0).getTrace())) {
            // 业务响应成功，结果为空
            log.warn("优速查询, 业务响应成功，结果为空");
            return new ThirdMaptrackQueryResponse.BusinessResult(true, null, "数据为空");
        } else {
            // 业务响应成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, response.getResponse().getErrorCode(), "成功");
            // 轨迹数据逆序排序
            response.getResponse().getTraceList().get(0).sortByTimeDesc();
            // 【重点1】设置物流当前的运输状态
            businessResult.setThirdStateCode(response.getResponse().getTraceList().get(0).getTrace().get(0).getOptType());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(response.getResponse().getTraceList().get(0).getTrace()
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(UceMaptrackQueryResponse.Trace trace) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(trace.getOptType() + "/" + trace.getDesc());
        thirdTrackDataDTO.setTime(trace.getOptTime());
        thirdTrackDataDTO.setFtime(trace.getLongOptTime());
        return thirdTrackDataDTO;
    }
}
