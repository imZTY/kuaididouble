package com.zty.kdd.yuantong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zty.kdd.yuantong.response.MaptrackResponse;
import com.zty.kdd.yuantong.util.YuantongApiUtil;
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
public class YuantongTest {

    private final static Logger logger = LoggerFactory.getLogger(YuantongTest.class);

    // 生产数据
//    private String SECRET_KEY = "gejTtu";  //客户秘钥
//    private String APP_KEY = "feLOZ1";  //客户编码

    // 测试数据
    private String SECRET_KEY = "1QLlIZ";  //客户秘钥
    private String APP_KEY = "YTOTEST";  //客户编码

    private String USER_ID = "1i5aCM";

    private String FORMAT = "JSON";

    private String version = "v1";

    private String url = "https://openuat.yto56.com.cn:6443/open/track_query_adapter/v1/1i5aCM/TEST";

    /**
     * 文档地址：https://open.yto.net.cn/interfaceDocument/menu251/submenu258
     * 响应示例：
     * {"code":"1001","map":{"YT2600227881409":[]},"message":"查询结果为空。","success":true}
     */
    @Test
    public void queryLocationTest(){
        // 获取参数
        String json = "{\"timestamp\":\"1630056973033\",\"param\":\"{\\\"NUMBER\\\":\\\"YT2600227881409\\\"}\",\"sign\":\"uhSL6hg8txxadZi26YPYPg==\",\"format\":\"JSON\"}";
        Map params = JSONObject.parseObject(json).getInnerMap();

        // 生成签名
        // String sign = encryptSignForOpen(params);
        // 请求参数拼装
        // 获取结果
        MaptrackResponse resp = YuantongApiUtil.mapTrackQuery(json);
        logger.info("{}", JSON.toJSONString(resp));
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
}
