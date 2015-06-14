package com.ecrm.DAO;

import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Utils.Enumerable.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 5/30/2015.
 */
@Repository
public interface ReportDAO {

    public List<Integer> getDamagedRoom();

    public List<TblReportEntity> getLiveReportsInRoom(int room);

    public List<TblReportEntity> getReportByUserId(String username);

    public String getReportersInRoom(int room);

    public TblReportEntity getReportNewest(int room);

    public List<TblReportEntity> getAllReport(int limit, int offset);

    public boolean createReport();

    public List<String> getReportByClassId();
}
