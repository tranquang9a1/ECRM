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

        request.setAttribute("ACTIVETAB","STAFF_NOTIFY");
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

        return "redirect:/thong-bao";
    }

    private boolean resolve(int room, int category){
        //Change Equipment status and ReportDetail status
        List<TblEquipmentEntity> equips = equipmentDAO.getDamagedEquipmentsByCategory(room, category);
        for (int i = 0; i < equips.size(); i++) {
            equips.get(i).setStatus(false);
            equipmentDAO.merge(equips.get(i));

            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getUnresolveReportDetail(equips.get(i).getId());
            for (int j = 0; j < reportDetails.size(); j++) {
                reportDetails.get(j).setStatus(false);
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
}
