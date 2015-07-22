package com.ecrm.Service;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class SMSService {

    @Autowired
    private UserDAOImpl userDAO;

    public void sendSMS(String listUsers, String message){
        String[] users = listUsers.split(",");
        try {
            for (int i = 0; i < users.length; i++) {
                String phoneNumber = userDAO.getPhoneNumber(users[i]);
                SmsUtils.sendMessage(phoneNumber, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
