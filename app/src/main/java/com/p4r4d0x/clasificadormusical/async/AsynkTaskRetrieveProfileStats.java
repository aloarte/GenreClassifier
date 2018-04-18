package com.p4r4d0x.clasificadormusical.async;

import android.os.AsyncTask;

/**
 * AsyncTask to ask the profile statistics from the server
 */
public class AsynkTaskRetrieveProfileStats extends AsyncTask<Void, Void, Void> {

    private OnStatsRetrieved callbackListener;


    public AsynkTaskRetrieveProfileStats(OnStatsRetrieved listener) {
        this.callbackListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        callbackListener.onStatsFailed("error");
    }

    /**
     * Callback interface to indicate to the app when the result is obtained
     */
    public interface OnStatsRetrieved {

        void onStatsRetrieved();

        void onStatsFailed(String errorMessage);

    }

}


