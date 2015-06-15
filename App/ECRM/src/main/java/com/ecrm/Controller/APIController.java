package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DAO.ReportDAO;
import com.ecrm.DAO.ReportDetailDAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.DTO.*;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Constant;
import com.ecrm.Utils.Utils;
import org.omg.Dynamic.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Htang on 6/2/2015.
 */
@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    ReportDAOImpl reportDAO;

    @Autowired
    EquipmentDAOImpl equipmentDAO;

    @Autowired
    ReportDetailDAOImpl reportDetailDAO;

    @Autowired
    EquipmentCategoryDAOImpl equipmentCategoryDAO;

    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @RequestMapping(value = "/map")
    public String generateMap(HttpServletRequest request, @RequestParam("id")int classroomId){
        TblClassroomEntity classroomEntity = new TblClassroomEntity();
        Collection<TblEquipmentEntity> equipmentEntities = classroomEntity.getTblEquipmentsById();

        request.setAttribute("CLASSROOM", classroomEntity);
        request.setAttribute("EQUIPMENTS", equipmentEntities);
        return "ClassroomMap";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody AccountDTO login(@RequestParam("username")String username, @RequestParam("password") String password,
                                          HttpServletRequest request) {
        AccountDTO accountDTO = new AccountDTO();

        TblUserEntity userEntity = userDAO.login(username, password);
        if (userEntity != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            userDAO.updateLastLogin(username, timestamp);
            accountDTO = Utils.convertFromUserToAccountDTO(userEntity);
        }
        return accountDTO;
    }

    @RequestMapping(value = "/getReportByUsername", method = RequestMethod.GET)
    public @ResponseBody
    List<ReportDTO> getReport(@RequestParam("username") String username) {
        List<TblReportEntity> entities = reportDAO.getReportByUserId(username);
        return Utils.convertFromListEntityToListDTO(entities);
    }

    @RequestMapping(value="/getAllReport", method = RequestMethod.GET)
    public @ResponseBody List<ReportDTO> getAllReport(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        List<TblReportEntity> entities = reportDAO.getAllReport(limit, offset);
        return Utils.convertFromListEntityToListDTO(entities);
    }

    @RequestMapping(value="/createReport", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO createReport( @RequestParam("username") String username,@RequestParam("RoomId") int roomId,
                            @RequestParam("Evaluate") String evaluate, @RequestParam("Description") String desc,
                            @RequestParam("ListDamaged") String listDamaged,
                            @RequestParam("ListPosition") String listPosition,
                            @RequestParam("ListEvaluate") String listEvaluate
                            ) {
        ResultDTO resultDTO = new ResultDTO();

        try {
            TblClassroomEntity classroomEntity = classroomDAO.find(roomId);
            TblReportEntity report = new TblReportEntity(username, roomId, evaluate);
            reportDAO.insert(report);
            int reportId = report.getId();

            String[] equipments = listDamaged.split(",");
            String[] evaluates = listEvaluate.split(",");
            String[] positions = listPosition.split("-");
            List<Integer> listId = new ArrayList<Integer>();

            Map<String, String> mapEquipment = new HashMap<String, String>();
            int flag =0;
            for(int j = 0; j < equipments.length; j++) {
                if (mapEquipment.get(equipments[j]) == null) {
                    mapEquipment.put(equipments[j], evaluates[flag]);
                    flag++;
                }
            }

            for(int i = 0; i < equipments.length; i++) {
                TblEquipmentEntity entity = new TblEquipmentEntity(equipmentCategoryDAO.findEquipmentId(equipments[i]),
                        roomId, "", "", positions[i], 0, true);
                equipmentDAO.insert(entity);
                TblReportDetailEntity detailEntity = new TblReportDetailEntity(entity.getId(), reportId,mapEquipment.get(equipments[i]), "1", positions[i]);
                reportDetailDAO.insert(detailEntity);

            }

            reportDAO.updateDamageLevel(checkDamagedLevel(classroomEntity), reportId);
            classroomDAO.updateDamageLevel(checkDamagedLevel(classroomEntity), roomId);

            resultDTO.setError_code(100);
            resultDTO.setError("OK");
            return resultDTO;
        }catch (Exception e) {
            resultDTO.setError_code(101);
            e.printStackTrace();
            return resultDTO;
        }




    }

    @RequestMapping(value = "/getReportStaff", method = RequestMethod.GET)
    public @ResponseBody List<ReportClassDTO> getReportStaff() {
        List<ReportClassDTO> result = new ArrayList<ReportClassDTO>();
        List<Integer> listClass = reportDAO.getReportByClassId();
        for (int classId : listClass) {
            //int id = Integer.parseInt(classId);
            List<TblReportDetailEntity> details = reportDetailDAO.getReportByClassId(classId);
            TblClassroomEntity classroomEntity = classroomDAO.find(classId);
            List<EquipmentDTO> equipments = new ArrayList<EquipmentDTO>();
            int reportId = 0;
            for (TblReportDetailEntity entity : details) {
                EquipmentDTO detailDTO = new EquipmentDTO();
                detailDTO.setEquipmentName(entity.getTblEquipmentByEquipmentId().getName());
                detailDTO.setDamage(entity.getDamagedLevel());
                detailDTO.setEvaluate(entity.getSolution());
                detailDTO.setReportId(entity.getReportId());
                detailDTO.setStatus(entity.isStatus());
                detailDTO.setQuantity(1);
                reportId = entity.getReportId();
                equipments.add(detailDTO);
            }
            TblReportEntity reportEntity = reportDAO.find(reportId);
            ReportClassDTO reportClassDTO = new ReportClassDTO();
            reportClassDTO.setRoomId(classId);
            reportClassDTO.setEquipments(equipments);
            reportClassDTO.setUserReport(reportEntity.getTblUserByUserId().getTblUserInfoByUsername().getFullName());
            reportClassDTO.setDamageLevel(classroomEntity.getDamagedLevel());
            reportClassDTO.setTimeReport(reportEntity.getCreateTime() + "");
            reportClassDTO.setEvaluate(reportEntity.getEvaluate());
            result.add(reportClassDTO);

        }

        return result;

    }

    @RequestMapping(value = "/getAvailableRoom", method=RequestMethod.GET)
    public @ResponseBody List<String> getAvailableRoom(@RequestParam("id") int roomId) {
        List<String> availableRooms = Utils.getAvailableRoom(scheduleDAO.getScheduleInTime(null, roomId), classroomDAO.findAll());
        return availableRooms;
    }


    @RequestMapping(value="/resolve", method = RequestMethod.POST)
    public @ResponseBody ResultDTO resolveReport(@RequestParam("reportId") int reportId,
                                                 @RequestParam("equipmentId") int equipmentId,
                                                 @RequestParam("solution") String solution) {
        ResultDTO result = new ResultDTO();
        try {
            reportDAO.resolveReport(reportId, equipmentId, solution);
            result.setError_code(100);
            result.setError("OK");
        }catch (Exception e) {
            result.setError_code(101);
            result.setError(e.getMessage());
        }

        return result;
    }

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



    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public @ResponseBody List<ScheduleDTO> getSchedule(@RequestParam("username") String username) {
        List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
        List<TblScheduleEntity> listSchedule = scheduleDAO.getSchedulesOfUser(username);
        for (TblScheduleEntity scheduleEntity : listSchedule) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setClassId(scheduleEntity.getClassroomId());
            dto.setClassName(scheduleEntity.getTblClassroomByClassroomId().getName());
            dto.setTimeFrom(scheduleEntity.getTimeFrom().getTime() + "");
            dto.setTimeTo(scheduleEntity.getTimeFrom().getTime() + (scheduleEntity.getSlots()* Constant.TIME_ONE_SLOT*1000) + "");
            result.add(dto);
        }
        return result;
    }


    @RequestMapping(value = "/getEquipments", method = RequestMethod.GET)
    public @ResponseBody List<String> getEquipment(@RequestParam("classId") int classId) {
        List<EquipmentClassDTO> result = new ArrayList<EquipmentClassDTO>();
        TblClassroomEntity classroomEntity = classroomDAO.find(classId);
        TblRoomTypeEntity roomType = classroomEntity.getTblRoomTypeByRoomTypeId();
        List<String> list = new ArrayList<String>();
        if (roomType.getAirConditioning() > 0) {
            list.add("Máy Lạnh");
        }
        if(roomType.getBulb() > 0) {
            list.add("Bóng Đèn");
        }
        if (roomType.getFan() > 0) {
            list.add("Quạt");
        }
        if (roomType.getProjector() > 0) {
            list.add("Máy Chiếu");
        }
        if (roomType.getTelevision() > 0) {
            list.add("Tivi");
        }
        if (roomType.getSpeaker() > 0) {
            list.add("Loa");
        }
        list.add("Bàn");
        list.add("Ghế");

        return list;
    }

}
