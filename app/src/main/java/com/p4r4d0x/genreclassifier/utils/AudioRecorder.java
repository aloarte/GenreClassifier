package com.p4r4d0x.genreclassifier.utils;

import android.app.Activity;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class AudioRecorder {

    private MediaRecorder mediaRecorder = null;
    private String mFileName = null;

    public AudioRecorder(Activity ctx) {
        mFileName = ctx.getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtestAudioRecorder.3gp";
    }


    public void startRecording() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("ALRALR", "prepare() failed");
        }

        mediaRecorder.start();
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

}
