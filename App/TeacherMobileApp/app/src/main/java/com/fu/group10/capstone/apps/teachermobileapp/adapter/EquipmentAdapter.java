package com.fu.group10.capstone.apps.teachermobileapp.adapter;

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
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentCategory;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DBUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 6/28/2015.
 */
public class EquipmentAdapter extends BaseAdapter {

    private List<Equipment> listEquipment = new ArrayList<Equipment>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    DatabaseHelper db;
    List<EquipmentDAO> listCategory = new ArrayList<>();


    public EquipmentAdapter(Context context, List<Equipment> listEquipment){
        this.listEquipment = listEquipment;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
        db = new DatabaseHelper(context);
        listCategory = db.getEquipments();

    }

    @Override
    public int getCount() {
        return this.listEquipment.size();
    }

    @Override
    public Equipment getItem(int position) {
        return this.listEquipment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_equipment,parent,false);
            mViewHolder.equipmentImg = (ImageView) convertView.findViewById(R.id.equipmentImg);
            mViewHolder.txtEquipmentName = (TextView) convertView.findViewById(R.id.txtEquipmentName);
            mViewHolder.txtTimeRemain = (TextView) convertView.findViewById(R.id.txtTimeRemain);
            mViewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            mViewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            mViewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            mViewHolder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            mViewHolder.txtReported = (TextView) convertView.findViewById(R.id.txtReported);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        final Equipment items = getItem(position);

        if (items != null) {

            if (items.isReport()) {
                mViewHolder.txtReported.setVisibility(View.VISIBLE);
            } else {
                mViewHolder.txtReported.setVisibility(View.GONE);
            }
            if (!items.isDamaged()) {
                mViewHolder.txtStatus.setVisibility(View.VISIBLE);
            } else {
                mViewHolder.txtStatus.setVisibility(View.GONE);
            }

            if (items.isReport() || !items.isDamaged()) {
                convertView.setBackground(mContext.getResources().getDrawable(R.color.colorPrimaryLight));
            } else {
                convertView.setBackground(mContext.getResources().getDrawable(R.color.white));
            }


            if (mSelectedItemsIds.get(position)) {
                mViewHolder.equipmentImg.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_check));
            } else {
                mViewHolder.equipmentImg.setImageBitmap(DBUtils.setImage(mContext,items.getEquipmentName()));
            }
            mViewHolder.txtEquipmentName.setText(items.getEquipmentName());
            setText(mViewHolder, items);
        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView equipmentImg;
        private TextView txtEquipmentName;
        private TextView txtTimeRemain;
        private TextView txtName;
        private TextView textView2;
        private TextView textView3;
        private TextView txtStatus;
        private TextView txtReported;


    }


    private void setText(ViewHolder mViewHolder, Equipment items) {
        if (!items.getTimeRemain().equalsIgnoreCase("null")) {
            mViewHolder.txtTimeRemain.setText(items.getTimeRemain());
        } else {
            mViewHolder.txtTimeRemain.setVisibility(View.GONE);
            mViewHolder.textView2.setVisibility(View.GONE);
        }

        if (!items.getCompany().equalsIgnoreCase("null")) {
            mViewHolder.txtName.setText(items.getCompany());
        } else {
            mViewHolder.txtName.setVisibility(View.GONE);
            mViewHolder.textView3.setVisibility(View.GONE);
        }


    }





    public void toggleSelection(int position) {

        selectView(position, !mSelectedItemsIds.get(position));

    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public void remove(Equipment object) {
        listEquipment.remove(object);
        notifyDataSetChanged();
    }


}
