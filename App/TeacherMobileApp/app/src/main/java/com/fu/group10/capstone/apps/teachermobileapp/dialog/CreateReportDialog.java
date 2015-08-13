package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.ListRoomActivity;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class CreateReportDialog extends DialogFragment {

    Spinner spinner;
    Activity activity;
    private AlertDialog alertDialog;
    private View rootView;
    String listEvaluate;
    String listDamaged;
    String listPosition;
    String listDescription;
    int reportId;
    String username;
    String className;
    int classId;
    String type;
    ReportSuccessDialog reportSuccessDialog;

    Context context;
    ProgressDialog progress;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.wait_dialog_layout, null);
        context = rootView.getContext();
        spinner = (Spinner) rootView.findViewById(R.id.spinnerItem);

        builder.setView(rootView);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progress = ProgressDialog.show(context, "", "Đang gửi báo cáo", true);
                String evaluate = spinner.getSelectedItem().toString();
                if (type.equalsIgnoreCase("create")) {
                    create(evaluate);
                } else {
                    update(evaluate);
                }


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

    public void setParams(Activity activity,String username, int roomId, String className, String listDamaged, String listPosition, String listEvaluate, String listDescription, String type) {
        this.activity = activity;
        this.listDamaged = listDamaged;
        this.listEvaluate = listEvaluate;
        this.listDescription = listDescription;
        this.username = username;
        this.className = className;
        this.classId = roomId;
        this.listPosition = listPosition;
        this.type = type;
    }

    public void setParams(Activity activity,String username, int reportId, int roomId, String className, String listDamaged, String listPosition, String listEvaluate, String listDescription, String type) {
        this.activity = activity;
        this.listDamaged = listDamaged;
        this.listEvaluate = listEvaluate;
        this.listDescription = listDescription;
        this.username = username;
        this.className = className;
        this.classId = roomId;
        this.reportId = reportId;
        this.listPosition = listPosition;
        this.type = type;
    }






    public void create(final String evaluate) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("listDamaged", listDamaged);
        params.put("listEvaluate", listEvaluate);
        params.put("listDescription", listDescription);
        params.put("listPosition", listPosition);
        params.put("evaluate", evaluate);
        params.put("classId", classId + "");
        params.put("username", username);
        params.put("createTime", "1");

        RequestSender sender = new RequestSender();
        sender.method ="POST";
        sender.param = params;


        String url = Constants.API_CREATE_REPORT;
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Result result1 = ParseUtils.parseResult(result);
                progress.dismiss();
                if(result1.getError_code() == 100) {
                    String msg = username + " vừa báo cáo hư hại phòng học "+ className ;
                    if (evaluate.equalsIgnoreCase("Phải đổi phòng")) {
                        sendSMS(msg);
                    }
                    sendNotification(msg);

                   openMain();

                } else {
                    Toast.makeText(context,"Có lỗi xảy ra!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void update(final String evaluate) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("listDamaged", listDamaged);
        params.put("listEvaluate", listEvaluate);
        params.put("listDescription", listDescription);
        params.put("listPosition", listPosition);
        params.put("evaluate", evaluate);
        params.put("classId", classId + "");
        params.put("username", username);
        params.put("reportId", reportId + "");


        RequestSender sender = new RequestSender();
        sender.method ="POST";
        sender.param = params;


        String url = Constants.API_UPDATE_REPORT;
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Log.d("Create report", result);
                Result result1 = ParseUtils.parseResult(result);
                progress.dismiss();
                if(result1.getError_code() == 100) {
                    String msg = "Giáo viên" + username + " vừa báo cáo hư hại phòng học "+ classId ;
                    if (evaluate.equalsIgnoreCase("Phải đổi phòng")) {
                        sendSMS(msg);
                    }
                    sendNotification(msg);

                    openMain();

                } else if (result1.getError_code() == 500) {
                    Toast.makeText(context,result1.getError(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,"Có lỗi xảy ra!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sendSMS(String msg) {
        try {
            String url = Constants.SEND_SMS +URLEncoder.encode(msg, "UTF-8")+"&ListUser=staff";

            final RequestSender sender = new RequestSender();
            sender.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendNotification(String msg) {
        try {
            String url = Constants.SEND_NOTIFICATION + URLEncoder.encode(msg, "UTF-8")+"&ListUser=staff";
            final RequestSender sender = new RequestSender();
            sender.execute(url);
        }catch ( Exception e) {
            e.printStackTrace();
        }

    }

    public void openMain() {
        DialogUtils.showAlert(activity, "Gửi báo cáo thành công! Báo cáo của bạn đã được lưu vào hệ thống", new DialogUtils.IOnOkClicked() {
            @Override
            public void onClick() {
                Intent intent = new Intent(activity, ListRoomActivity.class);
                intent.putExtra("username", username);
                activity.setResult(Activity.RESULT_OK);
                activity.startActivity(intent);
                activity.finish();
            }
        });


    }



}
