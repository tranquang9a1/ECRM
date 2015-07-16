package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.ListRoomActivity;


/**
 * Created by QuangTV on 7/6/2015.
 */
public class ReportSuccessDialog extends DialogFragment {

    AlertDialog dialog;
    Activity activity;
    private View rootView;
    String username;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_report_success, null);



        builder.setView(rootView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startMain();
            }
        });
        dialog  = builder.create();

        dialog.setTitle(getString(R.string.alert));
        return  dialog;
    }

    public void setParams(Activity activity, String username) {
        this.activity = activity;
        this.username = username;
    }

    public void startMain() {
        Intent intent = new Intent(activity, ListRoomActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        activity.finish();
    }
}
