package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request){
        HttpSession session = request.getSession();

        List<Integer> rooms = reportDAO.getDamagedRoom();
        List<GroupReportsDTO> groups = new ArrayList<GroupReportsDTO>();
        String reporter = "";
        String equipmentNames = "";

        for(int i = 0; i < rooms.size(); i++){
            TblClassroomEntity room = classroomDAO.find(rooms.get(i));
            GroupReportsDTO group = new GroupReportsDTO();
            group.setRoomId(room.getId());
            group.setRoomName(room.getName());

            List<TblEquipmentCategoryEntity> equipments = equipmentCategoryDAO.getCategoriesInRoom(room.getId());
            for(int j = 0; j < equipments.size(); j++) {
                equipmentNames += equipments.get(j).getName() + ", ";
            }

            group.setListEquipments(equipmentNames.substring(0, equipmentNames.length()-2));
            group.setReporters(reportDAO.getReportersInRoom(room.getId()));

            groups.add(group);
        }
        request.setAttribute("NEWREPORT", groups);

        request.setAttribute("ACTIVELEFTTAB","STAFF_NOTIFY");
        return "staff/Notifications";
    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportDetail(HttpServletRequest request, @RequestParam(value = "roomId") int roomId){
        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");

        TblClassroomEntity classroom = classroomDAO.find(roomId);
        List<TblEquipmentEntity> equipments = equipmentDAO.getDamagedEquipments(classroom.getId());

        DamagedRoomDTO resultObject = new DamagedRoomDTO(classroom, reportDAO.getReportNewest(roomId), equipments);
        resultObject.setReporters(reportDAO.getReportersInRoom(roomId));
        resultObject.setRoomtype(classroom.getTblRoomTypeByRoomTypeId());
        resultObject.setDamagedLevel(classroom.getDamagedLevel());

        if(classroom.getDamagedLevel() >= 50) {
            TblScheduleEntity schedule = scheduleDAO.getScheduleInTime(null, roomId);
            if (schedule != null) {
                List<String> availableRooms = Utils.getAvailableRoom(scheduleDAO.getScheduleInTime(null, roomId), classroomDAO.findAll());
                resultObject.setSuggestRooms(availableRooms);
            }
        }
        request.setAttribute("DAMAGEDROOM", resultObject);
        return "staff/ReportDetail";
    }

    @RequestMapping(value = "sua-chua")
    @Transactional
    public String resolveReport(HttpServletRequest request, @RequestParam(value = "RoomId") int roomId,
                                @RequestParam(value = "ListResolve") String listResolve){
        String[] categories = listResolve.split(",");
        for (int i = 0; i < categories.length; i++) {
            resolve(roomId, Integer.parseInt(categories[i]));
        }

        TblClassroomEntity room = classroomDAO.find(roomId);
        room.setDamagedLevel(checkDamagedLevel(room));
        classroomDAO.merge(room);

        return "redirect:/thong-bao";
    }

    private boolean resolve(int room, int category){
        //Change Equipment status and ReportDetail status
        List<TblEquipmentEntity> equips = equipmentDAO.getDamagedEquipmentsByCategory(room, category);
        for (int i = 0; i < equips.size(); i++) {
            equips.get(i).setStatus(true);
            equipmentDAO.merge(equips.get(i));

            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getUnresolveReportDetail(equips.get(i).getId());
            for (int j = 0; j < reportDetails.size(); j++) {
                reportDetails.get(j).setStatus(true);
                reportDetails.get(j).setResolveTime(new Timestamp(new Date().getTime()));
                reportDetailDAO.merge(reportDetails.get(j));
            }
        }

        //Change Report status
        List<TblReportEntity> reports = reportDAO.getLiveReportsInRoom(room);
        int flag = 0;
        for (TblReportEntity report: reports) {
            List<TblReportDetailEntity> details = report.getTblReportDetailsById();
            for (int i = 0; i < details.size(); i++) {
                if(!details.get(i).isStatus()){
                    flag++;
                }
            }

            if(flag == details.size()){
                report.setStatus(ReportStatus.FINISH.getValue());
                reportDAO.merge(report);
            } else if(flag > 0) {
                report.setStatus(ReportStatus.GOING.getValue());
                reportDAO.merge(report);
            }
            flag = 0;
        }

        return true;
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
                        if (project.getDamagedLevel().equals("3")) {
                            projectorDamagedLevel = 20;
                        } else if (project.getDamagedLevel().equals("2")) {
                            projectorDamagedLevel = 30;
                        } else if (project.getDamagedLevel().equals("1")) {
                            projectorDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 2) {
                    List<TblReportDetailEntity> tivis = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity tivi : tivis) {
                        if (tivi.getDamagedLevel().equals("3")) {
                            tiviDamagedLevel = 20;
                        } else if (tivi.getDamagedLevel().equals("2")) {
                            tiviDamagedLevel = 30;
                        } else if (tivi.getDamagedLevel().equals("1")) {
                            tiviDamagedLevel = 50;
                        }

                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 3) {
                    List<TblReportDetailEntity> mayLanhs = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity mayLanh : mayLanhs) {
                        if (mayLanh.isStatus()) {
                            if (mayLanh.getDamagedLevel().equals("3")) {
                                mayLanhDamagedLevel += 10;
                            } else if (mayLanh.getDamagedLevel().equals("2")) {
                                mayLanhDamagedLevel += 15;
                            } else if (mayLanh.getDamagedLevel().equals("1")) {
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
                            if (quat.getDamagedLevel().equals("3")) {
                                quatDamagedLevel += 1;
                            } else if (quat.getDamagedLevel().equals("2")) {
                                quatDamagedLevel += 3;
                            } else if (quat.getDamagedLevel().equals("1")) {
                                quatDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 5) {
                    List<TblReportDetailEntity> loas = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity loa : loas) {
                        if (loa.getDamagedLevel().equals("3")) {
                            loaDamagedLevel = 1;
                        } else if (loa.getDamagedLevel().equals("2")) {
                            loaDamagedLevel = 3;
                        } else if (loa.getDamagedLevel().equals("1")){
                            loaDamagedLevel = 5;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 6) {
                    List<TblReportDetailEntity> dens = reportDetailDAO.getUnresolveReportDetail(tblEquipmentEntity.getId());
                    for (TblReportDetailEntity den : dens) {
                        if (den.getDamagedLevel().equals("3")) {
                            denDamagedLevel = 10;
                        } else if (den.getDamagedLevel().equals("2")) {
                            denDamagedLevel = 20;
                        } else if (den.getDamagedLevel().equals("1")){
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
                            if (ban.getDamagedLevel().equals("3")) {
                                banDamagedLevel += 2;
                            } else if (ban.getDamagedLevel().equals("2")) {
                                banDamagedLevel += 3;
                            } else if (ban.getDamagedLevel().equals("1")){
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
                            if (ghe.getDamagedLevel().equals("3")) {
                                gheDamagedLevel += 1;
                            } else if (ghe.getDamagedLevel().equals("2")) {
                                gheDamagedLevel += 2;
                            } else if (ghe.getDamagedLevel().equals("1")){
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
