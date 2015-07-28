package com.ecrm.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Htang on 7/28/2015.
 */
public class TblEquipmentQuantityEntityPK implements Serializable {
    private int roomTypeId;
    private int equipmentCategoryId;

    @Column(name = "RoomTypeId")
    @Id
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    @Column(name = "EquipmentCategoryId")
    @Id
    public int getEquipmentCategoryId() {
        return equipmentCategoryId;
    }

    public void setEquipmentCategoryId(int equipmentCategoryId) {
        this.equipmentCategoryId = equipmentCategoryId;
    }
}
