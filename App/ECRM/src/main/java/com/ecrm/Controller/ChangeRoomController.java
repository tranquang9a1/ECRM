package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

    @RequestMapping("/changeRoom")
    @ResponseBody
    public List<String> changeRoom(HttpServletRequest request, @RequestParam("classroomId") int classroomId) {
        List<String> messages = new ArrayList<String>();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
            if (!classroom.isEmpty()) {
                TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroom.get(0));
                tblScheduleEntity.setIsActive(false);
                scheduleDAO.merge(tblScheduleEntity);
                TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), classroomEntity.getId(),
                        tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng", tblScheduleEntity.getTimeFrom(),
                        tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
                String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                        tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + classroomEntity.getName() + ".";
                scheduleDAO.persist(newSchedule);
                messages.add(message);
            } else {
                String message = "Không còn phòng trống: " + tblScheduleEntity.getUsername() + " của phòng: " +
                        tblScheduleEntity.getTblClassroomByClassroomId().getName() + ".";
                messages.add(message);
            }
        }
        for (int i = 0; i < messages.size(); i++) {
            System.out.println(messages.get(i));
        }
        return messages;
    }
}
