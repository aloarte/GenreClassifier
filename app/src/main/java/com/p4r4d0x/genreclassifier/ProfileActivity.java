package com.p4r4d0x.genreclassifier;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.p4r4d0x.genreclassifier.fragments.ProfileDataHistoryFragment;
import com.p4r4d0x.genreclassifier.fragments.ProfileLastClassifyFragment;
import com.p4r4d0x.genreclassifier.fragments.ProfileRelatedSongGenreFragment;
import com.p4r4d0x.genreclassifier.rest.RetrofitClient;
import com.p4r4d0x.genreclassifier.rest.ServerErrorManager;
import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p4r4d0x.genreclassifier.utils.Constants.MAX_SERVICE_TIMEOUT_RETRIES;

public class ProfileActivity extends AppCompatActivity {

    /**
     * Retries allowed to hold the request against the server before call it as an error
     */
    private int serviceStatsTimeoutRetries = 0;

    StatsResponse statsData = null;

    ProfileLastClassifyFragment lastClassifyFragment;
    ProfileRelatedSongGenreFragment relatedSongGenreFragment;
    ProfileDataHistoryFragment dataHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getUserData();

        lastClassifyFragment = new ProfileLastClassifyFragment();
        relatedSongGenreFragment = new ProfileRelatedSongGenreFragment();
        dataHistoryFragment = new ProfileDataHistoryFragment();

        lastClassifyFragment.setParentActivity(this);
        relatedSongGenreFragment.setParentActivity(this);
        dataHistoryFragment.setParentActivity(this);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
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
                    statsData = response.body();
                    dataHistoryFragment.setUserValues(statsData);
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


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(lastClassifyFragment, "Última consulta");
        adapter.addFragment(relatedSongGenreFragment, "Relacionados");
        adapter.addFragment(dataHistoryFragment, "Historial");
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(adapter);


    }

    static class TabAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        TabAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}


