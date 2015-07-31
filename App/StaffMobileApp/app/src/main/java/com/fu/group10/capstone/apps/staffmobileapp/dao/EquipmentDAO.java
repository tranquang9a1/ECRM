package com.fu.group10.capstone.apps.staffmobileapp.dao;

/**
 * Created by QuangTV on 7/30/2015.
 */
public class EquipmentDAO {

    private String name;
    private byte[] images;

    public EquipmentDAO(String name, byte[] images) {
        this.name = name;
        this.images = images;
    }

    public EquipmentDAO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
