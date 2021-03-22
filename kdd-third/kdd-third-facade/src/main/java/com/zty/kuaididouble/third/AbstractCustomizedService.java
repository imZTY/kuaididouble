package com.zty.kuaididouble.third;

import java.util.Map;

/**
 * 自定义，抽象服务类
 * @author tianyi
 * @date 2021-03-01 00:59
 */
public abstract class AbstractCustomizedService {

    public abstract String getUrl();

    public abstract Object getHeaders(Map<String, Object> headers);

    public abstract Object getReqBody(Map<String, Object> params);



    public abstract String query(Map<String, Object> headers,
            Map<String, Object> params);
}
