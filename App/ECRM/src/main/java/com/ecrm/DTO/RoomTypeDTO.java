package com.ecrm.DTO;

import com.ecrm.Entity.TblRoomTypeEntity;
import org.json.simple.JSONArray;

/**
 * Created by Htang on 7/29/2015.
 */
public class RoomTypeDTO {
    private TblRoomTypeEntity roomType;
    private JSONArray equipment;


    public RoomTypeDTO(TblRoomTypeEntity roomType, JSONArray equipment) {
        this.roomType = roomType;
        this.equipment = equipment;
    }

    public RoomTypeDTO() {
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
