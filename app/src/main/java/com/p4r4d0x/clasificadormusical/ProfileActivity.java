package com.p4r4d0x.clasificadormusical;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.p4r4d0x.clasificadormusical.async.AsynkTaskRetrieveProfileStats;
import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

public class ProfileActivity extends AppCompatActivity implements AsynkTaskRetrieveProfileStats.OnStatsRetrieved {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public SResponse getUserStats() {
        new AsynkTaskRetrieveProfileStats(this,1223123L,"http://192.168.1.43:8081/").execute();
        return null;
    }

    @Override
    public void onStatsRetrieved(SResponse statsResponse) {

    }

    @Override
    public void onStatsFailed(String errorMessage) {

    }

}
