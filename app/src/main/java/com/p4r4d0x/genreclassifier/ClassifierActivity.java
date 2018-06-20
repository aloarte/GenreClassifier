package com.p4r4d0x.genreclassifier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.p4r4d0x.genreclassifier.fragments.ClassifierGetAudioFragment;
import com.p4r4d0x.genreclassifier.fragments.ClassifierResultFragment;
import com.p4r4d0x.genreclassifier.fragments.ClassifierSendingFragment;
import com.p4r4d0x.genreclassifier.rest.RetrofitClient;
import com.p4r4d0x.genreclassifier.rest.ServerErrorManager;
import com.p4r4d0x.genreclassifier.rest.classify.CRequest;
import com.p4r4d0x.genreclassifier.rest.classify.CResponse;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyRequest;
import com.p4r4d0x.genreclassifier.rest.classify.SongInfo;
import com.p4r4d0x.genreclassifier.rest.classify.User;
import com.p4r4d0x.genreclassifier.rest.old_rest.DataClassifySongResponse;
import com.p4r4d0x.genreclassifier.rest.old_rest.MusicGenres;
import com.p4r4d0x.genreclassifier.rest.stats.SResponse;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p4r4d0x.genreclassifier.utils.Constants.MAX_SERVICE_TIMEOUT_RETRIES;

/**
 * Activity that handles fragment to make the complete classify process
 */
public class ClassifierActivity extends AppCompatActivity {

    protected static final int INTENT_PICK_SONG = 101;
    protected static final int INTENT_RECORD_AUDIO = 102;
    protected static final int REQUEST_RECORD_AUDIO_PERMISSION = 201;
    private static final String LOG_TAG = "ALRALR";
    public boolean mStartRecording = true;
    public boolean isAudioRecordedLongThan2Mins = false;
    public boolean isPlayingASong = false;
    public AudioSelected audioSelected = AudioSelected.NONE;
    public String audioRecordedMicrophone = null;
    public Uri audioPicked;
    ClassifierResultFragment classifierResultFragment;
    ClassifierGetAudioFragment classifierGetAudioFragment;
    ClassifierSendingFragment classifierSendingFragment;
    private MediaRecorder mediaRecorder = null;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean isFragmentMain = false;

    private boolean isLoadedAudioShowing = false;
    private int serviceStatsTimeoutRetries = 0, serviceClassifyTimeoutRetries = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
        toolbar.inflateMenu(R.menu.application_menu);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        doFragmentGetAudio();
    }

    public void launchIntentPickSong() {
        Intent intentPickSong = new Intent();
        intentPickSong.setType("audio/*");
        intentPickSong.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentPickSong, INTENT_PICK_SONG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the onActivityResult is for the intent_pick_song
        if (requestCode == INTENT_PICK_SONG) {

            //If its OK
            if (resultCode == RESULT_OK) {
                audioPicked = data.getData();
                if (audioPicked != null) {
                    audioSelected = AudioSelected.AUDIO_FROM_SONG;
                    isLoadedAudioShowing = true;
                    classifierGetAudioFragment.setGetAudioPicked();

                }
            }
            //If fails
            else {
                doFragmentGetAudio();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    public void doFragmentGetAudio() {
        isFragmentMain = true;
        isLoadedAudioShowing = false;
        audioRecordedMicrophone = getExternalCacheDir().getAbsolutePath();
        audioRecordedMicrophone += "/audiorecordtest.3gp";
        resetRecordingAudio();
        classifierGetAudioFragment = new ClassifierGetAudioFragment();
        classifierGetAudioFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierGetAudioFragment);
        transaction.commit();

    }

    public void doFragmentSending() {
        isFragmentMain = false;
        classifierSendingFragment = new ClassifierSendingFragment();
        classifierSendingFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierSendingFragment);
        transaction.commit();
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

    }

    public void doFragmentResult(MusicGenres genre) {
        isFragmentMain = false;
        classifierResultFragment = new ClassifierResultFragment();
        classifierResultFragment.setGenre(genre);
        classifierResultFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierResultFragment);
        transaction.commit();
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

    }

    @SuppressLint("ShowToast")
    public void onRecordRecorded(boolean start) {
        if (start) {
            if (mediaRecorder == null) {

                mediaRecorder = new MediaRecorder();
                mediaRecorder.reset();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(audioRecordedMicrophone);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    mStartRecording = false;

                } catch (IOException e) {
                    Toast.makeText(this, "Recording Failed", Toast.LENGTH_SHORT);
                    Log.e(LOG_TAG, "prepare() failed");
                }

            }
        } else {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                audioSelected = AudioSelected.AUDIO_FROM_MICROPHONE;
            }
        }
    }

    public void resetRecordingAudio() {
        if (!mStartRecording) {
            onRecordRecorded(false);

        }
        mStartRecording = true;
        isAudioRecordedLongThan2Mins = false;
        isPlayingASong = false;

        audioSelected = AudioSelected.NONE;
        audioPicked = null;

        mediaRecorder = null;

    }

    public DataClassifySongResponse clasifySong(String name, Uri song, String uristring) {
        try {
            SongInfo requestSongInfo = new SongInfo();
            User user = new User();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
            Date currentTime = formatter.parse("2011-07-19T18:23:20+0000");
            long millisecondsSinceEpoch0 = currentTime.getTime();


            CRequest requestData = new CRequest(new ClassifyRequest(requestSongInfo, user, millisecondsSinceEpoch0));
            RetrofitClient restClient = new RetrofitClient();
            restClient.classifySong(((GenreClassificatorApplication) getApplicationContext()).getServiceURL(), requestData, new Callback<CResponse>() {
                @Override
                public void onResponse(Call<CResponse> call, Response<CResponse> response) {
                    serviceClassifyTimeoutRetries = 0;
                    Log.d("RetroFit", "ClassifyService onResponse: " + response.code());

                    if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
                        doFragmentResult(MusicGenres.Dubstep);
                    } else {
                        Log.e("RetroFit", "ClassifyService onResponse: " + response.code());
                        ServerErrorManager.manageServerError(response.code(), ServerErrorManager.getServiceMethodClassify());
                        doFragmentResult(MusicGenres.NONE);

                    }
                }

                @Override
                public void onFailure(Call<CResponse> call, Throwable t) {

                    //If its a SocketTimeoutException, enqueue again the call
                    if (serviceClassifyTimeoutRetries < MAX_SERVICE_TIMEOUT_RETRIES && (t instanceof SocketTimeoutException || t instanceof SocketException)) {
                        call.clone().enqueue(this);
                        Log.d("RetroFit", "ClassifyService Enqueued: " + serviceClassifyTimeoutRetries);
                        serviceClassifyTimeoutRetries++;

                    }
                    //Any other exception is treated as an error
                    else {
                        serviceClassifyTimeoutRetries = 0;
                        Log.e("RetroFit", "ClassifyService onFailure: " + t.getMessage());
                        ServerErrorManager.manageServerException(t, ServerErrorManager.getServiceMethodClassify(), (GenreClassificatorApplication) getApplicationContext());
                        doFragmentResult(MusicGenres.NONE);
                    }
                }
            });
            doFragmentSending();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void onBackPressed() {
        if (isFragmentMain) {
            if (!isLoadedAudioShowing) {
                super.onBackPressed();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
            } else {
                doFragmentGetAudio();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);

            }
        } else {
            doFragmentGetAudio();
            overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);

        }
    }


    public enum AudioSelected {
        AUDIO_FROM_SONG,
        AUDIO_FROM_MICROPHONE,
        NONE
    }
}
