package com.ecrm.Service;

import com.ecrm.DAO.Impl.ScheduleConfigDAOImpl;
import com.ecrm.Entity.TblScheduleConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Htang on 7/23/2015.
 */
@Service
public class ScheduleConfigService {
    @Autowired
    ScheduleConfigDAOImpl scheduleConfigDAO;
    public List<TblScheduleConfigEntity> findAll(){
        List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
        return  tblScheduleConfigEntities;
    }

}
