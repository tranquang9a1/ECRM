package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.ReportDetailDAO;
import com.ecrm.Entity.TblReportDetailEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class ReportDetailDAOImpl extends BaseDAO<TblReportDetailEntity, Integer> implements ReportDetailDAO {
    public ReportDetailDAOImpl() {
        super(TblReportDetailEntity.class);
    }
}
