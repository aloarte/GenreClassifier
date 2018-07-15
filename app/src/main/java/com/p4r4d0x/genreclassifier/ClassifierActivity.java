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
import android.view.MenuItem;
import android.widget.Toast;

import com.p4r4d0x.genreclassifier.fragments.ClassifierGetAudioFragment;
import com.p4r4d0x.genreclassifier.fragments.ClassifierResultErrorFragment;
import com.p4r4d0x.genreclassifier.fragments.ClassifierResultFragment;
import com.p4r4d0x.genreclassifier.fragments.ClassifierSendingFragment;
import com.p4r4d0x.genreclassifier.rest.RetrofitClient;
import com.p4r4d0x.genreclassifier.rest.ServerErrorManager;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyRequest;
import com.p4r4d0x.genreclassifier.rest.classify.ClassifyResponse;
import com.p4r4d0x.genreclassifier.rest.classify.Genre;
import com.p4r4d0x.genreclassifier.rest.classify.MusicGenre;
import com.p4r4d0x.genreclassifier.rest.classify.SongDetail;
import com.p4r4d0x.genreclassifier.rest.classify.SongInfo;
import com.p4r4d0x.genreclassifier.rest.classify.User;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private ClassifierResultErrorFragment classifierResultErrorFragment;
    private MediaRecorder mediaRecorder = null;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean isFragmentMain = false;

    private boolean isLoadedAudioShowing = false;
    private int serviceClassifyTimeoutRetries = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Intent profileActivity = new Intent(this, ProfileActivity.class);
                startActivity(profileActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
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
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.flFragmentContainer, classifierGetAudioFragment);
        transaction.commit();

    }

    public void doFragmentSending() {
        isFragmentMain = false;
        classifierSendingFragment = new ClassifierSendingFragment();
        classifierSendingFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.flFragmentContainer, classifierSendingFragment);
        transaction.commit();
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

    }

    public void doFragmentResult(List<Genre> relatedGenres, MusicGenre mainGenre, SongDetail songDetail) {
        isFragmentMain = false;
        classifierResultFragment = new ClassifierResultFragment();
        classifierResultFragment.setGenre(mainGenre);
        classifierResultFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.flFragmentContainer, classifierResultFragment);
        transaction.commit();
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

    }


    public void doFragmentResultError() {
        isFragmentMain = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        classifierResultErrorFragment = new ClassifierResultErrorFragment();
        classifierResultErrorFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.flFragmentContainer, classifierResultErrorFragment);
        transaction.commit();

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

    public ClassifyResponse clasifySong(String name, Uri song, String uristring) {

            SongInfo requestSongInfo = new SongInfo();
            User user = new User();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
        //Date currentTime = formatter.parse("2011-07-19T18:23:20+0000");
        //long millisecondsSinceEpoch0 = currentTime.getTime();


        ClassifyRequest requestData = new ClassifyRequest(requestSongInfo, 0.5, user, "2018-06-29T21:51:43.851615+00:00");
//            requestData.setSongInfo(new SongInfo("AUDIO_PICKED",180000,"The island","AAAAGGZ0eXAzZ3A","MP3"));
//            requestData.setUser(new User(true,"aaa"));

            RetrofitClient restClient = new RetrofitClient();
        restClient.classifySong(((GenreClassificatorApplication) getApplicationContext()).getServiceURL(), requestData, new Callback<ClassifyResponse>() {
                @Override
                public void onResponse(Call<ClassifyResponse> call, Response<ClassifyResponse> response) {
                    serviceClassifyTimeoutRetries = 0;
                    Log.d("RetroFit", "ClassifyService onResponse: " + response.code());

                    if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
                        ClassifyResponse songResponse = response.body();
                        doFragmentResult(songResponse.getGenres(), songResponse.getGenre(), songResponse.getSongDetail());
                    } else {
                        Log.e("RetroFit", "ClassifyService onResponse: " + response.code());
                        ServerErrorManager.manageServerError(response.code(), ServerErrorManager.getServiceMethodClassify());
                        doFragmentResult(null, MusicGenre.NONE, null);


                    }
                }

                @Override
                public void onFailure(Call<ClassifyResponse> call, Throwable t) {

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
                        doFragmentResultError();
                    }
                }
            });
            doFragmentSending();
        return null;
    }


    @Override
    public void onBackPressed() {
        if (isFragmentMain) {
            if (!isLoadedAudioShowing) {
                super.onBackPressed();
            } else {
                doFragmentGetAudio();

            }
        } else {
            doFragmentGetAudio();

        }
    }

    public enum AudioSelected {
        AUDIO_FROM_SONG,
        AUDIO_FROM_MICROPHONE,
        NONE
    }
}
