package com.fu.group10.capstone.apps.teachermobileapp.model;

/**
 * Created by QuangTV on 6/10/2015.
 */
public class EquipmentInfo {
    private String name;
    private int quantity;

    public EquipmentInfo(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public EquipmentInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
