package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/11/2015.
 */
public class ClassroomDTO {
    private int classId;
    private String classroomName;
    private String timeFrom;
    private String timeTo;

    public ClassroomDTO(int classId, String timeFrom, String timeTo, String classroomName) {
        this.classId = classId;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.classroomName = classroomName;
    }

    public ClassroomDTO() {

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

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}
