package com.ecrm.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Htang on 5/27/2015.
 */
@Entity(name = "tblClassroom")
public class Classroom {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="RoomTypeId", nullable = true)
    private
    RoomType roomType;

    @Basic
    @Column(name = "Name", nullable = false, insertable = true, updatable = true, length = 45)
    private String name;

    @Basic
    @Column(name = "DamagedLevel", nullable = true, insertable = true, updatable = true)
    private int damagedLevel;

    @Basic
    @Column(name = "CreateTime", nullable = false, insertable = true, updatable = true, columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Basic
    @Column(name = "UpdateTime", nullable = true, insertable = true, updatable = true, columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Classroom() {
    }

    public Classroom(RoomType roomType, String name, int damagedLevel, Date createTime, Date updateTime) {
        this.roomType = roomType;
        this.name = name;
        this.damagedLevel = damagedLevel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Classroom(RoomType roomType, String name, int damagedLevel, Date createTime) {
        this.roomType = roomType;
        this.name = name;
        this.damagedLevel = damagedLevel;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(int damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
