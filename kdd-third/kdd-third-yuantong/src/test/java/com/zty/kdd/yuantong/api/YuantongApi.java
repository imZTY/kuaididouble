package com.zty.kdd.yuantong.api;

import com.zty.kdd.yuantong.response.MaptrackResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: tianyi.zeng
 * @create: 27/8/2021 - 下午 5:49
 */
public interface YuantongApi {

    @POST("{version}/{userId}/TEST")
    @Headers({"Content-Type: application/json;"})
    public Call<MaptrackResponse> mapTrackQuery(@Body RequestBody jsonBody,
                                                @Path(value="version", encoded=true) String version,
                                                @Path(value="userId", encoded=true) String userId);
}
