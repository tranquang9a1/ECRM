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

    /*@Override
    public boolean insertDamagedEquipment(int classroomId, String position) {
        TblEquipmentEntity e = findEquipmentHavePosition(classroomId, position);
        if (e == null) {
            if (position.equals("[1]")) {
                e = new TblEquipmentEntity(1, classroomId, position, "Damaged");
            }
            if (position.equals("[2]")) {
                e = new TblEquipmentEntity(2, classroomId, position, "Damaged");
            }
            if (position.equals("[3]")) {
                e = new TblEquipmentEntity(3, classroomId, position, "Damaged");
            }
            if (position.equals("[4]")) {
                e = new TblEquipmentEntity(4, classroomId, position, "Damaged");
            }
            if (position.equals("[5]")) {
                e = new TblEquipmentEntity(5, classroomId, position, "Damaged");
            }
            if (position.equals("[6]")) {
                e = new TblEquipmentEntity(6, classroomId, position, "Damaged");
            }
            if (position.length() == 5) {
                e = new TblEquipmentEntity(7, classroomId, position, "Damaged");
            } else {
                e = new TblEquipmentEntity(8, classroomId, position, "Damaged");
            }
        }
        return false;
    }*/
}
