package com.fu.group10.apps.staff.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Constants;
import com.fu.group10.apps.staff.Utils.NetworkUtils;
import com.fu.group10.apps.staff.Utils.ParseUtils;
import com.fu.group10.apps.staff.Utils.Utils;
import com.fu.group10.apps.staff.component.InfinityListView;
import com.fu.group10.apps.staff.model.ReportInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 6/6/2015.
 */
public class TestAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter  {

    Context mContext;
    public List<ReportInfo> items;
    private final SimpleDateFormat dateFormater;

    public TestAdapter(Context appContext, List<ReportInfo> items) {
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
            holder.damageLevel = (TextView) row.findViewById(R.id.reason);
            holder.evaluate = (TextView) row.findViewById(R.id.equipment_name);
            holder.timeReport = (TextView) row.findViewById(R.id.time_report);
            holder.userReport = (TextView) row.findViewById(R.id.user_report);
            holder.equipmentImg = (ImageView) row.findViewById(R.id.equipmentImg);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Date time = new Date(Long.parseLong(items.get(i).getTimeReport()) * 1000 /** - Constants.TIME_ZONE_DIFF * 1000 **/);
        holder.timeReport.setText(dateFormater.format(time));
        holder.roomId.setText(items.get(i).getRoomId());
        holder.userReport.setText(items.get(i).getUserReport());
        holder.damageLevel.setText(items.get(i).getDamageLevel() + "");
        holder.evaluate.setText(items.get(i).getEvaluate());


            holder.equipmentImg.setImageResource(R.drawable.ic_action_search);

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
        //String response = NetworkUtils.getResponseFromGetRequest(Constants.API_LOGIN);
        String response = "[{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}," +
                "{'id':1,'roomId':'201', 'equipmentName':'Projector','reason':'Projector has down', 'userReport':'Tran Vinh Quang','timeReport':'123123123123', 'equipmentImg':'1'}]";
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
        TextView timeReport;
        TextView damageLevel;
        TextView evaluate;
        TextView userReport;
        TextView systemEvaluate;
        ImageView equipmentImg;
    }


}
