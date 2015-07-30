package com.ecrm.Controller;

import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.DTO.ClassroomMapDTO;
import com.ecrm.DTO.RoomTypeDTO;
import com.ecrm.Entity.*;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.RoomTypeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public static final String ERROR = "Error";
    @Autowired
    ClassroomService classroomService;
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    CategoryDAOImpl categoryDAO;

    @RequestMapping(value = "classroom")
    public String init(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab, @RequestParam("MESSAGE") String message) throws JSONException {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            List<RoomTypeDTO> tblRoomTypeEntities = roomTypeService.getAllRoomType();

            request.setAttribute("ALLROOMTYPE", tblRoomTypeEntities);
            List<ClassroomMapDTO> tblClassroomEntities = classroomService.getAllClassroomMap();
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            Iterator<TblEquipmentCategoryEntity> iterator = tblEquipmentCategoryEntities.iterator();
            while (iterator.hasNext()){
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator.next();
                if(tblEquipmentCategoryEntity.getName().trim().equals("Bàn")||tblEquipmentCategoryEntity.getName().trim().equals("Ghế")||
                        tblEquipmentCategoryEntity.getName().trim().equals("Empty")){
                    iterator.remove();
                }
            }
            request.setAttribute("ALLCLASSROOM", tblClassroomEntities);
            request.setAttribute("ACTIVETAB", activeTab);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_CLASSROOM");
            request.setAttribute("TABCONTROL", "STAFF_CLASSROOM");
            request.setAttribute("CATEGORY", tblEquipmentCategoryEntities);
            request.setAttribute("MESSAGE", message);
            return "Staff_Classroom2";
        }else {
            return "Login";
        }
    }

    @RequestMapping(value = "createRoomType", method = RequestMethod.POST)
    public String createRoomType(HttpServletRequest request, @RequestParam("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("RoomtypeName") String roomtypeName, @RequestParam("equip") String equip) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return roomTypeService.createRoomType(roomtypeId, slots, verticalRows, horizontalRows,
                    NumberOfSlotsEachHRows, roomtypeName, equip);
        }else {
            return "Login";
        }
    }

    //create classroom
    @RequestMapping(value = "createClassroom", method = RequestMethod.POST)
    public String createClassroom(HttpServletRequest request, @RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.createClassroom(roomTypeId, roomName);
        }else {
            return "Login";
        }
    }

    //remove classroom
    @RequestMapping(value = "removeClassroom")
    @Transactional
    public String removeClassroom(HttpServletRequest request, @RequestParam("classroomName") String classroomName) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.removeClassroom(classroomName);

        }else {
            return "Login";
        }
    }
}
