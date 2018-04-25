package com.p4r4d0x.genreclassifier.async;

import android.os.AsyncTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;
import com.p4r4d0x.genreclassifier.rest.StatsRetrofitService;
import com.p4r4d0x.genreclassifier.rest.stats.SResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AsyncTask to ask the profile statistics from the server
 */
public class AsynkTaskRetrieveProfileStats extends AsyncTask<Void, Void, SResponse> {

    /**
     * ID of the user to get the stats from the server
     */
    private final Long userId;

    /**
     * Base URL of the service
     */
    private final String baseUrl;

    /**
     * Callback to notify the events holded by this asynctask
     */
    private OnStatsRetrieved callbackListener;

    /**
     * By default null. Is setted in case of any error
     */
    private String errorMessage ;

    /**
     * Asynctask constructor
     * @param listener  callback to notify the events
     * @param userID    userID to get its stats
     * @param baseUrl   baseUrl where the endpoint is
     */
    public AsynkTaskRetrieveProfileStats(OnStatsRetrieved listener, Long userID, String baseUrl) {
        this.callbackListener = listener;
        this.userId = userID;
        this.baseUrl = baseUrl;
    }

    @Override
    protected SResponse doInBackground(Void... voids) {
        try {

            Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .build();

            StatsRetrofitService service = retrofit.create(StatsRetrofitService.class);

            Response<SResponse> response = service.userStats(userId).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            else{
                return null;
            }
        }
        //The json is malformed. Catch and log to track the dev error
        catch (MalformedJsonException exMalformed) {
            exMalformed.printStackTrace();
            errorMessage = exMalformed.getMessage();
            return null;
        }
        catch (IllegalArgumentException | IOException exIllegalIO){
            errorMessage = exIllegalIO.getMessage();
            return null;
        }
        //General catch exception
        catch (Exception ex) {
            errorMessage= ex.getMessage();
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(SResponse serviceResponse) {
        super.onPostExecute(serviceResponse);
        //Check if the response is not null
        if(serviceResponse!=null){
            callbackListener.onStatsRetrieved(serviceResponse);
        }
        //If its null, there is an error
        else{
            //Check if the error is not null
            if(errorMessage!=null){
                callbackListener.onStatsFailed(errorMessage);
            }
            //If null, return a general error
            else{
                callbackListener.onStatsFailed("GENERAL_ERROR");
            }
        }

    }

    /**
     * Callback interface to indicate to the app when the result is obtained
     */
    public interface OnStatsRetrieved {

        /**
         * The call was succesful. Returns SResponse with the data returned by the service
         * @param statsResponse     Structure with all the stats requested
         */
        void onStatsRetrieved(SResponse statsResponse);

        /**
         * The call failed. Return a message with the reason
         * @param errorMessage  String with the reason of the failure
         */
        void onStatsFailed(String errorMessage);

    }

}


