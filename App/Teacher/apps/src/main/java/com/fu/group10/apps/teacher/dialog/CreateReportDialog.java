package com.fu.group10.apps.teacher.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.ListRoomActivity;
import com.fu.group10.apps.teacher.model.Result;
import com.fu.group10.apps.teacher.utils.Constants;
import com.fu.group10.apps.teacher.utils.ParseUtils;
import com.fu.group10.apps.teacher.utils.RequestSender;

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
    String username;
    int classId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.wait_dialog_layout, null);

        spinner = (Spinner) rootView.findViewById(R.id.spinnerItem);

        builder.setView(rootView);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    public void setParams(Activity activity,String username, int roomId, String listDamaged, String listPosition, String listEvaluate, String listDescription) {
        this.activity = activity;
        this.listDamaged = listDamaged;
        this.listEvaluate = listEvaluate;
        this.listDescription = listDescription;
        this.username = username;
        this.classId = roomId;
        this.listPosition = listPosition;
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


        RequestSender sender = new RequestSender();
        sender.method ="POST";
        sender.param = params;


        String url = Constants.API_CREATE_REPORT;
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Result result1 = ParseUtils.parseResult(result);
                if(result1.getError_code() == 100) {
                    if (evaluate.equalsIgnoreCase("Cần phải đổi phòng")) {
                        sendSMS(evaluate, username, classId + "");
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Create Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DummyApplication.getAppContext(), ListRoomActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), result1.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sendSMS(String name, String username, String classId) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+84909969448", null, username + " can doi phong hoc!", null, null);
        Toast.makeText(getActivity().getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
    }
}
