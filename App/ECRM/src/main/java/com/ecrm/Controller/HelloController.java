package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import com.ecrm.Entity.TblUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpServletRequest request) {
        return "Login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        TblUserEntity user = userDAO.login(username, password);
        if (user.isStatus()) {
            session.setAttribute("USER", user);
            if (user.getTblRoleByRoleId().getName().equals("Admin")) {
                return "home";
            }
            if (user.getTblRoleByRoleId().getName().equals("Staff")) {
                List<TblRoomTypeEntity> lstRoomType = roomTypeDAO.findAll();
                request.setAttribute("ALLROOMTYPE", lstRoomType);
                return "Staff_Classroom";
            }
            if (user.getTblRoleByRoleId().getName().equals("User")) {
                return "home";
            }
        }
        return "error";
    }

    //Logout
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("USER");
        return "Login";
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
        return "Staff_Classroom";
    }

    //create roomtype
    @RequestMapping(value = "createClassroom")
    public String createClassroom(HttpServletRequest request,@RequestParam("RoomType") int roomTypeId,
                                  @RequestParam("RoomName") String roomName){
        TblRoomTypeEntity roomType = roomTypeDAO.find(roomTypeId);
        Date date = new Date();
        TblClassroomEntity classroom = new TblClassroomEntity(0, roomName, 0, new Timestamp(date.getTime()), null, roomTypeId);
        classroomDAO.persist(classroom);
        return "Staff_Classroom";
    }
}