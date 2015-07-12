package com.ecrm.DAO;

import com.ecrm.Entity.TblUserNotificationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 7/8/2015.
 */
@Repository
public interface UserNotificationDAO {

    public int getNumberUnreadNotifyOfUser(String username);

    public List<TblUserNotificationEntity> getUnreadNotifyOfUser(String username);

    public List<TblUserNotificationEntity> getReadNotifyOfUser(String username, int page, int size);

    public List<TblUserNotificationEntity> getNotificationByUser(String username, int page, int size);
}
