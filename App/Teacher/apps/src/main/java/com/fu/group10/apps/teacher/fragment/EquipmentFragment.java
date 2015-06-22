package com.fu.group10.apps.teacher.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fu.group10.apps.teacher.DummyApplication;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.ReportDetailActivity;
import com.fu.group10.apps.teacher.adapter.AccountAdapter;
import com.fu.group10.apps.teacher.adapter.EquipmentAdapter;
import com.fu.group10.apps.teacher.dialog.CreateReportDialog;
import com.fu.group10.apps.teacher.model.DamagedEquipment;
import com.fu.group10.apps.teacher.model.Equipment;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class EquipmentFragment extends Fragment {

    ListView listView;
    ArrayList<String> items;
    List<Equipment> list = new ArrayList<Equipment>();
    EquipmentAdapter adapter;
    Button btnSubmit;
    CreateReportDialog createReportDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        initListView(rootView);

        return rootView;
    }

    private void initListView(View rootView) {
        Log.d("demo", "Load list view");
        listView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        String[] equipments;
        final int classId = getActivity().getIntent().getExtras().getInt("classId");
        final String username = "KhanhKT";
        getData(classId);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<DamagedEquipment> equipmentList = adapter.getListDamage();
                if (equipmentList.size() == 0 ) {
                    Toast.makeText(DummyApplication.getAppContext(), "Bạn phải chọn ít nhất 1 thiết bị", Toast.LENGTH_LONG).show();
                } else {


                    String listDamaged = "";
                    String listEvaluate = "";
                    String listDescription = "";
                    String listPosition = "";
                    for (int i = 0; i < equipmentList.size(); i++) {
                        listDamaged += equipmentList.get(i).getName() + ",";
                        listPosition += equipmentList.get(i).getPosition() + ",";
                    }


                    Map<Integer, Boolean> valueChoose = adapter.getRowChosse();
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (valueChoose.get(i) != null) {
                            View view1 = listView.getChildAt(i);
                            Spinner spinner1 = (Spinner) view1.findViewById(R.id.spinnerItem);
                            if (spinner1 != null) {
                                if (spinner1.getVisibility() == View.VISIBLE) {
                                    if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Nặng")) {
                                        listEvaluate += "1,";
                                    } else if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Trung Bình")) {
                                        listEvaluate += "2,";
                                    } else {
                                        listEvaluate += "3,";
                                    }
                                }
                            }


                            EditText description = (EditText) view1.findViewById(R.id.txtDamaged);
                            if (description != null) {
                                if (description.getVisibility() == View.VISIBLE) {
                                    listDescription += description.getText().toString() + ",";
                                }
                            }
                        }

                    }
                    final String damaged = listDamaged.substring(0, listDamaged.length() - 1);
                    final String position = listPosition.substring(0, listPosition.length() - 1);
                    final String evaluate = listEvaluate.substring(0, listEvaluate.length() > 0 ? listEvaluate.length() - 1 : 0);
                    final String description = listDescription.substring(0, listDescription.length() > 0 ? listDescription.length() - 1 : 0);
                    createReportDialog = new CreateReportDialog();
                    createReportDialog.setParams(getActivity(),username, classId, damaged, position, evaluate, description);
                    createReportDialog.show(getActivity().getFragmentManager(), "haha");

                }
            }
        });
    }

    public void getData(int classId) {
        //String response = "['Loa','Quạt','Máy Lạnh', 'Máy Chiếu', 'Tivi', 'Ghế', 'Bàn']";
        String url = Constants.API_GET_EQUIPMENT + classId;


        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                String[] equipmentJson = ParseUtils.parseEquipmentJson(result);
                List<String> re = new ArrayList<String>(Arrays.asList(equipmentJson));
                items =(ArrayList<String>) re;

                adapter = new EquipmentAdapter(DummyApplication.getAppContext(), items);
                listView.setAdapter(adapter);
            }
        });

    }


}
