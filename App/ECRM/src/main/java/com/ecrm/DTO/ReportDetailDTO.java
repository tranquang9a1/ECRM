package com.ecrm.DTO;

import java.sql.Timestamp;

/**
 * Created by Thuy_Tien on 6/11/2015.
 */
public class ReportDetailDTO {
    private String equipmentName;
    private String description;
    private String damaged;
    private boolean status;
    private String solution;
    private Timestamp resolveTime;

    public ReportDetailDTO(String equipmentName, String description, String damaged, boolean status, String solution, Timestamp resolveTime) {
        this.equipmentName = equipmentName;
        this.description = description;
        this.damaged = damaged;
        this.status = status;
        this.solution = solution;
        this.resolveTime = resolveTime;
    }

    public ReportDetailDTO() {
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

    public Timestamp getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(Timestamp resolveTime) {
        this.resolveTime = resolveTime;
    }
}
