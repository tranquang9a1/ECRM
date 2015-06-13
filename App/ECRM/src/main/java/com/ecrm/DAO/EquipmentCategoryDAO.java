package com.ecrm.DAO;

import com.ecrm.Entity.TblEquipmentCategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Htang on 5/29/2015.
 */
@Repository
public interface EquipmentCategoryDAO {

    public List<TblEquipmentCategoryEntity> getCategoriesInRoom(int room);
    public int findEquipmentId(String name);
}
