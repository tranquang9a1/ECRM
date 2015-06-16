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
    public List<String> changeRoom(HttpServletRequest request, @RequestParam("classroomId")int classroomId){
        List<String> messages = new ArrayList<String>();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        for(TblScheduleEntity tblScheduleEntity: tblScheduleEntityList){
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
            if(!classroom.isEmpty()){
                TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroom.get(0));
                tblScheduleEntity.setIsActive(false);
                scheduleDAO.merge(tblScheduleEntity);
                TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), classroomEntity.getId(),
                        tblScheduleEntity.getNumberOfStudents(), "Thay doi phong",tblScheduleEntity.getTimeFrom(),
                        tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
                String message = "Da doi phong cho giao vien " + tblScheduleEntity.getUsername()+" tu phong: "+
                        tblScheduleEntity.getTblClassroomByClassroomId().getName()+" sang phong: "+newSchedule.getTblClassroomByClassroomId().getName()+".";
                messages.add(message);
            }else{
                String message = "Khong con phong trong cho giao vien: "+ tblScheduleEntity.getUsername()+" cua phong: "+
                        tblScheduleEntity.getTblClassroomByClassroomId().getName()+".";
            }
        }
        return messages;
    }
}
