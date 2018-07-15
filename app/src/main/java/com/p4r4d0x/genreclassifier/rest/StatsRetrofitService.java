package com.p4r4d0x.genreclassifier.rest;

import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatsRetrofitService {

    @GET("dev/classifier/stats")
    Call<StatsResponse> userStats(@Query("userId") Long userId);

}
