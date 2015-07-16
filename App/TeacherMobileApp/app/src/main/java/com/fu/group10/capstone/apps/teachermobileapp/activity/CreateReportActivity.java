package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.adapter.DamagedEquipmentAdapter;
import com.fu.group10.capstone.apps.teachermobileapp.model.DamagedEquipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.InfinityListView;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import java.util.*;

/**
 * Created by QuangTV on 6/7/2015.
 */
public class CreateReportActivity extends Activity {

    InfinityListView listView;
    Button sendReport;
    Spinner spinner;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        db = new DatabaseHelper(getApplicationContext());

        listView = (InfinityListView) findViewById(R.id.listView);
        ArrayList<DamagedEquipment> damagedEquipments = getIntent().getParcelableArrayListExtra("list");
        final String username = getIntent().getExtras().getString("username");
        final int roomId = getIntent().getExtras().getInt("classId");
        final List<DamagedEquipment> items = damagedEquipments;
        final List<EquipmentInfo> item = count(items);
        final DamagedEquipmentAdapter adapter = new DamagedEquipmentAdapter(DummyApplication.getAppContext(), item);


        listView.setAdapter(adapter);
        sendReport = (Button) findViewById(R.id.send_report_button);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("classId", roomId+"");
                params.put("evaluate", spinner.getSelectedItem().toString());
                params.put("listDescription", "");
                String ListPosition = "";
                String ListDamaged = "";
                for (int a = 0; a < items.size(); a++) {
                    ListPosition += items.get(a).getPosition() + "-";


                    ListDamaged += items.get(a).getName() + ",";

                }
                params.put("listPosition", removeEnd(ListPosition));

                String ListEvaluate = "";
                int sizeItems = items.size();
                int listSize = listView.getCount();


                for (int i = 0; i < listView.getCount(); i++) {
                    View view = listView.getChildAt(i);
                    Spinner spinner1 = (Spinner) view.findViewById(R.id.spinnerItem);
                    if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Nặng")) {
                        ListEvaluate += "1,";
                    } else if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Trung Bình")) {
                        ListEvaluate += "2,";
                    } else {
                        ListEvaluate += "3,";
                    }


                }
                params.put("listDamaged", removeEnd(ListDamaged));
                params.put("listEvaluate", removeEnd(ListEvaluate));
                RequestSender sender = new RequestSender();
                sender.method ="POST";
                sender.param = params;

                String url = Constants.API_CREATE_REPORT;
                sender.start(url, new RequestSender.IRequestSenderComplete() {
                    @Override
                    public void onRequestComplete(String result) {
                        Result result1 = ParseUtils.parseResult(result);
                        if(result1.getError_code() == 100) {

                            String msg = username + " đã báo cáo hư hỏng ở phòng " + roomId;
                            sendNotification(msg);
                            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                            intent.putExtra("roomId", roomId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), result1.getError(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


    }

    public String removeEnd(String list) {
        return list.substring(0, list.length() - 1);
    }


    public List<EquipmentInfo> count(List<DamagedEquipment> equipmentList) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (int i = 0; i < equipmentList.size(); i++) {
            DamagedEquipment damagedEquipment = equipmentList.get(i);
            if(result.get(damagedEquipment.getName()) == null) {
                result.put(damagedEquipment.getName(), 1);
            } else {
                result.put(damagedEquipment.getName(), result.get(damagedEquipment.getName()) + 1);
            }
        }
        List<EquipmentInfo> af = new ArrayList<EquipmentInfo>();
        Set<String> keys = result.keySet();
        for (String key : keys) {
            af.add(new EquipmentInfo(key, result.get(key)));
        }
        return af;

    }

    public void sendNotification(String msg) {
        String url = Constants.SEND_NOTIFICATION +msg+"&ListUser=staff";
        final RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Toast.makeText(getApplicationContext(), "Create Successfull", Toast.LENGTH_LONG).show();

            }
        });
    }


}
