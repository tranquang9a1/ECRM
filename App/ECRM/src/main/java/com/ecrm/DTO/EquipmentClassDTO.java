package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/15/2015.
 */
public class EquipmentClassDTO {

    private int equipmentId;
    private String equipmentName;

    public EquipmentClassDTO(int equipmentId, String equipmentName) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
    }

    public EquipmentClassDTO() {

    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
