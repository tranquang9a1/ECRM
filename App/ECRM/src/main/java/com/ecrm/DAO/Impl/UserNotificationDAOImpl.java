package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserNotificationDAO;
import com.ecrm.Entity.TblUserNotificationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 7/8/2015.
 */
@Repository
public class UserNotificationDAOImpl extends BaseDAO<TblUserNotificationEntity, Integer> implements UserNotificationDAO {
    public UserNotificationDAOImpl(){
        super(TblUserNotificationEntity.class);
    }

    @Override
    public List<TblUserNotificationEntity> getNotificationByUser(String username, int page, int size) {
        Query query = entityManager.createQuery("SELECT n FROM TblUserNotificationEntity n " +
                "WHERE n.username = :username ORDER BY n.status ASC, n.id DESC");
        query.setParameter("username", username);
        query.setMaxResults(size);
        query.setFirstResult((page-1)*size);

        List queryResult = query.getResultList();
        List<TblUserNotificationEntity> result = new ArrayList<TblUserNotificationEntity>();
        if(!queryResult.isEmpty()) {
            for(Iterator i = queryResult.iterator(); i.hasNext();){
                result.add((TblUserNotificationEntity) i.next());
            }
        }

        return result;
    }

    @Override
    public int getNumberUnreadNotifyOfUser(String username) {
        Query query = entityManager.createQuery("SELECT n FROM TblUserNotificationEntity n " +
                "WHERE n.username = :username AND n.status = false");
        query.setParameter("username", username);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult.size();
        }

        return 0;
    }

    @Override
    public List<TblUserNotificationEntity> getUnreadNotifyOfUser(String username) {
        Query query = entityManager.createQuery("SELECT n FROM TblUserNotificationEntity n " +
                "WHERE n.username = :username AND n.status = false");
        query.setParameter("username", username);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()) {
            return queryResult;
        }

        return null;
    }

    @Override
    public List<TblUserNotificationEntity> getReadNotifyOfUser(String username, int page, int size) {
        Query query = entityManager.createQuery("SELECT n FROM TblUserNotificationEntity n " +
                "WHERE n.username = :username AND n.status = true ORDER BY n.id DESC");
        query.setParameter("username", username);
        query.setMaxResults(size);
        query.setFirstResult((page-1)*size);

        List queryResult = query.getResultList();
        List<TblUserNotificationEntity> result = new ArrayList<TblUserNotificationEntity>();
        if(!queryResult.isEmpty()) {
            for(Iterator i = queryResult.iterator(); i.hasNext();){
                result.add((TblUserNotificationEntity) i.next());
            }
        }

        return result;
    }
}
