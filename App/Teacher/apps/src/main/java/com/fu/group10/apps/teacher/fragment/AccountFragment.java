package com.fu.group10.apps.teacher.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fu.group10.apps.teacher.R;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class AccountFragment extends Fragment{

    private WebView account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        initView(rootView);
        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View rootView) {

        account = (WebView) rootView.findViewById(R.id.account);
        account.getSettings().setJavaScriptEnabled(true);
        account.setWebViewClient(new WebViewClient());
        account.setBackgroundColor(0);
        account.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        refreshMap();

    }

    private void refreshMap() {
        account.clearCache(true);
        account.loadUrl("http://google.com");
    }
}
