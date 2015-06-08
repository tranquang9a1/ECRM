package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.TblUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by Htang on 6/7/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserDAOImpl userDAO;

    @RequestMapping(value = "account")
    public String init(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab) {
        Collection<TblUserEntity> tblUserEntities = userDAO.findAll();
        request.setAttribute("ALLUSERS", tblUserEntities);
        request.setAttribute("ACTIVETAB", activeTab);
        return "Admin_Account";
    }

    @RequestMapping(value = "editAccount")
    public String activateAccount(HttpServletRequest request, @RequestParam("Action") String action,
                                  @RequestParam("Username") String username) {
        TblUserEntity tblUserEntity = new TblUserEntity();
        String[] array = username.split("-");
        if (action.equals("Activate")) {
            for (int i = 0; i < array.length; i++) {
                tblUserEntity = userDAO.findUser(array[i]);
                tblUserEntity.setStatus(true);
                userDAO.merge(tblUserEntity);
            }
        }else{
            for (int i = 0; i < array.length; i++) {
                tblUserEntity = userDAO.findUser(array[i]);
                tblUserEntity.setStatus(false);
                userDAO.merge(tblUserEntity);
            }
        }
        return "redirect:/admin/account?ACTIVETAB=tab1";
    }
}
