package com.fu.group10.capstone.apps.teachermobileapp.model;

/**
 * Created by QuangTV on 7/30/2015.
 */
public class EquipmentCategory {

    private String id;
    private String name;
    private String imageUrl;

    public EquipmentCategory(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public EquipmentCategory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
