package com.fu.group10.capstone.apps.staffmobileapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;

/**
 * Created by QuangTV on 6/5/2015.
 */
public class MapActivity extends Activity {


    private WebView classmap;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        id = getIntent().getExtras().getInt("id");
        classmap = (WebView) findViewById(R.id.classmap);
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        refreshMap();
    }

    private void refreshMap() {
        classmap.clearCache(true);
        String url = Constants.API_LOAD_MAP + id;
        classmap.loadUrl(url);
    }

}
