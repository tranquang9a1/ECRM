package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.Impl.UserInfoDAOImpl;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Created by Htang on 6/7/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    UserInfoDAOImpl userInfoDAO;
    @RequestMapping(value = "account")
    public String init(HttpServletRequest request) {
        List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
        request.setAttribute("LSTUSERS", tblUserEntities);
        return "Admin_Account";
    }

    @RequestMapping(value = "editAccount")
    public String activateAccount(HttpServletRequest request, @RequestParam("Action") String action,
                                  @RequestParam("username") String username) {
        TblUserEntity tblUserEntity = userDAO.findUser(username);
        if(action.equals("Activate")){
            tblUserEntity.setStatus(true);
            userDAO.merge(tblUserEntity);
        }else{
            tblUserEntity.setStatus(false);
            userDAO.merge(tblUserEntity);
        }
        return "redirect:/admin/account";
    }

    @RequestMapping(value = "createAccount")
    public String createAccount(HttpServletRequest request, @RequestParam("Username") String username,
                                @RequestParam("FullName") String fullName, @RequestParam("Phone") String phone){
        TblUserEntity tblUserEntity = new TblUserEntity();
        tblUserEntity.setUsername(username);
        tblUserEntity.setPassword("123456");
        tblUserEntity.setStatus(true);
        tblUserEntity.setRoleId(3);
        userDAO.persist(tblUserEntity);
        TblUserInfoEntity tblUserInfoEntity = new TblUserInfoEntity();
        tblUserInfoEntity.setUsername(username);
        tblUserInfoEntity.setFullName(fullName);
        tblUserInfoEntity.setPhone(phone);
        tblUserInfoEntity.setDeviceId(null);
        tblUserInfoEntity.setLastLogin(null);
        userInfoDAO.persist(tblUserInfoEntity);
        return "redirect:/admin/account";
    }
}
