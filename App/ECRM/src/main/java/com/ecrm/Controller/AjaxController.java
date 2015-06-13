package com.ecrm.Controller;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.Entity.TblClassroomEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "findClassroom")
    public @ResponseBody
    void findClassroom(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String slot = request.getParameter("slot");
        String currentSlots= request.getParameter("numberOfStudent");
        String timeFrom = "07:00:00";
        if (slot.equals("2")) {
            timeFrom = "08:45:00";
        }
        if (slot.equals("3")) {
            timeFrom = "10:30:00";
        }
        if (slot.equals("4")) {
            timeFrom = "12:30:00";
        }
        if (slot.equals("5")) {
            timeFrom = "14:15:00";
        }
        if (slot.equals("6")) {
            timeFrom = "16:00:00";
        }
        String numberOfSlot = request.getParameter("numberOfSlots");
        String date = request.getParameter("date");
        String dateTime = date+" "+ timeFrom;
        List<Date> time = Utils.timeFraction(dateTime, Integer.parseInt(numberOfSlot));
        //Tìm những phòng có chỗ ngồi phù hợp
        List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
        for (int i = 0; i < tblClassroomEntities.size(); i++) {
             int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
            if (numberOfStudent >= Integer.parseInt(currentSlots)) {
                fitClassroom.add(tblClassroomEntities.get(i));
            }
        }
        tblClassroomEntities.clear();
        //So sánh ngày giờ với những schedule khác
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < fitClassroom.size(); i++) {
            Collection<TblScheduleEntity> tblScheduleEntities = fitClassroom.get(i).getTblSchedulesById();
            if (tblScheduleEntities != null) {
                for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                    //So sanh ngay
                    if (tblScheduleEntity1.getDate().getTime()==format.parse(date).getTime()) {
                        //So sanh gio
                        String t = tblScheduleEntity1.getDate().toString()+" "+tblScheduleEntity1.getTimeFrom();
                        List<Date> listTimeToCompare = Utils.timeFraction(t, tblScheduleEntity1.getSlots());
                        if(Utils.timeComparation(time, listTimeToCompare)){
                            fitClassroom.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        response.setContentType("text/html");
        response.getWriter().write("<option value='0'>");
        response.getWriter().write("---");
        response.getWriter().write("</option>");
        if(!fitClassroom.isEmpty()){
            for(int j = 0; j< fitClassroom.size(); j++){
                response.getWriter().write("<option value='"+fitClassroom.get(j).getId()+"'>");
                response.getWriter().write("Phòng "+fitClassroom.get(j).getName());
                response.getWriter().write("</option>");
            }
        }
    }


}
