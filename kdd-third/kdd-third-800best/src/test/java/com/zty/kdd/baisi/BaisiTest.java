package com.zty.kdd.baisi;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Client;
import com.best.javaSdk.kdTraceQuery.request.KdTraceQueryReq;
import com.best.javaSdk.kdTraceQuery.request.MailNos;
import com.best.javaSdk.kdTraceQuery.response.KdTraceQueryRsp;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tianyi
 * @date 2021-03-21 22:58
 */
public class BaisiTest {

    private static final Logger log = LoggerFactory.getLogger(BaisiTest.class);

    private static final String KD_TRACE_QUERY = "http://kdtest.800best.com/kd/api/process";

    private static final String QUERY_BODY = "{\n" +
            "    \"mailNos\": {\n" +
            "        \"mailNo\": [\n" +
            "            \"210323413836\"\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    @Test
    public void qurey() {
        String url = "http://edi-q9.ns.800best.com/kd/api/process"; //API调用地址

        String partnerID = "67688";       //客户ID

        String partnerKey = "7qdhnniah2oo";          //客户密钥

        String format = "JSON";                    //业务数据格式。
        Client client = new Client(url,  partnerID, partnerKey, format);

        //创建请求对象
        KdTraceQueryReq kdTraceQueryReq = new KdTraceQueryReq();
        MailNos mailNos = new MailNos();
        List<String> mailNo = new ArrayList<>();
        mailNo.add("550011398745680");
        mailNos.setMailNo(mailNo);
        kdTraceQueryReq.setMailNos(mailNos);

        //kdTraceQueryRsp即为百世的响应
        KdTraceQueryRsp kdTraceQueryRsp = client.executed(kdTraceQueryReq);
        log.info(JSON.toJSONString(kdTraceQueryRsp));
    }
}
