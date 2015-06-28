package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserInfoDAO;
import com.ecrm.Entity.TblUserInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 6/28/2015.
 */
@Repository
public class UserInfoDAOImpl extends BaseDAO<TblUserInfoEntity, String> implements UserInfoDAO{

    public UserInfoDAOImpl() {
        super(TblUserInfoEntity.class);
    }
}
