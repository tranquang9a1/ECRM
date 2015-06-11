package com.ecrm.DAO.Impl;

import com.ecrm.DAO.*;
import com.ecrm.Entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class ReportDAOImpl extends BaseDAO<TblReportEntity, Integer> implements ReportDAO {

    public ReportDAOImpl(){
        super(TblReportEntity.class);
    }

    @Override
    public List<TblReportEntity> getAllReportInStatus(boolean status) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblReportEntity r " +
                "WHERE r.status = :status");
        query.setParameter("status",status);

        List queryResult = query.getResultList();
        List<TblReportEntity> result = new ArrayList<TblReportEntity>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();) {
                TblReportEntity report = (TblReportEntity) i.next();
                result.add(report);
            }
        }

        return result;
    }

    @Override
    public List<TblReportEntity> getReportByUserId(String username) {
        Query query = entityManager.createQuery("SELECT r FROM TblReportEntity r WHERE r.username =:username");
        query.setParameter("username", username);

        return query.getResultList();

    }
}
