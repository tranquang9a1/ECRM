package com.fu.group10.capstone.apps.teachermobileapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.support.v7.widget.Toolbar;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.adapter.EquipmentAdapter;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.ChooseEquipmentDialog;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.CreateReportDialog;
import com.fu.group10.capstone.apps.teachermobileapp.dialog.CreateReportDialogOffline;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Utils;

import java.util.*;

/**
 * Created by QuangTV on 6/28/2015.
 */
public class EquipmentFragment extends Fragment{

    ListView listView;
    ArrayList<String> items;
    List<Equipment> listEquipments = new ArrayList<Equipment>();
    List<String> listEvaluate = new ArrayList<String>();
    List<Equipment> listDamaged = new ArrayList<Equipment>();
    EquipmentAdapter adapter;
    Button btnSubmit;
    CreateReportDialog createReportDialog;
    CreateReportDialogOffline createReportDialogOffline;
    ChooseEquipmentDialog chooseEquipmentDialog;
    Toolbar toolbar;
    int checkcount = 0;
    int classId = 0;
    String className = "";
    String username = "";
    DatabaseHelper db;
    boolean isOnline = true;
    String type;
    ReportInfo report;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        initListView(rootView);
        getActivity().setTitle("ECRM Teacher");
        return rootView;
    }

    private void initListView(View rootView) {
        Log.d("demo", "Load list view");
        listView = (ListView) rootView.findViewById(R.id.lv_sample_list);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
        toolbar.setTitle("ECRM Teacher");
        String[] equipments;
        type = getActivity().getIntent().getExtras().getString("type");
        if (type.equalsIgnoreCase("create")) {
            classId = getActivity().getIntent().getExtras().getInt("classId");
            username = getActivity().getIntent().getExtras().getString("username");
            className = getActivity().getIntent().getExtras().getString("className");
        } else {
            report = getActivity().getIntent().getParcelableExtra("report");
            classId = report.getClassId();
            convertList();
        }

        isOnline = Utils.isOnline();
        getData(classId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideSoftKeyboard(getActivity());
                    final Equipment equip = listEquipments.get(i);
                    if (isExist(listEquipments.get(i).getEquipmentName())) {

                        DialogUtils.showAlert(getActivity(), "Bạn muốn xóa thiết bị này khỏi danh sách báo cáo", new DialogUtils.IOnOkClicked() {
                            @Override
                            public void onClick() {
                                removeEquipment(equip.getEquipmentName());
                                equip.setIsReport(false);
                                equip.setIsDamaged(true);
                                adapter.notifyDataSetChanged();
                            }
                        }, new DialogUtils.IOnCancelClicked() {
                            @Override
                            public void onClick() {

                            }
                        });

                    } else if(!listEquipments.get(i).isDamaged()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Thiết bị này đã được báo cáo!", Toast.LENGTH_LONG).show();
                    } else {
                        chooseEquipmentDialog = new ChooseEquipmentDialog();
                        chooseEquipmentDialog.setParams(getActivity(), listEquipments.get(i), new ChooseEquipmentDialog.OnMsgEnteredListener() {
                            @Override
                            public void onMsgEnteredListener(Equipment equipment) {
                                listDamaged.add(equipment);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        chooseEquipmentDialog.show(getActivity().getFragmentManager(), "Enter Information");

                    }



            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listDamaged.size() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Bạn phải chọn ít nhất 1 thiết bị", Toast.LENGTH_LONG).show();
                } else {

                    if (isOnline) {
                        String listDamage = "";
                        String listEvaluate = "";
                        String listDescription = "";
                        String listPosition = "";


                        for (int i = 0; i< listDamaged.size(); i++) {
                            listDamage += listDamaged.get(i).getEquipmentName() + ",";
                            listEvaluate += listDamaged.get(i).getDamageLevel() + ",";
                            listDescription += listDamaged.get(i).getEvaluate() + ",";
                            listPosition += listDamaged.get(i).getPosition() + "-";
                        }

                        final String damaged = listDamage.substring(0, listDamage.length() - 1);
                        final String position = listPosition.substring(0, listPosition.length() - 1);
                        final String evaluate = listEvaluate.substring(0, listEvaluate.length() > 0 ? listEvaluate.length() - 1 : 0);
                        final String description = listDescription.substring(0, listDescription.length() > 0 ? listDescription.length() - 1 : 0);
                        createReportDialog = new CreateReportDialog();
                        if (type.equalsIgnoreCase("create")) {
                            createReportDialog.setParams(getActivity(),username, classId, damaged, position, evaluate, description, type);
                        } else {
                            createReportDialog.setParams(getActivity(),report.getUsername(), report.getReportId(),classId, damaged, position, evaluate, description, type);
                        }

                        createReportDialog.show(getActivity().getFragmentManager(), "haha");
                    } else {
                        createReportDialogOffline = new CreateReportDialogOffline();
                        createReportDialogOffline.setParams(getActivity(), username, classId, className, listDamaged );
                        createReportDialogOffline.show(getActivity().getFragmentManager(), "haha");
                    }




                }

            }
        });



    }

    public void getData(int classId) {
        //String response = "['Loa','Quạt','Máy Lạnh', 'Máy Chiếu', 'Tivi', 'Ghế', 'Bàn']";
        if (isOnline) {
            String url = Constants.API_GET_EQUIPMENT + classId;


            RequestSender sender = new RequestSender();
            sender.start(url, new RequestSender.IRequestSenderComplete() {
                @Override
                public void onRequestComplete(String result) {
                    listEquipments = ParseUtils.parseEquipmentJson(result);
                    adapter = new EquipmentAdapter(DummyApplication.getAppContext(), listEquipments);
                    listView.setAdapter(adapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                    setMultiChoice(adapter, listView);

                }
            });
        } else {
            db = new DatabaseHelper(getActivity().getApplicationContext());
            listEquipments = db.getEquipment(classId);
            adapter = new EquipmentAdapter(DummyApplication.getAppContext(), listEquipments);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            setMultiChoice(adapter, listView);
        }


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean isExist(String name) {
        for (int i = 0; i < listDamaged.size(); i++) {
            if (listDamaged.get(i).getEquipmentName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }





    private void setMultiChoice(final EquipmentAdapter adapter, ListView listView1) {
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {

                if (!listEquipments.get(position).isDamaged()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Thiết bị này đã được báo cáo!", Toast.LENGTH_LONG).show();

                } else {
                    toolbar.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);

                    // Calls toggleSelection method from ListViewAdapter Class
                    adapter.toggleSelection(position);
                    // Capture total checked items
                    checkcount = adapter.getSelectedCount();
                    // Set the CAB title according to total checked items

                    if (checkcount == 0) {
                        adapter.removeSelection();
                        mode.finish();
                        getActivity().setTitle("ECRM Teacher");
                    } else {
                        mode.setTitle(checkcount + " thiết bị đã chọn");
                    }
                }

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_send:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        if (isOnline) {
                            SparseBooleanArray selected = adapter
                                    .getSelectedIds();

                            String listDamage = "";
                            String listEvaluate = "";
                            String listDescription = "";
                            String listPosition = "";


                            // Captures all selected ids with a loop

                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    Equipment selecteditem = (Equipment)adapter
                                            .getItem(selected.keyAt(i));
                                    listDamage += selecteditem.getEquipmentName() + ",";
                                    listEvaluate += "1" + ",";
                                    listDescription += "Không sử dụng được" + ",";
                                    listPosition += Constants.getPosition(selecteditem.getEquipmentName()) + "-";
                                }
                            }
                            final String damaged = listDamage.substring(0, listDamage.length() - 1);
                            final String position = listPosition.substring(0, listPosition.length() - 1);
                            final String evaluate = listEvaluate.substring(0, listEvaluate.length() > 0 ? listEvaluate.length() - 1 : 0);
                            final String description = listDescription.substring(0, listDescription.length() > 0 ? listDescription.length() - 1 : 0);

                            createReportDialog = new CreateReportDialog();
                            createReportDialog.setParams(getActivity(),username, classId, damaged, position, evaluate, description, type);
                            getActivity().setResult(Activity.RESULT_OK);
                            createReportDialog.show(getActivity().getFragmentManager(), "haha");
                        } else {
                            SparseBooleanArray selected = adapter
                                    .getSelectedIds();

                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    Equipment selecteditem = (Equipment)adapter
                                            .getItem(selected.keyAt(i));
                                    selecteditem.setEvaluate("Không sử dụng được");
                                    selecteditem.setDamageLevel("1");
                                    listDamaged.add(selecteditem);
                                }
                            }
                            createReportDialogOffline = new CreateReportDialogOffline();
                            createReportDialogOffline.setParams(getActivity(), username, classId, className, listDamaged );
                            getActivity().setResult(Activity.RESULT_OK);
                            createReportDialogOffline.show(getActivity().getFragmentManager(), "haha");
                        }

                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                adapter.removeSelection();
                toolbar.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                checkcount = 0;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    public void convertList() {
        List<EquipmentReportInfo> equipments = report.getEquipments();
        for (EquipmentReportInfo equip : equipments ) {
            Equipment e = new Equipment(equip.getEquipmentName(), equip.getDamaged(), equip.getDescription(),
                    Constants.getPosition(equip.getEquipmentName()));
            listDamaged.add(e);
        }
    }

    public void removeEquipment(String equipmentName) {
        Iterator<Equipment> iter = listDamaged.iterator();
        while (iter.hasNext()) {
            if (iter.next().getEquipmentName().equalsIgnoreCase(equipmentName)) {
                iter.remove();
            }
        }
    }
}
