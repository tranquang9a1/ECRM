package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ReportDAO;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import com.ecrm.Utils.Enumerable.ReportStatus;
import org.springframework.stereotype.Repository;

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
                "AND u.username IN (SELECT r.username FROM TblReportEntity r WHERE r.classRoomId = :room GROUP BY r.username)");
        query.setParameter("room", room);

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
    public TblReportEntity getReportNewest(int room) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblReportEntity r " +
                "WHERE r.classRoomId = :room " +
                "AND (r.status = :status1 " +
                "OR r.status = :status2) " +
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
    public List<TblReportEntity> getReportByUserId(String username) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username =:username");
        query.setParameter("username", username);

        return query.getResultList();

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
    public List<Integer> getReportByClassId() {
        Query query  = entityManager.createQuery("Select u.classRoomId from TblReportEntity u where u.status = :statusnew " +
                "order by u.createTime DESC");
        query.setParameter("statusnew", ReportStatus.NEW.getValue());
        return query.getResultList();


    }

    @Override
    public boolean resolveReport(int reportId, int equipmentId, String solution) {
        Query query = entityManager.createQuery("Update TblReportDetailEntity u set u.status = true, u.solution =:solution " +
                "where u.reportId = :reportId and u.equipmentId = :equipmentId ");
        query.setParameter("solution", solution);
        query.setParameter("reportId", reportId);
        query.setParameter("equipmentId", equipmentId);

        Query query1 = entityManager.createQuery("Select u from TblReportDetailEntity u where u.reportId = :reportId and u.status = false ");
        query1.setParameter("reportId", reportId);

        if (query1.getResultList().size() == 0) {
            Query query2 = entityManager.createQuery("Update TblReportEntity u set u.status = true");
            query2.executeUpdate();
        }

        int rows = query.executeUpdate();
        return rows > 0 ? true : false;
    }

    @Override
    public boolean updateDamageLevel(int damageLevel, int reportId) {
        Query query = entityManager.createQuery("Update TblReportEntity r set r.damagedLevel = :damageLevel where r.reportId = :reportId");
        query.setParameter("damageLevel", damageLevel);
        query.setParameter("reportId", reportId);

        int rows = query.executeUpdate();
        return rows > 0 ? true : false;

    }
}
