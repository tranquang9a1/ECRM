package com.fu.group10.apps.staff.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.fragment.FinishFragment;

/**
 * Created by QuangTV on 6/6/2015.
 */
public class TestActivity extends FragmentActivity {
    FinishFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        testFragment = new FinishFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.test_container, testFragment).commit();
    }
}
