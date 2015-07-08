package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.NotificationDAO;
import com.ecrm.Entity.TblNotificationEntity;
import com.ecrm.Utils.Enumerable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/26/2015.
 */
@Repository
public class NotificationDAOImp extends BaseDAO<TblNotificationEntity, Integer> implements NotificationDAO{
    public NotificationDAOImp(){
        super(TblNotificationEntity.class);
    }

//    @Override
//    public List<TblNotificationEntity> getAllNotifyOfUser(String username, int messageType) {
//        Query query = entityManager.createQuery("SELECT n FROM TblNotificationEntity n " +
//                "WHERE DATE(n.createTime) = CURDATE() " +
//                "AND n.status = true " +
//                "AND n.messageType = :mType " +
//                "AND n.classroomId IN (SELECT s.classroomId FROM TblScheduleEntity s " +
//                                "WHERE s.date = CURDATE() " +
//                                "AND s.username = :username " +
//                                "GROUP BY s.classroomId)");
//        query.setParameter("username", username);
//        query.setParameter("mType", messageType);
//
//        List queryResult = query.getResultList();
//        List<TblNotificationEntity> result = new ArrayList<TblNotificationEntity>();
//        if(!queryResult.isEmpty()) {
//            for (Iterator i = queryResult.iterator(); i.hasNext();){
//                result.add((TblNotificationEntity) i.next());
//            }
//        }
//
//        return result;
//    }

//    @Override
//    public List<TblNotificationEntity> getNotifyInDay() {
//        Query query = entityManager.createQuery("SELECT n FROM TblNotificationEntity n " +
//                "WHERE DATE(n.createTime) < CURDATE() " +
//                "AND n.status = true " +
//                "AND n.messageType = 2");
//
//        List queryResult = query.getResultList();
//        List<TblNotificationEntity> result = new ArrayList<TblNotificationEntity>();
//        if(!queryResult.isEmpty()) {
//            for (Iterator i = queryResult.iterator(); i.hasNext();){
//                result.add((TblNotificationEntity) i.next());
//            }
//        }
//
//        return result;
//    }

    @Override
    public TblNotificationEntity getNotifyOfRoom(int roomID, int messageType) {
        Query query = entityManager.createQuery("SELECT n FROM TblNotificationEntity n " +
                "WHERE n.classroomId = :room " +
                "AND n.status = false " +
                "AND DATE(n.createTime) = CURDATE() " +
                "AND n.messageType = :mType");
        query.setParameter("room", roomID);
        query.setParameter("mType", messageType);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return (TblNotificationEntity)queryResult.get(0);
        }

        return null;
    }

    @Override
    public List<TblNotificationEntity> getActiveNotifyOfRoom(int roomID, int messageType) {
        Query query = entityManager.createQuery("SELECT n FROM TblNotificationEntity n " +
                "WHERE n.classroomId = :room " +
                "AND n.status = false " +
                "AND n.messageType = :mType");
        query.setParameter("room", roomID);
        query.setParameter("mType", messageType);

        List queryResult = query.getResultList();
        List<TblNotificationEntity> result = new ArrayList<TblNotificationEntity>();
        if(!queryResult.isEmpty()) {
            for(Iterator i = queryResult.iterator(); i.hasNext();) {
                result.add((TblNotificationEntity) i.next());
            }
        }

        return result;
    }
}
