package com.ecrm.Service;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.*;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class APIService {

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

    @Autowired
    NotificationDAOImp notificationDAO;

    @Autowired
    CheckDamageService checkDamageService;
    @Autowired
    CategoryDAOImpl categoryDAO;


    public ClassroomMapDTO getClassroom(int classId) {
        TblClassroomEntity classroomEntity = classroomDAO.find(classId);
        JSONArray jsonArray = new JSONArray();
        ClassroomMapDTO classroomMapDTO = new ClassroomMapDTO();
        TblRoomTypeEntity2 tblRoomTypeEntity2 = classroomEntity.getTblRoomType2ByRoomTypeId2();
        List<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = tblRoomTypeEntity2.getTblEquipmentQuantityById();
        for (int i = 0; i < tblEquipmentQuantityEntities.size(); i++) {
            TblEquipmentQuantityEntity tblEquipmentQuantityEntity = tblEquipmentQuantityEntities.get(i);
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", tblEquipmentQuantityEntity.getEquipmentCategoryId());
            formDetailsJson.put("name", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getName());
            formDetailsJson.put("imageUrl", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getImageUrl());
            jsonArray.add(formDetailsJson);
        }
        classroomMapDTO.setEquipment(jsonArray);
        classroomMapDTO.setRoomType(tblRoomTypeEntity2);
        classroomMapDTO.setClassroom(classroomEntity);
        return classroomMapDTO;
    }

    public AccountDTO login(String username, String password) {
        AccountDTO accountDTO = new AccountDTO();
        TblUserEntity userEntity = userDAO.login(username, password);
        if (userEntity != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            userDAO.updateLastLogin(username, timestamp);
            accountDTO = Utils.convertFromUserToAccountDTO(userEntity);
        }
        return accountDTO;
    }

    public List<ReportDTO> getReportByUsername(String username, int offset, int limit) {
        TblUserEntity user = userDAO.findUser(username);
        if (user.isStatus()) {
            List<TblReportEntity> entities = reportDAO.getReportByUserId(username, offset, limit);
            return Utils.convertFromListEntityToListDTO(entities);
        } else {
            return new ArrayList<>();
        }

    }


    public List<ReportDTO> getAllReport(int limit, int offset) {
        List<TblReportEntity> entities = reportDAO.getAllReport(limit, offset);
        return Utils.convertFromListEntityToListDTO(entities);
    }

    public ResultDTO createReport(String username, String classId, String listDamaged, String listPosition,
                                  String listDescription, String evaluate, String listEvaluate, String createTime) {
        long time;

        TblUserEntity user = userDAO.findUser(username);
        if (!user.isStatus()) {
            return new ResultDTO(400, "Tai khoan bi khoa ");
        }

        if (createTime.equals("1")) {
            time = new Date().getTime();
            createTime = Utils.convertTime(time);
        } else {
            time = Long.parseLong(createTime);
            createTime = Utils.convertTime(time);
        }
        ResultDTO resultDTO = new ResultDTO();
        String currentDate = Utils.convertTime(new Date().getTime());
        if (currentDate.equals(createTime)) {
            try {
                int classroomId = Integer.parseInt(classId);
                TblClassroomEntity room = classroomDAO.find(classroomId);
                TblReportEntity report = reportDAO.getReportOfUsernameInDay(username, classroomId);
                if (report == null || report.getStatus() == Enumerable.ReportStatus.FINISH.getValue()) {
                    report = new TblReportEntity(username, classroomId, new Timestamp(time), evaluate, 1);
                    reportDAO.insert(report);
                }

                int reportId = report.getId();
                //position
                String[] positions = listPosition.split("-");
                //category name
                String[] categoryNames = listDamaged.split(",");
                List<Integer> categoriesId = new ArrayList<Integer>();
                //description
                String[] descriptions = listDescription.split(",");
                //list evaluate
                String[] evaluates = listEvaluate.split(",");
                String serialNumber = null;
                for (int i = 0; i < categoryNames.length; i++) {
                    categoriesId.add(equipmentCategoryDAO.findEquipmentId(categoryNames[i]));
                }
                for (int j = 0; j < categoriesId.size(); j++) {
                    int category = categoriesId.get(j);
                    String positon = positions[j];
                    String damagedLevel = evaluates[j];
                    String description = null;
                    if (Integer.parseInt(damagedLevel) == 1) {
                        description = descriptions[j];
                    }

                    TblEquipmentCategoryEntity tblEquipmentCategoryEntity = equipmentCategoryDAO.find(category);
                    if(!tblEquipmentCategoryEntity.getIsManaged()){
                        serialNumber = "";
                    }

                    if (!categoryNames[j].equalsIgnoreCase("Bàn") || !categoryNames[j].equalsIgnoreCase("Ghế")) {
                        TblEquipmentEntity tblEquipmentEntity = equipmentDAO.findEquipmentHavePosition(classroomId,
                                category, positon, serialNumber);
                        if (tblEquipmentEntity == null) {
                            tblEquipmentEntity = new TblEquipmentEntity(category, classroomId, null, null, positon,
                                    null, false);
                            equipmentDAO.insert(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        } else {
                            tblEquipmentEntity.setStatus(false);
                            equipmentDAO.merge(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        }
                    } else {
                        if (positon.equals("[0]")) {
                            positon = null;
                            TblEquipmentEntity tblEquipmentEntity = equipmentDAO.findEquipmentHavePosition(classroomId,
                                    category, positon, serialNumber);
                            if (tblEquipmentEntity == null) {
                                tblEquipmentEntity =
                                        new TblEquipmentEntity(category, classroomId, null, null, "[0]", null, false);
                                equipmentDAO.insert(tblEquipmentEntity);
                                TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                        tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                                reportDetailDAO.persist(tblReportDetailEntity);
                            } else {
                                tblEquipmentEntity.setPosition("[0]");
                                tblEquipmentEntity.setStatus(false);
                                equipmentDAO.merge(tblEquipmentEntity);
                                TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                        tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                                reportDetailDAO.persist(tblReportDetailEntity);
                            }
                        } else {
                            TblEquipmentEntity tblEquipmentEntity =
                                    equipmentDAO.findEquipmentHavePosition(classroomId, category, positon, serialNumber);
                            if (tblEquipmentEntity == null) {
                                tblEquipmentEntity =
                                        new TblEquipmentEntity(category, classroomId, null, null, positon, null, false);
                                equipmentDAO.insert(tblEquipmentEntity);
                                TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                        tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                                reportDetailDAO.persist(tblReportDetailEntity);
                            } else {
                                tblEquipmentEntity.setStatus(false);
                                equipmentDAO.merge(tblEquipmentEntity);
                                TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                        tblEquipmentEntity.getId(), reportId, damagedLevel, description, positon);
                                reportDetailDAO.persist(tblReportDetailEntity);
                            }

                        }
                    }
                }
                List<TblEquipmentEntity> listDamamagedEquipment = equipmentDAO.getDamagedEquipments(room.getId());

                int damagedLevel = checkDamageService.checkDamagedLevel(listDamamagedEquipment, room);

                room.setDamagedLevel(damagedLevel);
                classroomDAO.merge(room);

                resultDTO.setError_code(100);
                resultDTO.setError("OK");
                SocketIO socketIO = new SocketIO();
                List<TblUserEntity> staffs = userDAO.getAllStaff();
                String message = username + "  vừa báo cáo hư hại phòng " + room.getName();
                System.out.println("Send notification from mobile to web");
                for (TblUserEntity staff : staffs) {

                    boolean res = socketIO.sendNotifyMessageToUser(staff.getUsername(),
                            Enumerable.NotifyType.STAFFNOTIFYREPORT.getValue(), message, "/thong-bao/hu-hai?phong=" +
                                    report.getClassRoomId());

                    System.out.println("Send notification to " + staff.getUsername() + " - " + res);
                }
                System.out.println("End send notification from mobile to web");

                return resultDTO;
            } catch (Exception e) {
                resultDTO.setError_code(101);
                e.printStackTrace();
                return resultDTO;
            }
        } else {
            resultDTO.setError_code(101);
            return resultDTO;
        }
    }

    public ResultDTO removeReport(String username, int reportId) {
        try {
            TblUserEntity user = userDAO.findUser(username);
            if (!user.isStatus()) {
                return new ResultDTO(400, "Tai khoan bi khoa");
            }

            TblReportEntity report = reportDAO.find(reportId);
            List<TblReportDetailEntity> details = report.getTblReportDetailsById();
            for (TblReportDetailEntity detail : details) {
                detail.setStatus(true);
                reportDetailDAO.merge(detail);
                TblEquipmentEntity tblEquipmentEntity = detail.getTblEquipmentByEquipmentId();
                tblEquipmentEntity.setStatus(true);
                equipmentDAO.merge(tblEquipmentEntity);
            }
            report.setStatus(Enumerable.ReportStatus.REMOVE.getValue());
            reportDAO.merge(report);
            return new ResultDTO(200, "Success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO(500, e.getMessage());
        }
    }

    public ResultDTO editReport(String reportID, String username, String classId, String listDamaged, String listPosition,
                                String listDescription, String evaluate, String listEvaluate) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            TblUserEntity user = userDAO.findUser(username);
            if (!user.isStatus()) {
                return new ResultDTO(400, "Tai khoan bi khoa");
            }
            int classroomId = Integer.parseInt(classId);
            TblClassroomEntity room = classroomDAO.find(classroomId);

            int reportId = Integer.parseInt(reportID);
            TblReportEntity report = reportDAO.find(reportId);
            report.setEvaluate(evaluate);
            reportDAO.merge(report);


            // User muốn sửa lúc nào cũng được
//            Date nowDate = new Date();
//            long time = (nowDate.getTime() - report.getCreateTime().getTime()) / 1000*60;
//            if(time > 14) {
//                resultDTO.setError_code(500);
//                resultDTO.setError("Bạn hết thời gian sửa report!");
//                return resultDTO;
//            }

//            List<TblReportDetailEntity> reportDetails = report.getTblReportDetailsById();
//            for(TblReportDetailEntity detail: reportDetails) {
//                reportDetailDAO.remove(detail);
//            }

            List<TblEquipmentEntity> equipments = room.getTblEquipmentsById();
            for (TblEquipmentEntity equipment : equipments) {
                List<TblReportDetailEntity> reportDetails = reportDetailDAO.getUnresolveReportDetail(equipment.getId());
                for (TblReportDetailEntity detail : reportDetails) {
                    if (detail.getReportId() == reportId) {
                        if (reportDetails.size() < 2) {
                            equipment.setStatus(true);
                            equipmentDAO.merge(equipment);
                        }

                        reportDetailDAO.remove(detail);
                    }
                }
            }


            //position
            String[] positions = listPosition.split("-");
            //category name
            String[] categoryNames = listDamaged.split(",");
            List<Integer> categoriesId = new ArrayList<Integer>();
            //description
            String[] descriptions = listDescription.split(",");
            //list evaluate
            String[] evaluates = listEvaluate.split(",");
            String serialNumber = null;
            for (int i = 0; i < categoryNames.length; i++) {
                categoriesId.add(equipmentCategoryDAO.findEquipmentId(categoryNames[i]));
            }
            for (int j = 0; j < categoriesId.size(); j++) {
                int category = categoriesId.get(j);
                String position = positions[j];
                String damagedLevel = evaluates[j];
                String description = null;
                if (Integer.parseInt(damagedLevel) == 1) {
                    description = descriptions[j];
                }
                if (category > 3) {
                    serialNumber = "";
                }
                if (category < 7) {
                    TblEquipmentEntity tblEquipmentEntity =
                            equipmentDAO.findEquipmentHavePosition(classroomId, category, position, serialNumber);
                    if (tblEquipmentEntity == null) {
                        tblEquipmentEntity =
                                new TblEquipmentEntity(category, classroomId, null, null, position, null, false);
                        equipmentDAO.insert(tblEquipmentEntity);
                        TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                        reportDetailDAO.persist(tblReportDetailEntity);
                    } else {
                        tblEquipmentEntity.setStatus(false);
                        equipmentDAO.merge(tblEquipmentEntity);
                        TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                        reportDetailDAO.persist(tblReportDetailEntity);
                    }
                }
                if (category > 6) {
                    if (position.equals("[0]")) {
                        position = null;
                        TblEquipmentEntity tblEquipmentEntity =
                                equipmentDAO.findEquipmentHavePosition(classroomId, category, position, serialNumber);
                        if (tblEquipmentEntity == null) {
                            tblEquipmentEntity =
                                    new TblEquipmentEntity(category, classroomId, null, null, "[0]", null, false);
                            equipmentDAO.insert(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        } else {
                            tblEquipmentEntity.setPosition("[0]");
                            tblEquipmentEntity.setStatus(false);
                            equipmentDAO.merge(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        }
                    } else {
                        TblEquipmentEntity tblEquipmentEntity =
                                equipmentDAO.findEquipmentHavePosition(classroomId, category, position, serialNumber);
                        if (tblEquipmentEntity == null) {
                            tblEquipmentEntity =
                                    new TblEquipmentEntity(category, classroomId, null, null, position, null, false);
                            equipmentDAO.insert(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        } else {
                            tblEquipmentEntity.setStatus(false);
                            equipmentDAO.merge(tblEquipmentEntity);
                            TblReportDetailEntity tblReportDetailEntity = new TblReportDetailEntity(
                                    tblEquipmentEntity.getId(), reportId, damagedLevel, description, position);
                            reportDetailDAO.persist(tblReportDetailEntity);
                        }

                    }
                }
            }
            List<TblEquipmentEntity> listDamamagedEquipment = equipmentDAO.getDamagedEquipments(room.getId());

            int damagedLevel = checkDamageService.checkDamagedLevel(listDamamagedEquipment, room);

            room.setDamagedLevel(damagedLevel);
            classroomDAO.merge(room);

            resultDTO.setError_code(100);
            resultDTO.setError("OK");
            System.out.println("Update report finish!");

            return resultDTO;
        } catch (Exception e) {
            resultDTO.setError_code(101);
            e.printStackTrace();
            return resultDTO;
        }
    }

    public List<ReportClassDTO> getReportStaff(int limit, int offset, String status) {
        List<ReportClassDTO> result = new ArrayList<ReportClassDTO>();
        List<Integer> listClass = reportDAO.getReportByClassId(status, offset, limit);
        List<Integer> listReport = new ArrayList<Integer>();
        for (int classId : listClass) {
            //int id = Integer.parseInt(classId);
            List<TblReportDetailEntity> details = new ArrayList<TblReportDetailEntity>();
            if (status.equalsIgnoreCase("finish")) {
                details = reportDetailDAO.getReportFinishByClassID(classId);
            } else {
                details = reportDetailDAO.getReportByClassId(classId);
            }

            if (details.size() == 0) {
                continue;
            }

            TblClassroomEntity classroomEntity = classroomDAO.find(classId);
            List<EquipmentDTO> equipments = new ArrayList<EquipmentDTO>();
            int reportId = 0;
            Map<String, Integer> mapCount = new HashMap<String, Integer>();

            for (TblReportDetailEntity detail : details) {
                String categoryName =
                        detail.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName();
                if (mapCount.get(categoryName) != null) {
                    mapCount.put(categoryName, mapCount.get(categoryName) + 1);

                } else {
                    mapCount.put(categoryName, 1);
                }
            }

            Map<Integer, Integer> mapReport = new HashMap<Integer, Integer>();
            for (TblReportDetailEntity detail : details) {
                if (mapReport.get(detail.getReportId()) != null) {
                    mapReport.put(detail.getReportId(), mapReport.get(detail.getReportId()) + 1);
                } else {
                    mapReport.put(detail.getReportId(), 1);
                }
            }
            Map<String, Boolean> mapManage = new HashMap<String, Boolean>();
            for (TblReportDetailEntity entity : details) {
                String categoryName =
                        entity.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName();
                if (mapManage.get(categoryName) == null) {
                    EquipmentDTO detailDTO = new EquipmentDTO();

                    detailDTO.setEquipmentName(categoryName);
                    detailDTO.setDamage(entity.getDamagedLevel());
                    detailDTO.setEvaluate(entity.getDescription());
                    detailDTO.setReportId(entity.getReportId());
                    detailDTO.setStatus(entity.isStatus());
                    detailDTO.setQuantity(mapCount.get(detailDTO.getEquipmentName()));
                    reportId = entity.getReportId();
                    listReport.add(reportId);
                    mapManage.put(detailDTO.getEquipmentName(), true);
                    equipments.add(detailDTO);
                }

            }


            ReportClassDTO reportClassDTO = new ReportClassDTO();
            String userReport = "";
            for (Map.Entry<Integer, Integer> entry : mapReport.entrySet()) {
                TblReportEntity reportEntity = reportDAO.find(entry.getKey());
                reportClassDTO.setRoomName(reportEntity.getTblClassroomByClassRoomId().getName());
                reportClassDTO.setRoomId(classId);
                reportClassDTO.setEquipments(equipments);
                reportClassDTO.setDamageLevel(classroomEntity.getDamagedLevel());
                reportClassDTO.setTimeReport(reportEntity.getCreateTime() + "");
                reportClassDTO.setEvaluate(reportEntity.getEvaluate());
                reportClassDTO.setChangedRoom(reportEntity.getChangedRoom());
                if (!userReport.contains(reportEntity.getUsername())) {
                    userReport += reportEntity.getUsername() + ", ";
                }
            }

            reportClassDTO.setUserReport(userReport.substring(0, userReport.length() - 2));
            result.add(reportClassDTO);

        }

        System.out.println("Result Size: " + result.size());
        return result;
    }

    public List<String> getAvailableRoom(int roomId) {
        List<String> availableRooms = Utils.getAvailableRoom(scheduleDAO.getScheduleInTime(null, roomId), classroomDAO.findAll());
        return availableRooms;
    }

    public ResultDTO resolveReport(String listRoom) {
        ResultDTO result = new ResultDTO();
        try {
            String[] roomId = listRoom.split(",");
            for (int i = 0; i < roomId.length; i++) {
                System.out.println("RoomID" + roomId[i]);
                TblClassroomEntity room = classroomDAO.find(Integer.parseInt(roomId[i]));
                List<TblEquipmentEntity> equips = room.getTblEquipmentsById();
                for (TblEquipmentEntity equip : equips) {
                    if (!equip.isStatus()) {
                        equip.setStatus(true);
                        equipmentDAO.merge(equip);
                    }
                }

                List<TblReportEntity> reports = reportDAO.getLiveReportsInRoom(Integer.parseInt(roomId[i]));
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

                TblNotificationEntity notify = notificationDAO.getNotifyOfRoom(Integer.parseInt(roomId[i]),
                        Enumerable.MessageType.NEWREPORT.getValue());
                if (notify != null) {
                    notify.setStatus(false);
                    notificationDAO.merge(notify);
                }

                room.setDamagedLevel(0);
                classroomDAO.merge(room);
                result.setError("Success");
                result.setError_code(100);
            }

        } catch (Exception e) {
            result.setError_code(101);
            result.setError(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    public List<ScheduleDTO> getSchedule(String username) {

        TblUserEntity user = userDAO.findUser(username);
        if (!user.isStatus()) {
            return new ArrayList<>();
        }

        List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
        List<TblScheduleEntity> listSchedule = scheduleDAO.getSchedulesOfUser(username);
        for (int i = 0; i < listSchedule.size(); i++) {
            TblScheduleEntity scheduleEntity = listSchedule.get(i);
            ScheduleDTO dto = new ScheduleDTO();
            dto.setClassId(scheduleEntity.getClassroomId());
            dto.setClassName(scheduleEntity.getTblClassroomByClassroomId().getName());
            dto.setTimeFrom(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom().getTime() + "");
            int j = i + 1;
            if (j == listSchedule.size()) {
                dto.setTimeTo(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
            } else {
                TblScheduleEntity nextSchedule = listSchedule.get(j);
                if (nextSchedule.getClassroomId() == dto.getClassId() &&
                        nextSchedule.getScheduleConfigId() - scheduleEntity.getScheduleConfigId() == 1) {
                    dto.setTimeTo(nextSchedule.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
                    i = i + 1;
                } else {
                    dto.setTimeTo(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
                }
            }


            dto.setDate(scheduleEntity.getDate() + "");
            result.add(dto);
        }
        return result;
    }

    public ResultDTO getChangeRoom(int classId) {
        ResultDTO dto = new ResultDTO();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classId);
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
            if (!classroom.isEmpty()) {
                TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroom.get(0));
                tblScheduleEntity.setIsActive(false);
                scheduleDAO.merge(tblScheduleEntity);
                TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), classroomEntity.getId(),
                        tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng", tblScheduleEntity.getTimeFrom(),
                        tblScheduleEntity.getSlots(), tblScheduleEntity.getDate(), true);
                String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                        tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + classroomEntity.getName() + ".";
                scheduleDAO.persist(newSchedule);
                //update report status
                dto.setError(message);
                dto.setError_code(100);
                return dto;
            } else {
                String message = "Không còn phòng trống: " + tblScheduleEntity.getUsername() + " của phòng: " +
                        tblScheduleEntity.getTblClassroomByClassroomId().getName() + ".";
                dto.setError(message);
                dto.setError_code(101);
                return dto;
            }
        }
        return new ResultDTO(101, "Không có thời khóa biểu");
    }

    public List<EquipmentClassDTO> getEquipments(int classId) {
        List<EquipmentClassDTO> result = new ArrayList<EquipmentClassDTO>();
        TblClassroomEntity classroomEntity = classroomDAO.find(classId);
        List<TblEquipmentEntity> listEquipments = classroomEntity.getTblEquipmentsById();
        List<TblEquipmentQuantityEntity> equipmentQuantity = classroomEntity.getTblRoomType2ByRoomTypeId2().getTblEquipmentQuantityById();
        TblEquipmentEntity equipmentEntity = new TblEquipmentEntity();
        for (int i = 0; i < equipmentQuantity.size(); i++) {
            TblEquipmentQuantityEntity entity = equipmentQuantity.get(i);

            if (entity.getQuantity() > 0) {
                String name = entity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getName();
                int categoryId = entity.getEquipmentCategoryId();
                equipmentEntity = getEquipment(listEquipments, categoryId);
                if (equipmentEntity != null) {
                    result.add(new EquipmentClassDTO(name, equipmentEntity.getTimeRemain()+"", equipmentEntity.getName(), equipmentEntity.isStatus()));
                } else {
                    result.add(new EquipmentClassDTO(name, null, null, true));
                }

            }

        }
        equipmentEntity = getEquipment(listEquipments, 7);
        if (equipmentEntity != null) {
            result.add(new EquipmentClassDTO("Bàn", equipmentEntity.getTimeRemain()+"", equipmentEntity.getName(), equipmentEntity.isStatus()));
        } else {
            result.add(new EquipmentClassDTO("Bàn", null, null, true));
        }
        equipmentEntity = getEquipment(listEquipments, 8);
        if (equipmentEntity != null) {
            result.add(new EquipmentClassDTO("Ghế", equipmentEntity.getTimeRemain()+"", equipmentEntity.getName(), equipmentEntity.isStatus()));
        } else {
            result.add(new EquipmentClassDTO("Ghế", null, null, true));
        }


        return result;
    }

    public TblEquipmentEntity getEquipment(List<TblEquipmentEntity> listEquipments, int categoryId) {
        TblEquipmentEntity tblEquipmentEntity = null;
        for (int i = 0; i < listEquipments.size(); i++) {
            if (listEquipments.get(i).getCategoryId() == categoryId) {
                tblEquipmentEntity = listEquipments.get(i);

                if (tblEquipmentEntity != null && tblEquipmentEntity.isStatus() == false) {
                    break;
                }
            }
        }
        return tblEquipmentEntity;
    }

    public List<EquipmentCategoryDTO> getEquipment() {
        List<EquipmentCategoryDTO> result  = new ArrayList<EquipmentCategoryDTO>();
        List<TblEquipmentCategoryEntity> listEquipment = equipmentCategoryDAO.getAllEquipment();

        for (int i = 0; i < listEquipment.size(); i++) {
            TblEquipmentCategoryEntity equipment = listEquipment.get(i);
            EquipmentCategoryDTO dto = new EquipmentCategoryDTO();
            dto.setName(equipment.getName());
            dto.setImageUrl(equipment.getImageUrl());
            result.add(dto);
        }
        return result;
    }
}
