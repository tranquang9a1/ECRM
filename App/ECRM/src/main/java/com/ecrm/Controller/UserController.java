package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.ReportRequestDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.DTO.ScheduleDTO;
import com.ecrm.Entity.*;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


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
    RoomTypeDAOImpl roomTypeDAO;
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

    @RequestMapping(value = "")
    public String homePage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        List<TblUserNotificationEntity> listNotify = userNotificationDAO.getNotificationByUser(user.getUsername());
        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());

        List<TblScheduleEntity> list = scheduleDAO.getSchedulesOfUser(user.getUsername());

        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);
        request.setAttribute("LISTNOTIFY", listNotify);
        request.setAttribute("SCHEDULE", list);
        return "user/NewTemplate";
    }

    @RequestMapping(value = "thong-bao")
    public String notifications(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        int pageNumber = Integer.parseInt(page);
        int size = 5;
        int numberOfReport = reportDAO.getNumberOfUserReport(user.getUsername());
        int numberOfPage = numberOfReport/size + (numberOfReport%size>0?1:0);

        if(pageNumber > numberOfPage && pageNumber > 0) {
            return "Error";
        }

        if(numberOfReport == 0) {
            request.setAttribute("NOTIFICATIONS", new ArrayList<ReportResponseObject>());
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

            request.setAttribute("PAGE", pageNumber);
            request.setAttribute("MAX", numberOfPage);
            request.setAttribute("NOTIFICATIONS", listReport);
        }

        List<TblScheduleEntity> schedules = scheduleDAO.getSchedulesFinishOfUser(user.getUsername());
        if (schedules.size() > 0) {
            TblClassroomEntity classroom = classroomDAO.find(schedules.get(0).getClassroomId());
            TblRoomTypeEntity roomType = roomTypeDAO.find(classroom.getRoomTypeId());
            List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(schedules.get(0).getClassroomId());

            List<ScheduleDTO> listSchedule = new ArrayList<ScheduleDTO>();
            for (TblScheduleEntity schedule : schedules) {
                listSchedule.add(new ScheduleDTO(schedule.getClassroomId(), schedule.getTblClassroomByClassroomId().getName(), null, null));
            }

            request.setAttribute("LISTSCHEDULE", listSchedule);
            request.setAttribute("ROOM", classroom);
            request.setAttribute("EQUIPMENTS", listEquipment);
            request.setAttribute("ROOMTYPE", roomType);
        } else {
            request.setAttribute("ROOM", new TblClassroomEntity());
        }

        request.setAttribute("ACTIVELEFTTAB", "USER_NOTIFY");
        return "user/Notifications";
    }

    @RequestMapping(value = "sentReport", method = RequestMethod.POST)
    @ResponseBody
    public String createReport(HttpServletRequest request, @RequestBody ReportRequestDTO reportRequest) {
        GCMController gcm = new GCMController();
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        TblClassroomEntity room = classroomDAO.find(reportRequest.getRoomId());

        //List damagedLevel
        List<TblEquipmentEntity>listDamamagedEquipment1 =  room.getTblEquipmentsById();
        Iterator<TblEquipmentEntity> iterator1 = listDamamagedEquipment1.iterator();
        while (iterator1.hasNext()){
            TblEquipmentEntity tblEquipmentEntity = iterator1.next();
            if(tblEquipmentEntity.isStatus()){
                iterator1.remove();
            }
        }

        TblReportEntity report = reportDAO.getReportOfUsernameInDay(user.getUsername(), reportRequest.getRoomId());
        if (report == null) {
            report = new TblReportEntity(user.getUsername(), reportRequest.getRoomId(), reportRequest.getEvaluate());
            reportDAO.persist(report);
        } else if (report.getStatus() == ReportStatus.FINISH.getValue()) {
            report.setStatus(ReportStatus.GOING.getValue());
            reportDAO.merge(report);
        }

        String[] evaluates = reportRequest.getListEvaluate().split(",");
        int category = 0;
        if ("".equals(reportRequest.getListDamaged())) {
            for (int i = 0; i < evaluates.length; i++) {

                category = Integer.parseInt(evaluates[i].split("-")[0]);
                TblEquipmentEntity tblEquipmentEntity = insertEquipment(report.getId(), reportRequest.getRoomId(), category, null, evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
            }
        } else {
            String[] equipments = reportRequest.getListDamaged().split("--");
            for (int i = 0; i < evaluates.length; i++) {
                category = Integer.parseInt(evaluates[i].split("-")[0]);
                List<String> equipsInCate = getEquipmentsInCategory(equipments, category);

                if (equipsInCate.size() == 0) {
                    TblEquipmentEntity tblEquipmentEntity = insertEquipment(report.getId(), reportRequest.getRoomId(), category, null, evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
                } else {
                    for (int j = 0; j < equipsInCate.size(); j++) {
                        TblEquipmentEntity tblEquipmentEntity = insertEquipment(report.getId(), reportRequest.getRoomId(), category, equipsInCate.get(j), evaluates[i].split("-")[1], reportRequest.getListDesc().get(i));
                    }
                }
            }
        }

        List<TblEquipmentEntity> listDamamagedEquipment = equipmentDAO.getDamagedEquipments(room.getId());

        int damagedLevel = checkDamagedLevel(listDamamagedEquipment,room);
        room.setDamagedLevel(damagedLevel);
        classroomDAO.merge(room);

        String message = user.getTblUserInfoByUsername().getFullName() + " báo cáo hư hại phòng " + room.getName();
        TblNotificationEntity notify = notificationDAOImp.getNotifyOfRoom(reportRequest.getRoomId(), MessageType.NEWREPORT.getValue());
        if (notify == null) {
            notify = new TblNotificationEntity(reportRequest.getRoomId(), message, "/thong-bao/hu-hai?phong=" + reportRequest.getRoomId(), MessageType.NEWREPORT.getValue());
            notificationDAOImp.persist(notify);
        }

        SocketIO socketIO = new SocketIO();
        List<TblUserEntity> staffs = userDAO.getAllStaff();
        try {
            for (TblUserEntity staff : staffs) {
                TblUserNotificationEntity userNotification = new TblUserNotificationEntity(staff.getUsername(), notify.getId(), false);
                userNotificationDAO.persist(userNotification);

                socketIO.sendNotifyMessageToUser(staff.getUsername(), NotifyType.STAFFNOTIFYREPORT.getValue(), message, "/thong-bao/notify?link="+ userNotification.getId());
                SmsUtils.sendMessage(staff.getTblUserInfoByUsername().getPhone(), message);
                gcm.sendNotification(message, staff.getTblUserInfoByUsername().getDeviceId());
            }
        } catch (TwilioRestException e) {
            System.out.println("Khong the gui SMS!");
        } catch (Exception e) {
            System.out.println("Khong the gui GMS!");
        }

        String equipmentNames = equipmentDAO.getDamagedEquipmentNames(report.getId());
        return report.getId() + "-" + room.getName() + "-" + equipmentNames + "-" + report.getCreateTime().getTime();
    }

    @RequestMapping(value = "mau-phong")
    public String getReportRoom(HttpServletRequest request, @RequestParam("RoomId") int roomId) {
        TblClassroomEntity classroom = classroomDAO.find(roomId);
        TblRoomTypeEntity roomType = roomTypeDAO.find(classroom.getRoomTypeId());
        List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(roomId);

        request.setAttribute("ROOM", classroom);
        request.setAttribute("EQUIPMENTS", listEquipment);
        request.setAttribute("ROOMTYPE", roomType);
        return "user/ReportRoom";
    }

    @RequestMapping(value = "viewHistory")
    public String viewReportByUser(HttpServletRequest request, @RequestParam("ReportId") int reportId, @RequestParam("RoomId") String roomId) {
        TblClassroomEntity classroom = classroomDAO.getClassroomByName(roomId);
        List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(classroom.getId());
        TblReportEntity report = reportDAO.find(reportId);

        request.setAttribute("ROOM", classroom);
        request.setAttribute("REPORT", report);
        request.setAttribute("EQUIPMENTS", listEquipment);
        request.setAttribute("ROOMTYPE", classroom.getTblRoomTypeByRoomTypeId());

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
        String position = null;
        if (category < 7) {
            position = "[" + category + "]";
        } else {
            position = "[0]";
        }

        if (pos != null && "".equals(pos) == false) {
            position = pos;
        }

        String serialNumber = "";
        if (category < 4) {
            serialNumber = null;
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

    //Check damaged level

    public int checkDamagedLevel(List<TblEquipmentEntity> damagedEquipment, TblClassroomEntity tblClassroomEntity) {
        int damagedLevel = 0;
        int projectorDamagedLevel = 0;
        int mayLanhDamagedLevel = 0;
        int tiviDamagedLevel = 0;
        int quatDamagedLevel = 0;
        int loaDamagedLevel = 0;
        int denDamagedLevel = 0;
        int banDamagedLevel = 0;
        int gheDamagedLevel = 0;
        int MayLanh = 0;
        int Quat = 0;

        TblRoomTypeEntity roomTypeEntity = tblClassroomEntity.getTblRoomTypeByRoomTypeId();
        int chair = roomTypeEntity.getSlots();
        String[] row = roomTypeEntity.getHorizontalRows().split("-");
        int table = 0;
        for (int i = 0; i < row.length; i++) {
            table += Integer.parseInt(row[i]);
        }
        if (roomTypeEntity.getAirConditioning() > 0) {
            MayLanh = roomTypeEntity.getAirConditioning();
        }
        if (roomTypeEntity.getFan() > 0) {
            Quat = roomTypeEntity.getFan();
        }

        if (!damagedEquipment.isEmpty()) {
            for (TblEquipmentEntity tblEquipmentEntity : damagedEquipment) {
                if (tblEquipmentEntity.getCategoryId() == 1) {
                    List<TblReportDetailEntity> projectors = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity project : projectors) {
                        if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                            projectorDamagedLevel = 20;
                        } else if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                            projectorDamagedLevel = 30;
                        } else if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                            projectorDamagedLevel = 50;
                        } else {
                            projectorDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 2) {
                    List<TblReportDetailEntity> tivis = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity tivi : tivis) {
                        if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                            tiviDamagedLevel = 20;
                        } else if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                            tiviDamagedLevel = 30;
                        } else if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                            tiviDamagedLevel = 50;
                        } else {
                            tiviDamagedLevel = 20;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 3) {
                    List<TblReportDetailEntity> mayLanhs = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity mayLanh : mayLanhs) {
                        if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                            mayLanhDamagedLevel += 10;
                        } else if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                            mayLanhDamagedLevel += 15;
                        } else if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                            mayLanhDamagedLevel += 25;
                        } else {
                            mayLanhDamagedLevel += 25;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 4) {
                    List<TblReportDetailEntity> quats = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    if (MayLanh == 0) {
                        if ((quats.size() / Quat) * 100 >= 50) {
                            quatDamagedLevel = 50;
                        }
                    } else {
                        for (TblReportDetailEntity quat : quats) {
                            if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                quatDamagedLevel += 1;
                            } else if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                quatDamagedLevel += 3;
                            } else if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                quatDamagedLevel += 5;
                            } else {
                                quatDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 5) {
                    List<TblReportDetailEntity> loas = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity loa : loas) {
                        if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                            loaDamagedLevel = 1;
                        } else if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                            loaDamagedLevel = 3;
                        } else if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                            loaDamagedLevel = 5;
                        } else {
                            loaDamagedLevel = 5;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 6) {
                    List<TblReportDetailEntity> dens = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity den : dens) {
                        if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                            denDamagedLevel = 10;
                        } else if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                            denDamagedLevel = 20;
                        } else if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                            denDamagedLevel = 50;
                        } else {
                            denDamagedLevel = 10;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 7) {
                    List<TblReportDetailEntity> bans = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    if ((bans.size() / table) / 100 >= 50) {
                        banDamagedLevel = 50;
                    } else {
                        for (TblReportDetailEntity ban : bans) {
                            if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                banDamagedLevel += 2;
                            } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                banDamagedLevel += 3;
                            } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                banDamagedLevel += 5;
                            } else {
                                banDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 8) {
                    List<TblReportDetailEntity> ghes = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    if ((ghes.size() / chair) / 100 >= 50) {
                        gheDamagedLevel = 50;
                    } else {
                        for (TblReportDetailEntity ghe : ghes) {
                            if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
                                gheDamagedLevel += 1;
                            } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
                                gheDamagedLevel += 2;
                            } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
                                gheDamagedLevel += 3;
                            } else {
                                gheDamagedLevel += 3;
                            }
                        }
                    }
                }
            }
        }
        damagedLevel = projectorDamagedLevel + mayLanhDamagedLevel + tiviDamagedLevel + loaDamagedLevel + quatDamagedLevel + denDamagedLevel
                + banDamagedLevel + gheDamagedLevel;
        if (damagedLevel > 100) {
            damagedLevel = 100;
        }
        return damagedLevel;
    }

}
