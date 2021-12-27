package com.zty.kdd.deppon;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.result.ResultDO;
import com.deppon.dop.module.sdk.shared.util.HttpUtils;
import com.deppon.dop.module.sdk.shared.util.SecurityUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 德邦轨迹查询
 */
public class DepponTest {

    private final static Logger log = LoggerFactory.getLogger(DepponTest.class);

    // 测试环境
    /**
     * 客户编码 88888
     * sign  YYYF
     * companyCode  EWBHZZMKJYXGS
     * appKey  51edd8c7a2357ffc0b8d0772842b42c4
     */
//    private String URL = "http://dpsanbox.deppon.com/sandbox-web/standard-order/newTraceQuery.action";  // 测试
    private String URL = "http://dpapi.deppon.com/dop-interface-sync/standard-query/newTraceQuery.action";  // 生产
    private String CUSTOMER_CODE = "88888";
    private String SIGN = "YYYF";
    private String COMPANY_CODE = "EWBHZZMKJYXGS";

    private String APP_KEY = "51edd8c7a2357ffc0b8d0772842b42c4";

    @Test
    public void mapTrackQuery() {
        String params = "{\n" +
                "    \"mailNo\":\"6291370455\"\n" +
                "}";
        //companyCode与appkey为双方约定
        //时间戳 SDK提供SecurityUtil获取时间戳
        String timestamp = System.currentTimeMillis() + "";
        //生成摘要
        String digest = Base64.encodeBase64String(DigestUtils.md5Hex(params + APP_KEY + timestamp)
                .getBytes());
        //post请求参数
        NameValuePair[] data = new NameValuePair[4];
        data[0] = new NameValuePair("companyCode", COMPANY_CODE);
        data[1] = new NameValuePair("digest", digest);
        data[2] = new NameValuePair("timestamp", timestamp);
        data[3] = new NameValuePair("params", params);
        ResultDO<String> response = null;
        //返回结果
        response = HttpUtils.sendRequest(URL, data, "UTF-8", 5000);
        log.info(JSON.toJSONString(response));
    }

    @Test
    public void foo() {
        System.out.println(System.nanoTime());
    }
}
