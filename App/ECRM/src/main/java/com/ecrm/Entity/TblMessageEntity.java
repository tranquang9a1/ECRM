package com.ecrm.Entity;

import javax.persistence.*;

/**
 * Created by Htang on 7/20/2015.
 */
@Entity
@Table(name = "tblMessage")
public class TblMessageEntity {
    private int id;
    private String phone;
    private String body;
    private Boolean isRead;


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
    @Column(name = "Phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "Body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "IsRead")
    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public TblMessageEntity() {
    }

    public TblMessageEntity(int id, String phone, String body, Boolean isRead) {
        this.id = id;
        this.phone = phone;
        this.body = body;
        this.isRead = isRead;
    }
}
