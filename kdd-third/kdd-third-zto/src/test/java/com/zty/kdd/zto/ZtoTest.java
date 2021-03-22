package com.zty.kdd.zto;

import java.io.IOException;

import com.zto.zop.ZopClient;
import com.zto.zop.ZopPublicRequest;
import org.junit.Test;

/**
 * https://open.zto.com/#/interface?resourceGroup=10&apiName=zto.open.residualTime
 * @author tianyi
 * @date 2021-03-21 19:44
 */
public class ZtoTest {

    // 沙箱环境
    private static final String appKey = "a926de8a8a726544c5131";
    private static final String appSecret = "b1d672121ecba2fdc23d13a590dad248";

    // 正式环境
//    private static final String appKey = "b3285c71bcd18644d7283";
//    private static final String appSecret = "feba0af8b55b916702c19d8fb9e88e2b";

    @Test
    public void order() {

    }

    @Test
    public void query() throws IOException {
        ZopClient client = new ZopClient(appKey, appSecret);

        ZopPublicRequest request = new ZopPublicRequest();

        request.setBody("{\"billCode\":\"73111435816656\"}");

        request.setUrl("https://japi-test.zto.com/zto.open.residualTime");

        System.out.println(client.execute(request));
    }
}
