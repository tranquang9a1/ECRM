package com.ecrm.Controller;

import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @RequestMapping(value = "classroom")
    public String init(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab) {
        //getAvailableRoom();
        List<TblRoomTypeEntity> lstRoomType = roomTypeDAO.findAll();
        List<TblRoomTypeEntity> tblRoomTypeEntities = new ArrayList<TblRoomTypeEntity>();
        for (TblRoomTypeEntity roomTypeEntity : lstRoomType) {
            if (!roomTypeEntity.getIsDelete()) {
                tblRoomTypeEntities.add(roomTypeEntity);
            }
        }
        request.setAttribute("ALLROOMTYPE", tblRoomTypeEntities);

        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        List<TblClassroomEntity> tblClassroomEntities = new ArrayList<TblClassroomEntity>();
        for (TblClassroomEntity classroomEntity : lstClassRoom) {
            if (!classroomEntity.getIsDelete()) {
                tblClassroomEntities.add(classroomEntity);
            }
        }
        request.setAttribute("ALLCLASSROOM", tblClassroomEntities);
        request.setAttribute("ACTIVETAB", activeTab);
        return "Staff_Classroom";
    }

    //create roomtype
    @RequestMapping(value = "createRoomType")
    public String createRoomType(HttpServletRequest request, @RequestParam("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("AirConditioning") int airConditioning, @RequestParam("Fan") int fan,
                                 @RequestParam("Projector") int projectors, @RequestParam("Speaker") int speaker,
                                 @RequestParam("Television") int television, @RequestParam("Bulb") int bulb) {
        TblRoomTypeEntity roomType = new TblRoomTypeEntity();
        horizontalRows = horizontalRows.substring(0, horizontalRows.length() - 1);
        NumberOfSlotsEachHRows = NumberOfSlotsEachHRows.substring(0, NumberOfSlotsEachHRows.length() - 1);
        java.util.Date date = new java.util.Date();


        if (roomtypeId != "") {
            roomType = roomTypeDAO.find(Integer.parseInt(roomtypeId));
            roomType = new TblRoomTypeEntity(Integer.parseInt(roomtypeId), slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                    speaker, bulb, television, roomType.getCreateTime(), false, new Timestamp(date.getTime()));
            roomTypeDAO.merge(roomType);
        } else {
            roomType = new TblRoomTypeEntity(0, slots, verticalRows, horizontalRows, NumberOfSlotsEachHRows, airConditioning, fan, projectors,
                    speaker, bulb, television, new Timestamp(date.getTime()), false, null);
            roomTypeDAO.persist(roomType);
        }
        return "redirect:/staff/classroom?ACTIVETAB=tab2";
    }

    //create classroom
    @RequestMapping(value = "createClassroom")
    public String createClassroom(HttpServletRequest request, @RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName) {
        Date date = new Date();
        if(roomName!=null){
            TblClassroomEntity classroom = classroomDAO.getClassroomByName(roomName);
            if (classroom != null) {
                classroom = new TblClassroomEntity(classroom.getId(), roomTypeId, roomName, 0, classroom.getCreateTime(),
                        new Timestamp(date.getTime()), false);
                classroomDAO.merge(classroom);
            }else{
                classroom = new TblClassroomEntity(0, roomTypeId, roomName, 0, new Timestamp(date.getTime()), null, false);
                classroomDAO.persist(classroom);
            }
        }
        return "redirect:/staff/classroom?ACTIVETAB=tab1";
    }


    //remove roomtype
    @RequestMapping(value = "removeRoomType")
    @Transactional
    public String removeRoomtype(HttpServletRequest request, @RequestParam("RoomtypeId") int roomtypeId) {
        TblRoomTypeEntity roomTypeEntity = roomTypeDAO.find(roomtypeId);
        Collection<TblClassroomEntity> tblClassroomEntities = roomTypeEntity.getTblClassroomsById();
        if (tblClassroomEntities.size() > 0) {
            for (TblClassroomEntity tblClassroomEntity : tblClassroomEntities) {
                tblClassroomEntity.setIsDelete(true);
                classroomDAO.merge(tblClassroomEntity);
                Collection<TblEquipmentEntity> tblEquipmentEntities = tblClassroomEntity.getTblEquipmentsById();
                for(TblEquipmentEntity tblEquipmentEntity:tblEquipmentEntities){
                    tblEquipmentEntity.setClassroomId(0);
                    equipmentDAO.merge(tblEquipmentEntity);
                }
            }
        }
        roomTypeEntity.setIsDelete(true);
        roomTypeDAO.merge(roomTypeEntity);
        return "redirect:/staff/classroom?ACTIVETAB=tab2";
    }

    //remove classroom
    @RequestMapping(value = "removeClassroom")
    @Transactional
    public String removeClassroom(HttpServletRequest request, @RequestParam("classroomName") String classroomName) {
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroomName);
        classroomEntity.setIsDelete(true);
        classroomDAO.merge(classroomEntity);
        Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
        for(TblEquipmentEntity tblEquipmentEntity:tblEquipmentEntities){
            tblEquipmentEntity.setClassroomId(0);
            equipmentDAO.merge(tblEquipmentEntity);
        }
        return "redirect:/staff/classroom?ACTIVETAB=tab1";
    }

    public String getAvailableRoom() {
        TblClassroomEntity tblClassroomEntity = classroomDAO.find(1022);
        int classroomId = tblClassroomEntity.getId();
        TblScheduleEntity tblScheduleEntity = scheduleDAO.find(3);
        int currentSlots = tblScheduleEntity.getNumberOfStudents();
        int slots = tblScheduleEntity.getSlots();
        Date dateFrom = tblScheduleEntity.getDateFrom();
        Time timeFrom = tblScheduleEntity.getTimeFrom();
        String datetime = dateFrom.toString() + " " + timeFrom.toString();
        List<Date> time = timeFraction(datetime, slots);
        Date now = new Date();

        for(int i=0; i<time.size(); i++){
            long from = (now.getTime() - time.get(i).getTime())/60000;
            if(from >= 70){
                time.remove(i);
            }
        }

        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        for (int i = 0; i < tblClassroomEntities.size(); i++) {
            int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
            if (numberOfStudent < currentSlots) {
                tblClassroomEntities.remove(i);
            }
        }

        for (int i = 0; i < tblClassroomEntities.size(); i++) {
            Collection<TblScheduleEntity> tblScheduleEntities = tblClassroomEntities.get(i).getTblSchedulesById();
            if (tblScheduleEntities != null) {
                for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                    if (tblScheduleEntity1.getDateFrom().toString().equals(dateFrom.toString())) {
                        if (tblScheduleEntity1.getTimeFrom().toString().equals(timeFrom.toString())) {
                            if (tblScheduleEntity1.getSlots() == slots) {
                                tblClassroomEntities.remove(i);
                            }
                        }
                    }
                }
            }
        }


        String classroom = "";
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            classroom += classroomEntity.getName();
        }
        System.out.println("--------Classroom: " + classroom);
        return classroom;
    }

    public List<Date> timeFraction(String datetime, int slots) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeFrom1 = null;
        try {
            timeFrom1 = df.parse(datetime);
        } catch (ParseException e) {
            System.out.println("erroe!!!!");
        }

        List<Date> time = new ArrayList<Date>();
        df.format(timeFrom1);
        time.add(timeFrom1);
        for (int i = 1; i <= slots; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(timeFrom1);
            cal.add(Calendar.MINUTE, i * 90);
            Date t = cal.getTime();
            df.format(t);
            time.add(t);
        }
        return time;
    }
}
