package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.CategoryDAO;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 6/14/2015.
 */
@Repository
public class CategoryDAOImpl extends BaseDAO<TblEquipmentCategoryEntity,Integer> implements CategoryDAO{

    public CategoryDAOImpl() {
        super(TblEquipmentCategoryEntity.class);
    }
}
