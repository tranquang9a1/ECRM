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
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * Created by Htang on 6/22/2015.
 */
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
    public void checkChangeClassroom() throws TwilioRestException {
        System.out.println("Task check change room run!!! Current time is : " + new Date());
        //tim nhung phong bi hu hai ma chua sua
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
        //tim nhung phong chua hu hai
        List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            int classroomId = classroomEntity.getId();
            List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleInClassroom(classroomId);
            for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                List<String> avaiClassroom = Utils.getAvailableRoom(tblScheduleEntity, validClassrooms);
                if (!avaiClassroom.isEmpty()) {
                    String username = tblScheduleEntity.getUsername();
                    TblUserEntity user = userDAO.findUser(username);
                    TblClassroomEntity newClassroom = classroomDAO.getClassroomByName(avaiClassroom.get(0));
                    tblScheduleEntity.setIsActive(false);
                    scheduleDAO.merge(tblScheduleEntity);
                    TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), newClassroom.getId(),
                            tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng", tblScheduleEntity.getTimeFrom(),
                            tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
                    String message = "Đã đổi phòng cho giáo viên " + username + " từ phòng: " +
                            tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + newClassroom.getName() + ".";
                    scheduleDAO.persist(newSchedule);
                    System.out.println("Đã đổi phòng cho giáo viên " + username + " từ phòng: " +
                            tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + newClassroom.getName() + ".");
                    SmsUtils.sendMessage(user.getTblUserInfoByUsername().getPhone(), message);
                }
            }
        }


        /*System.out.println("Task check time using run!!! Current time is: "+ new Date());
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
        }*/
    }

    @Scheduled(cron = "0 0 7 ? * MON-SAT")
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
