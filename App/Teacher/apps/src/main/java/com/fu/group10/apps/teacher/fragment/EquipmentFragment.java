package com.fu.group10.apps.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.ReportDetailActivity;
import com.fu.group10.apps.teacher.adapter.AccountAdapter;
import com.fu.group10.apps.teacher.adapter.EquipmentAdapter;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.InfinityListView;
import com.fu.group10.apps.teacher.utils.ParseUtils;
import com.fu.group10.apps.teacher.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class EquipmentFragment extends Fragment {

    ListView listView;
    ArrayList<String> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        initListView(rootView);

        return rootView;
    }

    private void initListView(View rootView) {
        Log.d("demo", "Load list view");
        listView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        String[] equipments;
        int id = getActivity().getIntent().getExtras().getInt("classId");
        getData();
        final EquipmentAdapter adapter = new EquipmentAdapter(DummyApplication.getAppContext(), items);
        listView.setAdapter(adapter);

    }

    public void getData() {
        String response = "['Loa','Qu?t','Máy L?nh', 'Máy Chi?u', 'Tivi', 'Gh?', 'Bàn']";
        String[] result = ParseUtils.parseEquipmentJson(response);
        List<String> re = new ArrayList<String>(Arrays.asList(result));
        this.items =(ArrayList<String>) re;
    }
}
