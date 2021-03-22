package com.zty.kdd.uce.sample;

import java.util.HashMap;
import java.util.Map;
import com.uc56.uop.client.exception.ClientException;
import com.uc56.uop.client.util.ReaderXmlUtil;
import com.uc56.uop.client.util.SecurityUtil;
import com.uc56.uop.client.util.HttpClientUtil;

/**
 * @author tianyi
 * @date 2021-03-22 00:09
 */
public class QueryTraceTest {
    //发送地址
//	public static final String SEND_URL = "http://localhost:8084/com.uc56.uop.main/gateway/gateway.action";
//	public static final String SEND_URL = "http://192.168.0.31/com.uc56.uop.main/gateway/gateway.action";
    public static final String SEND_URL = "http://uop.uc56.com/gateway/gateway.action";
//    public static final String SEND_URL = "http://uop.sit.uc56.com/uce-uop-main/gateway/gateway.action";
    //字符集
    public static final String CHARSET = "GBK";

    //签名方式
    public static final String SIGN_TYPE = "md5";

    //数据格式
    public static final String DATA_TYPE = "json";

    //密钥
    public static final String SECURITY_KEY = "5638725980faa155669d52616064f7f1";
    //合作伙伴编号
    public static final String PARTNER = "80448440";

    //调用的服务(映射的方法名)
    private static final String SERVER_NAME = "query_trace";

    public static Map<String, String> sendParam() throws ClientException{
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("partner", PARTNER);
            param.put("charset", CHARSET);
            param.put("dataType", DATA_TYPE);
            param.put("serviceName", SERVER_NAME);
            param.put("data", getData());
            param.put("dataSign", SecurityUtil.sign(param, SIGN_TYPE, SECURITY_KEY,
                    CHARSET));
            return param;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientException("生成发送的数据失败");
        }
    }

    private static String getData(){
        return ReaderXmlUtil.readXml(DATA_TYPE);
    }

    public static void main(String[] args) {

        try {
            String resultMsg = HttpClientUtil.sendHttpMessage(SEND_URL,sendParam());
            System.out.println(resultMsg);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
