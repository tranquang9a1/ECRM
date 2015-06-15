package com.fu.group10.apps.staff.fragment;

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
import android.widget.Toast;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Constants;
import com.fu.group10.apps.staff.Utils.ParseUtils;
import com.fu.group10.apps.staff.Utils.RequestSender;
import com.fu.group10.apps.staff.activity.MapActivity;
import com.fu.group10.apps.staff.model.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QuangTV on 6/14/2015.
 */
public class ResolveDialogFragment extends DialogFragment {

    private View rootView;
    private String equipmentId;
    private AlertDialog alertDialog;
    private String reportId;
    OnMsgEnteredListener onMsgEnteredListener;
    Activity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_resolve_message, null);

        final EditText txtResolve = (EditText) rootView.findViewById(R.id.txtResolve);
        txtResolve.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String resolveMsg = txtResolve.getText().toString();
                onMsgEnteredListener.onMsgEnteredListener(resolveMsg);
            }
        });

        builder.setView(rootView);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String resolveMsg = txtResolve.getText().toString();

                resolve(reportId, equipmentId, resolveMsg);
                onMsgEnteredListener.onMsgEnteredListener(resolveMsg);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog  = builder.create();

        alertDialog.setTitle(getString(R.string.resolve_title));
        return  alertDialog;
    }

    public void setParams(Activity activity,String equipmentId, String reportId, OnMsgEnteredListener cb) {
        this.activity = activity;
        this.equipmentId = equipmentId;
        this.reportId = reportId;
        this.onMsgEnteredListener = cb;
    }

    public interface OnMsgEnteredListener {
        void onMsgEnteredListener(String newName);
    }

    public void resolve(String reportId, String equipmentId,  String solution) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("reportId", reportId);
        params.put("equipmentId", equipmentId);
        params.put("solution", solution);

        RequestSender sender = new RequestSender();
        sender.method ="POST";
        sender.param = params;


        String url = Constants.API_RESOLVE_REPORT;
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Result result1 = ParseUtils.parseResult(result);
                if(result1.getError_code() == 100) {
                    Toast.makeText(getActivity().getApplicationContext(), "Resolve Successfull", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), result1.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
