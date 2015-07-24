package com.ecrm.Service;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DTO.ClassDTO;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblRoomTypeEntity;
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
    private ClassroomDAOImpl classroomDAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;


    public ClassDTO getClassroom(int classId) {
        TblClassroomEntity entity = classroomDAO.getClassroomById(classId);
        ClassDTO result = new ClassDTO(entity.getId(), entity.getName(), entity.getDamagedLevel());
        return result;
    }

    public TblClassroomEntity getClassroomByName(String name) {
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(name);
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
                            new Timestamp(date.getTime()), false, true, 0);
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
                targetEquipmentEntity.setPosition(currentEquipment.getPosition());
                equipmentDAO.merge(targetEquipmentEntity);
                equipmentDAO.remove(currentEquipment);
            }
        }
    }
}
