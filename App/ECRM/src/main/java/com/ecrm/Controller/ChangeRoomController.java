package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Htang on 6/16/2015.
 */
@Controller
@RequestMapping("/staff")
public class ChangeRoomController {
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;


    @RequestMapping(value = "getAvailableRoom")
    public
    @ResponseBody
    List<String> getAvailableRoom(HttpServletRequest request, @RequestParam("classroomId") int classroomId) {
        List<String> availableClassroom = new ArrayList<String>();
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
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
        return availableClassroom;
    }

    @RequestMapping(value = "changeRoom")
    public
    @ResponseBody
    Boolean changeRoom(String currentClassroom, String changeClassroom) throws TwilioRestException {
        TblClassroomEntity currentClassroomEntity = classroomDAO.getClassroomByName(currentClassroom);
        TblClassroomEntity changeClassroomEntity = classroomDAO.getClassroomByName(changeClassroom);
        List<TblScheduleEntity> currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroomEntity.getId());
        for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
            tblScheduleEntity.setIsActive(false);
            scheduleDAO.merge(tblScheduleEntity);
            TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                    tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng " + tblScheduleEntity.getTblClassroomByClassroomId().getName()
                    + " sang phòng " + changeClassroomEntity.getName(), tblScheduleEntity.getTimeFrom(),
                    tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
            String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                    tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                    + tblScheduleEntity.getTimeFrom() + " ngày " + tblScheduleEntity.getDate();
            scheduleDAO.persist(newSchedule);
            SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
        }
        return true;
    }
}
