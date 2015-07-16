package com.fu.group10.capstone.apps.teachermobileapp.dao;

import java.util.Date;

/**
 * Created by QuangTV on 7/11/2015.
 */
public class ScheduleDAO {
    private int id;
    private String username;
    private int classId;
    private String className;
    private String timeFrom;
    private String timeTo;
    private String date;
    private int isActive;

    public ScheduleDAO(int id, String username, int classId, String className, String timeFrom, String timeTo, String date, int isActive) {
        this.id = id;
        this.username = username;
        this.classId = classId;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date = date;
        this.isActive = isActive;
        this.className = className;
    }

    public ScheduleDAO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
