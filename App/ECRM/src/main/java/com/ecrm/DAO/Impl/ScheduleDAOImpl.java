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
                "AND current_time() >= s.timeFrom " +
                "AND current_time() < DATEADD(MINUTE, ((s.slots* 105) - 15), s.timeFrom) " +
                "AND current_date() = s.date");
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
                "AND current_date() = s.date " +
                "AND s.isActive = true");
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

    @Override
    public List<TblScheduleEntity> getSchedulesFinishOfUser(String username) {
        Query query = entityManager.createQuery("SELECT s " +
                "FROM TblScheduleEntity s " +
                "WHERE s.username = :username " +
                "AND CURTIME() >= s.timeFrom " +
                "AND CURDATE() = s.date " +
                "AND s.isActive = true " +
                "GROUP BY s.classroomId");
        query.setParameter("username", username);

        List list = query.getResultList();
        List<TblScheduleEntity> result = new ArrayList<TblScheduleEntity>();
        if(!list.isEmpty()){
            for(Iterator i = list.iterator(); i.hasNext();) {
                TblScheduleEntity schedule = (TblScheduleEntity) i.next();
                result.add(schedule);
            }
        }

        return result;
    }

    @Override
    public List<TblScheduleEntity> getScheduleNoFinishOfRoom(int room) {
        Query query = entityManager.createQuery("SELECT s FROM TblScheduleEntity s WHERE s.classroomId = :classroomId " +
                "AND CURDATE() = s.date AND s.isActive = true AND ADDTIME(s.timeFrom, '01:30:00') >= CURTIME()");
        query.setParameter("classroomId", room);

        List list = query.getResultList();
        List<TblScheduleEntity> result = new ArrayList<TblScheduleEntity>();
        if(!list.isEmpty()){
            for(Iterator i = list.iterator(); i.hasNext();) {
                TblScheduleEntity schedule = (TblScheduleEntity) i.next();
                result.add(schedule);
            }
        }

        return result;
    }

    public List<TblScheduleEntity> findScheduleWithDate(String username, String date, String timeFrom){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where s.username = :username and" +
                " s.date = Date(:date) and s.timeFrom = Time(:timeFrom)");
        q.setParameter("username", username);
        q.setParameter("date", date);
        q.setParameter("timeFrom", timeFrom);
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> findSpecificSchedule(String date, String timeFrom, int classroomId){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where" +
                " s.date = Date(:date) and s.timeFrom = Time(:timeFrom) and s.isActive = true and s.classroomId =:classroomId");
        q.setParameter("date", date);
        q.setParameter("timeFrom", timeFrom);
        q.setParameter("classroomId", classroomId);
        List<TblScheduleEntity> tblScheduleEntities =  q.getResultList();
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> findAllScheduleInClassroom(int classroomId){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where s.classroomId = :classroomId and " +
                " current_date() = s.date  " +
                " and s.isActive= true");
        q.setParameter("classroomId", classroomId);
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> findAllScheduleToday(){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where " +
                " current_date() = s.date  " +
                "and s.isActive= true");
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> advanceSearch(String dateFrom, String dateTo, String classroomId, String username){
        Query q = entityManager.createQuery("select s from TblScheduleEntity s where " +
                "s.date between Date(:dateFrom) and Date(:dateTo) and s.isActive=true and s.username like :username and " +
                "CONVERT(s.classroomId, CHAR(16)) like :classroomId");
        q.setParameter("dateFrom", dateFrom);
        q.setParameter("dateTo", dateTo);
        q.setParameter("username", "%"+username+"%");
        q.setParameter("classroomId", "%"+classroomId+"%");
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        
        return tblScheduleEntities;
    }

    public List<TblScheduleEntity> findAllScheduleMoreThan15MLeft(int classroomId){
        Query q = entityManager.createQuery("SELECT s from TblScheduleEntity s where s.classroomId = :classroomId and " +
                " current_date() = s.date  " +
                " and s.isActive= true and SUBTIME(ADDTIME(s.timeFrom, '01:30:00'),CURTIME())>'00:15:00'");
        q.setParameter("classroomId", classroomId);
        List<TblScheduleEntity> tblScheduleEntities = q.getResultList();
        return tblScheduleEntities;
    }


    @Override
    public int findScheduleAfterCurrentTime(int classroomId) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM TblScheduleEntity  s WHERE s.classroomId = :classroomId " +
                "AND s.timeFrom > current_time() AND s.date = current_date() AND s.isActive=true ");
        query.setParameter("classroomId", classroomId);
        Integer result = (Integer) query.getSingleResult();
        return result;
    }
}
