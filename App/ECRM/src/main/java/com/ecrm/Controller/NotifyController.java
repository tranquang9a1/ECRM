package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "")
    public String notifications(HttpServletRequest request){
        HttpSession session = request.getSession();

        List<TblReportEntity> reports = reportDAO.getAllReportInStatus(true);
        request.setAttribute("NEWREPORT", reports);

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
