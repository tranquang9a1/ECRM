package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.AccountFragment;


public class MainActivity extends FragmentActivity {

    LinearLayout tabClassroom;
    LinearLayout tabAccount;

    LinearLayout tabClassroomArrow;
    LinearLayout tabAccountArrow;

    AccountFragment accountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_main);
        tabClassroom = (LinearLayout) findViewById(R.id.tabClassroom);
        tabAccount = (LinearLayout) findViewById(R.id.tabAccount);

        tabAccountArrow = (LinearLayout) findViewById(R.id.tabAccountArrow);
        tabClassroomArrow = (LinearLayout) findViewById(R.id.tabClassroomArrow);


        tabAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccount();
            }
        });

        accountFragment = new AccountFragment();


        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accountFragment).commit();

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
