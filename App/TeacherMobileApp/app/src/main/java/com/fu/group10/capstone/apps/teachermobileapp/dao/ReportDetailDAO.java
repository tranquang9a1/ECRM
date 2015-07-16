package com.fu.group10.capstone.apps.teachermobileapp.dao;

/**
 * Created by QuangTV on 7/13/2015.
 */
public class ReportDetailDAO {
    private String equipmentName;
    private String description;
    private String damaged;
    private boolean status;
    private String solution;
    private String resolveTime;

    public ReportDetailDAO(String equipmentName, String description, String damaged, boolean status) {
        this.equipmentName = equipmentName;
        this.description = description;
        this.damaged = damaged;
        this.status = status;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(String resolveTime) {
        this.resolveTime = resolveTime;
    }
}
