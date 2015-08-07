package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.ReportRequestDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.DTO.RoomTypeDTO;
import com.ecrm.DTO.ScheduleDTO;
import com.ecrm.Entity.*;
import com.ecrm.Service.CheckDamageService;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.GCMService;
import com.ecrm.Service.RoomTypeService;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Enumerable.MessageType;
import com.ecrm.Utils.Enumerable.NotifyType;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.socket.SocketIO;
import com.twilio.sdk.TwilioRestException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Controller
@RequestMapping("/giang-vien")
public class UserController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    ReportDAOImpl reportDAO;
    @Autowired
    ReportDetailDAOImpl reportDetailDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO2;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;
    @Autowired
    NotificationDAOImp notificationDAOImp;
    @Autowired
    UserNotificationDAOImpl userNotificationDAO;
    @Autowired
    CheckDamageService checkDamageService;

    @Autowired
    GCMService gcmService;
    @Autowired
    RoomTypeService roomTypeService;

    @RequestMapping(value = "")
    public String homePage(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        //Check user login
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        //Get notifications
        List<TblUserNotificationEntity> listNotify = userNotificationDAO.getNotificationByUser(user.getUsername(), 1, 5);
        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);
        request.setAttribute("LISTNOTIFY", listNotify);

        //Get all schedule in day
        List<TblScheduleEntity> allSchedules = scheduleDAO.getSchedulesOfUser(user.getUsername());
        request.setAttribute("ALLSCHEDULE", allSchedules);

        //Get schedules are finish and going
        List<TblScheduleEntity> finishSchedules = scheduleDAO.getSchedulesFinishOfUser(user.getUsername());
        List<ScheduleDTO> listFinnishSchedule = new ArrayList<ScheduleDTO>();
        for (TblScheduleEntity schedule : finishSchedules) {
            listFinnishSchedule.add(new ScheduleDTO(schedule.getClassroomId(), schedule.getTblClassroomByClassroomId().getName(), null, null, null));
        }
        request.setAttribute("FINISHSCHEDULE", listFinnishSchedule);

        //Get history report
        int pageNumber = 0;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException ex) {
            pageNumber = 1;
        }

        int size = 5;
        int numberOfReport = reportDAO.getNumberOfUserReport(user.getUsername());
        int numberOfPage = numberOfReport/size + (numberOfReport%size>0?1:0);

        if(pageNumber > numberOfPage && pageNumber > 0) {
            return "Error";
        }

        if(numberOfReport == 0) {
            request.setAttribute("HISTORYREPORT", new ArrayList<ReportResponseObject>());
        } else {
            if(pageNumber == 0) {
                pageNumber = 1;
            }

            List<TblReportEntity> list = reportDAO.getPagingReportByUser(user.getUsername(), pageNumber, size);
            List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
            for (int i = 0; i < list.size(); i++) {
                ReportResponseObject report = new ReportResponseObject(list.get(i));
                report.setListEquipment(equipmentDAO.getDamagedEquipmentNames(report.getReportId()));

                listReport.add(report);
            }

            request.setAttribute("CURRENTPAGE", pageNumber);
            request.setAttribute("MAXPAGE", numberOfPage);
            request.setAttribute("HISTORYREPORT", listReport);
        }

        return "user/NewTemplate";
    }

    @RequestMapping(value = "phong-bao-cao")
    public String getRoom(HttpServletRequest request, @RequestParam("roomId") int roomId) {
        TblClassroomEntity classroom = classroomDAO.find(roomId);
        RoomTypeDTO roomTypeDTO = roomTypeService.getRoomTypeOfRoom(classroom);
        List<TblEquipmentEntity> listEquipment = equipmentDAO.getDamagedEquipments(roomId);

        request.setAttribute("ROOM", classroom);
        request.setAttribute("ROOMTYPE", roomTypeDTO);
        request.setAttribute("DAMAGEDEQUIPMENTS", listEquipment);
        return "user/ReportRoomNew";
    }

//    @RequestMapping(value = "thong-bao")
//    public String notifications(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("USER") == null) {
//            return "redirect:/";
//        }
//
//        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
//        int pageNumber = Integer.parseInt(page);
//        int size = 5;
//        int numberOfReport = reportDAO.getNumberOfUserReport(user.getUsername());
//        int numberOfPage = numberOfReport/size + (numberOfReport%size>0?1:0);
//
//        if(pageNumber > numberOfPage && pageNumber > 0) {
//            return "Error";
//        }
//
//        if(numberOfReport == 0) {
//            request.setAttribute("NOTIFICATIONS", new ArrayList<ReportResponseObject>());
//        } else {
//            if(pageNumber == 0) {
//                pageNumber = 1;
//            }
//
//            List<TblReportEntity> list = reportDAO.getPagingReportByUser(user.getUsername(), pageNumber, size);
//            List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
//            for (int i = 0; i < list.size(); i++) {
//                ReportResponseObject report = new ReportResponseObject(list.get(i));
//                report.setListEquipment(equipmentDAO.getDamagedEquipmentNames(report.getReportId()));
//
//                listReport.add(report);
//            }
//
//            request.setAttribute("PAGE", pageNumber);
//            request.setAttribute("MAX", numberOfPage);
//            request.setAttribute("NOTIFICATIONS", listReport);
//        }
//
//        List<TblScheduleEntity> schedules = scheduleDAO.getSchedulesFinishOfUser(user.getUsername());
//        if (schedules.size() > 0) {
//            TblClassroomEntity classroom = classroomDAO.find(schedules.get(0).getClassroomId());
//            TblRoomTypeEntity2 roomType = roomTypeDAO2.find(classroom.getRoomTypeId2());
//            List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(schedules.get(0).getClassroomId());
//
//            List<ScheduleDTO> listSchedule = new ArrayList<ScheduleDTO>();
//            for (TblScheduleEntity schedule : schedules) {
//                listSchedule.add(new ScheduleDTO(schedule.getClassroomId(), schedule.getTblClassroomByClassroomId().getName(), null, null, null));
//            }
//
//            request.setAttribute("LISTSCHEDULE", listSchedule);
//            request.setAttribute("ROOM", classroom);
//            request.setAttribute("EQUIPMENTS", listEquipment);
//            request.setAttribute("ROOMTYPE", roomType);
//        } else {
//            request.setAttribute("ROOM", new TblClassroomEntity());
//        }
//
//        request.setAttribute("ACTIVELEFTTAB", "USER_NOTIFY");
//        return "user/Notifications";
//    }

    @RequestMapping(value = "sentReport", method = RequestMethod.POST)
    @ResponseBody
    public String createReport(HttpServletRequest request, @RequestBody ReportRequestDTO reportRequest) {
        GCMController gcm = new GCMController();
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        TblClassroomEntity room = classroomDAO.find(reportRequest.getRoomId());

        //List damagedLevel
//        List<TblEquipmentEntity>listDamamagedEquipment1 =  room.getTblEquipmentsById();
//        Iterator<TblEquipmentEntity> iterator1 = listDamamagedEquipment1.iterator();
//        while (iterator1.hasNext()){
//            TblEquipmentEntity tblEquipmentEntity = iterator1.next();
//            if(tblEquipmentEntity.isStatus()){
//                iterator1.remove();
//            }
//        }

        TblReportEntity report = reportDAO.getReportOfUsernameInDay(user.getUsername(), reportRequest.getRoomId());
        if (report == null || report.getStatus() == ReportStatus.FINISH.getValue()) {
            report = new TblReportEntity(user.getUsername(), reportRequest.getRoomId(), reportRequest.getEvaluate());
            reportDAO.persist(report);
        }
//        else if (report.getStatus() == ReportStatus.FINISH.getValue()) {
//            report.setStatus(ReportStatus.GOING.getValue());
//            reportDAO.merge(report);
//        }

        String[] evaluates = reportRequest.getListEvaluate().split(",");
        int category = 0;
        if ("".equals(reportRequest.getListDamaged())) {
            for (int i = 0; i < evaluates.length; i++) {

                category = Integer.parseInt(evaluates[i].split("-")[0]);
                insertEquipment(report.getId(), reportRequest.getRoomId(), category, null, evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
            }
        } else {
            String[] equipments = reportRequest.getListDamaged().split("--");
            for (int i = 0; i < evaluates.length; i++) {
                category = Integer.parseInt(evaluates[i].split("-")[0]);
                List<String> equipsInCate = getEquipmentsInCategory(equipments, category);

                if (equipsInCate.size() == 0) {
                    insertEquipment(report.getId(), reportRequest.getRoomId(), category, null, evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
                } else {
                    for (int j = 0; j < equipsInCate.size(); j++) {
                        insertEquipment(report.getId(), reportRequest.getRoomId(), category, equipsInCate.get(j), evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
                    }
                }
            }
        }

        List<TblEquipmentEntity> listDamamagedEquipment = equipmentDAO.getDamagedEquipments(room.getId());

        int damagedLevel = checkDamageService.checkDamagedLevel(listDamamagedEquipment, room);
        room.setDamagedLevel(damagedLevel);
        classroomDAO.merge(room);

        String message = user.getTblUserInfoByUsername().getFullName() + " báo cáo hư hại phòng " + room.getName();
        TblNotificationEntity notify = notificationDAOImp.getNotifyOfRoom(reportRequest.getRoomId(), MessageType.NEWREPORT.getValue());
        boolean check = false;
        if (notify == null) {
            check = true;
            notify = new TblNotificationEntity(reportRequest.getRoomId(), message, "/bao-cao/hu-hai?phong=" + reportRequest.getRoomId(), MessageType.NEWREPORT.getValue());
            notificationDAOImp.persist(notify);
        }

        SocketIO socketIO = new SocketIO();
        List<TblUserEntity> staffs = userDAO.getAllStaff();
        try {
            for (TblUserEntity staff : staffs) {
                if(check) {
                    TblUserNotificationEntity userNotification = new TblUserNotificationEntity(staff.getUsername(), notify.getId(), false);
                    userNotificationDAO.persist(userNotification);
                    socketIO.sendNotifyMessageToUser(staff.getUsername(), NotifyType.STAFFNOTIFYREPORT.getValue(), message, "/bao-cao/notify?link=" + userNotification.getId());
                }

                SmsUtils.sendMessage(staff.getTblUserInfoByUsername().getPhone(), message);
                gcmService.sendNotification(message, staff.getTblUserInfoByUsername().getDeviceId());
            }
        } catch (TwilioRestException e) {
            System.out.println("Khong the gui SMS!");
        } catch (Exception e) {
            System.out.println("Khong the gui GMS!");
        }

        String equipmentNames = equipmentDAO.getDamagedEquipmentNames(report.getId());
        return report.getId() + "-" + room.getName() + "-" + equipmentNames + "-" + report.getCreateTime().getTime();
    }

    @RequestMapping(value = "doi-mat-khau", method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
        TblUserEntity user = userDAO.findUser(username);
        user.setPassword(password);
        userDAO.merge(user);

        return "true";
    }

//    @RequestMapping(value = "mau-phong")
//    public String getReportRoom(HttpServletRequest request, @RequestParam("RoomId") int roomId) {
//        TblClassroomEntity classroom = classroomDAO.find(roomId);
//        RoomTypeDTO roomTypeDTO = roomTypeService.getRoomTypeOfRoom(classroom);
//        List<TblEquipmentEntity> listEquipment = equipmentDAO.getDamagedEquipments(roomId);
//
//        request.setAttribute("ROOM", classroom);
//        request.setAttribute("ROOMTYPE", roomTypeDTO);
//        request.setAttribute("DAMAGEDEQUIPMENTS", listEquipment);
//        return "user/ReportRoom";
//    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportByUser(HttpServletRequest request, @RequestParam("bao-cao") int reportId) {
        TblReportEntity report = reportDAO.find(reportId);
        TblClassroomEntity classroom = report.getTblClassroomByClassRoomId();
        RoomTypeDTO roomTypeDTO = roomTypeService.getRoomTypeOfRoom(classroom);
        List<TblEquipmentEntity> listEquipment = equipmentDAO.getDamagedEquipmentsInReport(reportId);

        request.setAttribute("ROOM", classroom);
        request.setAttribute("REPORT", report);
        request.setAttribute("EQUIPMENTS", listEquipment);
        request.setAttribute("ROOMTYPE", roomTypeDTO);

        return "user/ReportHistory";
    }

    @RequestMapping(value = "lich-day")
    public String viewSchedule(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        List<TblScheduleEntity> list = scheduleDAO.getSchedulesOfUser(user.getUsername());

        request.setAttribute("SCHEDULE", list);
        request.setAttribute("ACTIVELEFTTAB", "USER_SCHEDULE");
        return "user/Schedule";
    }

    //  PRIVATE METHOD
    private List<String> getEquipmentsInCategory(String[] equipments, int category) {

        List<String> result = new ArrayList<String>();
        for (int i = 0; i < equipments.length; i++) {
            String[] equip = equipments[i].split("-");
            if (Integer.parseInt(equip[0]) == category) {
                result.add(equip[1]);
            }
        }

        return result;
    }

    private TblEquipmentEntity insertEquipment(int reportId, int roomId, int category, String pos, String evaluate, String description) {
        TblEquipmentCategoryEntity equipmentCategory = equipmentCategoryDAO.find(category);

        String position = null;
        if (!equipmentCategory.getName().equalsIgnoreCase("Bàn") || !equipmentCategory.getName().equalsIgnoreCase("Ghế")) {
            position = "[" + category + "]";
        } else {
            position = "[0]";
        }
        if (pos != null && "".equals(pos) == false) {
            position = pos;
        }

        String serialNumber = null;
        if (!equipmentCategory.getIsManaged()) {
            serialNumber = "";
        }

        TblEquipmentEntity equip = equipmentDAO.findEquipmentHavePosition(roomId, category, position, serialNumber);

        if (equip != null) {
            equip.setStatus(false);
            equipmentDAO.merge(equip);

            TblReportDetailEntity reportDetail = reportDetailDAO.getReportDetail(reportId, equip.getId());
            if (reportDetail != null) {
                reportDetail.setDescription(description);
                reportDetail.setDamagedLevel(evaluate);
                reportDetail.setStatus(false);
                reportDetailDAO.merge(reportDetail);
            } else if (reportDetail == null) {
                reportDetail = new TblReportDetailEntity(equip.getId(), reportId, evaluate, description, equip.getPosition());
                reportDetailDAO.persist(reportDetail);
            }
        } else {
            equip = new TblEquipmentEntity(category, roomId, null, null, position, null, false);
            equipmentDAO.persist(equip);

            TblReportDetailEntity reportDetail = new TblReportDetailEntity(equip.getId(), reportId, evaluate, description, equip.getPosition());
            reportDetailDAO.persist(reportDetail);
        }

        return equip;
    }


}
