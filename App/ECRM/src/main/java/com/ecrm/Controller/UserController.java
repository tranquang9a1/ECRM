package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
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


    @RequestMapping(value = "thong-bao")
    public String notifications(HttpServletRequest request){
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");

        List<TblReportEntity> list = reportDAO.getReportByUserId(user.getUsername());
        List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
        for(int i = 0; i < list.size(); i++) {
            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getReportDetailsInReport(list.get(i).getId());
            ReportResponseObject report = new ReportResponseObject(list.get(i),reportDetails);
            listReport.add(report);
        }
        request.setAttribute("NOTIFICATIONS", listReport);

        TblScheduleEntity schedule = scheduleDAO.getScheduleInTime(user.getUsername(), 0);
        if(schedule != null) {
            TblClassroomEntity classroom = classroomDAO.find(schedule.getClassroomId());
            TblRoomTypeEntity roomType = roomTypeDAO.find(classroom.getRoomTypeId());
            List<TblEquipmentEntity> listEquipment = equipmentDAO.getActiveEquipments(schedule.getClassroomId());

            request.setAttribute("ROOM", classroom);
            request.setAttribute("EQUIPMENTS", listEquipment);
            request.setAttribute("ROOMTYPE", roomType);
        } else {
            request.setAttribute("ROOM", new TblClassroomEntity());
        }

        request.setAttribute("ACTIVETAB", "USER_NOTIFY");
        return "user/Notifications";
    }

    @RequestMapping(value = "sentReport")
    @Transactional
    @ResponseBody
    public String createReport(HttpServletRequest request, @RequestParam("RoomId") int roomId, @RequestParam("Evaluate") String evaluate, @RequestParam("Description") String desc,
                               @RequestParam("ListDamaged") String listDamaged, @RequestParam("ListEvaluate") String listEvaluate){

        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");
        TblReportEntity report = new TblReportEntity(user.getUsername(), roomId,100, evaluate);
        reportDAO.persist(report);

        String[] evaluates = listEvaluate.split(",");
        int category = 0;
        String equipmentNames = "";
        TblEquipmentEntity equip;
        TblEquipmentCategoryEntity categoryName;

        if("".equals(listDamaged)) {
            for (int i = 0; i < evaluates.length; i++) {
                category = Integer.parseInt(evaluates[i].split("-")[0]);

                equip = insertEquipment(report.getId(), roomId, category, null, evaluates[i].split("-")[1]);
                categoryName = equipmentCategoryDAO.find(equip.getCategoryId());
                equipmentNames += categoryName.getName() + ", ";
            }
        } else {
            String[] equipments = listDamaged.split("--");
            for (int i = 0; i < evaluates.length; i++) {
                category = Integer.parseInt(evaluates[i].split("-")[0]);
                List<String> equipsInCate = getEquipmentsInCategory(equipments, category);

                if(equipsInCate.size() == 0) {
                    equip = insertEquipment(report.getId(), roomId, category, null, evaluates[i].split("-")[1]);

                    categoryName = equipmentCategoryDAO.find(equip.getCategoryId());
                    equipmentNames += categoryName.getName() + ", ";
                } else {
                    for(int j = 0; j < equipsInCate.size(); j++){
                        equip = insertEquipment(report.getId(), roomId, category, equipsInCate.get(j), evaluates[i].split("-")[1]);

                        if(j == 0) {
                            categoryName = equipmentCategoryDAO.find(equip.getCategoryId());
                            equipmentNames += categoryName.getName() + ", ";
                        }
                    }
                }
            }
        }

        TblClassroomEntity room = classroomDAO.find(report.getClassRoomId());
        return roomId + "-" + room.getName() + "-" + equipmentNames.substring(0, equipmentNames.length()-2) + "-" + report.getCreateTime().getTime();
    }

    @RequestMapping(value = "viewHistory")
    public String viewReportByUser(HttpServletRequest request, @RequestParam("ReportId") int reportId, @RequestParam("RoomId") String roomId){
        TblClassroomEntity classroom = classroomDAO.getClassroomByName(roomId);
        TblRoomTypeEntity roomType = roomTypeDAO.find(classroom.getRoomTypeId());
        List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(classroom.getId());
        TblReportEntity report = reportDAO.find(reportId);

        request.setAttribute("ROOM", classroom);
        request.setAttribute("REPORT", report);
        request.setAttribute("EQUIPMENTS", listEquipment);
        request.setAttribute("ROOMTYPE", roomType);

        return "user/ReportHistory";
    }

    @RequestMapping(value = "lich-day")
    public String viewSchedule(HttpServletRequest request){

        HttpSession session= request.getSession();
        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");

        List<TblScheduleEntity> list = scheduleDAO.getSchedulesOfUser(user.getUsername());

        request.setAttribute("SCHEDULE", list);
        request.setAttribute("ACTIVETAB","USER_SCHEDULE");
        return "user/Schedule";
    }

//  PRIVATE METHOD
    private List<String> getEquipmentsInCategory(String[] equipments, int category){

        List<String> result = new ArrayList<String>();
        for(int i = 0; i < equipments.length; i++) {
            String[] equip = equipments[i].split("-");
            if(Integer.parseInt(equip[0]) == category) {
                result.add(equip[1]);
            }
        }

        return result;
    }

    private TblEquipmentEntity insertEquipment(int reportId, int roomId, int category, String pos, String evaluate){
        String position = null;
        if(category < 7) {
            position = "[" + category + "]";
        } else if(pos != null && "".equals(pos) == false) {
            position = pos;
        }

        TblEquipmentEntity equip = equipmentDAO.findEquipmentHavePosition(roomId, category, position);

        if (equip != null) {
            equip.setStatus(true);
            equipmentDAO.merge(equip);
        } else {
            equip = new TblEquipmentEntity(category, roomId, null, null, position, 0, true);
            equipmentDAO.persist(equip);
        }

        TblReportDetailEntity reportDetail = new TblReportDetailEntity(equip.getId(), reportId, evaluate, equip.getPosition());
        reportDetailDAO.persist(reportDetail);

        return equip;
    }

    //Check damaged level
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
            if (tblEquipmentEntity.isStatus()) {
                damagedEquipment.add(tblEquipmentEntity);
            }
        }
        if (!damagedEquipment.isEmpty()) {
            for (TblEquipmentEntity tblEquipmentEntity : damagedEquipment) {
                if (tblEquipmentEntity.getCategoryId() == 1) {
                    List<TblReportDetailEntity> projectors = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity project : projectors) {
                            if (project.getDamagedLevel().equals("1")) {
                                projectorDamagedLevel = 20;
                            }
                            if (project.getDamagedLevel().equals("2")) {
                                projectorDamagedLevel = 30;
                            }
                            if (project.getDamagedLevel().equals("3")) {
                                projectorDamagedLevel = 50;
                            }
                        }
                }
                if (tblEquipmentEntity.getCategoryId() == 2) {
                    List<TblReportDetailEntity> tivis = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity tivi : tivis) {
                            if (tivi.getDamagedLevel().equals("1")) {
                                tiviDamagedLevel = 20;
                            }
                            if (tivi.getDamagedLevel().equals("2")) {
                                tiviDamagedLevel = 30;
                            }
                            if (tivi.getDamagedLevel().equals("3")) {
                                tiviDamagedLevel = 50;
                            }

                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 3) {
                    List<TblReportDetailEntity> mayLanhs = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity mayLanh : mayLanhs) {
                        if (mayLanh.isStatus()) {
                            if (mayLanh.getDamagedLevel().equals("1")) {
                                mayLanhDamagedLevel += 10;
                            }
                            if (mayLanh.getDamagedLevel().equals("2")) {
                                mayLanhDamagedLevel += 15;
                            }
                            if (mayLanh.getDamagedLevel().equals("3")) {
                                mayLanhDamagedLevel += 25;
                            }
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
                            if (quat.getDamagedLevel().equals("1")) {
                                quatDamagedLevel += 1;
                            }
                            if (quat.getDamagedLevel().equals("2")) {
                                quatDamagedLevel += 3;
                            } else {
                                quatDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 5) {
                    List<TblReportDetailEntity> loas = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity loa : loas) {
                        if (loa.getDamagedLevel().equals("1")) {
                            loaDamagedLevel = 1;
                        }
                        if (loa.getDamagedLevel().equals("2")) {
                            loaDamagedLevel = 3;
                        } else {
                            loaDamagedLevel = 5;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 6) {
                    List<TblReportDetailEntity> dens = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity den : dens) {
                        if (den.getDamagedLevel().equals("1")) {
                            denDamagedLevel = 10;
                        }
                        if (den.getDamagedLevel().equals("2")) {
                            denDamagedLevel = 20;
                        } else {
                            denDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 7) {
                    List<TblReportDetailEntity> bans = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    if ((bans.size() / table) / 100 >= 50) {
                        banDamagedLevel = 50;
                    } else {
                        for (TblReportDetailEntity ban : bans) {
                            if (ban.getDamagedLevel().equals("1")) {
                                banDamagedLevel += 2;
                            }
                            if (ban.getDamagedLevel().equals("2")) {
                                banDamagedLevel += 3;
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
                            if (ghe.getDamagedLevel().equals("1")) {
                                gheDamagedLevel += 1;
                            }
                            if (ghe.getDamagedLevel().equals("2")) {
                                gheDamagedLevel += 2;
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
