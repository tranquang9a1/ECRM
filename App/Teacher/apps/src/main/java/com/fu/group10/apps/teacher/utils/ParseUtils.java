package com.fu.group10.apps.teacher.utils;


import android.util.Log;
import com.fu.group10.apps.teacher.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    user.getString("username"),
                    user.getString("phone"),
                    user.getInt("classId"),
                    user.getBoolean("role"));

        } catch (JSONException e) {
            Log.d("Parse User", "Wrong format");
            e.printStackTrace();
        }
        return result;
    }
}
