package com.ecrm.DAO.Impl;

import com.ecrm.DAO.*;
import com.ecrm.Entity.*;
import org.springframework.stereotype.Repository;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public class ReportDAOImpl extends BaseDAO<TblReportEntity, Integer> implements ReportDAO {
    public ReportDAOImpl(){
        super(TblReportEntity.class);
    }
}
