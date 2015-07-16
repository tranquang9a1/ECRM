package com.fu.group10.capstone.apps.teachermobileapp.dao;

/**
 * Created by QuangTV on 7/11/2015.
 */
public class UserDAO {

    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String deviceId;

    public UserDAO(String username, String password, String fullname, String phone, String deviceId) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.deviceId = deviceId;
    }

    public UserDAO() {
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
