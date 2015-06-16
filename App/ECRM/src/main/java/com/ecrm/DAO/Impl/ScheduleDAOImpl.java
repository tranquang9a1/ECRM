package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ScheduleDAO;
import com.ecrm.Entity.TblClassroomEntity;
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
    public TblScheduleEntity getScheduleInTime(String username, int room) {

        Query query = entityManager.createQuery("SELECT s " +
                "FROM TblScheduleEntity s " +
                "WHERE (s.username = :username OR s.classroomId = :room)" +
                "AND CONVERT (Time, CURRENT_TIMESTAMP) >= s.timeFrom " +
                "AND CONVERT (Time, CURRENT_TIMESTAMP) < DATEADD(MINUTE, ((s.slots* 105) - 15), s.timeFrom) " +
                "AND CONVERT (Date, CURRENT_TIMESTAMP) = s.date");
        query.setParameter("username", username);
        query.setParameter("room", room);

        List list = query.getResultList();
        if(!list.isEmpty()){
            TblScheduleEntity schedule = (TblScheduleEntity) list.get(0);
            return schedule;
        }

        return null;
    }

    @Override
    public List<TblScheduleEntity> getSchedulesOfUser(String username) {
        Query query = entityManager.createQuery("SELECT s " +
                "FROM TblScheduleEntity s " +
                "WHERE s.username = :username " +
                "AND CONVERT (Date, CURRENT_TIMESTAMP) = s.date");
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

    public List<TblScheduleEntity> findSpecificSchedule(java.util.Date date, String timeFrom){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where" +
                " s.date = :date and s.timeFrom = CONVERT (time, :timeFrom) and s.isActive = true");
        q.setParameter("date", date);
        q.setParameter("timeFrom", timeFrom);
        List<TblScheduleEntity> tblScheduleEntities =  q.getResultList();
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> findAllScheduleInClassroom(int classroomId){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where s.classroomId = :classroomId and" +
                " CONVERT (Date, CURRENT_TIMESTAMP) = s.date CONVERT (Time, CURRENT_TIMESTAMP) < DATEADD(MINUTE, ((s.slots* 105) - 15), s.timeFrom) ");
        q.setParameter("classroomId", classroomId);
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }
}
