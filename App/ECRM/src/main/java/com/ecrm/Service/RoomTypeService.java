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


    public String createRoomType( int roomtypeId,int slots, int verticalRows,
                                   String horizontalRows,String NumberOfSlotsEachHRows,
                                   String roomtypeName, String equip, String action){
        try {
            TblRoomTypeEntity2 roomType = new TblRoomTypeEntity2();
            horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
            NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
            java.util.Date date = new java.util.Date();
            String message = "";
            if (roomtypeName != "") {
                roomType = roomType2DAO.getRoomTypeByName(roomtypeName);
                if ( action.equals("update")) {
                    if(roomType==null){
                        roomType = roomType2DAO.find(roomtypeId);
                    }
                    String oldRoomType = roomType.getName();
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
                    roomType = new TblRoomTypeEntity2(roomType.getId(), roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                            roomType.getCreateTime(), false, new Timestamp(date.getTime()));

                    roomType2DAO.merge(roomType);
                    message = "Cập nhật "+oldRoomType+" thành công!";
                }
                if(action.equals("create") && roomType==null) {
                    roomType = new TblRoomTypeEntity2(0, roomtypeName, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows,
                            new Timestamp(date.getTime()), false, new Timestamp(date.getTime()));
                    roomType2DAO.insert(roomType);
                    message = "Tạo "+roomtypeName+" thành công!";
                }
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

    public String removeRoomType(int roomTypeId){
        try {
            TblRoomTypeEntity2 roomTypeEntity = roomType2DAO.find(roomTypeId);
            roomTypeEntity.setIsDelete(true);
            roomType2DAO.merge(roomTypeEntity);
            String message = "Xóa loại phòng "+roomTypeEntity.getName()+" thành công!";
            return "redirect:/staff/classroom?ACTIVETAB=tab2&MESSAGE=" +  URLEncoder.encode(message, "UTF-8");
        }catch (Exception e){
            return ERROR;
        }

    }

    public RoomTypeDTO getRoomTypeOfRoom(TblClassroomEntity classroom){
        TblRoomTypeEntity2 roomType = roomType2DAO.find(classroom.getRoomTypeId2());

        JSONArray jsonArray = new JSONArray();
        List<TblEquipmentQuantityEntity>tblEquipmentQuantityEntities = roomType.getTblEquipmentQuantityById();
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

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setRoomType(roomType);
        roomTypeDTO.setEquipment(jsonArray);

        return roomTypeDTO;
    }
}
