package com.p4r4d0x.edmgenreclassifier

import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
//    /**
//     * History fragment with the user data
//     */
//    private var historyFragment: HistoryFragment? = null
//
//    /**
//     * Profile fragment with the account logged
//     */
//    private var profileFragment: ProfileFragment? = null
//
//    /**
//     * Classifier fragmento to retrieve the audio
//     */
//    private var classifierGetAudioFragment: ClassifierGetAudioFragment? = null
//
//    /**
//     * Classifier fragment to show a wait screen to the user
//     */
//    private var classifierSendingFragment: ClassifierSendingFragment? = null
//
//    /**
//     * Classifier fragment to show the result of the classify
//     */
//    private var classifierResultFragment: ClassifierResultFragment? = null
//
//    /**
//     * Classifier fragment to show an error case
//     */
//    private var classifierResultErrorFragment: ClassifierResultErrorFragment? = null

    /**
     * Max timeout retries for the serviceStats request
     */
    private var serviceStatsTimeoutRetries = 0

    /**
     * Max timeout retries for the classify request
     */
    private var serviceClassifyTimeoutRetries = 0

    /**
     * String with the name of the file where the record audio is saved
     */
    var audioRecordedMicrophone: String? = null
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_classify -> {
                    doFragmentClassifyRecord()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    doFragmentHistory()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    doFragmentProfile()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    //    /**
//     * Launch the profile fragment
//     */
    fun doFragmentProfile() {
//        val transaction =
////            supportFragmentManager.beginTransaction()
////        transaction.replace(R.id.main_fragment_container, profileFragment)
////        transaction.addToBackStack(null)
////        transaction.commit()
    }

    //
//    /**
//     * Launch the user history fragment
//     */
    fun doFragmentHistory() {
        //Check if the data is not already loaded in the fragment, so the fragment doesnt call several times to the service
//        if (!historyFragment.isDataLoaded()) {
//            serviceGetUserData()
//        }
//        val transaction =
//            supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_fragment_container, historyFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
    }

    //
//    /**
//     * Launch the main classify fragment
//     */
    fun doFragmentClassifyRecord() {
//        audioRecordedMicrophone = externalCacheDir!!.absolutePath
//        audioRecordedMicrophone += "/audiorecordtest.3gp"
//        //resetRecordingAudio();
//        val transaction =
//            supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_fragment_container, classifierGetAudioFragment)
//        transaction.commit()
    }
//
//    /**
//     * Launch the classify sending fragment
//     */
//    fun doFragmentClassifySending() {
//        val transaction =
//            supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
//        transaction.replace(R.id.main_fragment_container, classifierSendingFragment)
//        transaction.commit()
//        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change)
//    }
//
//    /**
//     * Launch the classify result fragment
//     */
//    fun doFragmentClassifyResult(
//        relatedGenres: List<Genre?>?,
//        mainGenre: MusicGenre?,
//        songDetail: SongDetail?
//    ) {
//        try {
//            Thread.sleep(2000)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//        classifierResultFragment.setGenre(mainGenre)
//        val transaction =
//            supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
//        transaction.replace(R.id.main_fragment_container, classifierResultFragment)
//        transaction.commit()
//        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change)
//    }
//
//    /**
//     * Launch the classify result error fragment
//     */
//    private fun doFragmentClassifyResultError() {
//        val transaction =
//            supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
//        transaction.replace(R.id.main_fragment_container, classifierResultErrorFragment)
//        transaction.commit()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instanciateFragments()
        //Launch the first classify fragment
        doFragmentClassifyRecord()
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryMedium)
        val navigation =
            findViewById<BottomNavigationView>(R.id.navigation_bottom_bar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val taskDescription = TaskDescription(
            resources.getString(R.string.app_name),
            null,
            resources.getColor(R.color.colorPrimaryMedium)
        )
        (this as Activity).setTaskDescription(taskDescription)
    }

    /**
     * Iniciate the fragments
     */
    fun instanciateFragments() {
        //Create all the fragments
//        historyFragment = HistoryFragment()
//        profileFragment = ProfileFragment()
//        classifierGetAudioFragment = ClassifierGetAudioFragment()
//        classifierSendingFragment = ClassifierSendingFragment()
//        classifierResultFragment = ClassifierResultFragment()
//        classifierResultErrorFragment = ClassifierResultErrorFragment()
//        historyFragment.setParentActivity(this)
//        profileFragment.setParentActivity(this)
//        classifierGetAudioFragment.setParentActivity(this)
//        classifierSendingFragment.setParentActivity(this)
//        classifierResultFragment.setParentActivity(this)
//        classifierResultErrorFragment.setParentActivity(this)
    }

//    /**
//     * Launch an intent pick to retrieve a song from the phone
//     */
//    fun launchIntentPickSong() {
//        val intentPickSong = Intent()
//        intentPickSong.type = "audio/*"
//        intentPickSong.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intentPickSong, INTENT_PICK_SONG)
//    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        //Check if the onActivityResult is for the intent_pick_song
//        if (requestCode == INTENT_PICK_SONG) {
//
////            //If its OK
////            if (resultCode == RESULT_OK) {
////                audioPicked = data.getData();
////                if (audioPicked != null) {
////                    audioSelected = ClassifierActivity.AudioSelected.AUDIO_FROM_SONG;
////                    isLoadedAudioShowing = true;
////                    classifierGetAudioFragment.setGetAudioPicked();
////
////                }
////            }
////            //If fails
////            else {
////                doFragmentGetAudio();
////            }
//        }
//    }

//    /**
//     * Perform a request to retrieve the user data from the service
//     */
//    fun serviceGetUserData() {
//        val restClient = RetrofitClient()
//        val userId = 12341234234L
//        restClient.userStats(
//            (applicationContext as GenreClassificatorApplication).getServiceURL(),
//            userId,
//            object : Callback<StatsResponse?> {
//                override fun onResponse(
//                    call: Call<StatsResponse?>,
//                    response: Response<StatsResponse?>
//                ) {
//                    Log.d("RetroFit", "StatsService onResponse: " + response.code())
//                    serviceStatsTimeoutRetries = 0
//                    if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
//                        val statsData: StatsResponse? = response.body()
//                        historyFragment.setUserValues(statsData)
//                    } else {
//                        Log.e(
//                            "RetroFit",
//                            "StatsService onResponse: " + response.code()
//                        )
//                        ServerErrorManager.manageServerError(
//                            response.code(),
//                            ServerErrorManager.getServiceMethodClassify()
//                        )
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<StatsResponse?>,
//                    t: Throwable
//                ) {
//                    //If its a SocketTimeoutException,SocketException or UnknownHostException (timeout ,bad network or no network) enqueue again the call after 2 seconds of sleeping
//                    if (t is SocketTimeoutException || t is SocketException || t is UnknownHostException) {
//                        try {
//                            Thread.sleep(2000)
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//                        call.clone().enqueue(this)
//                        Log.d(
//                            "RetroFit",
//                            "StatsService Enqueued: $serviceStatsTimeoutRetries"
//                        )
//                        serviceStatsTimeoutRetries++
//                    } else {
//                        serviceStatsTimeoutRetries = 0
//                        Log.e("RetroFit", "StatsService onFailure: " + t.message)
//                        ServerErrorManager.manageServerException(
//                            t,
//                            ServerErrorManager.getServiceMethodClassify(),
//                            applicationContext as GenreClassificatorApplication
//                        )
//                    }
//                }
//            })
//    }
//
//    fun serviceClasifySong(
//        name: String?,
//        song: Uri?,
//        uristring: String?
//    ): ClassifyResponse? {
//        val requestSongInfo = SongInfo()
//        val user = User()
//        @SuppressLint("SimpleDateFormat") val formatter =
//            SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ")
//        //Date currentTime = formatter.parse("2011-07-19T18:23:20+0000");
//        //long millisecondsSinceEpoch0 = currentTime.getTime();
//        val requestData =
//            ClassifyRequest(requestSongInfo, 0.5, user, "2018-06-29T21:51:43.851615+00:00")
//        //            requestData.setSongInfo(new SongInfo("AUDIO_PICKED",180000,"The island","AAAAGGZ0eXAzZ3A","MP3"));
////            requestData.setUser(new User(true,"aaa"));
//        val restClient = RetrofitClient()
//        restClient.classifySong(
//            (applicationContext as GenreClassificatorApplication).getServiceURL(),
//            requestData,
//            object : Callback<ClassifyResponse?> {
//                override fun onResponse(
//                    call: Call<ClassifyResponse?>,
//                    response: Response<ClassifyResponse?>
//                ) {
//                    serviceClassifyTimeoutRetries = 0
//                    Log.d("RetroFit", "ClassifyService onResponse: " + response.code())
//                    if (response.code() >= Constants.SERVER_CONTENT_BOT && response.code() <= Constants.SERVER_CONTENT_TOP) {
//                        val songResponse: ClassifyResponse? = response.body()
//                        doFragmentClassifyResult(
//                            songResponse.getGenres(),
//                            songResponse.getGenre(),
//                            songResponse.getSongDetail()
//                        )
//                    } else {
//                        Log.e(
//                            "RetroFit",
//                            "ClassifyService onResponse: " + response.code()
//                        )
//                        ServerErrorManager.manageServerError(
//                            response.code(),
//                            ServerErrorManager.getServiceMethodClassify()
//                        )
//                        doFragmentClassifyResult(null, MusicGenre.NONE, null)
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ClassifyResponse?>,
//                    t: Throwable
//                ) {
//
//                    //If its a SocketTimeoutException, enqueue again the call
//                    if (serviceClassifyTimeoutRetries < MAX_SERVICE_TIMEOUT_RETRIES && (t is SocketTimeoutException || t is SocketException)) {
//                        call.clone().enqueue(this)
//                        Log.d(
//                            "RetroFit",
//                            "ClassifyService Enqueued: $serviceClassifyTimeoutRetries"
//                        )
//                        serviceClassifyTimeoutRetries++
//                    } else {
//                        serviceClassifyTimeoutRetries = 0
//                        Log.e("RetroFit", "ClassifyService onFailure: " + t.message)
//                        ServerErrorManager.manageServerException(
//                            t,
//                            ServerErrorManager.getServiceMethodClassify(),
//                            applicationContext as GenreClassificatorApplication
//                        )
//                        doFragmentClassifyResultError()
//                    }
//                }
//            })
//        doFragmentClassifySending()
//        return null
//    }
}