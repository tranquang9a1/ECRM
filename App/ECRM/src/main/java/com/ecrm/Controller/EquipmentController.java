package com.ecrm.Controller;

import com.ecrm.DAO.Impl.CategoryDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Utils.Constant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
    public String equipment(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            List<TblEquipmentEntity> tblEquipmentEntities = equipmentDAO.findAll();
            Iterator<TblEquipmentEntity> iteratorE = tblEquipmentEntities.iterator();
            while (iteratorE.hasNext()) {
                TblEquipmentEntity tblEquipmentEntity = iteratorE.next();
                String name = tblEquipmentEntity.getName();
                String serialNumber = tblEquipmentEntity.getSerialNumber();
                if (!tblEquipmentEntity.getTblEquipmentCategoryByCategoryId().getIsManaged()) {
                    iteratorE.remove();
                } else {
                    if (name == null || serialNumber == null) {
                        iteratorE.remove();
                    }
                }
            }
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.findAll();
            Iterator<TblEquipmentCategoryEntity> iterator = tblEquipmentCategoryEntities.iterator();
            while (iterator.hasNext()) {
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator.next();
                String categoryName = tblEquipmentCategoryEntity.getName();
                if (!tblEquipmentCategoryEntity.getIsManaged() && (categoryName.equalsIgnoreCase("Bàn") || categoryName.equalsIgnoreCase("Ghế"))) {
                    iterator.remove();
                }
            }
            request.setAttribute("EQUIPMENTS", tblEquipmentEntities);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_EQUIP");
            request.setAttribute("TABCONTROL", "STAFF_EQUIP");
            request.setAttribute("CATEGORIES", tblEquipmentCategoryEntities);
            return "Staff_Equipment";
        } else {
            return "Login";
        }
    }

    @RequestMapping("/createEquipment")
    public String createEquipment(HttpServletRequest request, @RequestParam("categoryId") int categoryId
            , @RequestParam("name") String name, @RequestParam("serialNumber") String serialNumber) {
        HttpSession session = request.getSession();
        if (session != null) {
            TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity(categoryId, null, name, serialNumber, null, null, true);
            equipmentDAO.persist(tblEquipmentEntity);
            return "redirect:/staff/equipment";
        } else {
            return "Login";
        }
    }

    @RequestMapping("/removeEquipment")
    public String removeEquipment(HttpServletRequest request, @RequestParam("equipmentId") int equipmentId, @RequestParam("remove") boolean isRemove) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (isRemove) {
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                equipmentDAO.remove(tblEquipmentEntity);
            } else {
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                tblEquipmentEntity.setClassroomId(null);
                equipmentDAO.merge(tblEquipmentEntity);
            }

            return "redirect:/staff/equipment";
        } else {
            return "Login";
        }
    }

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public String createCategory(HttpServletRequest request, HttpSession httpSession) {
        HttpSession session = request.getSession();
        if (session != null) {
            try {
                String fileName = "";
                File file;
                int maxFileSize = 5000 * 1024;
                int maxMemSize = 5000 * 1024;
                String filePath = "";
                String contentType = request.getContentType();
                Hashtable<String, String> params = new Hashtable<String, String>();

                if ((contentType.indexOf("multipart/form-data") >= 0)) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    // maximum size that will be stored in memory
                    factory.setSizeThreshold(maxMemSize);
                    // Location to save data that is larger than maxMemSize.
                    factory.setRepository(new File("/path/to/uploads"));
                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    // maximum file size to be uploaded.
                    upload.setSizeMax(maxFileSize);
                    // Parse the request to get file items.
                    List fileItems = upload.parseRequest(request);
                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();
                    while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();
                        if (!fi.isFormField()) {
                            // Get the uploaded file parameters
                            fileName = fi.getName();
                            fileName = fileName.replace("-", "");
                            ServletContext servletContext = request.getSession().getServletContext();
                            filePath = servletContext.getRealPath("/resource/img/equipment");
                            String saveFile = filePath + "/" + fileName;
                            // Write the file
                            file = new File(saveFile);
                            fi.write(file);
                        } else {
                            String fieldName = fi.getFieldName();
                            String fieldValue = fi.getString();
                            params.put(fieldName, fieldValue);
                        }
                    }


                }
                String name = params.get("name");
                String isManaged = params.get("isManaged");
                boolean managed = false;
                if (isManaged.equals("1")) {
                    managed = true;
                }
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = new TblEquipmentCategoryEntity(name, 0,managed,fileName,false);
                categoryDAO.persist(tblEquipmentCategoryEntity);
                return "redirect:/staff/equipment";
            } catch (Exception ex) {
                System.out.println(ex);
                return "Error";
            }
        } else {
            return "Login";
        }

    }

}
