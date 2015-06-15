package com.fu.group10.apps.staff.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DialerFilter;
import android.widget.TextView;
import com.fu.group10.apps.staff.R;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class ChooseRoomDialogFragment extends DialogFragment{

    String roomId;
    String[] items;
    TextView txtNewRoom;
    private AlertDialog dialog;
    OnListChooseListener onListChooseListener;

    public ChooseRoomDialogFragment() {

    }

    public void setParam(String[] items, OnListChooseListener cb) {
        this.items = items;
        this.onListChooseListener = cb;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_room_dialog_title).setPositiveButton(R.string.action_choose_room, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onListChooseListener.onListChoose(items[which]);

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


}
