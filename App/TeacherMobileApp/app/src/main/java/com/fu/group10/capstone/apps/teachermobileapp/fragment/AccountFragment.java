package com.fu.group10.capstone.apps.teachermobileapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.ReportDetailActivity;
import com.fu.group10.capstone.apps.teachermobileapp.adapter.ListRoomAdapter;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.RemoveReportDialog;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 6/2/2015.
 */
public class AccountFragment extends Fragment{

    private ListView mListView;
    private ListRoomAdapter mAdapter;
    private String username;
    private List<ReportInfo> mRandomData = new ArrayList<ReportInfo>();
    private View emptyView;
    private Context context;
    private ProgressBar progressBar;
    private TextView emptyMessage;
    private int OFFSET = 0;
    private int LIMIT = 5;
    boolean isOnline = true;
    DatabaseHelper db;
    RemoveReportDialog removeReportDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        isOnline = Utils.isOnline();
        db = new DatabaseHelper(getActivity().getApplicationContext());
        initListView(rootView);
        return rootView;
    }

    public void initListView(View rootView) {

        username = getActivity().getIntent().getExtras().getString("username");
        context = DummyApplication.getAppContext();
        emptyView = rootView.findViewById(R.id.empty_list);
        emptyMessage = (TextView) emptyView.findViewById(R.id.emptyMessage);
        progressBar = (ProgressBar) emptyView.findViewById(R.id.loadingImg);
        this.mListView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        mListView.setEmptyView(emptyView);
        getDummyData();


    }

    private void getDummyData(){

        if (isOnline) {
            String url = Constants.API_GET_REPORT_BY_USERNAME + username + "&offset=" + OFFSET + "&limit=" + LIMIT;
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
        } else {
            mRandomData = db.getReport();
            if (mRandomData.size() > 0) {
                progressBar.setVisibility(View.GONE);
                emptyMessage.setVisibility(View.INVISIBLE);
                setAdapter();
            } else {
                progressBar.setVisibility(View.GONE);
                emptyMessage.setVisibility(View.VISIBLE);
            }
        }

        //sender.start(url, new RequestSender.IRequestSenderComplete() {
        //String response = "[{'className': '103','classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '104','classId' : 124,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '105','classId' : 125,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '106',	'classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'}]";



    }

    public void setAdapter()
    {

        this.mAdapter = new ListRoomAdapter(context,mRandomData);
        this.mListView.setAdapter(mAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openMap(mRandomData.get(i));

                }

        });
    }


    private void openMap(ReportInfo reportInfo) {
        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
        intent.putExtra("report", reportInfo);
        startActivity(intent);
    }


}
