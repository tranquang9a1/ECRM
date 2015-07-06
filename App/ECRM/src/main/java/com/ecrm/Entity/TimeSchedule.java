package com.ecrm.Entity;

import java.util.List;

/**
 * Created by Htang on 7/4/2015.
 */
public class TimeSchedule {
    private String timeFrom;
    private String timeTo;
    private int scheduleConfigId;
    private List<TeacherSchedule> teacherSchedules;

    public TimeSchedule() {
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

    public List<TeacherSchedule> getTeacherSchedules() {
        return teacherSchedules;
    }

    public void setTeacherSchedules(List<TeacherSchedule> teacherSchedules) {
        this.teacherSchedules = teacherSchedules;
    }

    public int getScheduleConfigId() {
        return scheduleConfigId;
    }

    public void setScheduleConfigId(int scheduleConfigId) {
        this.scheduleConfigId = scheduleConfigId;
    }
}
