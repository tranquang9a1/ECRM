package com.fu.group10.capstone.apps.teachermobileapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DBUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.InfinityListView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QuangTV on 6/8/2015.
 */
public class DamagedEquipmentAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter {
    Context mContext;
    public List<EquipmentInfo> items;
    private final SimpleDateFormat dateFormater;
    DatabaseHelper db;
    List<EquipmentDAO> listCategory = new ArrayList<>();

    public DamagedEquipmentAdapter(Context appContext, List<EquipmentInfo> items) {

        this.mContext = appContext;
        this.items = items;
        dateFormater = new SimpleDateFormat("HH:mm");
        db = new DatabaseHelper(appContext);
        listCategory = db.getEquipments();
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (row == null || row.getTag() == null) {

            row = inflater.inflate(R.layout.list_equipment, null);
            holder = new ViewHolder();
            holder.roomId = (TextView) row.findViewById(R.id.room_id);
            holder.quantity = (TextView) row.findViewById(R.id.quantity);
            holder.equipmentImg = (ImageView) row.findViewById(R.id.equipmentImg);
            holder.evaluate = (EditText) row.findViewById(R.id.evaluate);
            holder.spinner = (Spinner) row.findViewById(R.id.spinnerItem);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        holder.roomId.setText(items.get(i).getName());
        holder.quantity.setText(items.get(i).getQuantity() + "");
        int position = 0;
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = position == position ? position : 0;
                holder.spinner.setSelection(position);
                if (holder.spinner.getSelectedItem().toString().equalsIgnoreCase("Nặng")) {
                holder.evaluate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.spinner.setSelection(position);
        holder.equipmentImg.setImageBitmap(DBUtils.setImage(mContext, items.get(i).getName()));

        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((EquipmentInfo) obj);
        }
    }

    @Override
    public Collection loadMore(int offset, int count) {
            return new ArrayList();
    }

    @Override
    public boolean hasMoreData() {
        return items.size() == 0;
    }



    private class ViewHolder {
        TextView roomId;
        TextView reason;
        EditText evaluate;
        ImageView equipmentImg;
        TextView quantity;
        Spinner spinner;
    }









}
