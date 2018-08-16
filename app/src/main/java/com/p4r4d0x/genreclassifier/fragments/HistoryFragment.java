package com.p4r4d0x.genreclassifier.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.p4r4d0x.genreclassifier.MainActivity;
import com.p4r4d0x.genreclassifier.R;
import com.p4r4d0x.genreclassifier.rest.stats.ClassifyStats;
import com.p4r4d0x.genreclassifier.rest.stats.LastClassify;
import com.p4r4d0x.genreclassifier.rest.stats.StatsResponse;
import com.p4r4d0x.genreclassifier.utils.ElementGenre;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class HistoryFragment extends Fragment {

    /**
     * Parent activity of the fragment
     */
    private MainActivity parentActivity;

    /**
     * Data with the stats of the user
     */
    StatsResponse userDataObtained = null;

    /**
     * ProgressBars for the main cardviews to indicate load progress
     */
    private ProgressBar pbLastClassify, pbHistory;

    /**
     * LinearLayouts with the main view of the cardview which contains the data
     */
    private LinearLayout llLastClassifyBody, llHistoryBody;

    /**
     * LinearLayouts for the included views for each genre
     */
    private LinearLayout ilAvgGenre, ilRowGenre,ilLastGenre;

    /**
     * TextViews for the time and capture mode of each capture
     */
    private TextView tvAvgCaptureTime, tvAvgCaptureMode, tvLastCaptureMode,tvLastCaptureTime;

    /**
     * Flag that holds if the views of the fragment are initialized
     */
    private boolean isViewInitialized = false;

    /**
     * Flag that holds if the data is loaded into the views
     */
    private boolean dataLoaded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Load the root view
        View inflatedView = inflater.inflate(R.layout.fragment_history, container, false);
        initLayoutElements(inflatedView);

        //If the userData is loaded, fill the views
        if (userDataObtained != null) {
            fillClassifyStats(userDataObtained.getClassifyStats());
            fillLastClassify(userDataObtained.getLastClassify());
            dataLoaded=true;
            swapProgressLoad(true);
        }
        //In the other case, show a progressBar
        else{
            swapProgressLoad(false);
        }

        return inflatedView;
    }

    /**
     * Initialize all the views from the fragment
     * @param inflatedView  The main view of the fragment
     */
    private void initLayoutElements(View inflatedView) {
        //Views for the history data
        ilAvgGenre = inflatedView.findViewById(R.id.il_avg_genre);
        ilRowGenre = inflatedView.findViewById(R.id.il_inarow_genre);
        tvAvgCaptureMode = inflatedView.findViewById(R.id.tv_avg_capture_mode);
        tvAvgCaptureTime = inflatedView.findViewById(R.id.tv_avg_capture_time);
        pbHistory = inflatedView.findViewById(R.id.pb_history);
        llHistoryBody = inflatedView.findViewById(R.id.ll_body_history);

        //Views for the last capture data
        ilLastGenre = inflatedView.findViewById(R.id.il_last_genre);
        tvLastCaptureMode = inflatedView.findViewById(R.id.tv_last_capture_mode);
        tvLastCaptureTime = inflatedView.findViewById(R.id.tv_last_capture_time);
        pbLastClassify = inflatedView.findViewById(R.id.pb_last_classify);
        llLastClassifyBody = inflatedView.findViewById(R.id.ll_body_last_classify);

        isViewInitialized = true;

    }

    /**
     * Set the parent activity to the fragment
     * @param parentActivity Activity
     */
    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    /**
     * Set into the views the data obtained
     * @param userData  Data of the request
     */
    public void setUserValues(StatsResponse userData) {

        //Check if the view is initialized previously
        if(isViewInitialized){
            fillClassifyStats(userData.getClassifyStats());
            fillLastClassify(userData.getLastClassify());
            dataLoaded=true;
            swapProgressLoad(true);
        }
        userDataObtained = userData;

    }

    /**
     * Fills the views with the data from the classify history
     * @param classifyStats Data to fill the views
     */
    @SuppressLint("SetTextI18n")
    private void fillClassifyStats(ClassifyStats classifyStats) {
        tvAvgCaptureMode.setText(classifyStats.getAvgAudioType());
        tvAvgCaptureTime.setText(classifyStats.getAvgSampleTime().toString());
        ElementGenre.fillViewElementGenre(parentActivity, ilAvgGenre, classifyStats.getAvgGenre());
        ElementGenre.fillViewElementGenre(parentActivity, ilRowGenre, classifyStats.getConsecutiveGenre());

    }

    /**
     * Fills the views with the data from the last classify
     * @param lastClassify  Data to fill the views
     */
    @SuppressLint("SetTextI18n")
    public void fillLastClassify(LastClassify lastClassify){
        tvLastCaptureMode.setText(lastClassify.getLastAudioType());
        tvLastCaptureTime.setText(lastClassify.getLastSampleTime().toString());
        ElementGenre.fillViewElementGenre(parentActivity, ilLastGenre, lastClassify.getLastGenre());
    }

    /**
     * Show or hide the progress bar and the main info views of the fragment
     * @param isLoaded  True to show the views with info, False to hide and show progress bars
     */
    public void swapProgressLoad(boolean isLoaded){
        pbLastClassify.setVisibility(isLoaded ? View.GONE : View.VISIBLE);
        pbHistory.setVisibility(isLoaded ? View.GONE : View.VISIBLE);
        llLastClassifyBody.setVisibility(isLoaded ? View.VISIBLE : View.GONE);
        llHistoryBody.setVisibility(isLoaded ? View.VISIBLE : View.GONE);
    }


    /**
     * Get if the data is loaded into the views of the fragment
     * @return
     */
    public boolean isDataLoaded() {
        return dataLoaded;
    }
}
