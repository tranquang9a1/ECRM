package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleConfigDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.ScheduleConfigDAO;
import com.ecrm.Entity.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Time;
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
    @Autowired
    ScheduleConfigDAOImpl scheduleConfigDAO;

    @RequestMapping(value = "schedule")
    public String mappingSchedule(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
            List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
            List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
            request.setAttribute("SCHEDULECONFIG", tblScheduleConfigEntities);
            request.setAttribute("CLASSROOMS", tblClassroomEntities);
            request.setAttribute("TEACHERS", tblUserEntities);
            request.setAttribute("TABCONTROL", "STAFF_SCHEDULE");
            return "Staff_Schedule";
        } else {
            return "Login";
        }
    }

    @RequestMapping(value = "import")
    public String importFile(HttpServletRequest request) throws IOException, InvalidFormatException, ParseException {
        HttpSession session = request.getSession();
        if (session != null) {
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
                TblClassroomEntity classroomEntity = new TblClassroomEntity();
                while (rowIterator.hasNext()) {
                    String slot = "";
                    String timeFrom;
                    String teacher = "";
                    String timeTo;
                    int scheduleConfigId;

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
                        timeFrom = array[0].trim();
                        timeTo = array[1].trim();
                        List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findScheduleConfigByTFTT(timeFrom, timeTo);
                        scheduleConfigId = tblScheduleConfigEntities.get(0).getId();
                        count = 0;
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            if (cell.getColumnIndex() > 1) {
                                teacher = cell.getStringCellValue();
                                if (teacher.trim().length() > 0) {
                                    insertSchedule(teacher, classroomEntity,
                                            numberOfSlot, timeFrom, day.get(count), formatter, scheduleConfigId);
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
            return "redirect:/staff/Staff_Schedule";
        } else {
            return "Login";
        }
    }

    public void insertSchedule(String teacher, TblClassroomEntity classroomEntity,
                               int numberOfSlot, String timeFrom, String date, DateFormat formatter, int scheduleConfigId) throws ParseException {
        java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date).getTime());
        if (checkValidSchedule(teacher, date, scheduleConfigId)) {
            TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(teacher, classroomEntity.getId(), numberOfSlot, null, java.sql.Time.valueOf(timeFrom), 1,
                    teachingDate, true, scheduleConfigId);
            scheduleDAO.persist(tblScheduleEntity);
        }

    }

    public boolean checkValidSchedule(String teacher, String teachingDate, int scheduleConfigId) {
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findScheduleWithDate(teacher, teachingDate, scheduleConfigId);
        if (!tblScheduleEntities.isEmpty()) {
            return false;
        }
        return true;
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
                                 @RequestParam("slot") int slot, @RequestParam("numberOfSlots") int numberOfSlots,
                                 @RequestParam("numberOfStudent") int numberOfStudent, @RequestParam("dateF") String dateFrom,
                                 @RequestParam("dateT") String dateTo) throws ParseException {
        HttpSession session = request.getSession();
        if (session != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate dateF = new LocalDate(dateFrom);
            if (dateTo.trim().length() == 0) {
                dateTo = dateFrom;
            }
            LocalDate dateT = new LocalDate(dateTo);
            for(int i = 0; i<numberOfSlots; i++){
                List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findScheduleConfigBySlot(slot+i);
                String timeFrom = tblScheduleConfigEntities.get(0).getTimeFrom().toString();
                int scheduleConfigId = tblScheduleConfigEntities.get(0).getId();
                if (!dateTo.equals(dateFrom)) {
                    for (LocalDate date = dateF; date.isBefore(dateT.plusDays(1)); date = date.plusDays(1)) {
                        java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date.toString()).getTime());

                        int classroom;
                        if (all.equals("0")) {
                            classroom = Integer.parseInt(avai);
                        } else {
                            classroom = Integer.parseInt(all);
                        }
                        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, scheduleConfigId, classroom);
                        List<TblScheduleEntity> tblScheduleEntities2 = scheduleDAO.findScheduleWithDate(username, dateFrom, scheduleConfigId);
                        if (!tblScheduleEntities.isEmpty()) {
                            tblScheduleEntities.get(0).setIsActive(false);
                            tblScheduleEntities.get(0).setNote("Đã thay đổi");
                            scheduleDAO.merge(tblScheduleEntities.get(0));
                        }if(!tblScheduleEntities2.isEmpty() && !tblScheduleEntities.get(0).getUsername().equals(tblScheduleEntities2.get(0).getUsername())){
                            TblClassroomEntity classroomEntity = classroomDAO.find(classroom);
                            tblScheduleEntities2.get(0).setIsActive(false);
                            tblScheduleEntities2.get(0).setNote("Đã đổi sang phòng: " + classroomEntity.getName());
                            scheduleDAO.merge(tblScheduleEntities2.get(0));
                        }
                        TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, classroom, numberOfStudent, "Nhập tay", java.sql.Time.valueOf(timeFrom), 1,
                                teachingDate, true, scheduleConfigId);
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                } else {
                    java.sql.Date teachingDate = new java.sql.Date(formatter.parse(dateFrom).getTime());
                    int classroom;
                    if (all.equals("0")) {
                        classroom = Integer.parseInt(avai);
                    } else {
                        classroom = Integer.parseInt(all);
                    }
                    List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, scheduleConfigId, classroom);
                    List<TblScheduleEntity> tblScheduleEntities2 = scheduleDAO.findScheduleWithDate(username, dateFrom, scheduleConfigId);
                    if (!tblScheduleEntities.isEmpty()) {
                        tblScheduleEntities.get(0).setIsActive(false);
                        tblScheduleEntities.get(0).setNote("Đã thay đổi");
                        scheduleDAO.merge(tblScheduleEntities.get(0));
                    }if(!tblScheduleEntities2.isEmpty() && !tblScheduleEntities.get(0).getUsername().equals(tblScheduleEntities2.get(0).getUsername())){
                        TblClassroomEntity classroomEntity = classroomDAO.find(classroom);
                        tblScheduleEntities2.get(0).setIsActive(false);
                        tblScheduleEntities2.get(0).setNote("Đã đổi sang phòng: "+ classroomEntity.getName());
                        scheduleDAO.merge(tblScheduleEntities2.get(0));
                    }
                    TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, classroom, numberOfStudent, "Nhập tay", java.sql.Time.valueOf(timeFrom), 1,
                            teachingDate, true, scheduleConfigId);
                    scheduleDAO.persist(tblScheduleEntity);
                }
            }



            return "redirect:/staff/Staff_Schedule";
        } else {
            return "Login";
        }
    }

    //Search
    @RequestMapping(value = "searchSchedule")
    public String searchSchedule(HttpServletRequest request, @RequestParam("datefrom") String datefrom, @RequestParam("dateto") String dateto
            , @RequestParam("classroomId") String classroomId, @RequestParam("username") String username) {
        HttpSession session = request.getSession();
        if (session != null) {
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
            List<TblUserEntity> tblUserEntities = userDAO.findTeacher();
            request.setAttribute("CLASSROOMS", tblClassroomEntities);
            request.setAttribute("TEACHERS", tblUserEntities);
            request.setAttribute("TABCONTROL", "STAFF_SCHEDULE");
            List<LocalDate> teachingDate = new ArrayList<LocalDate>();
            LocalDate dateFrom = new LocalDate();
            LocalDate dateTo = new LocalDate();
            //
            if (datefrom.trim().length() == 0 && dateto.trim().length() == 0) {
                dateTo = dateFrom.plusDays(5);
            }
            if (datefrom.trim().length() > 0 && dateto.trim().length() > 0) {
                dateFrom = new LocalDate(datefrom);
                dateTo = new LocalDate(dateto);
            }
            if (datefrom.trim().length() > 0 && dateto.trim().length() == 0) {
                dateFrom = new LocalDate(datefrom);
                dateTo = new LocalDate(datefrom);
            }
            if (datefrom.trim().length() == 0 && dateto.trim().length() > 0) {
                dateTo = new LocalDate(dateTo);
                dateFrom = dateTo.minusDays(5);
            }
            for (LocalDate date = dateFrom; date.isBefore(dateTo.plusDays(1)); date = date.plusDays(1)) {
                teachingDate.add(date);
            }
            if (classroomId.equals("0")) {
                classroomId = "";
            }
            if (username.equals("0")) {
                username = "";
            }
            List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.advanceSearch(dateFrom.toString(), dateTo.toString(), classroomId, username);
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

            List<CrSdEntity> crSdEntities = new ArrayList<CrSdEntity>();
            List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
            for (int i = 0; i < classroomName.size(); i++) {
                List<TimeSchedule> timeSchedules = new ArrayList<TimeSchedule>();
                for (int j = -0; j < tblScheduleConfigEntities.size(); j++) {
                    TimeSchedule timeSchedule = new TimeSchedule();
                    timeSchedule.setTimeFrom(tblScheduleConfigEntities.get(j).getTimeFrom().toString());
                    timeSchedule.setTimeTo(tblScheduleConfigEntities.get(j).getTimeTo().toString());
                    timeSchedule.setScheduleConfigId(tblScheduleConfigEntities.get(j).getId());
                    timeSchedules.add(timeSchedule);
                }
                CrSdEntity crSdEntity = new CrSdEntity();
                crSdEntity.setRoomName(classroomName.get(i));
                crSdEntity.setTimeSchedules(timeSchedules);
                crSdEntities.add(crSdEntity);
            }

            String room = "";
            String time = "";

            for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
                for (int i = 0; i < crSdEntities.size(); i++) {
                    room = tblScheduleEntity.getTblClassroomByClassroomId().getName();
                    if (crSdEntities.get(i).getRoomName().equals(room)) {
                        List<TimeSchedule> timeSchedules1 = crSdEntities.get(i).getTimeSchedules();
                        int id = tblScheduleEntity.getScheduleConfigId();
                        for (int j = 0; j < 6; j++) {
                            if (timeSchedules1.get(j).getScheduleConfigId() == id) {
                                TeacherSchedule teacherSchedule = new TeacherSchedule();
                                teacherSchedule.setTeacher(tblScheduleEntity.getUsername());
                                teacherSchedule.setDate(tblScheduleEntity.getDate().toString());
                                teacherSchedule.setNote(tblScheduleEntity.getNote());
                                teacherSchedule.setIsActive(tblScheduleEntity.getIsActive());
                                List<TeacherSchedule> teacherSchedules = timeSchedules1.get(j).getTeacherSchedules();
                                if (teacherSchedules == null) {
                                    teacherSchedules = new ArrayList<TeacherSchedule>();
                                }
                                teacherSchedules.add(teacherSchedule);
                                timeSchedules1.get(j).setTeacherSchedules(teacherSchedules);
                                j = 6;
                            }
                        }
                    }

                }
            }
            boolean isEmpty = true;
            for (CrSdEntity crSdEntity : crSdEntities) {
                int rowspan = 0;
                List<TimeSchedule> timeSchedules = crSdEntity.getTimeSchedules();
                for (TimeSchedule timeSchedule : timeSchedules) {
                    if (timeSchedule.getTeacherSchedules() != null) {
                        isEmpty = false;
                        rowspan += 1;
                    }
                }
                crSdEntity.setRowspan(rowspan + 1);
            }
            Collections.sort(crSdEntities, new CustomComparator());
            request.setAttribute("SCHEDULECONFIG", tblScheduleConfigEntities);
            request.setAttribute("TEACHINGDATE", teachingDate);
            request.setAttribute("CLASSROOMID", classroomName);
            request.setAttribute("SCHEDULES", crSdEntities);
            request.setAttribute("TEACHER", username);
            request.setAttribute("CLASSROOM", classroomId);
            request.setAttribute("DATEFROM", datefrom);
            request.setAttribute("DATETO", dateto);
            request.setAttribute("ISEMPTY", isEmpty);


            return "Staff_Schedule";
        } else {
            return "Login";
        }
    }

    public class CustomComparator implements Comparator<CrSdEntity> {
        @Override
        public int compare(CrSdEntity o1, CrSdEntity o2) {
            return o1.getRoomName().compareTo(o2.getRoomName());
        }
    }

    @RequestMapping("createSchedule")
    public void createSchedule(HttpServletRequest request) throws IOException, InvalidFormatException {
        String filePath = "";
        ServletContext servletContext = request.getSession().getServletContext();
        filePath = servletContext.getRealPath("Schedule_Sample.xlsx");
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        //Get the workbook instance for XLS file
        Workbook workbook = WorkbookFactory.create(fileInputStream);

        //Get first sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        int i = 1;
        int j = 0;
        List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findAll();
        int size = tblScheduleConfigEntities.size();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            //For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell cell = null;
            if (row.getRowNum() > 4) {
                cell = sheet.getRow(row.getRowNum()).getCell(1);
                if(j<=size-1){
                    cell.setCellValue(tblScheduleConfigEntities.get(j).getTimeFrom()+" - "+tblScheduleConfigEntities.get(j).getTimeTo());
                }else{
                    row.removeCell(cell);
                }
                j+=1;
            }
            i+=1;
        }
        if(j<=size){
            for(int k = j; k<size; k++){
                Row row = workbook.getSheetAt(0).createRow(i);
                Cell cell = row.createCell(1);
                cell.setCellValue(tblScheduleConfigEntities.get(k).getTimeFrom()+" - "+tblScheduleConfigEntities.get(k).getTimeTo());
                i+=1;
            }
        }
        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
