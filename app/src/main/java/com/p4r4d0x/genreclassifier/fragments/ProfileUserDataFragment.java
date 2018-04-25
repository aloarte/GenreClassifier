package com.p4r4d0x.genreclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p4r4d0x.genreclassifier.ProfileActivity;
import com.p4r4d0x.genreclassifier.R;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class ProfileUserDataFragment extends Fragment {

    private ProfileActivity parentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_profile_user_data, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {

    }


    public ProfileActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ProfileActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
