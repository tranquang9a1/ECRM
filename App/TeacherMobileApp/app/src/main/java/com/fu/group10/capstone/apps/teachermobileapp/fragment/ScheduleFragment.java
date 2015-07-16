package com.fu.group10.capstone.apps.teachermobileapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.TestActivity;
import com.fu.group10.capstone.apps.teachermobileapp.adapter.SampleAdapter;
import com.fu.group10.capstone.apps.teachermobileapp.model.ClassroomInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 7/8/2015.
 */
public class ScheduleFragment extends Fragment {

    private ListView mListView;
    private SampleAdapter mAdapter;
    private TextView emptyMessage;
    ProgressBar progressBar;
    private String username;
    private List<ClassroomInfo> mRandomData = new ArrayList<ClassroomInfo>();
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");
    ProgressDialog progress;
    private boolean flag = true;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);
        flag = Utils.isOnline();
        db = new DatabaseHelper(getActivity().getApplicationContext());
        initListView(rootView);

        return rootView;
    }

    public void initListView(View rootView) {
        emptyMessage = (TextView) rootView.findViewById(R.id.emptyMessage);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loadingImg);
        username = getActivity().getIntent().getExtras().getString("username");
        this.mListView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        getDummyData();
    }



    private void getDummyData(){


        if (flag) {

        String url = Constants.API_SCHEDULE + username;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                List<ClassroomInfo> classrooms = ParseUtils.parseClassroom(result);
               // progress.dismiss();
                mRandomData = classrooms;

                if (classrooms.size() > 0) {
                    emptyMessage.setVisibility(View.GONE);
                    setAdapter();

                } else {
                    emptyMessage.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
        //sender.start(url, new RequestSender.IRequestSenderComplete() {
        //String response = "[{'className': '103','classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '104','classId' : 124,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '105','classId' : 125,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '106',	'classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'}]";

        } else {
            List<ClassroomInfo> classrooms = db.getSchedule();
            // progress.dismiss();
            mRandomData = classrooms;

            if (classrooms.size() > 0) {
                emptyMessage.setVisibility(View.GONE);
                setAdapter();

            } else {
                emptyMessage.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        }


    }

    public void setAdapter()
    {

        this.mAdapter = new SampleAdapter(getActivity(),mRandomData);
        this.mListView.setAdapter(mAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag) {
                    String url = Constants.API_GET_CURRENT_TIME;
                    RequestSender sender = new RequestSender();
                    final int id = mRandomData.get(i).getClassid();
                    final String className = mRandomData.get(i).getClassName();
                    final Long fromTime = Long.parseLong(mRandomData.get(i).getTimeFrom());
                    sender.start(url, new RequestSender.IRequestSenderComplete() {
                        @Override
                        public void onRequestComplete(String result) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String current = dateFormat.format(new Date(Long.parseLong(result)));
                            try {
                                Date date = df.parse("1970-01-01 " + current);
                                if (date.getTime() > fromTime) {
                                    Intent intent = new Intent(getActivity(), TestActivity.class);
                                    intent.putExtra("classId", id);
                                    intent.putExtra("className", className);
                                    intent.putExtra("username", username);
                                    intent.putExtra("type", "create");
                                    startActivityForResult(intent, 1);
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Bạn không thể báo cáo phòng này", Toast.LENGTH_LONG).show();
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }



                        }
                    });

                } else {
                    final int id = mRandomData.get(i).getClassid();
                    final String className = mRandomData.get(i).getClassName();
                    final Long fromTime = Long.parseLong(mRandomData.get(i).getTimeFrom());
                    Long result = System.currentTimeMillis();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String current = dateFormat.format(new Date(result));
                    try {
                        Date date = df.parse("1970-01-01 " + current);
                        if (date.getTime() > fromTime) {
                            Intent intent = new Intent(getActivity(), TestActivity.class);
                            intent.putExtra("classId", id);
                            intent.putExtra("className", className);
                            intent.putExtra("username", username);
                            intent.putExtra("type", "create");
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Bạn không thể báo cáo phòng này", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }



            }
        });


    }





}
