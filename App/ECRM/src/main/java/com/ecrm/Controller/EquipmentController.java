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
import javax.servlet.http.HttpSession;
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
        HttpSession session  =  request.getSession();
        if(session!=null) {
            List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.findAll();
            List<TblEquipmentEntity> fitEquipment = new ArrayList<TblEquipmentEntity>();
            for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                if (tblEquipmentEntity.getName() != null && tblEquipmentEntity.getSerialNumber() != null) {
                    fitEquipment.add(tblEquipmentEntity);
                }
            }
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            List<TblEquipmentCategoryEntity> fitCategory = new ArrayList<TblEquipmentCategoryEntity>();
            for (TblEquipmentCategoryEntity tblEquipmentCategoryEntity : tblEquipmentCategoryEntities) {
                if (tblEquipmentCategoryEntity.getId() < 4) {
                    fitCategory.add(tblEquipmentCategoryEntity);
                }
            }
            request.setAttribute("EQUIPMENTS", fitEquipment);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_EQUIP");
            request.setAttribute("TABCONTROL", "STAFF_EQUIP");
            request.setAttribute("CATEGORIES", fitCategory);
            return "Staff_Equipment";
        }
        else{
            return "Login";
        }
    }

    @RequestMapping("/createEquipment")
    public String createEquipment(HttpServletRequest request, @RequestParam("categoryId") int categoryId
            , @RequestParam("name") String name, @RequestParam("serialNumber") String serialNumber){
        HttpSession session  =  request.getSession();
        if(session!=null) {
            TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity(categoryId, null, name, serialNumber, null, null, true);
            equipmentDAO.persist(tblEquipmentEntity);
            return "redirect:/staff/equipment";
        }else {
            return "Login";
        }
    }

    @RequestMapping("/removeEquipment")
    public String removeEquipment(HttpServletRequest request, @RequestParam("equipmentId")int equipmentId, @RequestParam("remove") boolean isRemove){
        HttpSession session  =  request.getSession();
        if(session!=null) {
            if(isRemove) {
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                equipmentDAO.remove(tblEquipmentEntity);
            } else {
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                tblEquipmentEntity.setClassroomId(null);
                equipmentDAO.merge(tblEquipmentEntity);
            }

            return "redirect:/staff/equipment";
        }else {
            return "Login";
        }
    }

}
