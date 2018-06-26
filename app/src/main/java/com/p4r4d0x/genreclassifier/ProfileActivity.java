package com.p4r4d0x.genreclassifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.p4r4d0x.genreclassifier.rest.RetrofitClient;
import com.p4r4d0x.genreclassifier.rest.ServerErrorManager;
import com.p4r4d0x.genreclassifier.rest.stats.SResponse;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p4r4d0x.genreclassifier.utils.Constants.MAX_SERVICE_TIMEOUT_RETRIES;

public class ProfileActivity extends AppCompatActivity {

    /**
     * Retries allowed to hold the request against the server before call it as an error
     */
    private int serviceStatsTimeoutRetries = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getUserData();
    }


    public void getUserData() {
        RetrofitClient restClient = new RetrofitClient();
        Long userId = 12341234234L;
        restClient.userStats(((GenreClassificatorApplication) getApplicationContext()).getServiceURL(), userId, new Callback<SResponse>() {


            @Override
            public void onResponse(Call<SResponse> call, Response<SResponse> response) {
                Log.d("RetroFit", "StatsService onResponse: " + response.code());
                serviceStatsTimeoutRetries = 0;

                if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
                } else {
                    Log.e("RetroFit", "StatsService onResponse: " + response.code());
                    ServerErrorManager.manageServerError(response.code(), ServerErrorManager.getServiceMethodClassify());

                }
            }

            @Override
            public void onFailure(Call<SResponse> call, Throwable t) {
                //If its a SocketTimeoutException, enqueue again the call
                if (serviceStatsTimeoutRetries < MAX_SERVICE_TIMEOUT_RETRIES && (t instanceof SocketTimeoutException || t instanceof SocketException)) {
                    call.clone().enqueue(this);
                    Log.d("RetroFit", "StatsService Enqueued: " + serviceStatsTimeoutRetries);
                    serviceStatsTimeoutRetries++;

                }
                //Any other exception is treated as an error
                else {
                    serviceStatsTimeoutRetries = 0;
                    Log.e("RetroFit", "StatsService onFailure: " + t.getMessage());
                    ServerErrorManager.manageServerException(t, ServerErrorManager.getServiceMethodClassify(), (GenreClassificatorApplication) getApplicationContext());
                }
            }
        });
    }

}
