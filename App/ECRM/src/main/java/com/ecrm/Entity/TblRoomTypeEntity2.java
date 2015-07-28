package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Htang on 7/28/2015.
 */
@Entity
@Table(name = "tblRoomType2")
public class TblRoomTypeEntity2 {
    private int id;
    private String name;
    private int slots;
    private int verticalRows;
    private String horizontalRows;
    private String numberOfSlotsEachHRows;
    private Timestamp createTime;
    private Boolean isDelete;
    private Timestamp updateTime;
    private Collection<TblClassroomEntity> tblClassroomsById;
    private Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityById;

    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name="Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Slots")
    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    @Basic
    @Column(name = "VerticalRows")
    public int getVerticalRows() {
        return verticalRows;
    }

    public void setVerticalRows(int verticalRows) {
        this.verticalRows = verticalRows;
    }

    @Basic
    @Column(name = "HorizontalRows")
    public String getHorizontalRows() {
        return horizontalRows;
    }

    public void setHorizontalRows(String horizontalRows) {
        this.horizontalRows = horizontalRows;
    }

    @Basic
    @Column(name = "NumberOfSlotsEachHRows")
    public String getNumberOfSlotsEachHRows() {
        return numberOfSlotsEachHRows;
    }

    public void setNumberOfSlotsEachHRows(String numberOfSlotsEachHRows) {
        this.numberOfSlotsEachHRows = numberOfSlotsEachHRows;
    }

    @Basic
    @Column(name = "CreateTime")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "IsDelete")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "UpdateTime")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }



    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + slots;
        result = 31 * result + verticalRows;
        result = 31 * result + (horizontalRows != null ? horizontalRows.hashCode() : 0);
        result = 31 * result + (numberOfSlotsEachHRows != null ? numberOfSlotsEachHRows.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblRoomType2ByRoomTypeId2")
    public Collection<TblClassroomEntity> getTblClassroomsById() {
        return tblClassroomsById;
    }

    public void setTblClassroomsById(Collection<TblClassroomEntity> tblClassroomsById) {
        this.tblClassroomsById = tblClassroomsById;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblRoomType2ByRoomTypeId")
    public Collection<TblEquipmentQuantityEntity> getTblEquipmentQuantityById() {
        return tblEquipmentQuantityById;
    }

    public void setTblEquipmentQuantityById(Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityById) {
        this.tblEquipmentQuantityById = tblEquipmentQuantityById;
    }

    public TblRoomTypeEntity2() {
    }

    public TblRoomTypeEntity2(int id, String name, int slots, int verticalRows, String horizontalRows, String numberOfSlotsEachHRows,Timestamp createTime, Boolean isDelete, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.slots = slots;
        this.verticalRows = verticalRows;
        this.horizontalRows = horizontalRows;
        this.numberOfSlotsEachHRows = numberOfSlotsEachHRows;
        this.createTime = createTime;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
    }


}
