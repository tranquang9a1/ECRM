package com.ecrm.Entity;

import javax.persistence.*;

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
    @Column(name = "Name", nullable = false, insertable = false, updatable = true, length = 45)
    private String name;

    @Basic
    @Column(name = "DamagedLevel", nullable = true, insertable = false, updatable = true, length = 45)
    private int damagedLevel;

    public Classroom() {
    }

    public Classroom(RoomType roomType, String name, int damagedLevel) {
        this.roomType = roomType;
        this.name = name;
        this.damagedLevel = damagedLevel;
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
}
