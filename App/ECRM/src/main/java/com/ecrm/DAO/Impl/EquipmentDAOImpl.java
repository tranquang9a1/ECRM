package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentDAO;
import com.ecrm.Entity.TblEquipmentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Htang on 6/1/2015.
 */
@Repository
public class EquipmentDAOImpl extends BaseDAO<TblEquipmentEntity, Integer> implements EquipmentDAO {

    public EquipmentDAOImpl() {
        super(TblEquipmentEntity.class);
    }

    public List<TblEquipmentEntity> findAllE(){
        Query q = entityManager.createQuery("select e from TblEquipmentEntity e");
        List<TblEquipmentEntity> tblEquipmentEntities = q.getResultList();
        return tblEquipmentEntities;
    }

    @Override
    public List<TblEquipmentEntity> getEquipmentsInClassroom(int roomId) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentEntity c " +
                "WHERE c.classroomId = :roomId");
        query.setParameter("roomId", roomId);

        List queryResult = query.getResultList();
        List<TblEquipmentEntity> result = new ArrayList<TblEquipmentEntity>();
        if (!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext(); ) {
                TblEquipmentEntity item = (TblEquipmentEntity) i.next();
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public TblEquipmentEntity findEquipmentHavePosition(int roomId, String position) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentEntity c " +
                "WHERE c.classroomId = :roomId " +
                "AND c.position = :position");
        query.setParameter("roomId", roomId);
        query.setParameter("position", position);

        List queryResult = query.getResultList();
        if (!queryResult.isEmpty()) {
            return (TblEquipmentEntity) queryResult.get(0);
        }

        return null;
    }

    @Override
    public List<TblEquipmentEntity> getActiveEquipments(int roomId) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentEntity c " +
                "WHERE c.classroomId = :roomId " +
                "AND c.position != null");
        query.setParameter("roomId", roomId);

        List queryResult = query.getResultList();
        List<TblEquipmentEntity> result = new ArrayList<TblEquipmentEntity>();
        if (!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext(); ) {
                TblEquipmentEntity item = (TblEquipmentEntity) i.next();
                result.add(item);
            }
        }

        return result;
    }



}
