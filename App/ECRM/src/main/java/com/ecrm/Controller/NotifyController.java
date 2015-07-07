package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.Entity.*;
import com.ecrm.Service.GroupUser;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Enumerable.MessageType;
import com.ecrm.Utils.Enumerable.NotifyType;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import com.twilio.sdk.TwilioRestException;
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
@RequestMapping("/thong-bao")
public class NotifyController {
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    ReportDAOImpl reportDAO;
    @Autowired
    ReportDetailDAOImpl reportDetailDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    RoomTypeDAOImpl roomTypeDAO;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;
    @Autowired
    NotificationDAOImp notificationDAOImp;

    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request, @RequestParam(value = "trang", defaultValue = "0", required = false) String page) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        if(!getListReport(request, page)) {
            return "Error";
        }

        return "staff/Notifications";
    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportDetail(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        TblClassroomEntity classroom = classroomDAO.find(roomId);
        List<TblEquipmentEntity> equipments = equipmentDAO.getEquipmentsInClassroom(classroom.getId());
        List<TblScheduleEntity> schedules = scheduleDAO.getScheduleNoFinishOfRoom(roomId);

        DamagedRoomDTO resultObject = new DamagedRoomDTO(classroom, reportDAO.getReportNewest(roomId), equipments);
        resultObject.setReporters(reportDAO.getReportersInRoom(roomId));
        resultObject.setRoomtype(classroom.getTblRoomTypeByRoomTypeId());
        resultObject.setDamagedLevel(classroom.getDamagedLevel());
        if (schedules.size() > 0) {
            resultObject.setSuggestRooms(getAvailableRoom(roomId));
        } else {
            resultObject.setFree(true);
        }
        request.setAttribute("DAMAGEDROOM", resultObject);
        return "staff/ReportDetail";
    }

    @RequestMapping(value = "hu-hai")
    public String showReportDetail(HttpServletRequest request, @RequestParam(value = "phong") int roomId) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER") == null) {
            return "redirect:/";
        }

        List<TblEquipmentEntity> equipments = equipmentDAO.getDamagedEquipments(roomId);
        if (equipments.size() > 0) {
            getListReport(request, null);
            request.setAttribute("SHOWDETAIL", roomId);
            return "staff/Notifications";
        }

        return "redirect:/thong-bao";
    }

    @RequestMapping(value = "sua-chua")
    @Transactional
    public String resolveReport(HttpServletRequest request, @RequestParam(value = "RoomId") int roomId,
                                @RequestParam(value = "ListResolve") String listResolve, @RequestParam(value = "ListRealEquip") String listRealEquip) {
        String[] categories = listResolve.split(",");
        String[] listEquipment = listRealEquip.split(",");
        for (int i = 0; i < categories.length; i++) {
            resolve(roomId, Integer.parseInt(categories[i]), listEquipment);
        }

        //Change Report status
        List<TblReportEntity> reports = reportDAO.getLiveReportsInRoom(roomId);
        int flag = 0;
        for (TblReportEntity report : reports) {
            List<TblReportDetailEntity> details = report.getTblReportDetailsById();
            for (int i = 0; i < details.size(); i++) {
                if (!details.get(i).isStatus()) {
                    flag++;
                }
            }

            if (flag == details.size()) {
                report.setStatus(ReportStatus.FINISH.getValue());
                reportDAO.merge(report);
            } else if (flag > 0) {
                report.setStatus(ReportStatus.GOING.getValue());
                reportDAO.merge(report);
            }
            flag = 0;
        }

        TblClassroomEntity room = classroomDAO.find(roomId);
        room.setDamagedLevel(checkDamagedLevel(room));
        classroomDAO.merge(room);

        return "redirect:/thong-bao";
    }

    @RequestMapping(value = "sua-het")
    @Transactional
    public String resolveAllReportInRoom(HttpServletRequest request, @RequestParam(value = "roomId") int roomId) {
        TblClassroomEntity room = classroomDAO.find(roomId);
        Collection<TblEquipmentEntity> equips = room.getTblEquipmentsById();
        for (TblEquipmentEntity equip : equips) {
            if (!equip.isStatus()) {
                equip.setStatus(true);
                equipmentDAO.merge(equip);
            }
        }

        List<TblReportEntity> reports = reportDAO.getLiveReportsInRoom(roomId);
        for (TblReportEntity report : reports) {
            List<TblReportDetailEntity> details = report.getTblReportDetailsById();
            for (TblReportDetailEntity detail : details) {
                if (!detail.isStatus()) {
                    detail.setStatus(true);
                    reportDetailDAO.merge(detail);
                }
            }

            report.setStatus(ReportStatus.FINISH.getValue());
            reportDAO.merge(report);
        }

        TblNotificationEntity notify = notificationDAOImp.getNotifyOfRoom(roomId, MessageType.NEWREPORT.getValue());
        if (notify != null) {
            notify.setStatus(false);
            notificationDAOImp.merge(notify);
        }

        room.setDamagedLevel(0);
        classroomDAO.merge(room);
        return "redirect:/thong-bao";
    }

    @RequestMapping(value = "doi-phong")
    @ResponseBody
    public String changeRoom(HttpServletRequest request, @RequestParam("currentClassroom") String currentRoom, @RequestParam("changeClassroom") String changeRoom) throws TwilioRestException {
        TblClassroomEntity currentClassroom = classroomDAO.getClassroomByName(currentRoom);
        TblClassroomEntity changeClassroom = classroomDAO.getClassroomByName(changeRoom);

        List<TblScheduleEntity> currentSchedule = scheduleDAO.getScheduleNoFinishOfRoom(currentClassroom.getId());

        List<GroupUser> groupUsers = new ArrayList<GroupUser>();
        for (TblScheduleEntity schedule : currentSchedule) {
            GroupUser group = GroupUser.checkContainIn(groupUsers, schedule.getUsername());
            if (group == null) {
                group = new GroupUser(schedule.getUsername(), schedule.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), currentRoom, changeRoom);
                groupUsers.add(group);
            }
            List<String> listTime = group.getListTime();
            listTime.add(schedule.getTimeFrom().getHours() + "h" + schedule.getTimeFrom().getMinutes());

            schedule.setIsActive(false);
            scheduleDAO.merge(schedule);

            TblScheduleEntity newSchedule = new TblScheduleEntity(schedule.getUsername(), changeClassroom.getId(),
                    schedule.getNumberOfStudents(), "Thay đổi phòng từ phòng " + schedule.getTblClassroomByClassroomId().getName()
                    + " sang phòng " + changeClassroom.getName(), schedule.getTimeFrom(),
                    schedule.getSlots(), schedule.getDate(), true, schedule.getScheduleConfigId());
            scheduleDAO.persist(newSchedule);
        }

        String message = "Đổi phòng " + currentRoom + " sang phòng " + changeRoom;
        TblNotificationEntity notify = notificationDAOImp.getNotifyOfRoom(currentClassroom.getId(), MessageType.CHANGEROOM.getValue());
        if (notify == null) {
            notify = new TblNotificationEntity(currentClassroom.getId(), message, "/giang-vien/lich-day", MessageType.CHANGEROOM.getValue());
            notificationDAOImp.persist(notify);
        }

        SocketIO socket = new SocketIO();
        for (GroupUser user : groupUsers) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currentRoom", currentRoom);
            jsonObject.put("currentRoomId", currentClassroom.getId());
            jsonObject.put("changeRoom", changeRoom);
            jsonObject.put("changeRoomId", changeClassroom.getId());
            jsonObject.put("listTime", user.getListTime());

            boolean check = socket.sendNotifyObjectToStaff(user.getUsername(), NotifyType.TEACHERCHANGEROOM.getValue(), jsonObject);
            SmsUtils.sendMessage(user.getPhone(), user.toString());

        }
//update status report
        List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
        for (TblReportEntity tblReportEntity : tblReportEntities) {
            tblReportEntity.setChangedRoom(changeRoom);
            tblReportEntity.setStatus(2);
            reportDAO.merge(tblReportEntity);
        }
        return "Đổi phòng thành công!";
    }

    @RequestMapping(value = "all-notify")
    public String getAllNotify(HttpServletRequest request) {
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        String role = user.getTblRoleByRoleId().getName();
        List<TblNotificationEntity> listNotify;
        if (role.equals("Teacher")) {
            listNotify = notificationDAOImp.getAllNotifyOfUser(user.getUsername(), MessageType.CHANGEROOM.getValue());
        } else {
            listNotify = notificationDAOImp.getAllNotifyOfStaff();
        }

        request.setAttribute("LISTNOTIFY", listNotify);

        return "ListNotification";
    }

    private boolean resolve(int room, int category, String[] listEquipment) {
        //Change Equipment status and ReportDetail status
        List<TblEquipmentEntity> equips = equipmentDAO.getDamagedEquipmentsByCategory(room, category);
        for (TblEquipmentEntity equip : equips) {
            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getUnresolveReportDetail(equip.getId());
            List<TblEquipmentEntity> listEquips = getListRealEquipment(listEquipment, category + "", room);

            if (listEquips.size() > 0) {
                for (TblEquipmentEntity item : listEquips) {
                    for (TblReportDetailEntity detailItem : reportDetails) {
                        TblReportDetailEntity reportDetail = new TblReportDetailEntity(item.getId(), detailItem.getReportId(), detailItem.getDamagedLevel(),
                                detailItem.getDescription(), item.getPosition(), true);
                        reportDetail.setResolveTime(new Timestamp(new Date().getTime()));
                        reportDetailDAO.persist(reportDetail);
                    }
                }
            }

            for (int j = 0; j < reportDetails.size(); j++) {
                reportDetails.get(j).setStatus(true);
                reportDetails.get(j).setResolveTime(new Timestamp(new Date().getTime()));
                reportDetailDAO.merge(reportDetails.get(j));
            }
            equip.setStatus(true);
            equipmentDAO.merge(equip);
        }

        return true;
    }

    private List<TblEquipmentEntity> getListRealEquipment(String[] list, String category, int roomId) {
        List<TblEquipmentEntity> result = new ArrayList<TblEquipmentEntity>();
        String[] equipment;

        for (int i = 0; i < list.length; i++) {
            equipment = list[i].split("-");
            if (equipment.length > 0 && category.equals(equipment[0])) {
                TblEquipmentEntity equip = equipmentDAO.getEquipmentBySerialNumber(roomId, equipment[1]);
                if (equip != null) {
                    result.add(equip);
                }
            }
        }

        return result;
    }

    public int checkDamagedLevel(TblClassroomEntity classroomEntity) {
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

        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
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
        Collection<TblEquipmentEntity> damagedEquipment = new ArrayList<TblEquipmentEntity>();
        Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
        for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
            if (!tblEquipmentEntity.isStatus()) {
                damagedEquipment.add(tblEquipmentEntity);
            }
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

    private List<String> getAvailableRoom(int classroomId) {
        List<String> availableClassroom = new ArrayList<String>();
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findAllScheduleInClassroom(classroomEntity.getId());
            if (tblScheduleEntities.isEmpty()) {
                availableClassroom.add(classroomEntity.getName());
            }
        }

        if (!availableClassroom.isEmpty()) {
            return availableClassroom;
        } else {
            List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
            for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
                List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
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
            return availableClassroom;
        }
    }

    private boolean getListReport(HttpServletRequest request, String page) {
        List<Integer> rooms = reportDAO.getDamagedRoom();
        List<GroupReportsDTO> groups = new ArrayList<GroupReportsDTO>();
        String equipmentNames = "";

        for (int i = 0; i < rooms.size(); i++) {
            TblClassroomEntity room = classroomDAO.find(rooms.get(i));
            GroupReportsDTO group = new GroupReportsDTO();
            group.setRoomId(room.getId());
            group.setRoomName(room.getName());

            equipmentNames = equipmentCategoryDAO.getCategoriesInRoom(room.getId());
            group.setListEquipments(equipmentNames.substring(0, equipmentNames.length() - 2));
            equipmentNames = "";
            group.setReporters(reportDAO.getReportersInRoom(room.getId()));

            groups.add(group);
        }

        int pageNumber = 0;
        int size = 5;
        int numberOfReport = reportDAO.getNumberOfFinishReport();
        int numberOfPage = numberOfReport/size + (numberOfReport%size>0?1:0);
        if(page != null) {
            pageNumber = Integer.parseInt(page);
            request.setAttribute("ACTIVETAB", "tab2");

            if(pageNumber > numberOfPage) {
                return false;
            }
        }

        if(pageNumber <= 0) {
            pageNumber = 1;
        }

        List<TblReportEntity> finishReport = reportDAO.getFinishReport(size, (pageNumber-1)*size);
        List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
        for (int i = 0; i < finishReport.size(); i++) {
            ReportResponseObject report = new ReportResponseObject(finishReport.get(i));
            report.setReporter(finishReport.get(i).getTblUserByUserId().getTblUserInfoByUsername().getFullName());
            report.setListEquipment(equipmentDAO.getDamagedEquipmentNames(report.getReportId()));

            listReport.add(report);
        }

        request.setAttribute("PAGE", pageNumber);
        request.setAttribute("SIZE", numberOfPage);
        request.setAttribute("FINISHREPORT", listReport);
        request.setAttribute("NEWREPORT", groups);

        request.setAttribute("ACTIVELEFTTAB", "STAFF_NOTIFY");

        return true;
    }
}
