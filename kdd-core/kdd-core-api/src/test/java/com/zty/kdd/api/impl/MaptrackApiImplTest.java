package com.zty.kdd.api.impl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.customized.YundaCustomizedService;
import com.zty.kuaididouble.third.AbstractCustomizedService;

/**
 * @author tianyi
 * @date 2021-03-02 01:35
 */
public class MaptrackApiImplTest {

    @Test
    public void query() {
        AbstractCustomizedService thirdService = new YundaCustomizedService();
        String orderNo = "340987657890876";
        MaptrackQueryReqAO.QueryParam param = new MaptrackQueryReqAO.QueryParam();
        param.setOrder(orderNo);
        String result = thirdService.query(null, JSONObject.parseObject("{\n" +
                "    \"mailno\":\"" + param.getOrder() + "\"\n" +
                "}"));
        System.out.println(result);
    }

}