package com.ecrm.DAO;

import com.ecrm.Entity.TblClassroomEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by Htang on 5/29/2015.
 */
@Repository
public interface ClassroomDAO {
    public TblClassroomEntity getClassroomByName(String name);
}
