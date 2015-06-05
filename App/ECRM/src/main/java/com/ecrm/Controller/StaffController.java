package com.ecrm.Controller;

import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Htang on 5/29/2015.
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;

    @RequestMapping(value = "classroom")
    public String init(HttpServletRequest request) {
        List<TblRoomTypeEntity> lstRoomType = roomTypeDAO.findAll();
        List<TblRoomTypeEntity> tblRoomTypeEntities = new ArrayList<TblRoomTypeEntity>();
        for (TblRoomTypeEntity roomTypeEntity : lstRoomType){
            if(!roomTypeEntity.getIsDelete()){
                tblRoomTypeEntities.add(roomTypeEntity);
            }
        }
        request.setAttribute("ALLROOMTYPE", tblRoomTypeEntities);

        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        List<TblClassroomEntity> tblClassroomEntities = new ArrayList<TblClassroomEntity>();
        for(TblClassroomEntity classroomEntity : lstClassRoom){
            if(!classroomEntity.getIsDelete()){
                tblClassroomEntities.add(classroomEntity);
            }
        }
        request.setAttribute("ALLCLASSROOM", tblClassroomEntities);
        return "Staff_Classroom";
    }

    //create roomtype
    @RequestMapping(value = "createRoomType")
    public String createRoomType(HttpServletRequest request,@RequestParam ("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("AirConditioning") int airConditioning, @RequestParam("Fan") int fan,
                                 @RequestParam("Projector") int projectors, @RequestParam("Speaker") int speaker,
                                 @RequestParam("Television") int television, @RequestParam("Bulb") int bulb) {
        TblRoomTypeEntity roomType = new TblRoomTypeEntity();
        horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
        NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
        java.util.Date date = new java.util.Date();
        if(roomtypeId!=""){
            roomType = new TblRoomTypeEntity(Integer.parseInt(roomtypeId), slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                    speaker,bulb, television, roomTypeDAO.find(Integer.parseInt(roomtypeId)).getCreateTime(),false, new Timestamp(date.getTime()));
            roomTypeDAO.merge(roomType);
        }else{
            roomType = new TblRoomTypeEntity(0, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                    speaker,bulb, television, new Timestamp(date.getTime()),false, null);
            roomTypeDAO.persist(roomType);
        }
        return "redirect:/staff/classroom";
    }

    //create roomtype
    @RequestMapping(value = "createClassroom")
    public String createClassroom(HttpServletRequest request, @RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName) {
        Date date = new Date();
        TblClassroomEntity classroom = new TblClassroomEntity();
        classroom = new TblClassroomEntity(0,roomTypeId, roomName, 0, new Timestamp(date.getTime()),null, false);
        classroomDAO.persist(classroom);
        int id = classroomDAO.getId(roomName);
        colectionEquipment(id, roomTypeId);
        return "redirect:/staff/classroom";
    }

    public void colectionEquipment(int classroomId, int roomTypeId) {
        TblEquipmentEntity e = new TblEquipmentEntity();
        TblRoomTypeEntity roomTypeEntity = roomTypeDAO.find(roomTypeId);
        int vrows = roomTypeEntity.getVerticalRows();
        String[] soDay = null;
        String[] soChoNgoi = null;
        if (roomTypeEntity.getHorizontalRows().length() > 1) {
            soDay = roomTypeEntity.getHorizontalRows().split("-");
        }
        if (roomTypeEntity.getNumberOfSlotsEachHRows().length() > 1) {
            soChoNgoi = roomTypeEntity.getNumberOfSlotsEachHRows().split("-");
        }
        for (int i = 0; i < vrows; i++) {
            for (int j = 0; j <= Integer.parseInt(soDay[i]); j++) {
                //tao ban giao vien
                if (j == 0) {
                    if (i == 0) {
                        String banGV = "[" + i + "," + j + "]";
                        e = new TblEquipmentEntity(7, classroomId,  banGV,
                                "OK");
                        equipmentDAO.persist(e);
                        String gheGV = "[" + i + "," + j + ",0]";
                        e = new TblEquipmentEntity(8, classroomId,gheGV,
                                "OK");
                        equipmentDAO.persist(e);
                    }
                } else {
                    String ban = "[" + i + "," + j + "]";
                    e = new TblEquipmentEntity(7, classroomId, ban,
                            "OK");
                    equipmentDAO.persist(e);
                    for (int k = 0; k < Integer.parseInt(soChoNgoi[i]); k++) {
                        String ghe = "[" + i + "," + j + "," + k + "]";
                        e = new TblEquipmentEntity(8, classroomId,ghe,
                                "OK");
                        equipmentDAO.persist(e);
                    }
                }
            }
        }
        if (roomTypeEntity.getProjector() > 0) {
            e = new TblEquipmentEntity(1, classroomId, "[1]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.getTelevision()>0) {
            e = new TblEquipmentEntity(2, classroomId,"[2]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.getAirConditioning()>0) {
            e = new TblEquipmentEntity(3, classroomId, "[3]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.getFan()>0) {
            e = new TblEquipmentEntity(4, classroomId, "[4]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.getSpeaker()>0) {
            e = new TblEquipmentEntity(5, classroomId, "[5]",
                    "OK");
            equipmentDAO.persist(e);
        }
        e = new TblEquipmentEntity(6, classroomId, "[6]",
                "OK");
        equipmentDAO.persist(e);
    }

    //remove roomtype
    @RequestMapping(value = "remove")
    @Transactional
    public String removeRoomtype(HttpServletRequest request, @RequestParam("RoomtypeId") int roomtypeId){
        TblRoomTypeEntity roomTypeEntity = roomTypeDAO.find(roomtypeId);
        Collection<TblClassroomEntity>  tblClassroomEntities = roomTypeEntity.getTblClassroomsById();
        if(tblClassroomEntities.size()>0){
            for(TblClassroomEntity tblClassroomEntity : tblClassroomEntities){
                tblClassroomEntity.setIsDelete(true);
                classroomDAO.merge(tblClassroomEntity);
            }
        }
        roomTypeEntity.setIsDelete(true);
        roomTypeDAO.merge(roomTypeEntity);
        return "redirect:/staff/classroom";
    }
}
