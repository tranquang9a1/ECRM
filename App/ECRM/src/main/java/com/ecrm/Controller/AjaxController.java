package com.ecrm.Controller;

import com.ecrm.DAO.Impl.*;
import com.ecrm.Entity.*;
import com.ecrm.Service.*;
import com.ecrm.Utils.Utils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Htang on 6/12/2015.
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {
    @Autowired
    ClassroomDAOImpl classroomDAO;
    @Autowired
    UserDAOImpl userDAO;
    @Autowired
    ScheduleDAOImpl scheduleDAO;
    @Autowired
    ScheduleConfigDAOImpl scheduleConfigDAO;
    @Autowired
    RoomTypeDAOImpl roomType2DAO;
    @Autowired
    EquipmentDAOImpl equipmentDAO;
    @Autowired
    CategoryDAOImpl categoryDAO;
    @Autowired
    ChangeRoomService changeRoomService;

    @RequestMapping(value = "findClassroom")
    public
    @ResponseBody
    void findClassroom(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session = request.getSession();
        if (session != null) {
            int slot = Integer.parseInt(request.getParameter("slot"));
            String currentSlots = request.getParameter("numberOfStudent");
            int numberOfSlot = Integer.parseInt(request.getParameter("numberOfSlots"));
            List<Integer> listSlot = new ArrayList<Integer>();
            listSlot.add(slot);
            if (numberOfSlot > 1) {
                for (int i = 2; i <= numberOfSlot; i++) {
                    slot = slot+1;
                    listSlot.add(slot);
                }
            }
            String date = request.getParameter("dateT");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(dateT);
            //Tìm những phòng có chỗ ngồi phù hợp
            List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
            for (int i = 0; i < tblClassroomEntities.size(); i++) {
                int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
                if (numberOfStudent >= Integer.parseInt(currentSlots)*20/100) {
                    fitClassroom.add(tblClassroomEntities.get(i));
                }
            }
            tblClassroomEntities.clear();
            Iterator<TblClassroomEntity> iterator = fitClassroom.iterator();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            boolean isMatch = false;
            while (iterator.hasNext()) {
                isMatch = false;
                TblClassroomEntity classroomEntity = iterator.next();
                Collection<TblScheduleEntity> tblScheduleEntities = classroomEntity.getTblSchedulesById();
                if (tblScheduleEntities != null) {
                    for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                        //So sanh ngay
                        if (tblScheduleEntity1.getDate().getTime() == format.parse(dateString2).getTime() && tblScheduleEntity1.getIsActive()) {
                            //So sanh gio
                            int targetSlot = tblScheduleEntity1.getTblScheduleConfigByScheduleConfigId().getSlot();
                            for (int i = 0; i < listSlot.size(); i++) {
                                if (targetSlot == listSlot.get(i)) {
                                    iterator.remove();
                                    isMatch = true;
                                    break;
                                }
                            }
                        }
                        if (isMatch) {
                            break;
                        }
                    }
                }
            }
            response.setContentType("text/html");
            response.getWriter().write("<option value='0'>");
            response.getWriter().write("");
            response.getWriter().write("</option>");
            if (!fitClassroom.isEmpty()) {
                for (int j = 0; j < fitClassroom.size(); j++) {
                    response.getWriter().write("<option value='" + fitClassroom.get(j).getId() + "'>");
                    response.getWriter().write(fitClassroom.get(j).getName());
                    response.getWriter().write("</option>");
                }
            }
        }
    }

    //check username
    @RequestMapping(value = "checkUsername")
    public
    @ResponseBody
    String checkUsername(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("Username");
        List<TblUserEntity> tblUserEntities = userDAO.checkUsername(username);
        if (tblUserEntities.isEmpty()) {
            return "OK";
        }
        return "NO";
    }

    @RequestMapping(value = "checkClassroom")
    public
    @ResponseBody
    ValidateEntity checkClassroom(HttpServletRequest request, HttpServletResponse response) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);
        String username = request.getParameter("username");
        if (username == null || username.trim().length() == 0) {
            alert = "Phải nhập tài khoản!";
            validateEntity.setAlert(alert);
            validateEntity.setStatus(false);
            return validateEntity;
        } else {
            List<TblUserEntity> tblUserEntity = userDAO.checkUsername(username);
            if (tblUserEntity.isEmpty()) {
                alert = "Tài khoản không tồn tại!";
                validateEntity.setAlert(alert);
                validateEntity.setStatus(false);
                return validateEntity;
            }
        }
        String numberOfStudent = request.getParameter("numberOfStudent");
        if (numberOfStudent == null || numberOfStudent.trim().length() == 0) {
            alert = "Phải nhập số lượng học sinh!";
            validateEntity.setAlert(alert);
            validateEntity.setStatus(false);
            return validateEntity;
        }
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        if (dateFrom == null || dateFrom.trim().length() == 0) {
            alert = "Phải nhập ngày bắt đầu!";
            validateEntity.setAlert(alert);
            validateEntity.setStatus(false);
            return validateEntity;
        }
        if (dateFrom != null && dateFrom.trim().length() != 0 && dateTo != null && dateTo.trim().length() != 0) {
            LocalDate date1 = new LocalDate(dateFrom);
            LocalDate date2 = new LocalDate(dateTo);
            if (date2.isBefore(date1)) {
                alert = "Ngày kết thúc không được nhỏ hơn ngày bắt đầu!";
                validateEntity.setAlert(alert);
                validateEntity.setStatus(false);
                return validateEntity;
            }
        }
        int avai = Integer.parseInt(request.getParameter("avai"));
        String all = request.getParameter("all");
        if (avai == 0 && all.trim().length() == 0) {
            alert = "Phải chọn phòng học!";
            validateEntity.setAlert(alert);
            validateEntity.setStatus(false);
            return validateEntity;
        }
        if (all.trim().length() != 0) {
            TblClassroomEntity classroom = classroomDAO.getClassroomByName(all.trim());
            if (classroom == null) {
                alert = "Phòng học không tồn tại!";
                validateEntity.setAlert(alert);
                validateEntity.setStatus(false);
                return validateEntity;
            }
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
            for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                if (all.trim().equals(classroomEntity.getName())) {
                    alert = "Phòng học hiện không khả dụng!";
                    validateEntity.setAlert(alert);
                    validateEntity.setStatus(false);
                    return validateEntity;
                }
            }
        }
        return validateEntity;
    }

    @RequestMapping(value = "checkCreateClassroom")
    public
    @ResponseBody
    ValidateEntity checkCreateClassroom(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        String roomName = request.getParameter("roomName");
        if (roomName == null || roomName.trim().length() == 0) {
            alert = "Số phòng không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        if (!Utils.isNumeric(roomName)) {
            alert = "Số phòng không hợp lệ!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        String roomType = request.getParameter("roomType");
        if (roomType == null || roomType.trim().length() == 0) {
            alert = "Phải chọn loại phòng!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        TblClassroomEntity classroomEntity = classroomDAO.getClassroomByName(roomName.trim());
        if (classroomEntity != null) {
            alert = "Bạn có muốn cập nhật cho phòng " + classroomEntity.getName();
            status = true;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        return validateEntity;
    }

    @RequestMapping(value = "checkCreateRoomType")
    public
    @ResponseBody
    ValidateEntity checkCreateRoomType(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        String roomTypeName = request.getParameter("roomtypeName");
        if (roomTypeName == null || roomTypeName.trim().length() == 0) {
            alert = "Tên loại phòng không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        } else {
            String action = request.getParameter("action");
            if (action.equals("create")) {
                TblRoomTypeEntity roomTypeEntity = roomType2DAO.getRoomTypeByName(roomTypeName.trim());
                if (roomTypeEntity != null) {
                    alert = "Bạn có muốn cập nhật cho loại phòng " + roomTypeName;
                    status = true;
                    validateEntity = new ValidateEntity(alert, status);
                    return validateEntity;
                }
            }
        }
        return validateEntity;
    }

    @RequestMapping(value = "checkDeleteRoomType")
    public
    @ResponseBody
    ValidateEntity checkDeleteRoomType(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
        TblRoomTypeEntity tblRoomTypeEntity = roomType2DAO.find(roomTypeId);
        List<TblClassroomEntity> tblClassroomEntities = tblRoomTypeEntity.getTblClassroomsById();
        if (!tblClassroomEntities.isEmpty()) {
            alert = "Không thể xóa loại phòng này! Bạn phải gỡ ra khỏi phòng học trước!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        return validateEntity;
    }

    @RequestMapping(value = "checkEquipment")
    public
    @ResponseBody
    ValidateEntity checkEquipment(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        if (name == null || name.trim().length() == 0) {
            alert = "Tên thiết bị không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        }
        String serialNumber = request.getParameter("serialNumber");
        if (serialNumber == null || serialNumber.trim().length() == 0) {
            alert = "Số serial không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        } else {
            if (action.equals("insert")) {
                List<TblEquipmentEntity> equipmentEntities = equipmentDAO.getEquipmentBySN(serialNumber);
                if (!equipmentEntities.isEmpty()) {
                    alert = "Số serial đã tồn tại!";
                    status = false;
                    validateEntity = new ValidateEntity(alert, status);
                    return validateEntity;
                }
            }
        }
        String usingTime = request.getParameter("usingTime");
        if (usingTime != null) {
            if (usingTime.equals("")) {
                alert = "Thời gian sử dụng không được bỏ trống!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }
            if (!Utils.isDouble(usingTime)) {
                alert = "Thời gian sử dụng không được là chữ!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }

        }
        if (!action.equals("insert")) {
            String timeRemain = request.getParameter("timeRemain");
            if (timeRemain != null) {
                if (timeRemain.equals("")) {
                    alert = "Thời gian còn lại không được bỏ trống!";
                    status = false;
                    validateEntity = new ValidateEntity(alert, status);
                    return validateEntity;
                }
                if (!Utils.isDouble(timeRemain)) {
                    alert = "Thời gian còn lại không được là chữ!";
                    status = false;
                    validateEntity = new ValidateEntity(alert, status);
                    return validateEntity;
                }

            }
            if (Double.parseDouble(timeRemain) > Double.parseDouble(usingTime)) {
                alert = "Thời gian còn lại không được lớn hơn thời gian sử dụng";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }
        }


        return validateEntity;
    }

    @RequestMapping(value = "checkCategory")
    public
    @ResponseBody
    ValidateEntity checkCategory(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        String name = request.getParameter("name");
        if (name == null || name.trim().length() == 0) {
            alert = "Tên loại thiết bị không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        } else {
            List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntity = categoryDAO.getCategoryByName(name.trim());
            if (!tblEquipmentCategoryEntity.isEmpty()) {
                alert = "Tên loại thiết bị đã tồn tại!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }
        }

        String expiredTime = request.getParameter("expiredTime");
        if (expiredTime != null) {
            if (expiredTime.equals("")) {
                alert = "Thời gian báo hư không được bỏ trống!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }
            if (!Utils.isDouble(expiredTime)) {
                alert = "Thời báo hư không được là chữ!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }

        }
        return validateEntity;
    }

    @RequestMapping(value = "checkEditCategory")
    public
    @ResponseBody
    ValidateEntity checkEditCategory(HttpServletRequest request) {
        String alert = "";
        boolean status = true;
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setAlert(alert);
        validateEntity.setStatus(status);

        String name = request.getParameter("name");
        if (name == null || name.trim().length() == 0) {
            alert = "Tên loại thiết bị không được bỏ trống!";
            status = false;
            validateEntity = new ValidateEntity(alert, status);
            return validateEntity;
        } else {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            TblEquipmentCategoryEntity tblEquipmentCategoryEntity = categoryDAO.find(categoryId);
            if (!tblEquipmentCategoryEntity.getName().equals(name.trim())) {
                List<TblEquipmentCategoryEntity> tblEquipmentCategoryEntities = categoryDAO.getCategoryByName(name.trim());
                if (!tblEquipmentCategoryEntities.isEmpty()) {
                    alert = "Tên loại thiết bị đã tồn tại!";
                    status = false;
                    validateEntity = new ValidateEntity(alert, status);
                    return validateEntity;
                }
            }
        }
        String expiredTime = request.getParameter("expiredTime");
        if (expiredTime != null) {
            if (expiredTime.equals("")) {
                alert = "Thời gian báo hư không được bỏ trống!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }
            if (!Utils.isDouble(expiredTime)) {
                alert = "Thời báo hư không được là chữ!";
                status = false;
                validateEntity = new ValidateEntity(alert, status);
                return validateEntity;
            }

        }
        return validateEntity;
    }

    @Autowired
    ScheduleConfigService scheduleConfigService;
    @Autowired
    UserService userService;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "searchSchedule")
    public
    @ResponseBody
    void searchSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String datefrom = request.getParameter("dateFrom");
        String target = request.getParameter("tags");
        String action = request.getParameter("action");
        String username = "";
        String classroom = "";
        if (action.equals("0")) {
            username = target;
        } else {
            classroom = target;
        }

        String dateto = request.getParameter("dateTo");
        List<LocalDate> teachingDate = new ArrayList<LocalDate>();
        LocalDate dateFrom = new LocalDate();
        LocalDate dateTo = new LocalDate();
        //
        if (datefrom.trim().length() == 0 && dateto.trim().length() == 0) {
            dateTo = dateFrom.plusDays(5);
        }
        if (datefrom.trim().length() > 0 && dateto.trim().length() > 0) {
            Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(datefrom);
            Date dateF = new SimpleDateFormat("dd-MM-yyyy").parse(dateto);
            String dateString1 = new SimpleDateFormat("yyyy-MM-dd").format(dateT);
            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(dateF);
            dateFrom = new LocalDate(dateString1);
            dateTo = new LocalDate(dateString2);
        }
        if (datefrom.trim().length() > 0 && dateto.trim().length() == 0) {
            Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(datefrom);
            String dateString1 = new SimpleDateFormat("yyyy-MM-dd").format(dateT);
            dateFrom = new LocalDate(dateString1);
            dateTo = new LocalDate(dateString1);
        }
        if (datefrom.trim().length() == 0 && dateto.trim().length() > 0) {
            Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(dateto);
            String dateString1 = new SimpleDateFormat("yyyy-MM-dd").format(dateT);
            dateTo = new LocalDate(dateString1);
            dateFrom = dateTo.minusDays(5);
        }
        for (LocalDate date = dateFrom; date.isBefore(dateTo.plusDays(1)); date = date.plusDays(1)) {
            teachingDate.add(date);
        }


        List<TblScheduleEntity> tblScheduleEntities = scheduleService.advanceSearch(dateFrom, dateTo, classroom, username);
        List<String> classroomName = new ArrayList<String>();
        classroomName = scheduleService.classroomName(tblScheduleEntities, classroomName);

        List<CrSdEntity> crSdEntities = new ArrayList<CrSdEntity>();
        List<TblScheduleConfigEntity> tblScheduleConfigEntities = scheduleConfigService.findAll();
        crSdEntities = scheduleService.getCrSdEntity(crSdEntities, classroomName, tblScheduleConfigEntities, tblScheduleEntities);
        LocalTime localTime = new LocalTime();
        for (CrSdEntity crSdEntity : crSdEntities) {
            int rowspan = 0;
            List<TimeSchedule> timeSchedules = crSdEntity.getTimeSchedules();
            for (TimeSchedule timeSchedule : timeSchedules) {
                if (timeSchedule.getTeacherSchedules() != null) {
                    LocalTime timeFrom = LocalTime.parse(timeSchedule.getTimeFrom());
                    LocalTime timeTo = LocalTime.parse(timeSchedule.getTimeTo());
                    if (localTime.isBefore(timeTo) && localTime.isAfter(timeFrom)) {
                        timeSchedule.setStyle("tr-active");
                    } else {
                        timeSchedule.setStyle("");
                    }
                    rowspan += 1;
                }
            }
            crSdEntity.setRowspan(rowspan + 1);
        }
        response.setContentType("text/html");
        if (crSdEntities.isEmpty()) {
            response.getWriter().write("<h3>Không tìm thấy lịch!</h3>");
        } else {
            Collections.sort(crSdEntities, new CustomComparator());
            response.getWriter().write("<table class='overflow-y' style='position:inherit; max-height: 317px;'>");
            response.getWriter().write("<thead>");
            response.getWriter().write("<tr>");
            response.getWriter().write("<th>Phòng</th>");
            response.getWriter().write("<th>Giờ</th>");

            for (int i = 0; i < teachingDate.size(); i++) {
                response.getWriter().write("<th>" + teachingDate.get(i) + "</th>");
            }
            response.getWriter().write("</tr>");
            response.getWriter().write("</thead>");
            response.getWriter().write("<tbody>");
            for (int i = 0; i < crSdEntities.size(); i++) {
                CrSdEntity cs = crSdEntities.get(i);
                response.getWriter().write("<tr>");
                response.getWriter().write("<th rowspan='" + cs.getRowspan() + "'>" + cs.getRoomName() + "</th>");
                response.getWriter().write("</tr>");
                for (int j = 0; j < cs.getTimeSchedules().size(); j++) {
                    TimeSchedule tis = cs.getTimeSchedules().get(j);
                    if (tis.getTeacherSchedules() != null && !tis.getTeacherSchedules().isEmpty()) {
                        response.getWriter().write("<tr class='" + tis.getStyle() + "'>");
                        response.getWriter().write("<td style='color: white;background-color:rgb(49, 119, 188);'>" + tis.getTimeFrom() + "-" + tis.getTimeTo() + "</td>");
                        for (int y = 0; y < teachingDate.size(); y++) {
                            LocalDate td = teachingDate.get(y);
                            response.getWriter().write("<td>");
                            for (int k = 0; k < tis.getTeacherSchedules().size(); k++) {
                                TeacherSchedule tes = tis.getTeacherSchedules().get(k);
                                if (tes.getDate().equals(td.toString())) {
                                    String note = "";
                                    if (tes.getNote() != null) {
                                        note = tes.getNote();
                                    }
                                    response.getWriter().write(tes.getTeacher() + " <span style='" + tes.getStyle() + "'>" + note + "</span>");
                                }
                            }
                            response.getWriter().write("</td>");
                        }
                        response.getWriter().write("</tr>");
                    }
                }
            }
            response.getWriter().write("</tbody>");
            response.getWriter().write("</table>");
        }

    }

    public class CustomComparator implements Comparator<CrSdEntity> {
        @Override
        public int compare(CrSdEntity o1, CrSdEntity o2) {
            return o1.getRoomName().compareTo(o2.getRoomName());
        }
    }

    //create changeroom with date range
    @RequestMapping("getChangeRoom")
    public
    @ResponseBody
    void getChangeRoom(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        int classroomId = Integer.parseInt(request.getParameter("classroomId"));
        TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
        String tt = request.getParameter("timeTo");
        String tf = request.getParameter("timeFrom");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateT = new SimpleDateFormat("dd-MM-yyyy").parse(tf);
        String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(dateT);
        LocalDate timeFrom = new LocalDate(dateString2);
        LocalDate timeTo = timeFrom;
        if (tt.trim().length() == 0) {
            tt = tf;
            timeTo = timeFrom;
        } else {
            timeTo = new LocalDate(dateString2);
        }
        boolean tempMorning = true;
        boolean tempNoon = true;
        List<TblClassroomEntity> validClassrooms = classroomDAO.getAllClassroom();
        List<String> availableClassroom1 = new ArrayList<String>();
        List<String> availableClassroom2 = new ArrayList<String>();
        for (LocalDate date = timeFrom; date.isBefore(timeTo.plusDays(1)); date = date.plusDays(1)) {
            java.sql.Date teachingDate = new java.sql.Date(formatter.parse(date.toString()).getTime());
            List<TblScheduleEntity> morningSchedule = scheduleDAO.findAllScheduleInClassroomByDayTimeWithDate(classroomId, "06:00:00",
                    teachingDate);
            if (!morningSchedule.isEmpty()) {
                if (!tempMorning) {
                    break;
                }
                for (TblScheduleEntity currentSchedule : morningSchedule) {
                    List<String> classroom = Utils.getAvailableRoom(currentSchedule, validClassrooms);
                    if (!classroom.isEmpty()) {
                        if (availableClassroom1.isEmpty()) {
                            availableClassroom1 = classroom;
                        } else {
                            Iterator<String> it = availableClassroom1.iterator();
                            while (it.hasNext()) {
                                String room = it.next();
                                if (!classroom.contains(room)) {
                                    it.remove();
                                }
                            }
                        }
                    } else {
                        tempMorning = false;
                        break;
                    }
                }
            }


            List<TblScheduleEntity> noonSchedule = scheduleDAO.findAllScheduleInClassroomByDayTimeWithDate(classroomId, "12:00:00",
                    teachingDate);
            if (!noonSchedule.isEmpty()) {
                if (!tempNoon) {
                    break;
                }
                for (TblScheduleEntity currentSchedule : noonSchedule) {
                    List<String> classroom = Utils.getAvailableRoom(currentSchedule, validClassrooms);
                    if (!classroom.isEmpty()) {
                        if (availableClassroom2.isEmpty()) {
                            availableClassroom2 = classroom;
                        } else {
                            Iterator<String> it = availableClassroom2.iterator();
                            while (it.hasNext()) {
                                String room = it.next();
                                if (!classroom.contains(room)) {
                                    it.remove();
                                }
                            }
                        }
                    } else {
                        tempNoon = false;
                        break;
                    }
                }
            }
        }
        response.setContentType("text/html");
        response.getWriter().write("<div class='group-control'>");
        response.getWriter().write("<div class='name'>Phòng cho buổi sáng:</div>");
        response.getWriter().write("<div class='control'>");
        if (!availableClassroom1.isEmpty() && tempMorning) {
            availableClassroom1 = Utils.sortClassroom(availableClassroom1, classroomEntity.getName());
            availableClassroom1.remove(classroomEntity.getName());
            availableClassroom1 = changeRoomService.sortByRoomType(availableClassroom1, classroomEntity.getName());
            response.getWriter().write("<select id='morning' name='morning'>");
            for(String room: availableClassroom1){
                response.getWriter().write("<option value='"+room+"'>"+room+"</option>");
            }
            response.getWriter().write("</select>");
        }else {
            response.getWriter().write("<input type='hidden' value='0' id='morning' name='morning'>");
            response.getWriter().write("Không có phòng cho buổi sáng hoặc không có lịch dạy!");
        }
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");

        response.getWriter().write("<div class='group-control'>");
        response.getWriter().write("<div class='name'>Phòng cho buổi sáng:</div>");
        response.getWriter().write("<div class='control'>");
        if (!availableClassroom2.isEmpty() && tempNoon) {
            availableClassroom2 = Utils.sortClassroom(availableClassroom2, classroomEntity.getName());
            availableClassroom2.remove(classroomEntity.getName());
            availableClassroom2 = changeRoomService.sortByRoomType(availableClassroom2, classroomEntity.getName());
            response.getWriter().write("<select id='noon' name='noon'>");
            for(String room: availableClassroom2){
                response.getWriter().write("<option value='"+room+"'>"+room+"</option>");
            }
            response.getWriter().write("</select>");
        }else{
            response.getWriter().write("<input type='hidden' value='0' id='noon' name='noon'>");
            response.getWriter().write("Không có phòng cho buổi chiều hoặc không có lịch dạy!");
        }
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
    }
}
