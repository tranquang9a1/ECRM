package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentCategoryDAO;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblEquipmentQuantityEntity;
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
    public String getCategoriesInRoom(int room) {

        Query query = entityManager.createQuery("SELECT c.name " +
                "FROM TblEquipmentCategoryEntity c " +
                "WHERE c.id IN (SELECT e.categoryId " +
                    "FROM TblEquipmentEntity e " +
                    "WHERE e.classroomId = :roomId " +
                    "AND e.status = false " +
                    "GROUP BY e.categoryId)");
        query.setParameter("roomId", room);

        List queryResult = query.getResultList();
        String result = "";
        if (!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext(); ) {
                result += (String)i.next() + ", ";
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

    @Override
    public List<TblEquipmentCategoryEntity> getAllEquipment() {
        List<TblEquipmentCategoryEntity> result = new ArrayList<TblEquipmentCategoryEntity>();
        Query query = entityManager.createQuery("SELECT e FROM TblEquipmentCategoryEntity  e");
        result = query.getResultList();
        return result;

    }

}
