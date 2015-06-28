package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
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

    private static final String GOOGLE_SERVER_KEY = "AIzaSyCmWFgLqqwZZZjPjZ2DmbJWqECnL9Etk8w";
    static final String MESSAGE_KEY = "message";


    @Autowired
    UserDAOImpl userDAO;


    @RequestMapping(value = "/register", method= RequestMethod.POST)
    public String gcmNotify(@RequestParam("regId") String regId, @RequestParam("shareRegId") String share,
                            HttpServletRequest request, @RequestParam("username") String username) {



        // GCM RedgId of Android device to send push notification
        if (share != null && !share.isEmpty()) {

            try {
                userDAO.updateDeviceId(username, regId);
                request.setAttribute("pushStatus", "GCM RegId Received.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "notify";
    }

    @RequestMapping(value ="/sendNotification")
    public String sendNotification(@RequestParam("message") String msg, HttpServletRequest request,
                                   @RequestParam("ListUser") String listUser) {
        Result result = null;
        String[] users = listUser.split(",");
        try {
           for (int  i = 0; i < users.length; i++) {
               String deviceId = userDAO.getDeviceId(users[i]);

               Sender sender = new Sender(GOOGLE_SERVER_KEY);
               Message message = new Message.Builder().timeToLive(120)
                       .delayWhileIdle(false).addData(MESSAGE_KEY, msg).build();
               System.out.println("User: " + users[i] + " - regId: " + deviceId);
               result = sender.send(message, deviceId, 1);
           }
            request.setAttribute("pushStatus", result.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            request.setAttribute("pushStatus",
                    "RegId required: " + ioe.toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("pushStatus", e.toString());
        }
        return "notify";
    }

    public void sendNotification(String msg, String listUser) {
        Result result = null;
        String[] users = listUser.split(",");
        try {
            for (int  i = 0; i < users.length; i++) {
                String deviceId = userDAO.getDeviceId(users[i]);

                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message = new Message.Builder().timeToLive(120)
                        .delayWhileIdle(false).addData(MESSAGE_KEY, msg).build();
                System.out.println("User: " + users[i] + " - regId: " + deviceId);
                result = sender.send(message, deviceId, 1);
            }
            System.out.println(result.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

}
