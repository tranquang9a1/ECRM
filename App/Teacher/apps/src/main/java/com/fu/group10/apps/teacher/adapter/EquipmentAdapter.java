package com.fu.group10.apps.teacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.activity.TestActivity;
import com.fu.group10.apps.teacher.model.*;
import com.fu.group10.apps.teacher.utils.Constants;
import com.fu.group10.apps.teacher.utils.JsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class EquipmentAdapter extends BaseAdapter {

    private JsInterface jsInterface;
    private List<Equipment> listEquipment = new ArrayList<Equipment>();
    private ArrayList<String> mEntries;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Map<Integer, Boolean> selected;
    private Map<Integer, Boolean> damaged;
    private List<String> listEvaluate;
    private int id;

    public EquipmentAdapter(Context context,ArrayList<String> entries){
        this.mEntries = entries;
        this.damaged = new HashMap<Integer, Boolean>();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        jsInterface = new JsInterface(mContext);
        this.selected = new HashMap<Integer, Boolean>();
        listEvaluate = new ArrayList<String>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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



        final String items = getItem(position);
        refreshStatus(mViewHolder, position, items);
        checkDamage(mViewHolder, position);
        if (items != null) {
            mViewHolder.equipmentImg.setImageResource(setImage(items));
            checkHasChoose(position, items);
            mViewHolder.equipmentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected.get(position) != null) {
                        selected.put(position, !selected.get(position));
                    } else {
                        selected.put(position, true);
                    }

                    refreshStatus(mViewHolder, position, items);

                }
            });

            mViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    boolean check = mViewHolder.spinner.getSelectedItemPosition() == 2 ? true : false;
                    if (damaged.get(position) != null) {
                        damaged.put(position, check);
                    } else {
                        damaged.put(position, check);
                    }
                    checkDamage(mViewHolder, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            mViewHolder.txtDamaged.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    setDamaged(mViewHolder, position);
                    return true;
                }
            });

            mViewHolder.txtDamaged.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

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

    private void refreshStatus(ViewHolder viewHolder, int position, String items) {

            if (position >= 0 && items != null) {
                if (selected.get(position) != null && selected.get(position)) {
                    viewHolder.spinner.setVisibility(View.VISIBLE);
                    jsInterface.addEquipment(items, Constants.getPosition(items));
                    viewHolder.equipmentImg.setBackground(mContext.getResources().getDrawable(R.color.colorPrimaryLight));
                }  else  {
                    viewHolder.spinner.setVisibility(View.INVISIBLE);
                    viewHolder.txtDamaged.setVisibility(View.INVISIBLE);
                    jsInterface.removeEquipment(items);
                    //viewHolder.equipmentImg.setBackground(mContext.getResources().getDrawable(R.color.colorPrimary));
                }
            }


    }

    private void checkDamage(ViewHolder viewHolder, int position) {


        if (position >= 0) {
            if (damaged.get(position) != null && damaged.get(position)) {
                viewHolder.txtDamaged.setVisibility(View.VISIBLE);
                viewHolder.txtDamaged.requestFocus();
                viewHolder.spinner.setSelection(2);
            }  else {
                viewHolder.txtDamaged.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void checkHasChoose(int position, String name) {
        List<DamagedEquipment> listDamage = jsInterface.getListDamaged();
        for (int i = 0; i < listDamage.size(); i++) {
            if (listDamage.get(i).getName().equalsIgnoreCase(name)) {
                selected.put(position, true);
            }
        }
    }

    private void setDamaged(ViewHolder viewHolder, int position) {
        if (damaged.get(position) != null && damaged.get(position)) {
            viewHolder.spinner.setSelection(2);
        }
    }

    public List<DamagedEquipment> getListDamage() {
        return jsInterface.getListDamaged();
    }

    public Map<Integer, Boolean> getRowChosse() {
        return selected;
    }






    public int setImage(String name) {
            if (name.equalsIgnoreCase("Quạt")) {
                return R.drawable.ic_fan;
            } else if (name.equalsIgnoreCase("Máy Lạnh")) {
                return R.drawable.ic_air;
            } else if (name.equalsIgnoreCase("Tivi")) {
                return R.drawable.ic_tv;
            } else if (name.equalsIgnoreCase("Ghế")) {
                return R.drawable.ic_chair;
            } else if (name.equalsIgnoreCase("Máy Chiếu")) {
                return R.drawable.ic_projector;
            } else if (name.equalsIgnoreCase("Bàn")) {
                return R.drawable.ic_table1;
            } else {
                return R.drawable.ic_speaker;
            }
        }

}
