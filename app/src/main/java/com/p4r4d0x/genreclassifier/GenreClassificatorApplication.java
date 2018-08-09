package com.p4r4d0x.genreclassifier;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.p4r4d0x.genreclassifier.utils.Constants;

public class GenreClassificatorApplication extends Application implements Thread.UncaughtExceptionHandler {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Listen to all the uncaughExceptions to log them
     *
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (isCrashReportEnabled()) {
//            Crashlytics.log(Log.ERROR, "UncaugthException", "Reason " + throwable.getMessage());
//            Crashlytics.logException(throwable);

        }

    }

    /**
     * Save the loaded params from the remote config in Firebase into the application
     *
     * @param paramEndpoint           Endpoint of the service
     * @param paramCrashReportEnabled Boolean flag to report the crashes
     */
    public void setRemoteConfig(String paramEndpoint, String paramCrashReportEnabled) {
        SharedPreferences sharedPref = getSharedPreferences("GenreClassificatorApplication", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.putString(Constants.PROPERTY_BACK_ENDPOINT, paramEndpoint);
        try {
            sharedPrefEditor.putBoolean(Constants.PROPERTY_CRASH_REPORT_ENABLED, Boolean.parseBoolean(paramCrashReportEnabled));
        } catch (Exception e) {
            sharedPrefEditor.putBoolean(Constants.PROPERTY_CRASH_REPORT_ENABLED, true);
        }
        sharedPrefEditor.apply();
    }

    /**
     * Recover from the preferences the URL of the classify request
     *
     * @return String with the complete url
     */
    public String getServiceURL() {
        SharedPreferences sharedPref = getSharedPreferences("GenreClassificatorApplication", Context.MODE_PRIVATE);
        return sharedPref.getString(Constants.PROPERTY_BACK_ENDPOINT, "");
    }

    /**
     * Recover from the preferences if the crash reports with crashlytics are enabled
     *
     * @return Boolean with the value
     */
    public boolean isCrashReportEnabled() {
        SharedPreferences sharedPref = getSharedPreferences("GenreClassificatorApplication", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Constants.PROPERTY_CRASH_REPORT_ENABLED, true);
    }


}
