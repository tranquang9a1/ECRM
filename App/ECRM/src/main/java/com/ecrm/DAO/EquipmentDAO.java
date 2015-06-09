package com.ecrm.DAO;

import com.ecrm.Entity.TblEquipmentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Htang on 6/1/2015.
 */
@Repository
public interface EquipmentDAO {

    public List<TblEquipmentEntity> getEquipmentsInClassroom(int roomId);

    public TblEquipmentEntity findEquipmentHavePosition(int roomId, String position);

    public boolean insertDamagedEquipment(int classroomId, String position);
}
