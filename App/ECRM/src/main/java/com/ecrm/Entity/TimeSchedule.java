package com.ecrm.Entity;

import java.util.List;

/**
 * Created by Htang on 7/4/2015.
 */
public class TimeSchedule {
    private String time;
    private List<TeacherSchedule> teacherSchedules;

    public TimeSchedule() {
    }

    public TimeSchedule(String time, List<TeacherSchedule> teacherSchedules) {
        this.time = time;
        this.teacherSchedules = teacherSchedules;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<TeacherSchedule> getTeacherSchedules() {
        return teacherSchedules;
    }

    public void setTeacherSchedules(List<TeacherSchedule> teacherSchedules) {
        this.teacherSchedules = teacherSchedules;
    }
}
