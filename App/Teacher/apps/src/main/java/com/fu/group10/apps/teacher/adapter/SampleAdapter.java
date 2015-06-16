package com.fu.group10.apps.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fu.group10.apps.teacher.model.ClassroomInfo;
import com.fu.group10.apps.teacher.model.RoundedLetterView;
import com.fu.group10.apps.teacher.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class SampleAdapter extends BaseAdapter {
    private List<ClassroomInfo> mEntries = new ArrayList<ClassroomInfo>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SampleAdapter(Context context, List<ClassroomInfo> entries){
        this.mEntries = entries;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
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
            mViewHolder.timeFrom.setText(items.getTimeFrom());
            mViewHolder.timeTo.setText(items.getTimeTo());
            mViewHolder.mRoundedLetterView.setTitleText(items.getClassName());
            if(position%2 == 0){
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }else{
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        private RoundedLetterView mRoundedLetterView;
        private TextView timeFrom;
        private TextView timeTo;
    }
}
