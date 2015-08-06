package com.ecrm.DTO;

import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.json.simple.JSONArray;

/**
 * Created by Htang on 7/29/2015.
 */
public class ClassroomMapDTO {
    TblClassroomEntity classroom;
    private TblRoomTypeEntity roomType;
    private JSONArray equipment;

    public ClassroomMapDTO() {
    }

    public ClassroomMapDTO(TblClassroomEntity tblClassroomEntity, TblRoomTypeEntity roomType, JSONArray equipment) {
        this.classroom = tblClassroomEntity;
        this.roomType = roomType;
        this.equipment = equipment;
    }

    public TblClassroomEntity getClassroom() {
        return classroom;
    }

    public void setClassroom(TblClassroomEntity tblClassroomEntity) {
        this.classroom = tblClassroomEntity;
    }

    public TblRoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(TblRoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    public JSONArray getEquipment() {
        return equipment;
    }

    public void setEquipment(JSONArray equipment) {
        this.equipment = equipment;
    }
}
