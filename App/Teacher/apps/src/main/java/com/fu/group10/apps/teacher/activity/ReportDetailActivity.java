package com.fu.group10.apps.teacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.adapter.DamagedEquipmentAdapter;
import com.fu.group10.apps.teacher.adapter.ReportDetailAdapter;
import com.fu.group10.apps.teacher.model.*;
import com.fu.group10.apps.teacher.utils.InfinityListView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class ReportDetailActivity extends Activity{

    TextView txtClassroom;
    TextView txtTime;
    TextView txtDamageLevel;
    TextView txtEvaluate;
    InfinityListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);


        listView = (InfinityListView) findViewById(R.id.listView);

        ReportInfo report = getIntent().getParcelableExtra("report");

        txtClassroom = (TextView) findViewById(R.id.txtClassroom);
        txtDamageLevel = (TextView) findViewById(R.id.txtDamageLevel);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtEvaluate = (TextView) findViewById(R.id.txtEvaluate);

        txtClassroom.setText(report.getClassId());
        txtDamageLevel.setText(report.getDamageLevel() + "");
        Date time = new Date(Long.parseLong(report.getCreateTime()) / 1000 /** - Constants.TIME_ZONE_DIFF * 1000 **/);
        SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");
        txtTime.setText(dateFormater.format(time));

        txtEvaluate.setText(report.getEvaluate());
        List<EquipmentReportInfo> reportInfoList = report.getEquipments();
        List<EquipmentReport> equipmentInfos = new ArrayList<EquipmentReport>();

        equipmentInfos = count(reportInfoList);
        final ReportDetailAdapter adapter = new ReportDetailAdapter(DummyApplication.getAppContext(), equipmentInfos);


        listView.setAdapter(adapter);



    }


    public List<EquipmentReport> count(List<EquipmentReportInfo> equipmentList) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (int i = 0; i < equipmentList.size(); i++) {
            EquipmentReportInfo damagedEquipment = equipmentList.get(i);
            if (result.get(damagedEquipment.getEquipmentName()) == null) {
                result.put(damagedEquipment.getEquipmentName(), 1);
            } else {
                result.put(damagedEquipment.getEquipmentName(), result.get(damagedEquipment.getEquipmentName()) + 1);
            }
        }


        List<EquipmentReport> af = new ArrayList<EquipmentReport>();
        Set<String> keys = result.keySet();
        for (String key : keys) {
            for (int i = 0; i < equipmentList.size(); i++) {
                EquipmentReportInfo damagedEquipment = equipmentList.get(i);
                if (damagedEquipment.getEquipmentName().equalsIgnoreCase(key)) {
                    af.add(new EquipmentReport(key, result.get(key), damagedEquipment.getDamaged(), damagedEquipment.getSolution(), damagedEquipment.isStatus()));

                }

            }

        }

        return af;
    }
}
