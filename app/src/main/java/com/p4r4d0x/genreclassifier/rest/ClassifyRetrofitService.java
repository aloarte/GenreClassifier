package com.p4r4d0x.genreclassifier.rest;

import com.p4r4d0x.genreclassifier.rest.classify.ClassifyRequest;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClassifyRetrofitService {

    @POST("dev/classifier/classifygenre")
    Call<ClassifyResponse> classifySong(@Body ClassifyRequest classifyRequest);


}
