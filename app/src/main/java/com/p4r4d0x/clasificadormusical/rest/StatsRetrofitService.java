package com.p4r4d0x.clasificadormusical.rest;

import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StatsRetrofitService {

    @GET("/classifier/stats/{userID}")
    Call<SResponse> userStats(@Path("userID") Long userId);


}
