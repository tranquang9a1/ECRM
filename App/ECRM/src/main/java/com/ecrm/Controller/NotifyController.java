package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.DamagedRoomDTO;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable.ReportStatus;
import com.ecrm.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<TblEquipmentEntity> equipments = equipmentDAO.getActiveEquipments(classroom.getId());

        DamagedRoomDTO resultObject = new DamagedRoomDTO(classroom, reportDAO.getReportNewest(roomId));
        resultObject.setReporters(reportDAO.getReportersInRoom(roomId));
        resultObject.setRoomtype(classroom.getTblRoomTypeByRoomTypeId());
        resultObject.setEquipments(equipments);

        int damagedLevel = Utils.checkDamagedLevel(classroomDAO.find(roomId));
        resultObject.setDamagedLevel(damagedLevel);

        if(damagedLevel >= 50) {
            TblScheduleEntity schedule = scheduleDAO.getScheduleInTime(null, roomId);
            if (schedule != null) {
                List<String> availableRooms = Utils.getAvailableRoom(scheduleDAO.getScheduleInTime(null, roomId), classroomDAO.findAll());
                resultObject.setSuggestRooms(availableRooms);
            }
        }
        request.setAttribute("DAMAGEDROOM", resultObject);
        return "staff/ReportDetail";
    }
}
