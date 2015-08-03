package com.ecrm.Service;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.DTO.ReportResponseObject;
import com.ecrm.DTO.StatisticDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Utils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 7/24/2015.
 */

@Service
public class ReportService {
    @Autowired
    private ReportDAOImpl reportDAO;
    @Autowired
    private ReportDetailDAOImpl reportDetailDAO;
    @Autowired
    private ClassroomDAOImpl classroomDAO;
    @Autowired
    private EquipmentDAOImpl equipmentDAO;
    @Autowired
    private EquipmentCategoryDAOImpl equipmentCategoryDAO;
    @Autowired
    private UserNotificationDAOImpl userNotificationDAO;

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CheckDamageService checkDamageService;

    // PUBLIC METHOD
    public boolean resolveRoom(int roomId, String listResolve, String listRealEquip) {
        String[] categories = listResolve.split(",");
        String[] listEquipment = listRealEquip.split(",");
        for (int i = 0; i < categories.length; i++) {
            resolveEquipment(roomId, Integer.parseInt(categories[i]), listEquipment);
        }

        //Change Report status
        List<TblReportEntity> reports = reportDAO.getLiveReportsInRoom(roomId);
        int flag = 0;
        for (TblReportEntity report : reports) {
            List<TblReportDetailEntity> details = report.getTblReportDetailsById();
            for (int i = 0; i < details.size(); i++) {
                if (details.get(i).isStatus()) {
                    flag++;
                }
            }

            if (flag == details.size()) {
                report.setStatus(Enumerable.ReportStatus.FINISH.getValue());
                reportDAO.merge(report);

                notificationService.changeNotificationStatus(roomId);
            } else if (flag > 0) {
                report.setStatus(Enumerable.ReportStatus.GOING.getValue());
                reportDAO.merge(report);
            }
            flag = 0;
        }

        TblClassroomEntity room = classroomDAO.find(roomId);
        List<TblEquipmentEntity> listDamagedEquipment = equipmentDAO.getDamagedEquipments(room.getId());

        int damagedLevel = checkDamageService.checkDamagedLevel(listDamagedEquipment, room);
        room.setDamagedLevel(damagedLevel);
        classroomDAO.merge(room);

        return true;
    }

    public boolean resolveAllEquipment(int roomId) {
        TblClassroomEntity room = classroomDAO.find(roomId);
        if(room == null) {
            return false;
        }

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

            report.setStatus(Enumerable.ReportStatus.FINISH.getValue());
            reportDAO.merge(report);
        }

        notificationService.changeNotificationStatus(roomId);
        room.setDamagedLevel(0);
        classroomDAO.merge(room);

        return true;
    }

    public DamagedRoomDTO getReportDetail(int roomId){
        TblClassroomEntity classroom = classroomDAO.find(roomId);
        if(classroom == null || classroom.getDamagedLevel() == 0) {
            return null;
        }

        List<TblEquipmentEntity> equipments = equipmentDAO.getEquipmentsInClassroom(classroom.getId());
        DamagedRoomDTO resultObject = new DamagedRoomDTO(classroom, reportDAO.getReportNewest(roomId), equipments);

        resultObject.setReporters(reportDAO.getReportersInRoom(roomId));
        resultObject.setRoomtype(classroom.getTblRoomType2ByRoomTypeId2());
        resultObject.setDamagedLevel(classroom.getDamagedLevel());

        List<String> availableRooms = classroomService.getAvailableRoom(roomId);
        if (availableRooms.size() > 0) {
            resultObject.setSuggestRooms(availableRooms);
        } else {
            resultObject.setFree(true);
        }

        return resultObject;
    }

    public boolean getListReport(HttpServletRequest request, String page) {
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
        if(page != null && !page.equals("0")) {
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

        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity) session.getAttribute("USER");
        int numberUnreadNotify = userNotificationDAO.getNumberUnreadNotifyOfUser(user.getUsername());
        request.setAttribute("NUMBEROFNOTIFY", numberUnreadNotify);

        request.setAttribute("TABCONTROL", "STAFF_REPORT");

        return true;
    }

    public void changeLiveReportStatus(int currentClassroomId, String changeClassroom){
        List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroomId);
        for(TblReportEntity tblReportEntity:tblReportEntities){
            tblReportEntity.setChangedRoom(changeClassroom);
            tblReportEntity.setStatus(2);
            reportDAO.merge(tblReportEntity);
        }
    }

    public StatisticDTO getNumberOfChangeRoomByMonth(int year) {
        List list = reportDAO.getNumberOfChangeRoomByMonth(year);
        if(list.size() > 0) {
            StatisticDTO result = new StatisticDTO();
            for(int i = 0; i < list.size(); i++) {
                Object[] item = (Object[]) list.get(i);
                result.getListMonth().add(item[1].toString());
                result.getListNumber().add(((Long) item[0]).intValue());
            }

            return result;
        }

        return null;
    }

    // PRIVATE METHOD
    private boolean resolveEquipment(int room, int category, String[] listEquipment) {
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
}
