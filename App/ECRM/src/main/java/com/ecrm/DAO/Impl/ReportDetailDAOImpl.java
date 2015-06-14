package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ReportDetailDAO;
import com.ecrm.Entity.TblReportDetailEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class ReportDetailDAOImpl extends BaseDAO<TblReportDetailEntity, Integer> implements ReportDetailDAO {

    public ReportDetailDAOImpl() {
        super(TblReportDetailEntity.class);
    }

    @Override
    public List<TblReportDetailEntity> getReportDetailsInReport(int reportId) {

        Query query = entityManager.createQuery("SELECT rd " +
                "FROM TblReportDetailEntity rd " +
                "WHERE rd.reportId = :reportId");
        query.setParameter("reportId", reportId);

        List queryResult = query.getResultList();
        List<TblReportDetailEntity> result = new ArrayList<TblReportDetailEntity>();
        if(!queryResult.isEmpty()){
            for(Iterator i = queryResult.iterator(); i.hasNext();){
                TblReportDetailEntity item = (TblReportDetailEntity) i.next();
                result.add(item);
            }
        }

        return result;
    }

    public List<TblReportDetailEntity> getUnresolveReportDetail(int equimentId){
        Query q = entityManager.createQuery("Select rd from TblReportDetailEntity rd where rd.equipmentId =:equipmentId " +
                "and rd.status =:true");
        q.setParameter("equipmentId", equimentId);
        List<TblReportDetailEntity> tblReportDetailEntities = q.getResultList();
        return tblReportDetailEntities;
    }
}
