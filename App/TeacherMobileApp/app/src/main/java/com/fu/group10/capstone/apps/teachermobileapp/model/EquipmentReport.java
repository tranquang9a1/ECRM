package com.fu.group10.capstone.apps.teachermobileapp.model;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class EquipmentReport {

    private String equipmentName;
    private int quantity;
    private String damagedLevel;
    private String solution;
    private boolean status;

    public EquipmentReport(String equipmentName, int quantity, String damagedLevel, String solution, boolean status) {
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.damagedLevel = damagedLevel;
        this.solution = solution;
        this.status = status;
    }

    public EquipmentReport() {
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(String damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
