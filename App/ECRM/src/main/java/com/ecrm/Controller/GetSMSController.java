package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.Entity.*;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Htang on 7/17/2015.
 */
@Controller
@RequestMapping("offline")
public class GetSMSController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    UserInfoDAOImpl userInfoDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    MessageDAOImpl messageDAO;

    @RequestMapping(value = "getSMS", method = RequestMethod.POST)
    public void getSMS(HttpServletRequest request) throws TwilioRestException {
        String body = request.getParameter("Body");
        System.out.println("Message body: " + body);
        String from = request.getParameter("From");
        System.out.println("Message from:" + from);
        String classroomId = body.split("-")[1];
        TblMessageEntity tblMessageEntity = new TblMessageEntity();
        tblMessageEntity.setBody(classroomId);
        tblMessageEntity.setPhone(from);
        tblMessageEntity.setIsRead(false);
        messageDAO.persist(tblMessageEntity);
        /*TblClassroomEntity classroomEntity = classroomDAO.find(Integer.parseInt(classroomId));
        List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
        LocalTime localTime = new LocalTime();
        LocalTime noon = new LocalTime("12:00:00");
        List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
        if (localTime.isBefore(noon)) {
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(Integer.parseInt(classroomId), "Morning");
        } else {
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(Integer.parseInt(classroomId), "Noon");
        }
        List<String> availableClassroom = new ArrayList<String>();
        for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, validClassrooms);
            if (!classroom.isEmpty()) {
                if (availableClassroom.isEmpty()) {
                    availableClassroom = classroom;
                } else {
                    Iterator<String> it = availableClassroom.iterator();
                    while (it.hasNext()) {
                        String room = it.next();
                        if (!classroom.contains(room)) {
                            it.remove();
                        }
                    }
                }
            }
        }
        if (!availableClassroom.isEmpty()) {

            System.out.println("Total: " + availableClassroom.size() + " available room");
            availableClassroom = Utils.sortClassroom(availableClassroom, classroomEntity.getName());
            availableClassroom.remove(classroomEntity.getName());
            TblClassroomEntity changeClassroomEntity = classroomDAO.getClassroomByName(availableClassroom.get(0));

            //change room
            GCMController gcmController = new GCMController();
            System.out.println("Kiem lich hien tai: " + currentSchedule.size());
            for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
                String message = changeRoom(tblScheduleEntity, changeClassroomEntity);
                SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
            }
        } else {
            System.out.println("Can't find any available room");
        }*/
    }

    public String changeRoom(TblScheduleEntity tblScheduleEntity, TblClassroomEntity changeClassroomEntity) {
        tblScheduleEntity.setIsActive(false);
        tblScheduleEntity.setNote("Đổi sang phòng " + changeClassroomEntity.getName());
        scheduleDAO.merge(tblScheduleEntity);
        TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng " + tblScheduleEntity.getTblClassroomByClassroomId().getName()
                + " sang phòng " + changeClassroomEntity.getName(),  tblScheduleEntity.getDate(), true, tblScheduleEntity.getScheduleConfigId());
        String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                + tblScheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom() + " ngày " + tblScheduleEntity.getDate();
        scheduleDAO.persist(newSchedule);
        System.out.println(message);
        return message;
    }

    @RequestMapping(value = "getBody")
    @ResponseBody
    public String getBody(){
        List<TblMessageEntity> tblMessageEntities = messageDAO.getUnreadMessage();
        String result="";
        for(TblMessageEntity tblMessageEntity: tblMessageEntities){
            result+=tblMessageEntity.getBody()+"-";
            tblMessageEntity.setIsRead(true);
            messageDAO.merge(tblMessageEntity);
        }
        System.out.println("Nhan duoc: "+tblMessageEntities.size()+" tin nhan!");

    return result;
    }
}
