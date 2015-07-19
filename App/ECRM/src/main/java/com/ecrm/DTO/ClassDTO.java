package com.ecrm.DTO;

/**
 * Created by QuangTV on 7/19/2015.
 */
public class ClassDTO {

    private int classId;
    private String className;
    private int damageLevel;

    public ClassDTO(int classId, String className, int damageLevel) {
        this.classId = classId;
        this.className = className;
        this.damageLevel = damageLevel;
    }

    public ClassDTO() {
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
}
