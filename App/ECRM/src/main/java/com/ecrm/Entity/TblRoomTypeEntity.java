package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Htang on 5/29/2015.
 */
@Entity
@Table(name = "tblRoomType", schema = "dbo", catalog = "ecrm")
public class TblRoomTypeEntity {
    private int id;
    private int slots;
    private int verticalRows;
    private String horizontalRows;
    private String noSlotsEachHRows;
    private boolean airConditioning;
    private boolean fan;
    private boolean projector;
    private boolean speaker;
    private boolean television;
    private Timestamp createTime;
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
    @Column(name = "NoSlotsEachHRows")
    public String getNoSlotsEachHRows() {
        return noSlotsEachHRows;
    }

    public void setNoSlotsEachHRows(String noSlotsEachHRows) {
        this.noSlotsEachHRows = noSlotsEachHRows;
    }

    @Basic
    @Column(name = "AirConditioning")
    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    @Basic
    @Column(name = "Fan")
    public boolean isFan() {
        return fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }

    @Basic
    @Column(name = "Projector")
    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    @Basic
    @Column(name = "Speaker")
    public boolean isSpeaker() {
        return speaker;
    }

    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }

    @Basic
    @Column(name = "Television")
    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
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
        if (horizontalRows != null ? !horizontalRows.equals(that.horizontalRows) : that.horizontalRows != null)
            return false;
        if (noSlotsEachHRows != null ? !noSlotsEachHRows.equals(that.noSlotsEachHRows) : that.noSlotsEachHRows != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + slots;
        result = 31 * result + verticalRows;
        result = 31 * result + (horizontalRows != null ? horizontalRows.hashCode() : 0);
        result = 31 * result + (noSlotsEachHRows != null ? noSlotsEachHRows.hashCode() : 0);
        result = 31 * result + (airConditioning ? 1 : 0);
        result = 31 * result + (fan ? 1 : 0);
        result = 31 * result + (projector ? 1 : 0);
        result = 31 * result + (speaker ? 1 : 0);
        result = 31 * result + (television ? 1 : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tblRoomTypeByRoomTypeId")
    public Collection<TblClassroomEntity> getTblClassroomsById() {
        return tblClassroomsById;
    }

    public void setTblClassroomsById(Collection<TblClassroomEntity> tblClassroomsById) {
        this.tblClassroomsById = tblClassroomsById;
    }

    public TblRoomTypeEntity() {
    }

    public TblRoomTypeEntity(int id, int slots, int verticalRows, String horizontalRows, String noSlotsEachHRows,
                             boolean airConditioning, boolean fan, boolean projector, boolean speaker, boolean television,
                             Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.slots = slots;
        this.verticalRows = verticalRows;
        this.horizontalRows = horizontalRows;
        this.noSlotsEachHRows = noSlotsEachHRows;
        this.airConditioning = airConditioning;
        this.fan = fan;
        this.projector = projector;
        this.speaker = speaker;
        this.television = television;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
