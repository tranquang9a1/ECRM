package com.fu.group10.capstone.apps.teachermobileapp.dao;

/**
 * Created by QuangTV on 7/19/2015.
 */
public class ClassroomDAO {

    private int classId;
    private String className;
    private int damageLevel;
    private int roomType;

    public ClassroomDAO(int classId, String className, int damageLevel) {
        this.classId = classId;
        this.className = className;
        this.damageLevel = damageLevel;
        this.roomType = 1;
    }

    public ClassroomDAO() {
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

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }
}
