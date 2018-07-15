package com.p4r4d0x.genreclassifier.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyRequest;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyResponse;
import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final OkHttpClient client;
    /**
     * Gson for the parser
     */
    private Gson gson;

    /**
     * Default constructor. Instanciate the core properties.
     */
    public RetrofitClient() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    /**
     * Calls to the service using ClassifyRetrofitService
     *
     * @param serviceUrl       Endpoint of the service
     * @param songRequest      CRequest object with the json body of the request
     * @param responseCallback Response callback to retrieve the result of the service call
     */
    public void classifySong(String serviceUrl, ClassifyRequest songRequest, Callback<ClassifyResponse> responseCallback) {


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
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
    public void userStats(String serviceUrl, Long userId, Callback<StatsResponse> responseCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(serviceUrl)
                .build();

        StatsRetrofitService service = retrofit.create(StatsRetrofitService.class);
        service.userStats(userId).enqueue(responseCallback);
    }
}
