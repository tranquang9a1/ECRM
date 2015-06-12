package com.ecrm.DTO;

/**
 * Created by Htang on 6/12/2015.
 */
public class ClassroomDTO {
    private int classId;
    private String classroomName;
    private String timeFrom;
    private String timeTo;

    public ClassroomDTO() {
    }

    public ClassroomDTO(int classId, String classroomName, String timeFrom, String timeTo) {
        this.classId = classId;
        this.classroomName = classroomName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
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
}
