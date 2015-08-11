package com.ecrm.Service;

import com.ecrm.DAO.ClassroomDAO;
import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleConfigDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.DAO.UserDAO;
import com.ecrm.DTO.ScheduleDTO;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Constant;
import com.ecrm.Utils.SmsUtils;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by QuangTV on 7/22/2015.
 */

@Service
public class ScheduleService {

    public static int X = 1;
    @Autowired
    private ScheduleDAOImpl scheduleDAO;
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    ScheduleConfigDAOImpl scheduleConfigDAO;
    @Autowired
    UserDAOImpl userDAO;

    public boolean checkSchedule(int classId) {
        try {
            List<TblScheduleEntity> result = scheduleDAO.getScheduleNoFinishOfRoom(classId);
            if (result.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ScheduleDTO> getAllSchedule(String username) {
        List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
        List<TblScheduleEntity> listSchedule = scheduleDAO.getAllSchedulesOfUser(username);
        for (int i = 0; i < listSchedule.size(); i++) {
            TblScheduleEntity scheduleEntity = listSchedule.get(i);
            ScheduleDTO dto = new ScheduleDTO();
            dto.setClassId(scheduleEntity.getClassroomId());
            dto.setClassName(scheduleEntity.getTblClassroomByClassroomId().getName());
            dto.setTimeFrom(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeFrom().getTime() + "");
            int j = i + 1;
            if (j == listSchedule.size()) {
                dto.setTimeTo(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
            } else {
                TblScheduleEntity nextSchedule = listSchedule.get(j);
                if (nextSchedule.getClassroomId() == dto.getClassId() &&
                        nextSchedule.getScheduleConfigId() - scheduleEntity.getScheduleConfigId() == 1) {
                    dto.setTimeTo(nextSchedule.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
                    i = i + 1;
                } else {
                    dto.setTimeTo(scheduleEntity.getTblScheduleConfigByScheduleConfigId().getTimeTo().getTime() + "");
                }
            }
            dto.setDate(scheduleEntity.getDate() + "");
            result.add(dto);
        }
        return result;
    }

    public String importSchedule(String filePath) {
        try {
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
            String date = "";
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
                                insertSchedule(teacher.trim(), classroomEntity,
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
            return "redirect:/staff/schedule";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public void insertSchedule(String teacher, TblClassroomEntity classroomEntity,
                               int numberOfSlot, String timeFrom, String date, DateFormat formatter, int scheduleConfigId) throws ParseException {
        java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date).getTime());
        int classroomId = classroomEntity.getId();
        if (checkValidSchedule(teacher, date, scheduleConfigId, classroomId)) {
            TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(teacher, classroomId, numberOfSlot, null,
                    teachingDate, true, scheduleConfigId);
            scheduleDAO.persist(tblScheduleEntity);
        }

    }

    public boolean checkValidSchedule(String teacher, String teachingDate, int scheduleConfigId, int classroomId) {
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findScheduleWithDate(teacher, teachingDate, scheduleConfigId);
        if (!tblScheduleEntities.isEmpty()) {
            return false;
        }
        if (!scheduleDAO.findSpecificSchedule(teachingDate, scheduleConfigId, classroomId).isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean importManually(String username, String all, String avai, int slot, int numberOfSlots,
                                  int numberOfStudent, String dateFrom, String dateTo, String sms, int classroom) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new SimpleDateFormat("dd-MM-yyyy").parse(dateFrom);
            String dateString1 = new SimpleDateFormat("yyyy-MM-dd").format(d);
            LocalDate dateF = new LocalDate(dateString1);
            if (dateTo.trim().length() == 0) {
                dateTo = dateFrom;
            }
            Date d2 = new SimpleDateFormat("dd-MM-yyyy").parse(dateTo);
            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(d2);
            LocalDate dateT = new LocalDate(dateString2);

            for (int i = 0; i < numberOfSlots; i++) {
                List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigDAO.findScheduleConfigBySlot(slot + i);
                int scheduleConfigId = tblScheduleConfigEntities.get(0).getId();
                if (!dateTo.equals(dateFrom)) {
                    for (LocalDate date = dateF; date.isBefore(dateT.plusDays(1)); date = date.plusDays(1)) {
                        java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date.toString()).getTime());

                        if (all.equals("")) {
                            classroom = Integer.parseInt(avai);
                        } else {
                            TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(all);
                            classroom = classroomEntity.getId();
                        }
                        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, scheduleConfigId, classroom);
                        List<TblScheduleEntity> tblScheduleEntities2 = scheduleDAO.findScheduleWithDate(username, dateFrom, scheduleConfigId);
                        if (!tblScheduleEntities.isEmpty()) {
                            tblScheduleEntities.get(0).setIsActive(false);
                            tblScheduleEntities.get(0).setNote("Đã thay đổi");
                            scheduleDAO.merge(tblScheduleEntities.get(0));
                        }
                        if (!tblScheduleEntities2.isEmpty()) {
                            TblClassroomEntity classroomEntity = classroomDAO.find(classroom);
                            tblScheduleEntities2.get(0).setIsActive(false);
                            tblScheduleEntities2.get(0).setNote("Đã đổi sang phòng: " + classroomEntity.getName());
                            scheduleDAO.merge(tblScheduleEntities2.get(0));
                        }
                        TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, classroom, numberOfStudent, "Nhập tay",
                                teachingDate, true, scheduleConfigId);
                        scheduleDAO.persist(tblScheduleEntity);
                    }
                } else {
                    java.sql.Date teachingDate = new java.sql.Date(formatter.parse(dateFrom).getTime());
                    if (all.equals("")) {
                        classroom = Integer.parseInt(avai);
                    } else {
                        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(all);
                        classroom = classroomEntity.getId();
                    }
                    List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.findSpecificSchedule(dateFrom, scheduleConfigId, classroom);
                    List<TblScheduleEntity> tblScheduleEntities2 = scheduleDAO.findScheduleWithDate(username, dateFrom, scheduleConfigId);
                    if (!tblScheduleEntities.isEmpty()) {
                        tblScheduleEntities.get(0).setIsActive(false);
                        tblScheduleEntities.get(0).setNote("Đã thay đổi");
                        scheduleDAO.merge(tblScheduleEntities.get(0));
                    }
                    if (!tblScheduleEntities2.isEmpty()) {
                        TblClassroomEntity classroomEntity = classroomDAO.find(classroom);
                        tblScheduleEntities2.get(0).setIsActive(false);
                        tblScheduleEntities2.get(0).setNote("Đã đổi sang phòng: " + classroomEntity.getName());
                        scheduleDAO.merge(tblScheduleEntities2.get(0));
                    }
                    TblScheduleEntity tblScheduleEntity = new TblScheduleEntity(username, classroom, numberOfStudent, "Nhập tay",
                            teachingDate, true, scheduleConfigId);
                    scheduleDAO.persist(tblScheduleEntity);
                }
            }

            if (sms.equals("1")) {
                TblUserEntity tblUserEntity = userDAO.findUser(username);
                SmsUtils.sendMessage(tblUserEntity.getTblUserInfoByUsername().getPhone(), "Giáo viên " + username + " đổi sang phòng " + classroom);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean searchSchedule(String datefrom, String dateto
            , String classroomId, String username) {
        try {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TblScheduleEntity> advanceSearch(LocalDate dateFrom, LocalDate dateTo, String classroomId, String username) {
        List<TblScheduleEntity> tblScheduleEntities = scheduleDAO.advanceSearch(dateFrom.toString(), dateTo.toString(), classroomId, username);
        return tblScheduleEntities;
    }

    public List<String> classroomName(List<TblScheduleEntity> tblScheduleEntities, List<String> classroomName) {
        for (TblScheduleEntity tblScheduleEntity : tblScheduleEntities) {
            if (classroomName.isEmpty()) {
                classroomName.add(tblScheduleEntity.getTblClassroomByClassroomId().getName());
            } else {
                if (!classroomName.contains(tblScheduleEntity.getTblClassroomByClassroomId().getName())) {
                    classroomName.add(tblScheduleEntity.getTblClassroomByClassroomId().getName());
                }
            }
        }
        return classroomName;
    }

    public List<CrSdEntity> getCrSdEntity(List<CrSdEntity> crSdEntities, List<String> classroomName,
                                          List<TblScheduleConfigEntity> tblScheduleConfigEntities, List<TblScheduleEntity> tblScheduleEntities) {
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
                            if (tblScheduleEntity.getIsActive()) {
                                teacherSchedule.setStyle("font-style: italic;color:blue");
                            } else {
                                teacherSchedule.setStyle("font-style: italic;color:red");
                            }
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
        return crSdEntities;
    }
}
