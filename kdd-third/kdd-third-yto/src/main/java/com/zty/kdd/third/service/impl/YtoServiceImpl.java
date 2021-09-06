package com.zty.kdd.third.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.CommonException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.impl.YtoMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.request.YtoMaptrackQueryRequest;
import com.zty.kdd.third.response.SuccessMaptrackResponse;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.response.YtoMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import com.zty.kdd.third.util.YuantongApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 5/9/2021 - 上午 10:17
 */
@Service("YTO_service")
public class YtoServiceImpl extends AbstractMaptrackQuerySDKService<YtoMaptrackQueryRequestFactory, YtoMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(YtoServiceImpl.class);

    public YtoServiceImpl(YtoMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     *
     * @return
     */
    @Override
    public String getUrl() {
        return null;
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
        if (!(thirdMaptrackQueryRequest instanceof YtoMaptrackQueryRequest)) {
            log.warn("入参不是YtoMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是YtoMaptrackQueryRequest");
        }
        YtoMaptrackQueryResponse response = null;
        try {
            // 内含出参入参日志打印
            response = YuantongApiUtil.mapTrackQuery((YtoMaptrackQueryRequest) thirdMaptrackQueryRequest);
        } catch (CommonException e) {
            // 已知异常 继续抛出
            throw e;
        } catch (Exception e) {
            log.error("圆通查询 未知异常，", e);
            throw new BusinessException("YtoServiceImpl", " 未知异常" + e.getMessage());
        }
        // 通信结果
        response.setCommuniecationResult(this.parseCommunicateResult(response, response.isSuccess() ? JSONArray.toJSONString(response.getSuccessMaptrackResponse()) : JSON.toJSONString(response.getErrorMaptrackResponse())));
        if (response.isCommunicateSuccess()) {
            // 通信成功才判断业务结果
            response.setBusinessResult(this.parseBusinessResult(response));
        }
        return response;
    }

    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     *
     * @param infoContent
     * @return
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String infoContent) {
        if (null == infoContent) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (infoContent) {
            case "收件":
            case "GOT":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "发件":
            case "到件":
            case "转件":
            case "PACKAGE":
            case "DEPARTURE":
            case "ARRIVAL":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "派件":
            case "SENT_SCAN":
                statusEnum = ThirdTransStatusEnum.DISTRIBUTE;
                break;
            case "拒签":
            case "FAILED":
                statusEnum = ThirdTransStatusEnum.REJECT;
                break;
            case "问题件":
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "转投":
            case "FORWARDING":
                statusEnum = ThirdTransStatusEnum.SWITCHING;
                break;
            case "签收":
            case "SIGNED":
            case "INBOUND":
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "退件":
            case "TMS_RETURN":
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
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(YtoMaptrackQueryResponse response, String responseStr) {
        // 无异常 默认响应成功
        return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, System.currentTimeMillis()+"", responseStr);
    }

    /**
     * 解析并获取第三方业务结果
     *
     * @param response
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(YtoMaptrackQueryResponse response) {
        log.debug("圆通查询 开始检查是否业务成功 {}", response.isSuccess());
        if (response.isSuccess()) {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, null, "");
            // 判断是否是空数据
            if (response.getSuccessMaptrackResponse() == null
                    || CollectionUtils.isEmpty(response.getSuccessMaptrackResponse().getTraces())) {
                log.warn("圆通查询 无轨迹结果");
                return businessResult;
            }
            // 轨迹数据逆序排序
            response.getSuccessMaptrackResponse().sortByTimeDesc();
            // 【重点1】设置物流当前的运输状态
            businessResult.setThirdStateCode(response.getSuccessMaptrackResponse().getTraces().get(0).getInfoContent());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(response.getSuccessMaptrackResponse().getTraces()
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        } else {
            // 业务失败
            log.warn("圆通查询, 业务失败, {}, {}", response.getErrorMaptrackResponse().getCode(), response.getErrorMaptrackResponse().getMessage());
            return new ThirdMaptrackQueryResponse.BusinessResult(false, response.getErrorMaptrackResponse().getCode(), response.getErrorMaptrackResponse().getMessage());
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(SuccessMaptrackResponse.Trace trace) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(trace.getInfoContent() + "/" + trace.getProcessInfo());
        thirdTrackDataDTO.setTime(trace.getUpload_Time());
        thirdTrackDataDTO.setFtime(trace.getLongUpload_Time());
        return thirdTrackDataDTO;
    }
}
