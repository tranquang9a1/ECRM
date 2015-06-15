package com.fu.group10.apps.staff.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.adapter.ResolveAdapter;
import com.fu.group10.apps.staff.component.InfinityListView;
import com.fu.group10.apps.staff.fragment.ChooseRoomDialogFragment;
import com.fu.group10.apps.staff.fragment.ResolveDialogFragment;
import com.fu.group10.apps.staff.model.ReportInfo;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class ResolveReportActivity extends Activity {
    InfinityListView listView;
    TextView txtTime;
    TextView txtClassroom;
    TextView txtDamageLevel;
    TextView txtEvaluate;
    TextView txtUser;
    TextView txtNewRoom;

    Button btnChooseRoom;
    Button btnViewMap;
    Button btnResolve;
    private String resolveMsg;

    private ChooseRoomDialogFragment chooseRoomDialog;
    private ResolveDialogFragment resolveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_report);
        initView();


    }

    private void initView() {

        txtClassroom = (TextView) findViewById(R.id.txtClassroom);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDamageLevel = (TextView) findViewById(R.id.txtDamageLevel);
        txtEvaluate = (TextView) findViewById(R.id.txtEvaluate);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtNewRoom = (TextView) findViewById(R.id.txtNewRoom);

        btnChooseRoom = (Button) findViewById(R.id.choose_room_button);
        btnResolve = (Button) findViewById(R.id.resolve_button);
        btnViewMap = (Button) findViewById(R.id.view_map_button);


        final ReportInfo report = getIntent().getParcelableExtra("report");
        txtClassroom.setText(report.getRoomId()+ "");
        txtTime.setText(report.getTimeReport());
        txtDamageLevel.setText(report.getDamageLevel() + "");
        txtEvaluate.setText(report.getEvaluate());
        txtNewRoom.setText(report.getSystemEvaluate() + "");
        txtUser.setText(report.getUserReport());
        listView = (InfinityListView) findViewById(R.id.listView);
        ResolveAdapter adapter = new ResolveAdapter(getApplicationContext(), report.getEquipments());
        listView.setAdapter(adapter);
        final String[] items = getResources().getStringArray(R.array.list);
        btnChooseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseRoomDialog = new ChooseRoomDialogFragment();
                chooseRoomDialog.setParam(items, new ChooseRoomDialogFragment.OnListChooseListener() {
                    @Override
                    public void onListChoose(String name) {
                        txtNewRoom.setText(name + "");
                    }
                });
                chooseRoomDialog.show(getFragmentManager(), "Choose Room");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int equipmentId = report.getEquipments().get(position).getEquipmentId();
                int reportId = report.getEquipments().get(position).getReportId();
                resolveDialog = new ResolveDialogFragment();
                resolveDialog.setParams(ResolveReportActivity.this, equipmentId + "", reportId + "", new ResolveDialogFragment.OnMsgEnteredListener() {
                    @Override
                    public void onMsgEnteredListener(String newName) {
                        resolveMsg = newName == null ? "" : newName;
                    }
                });


                resolveDialog.show(getFragmentManager(), "Enter Message");
            }
        });

        btnResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveDialog = new ResolveDialogFragment();
                resolveDialog.setParams(ResolveReportActivity.this,null, null, new ResolveDialogFragment.OnMsgEnteredListener() {
                    @Override
                    public void onMsgEnteredListener(String newName) {
                        resolveMsg = newName == null ? "" : newName;
                    }
                });

                resolveDialog.show(getFragmentManager(), "Resolve all");
            }
        });

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMap(report.getRoomId());
            }
        });

    }

    private void openMap(int id) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);


    }
}
