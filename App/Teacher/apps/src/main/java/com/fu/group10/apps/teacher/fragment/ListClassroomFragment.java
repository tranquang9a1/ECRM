package com.fu.group10.apps.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.MapActivity;
import com.fu.group10.apps.teacher.adapter.AccountAdapter;
import com.fu.group10.apps.teacher.adapter.ClassroomAdapter;
import com.fu.group10.apps.teacher.model.ClassroomInfo;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.InfinityListView;
import com.fu.group10.apps.teacher.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/11/2015.
 */
public class ListClassroomFragment extends Fragment{
    InfinityListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_classroom, container, false);
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


        ArrayList<ClassroomInfo> classroomInfos = getActivity().getIntent().getParcelableArrayListExtra("listClass");
        final List<ClassroomInfo> items = classroomInfos;


        final ClassroomAdapter adapter = new ClassroomAdapter(DummyApplication.getAppContext(), items);


        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int classId = items.get(position).getClassid();
                openMap(classId);
            }
        });


    }

    private void openMap(int id) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("roomId", id+"");
        intent.putExtra("username", getActivity().getIntent().getExtras().getString("username"));
        startActivity(intent);
    }
}
