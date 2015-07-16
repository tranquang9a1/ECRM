package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ScheduleConfigDAO;
import com.ecrm.Entity.TblScheduleConfigEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Htang on 7/10/2015.
 */
@Repository
public class ScheduleConfigDAOImpl extends BaseDAO<TblScheduleConfigEntity,Integer> implements ScheduleConfigDAO{

    public ScheduleConfigDAOImpl() {
        super(TblScheduleConfigEntity.class);
    }

    public List<TblScheduleConfigEntity> findScheduleConfigByTFTT(String timeFrom, String timeTo){
        Query query = entityManager.createQuery("SELECT s from TblScheduleConfigEntity s where " +
                "s.timeFrom =Time(:timeFrom) and s.timeTo=TIME(:timeTo)");
        query.setParameter("timeFrom",timeFrom);
        query.setParameter("timeTo",timeTo);
        List<TblScheduleConfigEntity>tblScheduleConfigEntities = query.getResultList();
        return tblScheduleConfigEntities;
    }

    public List<TblScheduleConfigEntity> findScheduleConfigBySlot(int slot){
        Query query = entityManager.createQuery("SELECT s from TblScheduleConfigEntity s where " +
                "s.slot = :slot");
        query.setParameter("slot", slot);
        List<TblScheduleConfigEntity>tblScheduleConfigEntities = query.getResultList();
        return tblScheduleConfigEntities;
    }

    public List<TblScheduleConfigEntity> findById(int id){
        Query query = entityManager.createQuery("SELECT s from TblScheduleConfigEntity s where " +
                "s.id = :id");
        query.setParameter("id", id);
        List<TblScheduleConfigEntity>tblScheduleConfigEntities = query.getResultList();
        return tblScheduleConfigEntities;
    }
}
