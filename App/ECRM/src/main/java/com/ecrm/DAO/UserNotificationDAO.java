package com.ecrm.DAO;

import com.ecrm.Entity.TblUserNotificationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChiDNMSE60717 on 7/8/2015.
 */
@Repository
public interface UserNotificationDAO {

    public List<TblUserNotificationEntity> getNotificationByUser(String username);

    public int getNumberUnreadNotifyOfUser(String username);
}
