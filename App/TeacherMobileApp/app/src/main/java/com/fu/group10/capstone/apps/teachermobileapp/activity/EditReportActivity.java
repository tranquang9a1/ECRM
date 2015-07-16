package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.ClassroomFragment;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.EquipmentFragment;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.GuideFragment;
import com.google.common.collect.Lists;


import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

/**
 * Created by QuangTV on 7/12/2015.
 */
public class EditReportActivity extends ActionBarActivity {
    ScrollerViewPager viewPager;
    GuideFragment guideFragment;
    ClassroomFragment classroomFragment;

    EquipmentFragment equipmentFragment;
    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int id = getIntent().getExtras().getInt("classId");
        username = getIntent().getExtras().getString("username");

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);


        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(getFragment(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // just set viewPager
        springIndicator.setViewPager(viewPager);

    }

    private List<String> getTitles(){
        return Lists.newArrayList("Tổng quát", "Chi tiết");
    }

    private List<Integer> getBgRes(){
        return Lists.newArrayList(R.mipmap.ic_air, R.mipmap.ic_air);
    }

    private List<Fragment> getFragment() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        classroomFragment = new ClassroomFragment();
        equipmentFragment = new EquipmentFragment();
        fragments.add(equipmentFragment);
        fragments.add(classroomFragment);
        return fragments;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
