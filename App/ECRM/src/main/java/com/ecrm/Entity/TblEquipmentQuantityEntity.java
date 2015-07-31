package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 7/28/2015.
 */
@Entity
@Table(name = "tblEquipmentQuantity")
@IdClass(TblEquipmentQuantityEntityPK.class)
public class TblEquipmentQuantityEntity {
    private Integer roomTypeId;
    private Integer equipmentCategoryId;
    private int quantity;
    private int priority;
    private boolean isDelete;
    private int id;


    private TblRoomTypeEntity2 tblRoomTypeEntity2ByRoomTypeId;
    private TblEquipmentCategoryEntity tblEquipmentCategoryEntityByEquipmentCategoryId;



    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Id
    @Column(name = "RoomTypeId", nullable = true)
    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }


    @Id
    @Column(name = "EquipmentCategoryId")
    public Integer getEquipmentCategoryId() {
        return equipmentCategoryId;
    }

    public void setEquipmentCategoryId(Integer equipmentCategoryId) {
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

    @Basic
    @Column(name = "Priority")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "IsDelete")
    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }



    @ManyToOne
    @JoinColumn(name = "RoomTypeId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblRoomTypeEntity2 getTblRoomType2ByRoomTypeId() {
        return tblRoomTypeEntity2ByRoomTypeId;
    }

    public void setTblRoomType2ByRoomTypeId(TblRoomTypeEntity2 tblRoomTypeEntity2ByRoomTypeId) {
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
