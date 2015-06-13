package com.ecrm.DTO;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/13/2015.
 */
public class GroupReportsDTO {
    private int roomId;
    private String roomName;
    private String listEquipments;
    private String damagedLevel;
    private String reporters;

    public GroupReportsDTO(){}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getListEquipments() {
        return listEquipments;
    }

    public void setListEquipments(String listEquipments) {
        this.listEquipments = listEquipments;
    }

    public String getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(String damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    public String getReporters() {
        return reporters;
    }

    public void setReporters(String reporters) {
        this.reporters = reporters;
    }

    public boolean containIn(List<GroupReportsDTO> list){

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getRoomId() == this.roomId) {
                return true;
            }
        }

        return false;
    }
}
