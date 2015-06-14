package com.ecrm.DTO;

import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblRoomTypeEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/13/2015.
 */
public class DamagedRoomDTO {
    private int roomId;
    private String roomName;
    private String reporters;
    private Date reportDate;
    private int damagedLevel;
    private String evaluate;

    private List<String> suggestRooms;
    private TblRoomTypeEntity roomtype;
    private List<TblEquipmentEntity> equipments;

    public DamagedRoomDTO(TblClassroomEntity room, TblReportEntity report) {
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.reportDate = report.getCreateTime();
        this.evaluate = report.getEvaluate();
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

    public String getReporters() {
        return reporters;
    }

    public void setReporters(String reporters) {
        this.reporters = reporters;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(int damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public List<String> getSuggestRooms() {
        return suggestRooms;
    }

    public void setSuggestRooms(List<String> suggestRooms) {
        this.suggestRooms = suggestRooms;
    }

    public TblRoomTypeEntity getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(TblRoomTypeEntity roomtype) {
        this.roomtype = roomtype;
    }

    public List<TblEquipmentEntity> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<TblEquipmentEntity> equipments) {
        this.equipments = equipments;
    }
}
