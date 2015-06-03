package com.fu.group10.apps.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.fragment.AccountFragment;
import com.fu.group10.apps.teacher.fragment.ClassroomFragment;


public class MainActivity extends FragmentActivity {

    LinearLayout tabClassroom;
    LinearLayout tabAccount;

    LinearLayout tabClassroomArrow;
    LinearLayout tabAccountArrow;

    ClassroomFragment classroomFragment;
    AccountFragment accountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabClassroom = (LinearLayout) findViewById(R.id.tabClassroom);
        tabAccount = (LinearLayout) findViewById(R.id.tabAccount);

        tabAccountArrow = (LinearLayout) findViewById(R.id.tabAccountArrow);
        tabClassroomArrow = (LinearLayout) findViewById(R.id.tabClassroomArrow);

        tabClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClassroom();
            }
        });

        tabAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccount();
            }
        });

        classroomFragment = new ClassroomFragment();
        accountFragment = new AccountFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("id", 123);
        classroomFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, classroomFragment).commit();

        setActiveTab(0);



    }



    private void setActiveTab(int index) {
        LinearLayout[] tabs = {tabClassroom, tabAccount};
        LinearLayout[] tabArrows = {tabClassroomArrow, tabAccountArrow};
        for (LinearLayout tab : tabs) {
            tab.setSelected(false);
        }
        for (LinearLayout tabArrow : tabArrows) {
            tabArrow.setVisibility(View.INVISIBLE);
        }
        tabs[index].setSelected(true);
        tabArrows[index].setVisibility(View.VISIBLE);

    }

    private void openClassroom() {
        setActiveTab(0);
        setFragment(classroomFragment);
    }

    private void openAccount() {
        setActiveTab(1);
        setFragment(accountFragment);
    }


    private void setFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
