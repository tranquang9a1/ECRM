package com.ecrm.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/26/2015.
 */
public class GroupUser {
    private String username;
    private String phone;
    private String currentRoom;
    private String changeRoom;
    private List<String> listTime;

    public GroupUser(String username, String phone, String currentRoom, String changeRoom) {
        this.username = username;
        this.phone = phone;
        this.currentRoom = currentRoom;
        this.changeRoom = changeRoom;
        this.listTime = new ArrayList<String>();
    }

    public static GroupUser checkContainIn(List<GroupUser> groups, String username){
        for (GroupUser item: groups){
            if (item.getUsername().equals(username)) {
                return item;
            }
        }

        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getListTime() {
        return listTime;
    }

    public void setListTime(List<String> listTime) {
        this.listTime = listTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getChangeRoom() {
        return changeRoom;
    }

    public void setChangeRoom(String changeRoom) {
        this.changeRoom = changeRoom;
    }

    public String toString() {
        String result = "Ngày " + (new Date()).getDate() + "/" + (new Date()).getMonth() + "/" + (new Date()).getYear() + ", bạn được chuyền từ phòng " + this.currentRoom + " sang phòng " + this.changeRoom + " vào các tiết: ";
        for (String time: listTime){
            result += time + ", ";
        }

        return result.substring(0, result.length()-2);
    }
}
