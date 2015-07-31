package com.ecrm.Controller;

import com.ecrm.DAO.Impl.CategoryDAOImpl;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.EquipmentDAOImpl;
import com.ecrm.DAO.Impl.EquipmentQuantityDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblEquipmentCategoryEntity;
import com.ecrm.Entity.TblEquipmentEntity;
import com.ecrm.Entity.TblEquipmentQuantityEntity;
import com.ecrm.Utils.Constant;
import com.ecrm.Utils.Utils;
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
import java.util.*;

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
    @Autowired
    EquipmentQuantityDAOImpl equipmentQuantityDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;

    @RequestMapping("/equipment")
    public String equipment(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab) {
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
            List<TblEquipmentCategoryEntity> mEquipmentCategoryEntities = new ArrayList<TblEquipmentCategoryEntity>();
            Iterator<TblEquipmentCategoryEntity> iterator = tblEquipmentCategoryEntities.iterator();
            while (iterator.hasNext()) {
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator.next();
                String categoryName = tblEquipmentCategoryEntity.getName();
                if (tblEquipmentCategoryEntity.getIsDelete() || categoryName.equalsIgnoreCase("Bàn") || categoryName.equalsIgnoreCase("Ghế")) {
                    iterator.remove();
                }else {
                    mEquipmentCategoryEntities.add(tblEquipmentCategoryEntity);
                }
            }

            Iterator<TblEquipmentCategoryEntity> iterator2 = mEquipmentCategoryEntities.iterator();
            while (iterator2.hasNext()) {
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = iterator2.next();
                String categoryName = tblEquipmentCategoryEntity.getName();
                if (tblEquipmentCategoryEntity.getIsDelete() || !tblEquipmentCategoryEntity.getIsManaged() || categoryName.equals("Empty")) {
                    iterator2.remove();
                }
            }
            request.setAttribute("CATEGORIES", tblEquipmentCategoryEntities);
            request.setAttribute("MANAGEDCATEGORIES", mEquipmentCategoryEntities);
            request.setAttribute("EQUIPMENTS", tblEquipmentEntities);
            request.setAttribute("ACTIVELEFTTAB", "STAFF_EQUIP");
            request.setAttribute("TABCONTROL", "STAFF_EQUIP");
            request.setAttribute("ACTIVETAB", activeTab);

            return "Staff_Equipment";
        } else {
            return "Login";
        }
    }

    @RequestMapping(value = "/createEquipment", method = RequestMethod.POST)
    public String createEquipment(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String serialNumber = request.getParameter("serialNumber");
            String action = request.getParameter("Action");
            int equipmentId = Integer.parseInt(request.getParameter("equipmentId"));
            double usingTime = Double.parseDouble(request.getParameter("usingTime"));
            int classroomId = Integer.parseInt(request.getParameter("classroomId"));
            String name = request.getParameter("name");
            if (action.equals("insert")) {
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                TblEquipmentEntity tblEquipmentEntity = new TblEquipmentEntity(categoryId, null, "[" + categoryId + "]", usingTime, name,
                        serialNumber, true, false, usingTime);
                equipmentDAO.persist(tblEquipmentEntity);
            } else {
                int category = Integer.parseInt(request.getParameter("category"));
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                tblEquipmentEntity.setCategoryId(category);
                tblEquipmentEntity.setClassroomId(classroomId);
                tblEquipmentEntity.setPosition("[" + category + "]");
                tblEquipmentEntity.setTimeRemain(usingTime);
                tblEquipmentEntity.setName(name);
                tblEquipmentEntity.setSerialNumber(serialNumber);
                tblEquipmentEntity.setStatus(true);
                tblEquipmentEntity.setIsDelete(false);
                tblEquipmentEntity.setUsingTime(usingTime);
                equipmentDAO.merge(tblEquipmentEntity);
            }
            return "redirect:/staff/equipment?ACTIVETAB=tab1";
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
                tblEquipmentEntity.setIsDelete(true);
            } else {
                TblEquipmentEntity tblEquipmentEntity = equipmentDAO.find(equipmentId);
                tblEquipmentEntity.setClassroomId(null);
                equipmentDAO.merge(tblEquipmentEntity);
                TblClassroomEntity classroomEntity = tblEquipmentEntity.getTblClassroomByClassroomId();
                classroomEntity.setIsAllInformation(false);
                classroomDAO.merge(classroomEntity);
            }

            return "redirect:/staff/equipment?ACTIVETAB=tab1";
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
                            filePath = servletContext.getRealPath("/resource/img/equipment/");
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
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = new TblEquipmentCategoryEntity(name, 0, managed, fileName, false);
                tblEquipmentCategoryEntity.setUpdateTime(new Date().getTime());
                categoryDAO.persist(tblEquipmentCategoryEntity);
                return "redirect:/staff/equipment?ACTIVETAB=tab2";
            } catch (Exception ex) {
                System.out.println(ex);
                return "Error";
            }
        } else {
            return "Login";
        }

    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public String editCategory(HttpServletRequest request) {
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
                            filePath = servletContext.getRealPath("/resource/img/equipment/");
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
                String categoryId = params.get("categoryId");
                String categoryName = params.get("name");
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = categoryDAO.find(Integer.parseInt(categoryId));
                tblEquipmentCategoryEntity.setImageUrl(fileName);
                tblEquipmentCategoryEntity.setName(categoryName.trim());
                tblEquipmentCategoryEntity.setUpdateTime(new Date().getTime());
                categoryDAO.merge(tblEquipmentCategoryEntity);
                return "redirect:/staff/equipment?ACTIVETAB=tab2";
            } catch (Exception ex) {
                System.out.println(ex);
                return "Error";
            }
        } else {
            return "Login";
        }
    }

    @RequestMapping(value = "removeCategory")
    public String removeCategory(HttpServletRequest request, @RequestParam("categoryId") int categoryId) {
        HttpSession session = request.getSession();
        if (session != null) {
            try {
                TblEquipmentCategoryEntity tblEquipmentCategoryEntity = categoryDAO.find(categoryId);
                tblEquipmentCategoryEntity.setIsDelete(true);
                categoryDAO.merge(tblEquipmentCategoryEntity);
                Collection<TblEquipmentEntity> tblEquipmentEntities = tblEquipmentCategoryEntity.getTblEquipmentsById();
                if (!tblEquipmentEntities.isEmpty()) {
                    for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
                        tblEquipmentEntity.setIsDelete(true);
                        tblEquipmentEntity.setClassroomId(null);
                        equipmentDAO.merge(tblEquipmentEntity);
                        TblClassroomEntity classroomEntity = tblEquipmentEntity.getTblClassroomByClassroomId();
                        classroomEntity.setIsAllInformation(false);
                        classroomDAO.merge(classroomEntity);
                    }
                }

                Collection<TblEquipmentQuantityEntity> tblEquipmentQuantityEntities = tblEquipmentCategoryEntity.getTblEquipmentQuantityById();
                if (!tblEquipmentQuantityEntities.isEmpty()) {
                    for (TblEquipmentQuantityEntity tblEquipmentQuantityEntity : tblEquipmentQuantityEntities) {
                        tblEquipmentQuantityEntity.setIsDelete(true);
                        equipmentQuantityDAO.merge(tblEquipmentQuantityEntity);
                    }
                }
                return "redirect:/staff/equipment?ACTIVETAB=tab2";
            } catch (Exception ex) {
                System.out.println(ex);
                return "Error";
            }
        } else {
            return "Login";
        }
    }

}
