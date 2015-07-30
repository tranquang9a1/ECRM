package com.ecrm.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Htang on 7/28/2015.
 */
public class TblEquipmentQuantityEntityPK implements Serializable {
    private Integer roomTypeId;
    private Integer equipmentCategoryId;
    private int id;

    @Column(name = "RoomTypeId")
    @Id
    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    @Column(name = "EquipmentCategoryId")
    @Id
    public Integer getEquipmentCategoryId() {
        return equipmentCategoryId;
    }

    public void setEquipmentCategoryId(Integer equipmentCategoryId) {
        this.equipmentCategoryId = equipmentCategoryId;
    }

    @Column(name = "Id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
