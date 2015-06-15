package com.fu.group10.apps.staff;

import android.app.Application;
import android.content.Context;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class DummyApplication extends Application {
    public static Context mContext;

    /**
     * Static class to get app context everywhere.
     * Use it carefully if or you will get memory leak
     * @return Context
     */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
