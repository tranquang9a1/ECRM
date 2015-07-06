package com.fu.group10.apps.staff.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.fragment.GoingFragment;


public class MainActivity extends FragmentActivity {


    GoingFragment reportFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_backup);

        reportFragment = new GoingFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, reportFragment).commit();

    }





}
