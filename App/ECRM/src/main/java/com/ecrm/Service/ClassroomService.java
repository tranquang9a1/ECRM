package com.ecrm.Service;

import com.ecrm.DAO.Impl.*;
import com.ecrm.DTO.ClassDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Enumerable;
import com.ecrm.Utils.Utils;
import com.ecrm.Utils.socket.SocketIO;
import org.joda.time.LocalTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class ClassroomService {
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


    public ClassDTO getClassroom(int classId) {
        TblClassroomEntity entity = classroomDAO.getClassroomById(classId);
        ClassDTO result = new ClassDTO(entity.getId(), entity.getName(), entity.getDamagedLevel());
        return result;
    }

    public TblClassroomEntity getClassroomByName(String name) {
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(name);
        return classroomEntity;
    }

    public TblClassroomEntity getClassroomById(int classroomId){
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

    public Boolean createClassroom(int roomTypeId, String roomName) {
        try {
            Date date = new Date();
            if (roomName != null) {
                TblClassroomEntity classroom = classroomDAO.getClassroomByName(roomName);
                if (classroom != null) {
                    Collection<TblEquipmentEntity> tblEquipmentEntities = classroom.getTblEquipmentsById();
                    for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                        tblEquipmentEntity.setClassroomId(null);
                        equipmentDAO.merge(tblEquipmentEntity);
                    }
                    classroom = new TblClassroomEntity(classroom.getId(), roomTypeId, roomName, classroom.getCreateTime(),
                            new Timestamp(date.getTime()), false, false, 0);
                    classroomDAO.merge(classroom);
                    insertEquipment(roomName);
                } else {
                    classroom = new TblClassroomEntity(0, roomTypeId, roomName, new Timestamp(date.getTime()), null, false, true, 0);
                    classroomDAO.persist(classroom);
                    insertEquipment(roomName);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertEquipment(String roomName) {
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(roomName);
        TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity();
        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
        if (roomTypeEntity.getProjector() > 0) {
            tblEquipmentEntity = new TblEquipmentEntity(1, classroomEntity.getId(), null, null, "[1]", 3000.0, true);
            equipmentDAO.persist(tblEquipmentEntity);
        }
        if (roomTypeEntity.getAirConditioning() > 0) {
            for (int i = 0; i < roomTypeEntity.getAirConditioning(); i++) {
                tblEquipmentEntity = new TblEquipmentEntity(3, classroomEntity.getId(), null, null, "[3]", null, true);
                equipmentDAO.persist(tblEquipmentEntity);
            }
        }
        if (roomTypeEntity.getTelevision() > 0) {
            tblEquipmentEntity = new TblEquipmentEntity(2, classroomEntity.getId(), null, null, "[2]", null, true);
            equipmentDAO.persist(tblEquipmentEntity);
        }
    }

    public boolean removeClassroom(String classroomName) {
        try {
            TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(classroomName);
            classroomEntity.setIsDelete(true);
            classroomDAO.merge(classroomEntity);
            Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
            for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                tblEquipmentEntity.setClassroomId(null);
                equipmentDAO.merge(tblEquipmentEntity);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TblEquipmentEntity> getEquipment(List<TblEquipmentEntity> equips, int classroomId, int position, boolean temp) {
        try {
            if (temp) {
                TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
                Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
                for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                    if (tblEquipmentEntity.getCategoryId() == position && tblEquipmentEntity.getName() == null) {
                        equips.add(tblEquipmentEntity);
                    }
                }
            } else {
                List<TblEquipmentEntity> availableEquipment = new ArrayList<TblEquipmentEntity>();
                availableEquipment = equipmentDAO.findAll();
                for (TblEquipmentEntity tblEquipmentEntity : availableEquipment) {
                    if (tblEquipmentEntity.getClassroomId() == null && tblEquipmentEntity.getName() != null) {
                        if (tblEquipmentEntity.getCategoryId() == position) {
                            equips.add(tblEquipmentEntity);
                        }
                    }
                }
            }

            return equips;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String updateInformation(int projector, int tivi, String airConditioning, int classroomId) {
        try {
            TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
            Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
            if (projector != 0) {
                executeUpdateInformation(tblEquipmentEntities, projector, 1);
            }
            if (tivi != 0) {
                executeUpdateInformation(tblEquipmentEntities, tivi, 2);
            }
            if (airConditioning != "") {
                String[] array = airConditioning.split("-");
                List<String> airs = new ArrayList<String>();
                for (int i = 0; i < array.length; i++) {
                    airs.add(array[i]);

                }
                Set<String> set = new HashSet<String>(airs);
                if (set.size() < airs.size()) {
                    return "redirect:/staff/classroom?ACTIVETAB=tab1";
                } else {
                    for (int i = 0; i < airs.size(); i++) {
                        if (!array[i].equals("0")) {
                            executeUpdateInformation(tblEquipmentEntities, Integer.parseInt(array[i]), 3);
                        }
                    }
                }
            }
            int projectors = classroomEntity.getTblRoomTypeByRoomTypeId().getProjector();
            int airconditioning = classroomEntity.getTblRoomTypeByRoomTypeId().getAirConditioning();
            int television = classroomEntity.getTblRoomTypeByRoomTypeId().getTelevision();
            int pro = 0;
            int air = 0;
            int te = 0;
            classroomEntity = classroomDAO.find(classroomId);
            List<TblEquipmentEntity> tblEquipmentEntities1 = classroomEntity.getTblEquipmentsById();
            for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities1) {
                if (tblEquipmentEntity.getName() != null && tblEquipmentEntity.getSerialNumber() != null) {
                    if (tblEquipmentEntity.getCategoryId() == 1) {
                        pro += 1;
                    }
                    if (tblEquipmentEntity.getCategoryId() == 2) {
                        te += 1;
                    } else {
                        air += 1;
                    }
                }
            }
            if (pro == projectors && te == television && air == airconditioning) {
                classroomEntity.setIsAllInformation(true);
                classroomDAO.merge(classroomEntity);
            } else {
                classroomEntity.setIsAllInformation(false);
                classroomDAO.merge(classroomEntity);
            }
            return "redirect:/staff/classroom?ACTIVETAB=tab1";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    //execute Update information
    public void executeUpdateInformation(Collection<TblEquipmentEntity> tblEquipmentEntities, int equipment, int category) {
        TblEquipmentEntity targetEquipmentEntity = equipmentDAO.find(equipment);
        for (TblEquipmentEntity currentEquipment : tblEquipmentEntities) {
            if (currentEquipment.getCategoryId() == category) {
                targetEquipmentEntity.setClassroomId(currentEquipment.getClassroomId());
                equipmentDAO.merge(targetEquipmentEntity);
                currentEquipment.setClassroomId(null);
                equipmentDAO.merge(currentEquipment);
            }
        }
    }


    public List<String> getAvailableRoom(int classroomId) {
        List<String> availableClassroom = new ArrayList<String>();
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
        List<TblScheduleEntity> tblScheduleEntityList = scheduleDAO.findAllScheduleInClassroom(classroomId);
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntityList) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity, tblClassroomEntities);
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
        if(!availableClassroom.isEmpty()){
            TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);

            availableClassroom = Utils.sortClassroom(availableClassroom, classroomEntity.getName());
            availableClassroom.remove(classroomEntity.getName());
        }
        return availableClassroom;
    }

    public String changeRoom(String currentRoom, String changeRoom) {
        TblClassroomEntity currentClassroom = classroomDAO.getClassroomByName(currentRoom);
        TblClassroomEntity changeClassroom = classroomDAO.getClassroomByName(changeRoom);

        LocalTime localTime =  new LocalTime();
        LocalTime noon = new LocalTime("12:00:00");
        List<TblScheduleEntity> currentSchedule;
        if(localTime.isBefore(noon)){
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroom.getId(), "Morning");
        }else{
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(changeClassroom.getId(), "Noon");
        }

        if(currentSchedule != null && currentSchedule.size() > 0) {
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
                listTime.add(schedule.getTimeFrom().getHours() + "h" + schedule.getTimeFrom().getMinutes());

                schedule.setIsActive(false);
                schedule.setNote("Đổi sang phòng "+changeRoom);
                scheduleDAO.merge(schedule);

                TblScheduleEntity newSchedule = new TblScheduleEntity(schedule.getUsername(), changeClassroom.getId(),
                        schedule.getNumberOfStudents(), message, schedule.getTimeFrom(),
                        schedule.getSlots(), schedule.getDate(), true, schedule.getScheduleConfigId());
                scheduleDAO.persist(newSchedule);
            }

            TblNotificationEntity notify = notificationDAO.getNotifyOfRoom(currentClassroom.getId(), Enumerable.MessageType.CHANGEROOM.getValue());
            if (notify == null) {
                notify = new TblNotificationEntity(currentClassroom.getId(), message, "/giang-vien/lich-day", Enumerable.MessageType.CHANGEROOM.getValue());
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
}
