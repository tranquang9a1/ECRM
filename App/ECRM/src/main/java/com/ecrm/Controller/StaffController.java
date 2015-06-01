package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "classroom")
    public String init(HttpServletRequest request){
        List<TblRoomTypeEntity> lstRoomType = roomTypeDAO.findAll();
        request.setAttribute("ALLROOMTYPE", lstRoomType);
        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        request.setAttribute("ALLCLASSROOM", lstClassRoom);
        return "Staff_Classroom";
    }
    //create roomtype
    @RequestMapping(value = "createRoomType")
    public String createRoomType(HttpServletRequest request,@RequestParam("Slots") int slots, @RequestParam("VerticalRows") int verticalRows,
                                 @RequestParam("HorizontalRows") String horizontalRows, @RequestParam("NoSlotsEachHRows") String noSlotsEachHRows,
                                 @RequestParam("AirConditioning") boolean airConditioning, @RequestParam("Fan") boolean fan,
                                 @RequestParam("Projector") boolean projectors, @RequestParam("Speaker") boolean speaker,
                                 @RequestParam("Television") boolean television){
        horizontalRows = horizontalRows.substring(0, horizontalRows.length()-1);
        noSlotsEachHRows = noSlotsEachHRows.substring(0, noSlotsEachHRows.length()-1);
        java.util.Date date = new java.util.Date();
        TblRoomTypeEntity roomType = new TblRoomTypeEntity(0,slots, verticalRows,horizontalRows,noSlotsEachHRows,airConditioning,fan,projectors,
                speaker,television, new Timestamp(date.getTime()),null);
        roomTypeDAO.persist(roomType);

        return "redirect:/staff/classroom";
    }

    //create roomtype
    @RequestMapping(value = "createClassroom")
    public String createClassroom(HttpServletRequest request,@RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName){
        TblRoomTypeEntity roomType = roomTypeDAO.find(roomTypeId);
        Date date = new Date();
        TblClassroomEntity classroom = new TblClassroomEntity(roomTypeId, roomName, 0, new Timestamp(date.getTime()),null, null,null, null, null );
        System.out.println("1234");
        classroomDAO.persist(classroom);
        System.out.println("abc");
        return "redirect:/staff/classroom";
    }

}
