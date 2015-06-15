package com.fu.group10.apps.staff.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.fu.group10.apps.staff.DummyApplication;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Utils;
import com.fu.group10.apps.staff.activity.MainActivity;
import com.fu.group10.apps.staff.activity.ResolveReportActivity;
import com.fu.group10.apps.staff.adapter.TestAdapter;
import com.fu.group10.apps.staff.component.InfinityListView;
import com.fu.group10.apps.staff.model.ReportInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/6/2015.
 */
public class TestFragment extends Fragment {

    InfinityListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
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

        TestAdapter adapter = new TestAdapter(DummyApplication.getAppContext(), items);


        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ReportInfo reportInfo = items.get(position);
                openLive(reportInfo);
            }
        });
    }

    private void openLive(ReportInfo reportInfo) {
        Intent intent = new Intent(getActivity(), ResolveReportActivity.class);
        intent.putExtra("report", reportInfo);
        startActivity(intent);
    }
}
