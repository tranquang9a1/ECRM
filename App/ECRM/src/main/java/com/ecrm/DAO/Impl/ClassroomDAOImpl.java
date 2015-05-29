package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ClassroomDAO;
import com.ecrm.Entity.Classroom;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 5/29/2015.
 */
@Repository
public class ClassroomDAOImpl extends BaseDAO<Classroom, Integer> implements ClassroomDAO{
    public  ClassroomDAOImpl(){
        super(Classroom.class);
    }

}
