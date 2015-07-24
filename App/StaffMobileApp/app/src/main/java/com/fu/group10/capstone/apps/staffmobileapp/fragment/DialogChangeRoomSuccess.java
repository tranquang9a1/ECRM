package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.activity.ListRoomActivity;

/**
 * Created by QuangTV on 7/6/2015.
 */
public class DialogChangeRoomSuccess extends DialogFragment {

    private View rootView;
    private AlertDialog alertDialog;
    Activity activity;
    TextView txtFrom;
    TextView txtTo;
    String from;
    String to;
    String username;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_change_room_success, null);

        txtFrom = (TextView) rootView.findViewById(R.id.txtFrom);
        txtTo = (TextView) rootView.findViewById(R.id.txtTo);

        txtFrom.setText(from);
        txtTo.setText(to);
        builder.setView(rootView);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openMain();
            }
        });
        alertDialog  = builder.create();

        alertDialog.setTitle(getString(R.string.resolve_title));
        return  alertDialog;
    }

    public void setParams(Activity activity,String from, String to, String username) {
        this.activity = activity;
        this.from = from;
        this.to = to;
        this.username = username;
    }

    public void openMain() {
        Intent  intent = new Intent(activity, ListRoomActivity.class);
        intent.putExtra("username", username);
        activity.setResult(2);

        startActivity(intent);
        activity.finish();
    }
}
