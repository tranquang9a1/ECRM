package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblScheduleEntity;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping(value = "schedule")
    public String mappingSchedule(HttpServletRequest request, @RequestParam("ACTIVETAB") String activeTab) {
        request.setAttribute("ACTIVETAB", activeTab);
        return "Staff_MappingSchedule";
    }

    @RequestMapping(value = "import")
    public String importFile(HttpServletRequest request) throws IOException, InvalidFormatException, ParseException {
        File file;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        String filePath = request.getContextPath();
        System.out.println(filePath);
        String contentType = request.getContentType();
        String fileName = "";
        if ((contentType.indexOf("multipart/form-data") >= 0)) {
            /*DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(maxMemSize);
            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File("D:\\Capstone\\trunk\\Document\\"));
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
                        // Write the file
                        if (fileName.lastIndexOf("\\") >= 0) {
                            file = new File(filePath +
                                    fileName.substring(fileName.lastIndexOf("\\")));
                        } else {
                            file = new File(filePath +
                                    fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }
                        fi.write(file);
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }*/
            //Read file excel
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            //Get the workbook instance for XLS file
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            //Get first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String mon = "";
            String tue = "";
            String wed = "";
            String thu = "";
            String fri = "";
            int classroom = 0;
            TblScheduleEntity tblScheduleEntity = new TblScheduleEntity();
            TblClassroomEntity classroomEntity = new TblClassroomEntity();
            while (rowIterator.hasNext()) {
                String slot = "";
                String timeFrom;
                String teacher = "";
                String teacher2 = "";
                String teacher3 = "";
                String teacher4 = "";
                String teacher5 = "";
                int numberOfSlot = 0;
                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (row.getRowNum() == 3) {
                    mon = formatter.format(row.getCell(2).getDateCellValue());
                    tue = formatter.format(row.getCell(3).getDateCellValue());
                    wed = formatter.format(row.getCell(4).getDateCellValue());
                    thu = formatter.format(row.getCell(5).getDateCellValue());
                    fri = formatter.format(row.getCell(6).getDateCellValue());
                }
                if (row.getRowNum() > 4) {
                    if (row.getCell(0).getNumericCellValue() != 0) {
                        classroom = (int) row.getCell(0).getNumericCellValue();
                    }
                    int classroomId = classroomDAO.getId(Integer.toString(classroom));
                    classroomEntity = classroomDAO.find(classroomId);
                    numberOfSlot = classroomEntity.getTblRoomTypeByRoomTypeId().getSlots();
                    slot = row.getCell(1).getStringCellValue();
                    timeFrom = convertSlotToTime(slot);
                    teacher = row.getCell(2).getStringCellValue();
                    if (!teacher.equals("")) {
                        tblScheduleEntity = new TblScheduleEntity(teacher, classroomId, numberOfSlot, null, Time.valueOf(timeFrom), 1, new java.sql.Date(formatter.parse(mon).getTime()));
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                    teacher2 = row.getCell(3).getStringCellValue();
                    if (!teacher2.equals("")) {
                        tblScheduleEntity = new TblScheduleEntity(teacher2, classroomId, numberOfSlot, null, Time.valueOf(timeFrom), 1, new java.sql.Date(formatter.parse(tue).getTime()));
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                    teacher3 = row.getCell(4).getStringCellValue();
                    if (!teacher3.equals("")) {
                        tblScheduleEntity = new TblScheduleEntity(teacher3, classroomId, numberOfSlot, null, Time.valueOf(timeFrom), 1, new java.sql.Date(formatter.parse(wed).getTime()));
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                    teacher4 = row.getCell(5).getStringCellValue();
                    if (!teacher4.equals("")) {
                        tblScheduleEntity = new TblScheduleEntity(teacher4, classroomId, numberOfSlot, null, Time.valueOf(timeFrom), 1, new java.sql.Date(formatter.parse(thu).getTime()));
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                    teacher5 = row.getCell(6).getStringCellValue();
                    if (!teacher5.equals("")) {
                        tblScheduleEntity = new TblScheduleEntity(teacher5, classroomId, numberOfSlot, null, Time.valueOf(timeFrom), 1, new java.sql.Date(formatter.parse(fri).getTime()));
                        scheduleDAO.persist(tblScheduleEntity);
                    }

                }

                System.out.println("");
            }
            fileInputStream.close();
            FileOutputStream out =
                    new FileOutputStream(new File(filePath + fileName));
            workbook.write(out);
            out.close();

        }
        return "redirect:/staff/schedule?ACTIVETAB=tab1";
    }

    public String convertSlotToTime(String slot) throws ParseException {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String timeFrom = timeFormat.format(timeFormat.parse("07:00:00"));
        if (slot.equals("Slot 2")) {
            timeFrom = timeFormat.format(timeFormat.parse("08:45:00"));
        }
        if (slot.equals("Slot 3")) {
            timeFrom = timeFormat.format(timeFormat.parse("10:30:00"));
        }
        if (slot.equals("Slot 4")) {
            timeFrom = timeFormat.format(timeFormat.parse("12:30:00"));
        }
        if (slot.equals("Slot 5")) {
            timeFrom = timeFormat.format(timeFormat.parse("14:15:00"));
        }
        if (slot.equals("Slot 6")) {
            timeFrom = timeFormat.format(timeFormat.parse("16:00:00"));
        }
        return timeFrom;
    }
}
