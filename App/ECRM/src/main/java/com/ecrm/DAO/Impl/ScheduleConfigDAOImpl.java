package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ScheduleConfigDAO;
import com.ecrm.Entity.TblScheduleConfigEntity;
import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;
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

    @Transactional
    public void removeTblScheduleConfig(int id){
        TblScheduleConfigEntity o1 = entityManager.find(TblScheduleConfigEntity.class, id);
        Collection<TblScheduleEntity> tblScheduleEntity = o1.getTblSchedulesById();
        for(TblScheduleEntity s1 : tblScheduleEntity){
            TblScheduleEntity s2 = entityManager.merge(s1);
            entityManager.remove(s2);
        }
        TblScheduleConfigEntity o2 = entityManager.merge(o1);
        entityManager.remove(o2);
    }
}
