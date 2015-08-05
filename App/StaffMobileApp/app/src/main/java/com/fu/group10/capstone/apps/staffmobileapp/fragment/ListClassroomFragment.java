package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.DialogUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.adapter.GridViewAdapter;
import com.fu.group10.capstone.apps.staffmobileapp.model.ClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 8/4/2015.
 */
public class ListClassroomFragment extends Fragment {


    private GridView gridView;
    private GridViewAdapter mAdapter;
    private List<ClassInfo> items = new ArrayList<>();
    private int floor = 0;
    ProgressDialog progressDialog;
    String[] availableRoom;
    private String current;
    ChooseRoomDialogFragment chooseRoomDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_room, container, false);
        initView(rootView);
        return rootView;
    }

    public void setParams(List<ClassInfo> items) {
        this.items = items;
    }


    public void initView(View rootView) {
        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        mAdapter = new GridViewAdapter(getActivity(),items);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showProgress();
                current = items.get(i).getName();
                getAvailableRoom(items.get(i).getId()+"");
            }
        });
    }

    public void showProgress(){
        progressDialog = ProgressDialog.show(getActivity(), "", Constants.FIND_ROOM, true);
    }

    public void getAvailableRoom(String roomId) {
        String url = Constants.API_GET_AVAILABLE_ROOM + roomId;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                availableRoom = ParseUtils.parseListRoom(result);
                progressDialog.dismiss();
                if (availableRoom != null && availableRoom.length > 0) {
                    chooseRoomDialog = new ChooseRoomDialogFragment();
                    chooseRoomDialog.setParam(getActivity(),availableRoom, current, "change");
                    chooseRoomDialog.show(getActivity().getFragmentManager(), "ChooseRoom");
                } else {
                    DialogUtils.showAlert(getActivity(), Constants.NO_ROOM_MESSAGE);
                }

            }
        });
    }


}
