package com.fu.group10.apps.staff.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.fu.group10.apps.staff.DummyApplication;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Constants;
import com.fu.group10.apps.staff.Utils.NetworkUtils;
import com.fu.group10.apps.staff.Utils.ParseUtils;
import com.fu.group10.apps.staff.Utils.Utils;
import com.fu.group10.apps.staff.activity.MainActivity;
import com.fu.group10.apps.staff.activity.MapActivity;
import com.fu.group10.apps.staff.activity.ResolveReportActivity;
import com.fu.group10.apps.staff.adapter.ReportAdapter;
import com.fu.group10.apps.staff.component.InfinityListView;
import com.fu.group10.apps.staff.model.ReportInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/4/2015.
 */
public class ReportFragment extends Fragment{

    InfinityListView listView;
    final List<ReportInfo> items = new ArrayList<ReportInfo>();
    EditText txtKeyword;
    ImageButton btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initListView(rootView);

        return rootView;
    }

    private void initListView(final View rootView) {
        Log.d("demo", "Load list view");
        final View emptyView = rootView.findViewById(R.id.empty_list);
        if (!Utils.isOnline()) {
            ((TextView) emptyView.findViewById(R.id.emptyMessage)).setText(DummyApplication.getAppContext().getString(R.string.noConnection));
        } else {
            ((TextView) emptyView.findViewById(R.id.emptyMessage)).setText(DummyApplication.getAppContext().getString(R.string.no_report_found));
        }
        listView = (InfinityListView) rootView.findViewById(R.id.listView);
        btnSearch = (ImageButton) rootView.findViewById(R.id.btnSearch);
        txtKeyword = (EditText) rootView.findViewById(R.id.txtKeyword);


        final ReportAdapter adapter = new ReportAdapter(DummyApplication.getAppContext(), items);


        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ReportInfo reportInfo = items.get(position);
                openMap(reportInfo);
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        setMultiChoice(adapter, listView);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = txtKeyword.getText().toString();
                //String response = NetworkUtils.getResponseFromGetRequest(Constants.API_SEARCH + keyword);
                String response = "[{'roomId':'301','timeReport':'301','damageLevel':'301','evaluate':'301'," +
                        "'userReport':'301','systemEvaluate': 0,'equipments': [{\t'equipmentName':'Bàn'," +
                        "'quantity' : 2,\t'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'}," +
                        "{'equipmentName':'Bàn','quantity' : 2,\t'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'}" +
                        ",{'equipmentName':'Bàn','quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'}," +
                        "{'equipmentName':'Bàn','quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'}]}," +
                        "{'roomId':'301','timeReport':'301','damageLevel':'301','evaluate':'301','userReport':'301'," +
                        "'systemEvaluate': 0,'equipments': [{\t'equipmentName':'Bàn','quantity' : 2,\t'status' : true," +
                        "'evaluate' : 'Nặng','damaged' : 'Nặng'},{'equipmentName':'Bàn','quantity' : 2,\t'status' : true," +
                        "'evaluate' : 'Nặng','damaged' : 'Nặng'},{'equipmentName':'Bàn','quantity' : 2,'status' : true," +
                        "'evaluate' : 'Nặng','damaged' : 'Nặng'},{'equipmentName':'Bàn','quantity' : 2,'status' : true," +
                        "'evaluate' : 'Nặng','damaged' : 'Nặng'}]}, {'roomId':'301','timeReport':'301','damageLevel':'301'," +
                        "'evaluate':'301','userReport':'301','systemEvaluate': 0,'equipments': [{\t'equipmentName':'Bàn'," +
                        "'quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'},{'equipmentName':'Bàn'," +
                        "'quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'},{'equipmentName':'Bàn'," +
                        "'quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'},{\t'equipmentName':'Bàn'," +
                        "'quantity' : 2,'status' : true,'evaluate' : 'Nặng','damaged' : 'Nặng'}]}]";
                List<ReportInfo> items = ParseUtils.parseReportFromJSON(response);
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (items != null) {

                    ReportAdapter reportAdapter = new ReportAdapter(DummyApplication.getAppContext(), items);


                    listView.setEmptyView(emptyView);
                    listView.setAdapter(reportAdapter);
                    setMultiChoice(reportAdapter, listView);
                } else {
                    Log.d("LiveAdapter", "Could not get data from server!");
//            Toast.makeText(DummyApplication.getAppContext(), "Could not get data from server!", Toast.LENGTH_LONG).show();
                    listView.setEmptyView(emptyView);
                }


            }
        });

        txtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });

    }

    private void openMap(ReportInfo reportInfo) {
        Intent intent = new Intent(getActivity(), ResolveReportActivity.class);
        intent.putExtra("report", reportInfo);
        startActivity(intent);
    }

    private void setMultiChoice(final ReportAdapter adapter, ListView listView1) {
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");

                // Calls toggleSelection method from ListViewAdapter Class
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = adapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                ReportInfo selecteditem = (ReportInfo)adapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                adapter.remove(selecteditem);
                            }
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
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                adapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
}
