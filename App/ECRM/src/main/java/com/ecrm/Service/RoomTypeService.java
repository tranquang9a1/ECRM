package com.ecrm.Service;

import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
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
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;

    public List<TblRoomTypeEntity> getAllRoomType(){
        List<TblRoomTypeEntity> lstRoomType = roomTypeDAO.findAll();
        List<TblRoomTypeEntity> tblRoomTypeEntities = new ArrayList<TblRoomTypeEntity>();
        for (TblRoomTypeEntity roomTypeEntity : lstRoomType) {
            if (!roomTypeEntity.getIsDelete()) {
                tblRoomTypeEntities.add(roomTypeEntity);
            }
        }
        return tblRoomTypeEntities;
    }

    public Boolean createRoomType( String roomtypeId,int slots, int verticalRows,
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
