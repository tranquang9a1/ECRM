package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblClassroom")
public class TblClassroomEntity {
    private int id;
    private String name;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Boolean isDelete;
    private Boolean isAllInformation;
    private Integer damagedLevel;
    private Integer roomTypeId2;
    private TblRoomTypeEntity2 tblRoomType2ByRoomTypeId2;
    private List<TblEquipmentEntity> tblEquipmentsById;
    private List<TblReportEntity> tblReportsById;
    private List<TblScheduleEntity> tblSchedulesById;

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
    @Column(name = "RoomTypeId2")
    public Integer getRoomTypeId2() {
        return roomTypeId2;
    }

    public void setRoomTypeId2(Integer roomTypeId2) {
        this.roomTypeId2 = roomTypeId2;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Basic
    @Column(name = "IsDelete")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "IsAllInformation")
    public Boolean getIsAllInformation() {
        return isAllInformation;
    }

    public void setIsAllInformation(Boolean isAllInformation) {
        this.isAllInformation = isAllInformation;
    }

    @Basic
    @Column(name = "DamagedLevel")
    public Integer getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(Integer damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblClassroomEntity that = (TblClassroomEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }


    @ManyToOne
    @JoinColumn(name = "RoomTypeId2", referencedColumnName = "Id", insertable = false, updatable = false)
    public TblRoomTypeEntity2 getTblRoomType2ByRoomTypeId2() {
        return tblRoomType2ByRoomTypeId2;
    }

    public void setTblRoomType2ByRoomTypeId2(TblRoomTypeEntity2 tblRoomType2ByRoomTypeId2) {
        this.tblRoomType2ByRoomTypeId2 = tblRoomType2ByRoomTypeId2;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblClassroomByClassroomId")
    public List<TblEquipmentEntity> getTblEquipmentsById() {
        return tblEquipmentsById;
    }

    public void setTblEquipmentsById(List<TblEquipmentEntity> tblEquipmentsById) {
        this.tblEquipmentsById = tblEquipmentsById;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblClassroomByClassRoomId")
    public List<TblReportEntity> getTblReportsById() {
        return tblReportsById;
    }

    public void setTblReportsById(List<TblReportEntity> tblReportsById) {
        this.tblReportsById = tblReportsById;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblClassroomByClassroomId")
    public List<TblScheduleEntity> getTblSchedulesById() {
        return tblSchedulesById;
    }

    public void setTblSchedulesById(List<TblScheduleEntity> tblSchedulesById) {
        this.tblSchedulesById = tblSchedulesById;
    }

    public TblClassroomEntity() {
    }

    public TblClassroomEntity(int id, String name, Timestamp createTime,
                              Timestamp updateTime, Boolean isDelete, Boolean isAllInformation, Integer damagedLevel) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.isAllInformation = isAllInformation;
        this.damagedLevel = damagedLevel;
    }

    public TblClassroomEntity(int id, String name, Timestamp createTime, Timestamp updateTime,
                              Boolean isDelete, Boolean isAllInformation, Integer damagedLevel, Integer roomTypeId2) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.isAllInformation = isAllInformation;
        this.damagedLevel = damagedLevel;
        this.roomTypeId2 = roomTypeId2;
    }
}
