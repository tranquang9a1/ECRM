package com.ecrm.DAO;

import com.ecrm.Entity.TblUserEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public interface UserDAO {
    public TblUserEntity login(String username, String password);
}
