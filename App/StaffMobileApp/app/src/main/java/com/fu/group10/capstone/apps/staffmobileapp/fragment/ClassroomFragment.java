package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.model.ClassInfo;

import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * Created by QuangTV on 8/3/2015.
 */
public class ClassroomFragment extends Fragment implements MaterialTabListener {

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;
    List<Fragment> fragmentList;
    List<List<ClassInfo>> items;
    int size = 6;
    RequestSender sender = new RequestSender();
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_classroom, container, false);
        tabHost = (MaterialTabHost) rootView.findViewById(R.id.tabHost);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        for (int i = 0; i < size; i++) {
            tabHost.addTab(tabHost.newTab().setText(setTitle(i)).setTabListener(ClassroomFragment.this));
        }
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });
        getData();
        return rootView;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        List<List<ClassInfo>> items;
        public ViewPagerAdapter(FragmentManager fm, List<List<ClassInfo>> items) {
            super(fm);
            this.items = items;

        }

        public Fragment getItem(int num) {
            ListClassroomFragment fragment = new ListClassroomFragment();
            fragment.setParams(items.get(num));
            return fragment;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tầng " + (position + 1);
        }

    }

    public void getData() {
        String url = Constants.API_GET_ROOM_IN_FLOOR;

        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                items = ParseUtils.parseClassInfo(result);
                adapter = new ViewPagerAdapter(getChildFragmentManager(), items);
                pager.setAdapter(adapter);
                //updateView(getView());
            }
        });

    }

    public String setTitle(int position) {
        if (position==0) {
            return "Tầng Trệt";
        }
        return "Tầng " + position;
    }

    public void updateView(View view) {
        size = items.size();

        RelativeLayout linear=(RelativeLayout)rootView.findViewById(R.id.classroom);
        linear.removeAllViews();

    }


}
