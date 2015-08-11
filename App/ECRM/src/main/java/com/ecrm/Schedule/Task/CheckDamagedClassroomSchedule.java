package com.ecrm.Schedule.Task;

import com.ecrm.Controller.GCMController;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.*;
import com.ecrm.Service.ChangeRoomService;
import com.ecrm.Service.CheckDamageService;
import com.ecrm.Service.GCMService;
import com.ecrm.Service.ReportService;
import com.ecrm.Utils.Constant;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Htang on 6/22/2015.
 */
@EnableScheduling
@EnableAsync
public class CheckDamagedClassroomSchedule {
    public static final String LAST_RUN_TXT = "LastRun.txt";
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
    @Autowired
    CheckDamageService checkDamageService;

    /*@Scheduled(fixedDelay = 1000)
    public void checkChangeClassroom() throws Exception {
        LocalTime localTime = new LocalTime();
        LocalDate localDate = new LocalDate();
        int hour = localTime.getHourOfDay();
        if (localDate.getDayOfWeek() != 7) {

            if (Utils.checkCronJob() && ((hour == 6 && localTime.getMinuteOfHour() == 0 && localTime.getSecondOfMinute() == 0)
                    || (hour == 12 && localTime.getMinuteOfHour() == 15 && localTime.getSecondOfMinute() == 0))) {
                System.out.println("Task 1: Task check change room run!!! Current time is : " + new Date());
                //tim nhung phong bi hu hai ma chua sua
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
                String dayTime = Integer.toString(hour).concat(":00:00");
                for (TblClassroomEntity currentClassroom : tblClassroomEntities) {
                    //tim nhung phong chua hu hai
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    System.out.println("Task 1: Get suitable classroom!");
                    int classroomId = currentClassroom.getId();

                    List<TblScheduleEntity> currentSchedule = scheduleDAO.findAllScheduleInClassroomByDayTime(classroomId,
                            dayTime);
                    if (!currentSchedule.isEmpty()) {
                        System.out.println("Task 1: Get all schedule in classroom: " + currentClassroom.getName());
                        System.out.println("Task 1: Total: " + currentSchedule.size() + " schedules!");
                        String classroomName = "";
                        List<TblReportEntity> liveReportsInRoom = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
                        System.out.println("Task 1: Get all reports in classroom: " + liveReportsInRoom.size() + " reports");
                        for (TblReportEntity tblReportEntity : liveReportsInRoom) {
                            if (tblReportEntity.getChangedRoom() != null) {
                                classroomName = tblReportEntity.getChangedRoom();
                                System.out.println("Task 1: Classroom was changed to last day: " + classroomName);
                            }
                        }
                        if (classroomName.trim().length() == 0) {
                            System.out.println("Task 1: Last day, classroom " + currentClassroom.getName() + " was not changed to any classroom!");
                        }
                        try {
                            changeRoomService.changingRoom(currentSchedule, validClassrooms, classroomName, currentClassroom);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Task 1: End changing room at:" + new Date());
                System.out.println("");

            }
            if (hour == 7 && localTime.getMinuteOfHour() == 0 && localTime.getSecondOfMinute() == 0 && Utils.checkCronJob()) {
                System.out.println("Task 1: Task check time using run!!! Current time is: " + new Date());
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
                for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                    double time = 0;
                    List<TblScheduleEntity> tblScheduleEntities = classroomEntity.getTblSchedulesById();
                    for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                        Time timeFrom = tblScheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom();
                        Time timeTo = tblScheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo();
                        time = timeTo.getTime() - timeFrom.getTime();
                    }
                    List<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
                    for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                        if (tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getIsManaged() && tblEquipmentEntity.getUsingTime() > 0) {
                            double timeRemain = tblEquipmentEntity.getTimeRemain() - time/(60*1000*60);
                            if (timeRemain <= tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getExpiredTime()
                                    && timeRemain > 0) {
                                String message = "Thiết bị: " + tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getName() + " " + tblEquipmentEntity.getName() + " số serial: " + tblEquipmentEntity.getSerialNumber() +
                                        " của phòng: " + classroomEntity.getName() + " chỉ còn: " + timeRemain + " giờ!";
                                gcmService.sendNotification(message, userDAO.getAllStaff().get(0).getTblUserInfoByUsername().getDeviceId());
                            }
                            tblEquipmentEntity.setTimeRemain(timeRemain);
                            equipmentDAO.merge(tblEquipmentEntity);
                            if (timeRemain <= 0) {
                                String message = "Thiết bị: " + tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getName() + " " + tblEquipmentEntity.getName() + " số serial: " + tblEquipmentEntity.getSerialNumber() +
                                        " của phòng: " + classroomEntity.getName() + " hết hạn sử dụng";
                                gcmService.sendNotification(message, userDAO.getAllStaff().get(0).getTblUserInfoByUsername().getDeviceId());
                                tblEquipmentEntity.setStatus(false);
                                equipmentDAO.merge(tblEquipmentEntity);
                                classroomEntity.setDamagedLevel(checkDamageService.checkDamagedLevelForEquipmentResolve(classroomEntity));
                                classroomEntity.setUpdateTime(new Timestamp(new Date().getTime()));
                                classroomDAO.merge(classroomEntity);
                            }

                        }
                    }
                }
                System.out.println("Task 1: Kết thúc cronjob check equipment vào lúc:" + new Date());
                System.out.println("");
            }
            if (hour == 0 && localTime.getMinuteOfHour() == 0 && localTime.getSecondOfMinute() == 0) {
                try {
                    System.out.println("Daily cronjob!");
                    String date = Long.toString(new Date().getTime());
                    File file = new File(Constant.FILE_PATH + LAST_RUN_TXT);
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(date);
                    bw.close();
                    System.out.println("Checking schedule!");
                    Date maxDate = scheduleDAO.getMaxDate();
                    Date currentDate = new Date();
                    long period = maxDate.getTime() - currentDate.getTime();
                    if (period >= 0 && period <= 604800000) {
                        int day = (int) ((period / (1000 * 60 * 60 * 24)) % 7) + 1;
                        String message = "Lịch trong hệ thống chỉ còn " + day + " ngày! Hãy nhập thêm lịch?";
                        List<TblUserEntity> tblUserEntity = userDAO.getAllStaff();
                        gcmService.sendNotification(message, tblUserEntity.get(0).getTblUserInfoByUsername().getDeviceId());
                    }
                    if (period < 0) {
                        String message = "Đã hết lịch trong hệ thống! Hãy nhập thêm lịch?";
                        List<TblUserEntity> tblUserEntity = userDAO.getAllStaff();
                        gcmService.sendNotification(message, tblUserEntity.get(0).getTblUserInfoByUsername().getDeviceId());
                    }
                    System.out.println("Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedDelay = 14000)
    public void changeRoom() throws TwilioRestException {
        LocalDate localDate = new LocalDate();
        LocalTime localTime = new LocalTime();
        if (localDate.getDayOfWeek() != 7) {
            if (((localTime.isAfter(new LocalTime("07:05:00")) && localTime.isBefore(new LocalTime("12:00:00"))) ||
                    localTime.isAfter(new LocalTime("12:20:00")) && localTime.isBefore(new LocalTime("21:00:00"))) && Utils.checkCronJob()) {
                LocalTime noon = new LocalTime("12:00:00");
                System.out.println("Task 2: Start cronjob changeroom lúc: " + new Date());
                List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
                System.out.println("Task 2: Get all damaged class. Total: " + tblClassroomEntities.size());
                for (TblClassroomEntity currentClassroom : tblClassroomEntities) {
                    List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
                    System.out.println("Task 2: Get all report in class: " + currentClassroom.getName() + ". Total: " + tblReportEntities.size());
                    List<TblClassroomEntity> validClassrooms = classroomDAO.getValidClassroom();
                    System.out.println("Task 2: Get all valid class. Total: " + validClassrooms.size());
                    int i = 0;
                    for (TblReportEntity tblReportEntity : tblReportEntities) {
                        System.out.println("Task 2: Set report " + i + 1);
                        List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
                        if (localTime.isBefore(noon)) {
                            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Morning");
                        } else {
                            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Noon");
                        }
                        if (currentSchedule.isEmpty()) {
                            System.out.println("Task 2: There are no schedule!");
                            break;
                        }
                        String changeRoom = "";
                        try {
                            changeRoom = changeRoomService.changingRoom(currentSchedule, validClassrooms, "", currentClassroom);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //update status report
                        System.out.println("Task 2: Change report status!");
                        tblReportEntity.setChangedRoom(changeRoom);
                        tblReportEntity.setStatus(2);
                        reportDAO.merge(tblReportEntity);
                        i++;
                    }
                }
                System.out.println("Task 2: End cronjob changeroom at: " + new Date());
                System.out.println("");
            }
        }
    }


    @Scheduled(fixedDelay = 30000)
    @ResponseBody
    public void test() throws IOException, TwilioRestException {
        LocalDate localDate = new LocalDate();
        LocalTime localTime = new LocalTime();
        if (localDate.getDayOfWeek() != 7) {
            if (((localTime.isAfter(new LocalTime("07:05:00")) && localTime.isBefore(new LocalTime("12:00:00"))) ||
                    localTime.isAfter(new LocalTime("12:20:00")) && localTime.isBefore(new LocalTime("21:00:00"))) && Utils.checkCronJob()) {
                System.out.println("Task 3: Run cronjob offline at:" + new Date());
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
                            currentSchedule = changeRoomService.getScheduleByDayTime(currentClassroom.getId());
                            try {
                                changeRoomService.changingRoom(currentSchedule, validClassrooms, "", currentClassroom);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                bufferedReader.close();
                System.out.println("Task 3: End cronjob offline at: " + new Date());
                System.out.println("");
            }
        }

    }*/

}
