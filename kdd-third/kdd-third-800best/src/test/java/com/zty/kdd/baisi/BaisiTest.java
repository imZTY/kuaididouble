package com.zty.kdd.baisi;

import java.util.ArrayList;
import java.util.List;

import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Client;
import com.best.javaSdk.kdTraceQuery.request.KdTraceQueryReq;
import com.best.javaSdk.kdTraceQuery.request.MailNos;
import org.junit.Test;

/**
 * @author tianyi
 * @date 2021-03-21 22:58
 */
public class BaisiTest {

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
        String url = "http://openapi.800best.com/api-server/kd/api/process"; //API调用地址

        String partnerID = "4565";       //客户ID

        String partnerKey = "306D3US9";          //客户密钥

        String format = "JSON";                    //业务数据格式。
        Client client = new Client(url, partnerID, partnerKey, format);     //构造client

        //创建请求对象
        KdTraceQueryReq kdTraceQueryReq = new KdTraceQueryReq();
        MailNos mailNos = new MailNos();
        List<String> mailNo = new ArrayList<>();
        mailNo.add("50004090806944");
        mailNos.setMailNo(mailNo);
        kdTraceQueryReq.setMailNos(mailNos);

        // 得到响应
        BaseResponse rsp = client.executed(kdTraceQueryReq);
    }
}
