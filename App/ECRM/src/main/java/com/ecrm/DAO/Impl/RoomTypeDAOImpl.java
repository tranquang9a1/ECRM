package com.ecrm.DAO.Impl;

import com.ecrm.DAO.BaseDAO;
import com.ecrm.DAO.RoomTypeDAO;
import com.ecrm.Entity.RoomType;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Htang on 5/26/2015.
 */
@Repository
public class RoomTypeDAOImpl extends BaseDAO<RoomType, Integer> implements RoomTypeDAO{
    public RoomTypeDAOImpl() {
        super(RoomType.class);
    }
    //Find roomtype by id
    public RoomType findByID(int id) {
        RoomType roomType = new RoomType();
        Query query = entityManager.createQuery("Select r from tblRoomType r where r.id = :id");
        query.setParameter("id", id);
        roomType = (RoomType) query.getSingleResult();
        return roomType;
    }

    //Create roomtype
    public void createRoomType(int slots, int verticalRows, String horizontalRows, String noSlotsEachHRows,
                                   int airConditioning, int fan, int projectors, int speaker,int television,
                                  Date createTime){
        Query query = entityManager.createNativeQuery("Insert INTO tblRoomType (slots, verticalRows, horizontalRows, noSlotsEachHRows," +
                "airConditioning, fan, projector, speaker, television, createTime) VALUES (?,?,?,?,?,?,?,?,?,?)")
                .setParameter(1, slots)
                .setParameter(2, verticalRows)
                .setParameter(3,horizontalRows)
                .setParameter(4,noSlotsEachHRows)
                .setParameter(5,airConditioning)
                .setParameter(6,fan)
                .setParameter(7,projectors)
                .setParameter(8,speaker)
                .setParameter(9,television)
                .setParameter(10,createTime , TemporalType.TIMESTAMP);
        query.executeUpdate();
    }
}
