package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Entity.TblUserEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Htang on 6/10/2015.
 */
@Controller
@RequestMapping("/staff")
public class ScheduleController {
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    UserDAOImpl userDAO;

    @RequestMapping(value = "schedule")
    public String mappingSchedule(HttpServletRequest request) {
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
        request.setAttribute("CLASSROOMS", tblClassroomEntities);
        request.setAttribute("TEACHERS", tblUserEntities);
        request.setAttribute("ACTIVELEFTTAB", "STAFF_SCHEDULE");
        return "Staff_MappingSchedule";
    }

    @RequestMapping(value = "import")
    public String importFile(HttpServletRequest request) throws IOException, InvalidFormatException, ParseException {
        String fileName = "";
        File file;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        String filePath = "/path/to/uploads";
        String contentType = request.getContentType();
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

            try {
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(request);

                // Process the uploaded file items
                Iterator i = fileItems.iterator();

                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (!fi.isFormField()) {
                        // Get the uploaded file parameters
                        fileName = fi.getName();
                        ServletContext servletContext = request.getSession().getServletContext();
                        filePath = servletContext.getRealPath(fileName);
                        // Write the file
                        if (fileName.lastIndexOf("\\") >= 0) {
                            file = new File(filePath);
                        } else {
                            file = new File(filePath);
                        }
                        fi.write(file);
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

            //Read file excel
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            //Get the workbook instance for XLS file
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            //Get first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            List<String> day = new ArrayList<String>();
            int classroom = 0;
            int numberOfSlot = 0;
            int count = 0;
            TblScheduleEntity tblScheduleEntity = new TblScheduleEntity();
            TblClassroomEntity classroomEntity = new TblClassroomEntity();
            while (rowIterator.hasNext()) {
                String slot = "";
                String timeFrom;
                String teacher = "";

                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                if (row.getRowNum() == 3) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() > 1) {
                            day.add(formatter.format(cell.getDateCellValue()));
                        }
                    }
                }
                if (row.getRowNum() > 4) {
                    if (row.getCell(0).getNumericCellValue() != 0) {
                        classroom = (int) row.getCell(0).getNumericCellValue();
                        classroomEntity = classroomDAO.getClassroomByName(Integer.toString(classroom));
                        numberOfSlot = classroomEntity.getTblRoomTypeByRoomTypeId().getSlots();
                    }
                    slot = row.getCell(1).getStringCellValue();
                    String[] array = slot.split("-");
                    timeFrom = array[0].trim() + ":00";
                    count = 0;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() > 1) {
                            teacher = cell.getStringCellValue();
                            if (teacher.trim().length() > 0) {
                                insertSchedule(teacher, classroomEntity,
                                        numberOfSlot, timeFrom, day.get(count), formatter);
                            }
                            count += 1;
                        }
                    }
                }

                System.out.println("");
            }
            fileInputStream.close();
            FileOutputStream out =
                    new FileOutputStream(new File(filePath));
            out.close();

        }
        return "redirect:/staff/schedule?ACTIVETAB=tab1";
    }

    public String convertSlotToTime(String slot) throws ParseException {
        String timeFrom = "07:00:00";
        if (slot.equals("Slot 2")) {
            timeFrom = "08:45:00";
        }
        if (slot.equals("Slot 3")) {
            timeFrom = "10:30:00";
        }
        if (slot.equals("Slot 4")) {
            timeFrom = "12:30:00";
        }
        if (slot.equals("Slot 5")) {
            timeFrom = "14:15:00";
        }
        if (slot.equals("Slot 6")) {
            timeFrom = "16:00:00";
        }
        return timeFrom;
    }

    public void insertSchedule(String teacher, TblClassroomEntity classroomEntity,
                               int numberOfSlot, String timeFrom, String date, DateFormat formatter) throws ParseException {
        boolean temp = true;
        Date timeFrom1;
        Date timeFrom2;
        java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date).getTime());
        if (checkValidSchedule(teacher, date, timeFrom)) {
            Collection<TblScheduleEntity> tblScheduleEntities = classroomEntity.getTblSchedulesById();
            for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                if (tblScheduleEntity1.getUsername().equals(teacher) &&
                        tblScheduleEntity1.getDate().toString().equals(teachingDate.toString())) {
                    timeFrom1 = parseTime(tblScheduleEntity1.getTimeFrom().toString());
                    timeFrom2 = parseTime(timeFrom);
                    if ((timeFrom2.getTime() - timeFrom1.getTime()) / 60000 == 105) {
                        tblScheduleEntity1.setSlots(tblScheduleEntity1.getSlots() + 1);
                        scheduleDAO.merge(tblScheduleEntity1);
                        temp = false;
                    }
                }
            }
            if (temp) {
                TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(teacher, classroomEntity.getId(), numberOfSlot, null, java.sql.Time.valueOf(timeFrom), 1,
                        teachingDate, true);
                scheduleDAO.persist(tblScheduleEntity);
            }
        }

    }

    public boolean checkValidSchedule(String teacher, String teachingDate, String teachingTime) {
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findScheduleWithDate(teacher, teachingDate, teachingTime);
        if (!tblScheduleEntities.isEmpty()) {
            return false;
        }
        return true;
    }

    public Date parseTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date timeFrom = null;
        try {
            timeFrom = df.parse(time);
        } catch (ParseException e) {
            System.out.println("erroe!!!!");
        }
        return timeFrom;
    }

    @RequestMapping(value = "download")
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = "D:\\Capstone\\trunk\\Document\\Book1.xlsx";

        File file = new File(filename);

        response.setContentType(new MimetypesFileTypeMap().getContentType(file));
        response.setContentLength((int) file.length());
        response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));

        InputStream is = new FileInputStream(file);
        FileCopyUtils.copy(is, response.getOutputStream());

        return null;
    }

    //Manually import
    @RequestMapping(value = "importManually")
    public String importManually(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("all") String all, @RequestParam("avai") String avai,
                                 @RequestParam("slot") String slot, @RequestParam("numberOfSlots") int numberOfSlots,
                                 @RequestParam("numberOfStudent") int numberOfStudent, @RequestParam("dateF") String dateFrom,
                                 @RequestParam("dateT") String dateTo) throws ParseException {
        slot = "Slot " + slot;
        String timeFrom = convertSlotToTime(slot);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dateF = new LocalDate(dateFrom);
        LocalDate dateT = new LocalDate(dateTo);
        if (dateTo.trim().length() > 0) {
            for (LocalDate date = dateF; date.isBefore(dateT.plusDays(1)); date = date.plusDays(1)) {
                java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date.toString()).getTime());
                TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, Integer.parseInt(avai), numberOfStudent, null, java.sql.Time.valueOf(timeFrom), numberOfSlots,
                        teachingDate, true);
                if (all.equals("0")) {
                    scheduleDAO.persist(tblScheduleEntity);
                } else {
                    List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, timeFrom);
                    if (!tblScheduleEntities.isEmpty()) {
                        tblScheduleEntities.get(0).setIsActive(false);
                        scheduleDAO.merge(tblScheduleEntities.get(0));
                        tblScheduleEntity.setClassroomId(Integer.parseInt(all));
                        scheduleDAO.persist(tblScheduleEntity);
                    } else {
                        tblScheduleEntity.setClassroomId(Integer.parseInt(all));
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                }
            }
        } else {
            java.sql.Date teachingDate = new java.sql.Date(formatter.parse(dateFrom).getTime());
            TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, Integer.parseInt(avai), numberOfStudent, null, java.sql.Time.valueOf(timeFrom), numberOfSlots,
                    teachingDate, true);
            if (all.equals("0")) {
                scheduleDAO.persist(tblScheduleEntity);
            } else {
                List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, timeFrom);
                if (!tblScheduleEntities.isEmpty()) {
                    tblScheduleEntities.get(0).setIsActive(false);
                    scheduleDAO.merge(tblScheduleEntities.get(0));
                    tblScheduleEntity.setClassroomId(Integer.parseInt(all));
                    scheduleDAO.persist(tblScheduleEntity);
                } else {
                    tblScheduleEntity.setClassroomId(Integer.parseInt(all));
                    scheduleDAO.persist(tblScheduleEntity);
                }
            }
        }


        return "redirect:/staff/schedule?ACTIVETAB=tab2";
    }

    //Search
    @RequestMapping(value = "searchSchedule")
    public String searchSchedule(HttpServletRequest request, @RequestParam("datefrom") String datefrom, @RequestParam("dateto") String dateto
            , @RequestParam("classroomId") String classroomId, @RequestParam("username") String username) {
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
        request.setAttribute("CLASSROOMS", tblClassroomEntities);
        request.setAttribute("TEACHERS", tblUserEntities);
        request.setAttribute("ACTIVELEFTTAB", "STAFF_SCHEDULE");
        List<LocalDate> teachingDate = new ArrayList<LocalDate>();
        //
        if(datefrom.trim().length()>0 && dateto.trim().length()>0){
            LocalDate dateFrom = new LocalDate(datefrom);
            LocalDate dateTo = new LocalDate(dateto);
            for (LocalDate date = dateFrom; date.isBefore(dateTo.plusDays(1)); date = date.plusDays(1)) {
                teachingDate.add(date);
            }
        }
        if(classroomId.equals("0")){
            classroomId = "";
        }
        if(username.equals("0")){
            username = "";
        }
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.advanceSearch(datefrom,dateto, classroomId, username);
        List<String> classroomName = new ArrayList<String>();
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
            if (classroomName.isEmpty()) {
                classroomName.add(tblScheduleEntity.getTblClassroomByClassroomId().getName());
            } else {
                if (!classroomName.contains(tblScheduleEntity.getTblClassroomByClassroomId().getName())) {
                    classroomName.add(tblScheduleEntity.getTblClassroomByClassroomId().getName());
                }
            }
        }
        request.setAttribute("TEACHINGDATE", teachingDate);
        request.setAttribute("CLASSROOMID", classroomName);
        request.setAttribute("SCHEDULES", tblScheduleEntities);
        return "Staff_MappingSchedule";
    }
}
