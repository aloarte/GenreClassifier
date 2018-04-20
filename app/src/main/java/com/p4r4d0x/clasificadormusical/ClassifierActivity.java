package com.p4r4d0x.clasificadormusical;

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

import com.p4r4d0x.clasificadormusical.async.AsynkTaskClasifySong;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierGetAudioFragment;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierResultFragment;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierSendingFragment;
import com.p4r4d0x.clasificadormusical.rest.old_rest.DataClassifySongResponse;
import com.p4r4d0x.clasificadormusical.rest.old_rest.MusicGenres;
import com.p4r4d0x.clasificadormusical.rest.old_rest.SongDescription;

import java.io.IOException;

/**
 * Activity that handles fragment to make the complete classify process
 */
public class ClassifierActivity extends AppCompatActivity implements AsynkTaskClasifySong.OnSongClassified {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return true;
    }

    @Override
    public void onSongClassifiedSuccess(MusicGenres genre) {
        doFragmentResult(genre);
    }

    @Override
    public void onSongClassifiedError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        doFragmentResult(MusicGenres.NONE);

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

        //Check ifd the onActivityResult is for the intent_pick_song
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

        new AsynkTaskClasifySong(this).execute(new SongDescription(name, null, getApplicationContext(), uristring));
        doFragmentSending();
        return null;
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
