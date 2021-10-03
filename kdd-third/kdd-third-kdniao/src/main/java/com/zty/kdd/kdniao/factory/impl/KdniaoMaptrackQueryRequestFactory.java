package com.zty.kdd.kdniao.factory.impl;

import com.alibaba.fastjson.JSON;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.kdniao.request.KdniaoMaptrackQueryRequest;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.factory.ThirdMaptrackQueryRequestFactory;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: tianyi.zeng
 * @create: 15/9/2021 - 上午 10:36
 */
@Component
public class KdniaoMaptrackQueryRequestFactory extends ThirdMaptrackQueryRequestFactory {

    private final static Logger log = LoggerFactory.getLogger(KdniaoMaptrackQueryRequestFactory.class);

    /**
     * 每家物流公司，都要自己实现前端入参的解析
     *
     * @param apiRequest
     */
    @Override
    public KdniaoMaptrackQueryRequest parseThirdQueryRequest(QueryParamDTO apiRequest) {
        KdniaoMaptrackQueryRequest request = new KdniaoMaptrackQueryRequest();
        if (StringUtils.isBlank(apiRequest.getNum())) {
            log.warn("运单号不可为空: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("运单号不可为空");
        }
        if (StringUtils.isBlank(apiRequest.getCom())) {
            log.warn("公司编码不可为空: {}", JSON.toJSONString(apiRequest));
            throw new ParamCheckException("公司编码不可为空");
        }
        // 获取快递鸟的shipperCode（前面要设置到 com 字段）
        String shipperCode = apiRequest.getCom();
        request.setRequestData(JSON.toJSONString(new KdniaoMaptrackQueryRequest.RequestData(shipperCode, apiRequest.getNum())));
        return request;
    }
}
