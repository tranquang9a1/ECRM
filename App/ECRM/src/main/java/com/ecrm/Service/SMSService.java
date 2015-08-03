package com.ecrm.Service;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DTO.ResultDTO;
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

    public ResultDTO sendSMS(String listUsers, String message){
        String[] users = listUsers.split(",");
        try {
            for (int i = 0; i < users.length; i++) {
                String phoneNumber = userDAO.getPhoneNumber(users[i]);
                SmsUtils.sendMessage(phoneNumber, message);
            }
            return new ResultDTO(200, "Send success ");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO(500, "Error when send SMS");
        }
    }
}
