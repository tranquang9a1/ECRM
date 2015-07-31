package com.fu.group10.capstone.apps.staffmobileapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.Constants;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.DatabaseHelper;
import com.fu.group10.capstone.apps.staffmobileapp.Utils.ParseUtils;
import com.fu.group10.capstone.apps.staffmobileapp.component.InfinityListView;
import com.fu.group10.capstone.apps.staffmobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.staffmobileapp.model.Equipment;
import com.fu.group10.capstone.apps.staffmobileapp.model.ReportInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class ResolveAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter   {

    Context mContext;
    public List<Equipment> items;
    private final SimpleDateFormat dateFormater;
    private SparseBooleanArray mSelectedItemsIds;
    DatabaseHelper db;
    List<EquipmentDAO> listCategory = new ArrayList<>();
    public ResolveAdapter(Context appContext, List<Equipment> items) {
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
            row = inflater.inflate(R.layout.list_equipment, null);
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
        holder.quantity.setText(items.get(i).getQuantity() + "");
        holder.damagedLevel.setText(Constants.convertDamageLevel(Integer.parseInt(items.get(i).getDamaged())));
        if (!items.get(i).getEvaluate().equalsIgnoreCase("null")) {
            holder.resolveDes.setVisibility(View.VISIBLE);
            holder.resolveDes.setText(items.get(i).getEvaluate());
        } else {
            holder.resolveDes.setVisibility(View.GONE);
        }


        holder.equipmentImg.setImageBitmap(setImage(items.get(i).getEquipmentName()));



        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((Equipment) obj);
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

    public Bitmap setImage(String equipName) {
        for (int i = 0; i < listCategory.size(); i++) {
            EquipmentDAO dao = listCategory.get(i);
            if (dao.getName().equalsIgnoreCase(equipName)) {
                return BitmapFactory.decodeByteArray(dao.getImages(),
                        0, dao.getImages().length);
            }
        }
        return null;
    }


}
