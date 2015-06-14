package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblRoomType", schema = "dbo", catalog = "ecrm")
public class TblRoomTypeEntity {
    private int id;
    private String name;
    private int slots;
    private int verticalRows;
    private String horizontalRows;
    private String numberOfSlotsEachHRows;
    private int airConditioning;
    private int fan;
    private int projector;
    private int speaker;
    private int bulb;
    private int television;
    private Timestamp createTime;
    private Boolean isDelete;
    private Timestamp updateTime;
    private Collection<TblClassroomEntity> tblClassroomsById;

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
    @Column(name = "AirConditioning")
    public int getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(int airConditioning) {
        this.airConditioning = airConditioning;
    }

    @Basic
    @Column(name = "Fan")
    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    @Basic
    @Column(name = "Projector")
    public int getProjector() {
        return projector;
    }

    public void setProjector(int projector) {
        this.projector = projector;
    }

    @Basic
    @Column(name = "Speaker")
    public int getSpeaker() {
        return speaker;
    }

    public void setSpeaker(int speaker) {
        this.speaker = speaker;
    }

    @Basic
    @Column(name = "Bulb")
    public int getBulb() {
        return bulb;
    }

    public void setBulb(int bulb) {
        this.bulb = bulb;
    }

    @Basic
    @Column(name = "Television")
    public int getTelevision() {
        return television;
    }

    public void setTelevision(int television) {
        this.television = television;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblRoomTypeEntity that = (TblRoomTypeEntity) o;

        if (id != that.id) return false;
        if (slots != that.slots) return false;
        if (verticalRows != that.verticalRows) return false;
        if (airConditioning != that.airConditioning) return false;
        if (fan != that.fan) return false;
        if (projector != that.projector) return false;
        if (speaker != that.speaker) return false;
        if (television != that.television) return false;
        if (bulb != that.bulb) return false;
        if (horizontalRows != null ? !horizontalRows.equals(that.horizontalRows) : that.horizontalRows != null)
            return false;
        if (numberOfSlotsEachHRows != null ? !numberOfSlotsEachHRows.equals(that.numberOfSlotsEachHRows) : that.numberOfSlotsEachHRows != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + slots;
        result = 31 * result + verticalRows;
        result = 31 * result + (horizontalRows != null ? horizontalRows.hashCode() : 0);
        result = 31 * result + (numberOfSlotsEachHRows != null ? numberOfSlotsEachHRows.hashCode() : 0);
        result = 31 * result + airConditioning;
        result = 31 * result + fan;
        result = 31 * result + projector;
        result = 31 * result + speaker;
        result = 31 * result + bulb;
        result = 31 * result + television;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblRoomTypeByRoomTypeId")
    public Collection<TblClassroomEntity> getTblClassroomsById() {
        return tblClassroomsById;
    }

    public void setTblClassroomsById(Collection<TblClassroomEntity> tblClassroomsById) {
        this.tblClassroomsById = tblClassroomsById;
    }

    public TblRoomTypeEntity() {
    }

    public TblRoomTypeEntity(int id, int slots, int verticalRows, String horizontalRows, String numberOfSlotsEachHRows,
                             int airConditioning, int fan, int projector, int speaker, int bulb, int television, Timestamp createTime, Boolean isDelete, Timestamp updateTime) {
        this.id = id;
        this.slots = slots;
        this.verticalRows = verticalRows;
        this.horizontalRows = horizontalRows;
        this.numberOfSlotsEachHRows = numberOfSlotsEachHRows;
        this.airConditioning = airConditioning;
        this.fan = fan;
        this.projector = projector;
        this.speaker = speaker;
        this.bulb = bulb;
        this.television = television;
        this.createTime = createTime;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
    }
}
