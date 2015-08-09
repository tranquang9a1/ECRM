package com.ecrm.Service;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.ClassDTO;
import com.ecrm.DTO.ClassroomMapDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import org.joda.time.LocalTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class ClassroomService {
    public static final String ERROR = "Error";
    @Autowired
    private ReportDAOImpl reportDAO;
    @Autowired
    private ScheduleDAOImpl scheduleDAO;
    @Autowired
    private EquipmentDAOImpl equipmentDAO;
    @Autowired
    private ClassroomDAOImpl classroomDAO;
    @Autowired
    private NotificationDAOImp notificationDAO;
    @Autowired
    private UserNotificationDAOImpl userNotificationDAO;
    @Autowired
    private RoomTypeDAOImpl roomType2DAO;

    public ClassDTO getClassroom(int classId) {
        TblClassroomEntity entity = classroomDAO.getClassroomById(classId);
        ClassDTO result = new ClassDTO(entity.getId(), entity.getName(), entity.getDamagedLevel());
        return result;
    }

    public TblClassroomEntity getClassroomByName(String name) {
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(name);
        return classroomEntity;
    }

    public void updateClassroom(TblClassroomEntity entity) {
        classroomDAO.merge(entity);
    }

    public TblClassroomEntity getClassroomById(int classroomId) {
        TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
        return classroomEntity;
    }

    public List<TblClassroomEntity> getAllClassroom() {
        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        List<TblClassroomEntity> tblClassroomEntities = new ArrayList<TblClassroomEntity>();
        for (TblClassroomEntity classroomEntity : lstClassRoom) {
            if (!classroomEntity.getIsDelete()) {
                tblClassroomEntities.add(classroomEntity);
            }
        }
        return tblClassroomEntities;
    }

    public List<ClassroomMapDTO> getAllClassroomMap() {
        List<ClassroomMapDTO> classroomMapDTOs = new ArrayList<ClassroomMapDTO>();
        List<TblClassroomEntity> lstClassRoom = classroomDAO.findAll();
        for (TblClassroomEntity classroomEntity : lstClassRoom) {
            if (!classroomEntity.getIsDelete()) {
                JSONArray jsonArray = new JSONArray();
                ClassroomMapDTO classroomMapDTO = new ClassroomMapDTO();
                TblRoomTypeEntity tblRoomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
                List<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = tblRoomTypeEntity.getTblEquipmentQuantityById();
                for (int i = 0; i < tblEquipmentQuantityEntities.size(); i++) {
                    TblEquipmentQuantityEntity tblEquipmentQuantityEntity = tblEquipmentQuantityEntities.get(i);
                    if (!tblEquipmentQuantityEntity.getIsDelete()) {
                        JSONObject formDetailsJson = new JSONObject();
                        formDetailsJson.put("id", tblEquipmentQuantityEntity.getEquipmentCategoryId());
                        formDetailsJson.put("name", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getName());
                        formDetailsJson.put("imageUrl", tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId().getImageUrl());
                        jsonArray.add(formDetailsJson);
                    }
                }
                classroomMapDTO.setEquipment(jsonArray);
                classroomMapDTO.setRoomType(tblRoomTypeEntity);
                classroomMapDTO.setClassroom(classroomEntity);
                classroomMapDTOs.add(classroomMapDTO);
            }
        }
        return classroomMapDTOs;
    }

    public String createClassroom(int roomTypeId, String roomName, String action, int classroomId) {
        try {
            String message = "";
            Date date = new Date();
            roomName = roomName.trim();

            if (roomName != null) {
                TblClassroomEntity classroom = classroomDAO.getClassroomByName(roomName);
                if (action.equals("update")) {
                    if (classroom == null) {
                        classroom = classroomDAO.find(classroomId);
                    }
                    String oldClass = classroom.getName();
                    Collection<TblEquipmentEntity> tblEquipmentEntities = classroom.getTblEquipmentsById();
                    for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                        tblEquipmentEntity.setClassroomId(null);
                        equipmentDAO.merge(tblEquipmentEntity);
                    }
                    classroom = new TblClassroomEntity(classroom.getId(), roomName, classroom.getCreateTime(),
                            new Timestamp(date.getTime()), false, false, 0, roomTypeId);
                    classroomDAO.merge(classroom);
                    message = "Cập nhật phòng " + oldClass + " thành công!";
                }
                if (action.equals("create") && classroom == null) {
                    classroom = new TblClassroomEntity(0, roomName.trim(), new Timestamp(date.getTime()), new Timestamp(date.getTime()), false, false, 0
                            , roomTypeId);
                    classroomDAO.insert(classroom);
                    message = "Tạo phòng " + roomName + " thành công!";
                }
            }

            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }


    public String removeClassroom(String classroomName) {
        try {
            TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroomName);
            Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
            for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                tblEquipmentEntity.setClassroomId(null);
                equipmentDAO.merge(tblEquipmentEntity);
            }
            classroomEntity.setIsDelete(true);
            classroomDAO.merge(classroomEntity);
            String message = "Xóa phòng " + classroomName + " thành công!";
            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String updateEquipment(int classroomId) {
        try {
            String message = "";
            TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
            TblRoomTypeEntity tblRoomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
            List<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = tblRoomTypeEntity.getTblEquipmentQuantityById();
            for (TblEquipmentQuantityEntity tblEquipmentQuantityEntity : tblEquipmentQuantityEntities) {
                //get category
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = tblEquipmentQuantityEntity.getTblEquipmentCategoryEntityByEquipmentCategoryId();
                //get equipment
                if (!tblEquipmentQuantityEntity.getIsDelete() && tblEquipmentCategoryEntity.getIsManaged()) {
                    //get quantity
                    int quantity = tblEquipmentQuantityEntity.getQuantity();
                    // get categoryId
                    int categoryId = tblEquipmentCategoryEntity.getId();
                    //get equipment already have in classroom
                    List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.getEquipmentByCategoryAndClassroomId(categoryId,
                            classroomEntity.getId());
                    //quantity missing equipment
                    int insertQuantity = quantity - tblEquipmentEntities.size();
                    if (insertQuantity > 0) {
                        //get available equipment
                        List<TblEquipmentEntity> insertEquipment = equipmentDAO.getEquipmentByCategory(categoryId);
                        if (insertEquipment.size() >= insertQuantity) {
                            for (int i = 0; i < insertQuantity; i++) {
                                insertEquipment.get(i).setClassroomId(classroomId);
                                equipmentDAO.merge(insertEquipment.get(i));
                            }
                        } else {
                            message = "Không đủ " + tblEquipmentCategoryEntity.getName();
                            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
                        }
                    }

                }
            }
            classroomEntity.setIsAllInformation(true);
            classroomEntity.setUpdateTime(new Timestamp(new Date().getTime()));
            classroomDAO.merge(classroomEntity);
            message = "Cập nhật thiết bị thành công!";
            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }


    public List<String> getAvailableRoom(int classroomId) {
        TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
        List<String> availableClassroom = new ArrayList<String>();
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity,classroomEntity, tblClassroomEntities);
            if (!classroom.isEmpty()) {
                if (availableClassroom.isEmpty()) {
                    availableClassroom = classroom;
                } else {
                    Iterator<String> it = availableClassroom.iterator();
                    while (it.hasNext()) {
                        String room = it.next();
                        if (!classroom.contains(room)) {
                            it.remove();
                        }
                    }
                }
            }
        }
        if (!availableClassroom.isEmpty()) {


            availableClassroom = Utils.sortClassroom(availableClassroom, classroomEntity.getName());
            availableClassroom.remove(classroomEntity.getName());
        }
        return availableClassroom;
    }

    public String changeRoom(String currentRoom, String changeRoom) {
        TblClassroomEntity currentClassroom = classroomDAO.getClassroomByName(currentRoom);
        TblClassroomEntity changeClassroom = classroomDAO.getClassroomByName(changeRoom);

        LocalTime localTime = new LocalTime();
        LocalTime noon = new LocalTime("12:00:00");
        List<TblScheduleEntity> currentSchedule;
        if (localTime.isBefore(noon)) {
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Morning");
        } else {
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(changeClassroom.getId(), "Noon");
        }

        if (currentSchedule != null && currentSchedule.size() > 0) {
            String message = "Đổi phòng từ " + currentRoom + " sang phòng " + changeRoom;
            //Group schedule by user
            List<GroupUser> groupUsers = new ArrayList<GroupUser>();
            for (TblScheduleEntity schedule : currentSchedule) {
                GroupUser group = GroupUser.checkContainIn(groupUsers, schedule.getUsername());
                if (group == null) {
                    group = new GroupUser(schedule.getUsername(), schedule.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), currentRoom, changeRoom);
                    groupUsers.add(group);
                }

                List<String> listTime = group.getListTime();
                listTime.add(schedule.getTblScheduleConfigByScheduleConfigId().getTimeFrom().getHours() + "h"
                        + schedule.getTblScheduleConfigByScheduleConfigId().getTimeFrom().getMinutes());

                schedule.setIsActive(false);
                schedule.setNote("Đổi sang phòng " + changeRoom);
                scheduleDAO.merge(schedule);

                TblScheduleEntity newSchedule = new TblScheduleEntity(schedule.getUsername(), changeClassroom.getId(),
                        schedule.getNumberOfStudents(), message, schedule.getDate(), true, schedule.getScheduleConfigId());
                scheduleDAO.persist(newSchedule);
            }

            TblNotificationEntity notify = notificationDAO.getNotifyOfRoom(currentClassroom.getId(), Enumerable.MessageType.CHANGEROOM.getValue());
            if (notify == null) {
                notify = new TblNotificationEntity(currentClassroom.getId(), message, "/giang-vien", Enumerable.MessageType.CHANGEROOM.getValue());
                notificationDAO.persist(notify);
            }

            SocketIO socket = new SocketIO();
            for (GroupUser user : groupUsers) {
                TblUserNotificationEntity userNotification = new TblUserNotificationEntity(user.getUsername(), notify.getId(), false);
                userNotificationDAO.persist(userNotification);

                //Message object for socket
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("currentRoom", currentRoom);
                jsonObject.put("currentRoomId", currentClassroom.getId());
                jsonObject.put("changeRoom", changeRoom);
                jsonObject.put("changeRoomId", changeClassroom.getId());
                jsonObject.put("listTime", user.getListTime());
                jsonObject.put("redirectLink", "/giang-vien/notify?link=" + userNotification.getId());

                //Sent notifies to user
                socket.sendNotifyObjectToStaff(user.getUsername(), Enumerable.NotifyType.TEACHERCHANGEROOM.getValue(), jsonObject);
                /*SmsUtils.sendMessage(user.getPhone(), user.toString());*/
            }

            //update status report
            List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroom.getId());
            for (TblReportEntity tblReportEntity : tblReportEntities) {
                tblReportEntity.setChangedRoom(changeRoom);
                tblReportEntity.setStatus(2);
                reportDAO.merge(tblReportEntity);
            }
            return "Đổi phòng thành công!";
        }

        return "Không còn lịch dạy của phòng " + currentRoom + " trong ngày!";
    }

    public int getFloor() {
        List<TblClassroomEntity> classroomEntities = classroomDAO.getAllClassroom();
        List<Integer> allCLass = new ArrayList<>();
        for (TblClassroomEntity classroomEntity : classroomEntities) {
            allCLass.add(Integer.parseInt(classroomEntity.getName()));
        }
        Collections.sort(allCLass);
        int floor = 0;
        for (int i = 0; i < allCLass.size(); i++) {
            int currentClass = allCLass.get(i);
            int currentFloor = currentClass / 100;
            if (floor != currentFloor) {
                floor += 1;
            }
        }
        return floor;
    }

    public List<List<ClassDTO>> getClassroomByFloor() {
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getAllClassroom();
        List<List<ClassDTO>> result = new ArrayList<>();
        List<Integer> allCLass = new ArrayList<>();
        for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
            allCLass.add(Integer.parseInt(classroomEntity.getName()));
        }
        Collections.sort(allCLass);
        int floor = 0;
        for (int i = 0; i < allCLass.size(); i++) {
            int currentClass = allCLass.get(i);
            int currentFloor = currentClass / 100;
            if (floor != currentFloor) {
                floor += 1;
            }
        }
        for (int i = 0; i <= floor; i++) {
            List<ClassDTO> roomFloor = new ArrayList<>();
            for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                int currentFloor = Integer.parseInt(classroomEntity.getName()) / 100;
                if (i == currentFloor) {
                    int damgagedLevel = 0;
                    if (classroomEntity.getDamagedLevel() != null) {
                        damgagedLevel = classroomEntity.getDamagedLevel();
                    }
                    ClassDTO classDTO = new ClassDTO();
                    classDTO.setClassName(classroomEntity.getName());
                    classDTO.setDamageLevel(damgagedLevel);
                    classDTO.setClassId(classroomEntity.getId());
                    roomFloor.add(classDTO);
                }
            }
            result.add(roomFloor);
        }

        return result;
    }

    public String changeRoomManually(int classroomId, String tf, String tt, String morning, String noon) throws UnsupportedEncodingException {
        String message="";
        if(morning.equals("0") && noon.equals("0")){
            message = "Không có gì thay đổi!";
            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date time1 = formatter.parse(tf.trim());
            if(tt.trim().length()==0){
                tt = tf;
            }
            Date time2 = formatter.parse(tt.trim());
            java.sql.Date timeFrom = new java.sql.Date(time1.getTime());
            java.sql.Date timeTo = new java.sql.Date(time2.getTime());
            TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
            if(!morning.equals("0")){
                TblClassroomEntity changeClassroom = classroomDAO.getClassroomByName(morning);
                List<TblScheduleEntity> morningSchedule = scheduleDAO.findAllScheduleInClassroomByDayTimeWithDateRange(classroomId,
                        "06:00:00", timeFrom, timeTo);
                for(TblScheduleEntity tblScheduleEntity: morningSchedule){
                    tblScheduleEntity.setIsActive(false);
                    tblScheduleEntity.setNote("Đổi sang phòng " + morning);
                    scheduleDAO.merge(tblScheduleEntity);
                    TblScheduleEntity changedSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroom.getId(),
                            tblScheduleEntity.getNumberOfStudents(), "Đổi từ phòng "+classroomEntity.getName()+" sang phòng "+morning,
                            tblScheduleEntity.getDate(), true,tblScheduleEntity.getScheduleConfigId());
                    scheduleDAO.persist(changedSchedule);
                }
            }
            if(!noon.equals("0")){
                TblClassroomEntity changeClassroom = classroomDAO.getClassroomByName(noon);
                List<TblScheduleEntity> noonSchedule = scheduleDAO.findAllScheduleInClassroomByDayTimeWithDateRange(classroomId,
                        "12:00:00", timeFrom, timeTo);
                for(TblScheduleEntity tblScheduleEntity: noonSchedule){
                    tblScheduleEntity.setIsActive(false);
                    tblScheduleEntity.setNote("Đổi sang phòng " + noon);
                    scheduleDAO.merge(tblScheduleEntity);
                    TblScheduleEntity changedSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroom.getId(),
                            tblScheduleEntity.getNumberOfStudents(), "Đổi từ phòng "+classroomEntity.getName()+" sang phòng "+noon,
                            tblScheduleEntity.getDate(), true,tblScheduleEntity.getScheduleConfigId());
                    scheduleDAO.persist(changedSchedule);
                }
            }
            message = "Đổi phòng cho phòng "+ classroomEntity.getName()+" thành công!";
        }catch (Exception e){
            message = "Sai kiểu ngày tháng yyyy-mm-dd!";
            return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
        }


        return "redirect:/staff/classroom?ACTIVETAB=tab1&MESSAGE=" + URLEncoder.encode(message, "UTF-8");
    }

}
