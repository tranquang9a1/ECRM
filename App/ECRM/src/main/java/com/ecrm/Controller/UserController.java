package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ReportResponseObject;
import com.ecrm.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
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
    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;

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
            List<TblEquipmentEntity> listEquipment = equipmentDAO.getActiveEquipments(classroomId);

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
        TblReportEntity report = new TblReportEntity(user.getUsername(), roomId, evaluate, desc);
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

        List<TblScheduleEntity> list = scheduleDAO.getScheduleOfUser(user.getUsername());

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
        TblEquipmentEntity equip = equipmentDAO.findEquipmentHavePosition(roomId, "[" + category + "]");
        if (category > 6) {
            equip = equipmentDAO.findEquipmentHavePosition(roomId, pos);
        }
        if (equip != null) {
            equip.setStatus(true);
            equipmentDAO.merge(equip);
        } else {
            String position = null;
            if(category < 7) {
                position = "[" + category + "]";
            } else if(pos != null && "".equals(pos) == false) {
                position = pos;
            }
            equip = new TblEquipmentEntity(category, roomId, null, null, position, 0, true);
            equipmentDAO.persist(equip);
        }

        TblReportDetailEntity reportDetail = new TblReportDetailEntity(equip.getId(), reportId, evaluate, equip.getPosition());
        reportDetailDAO.persist(reportDetail);

        return equip;
    }
}
