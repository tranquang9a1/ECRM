package com.fu.group10.capstone.apps.teachermobileapp.utils;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class Constants {
    //public static final String SOURCE = "http://192.168.43.80:8080";
    public static final int TIMEOUT = 10000;
    private static final String SOURCE = "http://128.199.208.93";
    public static final String API_SOURCE = SOURCE + "/api/";
    public static final String API_LOGIN = API_SOURCE + "login";
    public static final String API_SCHEDULE = API_SOURCE + "schedule?username=";
    public static final String API_GET_ALL_SCHEDULE = API_SOURCE + "getAllSchedule?username=";
    public static final String API_GET_REPORT_BY_USERNAME = API_SOURCE + "getReportByUsername?username=";
    public static final String API_LOAD_MAP = API_SOURCE + "map?id=";
    public static final String API_GET_EQUIPMENT = API_SOURCE + "getEquipments?classId=";
    public static final String API_CREATE_REPORT = API_SOURCE + "createReport";
    public static final String API_UPDATE_REPORT = API_SOURCE + "editReport";
    public static final String API_REMOVE_REPORT = API_SOURCE + "remove?reportId=";
    public static final String API_GET_CURRENT_TIME = API_SOURCE + "getCurrentTime";
    public static final String API_CHECK_CONNECTION = API_SOURCE + "checkConnection";
    public static final String API_GET_CLASSROOM = API_SOURCE + "getClassroom?classId=";
    public static final String PROJECTOR = "[1]";
    public static final String TELEVISION = "[2]";
    public static final String AIR = "[3]";
    public static final String FAN = "[4]";
    public static final String SPEAKER = "[5]";
    public static final String BULB = "[6]";
    public static final String TABLE = "[0]";
    public static final String APP_SERVER_URL = SOURCE + "/notification/register";
    public static final String STAFF_PHONE = "+17164063801";

    public static final int REMOVE_TIME = 15 * 60 * 1000;

    // GCM server using java
    // static final String APP_SERVER_URL =
    // "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

    // Google Project Number
    public static final String GOOGLE_PROJECT_ID = "683842465097";
    public static final String MESSAGE_KEY = "message";
    public static final String SEND_NOTIFICATION = SOURCE + "/notification/sendNotification?message=";

    public static final String SEND_SMS = API_SOURCE + "sendSMS?message=";

    public static final String HIGH = "Nặng";
    public static final String MEDIUM = "Trung Bình";
    public static final String LOW = "Nhẹ";

    public static String getPosition(String name) {
        if (name.equalsIgnoreCase("Máy Chiếu")) {
            return PROJECTOR;
        } else if (name.equalsIgnoreCase("Tivi")) {
            return TELEVISION;
        }else if (name.equalsIgnoreCase("Máy Lạnh")) {
            return AIR;
        }else if (name.equalsIgnoreCase("Máy Quạt")) {
            return FAN;
        }else if (name.equalsIgnoreCase("Loa")) {
            return SPEAKER;
        }else if (name.equalsIgnoreCase("Bóng Đèn")) {
            return BULB;
        } else {
            return TABLE;
        }
    }

    public static String convertDamageLevel(int number) {
        if (number == 4 || number == 1) {
            return HIGH;
        } else if (number == 2) {
            return MEDIUM;
        } else {
            return LOW;
        }
    }
}
