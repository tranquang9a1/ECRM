package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DAO.Impl.ScheduleConfigDAOImpl;
import com.ecrm.DAO.Impl.ScheduleDAOImpl;
import com.ecrm.DAO.Impl.UserDAOImpl;
import com.ecrm.Entity.*;
import com.ecrm.Utils.Utils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
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
            if(numberOfSlot>1){
                for(int i = 2; i<=numberOfSlot; i++){
                    listSlot.add(slot+1);
                }
            }
            String date = request.getParameter("date");
            //Tìm những phòng có chỗ ngồi phù hợp
            List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getValidClassroom();
            for (int i = 0; i < tblClassroomEntities.size(); i++) {
                int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
                if (numberOfStudent >= Integer.parseInt(currentSlots)) {
                    fitClassroom.add(tblClassroomEntities.get(i));
                }
            }
            tblClassroomEntities.clear();
            Iterator<TblClassroomEntity> iterator = fitClassroom.iterator();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            boolean isMatch = false;
            while (iterator.hasNext()){
                isMatch = false;
                TblClassroomEntity classroomEntity = iterator.next();
                Collection<TblScheduleEntity> tblScheduleEntities = classroomEntity.getTblSchedulesById();
                if (tblScheduleEntities != null) {
                    for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                        //So sanh ngay
                        if (tblScheduleEntity1.getDate().getTime() == format.parse(date).getTime() && tblScheduleEntity1.getIsActive()) {
                            //So sanh gio
                            int targetSlot = tblScheduleEntity1.getTblScheduleConfigByScheduleConfigId().getSlot();
                            for(int i = 0; i< listSlot.size(); i++){
                                if(targetSlot==listSlot.get(i)){
                                    iterator.remove();
                                    isMatch = true;
                                    break;
                                }
                            }
                        }
                        if(isMatch){
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
        if (dateFrom != null && dateFrom.trim().length()!=0 && dateTo != null && dateTo.trim().length() !=0) {
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
        int all = Integer.parseInt(request.getParameter("all"));
        if (avai == 0 && all == 0) {
            alert = "Phải chọn phòng học!";
            validateEntity.setAlert(alert);
            validateEntity.setStatus(false);
            return validateEntity;
        }
        if (all != 0) {
            List<TblClassroomEntity> tblClassroomEntities = classroomDAO.getDamagedClassroom();
            for (TblClassroomEntity classroomEntity : tblClassroomEntities) {
                if (all == classroomEntity.getId()) {
                    alert = "Phòng học hiện không khả dụng!";
                    validateEntity.setAlert(alert);
                    validateEntity.setStatus(false);
                    return validateEntity;
                }
            }
        }
        return validateEntity;
    }

}
