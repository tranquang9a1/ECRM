package com.ecrm.Service;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DTO.PriorityDTO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentQuantityEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Utils.SmsUtils;
import com.ecrm.Utils.Utils;
import com.twilio.sdk.TwilioRestException;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Htang on 7/24/2015.
 */
@Service
public class ChangeRoomService {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    GCMService gcmService;
    @Autowired
    ScheduleDAOImpl scheduleDAO;

    public String changingRoom(List<TblScheduleEntity> currentSchedule, List<TblClassroomEntity> validClassrooms,
                             String classroomName, TblClassroomEntity currentClassroom) throws TwilioRestException {
        String changeRoom ="";
        TblClassroomEntity changeClassroomEntity = null;
        List<String> availableClassroom = new ArrayList<String>();
        System.out.println("Find available classroom!");
        for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
            List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity,validClassrooms);
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
            System.out.println("Total: " + availableClassroom.size() + " classroom!");
            if (classroomName.trim().length() > 0 && availableClassroom.contains(classroomName)) {
                changeClassroomEntity = classroomDAO.getClassroomByName(classroomName);
                changeRoom = classroomName;
            } else {
                availableClassroom = Utils.sortClassroom(availableClassroom, currentClassroom.getName());
                availableClassroom.remove(currentClassroom.getName());
                availableClassroom = sortByRoomType(availableClassroom, currentClassroom.getName());
                changeClassroomEntity = classroomDAO.getClassroomByName(availableClassroom.get(0));
                changeRoom = availableClassroom.get(0);
            }
        } else {
            System.out.println("There are no classroom for all schedule!");
            System.out.println("Find classroom for each schedule!");
            for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
                validClassrooms = classroomDAO.getValidClassroom();
                List<String> classroom = Utils.getAvailableRoom(tblScheduleEntity,  validClassrooms);
                if (!classroom.isEmpty()) {
                    changeRoom = classroom.get(0);
                    System.out.println("Change room:");
                    TblClassroomEntity newClassroom = classroomDAO.getClassroomByName(classroom.get(0));
                    String message = changeRoom(tblScheduleEntity, newClassroom);
                    SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                    gcmService.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
                }
            }
        }
        if (changeClassroomEntity != null) {
            System.out.println("Start changing room!");
            for (TblScheduleEntity tblScheduleEntity : currentSchedule) {
                String message = changeRoom(tblScheduleEntity, changeClassroomEntity);
                SmsUtils.sendMessage(tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getPhone(), message);
                gcmService.sendNotification(message, tblScheduleEntity.getTblUserByUserId().getTblUserInfoByUsername().getDeviceId());
            }
            System.out.println("End changing room!");
        }
        return changeRoom;
    }


    public String changeRoom(TblScheduleEntity tblScheduleEntity, TblClassroomEntity changeClassroomEntity) {
        tblScheduleEntity.setIsActive(false);
        tblScheduleEntity.setNote("Đổi sang phòng " + changeClassroomEntity.getName());
        scheduleDAO.merge(tblScheduleEntity);
        TblScheduleEntity newSchedule = new TblScheduleEntity(tblScheduleEntity.getUsername(), changeClassroomEntity.getId(),
                tblScheduleEntity.getNumberOfStudents(), "Thay đổi phòng từ phòng " + tblScheduleEntity.getTblClassroomByClassroomId().getName()
                + " sang phòng " + changeClassroomEntity.getName(),  tblScheduleEntity.getDate(), true, tblScheduleEntity.getScheduleConfigId());
        String message = "Đã đổi phòng cho giáo viên " + tblScheduleEntity.getUsername() + " từ phòng: " +
                tblScheduleEntity.getTblClassroomByClassroomId().getName() + " sang phòng: " + changeClassroomEntity.getName() + "vào lúc "
                + tblScheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom() + " ngày " + tblScheduleEntity.getDate();
        scheduleDAO.persist(newSchedule);
        System.out.println(message);
        return message;
    }

    public List<String> getAvailableClassroom(int classroomId) {
        TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
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
        if (!availableClassroom.isEmpty()) {
            availableClassroom = Utils.sortClassroom(availableClassroom, classroomEntity.getName());
            availableClassroom.remove(classroomEntity.getName());
            availableClassroom = sortByRoomType(availableClassroom, classroomEntity.getName());
        }
        return availableClassroom;
    }

    public List<TblScheduleEntity> getScheduleByDayTime(int currentClassroomId){
        LocalTime localTime =  new LocalTime();
        LocalTime noon = new LocalTime("12:00:00");
        List<TblScheduleEntity> currentSchedule = new ArrayList<TblScheduleEntity>();
        if(localTime.isBefore(noon)){
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroomId, "Morning");
        }else{
            currentSchedule = scheduleDAO.findAllScheduleMoreThan15MLeft(currentClassroomId, "Noon");
        }

        return currentSchedule;
    }

    public List<String> sortByRoomType(List<String> classrooms, String room){
        List<TblClassroomEntity> tblClassroomEntities = new ArrayList<>();
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(room);
        List<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities =
                classroomEntity.getTblRoomTypeByRoomTypeId().getTblEquipmentQuantityById();
        for(String r: classrooms){
            TblClassroomEntity c = classroomDAO.getClassroomByName(r);
            tblClassroomEntities.add(c);
        }
        List<PriorityDTO> priorityDTOs = new ArrayList<>();
        for(TblClassroomEntity c: tblClassroomEntities){
            int mark = Utils.markPriority(tblEquipmentQuantityEntities,
                    c.getTblRoomTypeByRoomTypeId().getTblEquipmentQuantityById());
            PriorityDTO priorityDTO = new PriorityDTO(mark, c.getName());
            priorityDTOs.add(priorityDTO);
        }
        Collections.sort(priorityDTOs, new Comparator<PriorityDTO>() {
            @Override
            public int compare(PriorityDTO o1, PriorityDTO o2) {
                return o2.getMark().compareTo(o1.getMark());
            }
        });
        List<String> result = new ArrayList<>();
        for(int j = 0; j<priorityDTOs.size(); j++){
            result.add(priorityDTOs.get(j).getName());
        }
        return result;
    }
}
