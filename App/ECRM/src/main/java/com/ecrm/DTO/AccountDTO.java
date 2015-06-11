package com.ecrm.DTO;

import java.sql.Timestamp;

/**
 * Created by Thuy_Tien on 6/8/2015.
 */
public class AccountDTO {
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String role;
    private Timestamp lastLogin;
    private boolean status;
    private int classId;

    public AccountDTO(String username, String password, String fullname, String phone, String role, Timestamp lastLogin,
                      boolean status, int classId) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.role = role;
        this.lastLogin = lastLogin;
        this.status = status;
        this.classId = classId;
    }

    public AccountDTO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
