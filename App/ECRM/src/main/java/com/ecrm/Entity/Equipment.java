package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 5/27/2015.
 */
@Entity(name = "tblEquipment")
public class Equipment {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="CategoryId", nullable = false)
    private
    EquipmentCategory equipmentCategory;

    @ManyToOne
    @JoinColumn(name="ClassroomId", nullable = false)
    private
    Classroom classroom;

    @Basic
    @Column(name = "Position", nullable = false, insertable = false, updatable = true, length = 45)
    private String position;

    @Basic
    @Column(name = "UsingTime", nullable = false, insertable = false, updatable = true)
    private int UsingTime;

    @Basic
    @Column(name = "Status", nullable = false, insertable = false, updatable = true, length = 45)
    private String status;



    public Equipment() {
    }

    public Equipment(EquipmentCategory equipmentCategory, Classroom classroom, String position, int usingTime, String status) {
        this.equipmentCategory = equipmentCategory;
        this.classroom = classroom;
        this.position = position;
        UsingTime = usingTime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EquipmentCategory getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(EquipmentCategory equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getUsingTime() {
        return UsingTime;
    }

    public void setUsingTime(int usingTime) {
        UsingTime = usingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
