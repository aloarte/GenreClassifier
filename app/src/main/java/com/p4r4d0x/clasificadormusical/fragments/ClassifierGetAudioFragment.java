package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.p4r4d0x.clasificadormusical.ClassifierActivity;
import com.p4r4d0x.clasificadormusical.R;

/**
 * Fragment associated with ClassifierActivity.
 * Shows info about the multimedia recorded to classify it
 */
public class ClassifierGetAudioFragment extends Fragment {

    private ClassifierActivity parentActivity;

    private ProgressBar pbhStatusAudioRecorded;

    FloatingActionButton fabPickSong;
    private TextView tvCurrentTime, tvSongPickedTitle;
    private LinearLayout llSongPicked, llRecord;
    private ImageView ivRecord;
    private Button btnClassify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_classifier_get_audio, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        llSongPicked = inflatedView.findViewById(R.id.ll_song_picked);
        llRecord = inflatedView.findViewById(R.id.ll_record_audio);
        pbhStatusAudioRecorded = inflatedView.findViewById(R.id.pbhStatusAudioRecorded);
        ivRecord = inflatedView.findViewById(R.id.iv_song_picked);
        tvCurrentTime = inflatedView.findViewById(R.id.tvCurrentTime);
        tvSongPickedTitle = inflatedView.findViewById(R.id.tv_title);
        fabPickSong = inflatedView.findViewById(R.id.fab_pick_song);
        btnClassify = inflatedView.findViewById(R.id.btn_send_audio);
        fabPickSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.launchIntentPickSong();
            }
        });
        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.clasifySong("The island", null, parentActivity.audioRecordedMicrophone/*parentActivity.audioPicked*/);

            }
        });
        llRecord.setVisibility(View.VISIBLE);
        llSongPicked.setVisibility(View.GONE);
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

    }

    private CountDownTimer countDownTimer =
            new CountDownTimer(/*120*/1000, 100) { //120000 (02:00)
                public void onTick(long millisUntilFinished) {
                    //Get the time in seconds and invert it
                    long timeInSeconds = millisUntilFinished /1000;
                    long timeInSecondReversed = 120-  timeInSeconds;
                    //Get the minutes and the seconds
                    long minutes = timeInSecondReversed / 60;
                    long seconds = timeInSecondReversed % 60;
                    String substrMinutes ="";
                    String substrSeconds = "";
                    //If the minutes number have only 1 digit, add a 0 in front
                    if(Math.abs(minutes/10)>=1){
                        substrMinutes=""+minutes;
                    }
                    else{
                        substrMinutes="0"+minutes;
                    }
                    //If the seconds number have only 1 digit, add a 0 in front
                    if(Math.abs(seconds/10)>=1){
                        substrSeconds=""+seconds;
                    }
                    else{
                        substrSeconds="0"+seconds;
                    }
                    //Join both substrings
                    String currentTime = substrMinutes+":"+substrSeconds;

                    //Put the text and the progress in the layout
                    tvCurrentTime.setText(currentTime);
                    pbhStatusAudioRecorded.setProgress(Math.abs((int) millisUntilFinished / 1200 - 100));

                }

                @Override
                public void onFinish() {
                    parentActivity.onRecordRecorded(false);
                }
            };


    public void startRecording(){
       if(getParentActivity()!=null){
           parentActivity.onRecordRecorded(parentActivity.mStartRecording);
           countDownTimer.start();


       }

    }

    public ClassifierActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ClassifierActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    public void setGetAudioPicked() {
        llRecord.setVisibility(View.GONE);
        llSongPicked.setVisibility(View.VISIBLE);
        tvSongPickedTitle.setText("Song Picked");
    }
}
