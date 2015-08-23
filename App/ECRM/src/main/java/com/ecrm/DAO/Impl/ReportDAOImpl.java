package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ReportDAO;
import com.ecrm.DTO.StatisticDTO;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import com.ecrm.Utils.Enumerable.ReportStatus;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class ReportDAOImpl extends BaseDAO<TblReportEntity, Integer> implements ReportDAO {

    public ReportDAOImpl(){
        super(TblReportEntity.class);
    }

    @Override
    public List<Integer> getDamagedRoom() {

        Query query = entityManager.createQuery("SELECT r.classRoomId " +
                "FROM TblReportEntity r " +
                "WHERE r.status = :status1 " +
                "OR r.status = :status2 " +
                "GROUP BY r.classRoomId");
        query.setParameter("status1", ReportStatus.NEW.getValue());
        query.setParameter("status2", ReportStatus.GOING.getValue());

        List queryResult = query.getResultList();
        List<Integer> result = new ArrayList<Integer>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();) {
                Integer room = (Integer) i.next();
                result.add(room);
            }
        }

        return result;
    }

    @Override
    public List<TblReportEntity> getLiveReportsInRoom(int room) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblReportEntity r " +
                "WHERE r.classRoomId = :room " +
                "AND (r.status = :status1 " +
                "OR r.status = :status2)");
        query.setParameter("room", room);
        query.setParameter("status1", ReportStatus.NEW.getValue());
        query.setParameter("status2", ReportStatus.GOING.getValue());

        List queryResult = query.getResultList();
        List<TblReportEntity> result = new ArrayList<TblReportEntity>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();) {
                TblReportEntity report = (TblReportEntity) i.next();
                result.add(report);
            }
        }

        return result;
    }

    @Override
    public String getReportersInRoom(int room) {

        Query query = entityManager.createQuery("SELECT i " +
                "FROM TblUserEntity u, TblUserInfoEntity i " +
                "WHERE u.username = i.username " +
                "AND u.username IN (SELECT r.username FROM TblReportEntity r " +
                                    "WHERE r.classRoomId = :room " +
                                    "AND (r.status = :status1 OR r.status = :status2) " +
                                    "GROUP BY r.username)");
        query.setParameter("room", room);
        query.setParameter("status1", ReportStatus.NEW.getValue());
        query.setParameter("status2", ReportStatus.GOING.getValue());

        List queryResult = query.getResultList();
        String result = "";
        if(!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext();) {
                TblUserInfoEntity user = (TblUserInfoEntity) i.next();
                result += user.getFullName() + ", ";
            }
        }

        return result.substring(0, result.length()-2);
    }

    @Override
    public TblReportEntity getReportOfUsernameInDay(String username, int roomId) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username = :username AND DATE(r.createTime) = CURDATE() AND r.classRoomId = :room");
        query.setParameter("username", username);
        query.setParameter("room", roomId);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return (TblReportEntity) queryResult.get(0);
        }

        return null;
    }

    @Override
    public TblReportEntity getReportNewest(int room) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblReportEntity r " +
                "WHERE r.classRoomId = :room " +
                "AND (r.status = :status1 OR r.status = :status2) " +
                "ORDER BY r.createTime DESC");
        query.setParameter("room", room);
        query.setParameter("status1", ReportStatus.NEW.getValue());
        query.setParameter("status2", ReportStatus.GOING.getValue());

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()){
            return (TblReportEntity)queryResult.get(0);
        }

        return null;
    }

    @Override
    public List<TblReportEntity> getReportByUserId(String username, int offset, int limit) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username = :username ORDER BY r.createTime DESC");
        query.setParameter("username", username);
        query.setMaxResults(limit);
        query.setFirstResult(offset);

        return query.getResultList();
    }

    @Override
    public List<TblReportEntity> getPagingReportByUser(String username, int page, int size) {

        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username = :username ORDER BY r.createTime DESC");
        query.setParameter("username", username);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public int getNumberOfUserReport(String username) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username = :username");
        query.setParameter("username", username);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult.size();
        }

        return 0;
    }

    @Override
    public int getNumberOfFinishReport() {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.status = :status");
        query.setParameter("status", ReportStatus.FINISH.getValue());

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult.size();
        }

        return 0;
    }

    @Override
    public List<TblReportEntity> getFinishReport(int limit, int offset) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.status = :status ORDER BY r.id DESC");
        query.setParameter("status", ReportStatus.FINISH.getValue());
        query.setMaxResults(limit);
        query.setFirstResult(offset);

        List queryResult = query.getResultList();
        List<TblReportEntity> result = new ArrayList<TblReportEntity>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();){
                result.add((TblReportEntity)i.next());
            }
        }

        return result;
    }

    @Override
    public List<TblReportEntity> getAllReport(int limit, int offset) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r");
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public boolean createReport() {
        return true;
    }

    @Override
    public List<Integer> getReportByClassId(String status, int offset, int limit) {
        Query query  = entityManager.createQuery("Select u.classRoomId from TblReportEntity u where u.status = :statusnew " +
                "group by u.classRoomId ");
        if (status.equalsIgnoreCase("new")) {
            query.setParameter("statusnew", ReportStatus.NEW.getValue());
        } else if (status.equalsIgnoreCase("going")) {
            query.setParameter("statusnew", ReportStatus.GOING.getValue());
        } else if (status.equalsIgnoreCase("finish")) {
            query.setParameter("statusnew", ReportStatus.FINISH.getValue());
        } else {
            query.setParameter("statusnew", ReportStatus.REMOVE.getValue());
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public List<Integer> getClassFromStatus(int offset, int limit) {
        Query query  = entityManager.createQuery("Select u.classRoomId from TblReportEntity u where (u.status = :statusnew " +
                "OR u.status = :statusgoing)  group by u.classRoomId ");
        query.setParameter("statusnew", ReportStatus.NEW.getValue());
        query.setParameter("statusgoing", ReportStatus.GOING.getValue());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public boolean resolveReport(int reportId, int equipmentId, String solution) {
        Query query = entityManager.createQuery("Update TblReportDetailEntity u set u.status = true, u.solution =:solution " +
                "where u.reportId = :reportId and u.equipmentId = :equipmentId ");
        query.setParameter("solution", solution);
        query.setParameter("reportId", reportId);
        query.setParameter("equipmentId", equipmentId);

        Query query1 = entityManager.createQuery("Select u from TblReportDetailEntity u where u.reportId = :reportId and u.status = true ");
        query1.setParameter("reportId", reportId);

        if (query1.getResultList().size() == 0) {
            Query query2 = entityManager.createQuery("Update TblReportEntity u set u.status = true where u.reportId = :reportId");
            query2.setParameter("reportId", reportId);
            query2.executeUpdate();
        }

        int rows = query.executeUpdate();
        return rows > 0;
    }

    @Transactional
    @Override
    public boolean updateDamageLevel(int damageLevel, int reportId) {
        Query query = entityManager.createQuery("Update TblReportEntity r set r.damagedLevel = :damageLevel where r.id = :reportId");
        query.setParameter("damageLevel", damageLevel);
        query.setParameter("reportId", reportId);

        int rows = query.executeUpdate();
        return rows > 0;

    }

    @Transactional
    @Override
    public boolean resolveAll(String solution) {
        Query query = entityManager.createQuery("Update TblReportDetailEntity u set u.status = true, u.solution =:solution ");
        int rows = query.executeUpdate();
        Query query1 = entityManager.createQuery("Update TblReportEntity u set u.status = true");
        query1.executeUpdate();

        return rows > 0;
    }

    @Override
    public boolean removeReport(int reportId) {
        return true;
    }

    public List<TblReportEntity> findAllReportInOneMonth(int classroomId){
        Query query = entityManager.createQuery("SELECT r from TblReportEntity r where r.classRoomId =:classroomId and" +
                " r.createTime between DATE_FORMAT(CURDATE() ,'%Y-%m-01') and LAST_DAY(CURDATE())");
        query.setParameter("classroomId", classroomId);
        List<TblReportEntity> tblReportEntities = query.getResultList();
        return tblReportEntities;
    }

    @Override
    public List<StatisticDTO.StatisticRow> getNumberOfChangeRoomByMonth(int year) {
        Query query = entityManager.createQuery("SELECT COUNT (r.id) AS num, DATE_FORMAT(r.createTime, '%m-%Y') AS month " +
                                                "FROM TblReportEntity r " +
                                                "WHERE r.changedRoom IS NOT NULL AND YEAR(r.createTime) = :thatYear " +
                                                "GROUP BY MONTH(r.createTime), YEAR(r.createTime) " +
                                                "order by r.createTime ASC");
        query.setParameter("thatYear", year);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult;
        }
        return new ArrayList<StatisticDTO.StatisticRow>();
    }

    public List<Integer> getListYearForChangeRoomStatistic() {
        Query query = entityManager.createQuery("SELECT YEAR(r.createTime) " +
                "FROM TblReportEntity r " +
                "WHERE r.changedRoom IS NOT NULL " +
                "GROUP BY YEAR(r.createTime)ORDER BY YEAR(r.createTime) DESC");

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult;
        }

        return new ArrayList<Integer>();
    }
}
