package com.ecrm.Controller;

import com.ecrm.DAO.EquipmentCategoryDAO;
import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.GroupReportsDTO;
import com.ecrm.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;

    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request){
        HttpSession session = request.getSession();

        List<Integer> rooms = reportDAO.getDamagedRoom(true);
        List<GroupReportsDTO> groups = new ArrayList<GroupReportsDTO>();
        String reporter = "";
        String equipmentNames = "";

        for(int i = 0; i < rooms.size(); i++){
            TblClassroomEntity room = classroomDAO.find(rooms.get(i));
            GroupReportsDTO group = new GroupReportsDTO();
            group.setRoomId(room.getId());
            group.setRoomName(room.getName());

            List<TblReportEntity> reportsInRoom = reportDAO.getReportsInRoom(room.getId());
            for(int j = 0; j < reportsInRoom.size(); j++) {
                reporter += reportsInRoom.get(j).getTblUserByUserId().getTblUserInfoByUsername().getFullName() + ", ";
            }

            List<TblEquipmentCategoryEntity> equipments = equipmentCategoryDAO.getCategoriesInRoom(room.getId());
            for(int j = 0; j < equipments.size(); j++) {
                equipmentNames += equipments.get(j).getName() + ", ";
            }

            group.setListEquipments(equipmentNames.substring(0, equipmentNames.length()-2));
            group.setReporters(reporter.substring(0, reporter.length()-2));

            groups.add(group);
        }
        request.setAttribute("NEWREPORT", groups);

        request.setAttribute("ACTIVETAB","STAFF_NOTIFY");
        return "staff/Notifications";
    }

    @RequestMapping(value = "chi-tiet")
    public String viewReportDetail(HttpServletRequest request, @RequestParam(value = "reportId") int reportId){

        TblReportEntity report = reportDAO.find(reportId);
        TblClassroomEntity classroom = report.getTblClassroomByClassRoomId();
        TblRoomTypeEntity roomType = classroom.getTblRoomTypeByRoomTypeId();
        List<TblEquipmentEntity> equipments = equipmentDAO.getEquipmentsInClassroom(classroom.getId());
        List<TblReportDetailEntity> reportDetails = reportDetailDAO.getReportDetailsInReport(reportId);

        return "staff/ReportDetail";
    }
}
