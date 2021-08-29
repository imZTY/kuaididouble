package com.zty.kuaididouble.yunda;

import com.yundasys.api.DefaultOpenapiClient;
import com.yundasys.api.OpenapiConstants;
import com.yundasys.api.OpenapiException;
import com.yundasys.api.request.OpenApiDefaultRequest;
import com.yundasys.api.response.OpenApiDefaultResponse;
import org.junit.Test;

/**
 * @author tianyi
 * @date 2021-01-24 11:18
 */
public class YundaTest {

    // TODO: 27/8/2021 需沟通，开启无需订阅的接口权限
    @Test
    public void queryLocationTest() throws OpenapiException {
        //接口地址 测试地址
        final String serverUrl = "https://u-openapi.yundasys.com/openapi/outer/logictis/query";
        // 文档数据
//        final String appKey = "999999";
//        final String appSecret = "04d4ad40eeec11e9bad2d962f53dda9d";
        // 测试环境
        final String appKey = "000710";
        final String appSecret = "7c019621a887f9635c661f77581adb16";

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

}
