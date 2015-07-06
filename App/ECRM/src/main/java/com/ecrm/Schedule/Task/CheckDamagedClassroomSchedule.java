package com.ecrm.Schedule.Task;

import com.ecrm.Controller.GCMController;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Htang on 6/22/2015.
 */
@EnableScheduling
@EnableAsync
public class CheckDamagedClassroomSchedule {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    ReportDAOImpl reportDAO;

        
    @Scheduled(cron = "${cron.expression}")
    public void checkChangeClassroom() throws Exception {
        LocalTime localTime = new LocalTime();
        LocalDate localDate = new LocalDate();
        GCMController gcmController = new GCMController();
        if (localDate.getDayOfWeek() != 7) {
            int time = localTime.getHourOfDay() + localTime.getMinuteOfHour() + localTime.getSecondOfMinute();
            if (time == 6) {
                System.out.println("Task check change room run!!! Current time is : " + new Date());
                //tim nhung phong bi hu hai ma chua sua
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();

                for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                    //tim nhung phong chua hu hai
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    int classroomId = classroomEntity.getId();
                    List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleInClassroom(classroomId);
                    String classroomName = "";
                    List<TblReportEntity> liveReportsInRoom = reportDAO.getLiveReportsInRoom(classroomEntity.getId());
                    for (TblReportEntity tblReportEntity : liveReportsInRoom) {
                        if (tblReportEntity.getChangedRoom() != null) {
                            classroomName = tblReportEntity.getChangedRoom();
                        }
                    }
                    TblClassroomEntity changeClassroomEntity = new TblClassroomEntity();
                    if (classroomName.trim().length() != 0) {
                        changeClassroomEntity = classroomDAO.getClassroomByName(classroomName);
                    } else {
                        List<String> availableClassroom = new ArrayList<String>();
                        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
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
                            changeClassroomEntity = classroomDAO.getClassroomByName(availableClassroom.get(0));
                        }
                    }
                    if (changeClassroomEntity != null) {
                        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                            tblScheduleEntity.setIsActive(false);
                            scheduleDAO.merge(tblScheduleEntity);
                            TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                                    tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng " + tblScheduleEntity.getTblClassroomByClassroomId().getName()
                                    + " sang phòng " + changeClassroomEntity.getName(), tblScheduleEntity.getTimeFrom(),
                                    tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true, tblScheduleEntity.getScheduleConfigId());
                            String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                                    tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                                    + tblScheduleEntity.getTimeFrom() + " ngày " + tblScheduleEntity.getDate();
                            scheduleDAO.persist(newSchedule);
                            /*SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                            gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());*/
                        }
                    }
                }

            }
            if (time == 7) {
                System.out.println("Task check time using run!!! Current time is: " + new Date());
                List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleToday();
                for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                    double slots = tblScheduleEntity.getSlots();
                    TblClassroomEntity classroomEntity = tblScheduleEntity.getTblClassroomByClassroomId();
                    List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.getProjector(classroomEntity.getId());
                    if (!tblEquipmentEntities.isEmpty()) {
                        TblEquipmentEntity equipmentEntity = tblEquipmentEntities.get(0);
                        equipmentEntity.setTimeRemain(equipmentEntity.getTimeRemain() - (slots * 1.5));
                        equipmentDAO.merge(equipmentEntity);
                    }
                }
            }
        }
    }

}
