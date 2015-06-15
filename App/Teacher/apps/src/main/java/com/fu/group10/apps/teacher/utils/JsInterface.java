package com.fu.group10.apps.teacher.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.fu.group10.apps.teacher.model.DamagedEquipment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by QuangTV on 6/2/2015.
 */
public class JsInterface {
    Context context;
    List<DamagedEquipment> result = new ArrayList<DamagedEquipment>();
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
    public void addEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        result.add(damagedEquipment);
        Toast.makeText(context, "List had been add 1 equipment " + name, Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public void removeEquipment(String name, String position) {
        DamagedEquipment damagedEquipment = new DamagedEquipment(name, position);
        Iterator<DamagedEquipment> iter = result.iterator();

        while (iter.hasNext()) {
            if(iter.next().position.equalsIgnoreCase(position)) {
                iter.remove();
            }
        }
        Toast.makeText(context, "List had been remove 1 equipment " + name, Toast.LENGTH_SHORT).show();
    }




    public List<DamagedEquipment> getListDamaged() {
        if (result != null) {
            return result;
        }
        return null;

    }


}
