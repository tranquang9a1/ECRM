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
        List<TblEquipmentEntity> tblEquipmentEntities =  q.getResultList();
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
    public TblEquipmentEntity findEquipmentHavePosition(int roomId, int category, String position) {

        Query query;

        if(position == null) {
            query = entityManager.createQuery("SELECT c " +
                    "FROM TblEquipmentEntity c " +
                    "WHERE c.classroomId = :roomId " +
                    "AND c.categoryId = :category " +
                    "AND c.position IS NULL");

        } else {
            query = entityManager.createQuery("SELECT c " +
                    "FROM TblEquipmentEntity c " +
                    "WHERE c.classroomId = :roomId " +
                    "AND c.categoryId = :category " +
                    "AND c.position = :position");
            query.setParameter("position", position);
        }

        query.setParameter("roomId", roomId);
        query.setParameter("category", category);

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

    @Override
    public List<TblEquipmentEntity> getDamagedEquipments(int roomId) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentEntity c " +
                "WHERE c.classroomId = :roomId " +
                "AND c.status = false");
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
    public List<TblEquipmentEntity> getDamagedEquipmentsByCategory(int roomId, int category) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentEntity c " +
                "WHERE c.classroomId = :roomId " +
                "AND c.status = false " +
                "And c.categoryId = :category");
        query.setParameter("roomId", roomId);
        query.setParameter("category", category);

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

    public List<TblEquipmentEntity> getProjector(int classroomId){
        Query q = entityManager.createQuery("select e from TblEquipmentEntity e where e.categoryId = 1 and e.classroomId =:classroomId");
        q.setParameter("classroomId", classroomId);
        List<TblEquipmentEntity> tblEquipmentEntities = q.getResultList();
        return tblEquipmentEntities;
    }
}
