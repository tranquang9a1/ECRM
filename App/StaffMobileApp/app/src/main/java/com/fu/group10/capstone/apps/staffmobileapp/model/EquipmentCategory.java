package com.fu.group10.capstone.apps.staffmobileapp.model;

/**
 * Created by QuangTV on 7/30/2015.
 */
public class EquipmentCategory {

    private String name;
    private String imageUrl;

    public EquipmentCategory(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public EquipmentCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
