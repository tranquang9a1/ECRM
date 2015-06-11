package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/11/2015.
 */
public class ReportDetailDTO {
    private String equipmentName;
    private String description;
    private String damaged;

    public ReportDetailDTO(String equipmentName, String description, String damaged) {
        this.equipmentName = equipmentName;
        this.description = description;
        this.damaged = damaged;
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
}
