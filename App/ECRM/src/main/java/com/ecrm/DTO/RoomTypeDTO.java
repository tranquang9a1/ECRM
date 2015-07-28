package com.ecrm.DTO;

import com.ecrm.Entity.TblRoomTypeEntity2;
import org.json.simple.JSONArray;

/**
 * Created by Htang on 7/29/2015.
 */
public class RoomTypeDTO {
    private TblRoomTypeEntity2 roomType;
    private JSONArray equipment;


    public RoomTypeDTO(TblRoomTypeEntity2 roomType, JSONArray equipment) {
        this.roomType = roomType;
        this.equipment = equipment;
    }

    public RoomTypeDTO() {
    }

    public TblRoomTypeEntity2 getRoomType() {
        return roomType;
    }

    public void setRoomType(TblRoomTypeEntity2 roomType) {
        this.roomType = roomType;
    }

    public JSONArray getEquipment() {
        return equipment;
    }

    public void setEquipment(JSONArray equipment) {
        this.equipment = equipment;
    }
}
