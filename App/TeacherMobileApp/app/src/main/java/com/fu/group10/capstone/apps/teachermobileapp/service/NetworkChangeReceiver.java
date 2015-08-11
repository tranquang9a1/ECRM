package com.fu.group10.capstone.apps.teachermobileapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fu.group10.capstone.apps.teachermobileapp.DummyApplication;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ClassroomDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDetailDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ScheduleDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.UserDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.ClassroomInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentQuantity;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DBUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Utils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QuangTV on 7/13/2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    DatabaseHelper db;
    String username;
    String className;
    String evaluate;
    Map<Integer, Boolean> mapClass = new HashMap<Integer, Boolean>();
    UserDAO user = new UserDAO();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline()) {
            Log.d("NetworkChangeReceiver", "Start sync");
            db = new DatabaseHelper(context);
            List<ReportInfo> reports = db.getReportIsNotSync();

            for (ReportInfo report : reports) {
                String listDamaged = "";
                String listEvaluate = "";
                String listDescription = "";
                String listPosition = "";
                username = report.getUsername();
                className = report.getClassName();
                evaluate = report.getEvaluate();
                for (EquipmentReportInfo e : report.getEquipments()) {
                    listDamaged += e.getEquipmentName() + ",";
                    listEvaluate += e.getDamaged() + ",";
                    listDescription += e.getDescription() + ",";
                    listPosition += DBUtils.getPosition(context, e.getEquipmentName()) + "-";
                }

                Map<String, String> params = new HashMap<String, String>();


                params.put("listDamaged", listDamaged.substring(0, listDamaged.length()-1));
                params.put("listEvaluate", listEvaluate.substring(0, listEvaluate.length()-1));
                params.put("listDescription", listDescription.substring(0, listDescription.length()-1));
                params.put("listPosition", listPosition.substring(0, listPosition.length()-1));
                params.put("evaluate", report.getEvaluate());
                params.put("classId", report.getClassId() + "");
                params.put("username", report.getUsername());
                params.put("createTime", report.getCreateTime());


                RequestSender sender = new RequestSender();
                sender.method ="POST";
                sender.param = params;


                String url = Constants.API_CREATE_REPORT;
                sender.start(url, new RequestSender.IRequestSenderComplete() {
                    @Override
                    public void onRequestComplete(String result) {
                        Result result1 = ParseUtils.parseResult(result);

                        if (result1.getError_code() == 100) {
                            String msg = username + " vừa báo cáo hư hại phòng học " + className;
                            if (evaluate.equalsIgnoreCase("Phải đổi phòng")) {
                                sendSMS(msg);
                            }
                            sendNotification(msg);
                            Log.d("Sync", msg);


                        } else {
                            Log.d("Sync", "Error when sync data" + result1.getError());
                        }

                    }
                });

            }
            Log.d("Sync Data", "Start get User");
            getCurrentUser();
            if (user != null) {
                Log.d("Sync Data", "Remove currentDB");
                truncateDB();
                Log.d("Sync Data", "Start Import Schedule");
                importSchedule();
                db.closeDB();
            }
            Log.d("Sync Data",  "End sync data at " + new Date() );
        }


    }

    public void sendSMS(String msg) {
        try {
            String url = Constants.SEND_SMS + URLEncoder.encode(msg, "UTF-8")+"&ListUser=staff";

            final RequestSender sender = new RequestSender();
            sender.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendNotification(String msg) {
        try {
            String url = Constants.SEND_NOTIFICATION + URLEncoder.encode(msg, "UTF-8")+"&ListUser=staff";
            final RequestSender sender = new RequestSender();
            sender.execute(url);
        }catch ( Exception e) {
            e.printStackTrace();
        }

    }


    public  void importSchedule() {



        String url = Constants.API_GET_ALL_SCHEDULE + user.getUsername();
        RequestSender sender = new RequestSender();
        sender.start(url, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                List<ClassroomInfo> classrooms = ParseUtils.parseClassroom(result);

                for (int i = 0; i < classrooms.size(); i++) {
                    ClassroomInfo classroom = classrooms.get(i);
                    ScheduleDAO dao = new ScheduleDAO();
                    dao.setClassId(classroom.getClassid());
                    mapClass.put(classroom.getClassid(), true);
                    dao.setUsername(user.getUsername());
                    dao.setTimeFrom(classroom.getTimeFrom());
                    dao.setTimeTo(classroom.getTimeTo());
                    dao.setDate(classroom.getDate());
                    dao.setClassName(classroom.getClassName());

                    dao.setIsActive(1);
                    dao.setId(i);
                    db.insertSchedule(dao);
                }


                RequestSender sender1 = new RequestSender();
                String url1 = Constants.API_GET_REPORT_BY_USERNAME + user.getUsername() + "&offset=" + 0 + "&limit=" + 10;
                sender1.start(url1, new RequestSender.IRequestSenderComplete() {
                    @Override
                    public void onRequestComplete(String result) {
                        List<ReportInfo> classrooms = ParseUtils.parseReportFromJSON(result);
                        for (int i = 0; i < classrooms.size(); i++) {
                            syncReport(classrooms.get(i));


                        }
                    }
                });
                getEquipment();

            }
        });


    }



    public void truncateDB() {
        db.truncateTable();
    }

    public void getEquipment() {
        for (Map.Entry<Integer, Boolean> entry : mapClass.entrySet()) {
            final int id = entry.getKey();
            String url = Constants.API_GET_EQUIPMENT + id;
            RequestSender sender1 = new RequestSender();
            sender1.start(url, new RequestSender.IRequestSenderComplete() {
                @Override
                public void onRequestComplete(String result) {
                    syncEquipment(id + "", ParseUtils.parseEquipmentJson(result));
                }
            });

            String url1 = Constants.API_GET_CLASSROOM + id;
            RequestSender sender = new RequestSender();
            sender.start(url1, new RequestSender.IRequestSenderComplete() {
                @Override
                public void onRequestComplete(String result) {
                    syncClassroom(result);
                }
            });

            String url2 = Constants.API_GET_EQUIPMENT_QUANTITY + id;
            RequestSender sender2 = new RequestSender();
            sender2.start(url2, new RequestSender.IRequestSenderComplete() {
                @Override
                public void onRequestComplete(String result) {
                    List<EquipmentQuantity> eq = ParseUtils.parseEquipmentQuantity(result);
                    syncQuantity(eq);
                }
            });
        }
    }

    public void syncQuantity(List<EquipmentQuantity> listEquipments) {
        for (int i = 0; i < listEquipments.size(); i++) {
            db.addEquipmentQuantity(listEquipments.get(i));
        }
    }

    public void syncClassroom(String result ) {
        ClassroomDAO dao = ParseUtils.parseClassroomDAO(result);
        db.insertClassroom(dao);
    }



    public void syncEquipment(String classId, List<Equipment> listEquipments) {
        for (int i = 0; i < listEquipments.size(); i++) {
            db.syncEquipment(classId, listEquipments.get(i));
        }
    }

    public void syncReport(ReportInfo report) {
        int reportId = report.getReportId();
        ReportDAO dao = new ReportDAO();
        dao.setClassName(report.getClassName());
        dao.setEvaluate(report.getEvaluate());
        dao.setChangedRoom(report.getChangedRoom());
        dao.setClassId(report.getClassId());
        dao.setCreateTime(report.getCreateTime());
        dao.setDamageLevel(report.getDamageLevel());
        dao.setFullname(report.getFullname());
        dao.setReportId(report.getReportId());
        dao.setStatus(report.getStatus());
        dao.setUsername(report.getUsername());


        List<EquipmentReportInfo> equipments = report.getEquipments();
        for (EquipmentReportInfo e : equipments) {
            ReportDetailDAO detail = new ReportDetailDAO(e.getEquipmentName(),e.getDescription(),e.getDamaged(),e.isStatus());
            db.insertReportDetail(reportId, detail, true);

        }

        db.insertReport(dao, true);
    }




    public void getCurrentUser() {
        user = db.getUser();
    }
}
