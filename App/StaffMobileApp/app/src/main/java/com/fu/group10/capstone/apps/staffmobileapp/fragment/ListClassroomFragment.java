package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.DialogUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.adapter.GridViewAdapter;
import com.fu.group10.capstone.apps.staffmobileapp.model.ClassInfo;
import com.fu.group10.capstone.apps.staffmobileapp.model.Result;

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
    TextView emptyMessage;
    ChooseRoomDialogFragment chooseRoomDialog;
    ClassInfo currentClass;


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
        emptyMessage = (TextView) rootView.findViewById(R.id.emptyMessage);
        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        if (items.size() == 0) {
            emptyMessage.setVisibility(View.VISIBLE);
        }
        mAdapter = new GridViewAdapter(getActivity(),items);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                currentClass = items.get(i);
                if (currentClass.getDamageLevel() >= Constants.DAMAGE_TO_CHANGE) {
                    showProgress("Đang tải dữ liệu");
                    resolve(currentClass.getId() +"");
                } else {
                    showProgress(Constants.FIND_ROOM);
                    getAvailableRoom(currentClass.getId()+"");
                }
            }
        });
    }

    public void showProgress(String msg){
        progressDialog = ProgressDialog.show(getActivity(), "", msg, true);
    }

    public void resolve(final String roomId) {
        DialogUtils.showAlert(getActivity(), "Bạn muốn khắc phục hư hại phòng này ?", new DialogUtils.IOnOkClicked() {
            @Override
            public void onClick() {
                String url = Constants.API_RESOLVE_REPORT + roomId;

                RequestSender sender = new RequestSender();
                sender.start(url, new RequestSender.IRequestSenderComplete() {
                    @Override
                    public void onRequestComplete(String result) {
                        Result res = ParseUtils.parseResult(result);
                        progressDialog.dismiss();
                        if (res.getError_code() == 100) {
                            currentClass.setDamageLevel(Constants.DAMAGE_TO_ZERO);
                            mAdapter.notifyDataSetChanged();
                            DialogUtils.showAlert(getActivity(), "Khắc phục phòng thành công");
                        } else {
                            DialogUtils.showAlert(getActivity(), "Có lỗi xảy ra " + res.getError());
                        }
                    }
                });
            }
        }, new DialogUtils.IOnCancelClicked() {
            @Override
            public void onClick() {

            }
        });

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
                    chooseRoomDialog.setParam(getActivity(), availableRoom, currentClass.getName(), "change", new ChooseRoomDialogFragment.IChangeRoomComplete() {
                        @Override
                        public void onRequestComplete(boolean result) {
                            if (result) {
                                currentClass.setDamageLevel(Constants.DAMAGE_TO_CHANGE);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    chooseRoomDialog.show(getActivity().getFragmentManager(), "ChooseRoom");
                } else {
                    DialogUtils.showAlert(getActivity(), Constants.NO_ROOM_MESSAGE);
                }

            }
        });
    }


}
