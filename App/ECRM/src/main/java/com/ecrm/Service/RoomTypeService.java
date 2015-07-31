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

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Htang on 7/23/2015.
 */
@Service
public class RoomTypeService {
    public static final String ERROR = "ERROR";
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
    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;

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
                    if(!tblEquipmentQuantityEntities.get(i).getIsDelete()){
                        TblEquipmentQuantityEntity tblEquipmentQuantityEntity = tblEquipmentQuantityEntities.get(i);
                        JSONObject formDetailsJson = new JSONObject();
                        formDetailsJson.put("id", tblEquipmentQuantityEntity.getEquipmentCategoryId());
                        formDetailsJson.put("name", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getName());
                        formDetailsJson.put("imageUrl", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getImageUrl());
                        jsonArray.add(formDetailsJson);
                    }

                }
                roomTypeDTO.setRoomType(roomTypeEntity);
                roomTypeDTO.setEquipment(jsonArray);
                roomTypeDTOs.add(roomTypeDTO);
            }
        }

        return roomTypeDTOs;
    }


    public String createRoomType( String roomtypeId,int slots, int verticalRows,
                                   String horizontalRows,String NumberOfSlotsEachHRows,
                                   String roomtypeName, String equip){
        try {
            TblRoomTypeEntity2 roomType = new TblRoomTypeEntity2();
            horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
            NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
            java.util.Date date = new java.util.Date();
            String message = "";
            if (roomtypeId != "") {
                roomType = roomType2DAO.find(Integer.parseInt(roomtypeId));
                List<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = roomType.getTblEquipmentQuantityById();
                if(!tblEquipmentQuantityEntities.isEmpty()){
                    for(TblEquipmentQuantityEntity tblEquipmentQuantityEntity: tblEquipmentQuantityEntities){
                        tblEquipmentQuantityEntity.setIsDelete(true);
                        equipmentQuantityDAO.merge(tblEquipmentQuantityEntity);
                    }
                }
                Collection<TblClassroomEntity> tblClassroomEntities = roomType.getTblClassroomsById();
                if(!tblClassroomEntities.isEmpty()){
                    for(TblClassroomEntity classroomEntity: tblClassroomEntities){
                        List<TblEquipmentEntity>tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
                        for(TblEquipmentEntity tblEquipmentEntity: tblEquipmentEntities){
                            tblEquipmentEntity.setClassroomId(null);
                            equipmentDAO.merge(tblEquipmentEntity);
                        }
                        classroomEntity.setIsAllInformation(false);
                        classroomDAO.merge(classroomEntity);
                    }
                }
                roomType = new TblRoomTypeEntity2(Integer.parseInt(roomtypeId), roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                         roomType.getCreateTime(), false, new Timestamp(date.getTime()));

                roomType2DAO.merge(roomType);
                message = "Cập nhật "+roomtypeName+" thành công!";

            } else {
                roomType = new TblRoomTypeEntity2(0, roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                         new Timestamp(date.getTime()), false, null);
                roomType2DAO.insert(roomType);
                message = "Tạo "+roomtypeName+" thành công!";
            }
            String []array = equip.split("-");
            for(int i = 0; i<array.length; i++){
                String [] array2 = array[i].split(",");
                String quantity = array2[0];
                String priority = array2[1];
                String categoryId = array2[2];
                if(!categoryId.equals("0")&& !quantity.equals("0")){
                    TblEquipmentQuantityEntity tblEquipmentQuantityEntity = new TblEquipmentQuantityEntity();
                    tblEquipmentQuantityEntity.setEquipmentCategoryId(Integer.parseInt(categoryId));
                    tblEquipmentQuantityEntity.setRoomTypeId(roomType.getId());
                    tblEquipmentQuantityEntity.setPriority(Integer.parseInt(priority));
                    tblEquipmentQuantityEntity.setQuantity(Integer.parseInt(quantity));
                    tblEquipmentQuantityEntity.setIsDelete(false);
                    equipmentQuantityDAO.persist(tblEquipmentQuantityEntity);
                }
            }

            return "redirect:/staff/classroom?ACTIVETAB=tab2&MESSAGE=" +  URLEncoder.encode(message, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }

    /*public Boolean removeRoomType(int roomtypeId){
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

    }*/
}
