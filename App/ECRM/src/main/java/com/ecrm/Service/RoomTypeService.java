package com.ecrm.Service;

import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.RoomTypeDTO;
import com.ecrm.Entity.*;
import com.google.gson.Gson;

import org.json.JSONException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Htang on 7/23/2015.
 */
@Service
public class RoomTypeService {
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;
    @Autowired
    RoomType2DAOImpl roomType2DAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    EquipmentQuantityDAOImpl equipmentQuantityDAO;

    public List<RoomTypeDTO> getAllRoomType() throws JSONException {
        List<RoomTypeDTO> roomTypeDTOs = new ArrayList<RoomTypeDTO>();
        List<String> category = new ArrayList<String>();
        List<TblRoomTypeEntity2> lstRoomType = roomType2DAO.findAll();
        List<TblRoomTypeEntity2> tblRoomTypeEntities = new ArrayList<TblRoomTypeEntity2>();
        for (TblRoomTypeEntity2 roomTypeEntity : lstRoomType) {
            if (!roomTypeEntity.getIsDelete()) {
                JSONArray jsonArray = new JSONArray();
                RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
                tblRoomTypeEntities.add(roomTypeEntity);
                List<TblEquipmentQuantityEntity>tblEquipmentQuantityEntities = roomTypeEntity.getTblEquipmentQuantityById();
                for(int i = 0; i<tblEquipmentQuantityEntities.size(); i++){
                    TblEquipmentQuantityEntity tblEquipmentQuantityEntity = tblEquipmentQuantityEntities.get(i);
                    JSONObject formDetailsJson = new JSONObject();
                    formDetailsJson.put("id", tblEquipmentQuantityEntity.getEquipmentCategoryId());
                    formDetailsJson.put("name", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getName());
                    formDetailsJson.put("imageUrl", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getImageUrl());
                    jsonArray.add(formDetailsJson);
                }
                roomTypeDTO.setRoomType(roomTypeEntity);
                roomTypeDTO.setEquipment(jsonArray);
                roomTypeDTOs.add(roomTypeDTO);
            }
        }

        return roomTypeDTOs;
    }

    /*public Boolean createRoomType( String roomtypeId,int slots, int verticalRows,
                                 String horizontalRows,String NumberOfSlotsEachHRows,
                                 int airConditioning, int fan,
                                 int projectors, int speaker,
                                 int television,int bulb,String roomtypeName){
        try {
            TblRoomTypeEntity roomType = new TblRoomTypeEntity();
            horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
            NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
            java.util.Date date = new java.util.Date();
            if (roomtypeId != "") {
                roomType = roomTypeDAO.find(Integer.parseInt(roomtypeId));
                roomType = new TblRoomTypeEntity(Integer.parseInt(roomtypeId), roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                        speaker, bulb, television, roomType.getCreateTime(), false, new Timestamp(date.getTime()));
                roomTypeDAO.merge(roomType);
            } else {
                roomType = new TblRoomTypeEntity(0, roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                        speaker, bulb, television, new Timestamp(date.getTime()), false, null);
                roomTypeDAO.persist(roomType);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }*/

    public Boolean createRoomType( String roomtypeId,int slots, int verticalRows,
                                   String horizontalRows,String NumberOfSlotsEachHRows,
                                   String roomtypeName, String equip){
        try {
            TblRoomTypeEntity2 roomType = new TblRoomTypeEntity2();
            horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
            NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
            java.util.Date date = new java.util.Date();
            if (roomtypeId != "") {
                roomType = roomType2DAO.find(Integer.parseInt(roomtypeId));
                roomType = new TblRoomTypeEntity2(Integer.parseInt(roomtypeId), roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                         roomType.getCreateTime(), false, new Timestamp(date.getTime()));
                roomType2DAO.merge(roomType);
                Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = roomType.getTblEquipmentQuantityById();
                for(TblEquipmentQuantityEntity tblEquipmentQuantityEntity: tblEquipmentQuantityEntities){
                    tblEquipmentQuantityEntity.setRoomTypeId(null);
                    equipmentQuantityDAO.merge(tblEquipmentQuantityEntity);
                }
            } else {
                roomType = new TblRoomTypeEntity2(0, roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                         new Timestamp(date.getTime()), false, null);
                roomType2DAO.insert(roomType);
            }
            String []array = equip.split("-");
            for(int i = 0; i<array.length; i++){
                String [] array2 = array[i].split(",");
                String quantity = array2[0];
                String priority = array2[1];
                String categoryId = array2[2];
                if(!categoryId.equals("0")){
                    TblEquipmentQuantityEntity tblEquipmentQuantityEntity = new TblEquipmentQuantityEntity();
                    tblEquipmentQuantityEntity.setEquipmentCategoryId(Integer.parseInt(categoryId));
                    tblEquipmentQuantityEntity.setRoomTypeId(roomType.getId());
                    tblEquipmentQuantityEntity.setPriority(Integer.parseInt(priority));
                    tblEquipmentQuantityEntity.setQuantity(Integer.parseInt(quantity));
                    equipmentQuantityDAO.persist(tblEquipmentQuantityEntity);
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean removeRoomType(int roomtypeId){
        try {
            TblRoomTypeEntity roomTypeEntity = roomTypeDAO.find(roomtypeId);
            Collection<TblClassroomEntity> tblClassroomEntities = roomTypeEntity.getTblClassroomsById();
            if (tblClassroomEntities.size() > 0) {
                for (TblClassroomEntity tblClassroomEntity : tblClassroomEntities) {
                    tblClassroomEntity.setIsDelete(true);
                    classroomDAO.merge(tblClassroomEntity);
                    Collection<TblEquipmentEntity> tblEquipmentEntities = tblClassroomEntity.getTblEquipmentsById();
                    for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                        tblEquipmentEntity.setClassroomId(null);
                        equipmentDAO.merge(tblEquipmentEntity);
                    }
                }
            }
            roomTypeEntity.setIsDelete(true);
            roomTypeDAO.merge(roomTypeEntity);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
