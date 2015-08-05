package com.fu.group10.capstone.apps.teachermobileapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentCategory;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DBUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.InfinityListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class ReportDetailAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter {

    Context mContext;
    public List<EquipmentReportInfo> items;
    private final SimpleDateFormat dateFormater;
    private SparseBooleanArray mSelectedItemsIds;
    List<EquipmentDAO> listCategory = new ArrayList<>();
    DatabaseHelper db;

    public ReportDetailAdapter(Context appContext, List<EquipmentReportInfo> items) {
        mSelectedItemsIds = new SparseBooleanArray();
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
        ViewHolder holder;
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (row == null || row.getTag() == null) {
            row = inflater.inflate(R.layout.list_info, null);
            holder = new ViewHolder();
            holder.equipmentName = (TextView) row.findViewById(R.id.equipmentName);
            holder.quantity = (TextView) row.findViewById(R.id.quantity);
            holder.damagedLevel = (TextView) row.findViewById(R.id.damagedLevel);
            holder.resolveDes = (TextView) row.findViewById(R.id.resolveDes);
            holder.equipmentImg = (ImageView) row.findViewById(R.id.equipmentImg);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.equipmentName.setText(items.get(i).getEquipmentName());
        holder.quantity.setText(1 + "");
        holder.damagedLevel.setText(Constants.convertDamageLevel(Integer.parseInt(items.get(i).getDamaged())));
        if (!items.get(i).getDescription().equalsIgnoreCase("null")) {
            holder.resolveDes.setVisibility(View.VISIBLE);
            holder.resolveDes.setText(items.get(i).getDescription());
        } else {
            holder.resolveDes.setVisibility(View.GONE);
        }


        holder.equipmentImg.setImageBitmap(DBUtils.setImage(mContext, items.get(i).getEquipmentName()));



        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((EquipmentReportInfo) obj);
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
        TextView equipmentName;
        TextView quantity;
        TextView damagedLevel;
        TextView resolveDes;
        ImageView equipmentImg;
    }





}
