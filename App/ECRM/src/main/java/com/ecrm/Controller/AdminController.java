package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.Impl.UserInfoDAOImpl;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import com.ecrm.Utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Htang on 6/7/2015.
 */
@Controller
@RequestMapping("/quan-ly")
public class AdminController {
    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    UserInfoDAOImpl userInfoDAO;

    @RequestMapping(value = "")
    public String init(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        HttpSession session  =  request.getSession();
        if(session!=null){
            int pageNumber = 0;
            try {
                pageNumber = Integer.parseInt(page);
            } catch (NumberFormatException ex) {
                return "Error";
            }
            int size = 9;

            int numberOfTeacher = userDAO.getNumberOfTeacher();
            int numberOfPage = numberOfTeacher/size + (numberOfTeacher%size>0?1:0);
            if(pageNumber > numberOfPage && pageNumber > 0) {
                return "Error";
            }

            if(numberOfTeacher == 0) {
                request.setAttribute("LSTUSERS", new ArrayList<TblUserEntity>());
            } else {
                if(pageNumber == 0) {
                    pageNumber = 1;
                }

                List<TblUserEntity> tblUserEntities = userDAO.getTeacher(pageNumber, size);

                request.setAttribute("LSTUSERS", tblUserEntities);
                request.setAttribute("CURRENTPAGE", pageNumber);
                request.setAttribute("MAXPAGE", numberOfPage);
            }

            return "Admin_Account";
        }else{
            return "Login";
        }

    }

    @RequestMapping(value = "editAccount")
    public String activateAccount(HttpServletRequest request, @RequestParam("Action") String action,
                                  @RequestParam("Username") String username, @RequestParam("Page") int page) {
        HttpSession session  =  request.getSession();
        if(session!=null){
            TblUserEntity tblUserEntity = userDAO.findUser(username);
            if(action.equals("Activate")){
                tblUserEntity.setStatus(true);
                userDAO.merge(tblUserEntity);
            }else{
                tblUserEntity.setStatus(false);
                userDAO.merge(tblUserEntity);
            }

            if(page == 1) {
                return "redirect:/quan-ly";
            } else {
                return "redirect:/quan-ly?trang=" + page;
            }
        }
        else{
            return "Login";
        }
    }

    @RequestMapping(value = "createAccount")
    @Transactional
    public String createAccount(HttpServletRequest request, @RequestParam("Username") String username,
                                @RequestParam("FullName") String fullName, @RequestParam("Phone") String phone){
        HttpSession session  =  request.getSession();
        if(session!=null){
            TblUserEntity tblUserEntity = new TblUserEntity();
            tblUserEntity.setUsername(username);
            tblUserEntity.setPassword(Constant.DEFAULT_PASSWORD);
            tblUserEntity.setStatus(true);
            tblUserEntity.setRoleId(3);
            userDAO.persist(tblUserEntity);

            TblUserInfoEntity tblUserInfoEntity = new TblUserInfoEntity();
            tblUserInfoEntity.setUsername(username);
            tblUserInfoEntity.setFullName(fullName);
            tblUserInfoEntity.setPhone(phone);
            tblUserInfoEntity.setDeviceId(null);
            tblUserInfoEntity.setLastLogin(new Timestamp(new Date().getTime()));
            userInfoDAO.persist(tblUserInfoEntity);
            return "redirect:/quan-ly";
        }else{
            return "Login";
        }

    }
}
