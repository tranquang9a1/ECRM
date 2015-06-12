package com.ecrm.DAO;

import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/7/2015.
 */
@Repository
public interface ScheduleDAO {

    public int getClassroomIdByUsername(String username);

    public List<TblScheduleEntity> getScheduleOfUser(String username);
    public List<TblScheduleEntity> findScheduleWithDate(String username, java.util.Date date, String timeFrom);
}
