package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.model.DamagedEquipment;

import java.util.*;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class JsInterface {
    Context context;
    static List<DamagedEquipment> result = new ArrayList<DamagedEquipment>();
    List<Integer> valueChoose = new ArrayList<Integer>();
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
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        result.add(damagedEquipment);
        selected.put(name, true);
    }

    public static void deviceAddEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        result.add(damagedEquipment);
    }
    @JavascriptInterface
    public static void removeEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        Iterator<DamagedEquipment> iter = result.iterator();

        while (iter.hasNext()) {
            if(iter.next().position.equalsIgnoreCase(position)) {
                iter.remove();
            }
        }
        selected.put(name, false);
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
