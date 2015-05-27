package com.ecrm.DAO;

import com.ecrm.Entity.RoomType;
import org.springframework.stereotype.Repository;
/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public interface RoomTypeDAO {
    public RoomType findByID(int i);
}
