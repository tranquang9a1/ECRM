package com.fu.group10.apps.teacher.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.fu.group10.apps.teacher.R;
import android.webkit.WebViewClient;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class ClassroomFragment extends Fragment {

    private WebView classmap;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);

        initView(rootView);
        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View rootView) {
        classmap = (WebView) rootView.findViewById(R.id.classmap);
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        refreshMap();

    }

    private void refreshMap() {
        classmap.clearCache(true);
        classmap.loadUrl("http://tienhxtsb60520:8080/api/map?id=123");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id = bundle.getInt("id");
            refreshMap();
        }
    }



}
