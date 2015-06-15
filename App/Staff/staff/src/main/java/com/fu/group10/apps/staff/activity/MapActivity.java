package com.fu.group10.apps.staff.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fu.group10.apps.staff.R;

/**
 * Created by QuangTV on 6/5/2015.
 */
public class MapActivity extends Activity {


    private WebView classmap;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        id = getIntent().getExtras().getString("id");
        classmap = (WebView) findViewById(R.id.classmap);
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        refreshMap();
    }

    private void refreshMap() {
        classmap.clearCache(true);
        classmap.loadUrl("http://app.megahd.vn/api/capstone/test.html");
    }

}
