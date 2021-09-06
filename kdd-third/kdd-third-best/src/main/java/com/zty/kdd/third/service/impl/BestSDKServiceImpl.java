package com.zty.kdd.third.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.best.javaSdk.Client;
import com.best.javaSdk.kdTraceQuery.request.KdTraceQueryReq;
import com.best.javaSdk.kdTraceQuery.response.KdTraceQueryRsp;
import com.best.javaSdk.kdTraceQuery.response.Problems;
import com.best.javaSdk.kdTraceQuery.response.Trace;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.impl.BestMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.BestMaptrackQueryRequest;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.response.BestMaptrackQueryResponse;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 31/8/2021 - 下午 4:30
 */
@Service("Best_SDK")
public class BestSDKServiceImpl extends AbstractMaptrackQuerySDKService<BestMaptrackQueryRequestFactory, BestMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(BestSDKServiceImpl.class);

    private static final String URL = "http://edi-q9.ns.800best.com/kd/api/process"; //API调用地址(生产)
//    private static final String URL = "http://kdtest.800best.com/kd/api/process"; //API调用地址(测试)

    private static final String PARTNER_ID = "67688";       //客户ID

    private static final String PARTNER_KEY = "7qdhnniah2oo";          //客户密钥

    private static final String FORMAT = "JSON";                    //业务数据格式。

    public BestSDKServiceImpl(BestMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     *
     * @return
     */
    @Override
    public String getUrl() {
        return URL;
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
        if (!(thirdMaptrackQueryRequest instanceof BestMaptrackQueryRequest)) {
            log.warn("入参不是BestMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是BestMaptrackQueryRequest");
        }
        String url = getUrl();
        KdTraceQueryReq request = ((BestMaptrackQueryRequest) thirdMaptrackQueryRequest).getKdTraceQueryReq();
        Client client = new Client(url, PARTNER_ID, PARTNER_KEY, FORMAT);
        log.info("百世查询，请求：{}, {}", url, JSON.toJSONString(request));
        KdTraceQueryRsp kdTraceQueryRsp = null;
        try {
            kdTraceQueryRsp = client.executed(request);
        } catch (Exception e) {
            if (e instanceof IOException) {
                log.error("百世查询，网络异常", e);
                throw new NetworkException("百世查询，网络异常:" + e.getMessage());
            } else {
                log.error("百世查询，未知异常", e);
                throw new BusinessException("BestSDKServiceImpl", "百世查询，未知异常:" + e.getMessage());
            }
        }
        String respStr = JSON.toJSONString(kdTraceQueryRsp);
        log.info("百世查询，响应：{}", respStr);
        BestMaptrackQueryResponse response = new BestMaptrackQueryResponse(kdTraceQueryRsp);
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
            case "转件": ;
            case "派件": ;
            case "入库/入柜":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "问题件": ;
            case "异常拦截件": ;
            case "查无此人": ;
            case "节假日无人收货": ;
            case "快件遗失": ;
            case "大笔错写": ;
            case "地址不详": ;
            case "更改派送地址": ;
            case "客户要求延迟派送": ;
            case "联系不上收件人": ;
            case "客户拒收": ;
            case "站点拒收": ;
            case "快递员取出包裹": ;
            case "货物破损": ;
            case "管理员取出包裹": ;
            case "快件破损":
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "签收": ;
            case "用户提货": ;
            case "百世邻里": ;
            case "代理点签收": ;
            case "特殊签收": ;
            case "转同行":
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
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(BestMaptrackQueryResponse response, String responseStr) {
        // 百世响应报文里没有明确的通信码，默认SDK无抛异常则默认通信成功
        // 通信成功
        return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, response.getKdTraceQueryRsp().getRemark(), responseStr);
    }

    /**
     * 解析并获取第三方业务结果
     *
     * @param response
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(BestMaptrackQueryResponse response) {
        log.debug("百世查询 开始检查是否业务成功");
        KdTraceQueryRsp kdTraceQueryRsp = response.getKdTraceQueryRsp();
        if (kdTraceQueryRsp.getResult()) {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, kdTraceQueryRsp.getErrorCode(), kdTraceQueryRsp.getErrorDescription());
            if (CollectionUtils.isEmpty(kdTraceQueryRsp.getTraceLogs())
                    || CollectionUtils.isEmpty(kdTraceQueryRsp.getTraceLogs().get(0).getTraces().getTrace())) {
                // 轨迹不存在
                log.warn("百世查询 无轨迹结果");
                return businessResult;
            } else {
                Problems problems = kdTraceQueryRsp.getTraceLogs().get(0).getProblems();
                // 【重点1】设置物流当前的运输状态
                if (problems != null) {
                    // 检查是否是问题件
                    businessResult.setThirdStateCode(problems.getProblem().get(0).getProblemType());
                } else {
                    // 逆序排序
                    kdTraceQueryRsp.getTraceLogs().get(0).getTraces().sortByTimeDesc();
                    // 获取最新的scanType
                    businessResult.setThirdStateCode(kdTraceQueryRsp.getTraceLogs().get(0).getTraces().getTrace().get(0).getScanType());
                }
                // 【重点2】设置物流轨迹历史
                businessResult.setThirdTrackDataList(kdTraceQueryRsp.getTraceLogs().get(0).getTraces().getTrace()
                        .stream()
                        .map(this::parseThirdTrackData)
                        .collect(Collectors.toList()));
            }
            return businessResult;
        } else {
            // 业务失败
            log.warn("百世查询, 业务失败, {}, {}", kdTraceQueryRsp.getErrorCode(), kdTraceQueryRsp.getErrorDescription());
            return new ThirdMaptrackQueryResponse.BusinessResult(false, kdTraceQueryRsp.getErrorCode(), kdTraceQueryRsp.getErrorDescription());
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(Trace trace) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(trace.getScanType() + "/" + trace.getRemark());
        thirdTrackDataDTO.setTime(trace.getAcceptTime());
        thirdTrackDataDTO.setFtime(trace.getLongAcceptTime());
        return thirdTrackDataDTO;
    }
}
