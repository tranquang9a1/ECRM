package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.SystemConfigurationDAO;
import com.ecrm.Entity.TblSystemConfiguration;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 8/19/2015.
 */
@Repository
public class SystemConfigurationDAOImpl extends BaseDAO<TblSystemConfiguration, String> implements SystemConfigurationDAO {

    public SystemConfigurationDAOImpl() {
        super(TblSystemConfiguration.class);
    }
}
