package com.fu.group10.apps.teacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.Constants;
import com.fu.group10.apps.teacher.utils.InfinityListView;
import com.fu.group10.apps.teacher.utils.NetworkUtils;
import com.fu.group10.apps.teacher.utils.ParseUtils;
import com.sileria.android.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 6/7/2015.
 */
public class AccountAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter {

    Context mContext;
    public List<ReportInfo> items;
    public String username;
    private final SimpleDateFormat dateFormater;


    public AccountAdapter(Context appContext, List<ReportInfo> items, String username) {
        this.username = username;
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
        ViewHolder holder;
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (row == null || row.getTag() == null) {
            row = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.roomId = (TextView) row.findViewById(R.id.room_id);
            holder.reason = (TextView) row.findViewById(R.id.reason);
            holder.equipmentName = (TextView) row.findViewById(R.id.equipment_name);
            holder.timeReport = (TextView) row.findViewById(R.id.time_report);
            holder.damageLevel = (TextView) row.findViewById(R.id.damageLevel);
            holder.equipmentImg = (ImageView) row.findViewById(R.id.equipmentImg);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        if (items.get(i).getEquipments().size() != 0) {


        Date time = new Date(Long.parseLong(items.get(i).getCreateTime()) / 1000 /** - Constants.TIME_ZONE_DIFF * 1000 **/);
        holder.timeReport.setText(dateFormater.format(time));
        holder.roomId.setText(items.get(i).getClassId() + "");
        holder.reason.setText(items.get(i).getEquipments().get(0).getDamaged() + "");
        holder.damageLevel.setText(items.get(i).getEvaluate());
        if (items.get(i).getEquipments().size() > 1) {
            holder.equipmentName.setText("Nhiều Thiết Bị");
        } else if (items.get(i).getEquipments().size() == 0) {

        } else {
            holder.equipmentName.setText(items.get(i).getEquipments().get(0).getEquipmentName());
        }

        holder.equipmentImg.setImageResource(setImage(items.get(i)));

        Resources resource = mContext.getResources();
        row.setBackgroundColor(resource.getColor(R.color.resolved));

        }
        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((ReportInfo) obj);
        }
    }

    @Override
    public Collection loadMore(int offset, int count) {
        String url = Constants.API_GET_REPORT_BY_USERNAME + username;
        String response = NetworkUtils.getResponseFromGetRequest(url);

        List<ReportInfo> games = ParseUtils.parseReportFromJSON(response);
        if (games != null) {
            return games;
        } else {
            Log.d("LiveAdapter", "Could not get data from server!");
//            Toast.makeText(DummyApplication.getAppContext(), "Could not get data from server!", Toast.LENGTH_LONG).show();
            return new ArrayList();
        }
    }

    @Override
    public boolean hasMoreData() {
        return items.size() == 0;
    }


    private class ViewHolder {
        TextView roomId;
        TextView equipmentName;
        TextView reason;
        TextView timeReport;
        TextView damageLevel;
        ImageView equipmentImg;
    }


    public int setImage(ReportInfo report) {
        if (report.getEquipments().size() > 1) {
            return R.drawable.ic_tv;
        } else {
            if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Quạt")) {
                return R.drawable.ic_fan;
            } else if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Máy Lạnh")) {
                return R.drawable.ic_air;
            } else if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Tivi")) {
                return R.drawable.ic_tv;
            } else if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Ghế")) {
                return R.drawable.ic_chair;
            } else if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Máy Chiếu")) {
                return R.drawable.ic_projector;
            } else if (report.getEquipments().get(0).getEquipmentName().equalsIgnoreCase("Bàn")) {
                return R.drawable.ic_table1;
            } else {
                return R.drawable.ic_speaker;
            }
        }
    }
}
