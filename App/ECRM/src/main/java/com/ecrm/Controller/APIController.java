package com.ecrm.Controller;

import com.ecrm.DTO.*;
import com.ecrm.Entity.*;
import com.ecrm.Service.APIService;
import com.ecrm.Service.ClassroomService;
import com.ecrm.Service.SMSService;
import com.ecrm.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Htang on 6/2/2015.
 */
@Controller
@RequestMapping("/api")
public class APIController {


    @Autowired
    private APIService apiService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping(value = "/map")
    public String generateMap(HttpServletRequest request, @RequestParam("id") int classroomId) {
        ClassroomMapDTO classroomEntity = apiService.getClassroom(classroomId);
        List<TblEquipmentEntity> equipmentEntities = classroomEntity.getClassroom().getTblEquipmentsById();

        request.setAttribute("CLASSROOM", classroomEntity);
        request.setAttribute("EQUIPMENTS", equipmentEntities);
        return "ClassroomMap";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public
    @ResponseBody
    AccountDTO login(@RequestParam("username") String username, @RequestParam("password") String password,
                     HttpServletRequest request) {
        AccountDTO accountDTO = apiService.login(username, password);
        return accountDTO;
    }

    @RequestMapping(value = "/getReportByUsername", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ReportDTO> getReport(@RequestParam("username") String username, @RequestParam("offset") int offset,
                              @RequestParam("limit") int limit) {

        return apiService.getReportByUsername(username, offset, limit);
    }

    @RequestMapping(value = "/getAllReport", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ReportDTO> getAllReport(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return apiService.getAllReport(limit, offset);
    }

    @RequestMapping(value = "/createReport", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO createReport(@RequestParam("username") String username, @RequestParam("classId") String classId,
                           @RequestParam("listDamaged") String listDamaged,
                           @RequestParam("listPosition") String listPosition,
                           @RequestParam("listDescription") String listDescription,
                           @RequestParam("evaluate") String evaluate, @RequestParam("listEvaluate") String listEvaluate,
                           @RequestParam("createTime") String createTime) {
        return apiService.createReport(username, classId, listDamaged, listPosition, listDescription,
                evaluate, listEvaluate, createTime);

    }

    @RequestMapping(value = "/editReport", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO editReport(@RequestParam("reportId") String reportID, @RequestParam("classId") String classId,
                         @RequestParam("listDamaged") String listDamaged, @RequestParam("username") String username,
                         @RequestParam("listPosition") String listPosition,
                         @RequestParam("listDescription") String listDescription,
                         @RequestParam("evaluate") String evaluate,
                         @RequestParam("listEvaluate") String listEvaluate) {
        return apiService.editReport(reportID, username, classId, listDamaged, listPosition, listDescription, evaluate,
                listEvaluate);
    }


    @RequestMapping(value = "/getReportStaff", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ReportClassDTO> getReportStaff(@RequestParam("status") String status, @RequestParam("offset") int offset,
                                        @RequestParam("limit") int limit) {
        return apiService.getReportStaff(limit, offset, status);

    }

    @RequestMapping(value = "/getAvailableRoom", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getAvailableRoom(@RequestParam("id") int roomId) {
        return apiService.getAvailableRoom(roomId);
    }


    @RequestMapping(value = "/resolve", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO resolveReport(@RequestParam("listRoomId") String listRoom) {
        return apiService.resolveReport(listRoom);

    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ScheduleDTO> getSchedule(@RequestParam("username") String username) {
        return apiService.getSchedule(username);
    }

    @RequestMapping(value = "/getChangeRoom", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO getChangeRoom(@RequestParam("classId") int classId) {
        return apiService.getChangeRoom(classId);
    }


    @RequestMapping(value = "/getEquipments", method = RequestMethod.GET)
    public
    @ResponseBody
    List<EquipmentClassDTO> getEquipment(@RequestParam("classId") int classId) {
        return apiService.getEquipments(classId);
    }

    @RequestMapping(value = "/sendSMS", method = RequestMethod.GET)
    public ResultDTO sendSMS(@RequestParam("message") String message, @RequestParam("ListUser") String listUsers) {
        return smsService.sendSMS(listUsers, message);

    }

    @RequestMapping(value = "/checkSchedule", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkSchedule(@RequestParam("classId") int classId) {
        return scheduleService.checkSchedule(classId);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO removeReport(@RequestParam("username") String username, @RequestParam("reportId") int reportId) {
        return apiService.removeReport(username, reportId);
    }

    @RequestMapping(value = "/getCurrentTime", method = RequestMethod.GET)
    public
    @ResponseBody
    Long getCurrentTime() {
        return System.currentTimeMillis();
    }

    @RequestMapping(value = "/getAllSchedule", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ScheduleDTO> getAllSchedule(@RequestParam("username") String username) {
        return scheduleService.getAllSchedule(username);
    }

    @RequestMapping(value = "/checkConnection", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO checkConnection() {
        return new ResultDTO(200, "Connect Successful");
    }

    @RequestMapping(value = "/getClassroom", method = RequestMethod.GET)
    public
    @ResponseBody
    ClassDTO getClassroom(@RequestParam("classId") int classId) {
        return classroomService.getClassroom(classId);
    }

    @RequestMapping(value = "/getCategory", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CategoryDTO> getCategory(@RequestParam("username") String username) {
        return apiService.getEquipment(username);
    }

    @RequestMapping(value = "/getFloor", method = RequestMethod.GET)
    public @ResponseBody int getFloor() {
        return classroomService.getFloor();
    }

    @RequestMapping(value = "/getClassInFloor", method = RequestMethod.GET)
    public @ResponseBody List<RoomWithFloorDTO> getRoomInFloor(@RequestParam("floor") int floor) {
        return classroomService.getClassroomByFloor(floor);
    }


}


