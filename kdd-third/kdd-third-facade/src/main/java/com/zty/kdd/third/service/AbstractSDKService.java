package com.zty.kdd.third.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.zty.kdd.third.dto.ThirdQueryDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;

/**
 * 自定义，抽象服务类
 * @author tianyi
 * @date 2021-03-01 00:59
 */
public abstract class AbstractSDKService {

    public abstract String getUrl();

    public abstract Object getHeaders(Map<String, Object> headers);

    /**
     * 转换时，注意Object后续的用法
     * @param thirdQueryDTO
     * @return
     * @throws UnsupportedEncodingException
     */
    public abstract Map<String, Object> getReqBody(ThirdQueryDTO thirdQueryDTO) throws UnsupportedEncodingException;

    public abstract String query(Map<String, Object> headers,
            Map<String, Object> params);

    public abstract ThirdTransStatusEnum parseThirdCodeToEnum(String transCode);
}
