package com.zty.kdd.third.api;

import com.zty.kdd.third.response.ErrorMaptrackResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author: tianyi.zeng
 * @create: 27/8/2021 - 下午 5:49
 */
public interface YuantongApi {

    @POST("{version}/{userId}/{APP_KEY}")
    @Headers({"Content-Type: application/json;"})
    public Call<String> mapTrackQuery(@Body RequestBody jsonBody,
                                                     @Path(value="version", encoded=true) String version,
                                                     @Path(value="userId", encoded=true) String userId,
                                                     @Path(value="APP_KEY", encoded=true) String APP_KEY);
}
