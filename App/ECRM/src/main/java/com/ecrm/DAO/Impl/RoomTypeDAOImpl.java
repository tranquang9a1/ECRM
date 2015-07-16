package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.Entity.TblRoomTypeEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public class RoomTypeDAOImpl extends BaseDAO<TblRoomTypeEntity, Integer> implements RoomTypeDAO{
    public RoomTypeDAOImpl() {
        super(TblRoomTypeEntity.class);
    }

    public TblRoomTypeEntity getRoomtypeById(int roomtypeId){
        TblRoomTypeEntity roomTypeEntity = new TblRoomTypeEntity();
        Query query = entityManager.createQuery("SELECT r FROM TblRoomTypeEntity r WHERE r.id = :id");
        query.setParameter("id", roomtypeId);
        roomTypeEntity = (TblRoomTypeEntity) query.getSingleResult();
        return roomTypeEntity;
    }

    public TblRoomTypeEntity getRoomTypeByName(String name) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblRoomTypeEntity r " +
                "WHERE r.name = :name");
        query.setParameter("name", name);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()){
            return (TblRoomTypeEntity)queryResult.get(0);
        }

        return null;
    }


}
