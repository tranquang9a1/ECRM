package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/7/2015.
 */
@Repository
public class ScheduleDAOImpl extends BaseDAO<TblScheduleEntity, Integer> implements ScheduleDAO {

    public ScheduleDAOImpl(){
        super(TblScheduleEntity.class);
    }

    @Override
    public int getClassroomIdByUsername(String username) {

        Query query = entityManager.createQuery("SELECT s " +
                "FROM TblScheduleEntity s " +
                "WHERE s.username = :username " +
                "AND CONVERT (time, CURRENT_TIMESTAMP) >= s.timeFrom " +
                "AND CONVERT (time, CURRENT_TIMESTAMP) <= DATEADD(MINUTE, (s.slots* 105), s.timeFrom) " +
                "AND CONVERT (date, CURRENT_TIMESTAMP) >= s.dateFrom " +
                "AND CONVERT (date, CURRENT_TIMESTAMP) <= s.dateTo");
        query.setParameter("username", username);

        List list = query.getResultList();
        if(!list.isEmpty()){
            TblScheduleEntity schedule = (TblScheduleEntity) list.get(0);
            return schedule.getClassroomId();
        }

        return 0;
    }

    @Override
    public List<TblScheduleEntity> getScheduleOfUser(String username) {
        Query query = entityManager.createQuery("SELECT s " +
                "FROM TblScheduleEntity s " +
                "WHERE s.username = :username " +
                "AND CONVERT (date, CURRENT_TIMESTAMP) >= s.dateFrom " +
                "AND CONVERT (date, CURRENT_TIMESTAMP) <= s.dateTo");
        query.setParameter("username", username);

        List queryResult = query.getResultList();
        List<TblScheduleEntity> result = new ArrayList<TblScheduleEntity>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();){
                TblScheduleEntity item = (TblScheduleEntity)i.next();
                result.add(item);
            }
        }

        return result;
    }

    public List<TblScheduleEntity> findScheduleWithDate(String username, java.util.Date date, String timeFrom){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where s.username = :username and" +
                " s.date = :date and s.timeFrom = CONVERT (time, :timeFrom)");
        q.setParameter("username", username);
        q.setParameter("date", date);
        q.setParameter("timeFrom", timeFrom);
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }

    public TblScheduleEntity findSpecificSchedule(java.util.Date date, String timeFrom, int classroomId){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where" +
                " s.date = :date and s.timeFrom = CONVERT (time, :timeFrom) and s.classroomId = :classroomId");
        q.setParameter("date", date);
        q.setParameter("timeFrom", timeFrom);
        q.setParameter("classroomId", classroomId);
        TblScheduleEntity tblScheduleEntity = (TblScheduleEntity) q.getSingleResult();
        return tblScheduleEntity;
    }
}
