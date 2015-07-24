package com.ecrm.Schedule.Task;

import com.ecrm.Controller.GCMController;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.*;
import com.ecrm.Service.ChangeRoomService;
import com.ecrm.Service.GCMService;
import com.ecrm.Service.ReportService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

    @Autowired
    private GCMService gcmService;

    @Autowired
    ChangeRoomService changeRoomService;
    @Autowired
    ReportService reportService;


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
                for (TblClassroomEntity currentClassroom : tblClassroomEntities) {
                    //tim nhung phong chua hu hai
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    System.out.println("Get suitable classroom!");
                    int classroomId = currentClassroom.getId();

                    List<TblScheduleEntity> currentSchedule = scheduleDAO.findAllScheduleInClassroomByDayTime(classroomId,
                            dayTime);
                    if (!currentSchedule.isEmpty()) {
                        System.out.println("Get all schedule in classroom: " + currentClassroom.getName());
                        System.out.println("Total: " + currentSchedule.size() + " schedules!");
                        String classroomName = "";
                        List<TblReportEntity> liveReportsInRoom = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
                        System.out.println("Get all reports in classroom: " + liveReportsInRoom.size() + " reports");
                        for (TblReportEntity tblReportEntity : liveReportsInRoom) {
                            if (tblReportEntity.getChangedRoom() != null) {
                                classroomName = tblReportEntity.getChangedRoom();
                                System.out.println("Classroom was changed to last day: " + classroomName);
                            }
                        }
                        if (classroomName.trim().length() == 0) {
                            System.out.println("Last day, classroom " + currentClassroom.getName() + " was not changed to any classroom!");
                        }
                        changeRoomService.changingRoom(currentSchedule, validClassrooms, classroomName, currentClassroom);
                    }
                }
                System.out.println("End changing room at:" + new Date());

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
                                gcmService.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
                            }
                        }

                    }

                }
                System.out.println("Kết thúc cronjob check equipment vào lúc:" + new Date());

            }
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void changeRoom() throws TwilioRestException {
        LocalDate localDate = new LocalDate();
        LocalTime localTime = new LocalTime();
        if (localDate.getDayOfWeek() != 7) {
            if ((localTime.isAfter(new LocalTime("07:01:00")) && localTime.isBefore(new LocalTime("12:00:00"))) ||
                    localTime.isAfter(new LocalTime("12:16:00")) && localTime.isBefore(new LocalTime("21:00:00"))) {
                LocalTime noon = new LocalTime("12:00:00");
                System.out.println("Bắt đầu cronjob changeroom lúc: " + new Date());
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
                System.out.println("Get all damaged class. Total: " + tblClassroomEntities.size());
                for (TblClassroomEntity currentClassroom : tblClassroomEntities) {
                    List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
                    System.out.println("Get all report in class: " + currentClassroom.getName() + ". Total: " + tblReportEntities.size());
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    System.out.println("Get all valid class. Total: " + validClassrooms.size());
                    List<String> availableClassroom = new ArrayList<String>();
                    int i = 0;
                    for (TblReportEntity tblReportEntity : tblReportEntities) {
                        System.out.println("Set report " + i + 1);
                        List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
                        if (localTime.isBefore(noon)) {
                            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Morning");
                        } else {
                            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Noon");
                        }
                        changeRoomService.changingRoom(currentSchedule, validClassrooms, "", currentClassroom);
                        //update status report

                        tblReportEntity.setChangedRoom(availableClassroom.get(0));
                        tblReportEntity.setStatus(2);
                        reportDAO.merge(tblReportEntity);
                        i++;
                    }
                }
                System.out.println("End cronjob changeroom at: " + new Date());
                System.out.println("");
            }
        }
    }


    @Scheduled(fixedDelay = 60000)
    @ResponseBody
    public void test() throws IOException, TwilioRestException {
        LocalDate localDate = new LocalDate();
        LocalTime localTime = new LocalTime();
        if (localDate.getDayOfWeek() != 7) {
            if ((localTime.isAfter(new LocalTime("07:00:00")) && localTime.isBefore(new LocalTime("12:00:00"))) ||
                    localTime.isAfter(new LocalTime("12:15:00")) && localTime.isBefore(new LocalTime("21:00:00"))) {
                System.out.println("Run cronjob offline at:" + new Date());
                URL url = new URL("http://128.199.208.93/offline/getBody");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] array = line.split("-");
                    if (array.length > 0) {
                        for (int i = 0; i < array.length; i++) {
                            String classroomId = array[i];
                            TblClassroomEntity currentClassroom = classroomDAO.find(Integer.parseInt(classroomId));
                            List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                            List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
                            changeRoomService.getScheduleByDayTime(currentClassroom.getId());
                            changeRoomService.changingRoom(currentSchedule, validClassrooms, "", currentClassroom);
                        }
                    }
                }
                bufferedReader.close();
            }
        }

    }
}
