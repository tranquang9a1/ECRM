package com.ecrm.DAO;

import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentQuantityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Htang on 5/29/2015.
 */
@Repository
public interface EquipmentCategoryDAO {

    public String getCategoriesInRoom(int room);
    public int findEquipmentId(String name);
    public List<TblEquipmentCategoryEntity> getAllEquipment();

}
