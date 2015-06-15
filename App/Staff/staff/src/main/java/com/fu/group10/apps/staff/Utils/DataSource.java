package com.fu.group10.apps.staff.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinhquangtrung on 10/28/14.
 * It just like a API query string builder
 */
public class DataSource implements Parcelable {
    protected String link;
    protected HashMap<String, String> vars = new HashMap<String, String>();

    public DataSource() {
    }

    /**
     * - link : String
     * - size : Integer (hash map size)
     * loop (size) {
     *      - key : String
     *      - value : String
     * }
     */
    public DataSource(Parcel in) {
        this.link = in.readString();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            vars.put(in.readString(), in.readString());
        }
    }

    public DataSource setLink(String link) {
        this.link = link;
        return this;
    }

    public DataSource setVar(String name, String value) {
        vars.put(name, value);
        return this;
    }

    public String getVar(String name, String defaultValue) {
        String result = vars.get(name);
        return result != null ? result : defaultValue;
    }

    public String getURL() {
        StringBuilder result = new StringBuilder(link + "?");
        boolean firstVar = true;
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                result.append(firstVar ? "" : "&")
                      .append(key).append("=")
                      .append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            firstVar = false;
        }
        return result.toString();
    }

    /**
     * TODO: This will append some variables to the request for authentication
     * @return
     */
    public String getSecureURL() {
        StringBuilder result = new StringBuilder(link + "?");
        boolean firstVar = true;
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                result.append(firstVar ? "" : "&")
                        .append(key).append("=")
                        .append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            firstVar = false;
        }
        Log.d("API Call", "Send API: " + result.toString());
        return result.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * - link : String
     * - size : Integer (hash map size)
     * loop (size) {
     *      - key : String
     *      - value : String
     * }
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(link);
        parcel.writeInt(vars.size());
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            parcel.writeString(key);
            parcel.writeString(value);
        }
    }

    public static final Creator CREATOR = new Creator() {
        public DataSource createFromParcel(Parcel in) {
            return new DataSource(in);
        }

        public DataSource[] newArray(int size) {
            return new DataSource[size];
        }
    };
}
