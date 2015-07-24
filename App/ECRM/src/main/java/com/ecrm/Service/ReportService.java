package com.ecrm.Service;

import com.ecrm.DAO.Impl.ReportDAOImpl;
import com.ecrm.Entity.TblReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Htang on 7/24/2015.
 */
@Service
public class ReportService {
    @Autowired
    ReportDAOImpl reportDAO;
    public void changeLiveReportStatus(int currentClassroomId, String changeClassroom){
        List<TblReportEntity> tblReportEntities = reportDAO.getLiveReportsInRoom(currentClassroomId);
        for(TblReportEntity tblReportEntity:tblReportEntities){
            tblReportEntity.setChangedRoom(changeClassroom);
            tblReportEntity.setStatus(2);
            reportDAO.merge(tblReportEntity);
        }
    }
}
