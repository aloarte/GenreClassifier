package com.p4r4d0x.clasificadormusical.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p4r4d0x.clasificadormusical.R;
import com.p4r4d0x.clasificadormusical.StarterActivity;

/**
 * Fragment associated with StarterActivity.
 * Shows info about the app
 */
public class StarterInfoFragment extends Fragment {

    /**
     * Reference to the parent activity
     */
    StarterActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_starter_info, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {

    }
    /**
     * Set the value of the parent activity when the fragment is created by the activity
     * @param parentActivity    parent Activity that hold this fragment
     */
    public void setParentActivity(StarterActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
