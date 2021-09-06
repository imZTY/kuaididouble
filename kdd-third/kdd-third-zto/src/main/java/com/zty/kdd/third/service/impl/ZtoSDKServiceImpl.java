package com.zty.kdd.third.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.zto.zop.ZopClient;
import com.zto.zop.ZopPublicRequest;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.impl.ZtoMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.request.ZtoMaptrackQueryRequest;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.response.ZtoMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 29/8/2021 - 下午 4:23
 */
@Service("ZTO_SDK")
public class ZtoSDKServiceImpl extends AbstractMaptrackQuerySDKService<ZtoMaptrackQueryRequestFactory, ZtoMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(ZtoSDKServiceImpl.class);

    // 沙箱环境
    private static final String url = "https://japi-test.zto.com/zto.open.getRouteInfo";
    private static final String appKey = "a926de8a8a726544c5131";
    private static final String appSecret = "b1d672121ecba2fdc23d13a590dad248";

    // 正式环境
//    private static final String url = "https://japi.zto.com/zto.open.getRouteInfo";
//    private static final String appKey = "b3285c71bcd18644d7283";
//    private static final String appSecret = "feba0af8b55b916702c19d8fb9e88e2b";


    public ZtoSDKServiceImpl(ZtoMaptrackQueryRequestFactory thirdQueryDTOFactory) {
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
     * @param thirdMaptrackQueryRequest@return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Map<String, Object> getReqBody(ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public ThirdMaptrackQueryResponse query(Map<String, Object> headers, ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) {
        if (!(thirdMaptrackQueryRequest instanceof ZtoMaptrackQueryRequest)) {
            log.warn("入参不是ZtoMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是ZtoMaptrackQueryRequest");
        }
        String reqStr = JSON.toJSONString(thirdMaptrackQueryRequest);
        ZopClient client = new ZopClient(appKey, appSecret);
        ZopPublicRequest request = new ZopPublicRequest();
        request.setBody(reqStr);
        request.setUrl(url);
        log.info("中通查询，请求：{}, {}", url, reqStr);
        String respStr;
        try {
            respStr = client.execute(request);
        } catch (IOException e) {
            log.error("中通查询，网络异常", e);
            throw new NetworkException("中通查询，网络异常:" + e.getMessage());
        }
        log.info("中通查询，响应：{}", respStr);
        ZtoMaptrackQueryResponse response = null;
        try {
            response = JSON.parseObject(respStr, ZtoMaptrackQueryResponse.class);
        } catch (Exception e) {
            log.error("中通查询，响应报文解析异常", e);
            throw new BusinessException("ZtoSDKServiceImpl", "中通查询，响应报文解析异常", e);
        }
        // 通信结果
        response.setCommuniecationResult(this.parseCommunicateResult(response, respStr));
        if (response.isCommunicateSuccess()) {
            // 通信成功才判断业务结果
            response.setBusinessResult(this.parseBusinessResult(response));
        }
        return response;
    }

    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     *
     * @param scanType
     * @return
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String scanType) {
        if (null == scanType) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (scanType) {
            case "收件":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "发件": ;
            case "到件": ;
            case "派件": ;
            case "ARRIVAL":  //派件入三方自提柜
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "问题件":
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "签收": ;
            case "SIGNED":  //派件出三方自提柜
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
     * @param ztoMaptrackQueryResponse
     * @param responseStr
     */
    @Override
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(ZtoMaptrackQueryResponse ztoMaptrackQueryResponse, String responseStr) {
        // 中通响应报文里只有业务码，没通信码，SDK无抛异常则默认通信成功
        // 通信成功
        return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, "", responseStr);
    }

    /**
     * 解析并获取第三方业务结果
     * 返回标准的业务结果出参
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(ZtoMaptrackQueryResponse ztoMaptrackQueryResponse) {
        log.debug("中通查询 开始检查是否业务成功, {}", ztoMaptrackQueryResponse.isSuccess());
        if (ztoMaptrackQueryResponse.isSuccess()) {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, ztoMaptrackQueryResponse.getStatusCode(), ztoMaptrackQueryResponse.getMessage());
            if (CollectionUtils.isEmpty(ztoMaptrackQueryResponse.getResult())) {
                // 无轨迹
                log.warn("中通查询 无轨迹结果");
                return businessResult;
            } else {
                // 降序排序
                ztoMaptrackQueryResponse.sortByTimeDesc();
            }
            List<ZtoMaptrackQueryResponse.BillTrackOutput> descSortedResult = ztoMaptrackQueryResponse.getResult();
            ztoMaptrackQueryResponse.setResult(descSortedResult);
            // 【重点1】设置物流当前的运输状态
            businessResult.setThirdStateCode(descSortedResult.get(0).getScanType());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(descSortedResult
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        } else {
            // 业务失败
            return new ThirdMaptrackQueryResponse.BusinessResult(false, ztoMaptrackQueryResponse.getStatusCode(), ztoMaptrackQueryResponse.getMessage());
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(ZtoMaptrackQueryResponse.BillTrackOutput billTrackOutput) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(billTrackOutput.getScanType() + "/" + billTrackOutput.getDesc());
        thirdTrackDataDTO.setTime(billTrackOutput.getScanDate());
        thirdTrackDataDTO.setFtime(billTrackOutput.getScanDate());
        return thirdTrackDataDTO;
    }
}
