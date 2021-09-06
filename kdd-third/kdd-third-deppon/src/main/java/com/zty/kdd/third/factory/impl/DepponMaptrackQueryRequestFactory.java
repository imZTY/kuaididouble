package com.zty.kdd.third.factory.impl;

import com.alibaba.fastjson.JSON;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.DepponMaptrackQueryRequest;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DepponMaptrackQueryRequestFactory extends ThirdMaptrackQueryRequestFactory {

    private final static Logger log = LoggerFactory.getLogger(DepponMaptrackQueryRequestFactory.class);

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     *
     * @param apiRequest
     */
    @Override
    public DepponMaptrackQueryRequest parseThirdQueryRequest(QueryParamDTO apiRequest) {
        DepponMaptrackQueryRequest request = new DepponMaptrackQueryRequest();
        if (StringUtils.isBlank(apiRequest.getNum())) {
            log.warn("运单号不可为空: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("运单号不可为空");
        }
        request.setMailNo(apiRequest.getNum());
        return request;
    }
}
