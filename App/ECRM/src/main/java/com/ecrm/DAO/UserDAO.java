package com.ecrm.DAO;

import com.ecrm.Entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public interface UserDAO {
    public User login(String username, String password);
}
