package com.ecrm.DAO;

import com.ecrm.Entity.RoomType;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public interface RoomTypeDAO {
    public RoomType findByID(int i);

    public void createRoomType(int slots, int verticalRows, String horizontalRows, String noSlotsEachHRows,
                                   int airConditioning, int fan,int projectors, int speaker,int television, Date createTime);
}
