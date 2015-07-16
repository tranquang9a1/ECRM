package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDetailDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;

import java.util.List;


/**
 * Created by QuangTV on 7/13/2015.
 */
public class CreateReportDialogOffline extends DialogFragment {

    Spinner spinner;
    Activity activity;
    private AlertDialog alertDialog;
    private View rootView;
    String className;
    String username;
    List<Equipment> listDamaged;
    int classId;
    DatabaseHelper db;
    ReportSuccessDialog reportSuccessDialog;

    Context context;
    ProgressDialog progress;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_create_report, null);
        context = rootView.getContext();
        db = new DatabaseHelper(activity.getApplicationContext());
        spinner = (Spinner) rootView.findViewById(R.id.spinnerItem);

        builder.setView(rootView);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progress = ProgressDialog.show(context, "", "Đang gửi báo cáo", true);
                String evaluate = spinner.getSelectedItem().toString();
                create(evaluate);

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog  = builder.create();

        alertDialog.setTitle(getString(R.string.sendreport_title));
        return  alertDialog;
    }

    public void setParams(Activity activity,String username, int roomId, String className, List<Equipment> listDamaged ) {
        this.activity = activity;
        this.listDamaged = listDamaged;
        this.className = className;
        this.username = username;
        this.classId = roomId;
    }

    public void create(String evaluate){
        ReportDAO report = new ReportDAO();
        report.setCreateTime(System.currentTimeMillis() + "");
        report.setEvaluate(evaluate);
        report.setClassId(classId);
        report.setClassName(className);
        report.setChangedRoom(null);
        report.setDamageLevel(0);
        report.setReportId(db.getMaxReportId() + 1);
        report.setStatus(1);
        report.setUsername(username);
        for (int i = 0; i < listDamaged.size(); i++) {
            Equipment equipment = listDamaged.get(i);
            db.updateEquipment(equipment, classId);
            ReportDetailDAO detail = new ReportDetailDAO(equipment.getEquipmentName(), equipment.getEvaluate(), equipment.getDamageLevel(), false);
            db.insertReportDetail(report.getReportId(), detail, false);
        }


        db.insertReport(report, false);
        progress.dismiss();
        openMain();
    }

    public void openMain() {
        reportSuccessDialog = new ReportSuccessDialog();
        reportSuccessDialog.setParams(activity, username);
        reportSuccessDialog.show(activity.getFragmentManager(), "Show Success");
        activity.setResult(Activity.RESULT_OK);
    }
}
