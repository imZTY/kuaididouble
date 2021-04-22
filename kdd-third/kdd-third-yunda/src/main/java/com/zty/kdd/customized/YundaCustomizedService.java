package com.zty.kdd.customized;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yundasys.api.DefaultOpenapiClient;
import com.yundasys.api.OpenapiConstants;
import com.yundasys.api.OpenapiException;
import com.yundasys.api.request.OpenApiDefaultRequest;
import com.yundasys.api.response.OpenApiDefaultResponse;
import org.springframework.stereotype.Service;

import com.zty.kuaididouble.third.AbstractCustomizedService;

/**
 * @author tianyi
 * @date 2021-02-28 23:43
 */
@Service("Yunda_Customized")
public class YundaCustomizedService extends AbstractCustomizedService {
    
    public void queryLocationTest() throws OpenapiException {
        //接口地址 测试地址
        final String serverUrl = "https://devkyweixin.yundasys.com/openapi/outer/logictis/query";
        //创建应用分配的appkey
        final String appKey = "999999";
        //创建应用分配的appsecret
        final String appSecret = "04d4ad40eeec11e9bad2d962f53dda9d";

        DefaultOpenapiClient defaultOpenapiClient = new DefaultOpenapiClient(serverUrl, appKey, appSecret);

        OpenApiDefaultRequest openApiDefaultRequest = new OpenApiDefaultRequest();
        //请求参数body, 指定JSON格式
        String sourceContent ="{\n" +
                "    \"mailno\":\"340987657890876\"\n" +
                "}";
        openApiDefaultRequest.setBizContent(sourceContent);
        //生成的SIGN签名串
        String sign = defaultOpenapiClient.getSigner().sign(sourceContent, OpenapiConstants.SIGN_TYPE_MD5, OpenapiConstants.CHARSET_UTF8);
        openApiDefaultRequest.setHeadersMap(appKey, sign, String.valueOf(System.currentTimeMillis()));

        OpenApiDefaultResponse openApiDefaultResponse = defaultOpenapiClient.excute(openApiDefaultRequest);
        if(openApiDefaultResponse.isSuccess()){
            System.out.println("调用成功");
        }else{
            System.out.println("调用失败");
        }
        System.out.println(openApiDefaultResponse.toString());
    }

    @Override
    public String getUrl() {
        // TODO: 2021/3/2 从配置文件读取
        return "https://devkyweixin.yundasys.com/openapi/outer/logictis/query";
    }

    @Override
    public Object getHeaders(Map<String, Object> headers) {
        return null;
    }

    @Override
    public Object getReqBody(Map<String, Object> params) {
        return new JSONObject(params).toJSONString();
    }

    @Override
    public String query(Map<String, Object> headers, Map<String, Object> params) {
        //接口地址 测试地址
        final String serverUrl = getUrl();
        //创建应用分配的appkey
        final String appKey = "999999";
        //创建应用分配的appsecret
        final String appSecret = "04d4ad40eeec11e9bad2d962f53dda9d";

        DefaultOpenapiClient defaultOpenapiClient = new DefaultOpenapiClient(serverUrl, appKey, appSecret);

        OpenApiDefaultRequest openApiDefaultRequest = new OpenApiDefaultRequest();
        //请求参数body, 指定JSON格式
        String sourceContent = (String) getReqBody(params);
        openApiDefaultRequest.setBizContent(sourceContent);
        //生成的SIGN签名串
        String sign = defaultOpenapiClient.getSigner().sign(sourceContent, OpenapiConstants.SIGN_TYPE_MD5, OpenapiConstants.CHARSET_UTF8);
        openApiDefaultRequest.setHeadersMap(appKey, sign, String.valueOf(System.currentTimeMillis()));

        OpenApiDefaultResponse openApiDefaultResponse = null;
        try {
            openApiDefaultResponse = defaultOpenapiClient.excute(openApiDefaultRequest);
        } catch (OpenapiException e) {
            e.printStackTrace();
        }
        if(openApiDefaultResponse.isSuccess()){
            System.out.println("调用成功");
        }else{
            System.out.println("调用失败");
        }
        return openApiDefaultResponse.toString();
    }
}
