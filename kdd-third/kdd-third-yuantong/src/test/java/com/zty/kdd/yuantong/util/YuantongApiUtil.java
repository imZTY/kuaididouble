package com.zty.kdd.yuantong.util;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.kdd.yuantong.api.StringConverterFactory;
import com.zty.kdd.yuantong.api.YuantongApi;
import com.zty.kdd.yuantong.response.MaptrackResponse;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: tianyi.zeng
 * @create: 27/8/2021 - 下午 5:51
 */
public class YuantongApiUtil {

    private final static Logger logger = LoggerFactory.getLogger(YuantongApiUtil.class);

    private final static YuantongApi yuantongApi;

    private final static String VERSION = "v1";

    private final static String USER_ID = "1i5aCM";

    private final static String JENKINS_URI = "https://openuat.yto56.com.cn:6443/open/track_query_adapter/";//测试
//    private final static String JENKINS_URI = "http://openapi.yto.net.cn/service/waybill_query/";//生产


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
                .baseUrl(Objects.requireNonNull(JENKINS_URI))
                // 添加格式转换器
                .addConverterFactory(new StringConverterFactory())   //响应报文为空的场景，其他转换器会出现异常
                .addConverterFactory(GsonConverterFactory.create())  // 解析
                .build();
        yuantongApi = retrofit.create(YuantongApi.class);
    }

    public static MaptrackResponse mapTrackQuery(String jsonBody) {
        Call<MaptrackResponse> call = yuantongApi.mapTrackQuery(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonBody),
                VERSION, USER_ID);
        try {
            Response<MaptrackResponse> response = call.execute();
            int statusCode = response.code();
            logger.info("响应code：{}", statusCode);
            if(statusCode == HttpStatus.SC_OK) {
                return response.body();
            } else {
                logger.error("响应内容：{}", response.body());
                throw new BusinessException(statusCode+"", "响应code非200");
            }
        } catch (IOException e) {
            logger.error("网络异常，", e);
            throw new NetworkException(" 网络异常" + e.getMessage());
        } catch (Exception e) {
            logger.error("未知异常，", e);
            throw new BusinessException("500", " 未知异常" + e.getMessage());
        }
    }
}
