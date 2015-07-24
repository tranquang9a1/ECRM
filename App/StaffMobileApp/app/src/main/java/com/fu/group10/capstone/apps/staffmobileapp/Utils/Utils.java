package com.fu.group10.capstone.apps.staffmobileapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.fu.group10.capstone.apps.staffmobileapp.DummyApplication;
import com.fu.group10.capstone.apps.staffmobileapp.model.Result;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by QuangTV on 5/30/2015.
 *
 * @author TrungDQ
 */
public class Utils {


    static boolean isOnline = false;


    public static boolean isOnline() {

        try {

            ConnectivityManager cm =
                    (ConnectivityManager) DummyApplication.
                            getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
//            if (netInfo != null && netInfo.isConnected()) {
//                RequestSender sender = new RequestSender();
//                String url = Constants.API_CHECK_CONNECTION;
//                sender.start(url, new RequestSender.IRequestSenderComplete() {
//                    @Override
//                    public void onRequestComplete(String result) {
//                        try {
//                            Result res = ParseUtils.parseResult(result);
//                            if (res != null) {
//                                isOnline = true;
//                            } else {
//                                isOnline = false;
//                            }
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                            isOnline = false;
//                        }
//                    }
//                });
//
//            }
//            return isOnline;


        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }

    }



}
