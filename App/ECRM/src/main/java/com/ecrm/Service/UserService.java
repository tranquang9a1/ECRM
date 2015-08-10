package com.ecrm.Service;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Htang on 7/23/2015.
 */
@Service
public class UserService {
    @Autowired
    UserDAOImpl userDAO;

    public List<TblUserEntity> getAllTeacher(){
        List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
        return tblUserEntities;
    }

    public int changePassword(String username, String password) {
        try {
            TblUserEntity user = userDAO.findUser(username);
            if (!user.isStatus()) {
                return 400;
            }
            user.setPassword(password);
            userDAO.merge(user);
            return 200;
        }catch (NoResultException e) {
            return 201;
        }

    }
}
