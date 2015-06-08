package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.TblUserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public class UserDAOImpl extends BaseDAO<TblUserEntity, Integer> implements UserDAO {
    public UserDAOImpl() {
        super(TblUserEntity.class);
    }


    public TblUserEntity login(String username, String password) {
        TblUserEntity user = new TblUserEntity();
        Query query = entityManager.createQuery(" Select u from TblUserEntity u where u.username= :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        user = (TblUserEntity) query.getSingleResult();
        return user;
    }

    public TblUserEntity findUser(String username){
        Query query = entityManager.createQuery("select u from  TblUserEntity u where  u.username = :username");
        query.setParameter("username", username);
        TblUserEntity tblUserEntity = (TblUserEntity) query.getSingleResult();
        return tblUserEntity;
    }
}
