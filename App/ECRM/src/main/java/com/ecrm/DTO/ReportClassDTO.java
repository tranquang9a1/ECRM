package com.ecrm.DTO;

import java.util.List;

/**
 * Created by Thuy_Tien on 6/12/2015.
 */
public class ReportClassDTO {

    private int roomId;
    private String roomName;
    private String timeReport;
    private int damageLevel;
    private String evaluate;
    private String userReport;
    private int systemEvaluate;
    private String changedRoom;
    private List<EquipmentDTO> equipments;

    public ReportClassDTO(int roomId, String roomName, String timeReport, int damageLevel, String evaluate, String userReport, int systemEvaluate,String changedRoom, List<EquipmentDTO> equipments) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.timeReport = timeReport;
        this.damageLevel = damageLevel;
        this.evaluate = evaluate;
        this.userReport = userReport;
        this.systemEvaluate = systemEvaluate;
        this.changedRoom = changedRoom;
        this.equipments = equipments;
    }

    public ReportClassDTO() {
    }

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

    public String getTimeReport() {
        return timeReport;
    }

    public void setTimeReport(String timeReport) {
        this.timeReport = timeReport;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getUserReport() {
        return userReport;
    }

    public void setUserReport(String userReport) {
        this.userReport = userReport;
    }

    public int getSystemEvaluate() {
        return systemEvaluate;
    }

    public void setSystemEvaluate(int systemEvaluate) {
        this.systemEvaluate = systemEvaluate;
    }

    public String getChangedRoom() {
        return changedRoom;
    }

    public void setChangedRoom(String changedRoom) {
        this.changedRoom = changedRoom;
    }

    public List<EquipmentDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentDTO> equipments) {
        this.equipments = equipments;
    }
}
