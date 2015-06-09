package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.EquipmentCategoryDAO;
import com.ecrm.DAO.ReportDAO;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblReportEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class EquipmentCategoryDAOImpl extends BaseDAO<TblEquipmentCategoryEntity, Integer> implements EquipmentCategoryDAO {
    public EquipmentCategoryDAOImpl(){
        super(TblEquipmentCategoryEntity.class);
    }
}
