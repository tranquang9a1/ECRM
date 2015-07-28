package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentQuantityDAO;
import com.ecrm.Entity.TblEquipmentQuantityEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 7/29/2015.
 */
@Repository
public class EquipmentQuantityDAOImpl extends BaseDAO<TblEquipmentQuantityEntity, Integer> implements EquipmentQuantityDAO{
    public EquipmentQuantityDAOImpl() {
        super(TblEquipmentQuantityEntity.class);
    }
}
