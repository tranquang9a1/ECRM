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
    public String init(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab) throws JSONException {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            List<RoomTypeDTO> tblRoomTypeEntities = roomTypeService.getAllRoomType();

            request.setAttribute("ALLROOMTYPE", tblRoomTypeEntities);
            List<ClassroomMapDTO> tblClassroomEntities = classroomService.getAllClassroomMap();
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            Iterator<TblEquipmentCategoryEntity> iterator = tblEquipmentCategoryEntities.iterator();
            while (iterator.hasNext()){
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator.next();
                if(tblEquipmentCategoryEntity.getName().trim().equals("Bàn")||tblEquipmentCategoryEntity.getName().trim().equals("Ghế")){
                    iterator.remove();
                }
            }
            request.setAttribute("ALLCLASSROOM", tblClassroomEntities);
            request.setAttribute("ACTIVETAB", activeTab);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_CLASSROOM");
            request.setAttribute("TABCONTROL", "STAFF_CLASSROOM");
            request.setAttribute("CATEGORY", tblEquipmentCategoryEntities);
            return "Staff_Classroom2";
        }else {
            return "Login";
        }
    }

    //create roomtype
    /*@RequestMapping(value = "createRoomType", method = RequestMethod.POST)
    public String createRoomType(HttpServletRequest request, @RequestParam("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("AirConditioning") int airConditioning, @RequestParam("Fan") int fan,
                                 @RequestParam("Projector") int projectors, @RequestParam("Speaker") int speaker,
                                 @RequestParam("Television") int television, @RequestParam("Bulb") int bulb, @RequestParam("RoomtypeName") String roomtypeName) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            boolean createRoomType = roomTypeService.createRoomType(roomtypeId, slots, verticalRows, horizontalRows,
                    NumberOfSlotsEachHRows, airConditioning, fan, projectors, speaker, television, bulb, roomtypeName);
            if(createRoomType){
                return "redirect:/staff/classroom?ACTIVETAB=tab2";
            }else {
                return ERROR;
            }
        }else {
            return "Login";
        }
    }*/
    @RequestMapping(value = "createRoomType", method = RequestMethod.POST)
    public String createRoomType(HttpServletRequest request, @RequestParam("RoomtypeId") String roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("RoomtypeName") String roomtypeName, @RequestParam("equip") String equip) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            boolean createRoomType = roomTypeService.createRoomType(roomtypeId, slots, verticalRows, horizontalRows,
                    NumberOfSlotsEachHRows, roomtypeName, equip);
            if(createRoomType){
                return "redirect:/staff/classroom?ACTIVETAB=tab2";
            }else {
                return ERROR;
            }
        }else {
            return "Login";
        }
    }

    //remove roomtype
    @RequestMapping(value = "removeRoomType")
    public String removeRoomtype(HttpServletRequest request, @RequestParam("RoomtypeId") int roomtypeId) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            boolean removeRoomType = roomTypeService.removeRoomType(roomtypeId);
            if(removeRoomType){
                return "redirect:/staff/classroom?ACTIVETAB=tab2";
            }else {
                return ERROR;
            }
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
            boolean createClassroom = classroomService.createClassroom(roomTypeId, roomName);
            if(createClassroom){
                return "redirect:/staff/classroom?ACTIVETAB=tab1";
            }else {
                return ERROR;
            }
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
            boolean removeClassroom = classroomService.removeClassroom(classroomName);
            if(removeClassroom){
                return "redirect:/staff/classroom?ACTIVETAB=tab1";
            }else {
                return ERROR;
            }
        }else {
            return "Login";
        }
    }

    //Tạo Trang cập nhật những equipment chưa có thông tin
    @RequestMapping(value = "EquipmentInformation")
    public String createEquipmentInformation(HttpServletRequest request, @RequestParam("ClassroomId") int classroomId) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            TblClassroomEntity classroomEntity = classroomService.getClassroomById(classroomId);
            List<TblEquipmentEntity> projector = new ArrayList<TblEquipmentEntity>();
            List<TblEquipmentEntity> tivi = new ArrayList<TblEquipmentEntity>();
            List<TblEquipmentEntity> air = new ArrayList<TblEquipmentEntity>();
            projector = classroomService.getEquipment(projector, classroomId, 1,true);
            tivi = classroomService.getEquipment(tivi, classroomId, 2,true);
            air = classroomService.getEquipment(air, classroomId, 3,true);


            List<TblEquipmentEntity> availableProjector = new ArrayList<TblEquipmentEntity>();
            List<TblEquipmentEntity> availableTivi = new ArrayList<TblEquipmentEntity>();
            List<TblEquipmentEntity> availableAir = new ArrayList<TblEquipmentEntity>();
            availableProjector = classroomService.getEquipment(availableProjector,classroomId,1,false);
            availableTivi = classroomService.getEquipment(availableTivi,classroomId,2,false);
            availableAir = classroomService.getEquipment(availableAir,classroomId,3,false);

            request.setAttribute("PROJECTOR", projector);
            request.setAttribute("AVAILABLEPROJECTOR", availableProjector);
            request.setAttribute("TIVI", tivi);
            request.setAttribute("AVAILABLETIVI", availableTivi);
            request.setAttribute("AIR", air);
            request.setAttribute("AVAILABLEAIR", availableAir);
            request.setAttribute("CLASSROOMID", classroomId);
            request.setAttribute("CLASSROOMNAME", classroomEntity.getName());
            return "Staff_InformationEquipment";
        }else {
             return "Login";
        }
    }


    //Update thông tin cho những equipment chưa có thông tin
    @RequestMapping(value = "updateInformation", method = RequestMethod.POST)
    public String updateInformation(HttpServletRequest request, @RequestParam("projector") int projector,
                                    @RequestParam("tivi") int tivi, @RequestParam("airConditioning") String airConditioning,
                                    @RequestParam("classroomId") int classroomId) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            String updateInformation = classroomService.updateInformation(projector,tivi,airConditioning,classroomId);
            return updateInformation;
        }else {
            return "Login";
        }
    }




}
