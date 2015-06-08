package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.Entity.TblEquipmentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Htang on 6/1/2015.
 */
@Repository
public class EquipmentDAOImpl extends BaseDAO<TblEquipmentEntity, Integer> implements EquipmentDAO{
    public EquipmentDAOImpl() {
        super(TblEquipmentEntity.class);
    }

    public TblEquipmentEntity findEquipmentHavePosition(int classroomId, String position){
        TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity();
        Query q = entityManager.createQuery("SELECT e from TblEquipmentEntity e where e.classroomId = :classroomId and " +
                "e.position = :position");
        q.setParameter("classroomId", classroomId);
        q.setParameter("position", position);
        tblEquipmentEntity = (TblEquipmentEntity) q.getSingleResult();
        return tblEquipmentEntity;
    }
}
