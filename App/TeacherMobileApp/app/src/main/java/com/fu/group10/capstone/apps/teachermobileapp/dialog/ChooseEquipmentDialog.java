package com.fu.group10.capstone.apps.teachermobileapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;


/**
 * Created by QuangTV on 6/28/2015.
 */
public class ChooseEquipmentDialog extends DialogFragment {
    private View rootView;
    private String equipmentId;
    private AlertDialog alertDialog;
    private String reportId;
    Equipment equipment;
    OnMsgEnteredListener onMsgEnteredListener;
    Activity activity;
    String position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_choose_equipment, null);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerItem);
        final EditText txtResolve = (EditText) rootView.findViewById(R.id.txtDamaged);
        final TextView txtDetail = (TextView) rootView.findViewById(R.id.txtDetail);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItemPosition() == 1) {
                    txtDetail.setVisibility(View.VISIBLE);
                    txtResolve.setVisibility(View.VISIBLE);
                } else {
                    txtDetail.setVisibility(View.GONE);
                    txtResolve.setVisibility(View.GONE);
                }
                equipment.setDamageLevel(2 - spinner.getSelectedItemPosition() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                equipment.setEvaluate(resolveMsg);
            }
        });

        builder.setView(rootView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //JsInterface.addEquipment(equipment.getEquipmentName(), Constants.getPosition(equipment.getEquipmentName()));
//                if (equipment.getPosition().equalsIgnoreCase("")) {
//                    equipment.setPosition(Constants.getPosition(equipment.getEquipmentName()));
//                }

                equipment.setIsReport(true);
                onMsgEnteredListener.onMsgEnteredListener(equipment);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog  = builder.create();
        alertDialog.setTitle("Mô tả hư hại");

        return  alertDialog;
    }

    public void setParams(Activity activity, Equipment equipment, OnMsgEnteredListener cb) {
        this.activity = activity;
        this.equipment = equipment;

        this.onMsgEnteredListener = cb;
    }

    public interface OnMsgEnteredListener {
        void onMsgEnteredListener(Equipment equipment);
    }





}
