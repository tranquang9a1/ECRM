package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ReportResponseObject;
import com.ecrm.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @RequestMapping(value = "thong-bao")
    public String notifications(HttpServletRequest request){
        HttpSession session = request.getSession();

        List<TblReportEntity> list = reportDAO.findAll();
        List<ReportResponseObject> listReport = new ArrayList<ReportResponseObject>();
        for(int i = 0; i < list.size(); i++) {
            List<TblReportDetailEntity> reportDetails = reportDetailDAO.getReportDetailsInReport(list.get(i).getId());
            ReportResponseObject report = new ReportResponseObject(list.get(i),reportDetails);
            listReport.add(report);
        }
        request.setAttribute("NOTIFICATIONS", listReport);

        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");
        int classroomId = scheduleDAO.getClassroomIdByUsername(user.getUsername());
        if(classroomId != 0) {
            TblClassroomEntity classroom = classroomDAO.find(classroomId);
            TblRoomTypeEntity roomType = roomTypeDAO.find(classroom.getRoomTypeId());
            List<TblEquipmentEntity> listEquipment = equipmentDAO.getEquipmentsInClassroom(classroomId);

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
    public String createReport(HttpServletRequest request, @RequestParam("RoomId") int roomId, @RequestParam("Evaluate") String evaluate, @RequestParam("Description") String desc,
                               @RequestParam("ListDamaged") String listDamaged, @RequestParam("ListEvaluate") String listEvaluate){

        HttpSession session = request.getSession();
        TblUserEntity user = (TblUserEntity)session.getAttribute("USER");
        TblReportEntity report = new TblReportEntity(user.getUsername(), roomId, evaluate, desc);
        reportDAO.persist(report);

        String[] equipments = listDamaged.split(",");
        String[] evaluates = listEvaluate.split(",");

        for(int i = 0; i < equipments.length; i++) {
            TblEquipmentEntity equip = equipmentDAO.find(Integer.parseInt(equipments[i]));
            if(equip != null) {
                TblReportDetailEntity reportDetail = new TblReportDetailEntity(equip.getId(), report.getId(), getEvaluate(evaluates, equip.getCategoryId()), equip.getPosition());

                equip.setStatus("DAMAGED");
                equipmentDAO.merge(equip);
                reportDetailDAO.persist(reportDetail);
            }
        }

        request.setAttribute("MESSAGE","Báo cáo đã được gửi thành công! " + report.getId());
        return "redirect:/giang-vien/thong-bao";
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

        List<TblScheduleEntity> list = scheduleDAO.getScheduleOfUser(user.getUsername());

        request.setAttribute("SCHEDULE", list);
        request.setAttribute("ACTIVETAB","USER_SCHEDULE");
        return "user/Schedule";
    }

//    PRIVATE METHOD
    private String getEvaluate(String[] evaluates, int category){

        for(int i = 0; i < evaluates.length; i++) {
            String[] list = evaluates[i].split("-");
            if(Integer.parseInt(list[0]) == category) {
                return list[1];
            }
        }

        return null;
    }
}
