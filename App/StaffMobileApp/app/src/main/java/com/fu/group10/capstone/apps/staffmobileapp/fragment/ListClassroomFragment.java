package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.adapter.GridViewAdapter;
import com.fu.group10.capstone.apps.staffmobileapp.model.ClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 8/4/2015.
 */
public class ListClassroomFragment extends Fragment {


    private GridView gridView;
    private GridViewAdapter mAdapter;
    private List<ClassInfo> items = new ArrayList<>();
    private int floor = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_room, container, false);
        initView(rootView);
        return rootView;
    }

    public void setParams(List<ClassInfo> items) {
        this.items = items;
    }


    public void initView(View rootView) {
        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        mAdapter = new GridViewAdapter(getActivity(),items);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


}
