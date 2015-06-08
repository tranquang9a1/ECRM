package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ClassroomDAO;
import com.ecrm.Entity.TblClassroomEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collection;

/**
 * Created by Htang on 5/29/2015.
 */
@Repository
public class ClassroomDAOImpl extends BaseDAO<TblClassroomEntity, Integer> implements ClassroomDAO{
    public  ClassroomDAOImpl(){
        super(TblClassroomEntity.class);
    }

    public TblClassroomEntity getClassroomByName(String name){
        Query q= entityManager.createQuery("select c from TblClassroomEntity c where c.name = :name");
        q.setParameter("name", name);
        TblClassroomEntity c = (TblClassroomEntity) q.getSingleResult();
        return c;
    }

}
