package com.ecrm.Schedule.Task;

import com.ecrm.Controller.GCMController;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
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
        int hour = localTime.getHourOfDay();
        if (localDate.getDayOfWeek() != 7) {
            if ((hour == 6 && localTime.getMinuteOfHour() == 0 && localTime.getSecondOfMinute() == 0)
                    || (hour == 12 && localTime.getMinuteOfHour() == 15 && localTime.getSecondOfMinute() == 0)) {
                System.out.println("Task check change room run!!! Current time is : " + new Date());
                //tim nhung phong bi hu hai ma chua sua
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
                String dayTime = Integer.toString(hour).concat(":00:00");
                for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                    //tim nhung phong chua hu hai
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    System.out.println("Lấy các phòng thích hợp!");
                    int classroomId = classroomEntity.getId();

                    List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleInClassroomByDayTime(classroomId,
                            dayTime);
                    if (!tblScheduleEntities.isEmpty()) {
                        System.out.println("Lấy tất cả schedule trong phòng: " + classroomEntity.getName());
                        System.out.println("Tổng cộng: " + tblScheduleEntities.size() + " schedules!");
                        String classroomName = "";
                        List<TblReportEntity> liveReportsInRoom = reportDAO.getLiveReportsInRoom(classroomEntity.getId());
                        System.out.println("Lấy các report hiện có của phòng: " + liveReportsInRoom.size() + " reports");
                        for (TblReportEntity tblReportEntity : liveReportsInRoom) {
                            if (tblReportEntity.getChangedRoom() != null) {
                                classroomName = tblReportEntity.getChangedRoom();
                                System.out.println("Phòng đã đổi hôm qua: " + classroomName);
                            }
                        }
                        TblClassroomEntity changeClassroomEntity = new TblClassroomEntity();
                        if (classroomName.trim().length() != 0) {
                            changeClassroomEntity = classroomDAO.getClassroomByName(classroomName);
                        } else {
                            System.out.println("Hôm qua phòng " + classroomEntity.getName() + " không được đổi cho phòng nào khác!");
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
                            } else {
                                for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                                    validClassrooms = classroomDAO.getValidClassroom();
                                    List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, validClassrooms);
                                    if (!classroom.isEmpty()) {
                                        TblClassroomEntity newClassroom = classroomDAO.getClassroomByName(classroom.get(0));
                                        String message = changeRoom(tblScheduleEntity,newClassroom);
                                        SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                                        gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());

                                    }
                                }
                            }
                        }
                        if (changeClassroomEntity != null) {
                            System.out.println("Bắt đầu đổi phòng!");
                            for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                                String message = changeRoom(tblScheduleEntity,changeClassroomEntity);
                                SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                                gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
                            }
                            System.out.println("Kết thúc đổi phòng!");
                        }
                    }
                }
                System.out.println("Kết thúc cronjob vào lúc:" + new Date());

            }
            if (hour == 7 && localTime.getMinuteOfHour() == 0 && localTime.getSecondOfMinute() == 0) {
                System.out.println("Task check time using run!!! Current time is: " + new Date());
                List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleToday();
                for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                    TblScheduleConfigEntity scheduleConfigEntity = tblScheduleEntity.getTblScheduleConfigByScheduleConfigId();
                    Time timeFrom = scheduleConfigEntity.getTimeFrom();
                    Time timeTo = scheduleConfigEntity.getTimeTo();
                    long time = timeTo.getTime() - timeFrom.getTime();
                    double rs = time / (1000 * 60 * 60);
                    TblClassroomEntity classroomEntity = tblScheduleEntity.getTblClassroomByClassroomId();
                    List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.getProjector(classroomEntity.getId());
                    if (!tblEquipmentEntities.isEmpty()) {
                        for (TblEquipmentEntity equipmentEntity : tblEquipmentEntities) {
                            double timeRemain = equipmentEntity.getTimeRemain() - rs;
                            equipmentEntity.setTimeRemain(timeRemain);
                            equipmentDAO.merge(equipmentEntity);
                            if (equipmentEntity.getTimeRemain() <= 50) {
                                String message = "Bóng đèn của projector: " + equipmentEntity.getName() + " số serial: " + equipmentEntity.getSerialNumber() +
                                        " của phòng: " + equipmentEntity.getTblClassroomByClassroomId().getName() + " sắp hết thời gian sử dụng!";
                                gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
                            }
                        }

                    }

                }
                System.out.println("Kết thúc cronjob check equipment vào lúc:" + new Date());

            }
        }
    }

    @Scheduled(fixedDelay = 120000)
    public void changeRoom() throws TwilioRestException {
        LocalTime localTime = new LocalTime();
        LocalTime noon = new LocalTime("12:00:00");
        System.out.println("Bắt đầu cronjob changeroom lúc: " + new Date());
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
        System.out.println("Get all damaged class. Total: " + tblClassroomEntities.size());
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(classroomEntity.getId());
            System.out.println("Get all report in class: " + classroomEntity.getName() + ". Total: " + tblReportEntities.size());
            List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
            System.out.println("Get all valid class. Total: " + validClassrooms.size());
            List<String> availableClassroom = new ArrayList<String>();
            int i = 0;
            for (TblReportEntity tblReportEntity : tblReportEntities) {
                System.out.println("Set report " + i + 1);
                List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
                if (localTime.isBefore(noon)) {
                    currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(classroomEntity.getId(), "Morning");
                } else {
                    currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(classroomEntity.getId(), "Noon");
                }
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
                    //change room
                    if (i == 0) {
                        GCMController gcmController = new GCMController();
                        TblClassroomEntity changeClassroomEntity = classroomDAO.getClassroomByName(availableClassroom.get(0));
                        System.out.println("Kiem lich hien tai: " + currentSchedule.size());
                        for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
                            String message = changeRoom(tblScheduleEntity, changeClassroomEntity);
                            SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                            gcmController.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
                        }
                    }

                    //update status report

                    tblReportEntity.setChangedRoom(availableClassroom.get(0));
                    tblReportEntity.setStatus(1);
                    reportDAO.merge(tblReportEntity);
                    i++;
                } else {
                    System.out.println("Can't find any available room");
                }

            }
        }
        System.out.println("End cronjob changeroom at: " + new Date());
        System.out.println("");
    }

    public String changeRoom(TblScheduleEntity tblScheduleEntity, TblClassroomEntity changeClassroomEntity){
        tblScheduleEntity.setIsActive(false);
        tblScheduleEntity.setNote("Đổi sang phòng " + changeClassroomEntity.getName());
        scheduleDAO.merge(tblScheduleEntity);
        TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng " + tblScheduleEntity.getTblClassroomByClassroomId().getName()
                + " sang phòng " + changeClassroomEntity.getName(), tblScheduleEntity.getTimeFrom(),
                tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true, tblScheduleEntity.getScheduleConfigId());
        String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                + tblScheduleEntity.getTimeFrom() + " ngày " + tblScheduleEntity.getDate();
        scheduleDAO.persist(newSchedule);
        System.out.println(message);
        return message;
    }

}
