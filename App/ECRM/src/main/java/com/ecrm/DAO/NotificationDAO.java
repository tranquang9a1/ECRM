package com.ecrm.DAO;

import com.ecrm.Entity.TblNotificationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 6/26/2015.
 */
@Repository
public interface NotificationDAO {

//    public List<TblNotificationEntity> getAllNotifyOfUser(String username, int messageType);

    public TblNotificationEntity getNotifyOfRoom(int roomID, int messageType);

    public List<TblNotificationEntity> getActiveNotifyOfRoom(int roomID, int messageType);

//    public List<TblNotificationEntity> getNotifyInDay();
}
