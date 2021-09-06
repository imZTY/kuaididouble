package com.zty.kdd.uce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zty.kdd.third.response.UceMaptrackQueryResponse;
import org.junit.Test;

/**
 * https://api.uce.cn/doku.php
 * @author tianyi
 * @date 2021-03-21 20:46
 */
public class UceTest {

    @Test
    public void respStrFormat() {
        String respStr = "{\"response\":{\"traceList\":[{\"trace\":[{\"optType\":\"签收\",\"scanOrgCode\":\"UC017685\",\"billCode\":\"518270309160\",\"optTime\":\"2021-08-27 23:31:25\",\"operateSite\":\"金山二部\",\"contacts\":\"本人\",\"desc\":\"上海市】您的快件已签收，签收人：【本人签收】，如有疑问请电联业务员：【金山二部测试】【】\"}]}],\"isSuccess\":\"T\"}}";
        UceMaptrackQueryResponse jo = JSON.parseObject(respStr, UceMaptrackQueryResponse.class);
        System.out.println(jo);
    }
}
