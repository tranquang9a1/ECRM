package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import com.ecrm.Service.GCMService;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by QuangTV on 6/22/2015.
 */
@Controller
@RequestMapping("/notification")
public class GCMController {




    @Autowired
    private GCMService gcmService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String gcmNotify(@RequestParam("regId") String regId, @RequestParam("shareRegId") String share,
                            HttpServletRequest request, @RequestParam("username") String username) {


        boolean result = gcmService.gcmNotify(regId, share, username);
        if (result) {
            request.setAttribute("pushStatus", "GCM RegId Received.");
        }
        return "notify";
    }

    @RequestMapping(value = "/sendNotification")
    public String sendNotification(@RequestParam("message") String msg, HttpServletRequest request,
                                   @RequestParam("ListUser") String listUser) {


        Result result = gcmService.sendNotifications(msg, listUser);
        request.setAttribute("pushStatus", result.toString());
        return "notify";
    }


}
