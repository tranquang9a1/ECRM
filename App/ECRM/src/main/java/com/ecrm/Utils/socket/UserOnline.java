package com.ecrm.Utils.socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
public class UserOnline {
    private List<String> socketId;
    private String username;
    private int deviceType;
    private int role;

    public UserOnline(){}

    public UserOnline(List<String> socketId, String username, int deviceType, int role) {
        super();
        this.socketId = socketId;
        this.username = username;
        this.deviceType = deviceType;
        this.role = role;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public List<String> getSocketId() {
        return socketId;
    }

    public void setSocketId(List<String> socketId) {
        this.socketId = socketId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


    public static UserOnline checkContainIn(List<UserOnline> list, String username, String socketId, int deviceType) {

        if(username == null) {
            for (UserOnline item: list) {
                if(item.getSocketId().contains(socketId)) {
                    return item;
                }
            }
        } else {
            for (UserOnline item : list) {
                if (item.getUsername().equals(username) && item.getDeviceType() == deviceType) {
                    return item;
                }
            }
        }

        return null;
    }

    public static List<UserOnline> getUserByRole(List<UserOnline> list, int role){
        List<UserOnline> result = new ArrayList<UserOnline>();

        for (UserOnline user: list) {
            if(user.getRole() == role) {
                result.add(user);
            }
        }

        return result;
    }

    public static List<UserOnline> getUserByUsername(List<UserOnline> list, String username) {
        List<UserOnline> result = new ArrayList<UserOnline>();

        for (UserOnline item: list) {
            if(item.getUsername().equals(username)){
                result.add(item);
            }
        }

        return result;
    }
}
