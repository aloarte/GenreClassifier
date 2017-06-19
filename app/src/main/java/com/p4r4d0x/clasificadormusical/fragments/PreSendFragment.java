package com.p4r4d0x.clasificadormusical.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.p4r4d0x.clasificadormusical.MainActivity;
import com.p4r4d0x.clasificadormusical.R;

public class PreSendFragment extends Fragment {

    private MainActivity parentActivity;

    private ImageView      ivPauseAudio;
    private ImageView      ivPlayAudio;
    private ImageView      ivRemoveAudio;
    private ImageView      ivClasifySong;
    private RelativeLayout rlPlayingSong;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_pre_send, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        ivPlayAudio             = (ImageView)inflatedView.findViewById(R.id.iv_playAudio);
        ivPauseAudio            = (ImageView)inflatedView.findViewById(R.id.iv_stopAudio);
        ivRemoveAudio           = (ImageView)inflatedView.findViewById(R.id.iv_removeAudio);
        ivClasifySong           = (ImageView)inflatedView.findViewById(R.id.iv_clasify_song);
        rlPlayingSong           = (RelativeLayout) inflatedView.findViewById(R.id.rl_playingAudio);
        rlPlayingSong.setVisibility(View.INVISIBLE);

        ivPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null){
                    startAudio();
                }

            }
        });

        ivPauseAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null){
                    stopAudio();
                }
            }
        });

        ivRemoveAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null){
                    stopAudio();
                    parentActivity.doFragmentMain();
                }
            }
        });

        ivClasifySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null){
                    if(!(parentActivity.audioSelected == MainActivity.AudioSelected.NONE) &&
                            ((parentActivity.audioPicked != null) || (parentActivity.audioRecordedMicrophone != null ))){

                        stopAudio();
                        parentActivity.clasifySong("The island", parentActivity.audioPicked);

                    }
                    else{
                        Toast toast = Toast.makeText(parentActivity, "Pick a song first", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            }
        });

    }

    private void startAudio() {
        if(!parentActivity.isPlayingASong){
            rlPlayingSong.setVisibility(View.VISIBLE);
            if(parentActivity.audioSelected == MainActivity.AudioSelected.AUDIO_FROM_MICROPHONE){
                parentActivity.onPlayRecorded(true);
                parentActivity.isPlayingASong = true;
            }
            else if(parentActivity.audioSelected == MainActivity.AudioSelected.AUDIO_FROM_SONG){
                parentActivity.onPlaySongPicked(true);
                parentActivity.isPlayingASong = true;
            }
        }
    }

    public void stopAudio(){
        if(parentActivity.isPlayingASong){
            rlPlayingSong.setVisibility(View.INVISIBLE);
            if(parentActivity.audioSelected == MainActivity.AudioSelected.AUDIO_FROM_MICROPHONE){
                parentActivity.onPlayRecorded(false);
                parentActivity.isPlayingASong = false;

            }
            else if(parentActivity.audioSelected == MainActivity.AudioSelected.AUDIO_FROM_SONG){
                parentActivity.onPlaySongPicked(false);
                parentActivity.isPlayingASong = false;
            }
        }
    }

    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
