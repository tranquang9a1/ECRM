package com.ecrm.Controller;

import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.RoomType;
import com.ecrm.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpServletRequest request) {
        return "Login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userDAO.login(username, password);
        if (user.isStatus()) {
            session.setAttribute("USER", user);
            if (user.getRole().getName().equals("Admin")) {
                return "home";
            }
            if (user.getRole().getName().equals("Staff")) {
                return "Staff_Classroom";
            }
            if (user.getRole().getName().equals("User")) {
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
        RoomType roomType = new RoomType(slots, verticalRows,horizontalRows,noSlotsEachHRows,airConditioning,fan,projectors,
                speaker,television, new Timestamp(date.getTime()));
        roomTypeDAO.persist(roomType);
        return "Staff_Classroom";
    }
}