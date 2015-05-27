package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Htang on 5/27/2015.
 */
@Entity(name = "tblReport")
public class Report {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="EquipmentId", nullable = false)
    private
    Equipment equipment;

    @ManyToOne
    @JoinColumn(name="UserId", nullable = false)
    private
    User user;

    @Basic
    @Column(name = "Description", nullable = true, insertable = false, updatable = true, length = 250)
    private String description;

    @Basic
    @Column(name = "Status", nullable = false, insertable = false, updatable = true, length = 50)
    private String status;

    @Basic
    @Column(name = "CreateTime", nullable = false, insertable = true, updatable = true)
    private Timestamp createTime;

    @Basic
    @Column(name = "ResolveTime", nullable = true, insertable = true, updatable = true, length = 50)
    private String resolveTime;

    public Report() {
    }

    public Report(Equipment equipment, User user, String description, String status, Timestamp createTime, String resolveTime) {
        this.equipment = equipment;
        this.user = user;
        this.description = description;
        this.status = status;
        this.createTime = createTime;
        this.resolveTime = resolveTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(String resolveTime) {
        this.resolveTime = resolveTime;
    }
}
