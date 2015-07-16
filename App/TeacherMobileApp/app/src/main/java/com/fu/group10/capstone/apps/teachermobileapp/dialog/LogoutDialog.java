package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.LoginActivity;

/**
 * Created by QuangTV on 7/5/2015.
 */
public class LogoutDialog extends DialogFragment{

    AlertDialog dialog;
    Activity activity;
    private View rootView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_logout, null);



        builder.setView(rootView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getActivity().getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                startMain();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog  = builder.create();

        dialog.setTitle(getString(R.string.action_logout));
        return  dialog;
    }

    public void setParams(Activity activity) {
        this.activity = activity;
    }

    public void startMain() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
