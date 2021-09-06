package com.zty.kdd.yuantong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zty.kdd.third.request.YtoMaptrackQueryRequest;
import com.zty.kdd.third.response.ErrorMaptrackResponse;
import com.zty.kdd.third.response.YtoMaptrackQueryResponse;
import com.zty.kdd.third.util.YuantongApiUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author tianyi
 * @date 2021-01-24 14:29
 */
public class YtoTest {

    private final static Logger logger = LoggerFactory.getLogger(YtoTest.class);

    // 生产数据
//    private String SECRET_KEY = "KQ4uePP51c";  //客户秘钥
//    private String APP_KEY = "open15342827";  //客户编码

    // 测试数据
    private String SECRET_KEY = "123456";  //客户秘钥
    private String APP_KEY = "YTOTEST";  //客户编码

    private String USER_ID = "1i5aCM";

    private String FORMAT = "JSON";

    private String version = "v1";

    private String url = "https://openuat.yto56.com.cn:6443/open/track_query_adapter/v1/1i5aCM/TEST";

    private String proURL = "http://openapi.yto.net.cn/service/waybill_query/v1/1i5aCM";

    /**
     * 文档地址：https://open.yto.net.cn/interfaceDocument/menu251/submenu258
     * 响应示例：
     * 失败: {"code":"1001","map":{"YT2600227881409":[]},"message":"查询结果为空。","success":true}
     * 成功: [{
     * 	"waybill_No": "YT9093718083088",
     * 	"upload_Time": "2021-06-19 17:19:42",
     * 	"infoContent": "GOT",
     * 	"processInfo": "【浙江省金华市义乌市北苑春晗公司】 已揽收 取件人: 张**)"
     * }, {
     * 	"waybill_No": "YT9093718083088",
     * 	"upload_Time": "2021-06-19 17:48:10",
     * 	"infoContent": "ARRIVAL",
     * 	"processInfo": "【义乌转运中心直营公司】 已收入"
     * }]
     */
    @Test
    public void queryLocationTest() {
        // 获取参数
        String json = "{\"timestamp\":\"1630056973033\",\"param\":\"{\\\"NUMBER\\\":\\\"YT2600227881409\\\"}\",\"sign\":\"NEHxyy/nMJz8K1OEYlYmnQ==\",\"format\":\"JSON\"}";
//        String json = "{\"timestamp\":\"1630056973033\",\"param\":\"{\\\"NUMBER\\\":\\\"YT2600227881409\\\"}\",\"sign\":\"A5VvsQJFjUk8wEHg6wxDsQ==\",\"format\":\"JSON\"}";
        Map params = JSONObject.parseObject(json).getInnerMap();

        // 生成签名
        // String sign = encryptSignForOpen(params);
        // 请求参数拼装
        // 获取结果
        logger.info("{}", json);
        YtoMaptrackQueryResponse resp = YuantongApiUtil.mapTrackQuery(JSON.parseObject(json, YtoMaptrackQueryRequest.class));
        logger.info("是否成功响应: {}", resp.isSuccess());
    }

    /**
     * 开放平台公共加密方法-使用commons-codec-1.11.jar进行md5加密,然后对数组进行base64编码
     * @param data = param+method+v
     * @param secret
     * @return
     */
    public static String encryptSignForOpen(String data, String secret) {
        String sign;
        try {
            byte[] signByte = DigestUtils.md5(data + secret);
            sign = Base64.encodeBase64String(signByte);
        } catch (Throwable e) {
            logger.error("加密失败.e:{}.", e.toString());
            sign = "ERROR";
        }
        return sign;
    }

    @Test
    public void signTest() {
        String data = "{\"NUMBER\":\"YT2600227881409\"}" + "track_query_adapter" + version;
        String secret = "KQ4uePP51c";
        logger.info(encryptSignForOpen(data, secret));
    }
}
