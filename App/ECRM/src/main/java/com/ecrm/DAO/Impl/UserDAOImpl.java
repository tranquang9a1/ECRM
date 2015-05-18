package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public class UserDAOImpl extends BaseDAO<User, Integer> implements UserDAO {
    public UserDAOImpl() {
        super(User.class);
    }
}
