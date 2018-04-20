package com.p4r4d0x.clasificadormusical.async;

import android.os.AsyncTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.p4r4d0x.clasificadormusical.rest.StatsRetrofitService;
import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AsyncTask to ask the profile statistics from the server
 */
public class AsynkTaskRetrieveProfileStats extends AsyncTask<Void, Void, Void> {

    private OnStatsRetrieved callbackListener;


    public AsynkTaskRetrieveProfileStats(OnStatsRetrieved listener) {
        this.callbackListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.0.14:8081/")
                .build();

        StatsRetrofitService service = retrofit.create(StatsRetrofitService.class);
        try {
            Response<SResponse> response = service.userStats("123123123123123").execute();
            if (response.isSuccessful()) {
                response.body();
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        callbackListener.onStatsFailed("error");
    }

    /**
     * Callback interface to indicate to the app when the result is obtained
     */
    public interface OnStatsRetrieved {

        void onStatsRetrieved();

        void onStatsFailed(String errorMessage);

    }

}


