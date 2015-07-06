package com.fu.group10.apps.staff.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.model.ReportInfo;
import com.fu.group10.apps.staff.model.RoundedLetterView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private String type;

    public ListRoomAdapter(Context context, List<ReportInfo> entries, String type){
        this.mEntries = entries;
        this.mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.mLayoutInflater = LayoutInflater.from(context);
        dateFormater = new SimpleDateFormat("HH:mm");
        this.type = type;
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
            mViewHolder.txtUser = (TextView) convertView.findViewById(R.id.txtUser);
            mViewHolder.txtDamage = (TextView) convertView.findViewById(R.id.txtDamage);
            mViewHolder.txtEvaluate = (TextView) convertView.findViewById(R.id.txtEvaluate);
            mViewHolder.checkImg = (ImageView) convertView.findViewById(R.id.checkImg);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ReportInfo items = getItem(position);

        if (items != null){
            mViewHolder.txtUser.setText(items.getUserReport());
            mViewHolder.txtDamage.setText(items.getDamageLevel() + "%");
            if (type.equalsIgnoreCase("going")) {
                mViewHolder.txtEvaluate.setText("Đã đổi phòng sang " + items.getChangedRoom());
            } else if (type.equalsIgnoreCase("new")) {
                mViewHolder.txtEvaluate.setText(items.getEvaluate());
            } else if (type.equalsIgnoreCase("finish")){
                mViewHolder.txtEvaluate.setText("Phòng đã được sửa chữa");
            } else {
                mViewHolder.txtEvaluate.setText("Báo cáo đã bị xóa");
            }


            mViewHolder.mRoundedLetterView.setTitleText(items.getRoomName());
            if(position%2 == 0){
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }else{
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }

            if (mSelectedItemsIds.get(position)) {
                mViewHolder.checkImg.setVisibility(View.VISIBLE);
                mViewHolder.mRoundedLetterView.setVisibility(View.INVISIBLE);
            } else {
                mViewHolder.checkImg.setVisibility(View.GONE);
                mViewHolder.mRoundedLetterView.setVisibility(View.VISIBLE);
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        private RoundedLetterView mRoundedLetterView;
        private TextView txtUser;
        private TextView txtDamage;
        private TextView txtEvaluate;
        private ImageView checkImg;
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
