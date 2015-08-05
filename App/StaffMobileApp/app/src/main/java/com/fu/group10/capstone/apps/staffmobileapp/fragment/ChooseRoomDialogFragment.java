package com.fu.group10.capstone.apps.staffmobileapp.fragment;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DialerFilter;
import android.widget.TextView;
import android.widget.Toast;
import com.fu.group10.capstone.apps.staffmobileapp.DummyApplication;
import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.DialogUtils;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.RequestSender;
import com.fu.group10.capstone.apps.staffmobileapp.activity.ListRoomActivity;
import com.fu.group10.capstone.apps.staffmobileapp.activity.MainActivity;
import com.fu.group10.capstone.apps.staffmobileapp.activity.ResolveReportActivity;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class ChooseRoomDialogFragment extends DialogFragment{

    String roomId;
    String current;
    String[] items;
    TextView txtNewRoom;
    ProgressDialog progress;
    Activity activity;
    private AlertDialog dialog;
    OnListChooseListener onListChooseListener;
    DialogChangeRoomSuccess dialogChangeRoomSuccess;
    Context context;
    String type;

    public ChooseRoomDialogFragment() {

    }

    public void setParam(Activity activity, String[] items, String current, String type) {
        this.activity = activity;
        this.items = items;
        this.current = current;
        this.type = type;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_room_dialog_title).setPositiveButton(R.string.action_choose_room, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showProgress();
                changeRoom(current, items[which+1]);
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgress();
                changeRoom(current, items[i]);
            }
        });
        dialog =  builder.create();
        return dialog;
    }

    public String getRoomId() {
        return roomId;
    }

    public interface OnListChooseListener {
        void onListChoose(String name);
    }

    public void showProgress() {
        progress = ProgressDialog.show(activity, "", "Đang đổi phòng", true);
    }


    public void changeRoom(final String from, final String to) {
        String url = Constants.API_CHANGE_ROOM + from + "&to=" + to;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                progress.dismiss();
                if (result.equalsIgnoreCase("true")) {
                    openHome(from, to);
                } else {
                    Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openHome(String from, String to) {

        DialogUtils.showAlert(activity, "Đã đổi phòng " + from + " sang phòng " + to, new DialogUtils.IOnOkClicked() {
            @Override
            public void onClick() {
                if (type.equalsIgnoreCase("report")) {
                    Intent intent = new Intent(activity, ListRoomActivity.class);
                    intent.putExtra("username", "staff");
                    activity.setResult(2);
                    startActivity(intent);
                    activity.finish();
                }
            }
        });

    }


}
