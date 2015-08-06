package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DTO.ResultDTO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Service.ChangeRoomService;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.GCMService;
import com.ecrm.Service.ReportService;
import com.ecrm.Utils.Constant;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalTime;
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
    private GCMService gcmService;
    @Autowired
    ChangeRoomService changeRoomService;
    @Autowired
    ReportService reportService;
    @Autowired
    ClassroomService classroomService;

    @RequestMapping(value = "getAvailableRoom")
    public
    @ResponseBody
    List<String> getAvailableRoom(HttpServletRequest request, @RequestParam("classroomId") int classroomId) {
        List<String> availableClassroom = new ArrayList<String>();
        availableClassroom = changeRoomService.getAvailableClassroom(classroomId);
        return availableClassroom;
    }

    @RequestMapping(value = "changeRoom")
    public
    @ResponseBody
    ResultDTO changeRoom(@RequestParam("from")String currentClassroom,@RequestParam("to") String changeClassroom) throws TwilioRestException {
        TblClassroomEntity currentClassroomEntity = classroomService.getClassroomByName(currentClassroom);
        if (currentClassroomEntity.getDamagedLevel() < Constant.MIN_DAMAGED_CHANGE_ROOM) {
            currentClassroomEntity.setDamagedLevel(Constant.MIN_DAMAGED_CHANGE_ROOM);
            classroomService.updateClassroom(currentClassroomEntity);
        }
        TblClassroomEntity changeClassroomEntity = classroomService.getClassroomByName(changeClassroom);
        int currentClassroomId = currentClassroomEntity.getId();
        List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
        currentSchedule = changeRoomService.getScheduleByDayTime(currentClassroomId);
        for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
            String message = changeRoomService.changeRoom(tblScheduleEntity,changeClassroomEntity);
            SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
            gcmService.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
        }
        //update status report
        reportService.changeLiveReportStatus(currentClassroomId, changeClassroom);
        return new ResultDTO(200, "Change room successful");
    }
}
