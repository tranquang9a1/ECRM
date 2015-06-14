package com.ecrm.DAO;

import com.ecrm.Entity.TblReportDetailEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public interface ReportDetailDAO {

    public List<TblReportDetailEntity> getReportDetailsInReport(int reportId);
    public List<TblReportDetailEntity> getUnresolveReportDetail(int equimentId);
}
