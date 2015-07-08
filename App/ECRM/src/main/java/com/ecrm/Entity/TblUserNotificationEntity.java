package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by ChiDNMSE60717 on 7/8/2015.
 */
@Entity
@Table(name = "tblUserNotification")
public class TblUserNotificationEntity {
    private int id;
    private String username;
    private int notificationId;
    private boolean status;
    private TblUserEntity tblUserByUsername;
    private TblNotificationEntity tblNotificationById;

    public TblUserNotificationEntity() {}

    public TblUserNotificationEntity(String username, int notificationId, boolean status) {
        this.username = username;
        this.notificationId = notificationId;
        this.status = status;
    }

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
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "NotificationId")
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @Basic
    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false, insertable = false, updatable = false)
    public TblUserEntity getTblUserByUsername() {
        return tblUserByUsername;
    }

    public void setTblUserByUsername(TblUserEntity tblUserEntity) {
        this.tblUserByUsername = tblUserEntity;
    }

    @ManyToOne
    @JoinColumn(name = "NotificationId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblNotificationEntity getTblNotificationById() {
        return tblNotificationById;
    }

    public void setTblNotificationById(TblNotificationEntity tblNotificationEntity) {
        this.tblNotificationById = tblNotificationEntity;
    }
}
