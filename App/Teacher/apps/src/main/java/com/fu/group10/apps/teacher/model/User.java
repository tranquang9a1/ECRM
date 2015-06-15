package com.fu.group10.apps.teacher.model;

import java.util.List;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class User {
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String role;
    private Long lastLogin;
    private boolean status;
    private List<ClassroomInfo> classrooms;

    public User(String username, String password, String fullname, String phone, String role, Long lastLogin,
                boolean status, List<ClassroomInfo> classrooms) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.role = role;
        this.lastLogin = lastLogin;
        this.status = status;
        this.classrooms = classrooms;
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

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ClassroomInfo> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassroomInfo> classrooms) {
        this.classrooms = classrooms;
    }
}
