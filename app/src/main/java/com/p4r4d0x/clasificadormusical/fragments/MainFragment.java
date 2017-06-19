package com.p4r4d0x.clasificadormusical.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.p4r4d0x.clasificadormusical.MainActivity;
import com.p4r4d0x.clasificadormusical.R;


public class MainFragment extends Fragment {

    private ImageView      ivPickSong;
    private ImageView      ivRecordAudio;

    private MainActivity parentActivity;

    protected static final int INTENT_PICK_SONG    = 101;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_main, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        ivPickSong      = (ImageView)inflatedView.findViewById(R.id.iv_pick_song);
        ivRecordAudio   = (ImageView)inflatedView.findViewById(R.id.iv_record_audio);

        ivPickSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null){
                    parentActivity.launchIntentPickSong();
                }
            }
        });


        ivRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getParentActivity()!=null) {
                    parentActivity.doFragmentRecordAudio();
                }

            }
        });
    }




    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
