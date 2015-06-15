package com.fu.group10.apps.staff.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.fragment.ReportFragment;


public class MainActivity extends FragmentActivity {


    ReportFragment reportFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reportFragment = new ReportFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, reportFragment).commit();

    }





}
