package com.fu.group10.apps.teacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.model.EquipmentInfo;
import com.fu.group10.apps.teacher.model.EquipmentReport;
import com.fu.group10.apps.teacher.model.EquipmentReportInfo;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.InfinityListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class ReportDetailAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter {

    Context mContext;
    public List<EquipmentReport> items;
    private final SimpleDateFormat dateFormater;


    public ReportDetailAdapter(Context appContext, List<EquipmentReport> items) {

        this.mContext = appContext;
        this.items = items;
        dateFormater = new SimpleDateFormat("HH:mm");
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

            row = inflater.inflate(R.layout.list_info, null);
            holder = new ViewHolder();
            holder.equipmentImg = (ImageView) row.findViewById(R.id.equipmentImg);
            holder.equipmentName = (TextView) row.findViewById(R.id.equipmentName);
            holder.quantity = (TextView) row.findViewById(R.id.quantity);
            holder.damaged = (TextView) row.findViewById(R.id.damagedLevel);
            holder.solution = (TextView) row.findViewById(R.id.resolveDes);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        holder.equipmentName.setText(items.get(i).getEquipmentName());
        holder.quantity.setText(items.get(i).getQuantity() + "");
        holder.damaged.setText(items.get(i).getDamagedLevel());
        holder.equipmentImg.setImageResource(setImage(items.get(i)));
        if (items.get(i).isStatus()) {
            holder.solution.setText(items.get(i).getSolution());
            holder.solution.setVisibility(View.VISIBLE);
        }

        Resources resource = mContext.getResources();
        row.setBackgroundColor(resource.getColor(R.color.resolved));


        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((EquipmentReport) obj);
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
        TextView damaged;
        ImageView equipmentImg;
        TextView solution;
        TextView resolveTime;
    }


    public int setImage(EquipmentReport report) {
        if (report.getEquipmentName().equalsIgnoreCase("Bàn")) {
            return R.drawable.ic_table1;
        } else if (report.getEquipmentName().equalsIgnoreCase("Quạt")) {
            return R.drawable.ic_fan;
        } else if (report.getEquipmentName().equalsIgnoreCase("Máy Lạnh")) {
            return R.drawable.ic_air;
        } else if (report.getEquipmentName().equalsIgnoreCase("Tivi")) {
            return R.drawable.ic_tv;
        } else if (report.getEquipmentName().equalsIgnoreCase("Ghế")) {
            return R.drawable.ic_chair;
        } else if (report.getEquipmentName().equalsIgnoreCase("Máy Chiếu")) {
            return R.drawable.ic_projector;
        } else  {
            return R.drawable.ic_speaker;
        }
    }

}
