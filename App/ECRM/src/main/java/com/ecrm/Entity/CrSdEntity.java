package com.ecrm.Entity;

import java.util.List;

/**
 * Created by Htang on 7/4/2015.
 */
public class CrSdEntity {
    private String roomName;
    private List<TimeSchedule> timeSchedules;
    private int rowspan;

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public CrSdEntity() {
    }

    public CrSdEntity(String roomName, List<TimeSchedule> timeSchedules) {
        this.roomName = roomName;
        this.timeSchedules = timeSchedules;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<TimeSchedule> getTimeSchedules() {
        return timeSchedules;
    }

    public void setTimeSchedules(List<TimeSchedule> timeSchedules) {
        this.timeSchedules = timeSchedules;
    }
}
