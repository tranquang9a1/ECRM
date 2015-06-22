package com.fu.group10.apps.teacher.model;

/**
 * Created by QuangTV on 6/16/2015.
 */
public class Equipment {
    private String equipmentName;
    private boolean status;
    private int damaged;
    private String evaluate;

    public Equipment(String equipmentName, boolean status, int damaged, String evaluate) {
        this.equipmentName = equipmentName;
        this.status = status;
        this.damaged = damaged;
        this.evaluate = evaluate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getDamaged() {
        return damaged;
    }

    public void setDamaged(int damaged) {
        this.damaged = damaged;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}
