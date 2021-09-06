package com.zty.kdd.third.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;

/**
 * 自定义，抽象服务类
 * @author tianyi
 * @date 2021-03-01 00:59
 */
public abstract class AbstractMaptrackQuerySDKService<F extends ThirdMaptrackQueryRequestFactory, R extends ThirdMaptrackQueryResponse> {

    private F thirdQueryDTOFactory;

    public AbstractMaptrackQuerySDKService(F thirdQueryDTOFactory) {
        this.thirdQueryDTOFactory = thirdQueryDTOFactory;
    }

    /**
     * 将通用入参转换成专门的入参
     * @param queryParamDTO
     * @return
     */
    public ThirdMaptrackQueryRequest getThirdQueryRequest(QueryParamDTO queryParamDTO) {
        return this.thirdQueryDTOFactory.parseThirdQueryRequest(queryParamDTO);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     * @return
     */
    public abstract String getUrl();

    public abstract Map<String, Object> getHeaders(QueryParamDTO thirdQueryDTO);

    /**
     * 获取请求body
     * 转换时，注意Object后续的用法
     * @param thirdMaptrackQueryRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    public abstract Map<String, Object> getReqBody(ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) throws UnsupportedEncodingException;

    public abstract ThirdMaptrackQueryResponse query(Map<String, Object> headers,
                                                     ThirdMaptrackQueryRequest thirdMaptrackQueryRequest);

    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     * @param transCode
     * @return
     */
    public abstract ThirdTransStatusEnum parseThirdCodeToEnum(String transCode);

    /**
     * 解析并获取第三方通信结果
     */
    protected abstract ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(R response, String responseStr);

    /**
     * 解析并获取第三方业务结果
     */
    protected abstract ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(R response);
}
