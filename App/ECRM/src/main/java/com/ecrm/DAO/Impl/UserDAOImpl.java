package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.UserDAO;
import com.ecrm.Entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public class UserDAOImpl extends BaseDAO<User, Integer> implements UserDAO {
    public UserDAOImpl() {
        super(User.class);
    }


    public User login(String username, String password) {
        User user = new User();
        Query query = entityManager.createQuery("Select u from tblUser u where u.username= :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        user = (User) query.getSingleResult();
        return user;
    }
}
