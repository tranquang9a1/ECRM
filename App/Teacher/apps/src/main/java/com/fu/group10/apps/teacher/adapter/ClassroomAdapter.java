package com.fu.group10.apps.teacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.model.ClassroomInfo;
import com.fu.group10.apps.teacher.model.ReportInfo;
import com.fu.group10.apps.teacher.utils.InfinityListView;
import com.fu.group10.apps.teacher.utils.ParseUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 6/11/2015.
 */
public class ClassroomAdapter extends BaseAdapter implements InfinityListView.IInfinityAdapter {
    Context mContext;
    public List<ClassroomInfo> items;
    private final SimpleDateFormat dateFormater;


    public ClassroomAdapter(Context appContext, List<ClassroomInfo> items) {

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
            row = inflater.inflate(R.layout.list_classroom, null);
            holder = new ViewHolder();
            holder.roomName = (TextView) row.findViewById(R.id.room_name);
            holder.classImg = (ImageView) row.findViewById(R.id.classImg);
            holder.timeFrom = (TextView) row.findViewById(R.id.time_from);
            holder.timeTo = (TextView) row.findViewById(R.id.time_to);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.timeFrom.setText(items.get(i).getTimeFrom());
        holder.timeTo.setText(items.get(i).getTimeTo());
        holder.roomName.setText(items.get(i).getClassName());

        holder.classImg.setImageResource(R.drawable.ic_projector);



        return row;
    }


    @Override
    public void addItems(Collection<Object> objs) {
        for (Object obj : objs) {
            items.add((ClassroomInfo) obj);
        }
    }

    @Override
    public Collection loadMore(int offset, int count) {
//        //String response = NetworkUtils.getResponseFromGetRequest(Constants.API_LOGIN);
//        String response = "";
//        //List<ClassroomInfo> games = ParseUtils.parseReportFromJSON(response);
//        if (games != null) {
//            return games;
//        } else {
//            Log.d("LiveAdapter", "Could not get data from server!");
////            Toast.makeText(DummyApplication.getAppContext(), "Could not get data from server!", Toast.LENGTH_LONG).show();
//            return new ArrayList();
//        }
        return new ArrayList();
    }

    @Override
    public boolean hasMoreData() {
        return items.size() == 0;
    }



    private class ViewHolder {
        TextView roomId;
        TextView roomName;
        TextView timeFrom;
        TextView timeTo;
        ImageView classImg;
    }

}
