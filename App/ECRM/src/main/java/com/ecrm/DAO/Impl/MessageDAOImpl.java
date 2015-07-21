package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.MessageDAO;
import com.ecrm.Entity.TblMessageEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Htang on 7/20/2015.
 */
@Repository
public class MessageDAOImpl extends BaseDAO<TblMessageEntity, Integer> implements MessageDAO{

    public MessageDAOImpl() {
        super(TblMessageEntity.class);
    }

    public List<TblMessageEntity> getUnreadMessage(){
        Query query = entityManager.createQuery("select m from TblMessageEntity m where m.isRead = false");
        List<TblMessageEntity> tblMessageEntities = query.getResultList();
        return tblMessageEntities;
    }
}
