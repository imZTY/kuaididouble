package com.zty.kdd.sf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * https://qiao.sf-express.com/pages/developDoc/index.html?level2=897588
 * @author tianyi
 * @date 2021-03-21 19:58
 */
public class SfTest {

    // 下单
    private static final String orderCode = "KMKJPh";
    private static final String orderSecret = "DGJiz0VoHeEKnSAuK98sPIwqTQX6xupI";
    // 路由查询
    private static final String queryCode = "KMKJPh";
    private static final String querySecret = "DGJiz0VoHeEKnSAuK98sPIwqTQX6xupI";

    private static final String queryUrl = "https://sfapi-sbox.sf-express.com/std/service";

    @Test
    public void order() {
        String str = "{\"success\":true,\"errorCode\":\"S0000\",\"errorMsg\":null,\"msgData\":{\"routeResps\":[{\"mailNo\":\"444003077898\",\"routes\":[{\"acceptAddress\":\"深圳市\",\"acceptTime\":\"2021-05-05 22:46:12\",\"remark\":\"顺丰速运 已收取快件\",\"opCode\":\"50\"}]},{\"mailNo\":\"441003077850\",\"routes\":[{\"acceptAddress\":\"深圳市\",\"acceptTime\":\"2021-05-05 22:46:12\",\"remark\":\"顺丰速运 已收取快件\",\"opCode\":\"50\"}]}]}}";
        System.out.println(str);
    }

    @Test
    public void query() {
        Map<String, String> reqBody = new HashMap<String, String>();
        reqBody.put("requestID", UUID.randomUUID().toString().replace("-", ""));
        reqBody.put("timestamp", String.valueOf(System.currentTimeMillis()));
        System.out.println(JSONObject.toJSONString(reqBody));
    }

}

