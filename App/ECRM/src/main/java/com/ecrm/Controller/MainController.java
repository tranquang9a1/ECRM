package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.RoomTypeDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        if(user == null) {
            return "Login";
        } else if("Teacher".equals(user.getTblRoleByRoleId().getName())) {
            return "redirect:/giang-vien";
        } else if ("Staff".equals(user.getTblRoleByRoleId().getName())) {
            return "redirect:/giang-vien";
        }

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
                return "redirect:/admin/account";
            }
            if (user.getTblRoleByRoleId().getName().equals("Staff")) {
                return "redirect:/bao-cao";
            }
            if (user.getTblRoleByRoleId().getName().equals("Teacher")) {
                return "redirect:/giang-vien";
            }
        }
        request.setAttribute("MESSAGE","Tài khoản hoặc mật khẩu không đúng!");
        return "Login";
    }

    //Logout
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("USER");
        return "Login";
    }


}