package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserDAOImpl userDAO;
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
            return "redirect:/bao-cao";
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
                return "redirect:/quan-ly";
            }
            if (user.getTblRoleByRoleId().getName().equals("Staff")) {
                return "redirect:/bao-cao";
            }
            if (user.getTblRoleByRoleId().getName().equals("Teacher")) {
                if(password.equals(Constant.DEFAULT_PASSWORD)) {
                    session.setAttribute("FIRSTLOGIN", true);
                }

                return "redirect:/giang-vien";
            }
        }
        request.setAttribute("MESSAGE","Tài khoản hoặc mật khẩu không đúng!");
        return "Login";
    }

    //Logout
    @RequestMapping(value = "dang-xuat")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("USER");
        return "Login";
    }

    @RequestMapping(value = "error")
    public String error(){
        return "Error";
    }

}