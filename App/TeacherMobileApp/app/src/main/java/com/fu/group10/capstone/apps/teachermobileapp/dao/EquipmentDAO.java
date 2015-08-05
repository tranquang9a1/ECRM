package com.fu.group10.capstone.apps.teachermobileapp.dao;

/**
 * Created by QuangTV on 7/30/2015.
 */
public class EquipmentDAO {

    private String id;
    private String name;
    private byte[] images;

    public EquipmentDAO(String id, String name, byte[] images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public EquipmentDAO() {
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

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
