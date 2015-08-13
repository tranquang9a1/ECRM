package com.fu.group10.capstone.apps.teachermobileapp.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.ChooseEquipmentDialog;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.EquipmentFragment;
import com.fu.group10.capstone.apps.teachermobileapp.model.DamagedEquipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;

import java.util.*;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class JsInterface {
    static Context context;
    static List<DamagedEquipment> result = new ArrayList<DamagedEquipment>();
    static List<Equipment> listEquipments = new ArrayList<Equipment>();
    List<Integer> valueChoose = new ArrayList<Integer>();
    static ChooseEquipmentDialog chooseEquipmentDialog;

    static EquipmentFragment equipmentFragment;

    static Map<String, Boolean> selected = new HashMap<String, Boolean>();
    public JsInterface(Context context) {
        this.context = context;

    }

    @JavascriptInterface
    public void openURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public static void addEquipment(String name, String position) {
        chooseEquipmentDialog = new ChooseEquipmentDialog();
        Equipment equipment = new Equipment(name, null, null, position);
        chooseEquipmentDialog.setParams((Activity) context, equipment, new ChooseEquipmentDialog.OnMsgEnteredListener() {
            @Override
            public void onMsgEnteredListener(Equipment equipment) {
                listEquipments.add(equipment);
                //equipmentFragment.notifyAddEquipment(equipment);
            }
        });
        chooseEquipmentDialog.show(((Activity) context).getFragmentManager(), "Haha");

//        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
//        result.add(damagedEquipment);
//        selected.put(name, true);
    }

    public static List<Equipment> getListEquipments() {
        return listEquipments.size() > 0 ? listEquipments : new ArrayList<Equipment>();
    }

    public static void deviceAddEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        result.add(damagedEquipment);
    }
    @JavascriptInterface
    public static void removeEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        Iterator<Equipment> iter = listEquipments.iterator();

        while (iter.hasNext()) {
            if(iter.next().getPosition().equalsIgnoreCase(position)) {
                iter.remove();
            }
        }
        //selected.put(name, false);
    }

    public static void removeEquipment(String name) {
        if (name.isEmpty() || name == null) {
            return;
        }
        Iterator<DamagedEquipment> iter = result.iterator();
        int count = 0;
        while (iter.hasNext()) {
            if(iter.next().name.equalsIgnoreCase(name)) {
                iter.remove();
                count++;
            }
        }
        selected.put(name, false);
    }




    public static List<DamagedEquipment> getListDamaged() {
        if (result != null) {
            return result;
        }
        return new ArrayList<DamagedEquipment>();

    }

    public static boolean getDamage(String name) {
        if (selected.get(name) != null && selected.get(name)) {
            return true;
        }
        return false;
    }

    public static boolean isExist(String name) {
        for (int  i = 0; i < result.size(); i++) {
            if (result.get(i).getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }



    public List<Integer> getListChoose() {
        if (valueChoose != null) {
            return valueChoose;
        }
        return new ArrayList<Integer>();
    }


}
