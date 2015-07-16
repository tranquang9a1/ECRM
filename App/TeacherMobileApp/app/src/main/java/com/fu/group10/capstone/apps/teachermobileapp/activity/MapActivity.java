package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.model.DamagedEquipment;
import com.fu.group10.capstone.apps.teachermobileapp.utils.JsInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 6/11/2015.
 */
public class MapActivity extends Activity {

    private WebView classmap;
    private Button createReport;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        classmap = (WebView) findViewById(R.id.classmap);
        final JsInterface jsInterface = new JsInterface(getApplicationContext());
        classmap.getSettings().setJavaScriptEnabled(true);
        classmap.setWebViewClient(new WebViewClient());
        classmap.addJavascriptInterface(jsInterface, "Android");
        classmap.setBackgroundColor(0);
        classmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        String id = getIntent().getExtras().getString("roomId");
        refreshMap(id);

        createReport = (Button) findViewById(R.id.create_report_button);
        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DamagedEquipment> equipmentList = jsInterface.getListDamaged();
                if (equipmentList.size() > 0) {
                    ArrayList<DamagedEquipment> listEquipment = new ArrayList<DamagedEquipment>();
                    for (int i = 0; i < equipmentList.size(); i++) {
                        listEquipment.add(equipmentList.get(i));
                    }
                    openCreateReport(listEquipment);
                } else {
                    Toast.makeText(DummyApplication.getAppContext(), "Bạn phải chọn ít nhất 1 thiết bị!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void refreshMap(String id) {
        classmap.clearCache(true);
        //classmap.loadUrl(Constants.API_LOAD_MAP + id);
        classmap.loadUrl("http://app.megahd.vn/api/capstone/test.html");
    }

    private void openCreateReport(ArrayList<DamagedEquipment> listEquipment) {
        Intent intent = new Intent(this, CreateReportActivity.class);
        intent.putParcelableArrayListExtra("list", listEquipment);
        String id = getIntent().getExtras().getString("roomId");
        intent.putExtra("username", getIntent().getExtras().getString("username"));
        intent.putExtra("roomId", id);

        startActivity(intent);
    }
}
