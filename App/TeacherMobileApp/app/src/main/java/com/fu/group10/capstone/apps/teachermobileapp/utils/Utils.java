package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 5/30/2015.
 *
 * @author TrungDQ
 */
public class Utils {


    static boolean isOnline;


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
//                return isOnline;
//            }
//            Log.d("isOnline", isOnline + "");
//            //return false;
//            return false;


        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }

    }

}
