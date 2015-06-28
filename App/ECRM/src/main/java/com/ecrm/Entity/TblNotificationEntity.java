package com.ecrm.Entity;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ChiDNMSE60717 on 6/24/2015.
 */
@Entity
@Table(name = "tblNotification")
public class TblNotificationEntity {
    private int id;
    private int classroomId;
    private String message;
    private Timestamp createTime;
    private String redirectLink;
    private int messageType;
    private boolean status;
    private TblClassroomEntity tblClassroomEntity;

    public TblNotificationEntity() {}

    public TblNotificationEntity(int room, String message, String redirectLink, int messageType) {
        this.classroomId = room;
        this.message = message;
        this.createTime = new Timestamp(new Date().getTime());
        this.redirectLink = redirectLink;
        this.messageType = messageType;
        this.status = true;
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
    @Column(name = "ClassroomId")
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    @Basic
    @Column(name = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    @Column(name = "RedirectLink")
    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    @Basic
    @Column(name = "MessageType")
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
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
    @JoinColumn(name = "ClassroomId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblClassroomEntity getTblClassroomEntity() {
        return tblClassroomEntity;
    }

    public void setTblClassroomEntity(TblClassroomEntity tblClassroomEntity) {
        this.tblClassroomEntity = tblClassroomEntity;
    }
}
