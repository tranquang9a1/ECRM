package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 6/8/2015.
 */
@Repository
public class ScheduleDAOImpl extends BaseDAO<TblScheduleEntity, Integer> implements ScheduleDAO{

    public ScheduleDAOImpl() {
        super(TblScheduleEntity.class);
    }
}
