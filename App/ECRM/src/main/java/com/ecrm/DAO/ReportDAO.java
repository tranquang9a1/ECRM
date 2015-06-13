package com.ecrm.DAO;

import com.ecrm.Entity.TblReportEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public interface ReportDAO {

    public List<Integer> getDamagedRoom(boolean status);

    public List<TblReportEntity> getReportsInRoom(int room);

//    public

    public List<TblReportEntity> getReportByUserId(String username);

    public List<TblReportEntity> getAllReport(int limit, int offset);

    public boolean createReport();
}
