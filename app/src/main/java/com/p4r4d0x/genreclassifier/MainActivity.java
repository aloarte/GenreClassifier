package com.p4r4d0x.genreclassifier;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.p4r4d0x.genreclassifier.fragments.HistoryFragment;
import com.p4r4d0x.genreclassifier.fragments.ProfileFragment;
import com.p4r4d0x.genreclassifier.rest.RetrofitClient;
import com.p4r4d0x.genreclassifier.rest.ServerErrorManager;
import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p4r4d0x.genreclassifier.utils.Constants.MAX_SERVICE_TIMEOUT_RETRIES;

public class MainActivity extends AppCompatActivity {

    private HistoryFragment historyFragment;

    private ProfileFragment profileFragment;

    /**
     * Max timeout retries for the serviceStats request
     */
    private int serviceStatsTimeoutRetries = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_classify:
                    doFragmentClassify();
                    return true;
                case R.id.navigation_history:
                    doFragmentHistory();
                    return true;
                case R.id.navigation_profile:
                    doFragmentProfile();
                    return true;
            }
            return false;
        }
    };

    private void doFragmentProfile() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void doFragmentHistory() {

        getUserData();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, historyFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void doFragmentClassify() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyFragment = new HistoryFragment();
        historyFragment.setParentActivity(this);
        profileFragment = new ProfileFragment();
        profileFragment.setParentActivity(this);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryMedium));
        BottomNavigationView navigation = findViewById(R.id.navigation_bottom_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void getUserData() {
        RetrofitClient restClient = new RetrofitClient();
        Long userId = 12341234234L;
        restClient.userStats(((GenreClassificatorApplication) getApplicationContext()).getServiceURL(), userId, new Callback<StatsResponse>() {


            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                Log.d("RetroFit", "StatsService onResponse: " + response.code());
                serviceStatsTimeoutRetries = 0;

                if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
                    StatsResponse statsData = response.body();
                    historyFragment.setUserValues(statsData);
                } else {
                    Log.e("RetroFit", "StatsService onResponse: " + response.code());
                    ServerErrorManager.manageServerError(response.code(), ServerErrorManager.getServiceMethodClassify());

                }
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {
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
