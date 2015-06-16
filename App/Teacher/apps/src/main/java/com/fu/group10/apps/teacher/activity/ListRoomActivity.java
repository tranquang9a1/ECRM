package com.fu.group10.apps.teacher.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.adapter.SampleAdapter;
import com.fu.group10.apps.teacher.model.ClassroomInfo;
import com.fu.group10.apps.teacher.utils.Constants;
import com.fu.group10.apps.teacher.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class ListRoomActivity extends ActionBarActivity {

    private ListView mListView;
    private SampleAdapter mAdapter;
    private List<ClassroomInfo> mRandomData = new ArrayList<ClassroomInfo>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);

       // getActionBar().setElevation(4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getDummyData();

        this.mListView = (ListView) findViewById(R.id.lv_sample_list);
        this.mAdapter = new SampleAdapter(this,mRandomData);
        this.mListView.setAdapter(mAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = mRandomData.get(i).getClassid();
                Intent intent = new Intent(ListRoomActivity.this, TestActivity.class);
                intent.putExtra("classId", id);
                startActivity(intent);
            }
        });


    }

    private void getDummyData(){

        //String response = Constants.API_CREATE_REPORT + username;
        String response = "[{'className': '103','classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '104','classId' : 124,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '105','classId' : 125,'timeFrom' : '18:40','timeTo' : '19:40'},{'className': '106',	'classId' : 123,'timeFrom' : '18:40','timeTo' : '19:40'}]";
        List<ClassroomInfo> result = ParseUtils.parseClassroom(response);

        this.mRandomData = result;
    }
}