package com.fu.group10.apps.teacher.utils;


import android.util.Log;
import com.fu.group10.apps.teacher.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class ParseUtils {

    public static JSONArray parseMoviesJson(String json){
        try {
            JSONArray movies = new JSONArray(json);
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public static String[] parseCategoriesJson(String json){
        try {
            JSONArray jsonCategories = new JSONArray(json);
            String[] categories = new String[jsonCategories.length() + 1];
            categories[0] = "Home";
            for (int i = 0; i < jsonCategories.length(); i++){
                categories[i + 1] = jsonCategories.getString(i);
            }
            return categories;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static User parseUserJson(String json) {
        User result = null;
        try {
            if (json == null) {
                return result;
            }

            JSONObject user = new JSONObject(json);
            List<ClassroomInfo> classroomInfos = new ArrayList<ClassroomInfo>();
            JSONArray classroom = user.getJSONArray("classrooms");
            for (int i = 0; i< classroom.length(); i++) {
                ClassroomInfo classroomInfo = new ClassroomInfo(classroom.getJSONObject(i).getInt("classId"),
                        classroom.getJSONObject(i).getString("classroomName"),
                        classroom.getJSONObject(i).getString("timeFrom"),
                        classroom.getJSONObject(i).getString("timeTo"));

                classroomInfos.add(classroomInfo);
            }
            // Get movie object
            result = new User(
                    user.getString("username"), user.getString("password"), user.getString("fullname"),
                    user.getString("phone"), user.getString("role"), user.getLong("lastLogin"),
                    user.getBoolean("status"), classroomInfos);

        } catch (JSONException e) {
            Log.d("Parse User", "Wrong format");
            e.printStackTrace();
        }
        return result;
    }

    public static Result parseResult(String json) {
        Result result = null;
        try {
            if (json == null) {
                return result;
            }

            JSONObject object = new JSONObject(json);

            result = new Result(object.getInt("error_code"), object.getString("error"));

        } catch (JSONException e) {
            Log.d("Parse Result", "Wrong format");
            e.printStackTrace();
        }
        return result;
    }

    public static List<ReportInfo> parseReportFromJSON(String response) {
        try {
            List<ReportInfo> reports = new ArrayList<ReportInfo>();
            if (response == null) {
                return reports;
            }
            JSONArray result = new JSONArray(response);

            for (int i = 0; i < result.length(); i++) {
                JSONObject report = result.getJSONObject(i);
                List<EquipmentReportInfo> reportInfoList = new ArrayList<EquipmentReportInfo>();

                JSONArray equipments = report.getJSONArray("equipments");
                for (int j = 0; j < equipments.length(); j++) {
                    EquipmentReportInfo reportInfo = new EquipmentReportInfo(
                            equipments.getJSONObject(j).getString("equipmentName"),
                            equipments.getJSONObject(j).getString("description"),
                            equipments.getJSONObject(j).getString("damaged"),
                            equipments.getJSONObject(j).getBoolean("status"),
                            equipments.getJSONObject(j).getString("solution"),
                            equipments.getJSONObject(j).getString("resolveTime"));
                    reportInfoList.add(reportInfo);
                }


                reports.add(new ReportInfo(report.getInt("reportId"), report.getString("username"),
                        report.getString("fullname"), report.getString("classId"), report.getString("createTime"),
                        report.getString("evaluate"),  report.getInt("status"),report.getInt("damageLevel"),reportInfoList ));
            }


            return reports;
        } catch (JSONException e) {
            Log.d("Utils", "Wrong JSON format");
            e.printStackTrace();
            return null;
        }
    }
}
