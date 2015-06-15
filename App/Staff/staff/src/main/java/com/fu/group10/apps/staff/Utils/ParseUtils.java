package com.fu.group10.apps.staff.Utils;


import android.util.Log;
import com.fu.group10.apps.staff.model.Equipment;
import com.fu.group10.apps.staff.model.ReportInfo;
import com.fu.group10.apps.staff.model.Result;
import com.fu.group10.apps.staff.model.User;
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

            // Get movie object
            result = new User(
                    user.getString("username"), user.getString("password"), user.getString("fullname"),
                    user.getString("phone"), user.getString("role"), user.getLong("lastLogin"),
                    user.getBoolean("status"));

        } catch (JSONException e) {
            Log.d("Parse User", "Wrong format");
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
                JSONArray equipments = report.getJSONArray("equipments");
                List<Equipment> equipmentList = new ArrayList<Equipment>();
                for (int j = 0; j < equipments.length(); j++) {
                    JSONObject equipment = equipments.getJSONObject(j);

                    equipmentList.add(new Equipment(equipment.getInt("reportId"), equipment.getInt("equipmentId"), equipment.getString("equipmentName"),
                            equipment.getInt("quantity"), equipment.getBoolean("status"), equipment.getString("evaluate"),
                            equipment.getString("damage")));


                }

                reports.add(new ReportInfo(
                        result.getJSONObject(i).getInt("roomId"),
                        result.getJSONObject(i).getString("timeReport"),
                        result.getJSONObject(i).getInt("damageLevel"),
                        result.getJSONObject(i).getString("evaluate"),
                        result.getJSONObject(i).getString("userReport"),
                        result.getJSONObject(i).getInt("systemEvaluate"),
                        equipmentList
                ));
            }


            return reports;
        } catch (JSONException e) {
            Log.d("Utils", "Wrong JSON format");
            e.printStackTrace();
            return null;
        }
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
}
