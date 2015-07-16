package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by QuangTV on 5/31/2015.
 */
public class RequestSender extends AsyncTask<String, Integer, String> {

    IRequestSenderComplete callback;
    public String method = "GET";
    public Map<String, String> param;

    public void start(String url, IRequestSenderComplete callback) {
        this.callback = callback;
        this.execute(url);
    }

    protected String doInBackground(String... urls) {
        if(method.equals("GET"))
            return NetworkUtils.getResponseFromGetRequest(urls[0]);
        else
            return NetworkUtils.getResponseFromPOSTRequest(urls[0], param, 30000);
    }

    protected void onProgressUpdate(Integer... progress) {
        // setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        if (this.callback != null) {
            this.callback.onRequestComplete(result);
        }
    }

    public interface IRequestSenderComplete {
        void onRequestComplete(String result);
    }
}
