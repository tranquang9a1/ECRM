package com.ecrm.DTO;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/14/2015.
 */
public class ReportRequestDTO {
    private int roomId;
    private String evaluate;
    private String listDamaged;
    private String listEvaluate;
    private List<String> listDesc;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getListDamaged() {
        return listDamaged;
    }

    public void setListDamaged(String listDamaged) {
        this.listDamaged = listDamaged;
    }

    public String getListEvaluate() {
        return listEvaluate;
    }

    public void setListEvaluate(String listEvaluate) {
        this.listEvaluate = listEvaluate;
    }

    public List<String> getListDesc() {
        return listDesc;
    }

    public void setListDesc(List<String> listDesc) {
        this.listDesc = listDesc;
    }
}
