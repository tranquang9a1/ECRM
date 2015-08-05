package com.fu.group10.capstone.apps.staffmobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fu.group10.capstone.apps.staffmobileapp.R;
import com.fu.group10.capstone.apps.staffmobileapp.model.ClassInfo;
import com.fu.group10.capstone.apps.staffmobileapp.model.RoundedLetterView;

import java.util.List;

/**
 * Created by QuangTV on 8/4/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<ClassInfo> items;
    private Context mContext;
    public GridViewAdapter(Context context, List<ClassInfo> items) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public ClassInfo getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;

        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.gridview_row, null);

            viewHolder.mRoundedLetterView = (RoundedLetterView) convertView.findViewById(R.id.rlv_name_view);
            //viewHolder.txtViewTitle = (TextView) convertView.findViewById(R.id.txtName);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ClassInfo info = getItem(position);
        if (info != null) {
            //viewHolder.txtViewTitle.setText("Độ hư hại: " + info.getDamageLevel() + "%");
            viewHolder.mRoundedLetterView.setTitleText(info.getName());
            if (info.getDamageLevel() <= 20) {
                viewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
            } else if (info.getDamageLevel() > 20 && info.getDamageLevel() <= 60) {
                viewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            } else {
                viewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }

        }

        return convertView;
    }

    public static class ViewHolder {
        private RoundedLetterView mRoundedLetterView;
        public TextView txtViewTitle;
    }
}
