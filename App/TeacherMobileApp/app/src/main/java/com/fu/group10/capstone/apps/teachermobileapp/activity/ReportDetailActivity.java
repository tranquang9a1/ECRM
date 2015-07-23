package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.adapter.ReportDetailAdapter;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.RemoveReportDialog;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.InfinityListView;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;

import java.text.SimpleDateFormat;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class ReportDetailActivity extends FragmentActivity {
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


    Button btnEdit;
    Button btnRemove;
    private String resolveMsg;
    String[] items;
    String current;
    ProgressDialog progress;
    String type;
    RemoveReportDialog removeReportDialog;
    SimpleDateFormat dateFormater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormater = new SimpleDateFormat("HH:mm - dd/MM");
        setContentView(R.layout.activity_report_detail);
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
        btnEdit = (Button) findViewById(R.id.view_map_button);
        btnRemove = (Button) findViewById(R.id.resolve_button);
        txtStatusDes = (TextView) findViewById(R.id.textView17);

        final ReportInfo report = getIntent().getParcelableExtra("report");

        txtClassroom.setText(report.getClassName());
        txtTime.setText(dateFormater.format(Long.parseLong(report.getCreateTime())));
        txtDamageLevel.setText(report.getDamageLevel() + "");





        txtEvaluate.setText(report.getEvaluate());
        txtUser.setText(report.getUsername());
        listView = (InfinityListView) findViewById(R.id.listView);
        ReportDetailAdapter adapter = new ReportDetailAdapter(getApplicationContext(), report.getEquipments());
        listView.setAdapter(adapter);
        progress.dismiss();





        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRemove(report.getReportId(), report.getUsername());
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(report);
            }
        });


    }

    private void btnRemove(final int id, final String username) {
        DialogUtils.showAlert(this, "Bạn có chắc chắn muốn xóa báo cáo này ?", new DialogUtils.IOnOkClicked() {
            @Override
            public void onClick() {
                remove(id, username);
            }
        }, new DialogUtils.IOnCancelClicked() {
            @Override
            public void onClick() {

            }
        });
    }

    private void edit(ReportInfo report) {
        Intent intent = new Intent(this, EditReportActivity.class);
        intent.putExtra("report", report);
        intent.putExtra("type", "edit");
        intent.putExtra("classId", report.getClassId());
        startActivity(intent);
    }


    public void startMain(String username) {
        Intent intent = new Intent(this, ListRoomActivity.class);
        setResult(3);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();

    }

    public void remove(int reportId, final String username) {
        String url = Constants.API_REMOVE_REPORT + reportId;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                if (result.equalsIgnoreCase("true")) {
                    startMain(username);
                } else {
                    Toast.makeText(ReportDetailActivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                }
            }
        });
    }






}
