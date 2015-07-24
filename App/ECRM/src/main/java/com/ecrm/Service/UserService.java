package com.ecrm.Service;

import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
