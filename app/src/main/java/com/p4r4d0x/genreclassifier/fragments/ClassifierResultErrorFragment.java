package com.p4r4d0x.genreclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.p4r4d0x.genreclassifier.ClassifierActivity;
import com.p4r4d0x.genreclassifier.R;

public class ClassifierResultErrorFragment extends Fragment {


    private ClassifierActivity parentActivity;


    private Button btnErrorReclassify;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_classifier_result_error, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        btnErrorReclassify = inflatedView.findViewById(R.id.btn_reclassify_error);
        btnErrorReclassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.clasifySong(null, null, null);
            }
        });

    }


    public ClassifierActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ClassifierActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
