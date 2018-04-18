package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p4r4d0x.clasificadormusical.ClassifierActivity;
import com.p4r4d0x.clasificadormusical.R;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class ClassifierSendingFragment extends Fragment {

    private ClassifierActivity parentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_classifier_sending, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {

    }




    public ClassifierActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ClassifierActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
