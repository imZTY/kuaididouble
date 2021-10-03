package com.zty.kdd.kdniao.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author: tianyi.zeng
 * @create: 14/9/2021 - 下午 6:19
 */
public interface KdniaoApi {

    @FormUrlEncoded
    @POST("Ebusiness/EbusinessOrderHandle.aspx")
    @Headers({
            "accept: */*",
            "Content-Type: application/x-www-form-urlencoded",
            "connection: Keep-Alive",
            "user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"
    })
    public Call<String> mapTrackQuery(@Field("RequestData") String RequestData,
                                      @Field("EBusinessID") String EBusinessID,
                                      @Field("RequestType") String RequestType,
                                      @Field("DataSign") String DataSign,
                                      @Field("DataType") String DataType);
}
