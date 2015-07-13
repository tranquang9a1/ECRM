package com.ecrm.DTO;

/**
 * Created by Htang on 6/16/2015.
 */
public class ScheduleDTO {
    private int classId;
    private String className;
    private String timeFrom;
    private String timeTo;
    private String date;

    public ScheduleDTO(int classId, String className, String timeFrom, String timeTo, String date) {
        this.classId = classId;
        this.className = className;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date = date;
    }

    public ScheduleDTO() {
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
}
