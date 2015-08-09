package com.fu.group10.capstone.apps.staffmobileapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.DialogUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.adapter.ResolveAdapter;
import com.fu.group10.capstone.apps.staffmobileapp.component.InfinityListView;
import com.fu.group10.capstone.apps.staffmobileapp.fragment.ChooseRoomDialogFragment;
import com.fu.group10.capstone.apps.staffmobileapp.fragment.ResolveDialogFragment;
import com.fu.group10.capstone.apps.staffmobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.staffmobileapp.model.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    TextView txtSuggest;
    TextView txtStatus;
    TextView txtStatusDes;

    Button btnChooseRoom;
    Button btnViewMap;
    Button btnResolve;
    Button btnViewMapFull;
    private String resolveMsg;
    String[] items;
    String current;
    ProgressDialog progress;
    String type;
    SimpleDateFormat df = new SimpleDateFormat("HH:mm - dd/MM");

    private ChooseRoomDialogFragment chooseRoomDialog;
    private ResolveDialogFragment resolveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_report);
        initView();


    }

    private void initView() {

        progress = ProgressDialog.show(this, "", "Đang tải thông tin báo cáo", true);
        txtClassroom = (TextView) findViewById(R.id.txtClassroom);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDamageLevel = (TextView) findViewById(R.id.txtDamageLevel);
        txtEvaluate = (TextView) findViewById(R.id.txtEvaluate);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtSuggest = (TextView) findViewById(R.id.txtSuggest);
        btnChooseRoom = (Button) findViewById(R.id.resolve_button);
        btnResolve = (Button) findViewById(R.id.view_map_button);
        btnViewMapFull = (Button) findViewById(R.id.view_map_button1);
        txtStatusDes = (TextView) findViewById(R.id.textView17);

        final ReportInfo report = getIntent().getParcelableExtra("report");
        type  = getIntent().getExtras().getString("type");

        txtClassroom.setText(report.getRoomName());
        try {
            txtTime.setText(df.parse(report.getTimeReport()) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtDamageLevel.setText(report.getDamageLevel() + "");





        txtEvaluate.setText(report.getEvaluate());
        txtUser.setText(report.getUserReport());
        listView = (InfinityListView) findViewById(R.id.listView);
        ResolveAdapter adapter = new ResolveAdapter(getApplicationContext(), report.getEquipments());
        listView.setAdapter(adapter);
        checkSchedule(report.getRoomId()+"");
        current = report.getRoomName();
        if (report.getDamageLevel() > 30) {
            txtSuggest.setText("Đổi phòng");
        } else {
            txtSuggest.setText("Tiếp tục học");
        }
        setSuggest(report);
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




        btnChooseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                getAvailableRoom(report.getRoomId()+ "");
            }
        });

        btnResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressResolve();
                resolve(report.getRoomId() + "");
            }
        });

        btnViewMapFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap(report.getRoomId());
            }
        });
    }

    private void openMap(int id) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);


    }

    private void resolve(String roomId) {
        String url = Constants.API_RESOLVE_REPORT + roomId;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                Result res = ParseUtils.parseResult(result);
                progress.dismiss();
                if (res.getError_code() == 100) {
                    openHome();
                } else {
                    Toast.makeText(ResolveReportActivity.this, "Có lỗi xảy ra" + res.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void showProgress() {
        progress = ProgressDialog.show(this, "", Constants.FIND_ROOM, true);
    }
    public void progressResolve() {
        progress = ProgressDialog.show(this, "", "Đang khắc phục", true);
    }
    public void openHome() {
        DialogUtils.showAlert(this, "Khắc phục phòng thành công", new DialogUtils.IOnOkClicked() {
            @Override
            public void onClick() {
                Intent intent = new Intent(ResolveReportActivity.this, ListRoomActivity.class);
                setResult(2);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getAvailableRoom(String roomId) {
        String url = Constants.API_GET_AVAILABLE_ROOM + roomId;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                items = ParseUtils.parseListRoom(result);
                progress.dismiss();
                if (items != null) {
                    chooseRoomDialog = new ChooseRoomDialogFragment();
                    chooseRoomDialog.setParam(ResolveReportActivity.this, items, current, "report", new ChooseRoomDialogFragment.IChangeRoomComplete() {
                        @Override
                        public void onRequestComplete(boolean result) {

                        }
                    });
                    chooseRoomDialog.show(getFragmentManager(), "ChooseRoom");
                } else {
                    DialogUtils.showAlert(ResolveReportActivity.this, Constants.NO_ROOM_MESSAGE);
                }

            }
        });
    }

    public void checkSchedule(String roomId) {
        String url = Constants.API_CHECK_SCHEDULE + roomId;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                if (result.equalsIgnoreCase("false")) {
                    if (type.equalsIgnoreCase("finish") || type.equalsIgnoreCase("removed")) {
                        btnChooseRoom.setVisibility(View.GONE);
                        btnResolve.setVisibility(View.GONE);
                    } else {
                        btnChooseRoom.setVisibility(View.GONE);
                        btnResolve.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (type.equalsIgnoreCase("new") || type.equalsIgnoreCase("going")) {
                        btnChooseRoom.setVisibility(View.VISIBLE);
                        btnResolve.setVisibility(View.VISIBLE);
                    }  else {
                        btnChooseRoom.setVisibility(View.GONE);
                        btnResolve.setVisibility(View.GONE);
                    }

                }
                progress.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


    public void setSuggest(ReportInfo report) {
        if (type.equalsIgnoreCase("new") || type.equalsIgnoreCase("going")) {
            txtStatus.setVisibility(View.GONE);
            txtStatus.setText("Đổi phòng sang " + report.getChangedRoom());
            txtStatus.setVisibility(View.VISIBLE);
            txtStatusDes.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase("finish")) {
            txtStatus.setText("Phòng đã được sửa");
            txtStatus.setVisibility(View.VISIBLE);
            txtStatusDes.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase("going")) {

        } else {
            txtStatus.setText("Báo cáo đã bị xóa");
            txtStatus.setVisibility(View.VISIBLE);
            txtStatusDes.setVisibility(View.VISIBLE);
        }
    }
}
