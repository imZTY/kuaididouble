package com.zty.kdd.kdniao.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.kdd.kdniao.api.KdniaoApi;
import com.zty.kdd.kdniao.api.StringConverterFactory;
import com.zty.kdd.kdniao.request.KdniaoMaptrackQueryRequest;
import com.zty.kdd.kdniao.response.KdniaoMaptrackQueryResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KdniaoApiUtil {

    private final static Logger logger = LoggerFactory.getLogger(KdniaoApiUtil.class);

    private final static KdniaoApi kdniaoApi;

    //请求url, 正式环境地址 https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx
    private static final String ReqURL = "https://api.kdniao.com/";

    static {
        // 创建 OkHttp 客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 修改连接超时时间
                .connectTimeout(Duration.ofSeconds(5))
                // 修改写超时时间
                .writeTimeout(Duration.ofSeconds(10))
                // 修改读超时时间
                .readTimeout(Duration.ofSeconds(15))
                // 设置日志打印拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        // 获取原请求
                        Request original = chain.request();
                        // 打印请求内容
                        logger.info("即将{}请求:{}, 请求头:{}",
                                original.method(),
                                original.url().toString(),
                                original.headers().toString());
                        // 继续执行原请求
                        return chain.proceed(original);
                    }
                })
                .build();
        // 创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Objects.requireNonNull(ReqURL))
                // 添加格式转换器
                .addConverterFactory(new StringConverterFactory())   //响应报文为空的场景，其他转换器会出现异常
                .addConverterFactory(GsonConverterFactory.create())  // 解析
                .build();
        kdniaoApi = retrofit.create(KdniaoApi.class);
    }

    public static KdniaoMaptrackQueryResponse mapTrackQuery(KdniaoMaptrackQueryRequest request) {
        // 请求数据
        logger.info("快递鸟请求: {}", JSON.toJSONString(request));
        Call<String> call = kdniaoApi.mapTrackQuery(request.getRequestData(),
                request.getEBusinessID(),
                request.getRequestType(),
                request.getDataSign(),
                request.getDataType());
        try {
            Response<String> response = call.execute();
            int statusCode = response.code();
            logger.info("快递鸟查询 响应code：{}", statusCode);
            if(statusCode == HttpStatus.SC_OK) {
                String responseStr = response.body();
                logger.info("快递鸟查询 响应内容：{}", responseStr);
                JSONValidator jsonValidator = JSONValidator.fromUtf8(responseStr.getBytes(StandardCharsets.UTF_8));
                if (jsonValidator.validate()) {
                    return JSON.parseObject(responseStr, KdniaoMaptrackQueryResponse.class);
                } else {
                    // 无法识别json字符串
                    logger.error("快递鸟查询 无法识别json字符串");
                    throw new BusinessException(statusCode+"", "无法识别响应json");
                }
            } else {
                logger.warn("快递鸟查询 响应内容：{}", response.body());
                throw new BusinessException(statusCode+"", "响应code非200");
            }
        } catch (IOException e) {
            logger.error("快递鸟查询 网络异常，", e);
            throw new NetworkException(" 网络异常" + e.getMessage());
        } catch (Exception e) {
            logger.error("快递鸟查询 未知异常，", e);
            throw new BusinessException("KdniaoApiUtil", "未知异常" + e.getMessage());
        }
    }
}
