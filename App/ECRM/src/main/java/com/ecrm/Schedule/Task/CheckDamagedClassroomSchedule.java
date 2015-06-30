package com.ecrm.Schedule.Task;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;

/**
 * Created by Htang on 6/22/2015.
 */
@EnableWebMvc
@EnableAsync
@EnableScheduling
public class CheckDamagedClassroomSchedule {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;

    @Scheduled(cron = "0 0 6 ? * MON-SAT")
    @Async
    public void checkChangeClassroom() throws TwilioRestException {
        System.out.println("Task check change room run!!! Current time is : " + new Date());
        //tim nhung phong bi hu hai ma chua sua
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
        //tim nhung phong chua hu hai
        List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            int classroomId = classroomEntity.getId();
            List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleInClassroom(classroomId);
            List<String> availableClassroom = new ArrayList<String>();
            for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, validClassrooms);
                if(!classroom.isEmpty()){
                    if(availableClassroom.isEmpty()){
                        availableClassroom = classroom;
                    }else{
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
            if(!availableClassroom.isEmpty()){
                TblClassroomEntity changeClassroomEntity = classroomDAO.getClassroomByName(availableClassroom.get(0));
                for(TblScheduleEntity tblScheduleEntity:tblScheduleEntities){
                    tblScheduleEntity.setIsActive(false);
                    scheduleDAO.merge(tblScheduleEntity);
                    TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                            tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng "+tblScheduleEntity.getTblClassroomByClassroomId().getName()
                            +" sang phòng "+ changeClassroomEntity.getName(), tblScheduleEntity.getTimeFrom(),
                            tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
                    String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                            tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                            + tblScheduleEntity.getTimeFrom() + " ngày "+tblScheduleEntity.getDate();
                    scheduleDAO.persist(newSchedule);
                    SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                }
            }
        }

    }

    @Scheduled(cron = "0 0 7 ? * MON-SAT")
    @Async
    public void checkTimeUsing(){
        System.out.println("Task check time using run!!! Current time is: "+ new Date());
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleToday();
        for(TblScheduleEntity tblScheduleEntity: tblScheduleEntities){
            double slots = tblScheduleEntity.getSlots();
            TblClassroomEntity classroomEntity = tblScheduleEntity.getTblClassroomByClassroomId();
            List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.getProjector(classroomEntity.getId());
            if(!tblEquipmentEntities.isEmpty()){
                TblEquipmentEntity equipmentEntity = tblEquipmentEntities.get(0);
                equipmentEntity.setTimeRemain(equipmentEntity.getTimeRemain()-(slots*1.5));
                equipmentDAO.merge(equipmentEntity);
            }
        }
    }


}
