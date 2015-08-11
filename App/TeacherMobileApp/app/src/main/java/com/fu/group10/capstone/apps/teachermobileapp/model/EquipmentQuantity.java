package com.fu.group10.capstone.apps.teachermobileapp.model;

/**
 * Created by QuangTV on 8/11/2015.
 */
public class EquipmentQuantity {

    private int classId;
    private String equipmentName;
    private int quantity;
    private int priority;
    private boolean isDeleted;

    public EquipmentQuantity(int classId, String equipmentName, int quantity, int priority, boolean isDeleted) {
        this.classId = classId;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.priority = priority;
        this.isDeleted = isDeleted;
    }

    public EquipmentQuantity() {
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
