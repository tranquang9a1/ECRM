package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserInfoDAO;
import com.ecrm.Entity.TblUserInfoEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Htang on 6/28/2015.
 */
@Repository
public class UserInfoDAOImpl extends BaseDAO<TblUserInfoEntity, String> implements UserInfoDAO{

    public UserInfoDAOImpl() {
        super(TblUserInfoEntity.class);
    }

    public List<TblUserInfoEntity> getUserInfoByPhone(String phone){
        Query query = entityManager.createQuery("SELECT u from TblUserInfoEntity u where u.phone =:phone");
        query.setParameter("phone", phone);
        List<TblUserInfoEntity>tblUserInfoEntities = query.getResultList();
        return tblUserInfoEntities;
    }

}
