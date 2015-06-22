package com.ecrm.DTO;

/**
 * Created by QuangTV on 6/14/2015.
 */
public class EquipmentDTO {
    private int reportId;
    private int equipmentId;
    private String equipmentName;
    private int quantity;
    private boolean status;
    private String evaluate;
    private String damage;

    public EquipmentDTO(int reportId, int equipmentId, String equipmentName, int quantity, boolean status, String evaluate, String damage) {
        this.reportId = reportId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.status = status;
        this.evaluate = evaluate;
        this.damage = damage;
    }

    public EquipmentDTO() {
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }
}
