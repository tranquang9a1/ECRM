package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblUserInfo", schema = "dbo", catalog = "ecrm")
public class TblUserInfoEntity {
    private String username;
    private String fullName;
    private String phone;
    private Timestamp lastLogin;
    private TblUserEntity tblUserByUsername;

    @Id
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "FullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    @Column(name = "LastLogin")
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblUserInfoEntity that = (TblUserInfoEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (lastLogin != null ? !lastLogin.equals(that.lastLogin) : that.lastLogin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false)
    public TblUserEntity getTblUserByUsername() {
        return tblUserByUsername;
    }

    public void setTblUserByUsername(TblUserEntity tblUserByUsername) {
        this.tblUserByUsername = tblUserByUsername;
    }
}
