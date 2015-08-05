package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 8/5/2015.
 */
public class DBUtils {


    public static String getPosition(Context context, String name) {
        List<EquipmentDAO> listCategory = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(context);
        listCategory = db.getEquipments();
        for (EquipmentDAO ec : listCategory) {
            if (ec.getName().equalsIgnoreCase(name)) {
                db.closeDB();
                return ec.getId();
            }
        }
        db.closeDB();
        return null;
    }

    public static Bitmap setImage(Context context, String name) {
        List<EquipmentDAO> listCategory = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(context);
        listCategory = db.getEquipments();
        for (EquipmentDAO ec : listCategory) {
            if (ec.getName().equalsIgnoreCase(name)) {
                db.closeDB();
                return BitmapFactory.decodeByteArray(ec.getImages(),
                        0, ec.getImages().length);
            }
        }
        db.closeDB();
        return null;
    }
}
