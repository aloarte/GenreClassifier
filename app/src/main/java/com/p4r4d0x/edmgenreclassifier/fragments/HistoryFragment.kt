//package com.p4r4d0x.edmgenreclassifier.fragments
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.p4r4d0x.edmgenreclassifier.MainActivity
//import com.p4r4d0x.edmgenreclassifier.R
//
///**
// * Fragment associated with ClassifierActivity.
// * Shows a wait view while the multimedia is sended to the server
// */
//class HistoryFragment : Fragment() {
//    /**
//     * Parent activity of the fragment
//     */
//    private var parentActivity: MainActivity? = null
//
//    /**
//     * Data with the stats of the user
//     */
//    var userDataObtained: StatsResponse? = null
//
//    /**
//     * ProgressBars for the main cardviews to indicate load progress
//     */
//    private var pbLastClassify: ProgressBar? = null
//    private var pbHistory: ProgressBar? = null
//
//    /**
//     * LinearLayouts with the main view of the cardview which contains the data
//     */
//    private var llLastClassifyBody: LinearLayout? = null
//    private var llHistoryBody: LinearLayout? = null
//
//    /**
//     * LinearLayouts for the included views for each genre
//     */
//    private var ilAvgGenre: LinearLayout? = null
//    private var ilRowGenre: LinearLayout? = null
//    private var ilLastGenre: LinearLayout? = null
//
//    /**
//     * TextViews for the time and capture mode of each capture
//     */
//    private var tvAvgCaptureTime: TextView? = null
//    private var tvAvgCaptureMode: TextView? = null
//    private var tvLastCaptureMode: TextView? = null
//    private var tvLastCaptureTime: TextView? = null
//
//    /**
//     * Flag that holds if the views of the fragment are initialized
//     */
//    private var isViewInitialized = false
//
//    /**
//     * Get if the data is loaded into the views of the fragment
//     * @return
//     */
//    /**
//     * Flag that holds if the data is loaded into the views
//     */
//    var isDataLoaded = false
//        private set
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        //Load the root view
//        val inflatedView: View =
//            inflater.inflate(R.layout.fragment_history, container, false)
//        initLayoutElements(inflatedView)
//
//        //If the userData is loaded, fill the views
//        if (userDataObtained != null) {
//            fillClassifyStats(userDataObtained.getClassifyStats())
//            fillLastClassify(userDataObtained.getLastClassify())
//            isDataLoaded = true
//            swapProgressLoad(true)
//        } else {
//            swapProgressLoad(false)
//        }
//        return inflatedView
//    }
//
//    /**
//     * Initialize all the views from the fragment
//     * @param inflatedView  The main view of the fragment
//     */
//    private fun initLayoutElements(inflatedView: View) {
//        //Views for the history data
//        ilAvgGenre = inflatedView.findViewById(R.id.il_avg_genre)
//        ilRowGenre = inflatedView.findViewById(R.id.il_inarow_genre)
//        tvAvgCaptureMode = inflatedView.findViewById(R.id.tv_avg_capture_mode)
//        tvAvgCaptureTime = inflatedView.findViewById(R.id.tv_avg_capture_time)
//        pbHistory = inflatedView.findViewById(R.id.pb_history)
//        llHistoryBody = inflatedView.findViewById(R.id.ll_body_history)
//
//        //Views for the last capture data
//        ilLastGenre = inflatedView.findViewById(R.id.il_last_genre)
//        tvLastCaptureMode = inflatedView.findViewById(R.id.tv_last_capture_mode)
//        tvLastCaptureTime = inflatedView.findViewById(R.id.tv_last_capture_time)
//        pbLastClassify = inflatedView.findViewById(R.id.pb_last_classify)
//        llLastClassifyBody = inflatedView.findViewById(R.id.ll_body_last_classify)
//        isViewInitialized = true
//    }
//
//    /**
//     * Set the parent activity to the fragment
//     * @param parentActivity Activity
//     */
//    fun setParentActivity(parentActivity: MainActivity?) {
//        this.parentActivity = parentActivity
//    }
//
//    /**
//     * Set into the views the data obtained
//     * @param userData  Data of the request
//     */
//    fun setUserValues(userData: StatsResponse) {
//
//        //Check if the view is initialized previously
//        if (isViewInitialized) {
//            fillClassifyStats(userData.getClassifyStats())
//            fillLastClassify(userData.getLastClassify())
//            isDataLoaded = true
//            swapProgressLoad(true)
//        }
//        userDataObtained = userData
//    }
//
//    /**
//     * Fills the views with the data from the classify history
//     * @param classifyStats Data to fill the views
//     */
//    @SuppressLint("SetTextI18n")
//    private fun fillClassifyStats(classifyStats: ClassifyStats) {
//        tvAvgCaptureMode.setText(classifyStats.getAvgAudioType())
//        tvAvgCaptureTime.setText(classifyStats.getAvgSampleTime().toString())
//        ElementGenre.fillViewElementGenre(parentActivity, ilAvgGenre, classifyStats.getAvgGenre())
//        ElementGenre.fillViewElementGenre(
//            parentActivity,
//            ilRowGenre,
//            classifyStats.getConsecutiveGenre()
//        )
//    }
//
//    /**
//     * Fills the views with the data from the last classify
//     * @param lastClassify  Data to fill the views
//     */
//    @SuppressLint("SetTextI18n")
//    fun fillLastClassify(lastClassify: LastClassify) {
//        tvLastCaptureMode.setText(lastClassify.getLastAudioType())
//        tvLastCaptureTime.setText(lastClassify.getLastSampleTime().toString())
//        ElementGenre.fillViewElementGenre(parentActivity, ilLastGenre, lastClassify.getLastGenre())
//    }
//
//    /**
//     * Show or hide the progress bar and the main info views of the fragment
//     * @param isLoaded  True to show the views with info, False to hide and show progress bars
//     */
//    fun swapProgressLoad(isLoaded: Boolean) {
//        pbLastClassify!!.visibility = if (isLoaded) View.GONE else View.VISIBLE
//        pbHistory!!.visibility = if (isLoaded) View.GONE else View.VISIBLE
//        llLastClassifyBody!!.visibility = if (isLoaded) View.VISIBLE else View.GONE
//        llHistoryBody!!.visibility = if (isLoaded) View.VISIBLE else View.GONE
//    }
//
//}