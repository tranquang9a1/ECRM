package com.ecrm.Entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblUser", schema = "dbo", catalog = "ecrm")
public class TblUserEntity {
    private int roleId;
    private String username;
    private String password;
    private boolean status;
    private Collection<TblReportEntity> tblReportsByUsername;
    private Collection<TblScheduleEntity> tblSchedulesByUsername;
    private TblRoleEntity tblRoleByRoleId;
    private TblUserInfoEntity tblUserInfoByUsername;

    @Basic
    @Column(name = "RoleId")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Id
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

        if (roleId != that.roleId) return false;
        if (status != that.status) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "tblUserByUserId")
    public Collection<TblReportEntity> getTblReportsByUsername() {
        return tblReportsByUsername;
    }

    public void setTblReportsByUsername(Collection<TblReportEntity> tblReportsByUsername) {
        this.tblReportsByUsername = tblReportsByUsername;
    }

    @OneToMany(mappedBy = "tblUserByUserId")
    public Collection<TblScheduleEntity> getTblSchedulesByUsername() {
        return tblSchedulesByUsername;
    }

    public void setTblSchedulesByUsername(Collection<TblScheduleEntity> tblSchedulesByUsername) {
        this.tblSchedulesByUsername = tblSchedulesByUsername;
    }

    @ManyToOne
    @JoinColumn(name = "RoleId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblRoleEntity getTblRoleByRoleId() {
        return tblRoleByRoleId;
    }

    public void setTblRoleByRoleId(TblRoleEntity tblRoleByRoleId) {
        this.tblRoleByRoleId = tblRoleByRoleId;
    }

    @OneToOne(mappedBy = "tblUserByUsername")
    public TblUserInfoEntity getTblUserInfoByUsername() {
        return tblUserInfoByUsername;
    }

    public void setTblUserInfoByUsername(TblUserInfoEntity tblUserInfoByUsername) {
        this.tblUserInfoByUsername = tblUserInfoByUsername;
    }
}
