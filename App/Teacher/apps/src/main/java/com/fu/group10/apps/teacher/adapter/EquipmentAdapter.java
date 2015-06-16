package com.fu.group10.apps.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.model.ClassroomInfo;
import com.fu.group10.apps.teacher.model.RoundedLetterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class EquipmentAdapter extends BaseAdapter {

    private ArrayList<String> mEntries;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public EquipmentAdapter(Context context,ArrayList<String> entries){
        this.mEntries = entries;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.mEntries.size() -1;
    }

    @Override
    public String getItem(int position) {
        return this.mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;

        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_equipment,parent,false);
            mViewHolder.equipmentImg = (ImageView) convertView.findViewById(R.id.equipmentImg);
            mViewHolder.equipmentImg.setTag(position);
            mViewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinnerItem);
            mViewHolder.txtDamaged = (EditText) convertView.findViewById(R.id.txtDamaged);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        String items = getItem(position);
        if (items != null) {
            mViewHolder.equipmentImg.setImageResource(setImage(items));
            mViewHolder.equipmentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mViewHolder.spinner.getVisibility() == View.VISIBLE) {
                        mViewHolder.spinner.setVisibility(View.INVISIBLE);
                        mViewHolder.equipmentImg.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
                    } else {
                        mViewHolder.spinner.setVisibility(View.VISIBLE);
                        mViewHolder.equipmentImg.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }


                }
            });

            mViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selected = mViewHolder.spinner.getSelectedItem().toString();
                    String high = "N?ng";
                    if (selected.equalsIgnoreCase(high)) {
                        mViewHolder.txtDamaged.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView equipmentImg;
        private Spinner spinner;
        private EditText txtDamaged;

    }



    public int setImage(String name) {
            if (name.equalsIgnoreCase("Qu?t")) {
                return R.drawable.ic_fan;
            } else if (name.equalsIgnoreCase("Máy L?nh")) {
                return R.drawable.ic_air;
            } else if (name.equalsIgnoreCase("Tivi")) {
                return R.drawable.ic_tv;
            } else if (name.equalsIgnoreCase("Gh?")) {
                return R.drawable.ic_chair;
            } else if (name.equalsIgnoreCase("Máy Chi?u")) {
                return R.drawable.ic_projector;
            } else if (name.equalsIgnoreCase("Bàn")) {
                return R.drawable.ic_table1;
            } else {
                return R.drawable.ic_speaker;
            }
        }

}
