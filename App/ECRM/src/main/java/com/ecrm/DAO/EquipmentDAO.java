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

    public TblEquipmentEntity findEquipmentHavePosition(int roomId, int category, String position, String serialNumber);

    public List<TblEquipmentEntity> getActiveEquipments(int roomId);

    public TblEquipmentEntity getEquipmentBySerialNumber(int roomId, String serialNumber);

    public String getDamagedEquipmentNames(int reportId);

    public List<TblEquipmentEntity> getDamagedEquipments(int roomId);

    public List<TblEquipmentEntity> getDamagedEquipmentsByCategory(int roomId, int category);
}
