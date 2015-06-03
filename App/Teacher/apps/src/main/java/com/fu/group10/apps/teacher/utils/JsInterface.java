package com.fu.group10.apps.teacher.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class JsInterface {
    Context context;
    public JsInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void openURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }
}
