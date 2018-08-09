package com.p4r4d0x.genreclassifier.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p4r4d0x.genreclassifier.MainActivity;
import com.p4r4d0x.genreclassifier.R;
import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;
import com.p4r4d0x.genreclassifier.utils.ElementGenre;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class HistoryFragment extends Fragment {

    private MainActivity parentActivity;

    StatsResponse userDataObtained = null;
    private LinearLayout ilAvgGenre, ilRowGenre;
    private TextView tvAvgCaptureTime, tvAvgCaptureMode;
    private boolean isViewInitialized = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_history, container, false);
        initLayoutElements(inflatedView);

        if (userDataObtained != null) {
            tvAvgCaptureMode.setText(userDataObtained.getClassifyStats().getAvgAudioType());
            tvAvgCaptureTime.setText(userDataObtained.getClassifyStats().getAvgSampleTime().toString());
            ElementGenre.fillViewElementGenre(parentActivity, ilAvgGenre, userDataObtained.getClassifyStats().getAvgGenre());
            ElementGenre.fillViewElementGenre(parentActivity, ilRowGenre, userDataObtained.getClassifyStats().getConsecutiveGenre());
        }

        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        ilAvgGenre = inflatedView.findViewById(R.id.il_avg_genre);
        ilRowGenre = inflatedView.findViewById(R.id.il_inarow_genre);
        tvAvgCaptureMode = inflatedView.findViewById(R.id.tv_avg_capture_mode);
        tvAvgCaptureTime = inflatedView.findViewById(R.id.tv_avg_capture_time);


    }

    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @SuppressLint("SetTextI18n")
    public boolean setUserValues(StatsResponse userData) {
        tvAvgCaptureMode.setText(userData.getClassifyStats().getAvgAudioType());
        tvAvgCaptureTime.setText(userData.getClassifyStats().getAvgSampleTime().toString());
        ElementGenre.fillViewElementGenre(parentActivity, ilAvgGenre, userData.getClassifyStats().getAvgGenre());
        ElementGenre.fillViewElementGenre(parentActivity, ilRowGenre, userData.getClassifyStats().getConsecutiveGenre());
        return true;

    }


}
