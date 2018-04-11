package com.p4r4d0x.clasificadormusical;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.p4r4d0x.clasificadormusical.async.AsynkTaskClasifySong;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierSendingFragment;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierLoadSongFragment;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierRecordFragment;
import com.p4r4d0x.clasificadormusical.fragments.ClassifierResultFragment;
import com.p4r4d0x.clasificadormusical.rest.DataClassifySongResponse;
import com.p4r4d0x.clasificadormusical.rest.MusicGenres;
import com.p4r4d0x.clasificadormusical.rest.SongDescription;

import java.io.IOException;

/**
 * Activity that handles fragment to make the complete classify process
 */
public class ClassifierActivity extends AppCompatActivity implements AsynkTaskClasifySong.OnSongClassified{

    private MediaRecorder mediaRecorder = null;
    private MediaPlayer   mediaPlayerRecorded = null;
    private MediaPlayer mediaPlayerPickedSong = null;

    private static final String LOG_TAG = "ALRALR";
    protected static final int INTENT_PICK_SONG    = 101;
    protected static final int INTENT_RECORD_AUDIO = 102;
    protected static final int REQUEST_RECORD_AUDIO_PERMISSION = 201;


    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public boolean mStartRecording = true;
    public boolean isAudioRecordedLongThan2Mins = false;
    public boolean isPlayingASong = false;


    public AudioSelected audioSelected= AudioSelected.NONE;
    public String audioRecordedMicrophone = null;
    public Uri audioPicked;

    private boolean isFragmentMain = false;

    public enum AudioSelected{
        AUDIO_FROM_SONG,
        AUDIO_FROM_MICROPHONE,
        NONE
    }


    @Override
    public void onSongClassifiedSuccess(MusicGenres genre) {
        doFragmentResult(genre);
    }

    @Override
    public void onSongClassifiedError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        doFragmentMain();



    }

    public void launchIntentPickSong(){
        Intent intentPickSong = new Intent();
        intentPickSong.setType("audio/*");
        intentPickSong.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentPickSong,INTENT_PICK_SONG);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_PICK_SONG){
            if(resultCode == RESULT_OK){
                audioPicked = data.getData();
                if(audioPicked != null){
                    audioSelected = AudioSelected.AUDIO_FROM_SONG;
                    doFragmentPreSendAudio();
                }
            }
            else{
                resetRecordingAudio();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }




    public void doFragmentMain(){
        isFragmentMain = true;
        audioRecordedMicrophone = getExternalCacheDir().getAbsolutePath();
        audioRecordedMicrophone += "/audiorecordtest.3gp";
        resetRecordingAudio();

        ClassifierSendingFragment classifierSendingFragment = new ClassifierSendingFragment();
        classifierSendingFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierSendingFragment);
        transaction.commit();
    }

    public void doFragmentResult(MusicGenres genre){
        isFragmentMain = false;

        ClassifierResultFragment classifierResultFragment = new ClassifierResultFragment();
        classifierResultFragment.setGenre(genre);
        classifierResultFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierResultFragment);
        transaction.commit();
    }

    public void doFragmentRecordAudio(){
        isFragmentMain = false;

        ClassifierRecordFragment classifierRecordFragment = new ClassifierRecordFragment();
        classifierRecordFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierRecordFragment);
        transaction.commit();

    }

    public void doFragmentPreSendAudio(){
        isFragmentMain = false;

        ClassifierLoadSongFragment classifierLoadSongFragment = new ClassifierLoadSongFragment();
        classifierLoadSongFragment.setParentActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentContainer, classifierLoadSongFragment);
        transaction.commit();
    }

    public void onPlaySongPicked(boolean play){
        if(play){
            try{
                mediaPlayerPickedSong = new MediaPlayer();
                mediaPlayerPickedSong.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayerPickedSong.setDataSource(getApplicationContext(), audioPicked);
                mediaPlayerPickedSong.prepare();
                mediaPlayerPickedSong.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            mediaPlayerPickedSong.release();
            mediaPlayerPickedSong = null;
        }


    }

    public void onRecordRecorded(boolean start) {
        if (start) {
            if( mediaRecorder == null ) {

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
                    Toast.makeText(this,"Recording Failed",Toast.LENGTH_SHORT);
                    Log.e(LOG_TAG, "prepare() failed");
                }

            }
        }
        else {
            if( mediaRecorder != null ) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                audioSelected = AudioSelected.AUDIO_FROM_MICROPHONE;
            }
        }
    }

    public void onPlayRecorded(boolean play) {
        if (play) {
            mediaPlayerRecorded = new MediaPlayer();
            try {



                mediaPlayerRecorded.setDataSource(audioRecordedMicrophone);
                mediaPlayerRecorded.prepare();
                mediaPlayerRecorded.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        } else {
            mediaPlayerRecorded.release();
            mediaPlayerRecorded = null;        }
    }

    public void resetRecordingAudio() {
        if(!mStartRecording){
            onRecordRecorded(false);

        }
        mStartRecording = true;
        isAudioRecordedLongThan2Mins = false;
        isPlayingASong = false;

        audioSelected= AudioSelected.NONE;
        audioPicked = null ;

        mediaPlayerPickedSong = null;
        mediaPlayerRecorded=null;
        mediaRecorder = null;




    }

    public DataClassifySongResponse clasifySong(String name, Uri song, String uristring){

        new AsynkTaskClasifySong(this).execute(new SongDescription(name,null,getApplicationContext(),uristring));


        return null;
    }

    @Override
    public void onBackPressed() {
        if(isFragmentMain){
            super.onBackPressed();
        }
        else{
            doFragmentMain();
        }
    }
}
