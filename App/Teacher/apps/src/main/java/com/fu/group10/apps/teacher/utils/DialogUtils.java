package com.fu.group10.apps.teacher.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.fu.group10.apps.teacher.R;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class DialogUtils {
    public static void showAlert(Activity activity, String msg) {
        showAlert(activity, msg, null, null);
    }
    public static void showAlert(Activity activity, String msg, IOnOkClicked cb) {
        showAlert(activity, msg, cb, null);
    }

    public static void showAlert(Activity activity, String msg,
                                 final IOnOkClicked cb, final IOnCancelClicked cb2) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.alert))
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cb != null) {
                            cb.onClick();
                        }
                    }
                });
        if (cb2 != null) {
            builder.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cb2.onClick();
                }
            });
        }

        builder.create().show();
    }


    public static interface IOnOkClicked {
        void onClick();
    }

    public static interface IOnCancelClicked {
        void onClick();
    }
}
