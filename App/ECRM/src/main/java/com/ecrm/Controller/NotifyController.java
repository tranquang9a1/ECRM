package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.Entity.*;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.GroupUser;
import com.ecrm.Service.NotificationService;
import com.ecrm.Service.ReportService;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Enumerable.MessageType;
import com.ecrm.Utils.Enumerable.NotifyType;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */
@Controller
@RequestMapping("/bao-cao")
public class NotifyController {
    @Autowired
    private EquipmentDAOImpl equipmentDAO;
    @Autowired
    private UserNotificationDAOImpl userNotificationDAO;

    @Autowired
    private ReportService reportService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        if(!reportService.getListReport(request, page)) {
            return "Error";
        }

        return "staff/ListDamagedRoom";
    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportDetail(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        DamagedRoomDTO resultObject = reportService.getReportDetail(roomId);
        if(resultObject == null) {
            return "redirect:/bao-cao";
        }

        request.setAttribute("DAMAGEDROOM", resultObject);
        return "staff/RoomDetail";
    }

    @RequestMapping(value = "hu-hai")
    public String showReportDetail(HttpServletRequest request, @RequestParam(value = "phong") int roomId) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        List<TblEquipmentEntity> equipments = equipmentDAO.getDamagedEquipments(roomId);
        if (equipments.size() > 0) {
            reportService.getListReport(request, null);
            request.setAttribute("SHOWDETAIL", roomId);

            return "staff/ListDamagedRoom";
        }

        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "sua-chua")
    @Transactional
    public String resolveReport(HttpServletRequest request, @RequestParam(value = "RoomId") int roomId,
                                @RequestParam(value = "ListResolve") String listResolve, @RequestParam(value = "ListRealEquip") String listRealEquip) {
        reportService.resolveRoom(roomId, listResolve, listRealEquip);
        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "sua-het")
    @Transactional
    public String resolveAllReportInRoom(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        reportService.resolveAllEquipment(roomId);
        return "redirect:/bao-cao";
    }

    @RequestMapping(value = "doi-phong")
    @ResponseBody
    public String changeRoom(HttpServletRequest request, @RequestParam("currentClassroom") String currentRoom, @RequestParam("changeClassroom") String changeRoom) throws TwilioRestException {
        return classroomService.changeRoom(currentRoom, changeRoom);
    }

    @RequestMapping(value = "thong-bao")
    public String getListNotification(HttpServletRequest request, @RequestParam(value = "little") boolean isLittle,
                                      @RequestParam(value = "trang", defaultValue = "0", required = false) String page,
                                      @RequestParam(value = "quay-lai", required = false, defaultValue = "") String backLink) {
        return notificationService.getListNotifies(request, page, isLittle, backLink);
    }

    @RequestMapping(value = "danh-muc")
    public String getLeftMenu(HttpServletRequest request){
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");

        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);

        return "LeftCategory";
    }

    @RequestMapping(value = "notify")
    public String redirectNotify(HttpServletRequest request, @RequestParam(value = "link") int notifyId){
        return notificationService.redirectNotify(request, notifyId);
    }

//  PRIVATE METHOD
//    private void changeNotificationStatus(int roomId) {
//        List<TblNotificationEntity> notifies = notificationDAO.getActiveNotifyOfRoom(roomId, MessageType.NEWREPORT.getValue());
//        for (TblNotificationEntity notify: notifies) {
//            if(!notify.isStatus()) {
//                notify.setStatus(true);
//                notificationDAO.merge(notify);
//            }
//
//            List<TblUserNotificationEntity> listUserNotifies = notify.getTblUserNotificationById();
//            for (TblUserNotificationEntity userNotify: listUserNotifies) {
//                if(!userNotify.isStatus()) {
//                    userNotify.setStatus(true);
//                    userNotificationDAO.merge(userNotify);
//                }
//            }
//        }
//    }
//
//    private boolean resolve(int room, int category, String[] listEquipment) {
//        //Change Equipment status and ReportDetail status
//        List<TblEquipmentEntity> equips = equipmentDAO.getDamagedEquipmentsByCategory(room, category);
//        for (TblEquipmentEntity equip : equips) {
//            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getUnresolveReportDetail(equip.getId());
//            List<TblEquipmentEntity> listEquips = getListRealEquipment(listEquipment, category + "", room);
//
//            if (listEquips.size() > 0) {
//                for (TblEquipmentEntity item : listEquips) {
//                    for (TblReportDetailEntity detailItem : reportDetails) {
//                        TblReportDetailEntity reportDetail = new TblReportDetailEntity(item.getId(), detailItem.getReportId(), detailItem.getDamagedLevel(),
//                                detailItem.getDescription(), item.getPosition(), true);
//                        reportDetail.setResolveTime(new Timestamp(new Date().getTime()));
//                        reportDetailDAO.persist(reportDetail);
//                    }
//                }
//            }
//
//            for (int j = 0; j < reportDetails.size(); j++) {
//                reportDetails.get(j).setStatus(true);
//                reportDetails.get(j).setResolveTime(new Timestamp(new Date().getTime()));
//                reportDetailDAO.merge(reportDetails.get(j));
//            }
//            equip.setStatus(true);
//            equipmentDAO.merge(equip);
//        }
//
//        return true;
//    }
//
//    private List<TblEquipmentEntity> getListRealEquipment(String[] list, String category, int roomId) {
//        List<TblEquipmentEntity> result = new ArrayList<TblEquipmentEntity>();
//        String[] equipment;
//
//        for (int i = 0; i < list.length; i++) {
//            equipment = list[i].split("-");
//            if (equipment.length > 0 && category.equals(equipment[0])) {
//                TblEquipmentEntity equip = equipmentDAO.getEquipmentBySerialNumber(roomId, equipment[1]);
//                if (equip != null) {
//                    result.add(equip);
//                }
//            }
//        }
//
//        return result;
//    }
//
//    public int checkDamagedLevel(TblClassroomEntity classroomEntity) {
//        int damagedLevel = 0;
//        int projectorDamagedLevel = 0;
//        int mayLanhDamagedLevel = 0;
//        int tiviDamagedLevel = 0;
//        int quatDamagedLevel = 0;
//        int loaDamagedLevel = 0;
//        int denDamagedLevel = 0;
//        int banDamagedLevel = 0;
//        int gheDamagedLevel = 0;
//        int MayLanh = 0;
//        int Quat = 0;
//
//        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
//        int chair = roomTypeEntity.getSlots();
//        String[] row = roomTypeEntity.getHorizontalRows().split("-");
//        int table = 0;
//        for (int i = 0; i < row.length; i++) {
//            table += Integer.parseInt(row[i]);
//        }
//        if (roomTypeEntity.getAirConditioning() > 0) {
//            MayLanh = roomTypeEntity.getAirConditioning();
//        }
//        if (roomTypeEntity.getFan() > 0) {
//            Quat = roomTypeEntity.getFan();
//        }
//        Collection<TblEquipmentEntity> damagedEquipment = new ArrayList<TblEquipmentEntity>();
//        Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
//        for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
//            if (!tblEquipmentEntity.isStatus()) {
//                damagedEquipment.add(tblEquipmentEntity);
//            }
//        }
//        if (!damagedEquipment.isEmpty()) {
//            for (TblEquipmentEntity tblEquipmentEntity : damagedEquipment) {
//                if (tblEquipmentEntity.getCategoryId() == 1) {
//                    List<TblReportDetailEntity> projectors = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    for (TblReportDetailEntity project : projectors) {
//                        if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                            projectorDamagedLevel = 20;
//                        } else if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                            projectorDamagedLevel = 30;
//                        } else if (project.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                            projectorDamagedLevel = 50;
//                        } else {
//                            projectorDamagedLevel = 50;
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 2) {
//                    List<TblReportDetailEntity> tivis = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    for (TblReportDetailEntity tivi : tivis) {
//                        if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                            tiviDamagedLevel = 20;
//                        } else if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                            tiviDamagedLevel = 30;
//                        } else if (tivi.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                            tiviDamagedLevel = 50;
//                        } else {
//                            tiviDamagedLevel = 20;
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 3) {
//                    List<TblReportDetailEntity> mayLanhs = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    for (TblReportDetailEntity mayLanh : mayLanhs) {
//                        if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                            mayLanhDamagedLevel += 10;
//                        } else if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                            mayLanhDamagedLevel += 15;
//                        } else if (mayLanh.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                            mayLanhDamagedLevel += 25;
//                        } else {
//                            mayLanhDamagedLevel += 25;
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 4) {
//                    List<TblReportDetailEntity> quats = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    if (MayLanh == 0) {
//                        if ((quats.size() / Quat) * 100 >= 50) {
//                            quatDamagedLevel = 50;
//                        }
//                    } else {
//                        for (TblReportDetailEntity quat : quats) {
//                            if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                                quatDamagedLevel += 1;
//                            } else if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                                quatDamagedLevel += 3;
//                            } else if (quat.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                                quatDamagedLevel += 5;
//                            } else {
//                                quatDamagedLevel += 5;
//                            }
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 5) {
//                    List<TblReportDetailEntity> loas = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    for (TblReportDetailEntity loa : loas) {
//                        if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                            loaDamagedLevel = 1;
//                        } else if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                            loaDamagedLevel = 3;
//                        } else if (loa.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                            loaDamagedLevel = 5;
//                        } else {
//                            loaDamagedLevel = 5;
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 6) {
//                    List<TblReportDetailEntity> dens = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    for (TblReportDetailEntity den : dens) {
//                        if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                            denDamagedLevel = 10;
//                        } else if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                            denDamagedLevel = 20;
//                        } else if (den.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                            denDamagedLevel = 50;
//                        } else {
//                            denDamagedLevel = 10;
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 7) {
//                    List<TblReportDetailEntity> bans = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    if ((bans.size() / table) / 100 >= 50) {
//                        banDamagedLevel = 50;
//                    } else {
//                        for (TblReportDetailEntity ban : bans) {
//                            if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                                banDamagedLevel += 2;
//                            } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                                banDamagedLevel += 3;
//                            } else if (ban.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                                banDamagedLevel += 5;
//                            } else {
//                                banDamagedLevel += 5;
//                            }
//                        }
//                    }
//                }
//                if (tblEquipmentEntity.getCategoryId() == 8) {
//                    List<TblReportDetailEntity> ghes = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
//                    if ((ghes.size() / chair) / 100 >= 50) {
//                        gheDamagedLevel = 50;
//                    } else {
//                        for (TblReportDetailEntity ghe : ghes) {
//                            if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.LOW.getValue())) {
//                                gheDamagedLevel += 1;
//                            } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.MEDIUM.getValue())) {
//                                gheDamagedLevel += 2;
//                            } else if (ghe.getDamagedLevel().equals(Enumerable.DamagedLevel.HIGH.getValue())) {
//                                gheDamagedLevel += 3;
//                            } else {
//                                gheDamagedLevel += 3;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        damagedLevel = projectorDamagedLevel + mayLanhDamagedLevel + tiviDamagedLevel + loaDamagedLevel + quatDamagedLevel + denDamagedLevel
//                + banDamagedLevel + gheDamagedLevel;
//        if (damagedLevel > 100) {
//            damagedLevel = 100;
//        }
//        return damagedLevel;
//      }
//
//    private List<String> getAvailableRoom(int classroomId) {
//        List<String> availableClassroom = new ArrayList<String>();
//        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
//        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
//        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
//            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
//            if (!classroom.isEmpty()) {
//                if (availableClassroom.isEmpty()) {
//                    availableClassroom = classroom;
//                } else {
//                    Iterator<String> it = availableClassroom.iterator();
//                    while (it.hasNext()) {
//                        String room = it.next();
//                        if (!classroom.contains(room)) {
//                            it.remove();
//                        }
//                    }
//                }
//            }
//        }
//        if(!availableClassroom.isEmpty()){
//            TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
//
//            availableClassroom = Utils.sortClassroom(availableClassroom, classroomEntity.getName());
//            availableClassroom.remove(classroomEntity.getName());
//        }
//        return availableClassroom;
//    }
//
//    private boolean getListReport(HttpServletRequest request, String page) {
//        List<Integer> rooms = reportDAO.getDamagedRoom();
//        List<GroupReportsDTO> groups = new ArrayList<GroupReportsDTO>();
//        String equipmentNames = "";
//
//        for (int i = 0; i < rooms.size(); i++) {
//            TblClassroomEntity room = classroomDAO.find(rooms.get(i));
//            GroupReportsDTO group = new GroupReportsDTO();
//            group.setRoomId(room.getId());
//            group.setRoomName(room.getName());
//
//            equipmentNames = equipmentCategoryDAO.getCategoriesInRoom(room.getId());
//            group.setListEquipments(equipmentNames.substring(0, equipmentNames.length() - 2));
//            equipmentNames = "";
//            group.setReporters(reportDAO.getReportersInRoom(room.getId()));
//
//            groups.add(group);
//        }
//
//        int pageNumber = 0;
//        int size = 5;
//        int numberOfReport = reportDAO.getNumberOfFinishReport();
//        int numberOfPage = numberOfReport/size + (numberOfReport%size>0?1:0);
//        if(page != null && !page.equals("0")) {
//            pageNumber = Integer.parseInt(page);
//            request.setAttribute("ACTIVETAB", "tab2");
//
//            if(pageNumber > numberOfPage) {
//                return false;
//            }
//        }
//
//        if(pageNumber <= 0) {
//            pageNumber = 1;
//        }
//
//        List<TblReportEntity> finishReport = reportDAO.getFinishReport(size, (pageNumber-1)*size);
//        List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
//        for (int i = 0; i < finishReport.size(); i++) {
//            ReportResponseObject report = new ReportResponseObject(finishReport.get(i));
//            report.setReporter(finishReport.get(i).getTblUserByUserId().getTblUserInfoByUsername().getFullName());
//            report.setListEquipment(equipmentDAO.getDamagedEquipmentNames(report.getReportId()));
//
//            listReport.add(report);
//        }
//
//        request.setAttribute("PAGE", pageNumber);
//        request.setAttribute("SIZE", numberOfPage);
//        request.setAttribute("FINISHREPORT", listReport);
//        request.setAttribute("NEWREPORT", groups);
//
//        HttpSession session = request.getSession();
//        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
//        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
//        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);
//
//        request.setAttribute("TABCONTROL", "STAFF_REPORT");
//
//        return true;
//    }
}
