package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p4r4d0x.clasificadormusical.R;

/**
 * Fragment associated with SelectorActivity.
 * Shows the screen to record a song from the microphone and send it to the service
 */
public class SelectorRecordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_selector_record, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
