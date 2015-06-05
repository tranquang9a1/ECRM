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

                return "redirect:/staff/classroom";
            }
            if (user.getTblRoleByRoleId().getName().equals("Teacher")) {
                return "redirect:/giang-vien/thong-bao";
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


}