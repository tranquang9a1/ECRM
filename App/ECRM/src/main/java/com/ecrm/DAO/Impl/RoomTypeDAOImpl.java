package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.Entity.RoomType;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public class RoomTypeDAOImpl extends BaseDAO<RoomType, Integer> implements RoomTypeDAO{
    public RoomTypeDAOImpl() {
        super(RoomType.class);
    }

    public RoomType findByID(int id) {
        RoomType roomType = new RoomType();
        Query query = entityManager.createQuery("Select r from tblRoomType r where r.id = :id");
        query.setParameter("Id", id);
        roomType = (RoomType) query.getSingleResult();
        return roomType;
    }

}
