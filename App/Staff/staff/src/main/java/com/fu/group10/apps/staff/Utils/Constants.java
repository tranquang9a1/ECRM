package com.fu.group10.apps.staff.Utils;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class Constants {
    private static final String SOURCE = "http://128.199.208.93";
    //private static final String SOURCE = "http://192.168.43.80:8080";
    private static final String API_SOURCE = SOURCE + "/api/";
    public static final String API_LOGIN = API_SOURCE + "login";
    public static final String API_LOAD_MAP = API_SOURCE + "map?id=";
    public static final String API_SEARCH = API_SOURCE + "search?classid=";
    public static final String API_GET_REPORT = API_SOURCE + "getReportStaff?status=";
    public static final String API_RESOLVE_REPORT = API_SOURCE + "resolve?listRoomId=";
    public static final String API_CHANGE_ROOM = SOURCE + "/staff/changeRoom?from=";
    public static final String API_CHECK_SCHEDULE = API_SOURCE + "/checkSchedule?classId=";

    public static final String API_GET_AVAILABLE_ROOM = SOURCE + "/staff/getAvailableRoom?classroomId=";


    public static final String APP_SERVER_URL = SOURCE + "/notification/register";

    // GCM server using java
    // static final String APP_SERVER_URL =
    // "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

    // Google Project Number
    public static final String GOOGLE_PROJECT_ID = "683842465097";
    public static final String MESSAGE_KEY = "message";

    public static final String HIGH = "Nặng";
    public static final String MEDIUM = "Trung Bình";
    public static final String LOW = "Nhẹ";

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
