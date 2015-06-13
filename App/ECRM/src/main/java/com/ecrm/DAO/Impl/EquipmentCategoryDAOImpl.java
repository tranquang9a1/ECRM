package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentCategoryDAO;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class EquipmentCategoryDAOImpl extends BaseDAO<TblEquipmentCategoryEntity, Integer> implements EquipmentCategoryDAO {
    public EquipmentCategoryDAOImpl(){
        super(TblEquipmentCategoryEntity.class);
    }

    @Override
    public List<TblEquipmentCategoryEntity> getCategoriesInRoom(int room) {

        Query query = entityManager.createQuery("SELECT c " +
                "FROM TblEquipmentCategoryEntity c " +
                "WHERE c.id IN (SELECT e.categoryId " +
                "FROM TblEquipmentEntity e " +
                "WHERE e.classroomId = :roomId " +
                "AND e.status = true " +
                "GROUP BY e.categoryId)");
        query.setParameter("roomId", room);

        List queryResult = query.getResultList();
        List<TblEquipmentCategoryEntity> result = new ArrayList<TblEquipmentCategoryEntity>();
        if (!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext(); ) {
                TblEquipmentCategoryEntity item = (TblEquipmentCategoryEntity) i.next();
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public int findEquipmentId(String name) {
        Query query = entityManager.createQuery("Select e from TblEquipmentCategoryEntity e WHERE e.name = :name");
        query.setParameter("name", name);
        TblEquipmentCategoryEntity entity = (TblEquipmentCategoryEntity) query.getSingleResult();
        return entity.getId();
    }

}
