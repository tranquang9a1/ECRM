package com.ecrm.Controller;

import com.ecrm.DAO.Impl.CategoryDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Htang on 6/14/2015.
 */
@Controller
@RequestMapping("/staff")
public class EquipmentController {
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    CategoryDAOImpl categoryDAO;

    @RequestMapping("/equipment")
    public String equipment(HttpServletRequest request){
        List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.findAll();
        List<TblEquipmentEntity> fitEquipment = new ArrayList<TblEquipmentEntity>();
        for(TblEquipmentEntity tblEquipmentEntity: tblEquipmentEntities){
            if(tblEquipmentEntity.getName()!= null && tblEquipmentEntity.getSerialNumber()!=null){
                fitEquipment.add(tblEquipmentEntity);
            }
        }
        List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
        List<TblEquipmentCategoryEntity> fitCategory = new ArrayList<TblEquipmentCategoryEntity>();
        for(TblEquipmentCategoryEntity tblEquipmentCategoryEntity : tblEquipmentCategoryEntities){
            if(tblEquipmentCategoryEntity.getId()<4){
                fitCategory.add(tblEquipmentCategoryEntity);
            }
        }
        request.setAttribute("EQUIPMENTS", fitEquipment);
        request.setAttribute("ACTIVELEFTTAB", "STAFF_EQUIP");
        request.setAttribute("CATEGORIES", tblEquipmentCategoryEntities);
        return "Staff_Equipment";
    }

    @RequestMapping("/createEquipment")
    public String createEquipment(HttpServletRequest request, @RequestParam("categoryId") int categoryId
            , @RequestParam("name") String name, @RequestParam("serialNumber") String serialNumber){
        TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity(categoryId, null, name, serialNumber,null, null, true);
        equipmentDAO.persist(tblEquipmentEntity);
        return "redirect:/staff/equipment";
    }
}
