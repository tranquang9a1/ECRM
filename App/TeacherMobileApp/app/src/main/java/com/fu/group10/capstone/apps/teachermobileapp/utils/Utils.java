package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 5/30/2015.
 * @author TrungDQ
 */
public class Utils {

    private static int notificationId = 0;
    private static List<Integer> shownNotify = new ArrayList<Integer>();

    public static int dp2px(int sizeInDp) {
        float scale = DummyApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    public static int px2dp(int sizeInPx) {
        float scale = DummyApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) ((sizeInPx - 0.5f) / scale);
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }



    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) DummyApplication.
                        getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
