package com.ecrm.Service;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class GCMService {

    private static final String GOOGLE_SERVER_KEY = "AIzaSyCmWFgLqqwZZZjPjZ2DmbJWqECnL9Etk8w";
    static final String MESSAGE_KEY = "message";

    @Autowired
    UserDAOImpl userDAO;

    public boolean gcmNotify(String regId, String share, String username) {

        if (share != null && !share.isEmpty()) {
            try {
                userDAO.updateDeviceId(username, regId);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Result sendNotifications(String msg, String listUser) {
        Result result = null;
        String[] users = listUser.split(",");
        try {
            for (int i = 0; i < users.length; i++) {
                String deviceId = userDAO.getDeviceId(users[i]);

                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message = new Message.Builder().timeToLive(120)
                        .delayWhileIdle(false).addData(MESSAGE_KEY, msg).build();
                System.out.println("User: " + users[i] + " - regId: " + deviceId);
                result = sender.send(message, deviceId, 1);
            }
           return result;
        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public void sendNotification(String msg, String deviceId) {
        System.out.println("Start send notification" + deviceId);
        Result result = null;
        try {

            Sender sender = new Sender(GOOGLE_SERVER_KEY);
            Message message = new Message.Builder().timeToLive(120)
                    .delayWhileIdle(false).addData(MESSAGE_KEY, msg).build();
            result = sender.send(message, deviceId, 1);
            System.out.println("Send Notification Success: " + result.toString());
            System.out.println("RegID: " + deviceId);
            System.out.println("End send notification");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Error when send notification " + ioe.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when send notification " + e.toString());
        }
    }
}
