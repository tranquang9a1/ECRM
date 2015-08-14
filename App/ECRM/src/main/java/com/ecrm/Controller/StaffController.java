package com.ecrm.Controller;

import com.ecrm.DAO.ClassroomDAO;
import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.DTO.ClassroomMapDTO;
import com.ecrm.DTO.RoomTypeDTO;
import com.ecrm.Entity.*;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.RoomTypeService;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @RequestMapping(value = "classroom")
    public String init(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab, @RequestParam("MESSAGE") String message,
                       @RequestParam(value = "Page", required = false, defaultValue = "0") String page,
                       @RequestParam(value = "SORT", defaultValue = "ASC") String sort) throws JSONException, UnsupportedEncodingException {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            //Get list all room type
            List<RoomTypeDTO> tblRoomTypeEntities = roomTypeService.getAllRoomType();
            Collections.sort(tblRoomTypeEntities, new Comparator<RoomTypeDTO>() {
                @Override
                public int compare(RoomTypeDTO o1, RoomTypeDTO o2) {
                    return o2.getRoomType().getUpdateTime().compareTo(o1.getRoomType().getUpdateTime());
                }
            });
            request.setAttribute("ALLROOMTYPE", tblRoomTypeEntities);

            //Get list classroom by page
            int size = 9;
            int pageNumber = 0;
            try {
                pageNumber = Integer.parseInt(page);
            } catch (NumberFormatException ex) {
                return "Error";
            }

            int numberOfRoom = classroomDAO.getAllClassroom().size();
            int maxPage = (numberOfRoom/size) + (numberOfRoom%size==0?0:1);
            if(pageNumber > maxPage) {
                return "Error";
            } else if(pageNumber == 0) {
                pageNumber = 1;
            }

            List<ClassroomMapDTO> tblClassroomEntities = new ArrayList<ClassroomMapDTO>();
            if(maxPage > 0) {
                tblClassroomEntities = classroomService.getAllClassroomMap(pageNumber, size, sort);
            }

            //Get equipment category
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            Iterator<TblEquipmentCategoryEntity> iterator = tblEquipmentCategoryEntities.iterator();
            while (iterator.hasNext()){
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator.next();
                if(tblEquipmentCategoryEntity.getName().trim().equals("Bàn")||tblEquipmentCategoryEntity.getName().trim().equals("Ghế")||
                        tblEquipmentCategoryEntity.getName().trim().equals("Empty")){
                    iterator.remove();
                }
            }
            request.setCharacterEncoding("UTF-8");

            if(sort.equals("ASC")) {
                request.setAttribute("SORT", "DESC");
            } else {
                request.setAttribute("SORT", "ASC");
            }

            request.setAttribute("ALLCLASSROOM", tblClassroomEntities);
            request.setAttribute("MAXPAGE", maxPage);
            request.setAttribute("PAGE", pageNumber);
            request.setAttribute("ACTIVETAB", activeTab);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_CLASSROOM");
            request.setAttribute("TABCONTROL", "STAFF_CLASSROOM");
            request.setAttribute("CATEGORY", tblEquipmentCategoryEntities);
            request.setAttribute("MESSAGE", message);
            return "Staff_Classroom";
        }else {
            return "Login";
        }
    }

    @RequestMapping(value = "createRoomType", method = RequestMethod.POST)
    public String createRoomType(HttpServletRequest request, @RequestParam("RoomtypeId") int roomtypeId, @RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NumberOfSlotsEachHRows") String NumberOfSlotsEachHRows,
                                 @RequestParam("RoomtypeName") String roomtypeName, @RequestParam("equip") String equip, @RequestParam("Action") String action) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return roomTypeService.createRoomType(roomtypeId, slots, verticalRows, horizontalRows,
                    NumberOfSlotsEachHRows, roomtypeName, equip, action);
        }else {
            return "Login";
        }
    }

    //create classroom
    @RequestMapping(value = "createClassroom", method = RequestMethod.POST)
    public String createClassroom(HttpServletRequest request, @RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName, @RequestParam("Action") String action,
                                  @RequestParam("classroomId") int classroomId) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.createClassroom(roomTypeId, roomName, action, classroomId);
        }else {
            return "Login";
        }
    }

    //remove classroom
    @RequestMapping(value = "removeClassroom")
    public String removeClassroom(HttpServletRequest request, @RequestParam("classroomName") String classroomName) {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.removeClassroom(classroomName);

        }else {
            return "Login";
        }
    }

    @RequestMapping(value = "updateEquipment")
    public String updateEquipment(HttpServletRequest request, @RequestParam("classroomId")int classroomId){
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.updateEquipment(classroomId);

        }else {
            return "Login";
        }
    }

    @RequestMapping(value = "removeRoomType")
    public String removeRoomType(HttpServletRequest request, @RequestParam("roomTypeId")int roomTypeId){
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return roomTypeService.removeRoomType(roomTypeId);

        }else {
            return "Login";
        }
    }

    @RequestMapping(value = "changeRoomManually")
    public String changeRoomManually(HttpServletRequest request, @RequestParam("classroomId") int classroomId, @RequestParam("timeFrom") String tf,
                                     @RequestParam("timeTo") String tt, @RequestParam("morning") String morning,
                                     @RequestParam("noon") String noon) throws UnsupportedEncodingException, ParseException {
        HttpSession session  =  request.getSession();
        if(session!=null) {
            return classroomService.changeRoomManually(classroomId, tf, tt, morning, noon);
        }else {
            return "Login";
        }
    }
}
