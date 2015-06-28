package com.ecrm.DAO;

import com.ecrm.Entity.TblScheduleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/7/2015.
 */
@Repository
public interface ScheduleDAO {

    public TblScheduleEntity getScheduleInTime(String username, int room);

    public List<TblScheduleEntity> getSchedulesOfUser(String username);

    public List<TblScheduleEntity> getScheduleNoFinishOfRoom(int room);

    public List<TblScheduleEntity> getSchedulesFinishOfUser(String username);

    public List<TblScheduleEntity> findScheduleWithDate(String username, String date, String timeFrom);

    public List<TblScheduleEntity> findSpecificSchedule(String date, String timeFrom);
}
