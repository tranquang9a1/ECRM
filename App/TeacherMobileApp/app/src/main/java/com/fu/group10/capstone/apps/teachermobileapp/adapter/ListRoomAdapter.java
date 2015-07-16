package com.fu.group10.capstone.apps.teachermobileapp.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.RoundedLetterView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 6/26/2015.
 */
public class ListRoomAdapter extends BaseAdapter {

    private List<ReportInfo> mEntries = new ArrayList<ReportInfo>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    private final SimpleDateFormat dateFormater;

    public ListRoomAdapter(Context context, List<ReportInfo> entries){
        this.mEntries = entries;
        this.mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.mLayoutInflater = LayoutInflater.from(context);
        dateFormater = new SimpleDateFormat("HH:mm - dd/MM");
    }

    @Override
    public int getCount() {
        return this.mEntries.size();
    }

    @Override
    public ReportInfo getItem(int position) {
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
            convertView = mLayoutInflater.inflate(R.layout.list_classroom,parent,false);
            mViewHolder.mRoundedLetterView = (RoundedLetterView) convertView.findViewById(R.id.rlv_name_view);
            mViewHolder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            mViewHolder.txtDamage = (TextView) convertView.findViewById(R.id.txtDamage);
            mViewHolder.txtEvaluate = (TextView) convertView.findViewById(R.id.txtEvaluate);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ReportInfo items = getItem(position);

        if (items != null){
                mViewHolder.txtEvaluate.setText(items.getEvaluate());
            String timeReport = dateFormater.format(Long.parseLong(items.getCreateTime()));
            mViewHolder.txtDamage.setText(timeReport);
            if (items.getStatus() == 2) {
                mViewHolder.txtStatus.setText("Đã đổi phòng sang " + items.getChangedRoom());
            } else if (items.getStatus() == 1) {
                mViewHolder.txtStatus.setText("Đang chờ xử lí");
            } else if (items.getStatus() == 3){
                mViewHolder.txtStatus.setText("Phòng đã được sửa chữa");
            } else {
                mViewHolder.txtStatus.setText("Báo cáo đã bị xóa");
            }


            mViewHolder.mRoundedLetterView.setTitleText(items.getClassName());
            if(position%2 == 0){
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }else{
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }

            if (mSelectedItemsIds.get(position)) {
                mViewHolder.mRoundedLetterView.setVisibility(View.INVISIBLE);
            } else {
                mViewHolder.mRoundedLetterView.setVisibility(View.VISIBLE);
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        private RoundedLetterView mRoundedLetterView;
        private TextView txtStatus;
        private TextView txtDamage;
        private TextView txtEvaluate;
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

    public void remove(ReportInfo object) {
        mEntries.remove(object);
        notifyDataSetChanged();
    }
}
