package com.ecrm.Entity;

/**
 * Created by Htang on 8/4/2015.
 */
public class RoomWithFloorDTO {
    private String classroomName;
    private int damagedLevel;

    public RoomWithFloorDTO() {
    }

    public RoomWithFloorDTO(String classroomName, int damagedLevel) {
        this.classroomName = classroomName;
        this.damagedLevel = damagedLevel;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(int damagedLevel) {
        this.damagedLevel = damagedLevel;
    }
}
