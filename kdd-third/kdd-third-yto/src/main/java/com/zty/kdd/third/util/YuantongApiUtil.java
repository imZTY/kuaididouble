package com.zty.kdd.third.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONValidator;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.kdd.third.api.StringConverterFactory;
import com.zty.kdd.third.api.YuantongApi;
import com.zty.kdd.third.request.YtoMaptrackQueryRequest;
import com.zty.kdd.third.response.ErrorMaptrackResponse;
import com.zty.kdd.third.response.SuccessMaptrackResponse;
import com.zty.kdd.third.response.YtoMaptrackQueryResponse;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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

    // 生产数据
    private final static String SECRET_KEY = "KQ4uePP51c";  //客户秘钥
    private final static String APP_KEY = "open15342827";  //客户编码

    private final static String VERSION = "v1";

    private final static String USER_ID = "1i5aCM";

    private final static String METHOD = "track_query_adapter";

//    private final static String JENKINS_URI = "https://openuat.yto56.com.cn:6443/open/track_query_adapter/";//测试
    private final static String YTO_URL = "https://openapi.yto.net.cn:11443/open/track_query_adapter/";//生产


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
                .baseUrl(Objects.requireNonNull(YTO_URL))
                // 添加格式转换器
                .addConverterFactory(new StringConverterFactory())   //响应报文为空的场景，其他转换器会出现异常
                .addConverterFactory(GsonConverterFactory.create())  // 解析
                .build();
        yuantongApi = retrofit.create(YuantongApi.class);
    }

    public static YtoMaptrackQueryResponse mapTrackQuery(YtoMaptrackQueryRequest request) {
        // 生成签名
        String data = request.getParam() + METHOD + VERSION;
        request.setSign(encryptSignForOpen(data, SECRET_KEY));
        String requestStr = JSON.toJSONString(request);
        logger.info("圆通请求: {}", requestStr);
        Call<String> call = yuantongApi.mapTrackQuery(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr),
                VERSION, USER_ID, APP_KEY);
        try {
            Response<String> response = call.execute();
            int statusCode = response.code();
            logger.info("圆通查询 响应code：{}", statusCode);
            if(statusCode == HttpStatus.SC_OK) {
                String responseStr = response.body();
                logger.info("圆通查询 响应内容：{}", responseStr);
                JSONValidator jsonValidator = JSONValidator.fromUtf8(responseStr.getBytes(StandardCharsets.UTF_8));
                // 根据报文结构判断是否为成功响应
                if (jsonValidator.validate()) {
                    switch (jsonValidator.getType()) {
                        case Array:
                            // 成功响应
                            List<SuccessMaptrackResponse.Trace> traces = JSONArray.parseArray(responseStr, SuccessMaptrackResponse.Trace.class);
                            return new YtoMaptrackQueryResponse(new SuccessMaptrackResponse(traces));
                        case Object:
                            // 失败响应
                            return new YtoMaptrackQueryResponse(JSON.parseObject(responseStr, ErrorMaptrackResponse.class));
                        default:
                            // 不支持Value类型json字符串
                            logger.error("圆通查询 不支持Value类型json字符串");
                            throw new BusinessException(statusCode+"", "不支持Value类型json字符串");
                    }
                } else {
                    // 无法识别json字符串
                    logger.error("圆通查询 无法识别json字符串");
                    throw new BusinessException(statusCode+"", "无法识别json字符串");
                }
            } else {
                logger.error("圆通查询 响应内容：{}", response.body());
                throw new BusinessException(statusCode+"", "响应code非200");
            }
        } catch (IOException e) {
            logger.error("圆通查询 网络异常，", e);
            throw new NetworkException(" 网络异常" + e.getMessage());
        } catch (Exception e) {
            logger.error("圆通查询 未知异常，", e);
            throw new BusinessException("YuantongApiUtil", "未知异常" + e.getMessage());
        }
    }

    /**
     * Base64( MD5( data + secret ) )
     * @param data 请求字段字符串param + 接口方法字符串 + 版本v1
     * @param secret 客户秘钥
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
