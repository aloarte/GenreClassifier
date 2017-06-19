package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.p4r4d0x.clasificadormusical.MainActivity;
import com.p4r4d0x.clasificadormusical.R;

public class RecordAudioFragment extends Fragment {

    private MainActivity parentActivity;

    private ProgressBar pbhStatusAudioRecorded;

    private TextView tvCurrentTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_record_audio, container, false);
        initLayoutElements(inflatedView);
        startRecording();
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        pbhStatusAudioRecorded = (ProgressBar) inflatedView.findViewById(R.id.pbhStatusAudioRecorded);
        tvCurrentTime          = (TextView) inflatedView.findViewById(R.id.tvCurrentTime);
    }

    private CountDownTimer countDownTimer =
            new CountDownTimer(120000, 100) { //120000 (02:00)
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
                    parentActivity.doFragmentPreSendAudio();
                }
            };


    public void startRecording(){
       if(getParentActivity()!=null){
           parentActivity.onRecordRecorded(parentActivity.mStartRecording);
           countDownTimer.start();


       }

    }

    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
