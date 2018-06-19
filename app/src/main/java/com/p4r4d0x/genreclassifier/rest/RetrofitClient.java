package com.p4r4d0x.genreclassifier.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.p4r4d0x.genreclassifier.rest.classify.CRequest;
import com.p4r4d0x.genreclassifier.rest.classify.CResponse;
import com.p4r4d0x.genreclassifier.rest.stats.SResponse;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /**
     * Gson for the parser
     */
    Gson gson;

    /**
     * Default constructor. Instanciate the core properties.
     */
    public RetrofitClient() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();
    }

    /**
     * Calls to the service using ClassifyRetrofitService
     *
     * @param serviceUrl       Endpoint of the service
     * @param songRequest      CRequest object with the json body of the request
     * @param responseCallback Response callback to retrieve the result of the service call
     */
    public void classifySong(String serviceUrl, CRequest songRequest, Callback<CResponse> responseCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(serviceUrl)
                .build();

        ClassifyRetrofitService service = retrofit.create(ClassifyRetrofitService.class);
        service.classifySong(songRequest).enqueue(responseCallback);
    }

    /**
     * Calls to the service using StatsRetrofitService
     *
     * @param serviceUrl       Endpoint of the service
     * @param userId           Long with the ID of the client used in the request
     * @param responseCallback Response callback to retrieve the result of the service call
     */
    public void userStats(String serviceUrl, Long userId, Callback<SResponse> responseCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(serviceUrl)
                .build();

        StatsRetrofitService service = retrofit.create(StatsRetrofitService.class);
        service.userStats(userId).enqueue(responseCallback);
    }
}
