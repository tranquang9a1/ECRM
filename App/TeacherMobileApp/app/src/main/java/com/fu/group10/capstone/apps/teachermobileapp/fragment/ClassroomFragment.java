package com.fu.group10.capstone.apps.teachermobileapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import android.webkit.WebViewClient;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.CreateReportActivity;
import com.fu.group10.capstone.apps.teachermobileapp.model.DamagedEquipment;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.JsInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 5/30/2015.
 */
public class ClassroomFragment extends Fragment {

    private WebView classmap;
    private Button createReport;
    private int id;
    private int classId;
    private String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);

        initView(rootView);

        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(final View rootView) {
        classmap = (WebView) rootView.findViewById(R.id.classmap);
        final JsInterface jsInterface = new JsInterface(DummyApplication.getAppContext());
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.addJavascriptInterface(jsInterface, "Android");
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        classId = getActivity().getIntent().getExtras().getInt("classId");
        username = getActivity().getIntent().getExtras().getString("username");
        refreshMap(classId);

        createReport = (Button) rootView.findViewById(R.id.create_report_button);
        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DamagedEquipment> equipmentList = jsInterface.getListDamaged();
                if (equipmentList.size() > 0) {
                    ArrayList<DamagedEquipment> listEquipment = new ArrayList<DamagedEquipment>();
                    for (int i = 0; i < equipmentList.size(); i++) {
                        listEquipment.add(equipmentList.get(i));
                    }
                    openCreateReport(listEquipment);
                } else {
                    Toast.makeText(DummyApplication.getAppContext(), "Bạn phải chọn ít nhất 1 thiết bị!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void refreshMap(int classId) {
        classmap.clearCache(true);
        //classmap.loadUrl("http://app.megahd.vn/api/capstone/test.html");
        classmap.loadUrl(Constants.API_LOAD_MAP + classId);
    }

    private void openCreateReport(ArrayList<DamagedEquipment> listEquipment) {
        Intent intent = new Intent(getActivity(), CreateReportActivity.class);
        intent.putParcelableArrayListExtra("list", listEquipment);
        intent.putExtra("classId", classId);
        intent.putExtra("username", username);
        startActivity(intent);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        Bundle bundle = this.getArguments();
//
//        if (bundle != null) {
//            id = bundle.getInt("id");
//            refreshMap(id);
//        }
//    }




}
