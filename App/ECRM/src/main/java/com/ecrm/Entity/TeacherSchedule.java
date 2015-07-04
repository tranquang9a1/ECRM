package com.ecrm.Entity;

/**
 * Created by Htang on 7/4/2015.
 */
public class TeacherSchedule {
    private String teacher;
    private String date;

    public TeacherSchedule() {
    }

    public TeacherSchedule(String teacher, String date) {
        this.teacher = teacher;
        this.date = date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
