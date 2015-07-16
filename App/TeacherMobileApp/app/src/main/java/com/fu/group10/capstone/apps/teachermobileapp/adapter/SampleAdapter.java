package com.fu.group10.capstone.apps.teachermobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.model.ClassroomInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.RoundedLetterView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class SampleAdapter extends BaseAdapter {
    private List<ClassroomInfo> mEntries = new ArrayList<ClassroomInfo>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private final SimpleDateFormat dateFormater;

    public SampleAdapter(Context context, List<ClassroomInfo> entries){
        this.mEntries = entries;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        dateFormater = new SimpleDateFormat("HH:mm");
    }

    @Override
    public int getCount() {
        return this.mEntries.size();
    }

    @Override
    public ClassroomInfo getItem(int position) {
        return this.mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_view_item,parent,false);
            mViewHolder.mRoundedLetterView = (RoundedLetterView) convertView.findViewById(R.id.rlv_name_view);
            mViewHolder.timeFrom = (TextView) convertView.findViewById(R.id.txtTimeFrom);
            mViewHolder.timeTo = (TextView) convertView.findViewById(R.id.txtTimeTo);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ClassroomInfo items = getItem(position);

        if (items != null){
            Date timeFrom = new Date(Long.parseLong(items.getTimeFrom())   /** + 3600 * 1000 **/);
            mViewHolder.timeFrom.setText(dateFormater.format(timeFrom));
            Date timeTo = new Date(Long.parseLong(items.getTimeTo())  /** + 3600 * 1000 **/);
            mViewHolder.timeTo.setText(dateFormater.format(timeTo));
            mViewHolder.mRoundedLetterView.setTitleText(items.getClassName());
            if(position%2 == 0){
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }else{
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }

            Date currentTime = new Date();
            Long current = currentTime.getTime() / 100000;
            Long from = timeFrom.getTime();

//            if (current < from) {
//                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
//            }
        }

        return convertView;
    }

    private static class ViewHolder {
        private RoundedLetterView mRoundedLetterView;
        private TextView timeFrom;
        private TextView timeTo;
    }
}
