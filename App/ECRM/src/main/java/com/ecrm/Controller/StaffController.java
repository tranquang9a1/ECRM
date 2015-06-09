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
        return "redirect:/staff/classroom?ACTIVETAB=tab1";
    }

    public String getAvailableRoom(){
        TblClassroomEntity tblClassroomEntity = classroomDAO.find(1022);
        int classroomId = tblClassroomEntity.getId();
        TblScheduleEntity tblScheduleEntity = scheduleDAO.find(1);
        int currentSlots = tblScheduleEntity.getNumberOfStudents();
        Date date = tblScheduleEntity.getDateFrom();
        Time timeFrom = tblScheduleEntity.getTimeFrom();
        String time = timeFrom.toString();
        System.out.println("Time from:"+time);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        cal.add(Calendar.MINUTE, tblScheduleEntity.getSlots()*45);
        Date timeTO = cal.getTime();
        System.out.println("TIme to: "+ df.format(timeTO));

        Collection<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        for(TblClassroomEntity classroomEntity : tblClassroomEntities){
            int slots = classroomEntity.getTblRoomTypeByRoomTypeId().getSlots();
            if(slots<currentSlots){
                tblClassroomEntities.remove(classroomEntity);
            }
        }

        return "";
    }
}
