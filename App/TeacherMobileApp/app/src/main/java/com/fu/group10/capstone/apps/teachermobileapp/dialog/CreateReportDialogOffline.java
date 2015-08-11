package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDetailDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentQuantity;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DBUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;

import java.util.ArrayList;
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
    List<EquipmentDAO> listCategory = new ArrayList<>();
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
        listCategory = db.getEquipments();
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

        int damageLevel = checkDamageLevel();
        db.insertReport(report, false);
        progress.dismiss();
        final String msg =  username + "-" +classId;
        db.updateDamageLevel(classId, damageLevel);
        if (damageLevel > 50) {
            DialogUtils.showAlert(activity, "Hệ thống đánh giá phòng hư hại nặng! Bạn có muốn gửi " +
                    "tin nhắn đến nhân viên ? Việc này sẽ gây tốn phí!",
                    new DialogUtils.IOnOkClicked() {
                @Override
                public void onClick() {
                    sendSMSOffline(msg);
                    openMain();
                }
            }, new DialogUtils.IOnCancelClicked() {
                @Override
                public void onClick() {
                    openMain();
                }
            });
        }



    }

    public void openMain() {
        reportSuccessDialog = new ReportSuccessDialog();
        reportSuccessDialog.setParams(activity, username);
        reportSuccessDialog.show(activity.getFragmentManager(), "Show Success");
        activity.setResult(Activity.RESULT_OK);
    }

    public void sendSMSOffline(String messsage) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Constants.STAFF_PHONE, null, messsage, null, null);
    }

    public EquipmentQuantity getQuantity(List<EquipmentQuantity> quantities, Equipment equipment) {
        for (EquipmentQuantity entity : quantities) {
            if (entity.getEquipmentName().equalsIgnoreCase(equipment.getEquipmentName())) {
                return entity;
            }
        }
        return null;
    }

    public int checkDamageLevel() {
        int damageLevel = db.getDamageLevel(classId);
        List<EquipmentQuantity> quantities = db.getQuantity(classId);
        for (int i = 0; i <listDamaged.size(); i++) {
            Equipment equipment = listDamaged.get(i);

            if (!equipment.getEquipmentName().equalsIgnoreCase("Bàn")
                    && !equipment.getEquipmentName().equalsIgnoreCase("Ghế")) {
                EquipmentQuantity equipmentQuantity = getQuantity(quantities, equipment);
                if (!equipmentQuantity.isDeleted() && equipmentQuantity.getPriority() == 3) {
                    if (Integer.parseInt(equipment.getDamageLevel()) == 3) {
                        damageLevel += 20;
                    } else if (Integer.parseInt(equipment.getDamageLevel()) == 2){
                        damageLevel += 30;
                    } else {
                        damageLevel += 50;
                    }
                } else if (!equipmentQuantity.isDeleted() && equipmentQuantity.getPriority() == 2) {
                    if (Integer.parseInt(equipment.getDamageLevel()) == 3) {
                        damageLevel += 10;
                    } else if (Integer.parseInt(equipment.getDamageLevel()) == 2){
                        damageLevel += 20;
                    } else {
                        damageLevel += 30;
                    }
                } else {
                    if (Integer.parseInt(equipment.getDamageLevel()) == 3) {
                        damageLevel += 5;
                    } else if (Integer.parseInt(equipment.getDamageLevel()) == 2){
                        damageLevel += 10;
                    } else {
                        damageLevel += 15;
                    }
                }
            }

            if (equipment.getEquipmentName().equalsIgnoreCase("Bàn")) {
                if (Integer.parseInt(equipment.getDamageLevel()) == 3) {
                    damageLevel += 2;
                } else if (Integer.parseInt(equipment.getDamageLevel()) == 2){
                    damageLevel += 3;
                } else {
                    damageLevel += 5;
                }
            }

            if (equipment.getEquipmentName().equalsIgnoreCase("Bàn")) {
                if (Integer.parseInt(equipment.getDamageLevel()) == 3) {
                    damageLevel += 1;
                } else if (Integer.parseInt(equipment.getDamageLevel()) == 2){
                    damageLevel += 2;
                } else {
                    damageLevel += 3;
                }
            }




        }
        return damageLevel > 100 ? 100 : damageLevel;
    }

    public void removeSMS() {
        String SMS_INBOX_URI = "content://sms/sent";
        Uri SMS_INBOX = Uri.parse(SMS_INBOX_URI);
        Cursor c = activity.getContentResolver().query(SMS_INBOX,
                new String[]{"_id", "thread_id", "address", "person", "date", "body"}, null, null, null);
        //c.moveToFirst();

        if (c != null && c.moveToFirst()) {
            do {
                long id = c.getLong(0);
                long threadId = c.getLong(1);
                String address = c.getString(2);
                String body = c.getString(5);
                String date = c.getString(3);
                Log.e("log>>>", "0>" + c.getString(0) + "1>" + c.getString(1) + "2>" + c.getString(2) + "<-1>" + c.getString(3) + "4>" + c.getString(4) + "5>" + c.getString(5));

                if (address.equals(Constants.STAFF_PHONE)) {
                    // mLogger.logInfo("Deleting SMS with id: " + threadId);
                    int rows = activity.getContentResolver().delete(Uri.parse("content://sms/" + id), "date=?", new String[]{c.getString(4)});
                    Log.e("log>>>", "Delete success......... rows: " + rows);
                    Log.e("log>>>", "Delete success......... body: " + body);
                }
            } while (c.moveToNext());
        }
    }
}
