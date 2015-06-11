package com.ecrm.DAO;

import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblUserEntity;
import com.ecrm.Entity.TblUserInfoEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public interface UserDAO {
    public TblUserEntity login(String username, String password);
    public boolean updateLastLogin(String username, Timestamp lastLogin);
}
