package com.fu.group10.apps.teacher.model;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class User {
    public static String username;
    public static String phoneNumber;
    public static int classId;
    public static boolean role;

    public User(String username, String phoneNumber, int classId, boolean role) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.classId = classId;
        this.role = role;
    }


    @Override
    public String toString() {
        return username;
    }
}
