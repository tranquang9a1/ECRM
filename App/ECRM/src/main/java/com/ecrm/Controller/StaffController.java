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
        request.setAttribute("ALLROOMTYPE", lstRoomType);
        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        request.setAttribute("ALLCLASSROOM", lstClassRoom);
        return "Staff_Classroom";
    }

    //create roomtype
    @RequestMapping(value = "createRoomType")
    public String createRoomType(HttpServletRequest request,@RequestParam ("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NoSlotsEachHRows") String noSlotsEachHRows,
                                 @RequestParam("AirConditioning") boolean airConditioning, @RequestParam("Fan") boolean fan,
                                 @RequestParam("Projector") boolean projectors, @RequestParam("Speaker") boolean speaker,
                                 @RequestParam("Television") boolean television) {
        TblRoomTypeEntity roomType = new TblRoomTypeEntity();
        horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
        noSlotsEachHRows = noSlotsEachHRows.substring(0, noSlotsEachHRows.length() - 1);
        java.util.Date date = new java.util.Date();
        if(roomtypeId!=""){
            roomType = new TblRoomTypeEntity(Integer.parseInt(roomtypeId), slots, verticalRows, horizontalRows, noSlotsEachHRows, airConditioning, fan, projectors,
                    speaker, television, new Timestamp(date.getTime()), new Timestamp(date.getTime()));
            roomTypeDAO.merge(roomType);
        }else{
            roomType = new TblRoomTypeEntity(0, slots, verticalRows, horizontalRows, noSlotsEachHRows, airConditioning, fan, projectors,
                    speaker, television, new Timestamp(date.getTime()), null);
            roomTypeDAO.persist(roomType);
        }
        return "redirect:/staff/classroom";
    }

    //create roomtype
    @RequestMapping(value = "createClassroom")
    public String createClassroom(HttpServletRequest request, @RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName) {
        Date date = new Date();

        TblClassroomEntity classroom = new TblClassroomEntity(roomTypeId, roomName, 0, new Timestamp(date.getTime()),
                null, null, null, null, null);
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
        if (roomTypeEntity.getNoSlotsEachHRows().length() > 1) {
            soChoNgoi = roomTypeEntity.getNoSlotsEachHRows().split("-");
        }
        for (int i = 0; i < vrows; i++) {
            for (int j = 0; j <= Integer.parseInt(soDay[i]); j++) {
                //tao ban giao vien
                if (j == 0) {
                    if (i == 0) {
                        String banGV = "[" + i + "," + j + "]";
                        e = new TblEquipmentEntity(7, classroomId, 3000, banGV,
                                "OK");
                        equipmentDAO.persist(e);
                        String gheGV = "[" + i + "," + j + ",0]";
                        e = new TblEquipmentEntity(8, classroomId, 3000, gheGV,
                                "OK");
                        equipmentDAO.persist(e);
                    }
                } else {
                    String ban = "[" + i + "," + j + "]";
                    e = new TblEquipmentEntity(7, classroomId, 3000, ban,
                            "OK");
                    equipmentDAO.persist(e);
                    for (int k = 0; k < Integer.parseInt(soChoNgoi[i]); k++) {
                        String ghe = "[" + i + "," + j + "," + k + "]";
                        e = new TblEquipmentEntity(8, classroomId, 3000, ghe,
                                "OK");
                        equipmentDAO.persist(e);
                    }
                }
            }
        }
        if (roomTypeEntity.isProjector()) {
            e = new TblEquipmentEntity(1, classroomId, 3000, "[1]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.isTelevision()) {
            e = new TblEquipmentEntity(2, classroomId, 3000, "[2]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.isAirConditioning()) {
            e = new TblEquipmentEntity(3, classroomId, 3000, "[3]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.isFan()) {
            e = new TblEquipmentEntity(4, classroomId, 3000, "[4]",
                    "OK");
            equipmentDAO.persist(e);
        }
        if (roomTypeEntity.isSpeaker()) {
            e = new TblEquipmentEntity(5, classroomId, 3000, "[5]",
                    "OK");
            equipmentDAO.persist(e);
        }
        e = new TblEquipmentEntity(6, classroomId, 3000, "[6]",
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
                tblClassroomEntity.setRoomTypeId(null);

            }
        }
        roomTypeEntity.getTblClassroomsById().clear();
        roomTypeDAO.remove(roomTypeEntity);
        return "redirect:/staff/classroom";
    }
}
