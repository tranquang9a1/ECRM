package com.fu.group10.apps.staff.fragment;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DialerFilter;
import android.widget.TextView;
import android.widget.Toast;
import com.fu.group10.apps.staff.DummyApplication;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Constants;
import com.fu.group10.apps.staff.Utils.RequestSender;
import com.fu.group10.apps.staff.activity.ListRoomActivity;
import com.fu.group10.apps.staff.activity.MainActivity;
import com.fu.group10.apps.staff.activity.ResolveReportActivity;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class ChooseRoomDialogFragment extends DialogFragment{

    String roomId;
    String current;
    String[] items;
    TextView txtNewRoom;
    ProgressDialog progress;

    private AlertDialog dialog;
    OnListChooseListener onListChooseListener;
    Context context;

    public ChooseRoomDialogFragment() {

    }

    public void setParam(String[] items, String current) {
        this.items = items;
        this.current = current;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_room_dialog_title).setPositiveButton(R.string.action_choose_room, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        progress = ProgressDialog.show(context, "", "Đang đổi phòng", true);
    }


    public void changeRoom(String from, String to) {
        String url = Constants.API_CHANGE_ROOM + from + "&to=" + to;
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                //progress.dismiss();
                if (result.equalsIgnoreCase("true")) {
                    openHome();
                } else {
                    Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openHome() {
        Intent intent = new Intent(context, ListRoomActivity.class);
        intent.putExtra("username", "staff");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        Toast.makeText(context, "Đổi phòng thành công", Toast.LENGTH_LONG).show();
    }


}
