package com.ecrm.DAO;

import com.ecrm.Entity.TblReportEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public interface ReportDAO {

    public List<TblReportEntity> getAllReportInStatus(boolean status);
}
