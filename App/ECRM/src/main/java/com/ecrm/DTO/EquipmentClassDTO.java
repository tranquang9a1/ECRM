package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/15/2015.
 */
public class EquipmentClassDTO {


    private String equipmentName;
    private String timeRemain;
    private String company;
    private boolean damaged;

    public EquipmentClassDTO(String equipmentName, String timeRemain, String company, boolean damaged) {
        this.equipmentName = equipmentName;
        this.timeRemain = timeRemain;
        this.company = company;
        this.damaged = damaged;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(String timeRemain) {
        this.timeRemain = timeRemain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
}
