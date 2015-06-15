package com.fu.group10.apps.teacher.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.TextView;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.MainActivity;
import com.fu.group10.apps.teacher.activity.ReportDetailActivity;
import com.fu.group10.apps.teacher.adapter.AccountAdapter;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.InfinityListView;
import com.fu.group10.apps.teacher.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class AccountFragment extends Fragment{

    InfinityListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        initListView(rootView);

        return rootView;
    }

    private void initListView(View rootView) {
        Log.d("demo", "Load list view");
        View emptyView = rootView.findViewById(R.id.empty_list);
        if (!Utils.isOnline()) {
            ((TextView) emptyView.findViewById(R.id.emptyMessage)).setText(DummyApplication.getAppContext().getString(R.string.noConnection));
        } else {
            ((TextView) emptyView.findViewById(R.id.emptyMessage)).setText(DummyApplication.getAppContext().getString(R.string.no_report_found));
        }
        listView = (InfinityListView) rootView.findViewById(R.id.listView);
        final List<ReportInfo> items = new ArrayList<ReportInfo>();
        String username = getActivity().getIntent().getExtras().getString("username");
        final AccountAdapter adapter = new AccountAdapter(DummyApplication.getAppContext(), items, username);


        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ReportInfo reportInfo = items.get(position);
                Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                intent.putExtra("report", reportInfo);
                startActivity(intent);
            }
        });


    }


}
