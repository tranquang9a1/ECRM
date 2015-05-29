package com.ecrm.Entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 5/29/2015.
 */
@Entity
@Table(name = "tblUser", schema = "dbo", catalog = "ecrm")
public class TblUserEntity {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private boolean status;
    private TblRoleEntity tblRoleByRoleId;
    private int roleId;
    private Collection<TblReportEntity> tblReportsById;
    private Collection<TblScheduleEntity> tblSchedulesById;

    @Id
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
    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Fullname")
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblUserEntity that = (TblUserEntity) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "RoleId", referencedColumnName = "Id", nullable = false , insertable = false, updatable = false)
    public TblRoleEntity getTblRoleByRoleId() {
        return tblRoleByRoleId;
    }

    public void setTblRoleByRoleId(TblRoleEntity tblRoleByRoleId) {
        this.tblRoleByRoleId = tblRoleByRoleId;
    }

    @Basic
    @Column(name = "RoleId")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @OneToMany(mappedBy = "tblUserByUserId")
    public Collection<TblReportEntity> getTblReportsById() {
        return tblReportsById;
    }

    public void setTblReportsById(Collection<TblReportEntity> tblReportsById) {
        this.tblReportsById = tblReportsById;
    }

    @OneToMany(mappedBy = "tblUserByUserId")
    public Collection<TblScheduleEntity> getTblSchedulesById() {
        return tblSchedulesById;
    }

    public void setTblSchedulesById(Collection<TblScheduleEntity> tblSchedulesById) {
        this.tblSchedulesById = tblSchedulesById;
    }

    public TblUserEntity() {
    }
}
