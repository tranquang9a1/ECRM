package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.RoomType2DAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.Entity.TblRoomTypeEntity;
import com.ecrm.Entity.TblRoomTypeEntity2;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public class RoomType2DAOImpl extends BaseDAO<TblRoomTypeEntity2, Integer> implements RoomType2DAO {
    public RoomType2DAOImpl() {
        super(TblRoomTypeEntity2.class);
    }

    public TblRoomTypeEntity2 getRoomtypeById(int roomtypeId){
        TblRoomTypeEntity2 roomTypeEntity = new TblRoomTypeEntity2();
        Query query = entityManager.createQuery("SELECT r FROM TblRoomTypeEntity2 r WHERE r.id = :id");
        query.setParameter("id", roomtypeId);
        roomTypeEntity = (TblRoomTypeEntity2) query.getSingleResult();
        return roomTypeEntity;
    }

    public TblRoomTypeEntity2 getRoomTypeByName(String name) {

        Query query = entityManager.createQuery("SELECT r " +
                "FROM TblRoomTypeEntity2 r " +
                "WHERE r.name = :name");
        query.setParameter("name", name);

        List queryResult = query.getResultList();
        if(!queryResult.isEmpty()){
            return (TblRoomTypeEntity2)queryResult.get(0);
        }

        return null;
    }


}
