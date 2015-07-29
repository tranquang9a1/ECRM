package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.CategoryDAO;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Htang on 6/14/2015.
 */
@Repository
public class CategoryDAOImpl extends BaseDAO<TblEquipmentCategoryEntity,Integer> implements CategoryDAO{

    public CategoryDAOImpl() {
        super(TblEquipmentCategoryEntity.class);
    }

    public List<TblEquipmentCategoryEntity> getCategoryByName(String name){
        Query query = entityManager.createQuery("select c from TblEquipmentCategoryEntity  c where " +
                "c.name =:name and c.isDelete = false");
        query.setParameter("name", name);
        List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = query.getResultList();
        return tblEquipmentCategoryEntities;
    }
}
