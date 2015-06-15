package com.fu.group10.apps.teacher.fragment;

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
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import android.webkit.WebViewClient;
import com.fu.group10.apps.teacher.activity.CreateReportActivity;
import com.fu.group10.apps.teacher.activity.MainActivity;
import com.fu.group10.apps.teacher.model.DamagedEquipment;
import com.fu.group10.apps.teacher.utils.JsInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class ClassroomFragment extends Fragment {

    private WebView classmap;
    private Button createReport;
    private int id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);

        initView(rootView);

        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(final View rootView) {
        classmap = (WebView) rootView.findViewById(R.id.classmap);
        final JsInterface jsInterface = new JsInterface(getActivity().getApplicationContext());
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.addJavascriptInterface(jsInterface, "Android");
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        refreshMap();

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

    private void refreshMap() {
        classmap.clearCache(true);
        classmap.loadUrl("http://app.megahd.vn/api/capstone/test.html");
    }

    private void openCreateReport(ArrayList<DamagedEquipment> listEquipment) {
        Intent intent = new Intent(getActivity(), CreateReportActivity.class);
        intent.putParcelableArrayListExtra("list", listEquipment);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id = bundle.getInt("id");
            refreshMap();
        }
    }



}
