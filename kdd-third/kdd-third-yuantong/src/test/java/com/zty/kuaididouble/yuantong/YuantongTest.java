package com.zty.kuaididouble.yuantong;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Map;

    /**
     * @author tianyi
     * @date 2021-01-24 14:29
     */
    public class YuantongTest {

    private String SECRET_KEY = "gejTtu";

    private String USER_ID = "1i5aCM";

    private String APP_KEY = "feLOZ1";

    private String FORMAT = "JSON";

    private String V = "1.01";

    private String url = "http://openapi.yto.net.cn/service/waybill_query/v1/1i5aCM";

    /**
     * 序号	步骤
     * 1	将user_id、app_key、format、method、timestamp、v这六个字段按字母排序，再将字段对应的值放在字段后面，得到字符串A：app_keyABCDEFformatXMLmethodyto.Marketing.TransportPricetimestamp2016-6-1 13:14:35user_idyto_userv1.01
     * 2	将密钥(secret_key)放在字符串A的最前面，得到字符串B：123456app_keyABCDEFformatXMLmethodyto.Marketing.TransportPricetimestamp2016-6-1 13:14:35user_idyto_userv1.01
     * 3	用32位MD5加密算法对字符串B进行加密，得到字符串C
     * 4	然后将字符串C中的小写字母转成大写字母，得到字符串D：B78455F451153FA1284B186D5C4E31DF
     * 5	最终要发送的数据就是(param需url编码) sign=B78455F451153FA1284B186D5C4E31DF&app_key=ABCDEF&format=XML&method=yto.Marketing.WaybillTrace&timestamp=2016-6-1 13:14:35&user_id=yto_user&v=1.01&param=<?xml version="1.0"?><ufinterface><Result><WaybillCode><Number>710024476256</Number></WaybillCode></Result></ufinterface>
     */
    @Test
    public void queryLocationTest(){
        // 获取参数
        String json = "{\n" +
                "  \"timestamp\": \"2021-01-04 15:17:24\",\n" +
                "  \"method\": \"yto.Marketing.WaybillTrace\",\n" +
                "  \"user_id\": \"YTOTEST\",\n" +
                "  \"format\": \"JSON\",\n" +
                "  \"app_key\": \"sF1Jzn\",\n" +
                "  \"v\": \"1.01\"" +
                "}";
        Map params = JSONObject.parseObject(json).getInnerMap();
        // 生成签名
        String sign = getSign(params);
        // 请求参数拼装
        // 获取结果
    }

    private String getSign(Map<String, Object> params) {
        // 入参排序
        // 秘钥加入
        // MD5加密，转大写
        // url编码
        return null;
    }
}
