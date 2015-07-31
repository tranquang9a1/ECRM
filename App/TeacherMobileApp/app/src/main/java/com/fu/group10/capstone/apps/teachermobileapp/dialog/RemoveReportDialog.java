package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.ListRoomActivity;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;


/**
 * Created by QuangTV on 7/8/2015.
 */
public class RemoveReportDialog extends DialogFragment {

    String reportId;
    AlertDialog dialog;
    Activity activity;
    private View rootView;
    String username;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_report_detail, null);



        builder.setView(rootView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog  = builder.create();

        dialog.setTitle(getString(R.string.remove_title));
        return  dialog;
    }

    public void setParams(Activity activity, String reportId, String username) {
        this.reportId = reportId;
        this.activity = activity;
        this.username = username;
    }

    public void startMain() {
        Intent intent = new Intent(activity, ListRoomActivity.class);
        activity.setResult(3);
        intent.putExtra("username", username);
        activity.startActivity(intent);
        activity.finish();

    }

    public void remove() {
        //startMain();
        String url = Constants.API_REMOVE_REPORT + reportId + "&username="+username;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                if (result.equalsIgnoreCase("true")) {
                    startMain();
                } else {
                    Toast.makeText(activity, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
