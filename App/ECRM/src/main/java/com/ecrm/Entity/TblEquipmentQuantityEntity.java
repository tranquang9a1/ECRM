package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 7/28/2015.
 */
@Entity
@Table(name = "TblEquipmentQuantity")
@IdClass(TblEquipmentQuantityEntityPK.class)
public class TblEquipmentQuantityEntity {
    private int roomTypeId;
    private int equipmentCategoryId;
    private int quantity;

    private TblRoomTypeEntity2 tblRoomTypeEntity2ByRoomTypeId;
    private TblEquipmentCategoryEntity tblEquipmentCategoryEntityByEquipmentCategoryId;


    @Id
    @Column(name = "RoomTypeId")
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }


    @Id
    @Column(name = "EquipmentCategoryId")
    public int getEquipmentCategoryId() {
        return equipmentCategoryId;
    }

    public void setEquipmentCategoryId(int equipmentCategoryId) {
        this.equipmentCategoryId = equipmentCategoryId;
    }

    @Basic
    @Column(name = "Quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "RoomTypeId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblRoomTypeEntity2 getTblRoomType2ByRoomTypeId() {
        return tblRoomTypeEntity2ByRoomTypeId;
    }

    public void setTblRoomType2ByRoomTypeId(TblRoomTypeEntity2 tblEquipmentByEquipmentId) {
        this.tblRoomTypeEntity2ByRoomTypeId = tblRoomTypeEntity2ByRoomTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "EquipmentCategoryId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblEquipmentCategoryEntity getTblEquipmentCategoryEntityByEquipmentCategoryId() {
        return tblEquipmentCategoryEntityByEquipmentCategoryId;
    }

    public void setTblEquipmentCategoryEntityByEquipmentCategoryId(TblEquipmentCategoryEntity tblEquipmentCategoryEntityByEquipmentCategoryId) {
        this.tblEquipmentCategoryEntityByEquipmentCategoryId = tblEquipmentCategoryEntityByEquipmentCategoryId;
    }

    public TblEquipmentQuantityEntity() {
    }


}