package com.ecrm.DTO;

/**
 * Created by Thuy_Tien on 6/11/2015.
 */
public class ReportDetailDTO {
    private String equipmentName;
    private String desription;
    private String damaged;

    public ReportDetailDTO(String equipmentName, String desription, String damaged) {
        this.equipmentName = equipmentName;
        this.desription = desription;
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

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }
}
