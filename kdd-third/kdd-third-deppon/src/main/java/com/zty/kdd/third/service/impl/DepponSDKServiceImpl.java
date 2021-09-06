package com.zty.kdd.third.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.result.ResultDO;
import com.deppon.dop.module.sdk.shared.util.HttpUtils;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.impl.DepponMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.DepponMaptrackQueryRequest;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.response.DepponMaptrackQueryResponse;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 2/9/2021 - 上午 5:36
 */
@Service("DBL_SDK")
public class DepponSDKServiceImpl extends AbstractMaptrackQuerySDKService<DepponMaptrackQueryRequestFactory, DepponMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(DepponSDKServiceImpl.class);

    // 测试环境
    /**
     * 客户编码 88888
     * sign  YYYF
     * companyCode  EWBHZZMKJYXGS
     * appKey  51edd8c7a2357ffc0b8d0772842b42c4
     */
    private String TEST_URL = "http://dpsanbox.deppon.com/sandbox-web/standard-order/newTraceQuery.action";
    private String PRO_URL = "http://dpapi.deppon.com/dop-interface-sync/standard-query/newTraceQuery.action";  // 生产
    private String CUSTOMER_CODE = "88888";
    private String SIGN = "YYYF";
    private String COMPANY_CODE = "EWBHZZMKJYXGS";
    private String APP_KEY = "51edd8c7a2357ffc0b8d0772842b42c4";

    public DepponSDKServiceImpl(DepponMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     *
     * @return
     */
    @Override
    public String getUrl() {
        return PRO_URL;
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
        if (!(thirdMaptrackQueryRequest instanceof DepponMaptrackQueryRequest)) {
            log.warn("入参不是DepponMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是DepponMaptrackQueryRequest");
        }
        String url = getUrl();
        String requestStr = JSON.toJSONString(thirdMaptrackQueryRequest);
        String timestamp = System.currentTimeMillis() + "";
        //生成摘要
        String digest = Base64.encodeBase64String(DigestUtils.md5Hex(requestStr + APP_KEY + timestamp)
                .getBytes());
        //post请求参数
        NameValuePair[] data = new NameValuePair[4];
        data[0] = new NameValuePair("companyCode", COMPANY_CODE);
        data[1] = new NameValuePair("digest", digest);
        data[2] = new NameValuePair("timestamp", timestamp);
        data[3] = new NameValuePair("params", requestStr);
        log.info("德邦查询，请求：{}, {}", url, data);
        ResultDO<String> sdkResponse = null;
        try {
            sdkResponse = HttpUtils.sendRequest(url, data, "UTF-8", 5000);
        } catch (Exception e) {
            log.error("德邦查询，未知异常", e);
            throw new BusinessException("", e.getMessage());
        }
        log.info("德邦查询，响应：{}, {}, {}", sdkResponse.isSuccess(), sdkResponse.getExceptionList(), sdkResponse.getModel());
        // 检查SDK是否有异常
        if (!sdkResponse.isSuccess() && !CollectionUtils.isEmpty(sdkResponse.getExceptionList())) {
            for (Exception e : sdkResponse.getExceptionList()) {
                log.error("德邦查询, SDK异常", e);
            }
            // 只取第一个异常进行抛出
            Exception e = sdkResponse.getExceptionList().get(0);
            if (e instanceof IOException) {
                throw new NetworkException("德邦查询，网络异常:" + e.getMessage());
            } else {
                throw new BusinessException("DepponSDKServiceImpl", "百世查询，未知异常:" + e.getMessage());
            }
        }
        // SDK无异常
        DepponMaptrackQueryResponse response = JSON.parseObject(sdkResponse.getModel(), DepponMaptrackQueryResponse.class);
        // 通信结果
        response.setCommuniecationResult(this.parseCommunicateResult(response, sdkResponse.getModel()));
        if (response.isCommunicateSuccess()) {
            // 通信成功才判断业务结果
            response.setBusinessResult(this.parseBusinessResult(response));
        }
        return response;
    }

    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     *
     * @param status
     * @return
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String status) {
        if (null == status) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (status) {
            case "收件":
            case "GOT":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "发件":
            case "到件":
            case "转件":
            case "DEPARTURE":
            case "ARRIVAL":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "派件":
            case "SENT_SCAN":
            case "ADVANCE_DELIVERY":
                statusEnum = ThirdTransStatusEnum.DISTRIBUTE;
                break;
            case "拒签":
            case "FAILED":
                statusEnum = ThirdTransStatusEnum.REJECT;
                break;
            case "问题件":
            case "ERROR":
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "转投":
            case "OPERATETRACK":
                statusEnum = ThirdTransStatusEnum.SWITCHING;
                break;
            case "签收":
            case "SIGNED":
            case "STA_INBOUND":
            case "STA_SIGN":
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "退件":
                statusEnum = ThirdTransStatusEnum.REJECT_ON_WAY;
                break;
            case "退件完成":
            case "BACK_SIGNED":
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
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(DepponMaptrackQueryResponse response, String responseStr) {
        // 暂无明确的通信异常判断方式，SDK无异常则默认通信成功
        return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, response.getUniquerRequestNumber(), responseStr);
    }

    /**
     * 解析并获取第三方业务结果
     *
     * @param response
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(DepponMaptrackQueryResponse response) {
        log.debug("德邦查询 开始检查是否业务成功 {}", response.getResult());
        if ("true".equals(response.getResult())) {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, response.getResultCode(), response.getReason());
            // 判断是否是空数据
            if (response.getResponseParam() == null
                    || CollectionUtils.isEmpty(response.getResponseParam().getTrace_list())) {
                log.warn("德邦查询 无轨迹结果");
                return businessResult;
            }
            // 轨迹数据逆序排序
            response.getResponseParam().sortByTimeDesc();
            // 【重点1】设置物流当前的运输状态
            businessResult.setThirdStateCode(response.getResponseParam().getTrace_list().get(0).getStatus());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(response.getResponseParam().getTrace_list()
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        } else if ("false".equals(response.getResult())) {
            // 业务失败
            log.warn("德邦查询, 业务失败, {}, {}", response.getResultCode(), response.getReason());
            return new ThirdMaptrackQueryResponse.BusinessResult(false, response.getResultCode(), response.getReason());
        } else {
            // 无法识别
            log.warn("德邦查询, 无法识别响应字段 result: {}", response.getResult());
            throw new BusinessException("DepponSDKServiceImpl", "无法识别响应字段 result:"+response.getResult());
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(DepponMaptrackQueryResponse.Trace trace) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(trace.getStatus() + "/" + trace.getDescription());
        thirdTrackDataDTO.setTime(trace.getTime());
        thirdTrackDataDTO.setFtime(trace.getLongTime());
        return thirdTrackDataDTO;
    }
}
