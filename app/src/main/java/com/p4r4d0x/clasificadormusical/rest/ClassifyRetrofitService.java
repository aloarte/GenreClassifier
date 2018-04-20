package com.p4r4d0x.clasificadormusical.rest;

import com.p4r4d0x.clasificadormusical.rest.classify.CRequest;
import com.p4r4d0x.clasificadormusical.rest.classify.CResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClassifyRetrofitService {

    @POST("/classifier/clasifygenre/")
    Call<CResponse> classifySong(@Body CRequest classifyRequest);


}
