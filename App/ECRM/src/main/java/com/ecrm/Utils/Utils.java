package com.ecrm.Utils;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ClassroomDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.DTO.ReportDetailDTO;
import com.ecrm.Entity.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Thuy_Tien on 6/8/2015.
 */
public class Utils {

    static ClassroomDAOImpl classroomDAO;


    public static AccountDTO convertFromUserToAccountDTO(TblUserEntity userEntity) {
        AccountDTO accountDTO = new AccountDTO();
        try {
            accountDTO.setFullname(userEntity.getTblUserInfoByUsername().getFullName());
            accountDTO.setPassword(userEntity.getPassword());
            accountDTO.setLastLogin(userEntity.getTblUserInfoByUsername().getLastLogin());
            accountDTO.setPhone(userEntity.getTblUserInfoByUsername().getPhone());
            accountDTO.setRole(userEntity.getTblRoleByRoleId().getName());
            accountDTO.setStatus(userEntity.isStatus());
            accountDTO.setUsername(userEntity.getUsername());

//            if (!userEntity.getTblRoleByRoleId().getName().equalsIgnoreCase("Staff")) {
//                List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
//                List<TblScheduleEntity> scheduleEntities = userEntity.getTblSchedulesByUsername();
//                for (int i = 0; i < scheduleEntities.size(); i++) {
//                    TblScheduleEntity schedule = scheduleEntities.get(i);
//                    ClassroomDTO classroom = new ClassroomDTO();
//
//
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    Date date = new Date();
//
//                    String currentDate = dateFormat.format(date);
//                    String scheduleDate = dateFormat.format(schedule.getDate());
//                    if (currentDate.equalsIgnoreCase(scheduleDate)) {
//                        classroom.setClassId(schedule.getClassroomId());
//                        classroom.setClassroomName(schedule.getTblClassroomByClassroomId().getName());
//
//                        SimpleDateFormat df  = new SimpleDateFormat("HH:mm");
//                        classroom.setTimeFrom(df.format(schedule.getTimeFrom().getTime()) + "");
//                        Date d = df.parse(schedule.getTimeFrom() + "");
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(d);
//                        calendar.add(Calendar.MINUTE, schedule.getSlots() * Constant.TIME_ONE_SLOT);
//                        classroom.setTimeTo(df.format(calendar.getTime()) + "");
//                        classrooms.add(classroom);
//                    }
//
//
//                }
//                accountDTO.setClassrooms(classrooms);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accountDTO;
    }

    public static List<ReportDTO> convertFromListEntityToListDTO(List<TblReportEntity> entities) {
        List<ReportDTO> result = new ArrayList<ReportDTO>();
        for (int i = 0; i < entities.size(); i++) {
            result.add(convertFromReportToReportDTO(entities.get(i)));
        }
        return result;
    }

    public static ReportDTO convertFromReportToReportDTO(TblReportEntity reportEntity) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUsername(reportEntity.getUsername());
        reportDTO.setFullname(reportEntity.getTblUserByUserId().getTblUserInfoByUsername().getFullName());
        reportDTO.setClassId(reportEntity.getTblClassroomByClassRoomId().getName());
        reportDTO.setCreateTime(reportEntity.getCreateTime().getTime() + "");
        List<TblReportDetailEntity> detail = reportEntity.getTblReportDetailsById();
        reportDTO.setEquipments(convertFromReportEntityToDTO(detail));
        reportDTO.setEvaluate(reportEntity.getEvaluate());
        reportDTO.setReportId(reportEntity.getId());
        reportDTO.setStatus(reportEntity.getStatus());
        reportDTO.setDamageLevel(reportEntity.getTblClassroomByClassRoomId().getDamagedLevel());
        reportDTO.setChangedRoom(reportEntity.getChangedRoom());

        return reportDTO;
    }

    public static List<ReportDetailDTO> convertFromReportEntityToDTO(List<TblReportDetailEntity> list) {
        List<ReportDetailDTO> result = new ArrayList<ReportDetailDTO>();
        for (int i = 0; i < list.size(); i++) {
            TblReportDetailEntity entity = list.get(i);
            ReportDetailDTO dto = new ReportDetailDTO();
            dto.setEquipmentName(entity.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName());
            dto.setDamaged(entity.getDamagedLevel());
            dto.setDescription(entity.getDescription());
            dto.setResolveTime(entity.getResolveTime());
            dto.setSolution(entity.getSolution());
            dto.setStatus(entity.isStatus());
            result.add(dto);
        }

        return result;
    }

    //Tìm phòng trống
    public static List<String> getAvailableRoom(TblScheduleEntity tblScheduleEntity, List<TblClassroomEntity> tblClassroomEntities) {

        //lay so cho ngoi
        int currentSlots = tblScheduleEntity.getNumberOfStudents();
        //lay so tiet hoc
        int slots = tblScheduleEntity.getSlots();
        //lay thoi gian voi ngay hien tai
        Date dateFrom = tblScheduleEntity.getDate();
        String timeFrom = tblScheduleEntity.getTimeFrom().toString();
        String datetime = dateFrom.toString() + " " + timeFrom;
        //phân rã giờ theo số tiết, mỗi tiết 90 phút, trả về List
        List<Date> time = timeFraction(datetime, slots);
        //Tìm những phòng có chỗ ngồi phù hợp
        List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
        for (int i = 0; i < tblClassroomEntities.size(); i++) {
            int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
            if (numberOfStudent >= currentSlots) {
                fitClassroom.add(tblClassroomEntities.get(i));
            }
        }

        //So sánh ngày giờ với những schedule khác
        for (int i = 0; i < fitClassroom.size(); i++) {
            Collection<TblScheduleEntity> tblScheduleEntities = fitClassroom.get(i).getTblSchedulesById();
            if (tblScheduleEntities != null) {
                for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                    //So sanh ngay
                    if (tblScheduleEntity1.getDate().getTime() == dateFrom.getTime()) {
                        //So sanh gio
                        String t = tblScheduleEntity1.getDate().toString() + " " + tblScheduleEntity1.getTimeFrom();
                        List<Date> listTimeToCompare = timeFraction(t, tblScheduleEntity1.getSlots());
                        if (timeComparation(time, listTimeToCompare)) {
                            fitClassroom.remove(i);
                            break;
                        }
                    }
                }
            }
        }

        List<String> classroom = new ArrayList<String>();
        for (TblClassroomEntity classroomEntity : fitClassroom) {
            classroom.add(classroomEntity.getName());
        }

        return classroom;
    }

    //Phân rã thời gian theo tiết học
    public static List<Date> timeFraction(String datetime, int slots) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeFrom1 = null;
        try {
            timeFrom1 = df.parse(datetime);
        } catch (ParseException e) {
            System.out.println("erroe!!!!");
        }

        List<Date> time = new ArrayList<Date>();
        df.format(timeFrom1);
        time.add(timeFrom1);
        for (int i = 1; i <= slots; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(timeFrom1);
            cal.add(Calendar.MINUTE, i * 105);
            Date t = cal.getTime();
            df.format(t);
            time.add(t);
        }
        return time;
    }

    //So sánh giờ
    public static Boolean timeComparation(List<Date> time, List<Date> timeToCompare) {
        boolean temp = false;
        int count = 0;
        for (int i = 0; i < time.size(); i++) {
            for (int j = 0; j < timeToCompare.size(); j++) {
                if (time.get(i).getTime() == timeToCompare.get(j).getTime()) {
                    count += 1;
                }
            }
        }
        if (count >= 2) {
            temp = true;
        }
        return temp;
    }

    public static List<String> sortClassroom(List<String> list, String room) {
        Collections.sort(list);
        List<String> lstRoom = new ArrayList<String>();
        lstRoom.add(room);
        for (String s : list) {
            lstRoom.add(s);
        }
        Collections.sort(lstRoom);
        int index = lstRoom.indexOf(room);
        if (index > 0) {
            List<Integer> right = new ArrayList<Integer>();
            List<Integer> left = new ArrayList<Integer>();
            for (int i = index + 1; i < lstRoom.size(); i++) {
                right.add(Integer.parseInt(lstRoom.get(i)));
            }
            for (int j = 0; j < index; j++) {
                left.add(Integer.parseInt(lstRoom.get(j)));
            }
            lstRoom.clear();
            int temp = 0;
            Collections.sort(left, Collections.reverseOrder());
            int floorL = Integer.parseInt(room)/100;
            int floorR = Integer.parseInt(room)/100;
            for (int i = 0; i < left.size(); i++) {
                int currentLeftFloor = left.get(i)/100;
                if (floorL - currentLeftFloor ==0) {
                    int maxL = left.get(i);
                    lstRoom.add(Integer.toString(maxL));
                }
                else{
                    for(int j = temp; j<right.size(); j++){
                        int currentRightFloor = right.get(j)/100;
                        if(currentRightFloor-floorR == 0){
                            int maxR = right.get(j);
                            lstRoom.add(Integer.toString(maxR));
                            temp += 1;
                        }else{
                            floorR+=1;
                            break;
                        }
                    }
                    floorL = floorL-1;
                    i-=1;
                }


            }
            for (int l : left) {
                if (!lstRoom.contains(Integer.toString(l))) {
                    lstRoom.add(Integer.toString(l));
                }
            }
            for (int r : right) {
                if (!lstRoom.contains(Integer.toString(r))) {
                    lstRoom.add(Integer.toString(r));
                }
            }

        }

        return lstRoom;
    }
}
