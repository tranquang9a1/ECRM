package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public class UserDAOImpl extends BaseDAO<TblUserEntity, Integer> implements UserDAO {
    public UserDAOImpl() {
        super(TblUserEntity.class);
    }


    public TblUserEntity login(String username, String password) {
        Query query = entityManager.createQuery(" Select u from TblUserEntity as u where u.username= :username and u.password = :password", TblUserEntity.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<TblUserEntity> users = query.getResultList();
        return users.size() > 0 ? users.get(0) : null;
    }

    public TblUserEntity findUser(String username){
        Query query = entityManager.createQuery("select u from  TblUserEntity u where  u.username = :username");
        query.setParameter("username", username);
        TblUserEntity tblUserEntity = (TblUserEntity) query.getSingleResult();
        return tblUserEntity;
    }

    public List<TblUserEntity> findTeacher(){
        Query q = entityManager.createQuery("SELECT u from TblUserEntity u where u.roleId =:roleId");
        q.setParameter("roleId", 3);
        List<TblUserEntity> tblUserEntities = q.getResultList();
        return tblUserEntities;
    }

    public List<TblUserEntity> getAllStaff(){
        Query query = entityManager.createQuery("SELECT u FROM TblUserEntity u WHERE u.roleId = 2 AND u.status = true");

        List queryResult = query.getResultList();
        List<TblUserEntity> result = new ArrayList<TblUserEntity>();
        if(!queryResult.isEmpty()) {
            for (Iterator i = queryResult.iterator(); i.hasNext();) {
                result.add((TblUserEntity) i.next());
            }
        }

        return result;
    }

    @Transactional
    public boolean updateLastLogin(String username, Timestamp lastLogin) {
        Query query = entityManager.createQuery("UPDATE TblUserInfoEntity u set u.lastLogin = :lastLogin " +
                "where u.username = :username");
        query.setParameter("username", username);
        query.setParameter("lastLogin", lastLogin);

        int rows = query.executeUpdate();
        if (rows > 0) {
            return true;
        }
        return false;

    }

    @Transactional
    public boolean updateDeviceId(String username, String deviceId) {
        Query query = entityManager.createQuery("UPDATE TblUserInfoEntity u set u.deviceId = :deviceId " +
                "where u.username = :username");
        query.setParameter("username", username);
        query.setParameter("deviceId", deviceId);

        int rows = query.executeUpdate();
        if (rows > 0) {
            return true;
        }
        return false;
    }

    public String getDeviceId(String username) {
        Query query = entityManager.createQuery("SELECT u.deviceId FROM TblUserInfoEntity u " +
                "where u.username = :username");
        query.setParameter("username", username);

       String result = query.getSingleResult().toString();
        return result;
    }

    public String getPhoneNumber(String username) {
        Query query = entityManager.createQuery("SELECT u.phone FROM TblUserInfoEntity u " +
                "where u.username = :username");
        query.setParameter("username", username);

        String result = query.getSingleResult().toString();
        return result;
    }

    public List<TblUserEntity> checkUsername(String username){
        Query query = entityManager.createQuery("select u from TblUserEntity u where u.username =:username");
        query.setParameter("username", username);
        List<TblUserEntity> tblUserEntities = query.getResultList();
        return tblUserEntities;
    }
}
