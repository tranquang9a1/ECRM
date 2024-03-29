package com.fu.group10.apps.staff.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fu.group10.apps.staff.DummyApplication;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Constants;
import com.fu.group10.apps.staff.Utils.ParseUtils;
import com.fu.group10.apps.staff.Utils.RequestSender;
import com.fu.group10.apps.staff.Utils.Utils;
import com.fu.group10.apps.staff.activity.MainActivity;
import com.fu.group10.apps.staff.activity.ResolveReportActivity;
import com.fu.group10.apps.staff.adapter.ListRoomAdapter;
import com.fu.group10.apps.staff.adapter.TestAdapter;
import com.fu.group10.apps.staff.component.InfinityListView;
import com.fu.group10.apps.staff.model.ReportInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/6/2015.
 */
public class FinishFragment extends Fragment {

    private ListView mListView;
    private ListRoomAdapter mAdapter;
    private String username;
    private List<ReportInfo> mRandomData = new ArrayList<ReportInfo>();
    private View emptyView;
    private Context context;
    private ProgressBar progressBar;
    private TextView emptyMessage;
    private String type = "finish";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initListView(rootView);
        return rootView;
    }

    public void initListView(View rootView) {

        context = DummyApplication.getAppContext();
        emptyView = rootView.findViewById(R.id.empty_list);
        emptyMessage = (TextView) emptyView.findViewById(R.id.emptyMessage);
        progressBar = (ProgressBar) emptyView.findViewById(R.id.loadingImg);
        if (!Utils.isOnline()) {
            emptyMessage.setText(DummyApplication.getAppContext().getString(R.string.noConnection));
        } else {
            emptyMessage.setText(DummyApplication.getAppContext().getString(R.string.no_report_found));
        }

        this.mListView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        mListView.setEmptyView(emptyView);
        getDummyData();


    }

    private void getDummyData(){

        String url = Constants.API_GET_REPORT + type;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                List<ReportInfo> classrooms = ParseUtils.parseReportFromJSON(result);
                mRandomData = classrooms;
                if (classrooms.size() > 0) {
                    progressBar.setVisibility(View.GONE);
                    emptyMessage.setVisibility(View.INVISIBLE);
                    setAdapter();
                } else {
                    progressBar.setVisibility(View.GONE);
                    emptyMessage.setVisibility(View.VISIBLE);
                }

            }
        });
        //sender.start(url, new RequestSender.IRequestSenderComplete() {
        //String response = "[{'className': '103','classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '104','classId' : 124,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '105','classId' : 125,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '106',	'classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'}]";



    }

    public void setAdapter()
    {

        this.mAdapter = new ListRoomAdapter(context,mRandomData, type);
        this.mListView.setAdapter(mAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReportInfo report = mRandomData.get(i);
                openMap(report);
            }
        });
    }


    private void openMap(ReportInfo reportInfo) {
        Intent intent = new Intent(context, ResolveReportActivity.class);
        intent.putExtra("report", reportInfo);
        intent.putExtra("type", type);
        startActivityForResult(intent, 1);
    }
}
