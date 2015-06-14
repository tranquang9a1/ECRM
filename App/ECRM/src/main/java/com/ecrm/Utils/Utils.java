package com.ecrm.Utils;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ClassroomDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.DTO.ReportDetailDTO;
import com.ecrm.Entity.*;

import java.sql.Timestamp;
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
        accountDTO.setFullname(userEntity.getTblUserInfoByUsername().getFullName());
        accountDTO.setPassword(userEntity.getPassword());
        accountDTO.setLastLogin(userEntity.getTblUserInfoByUsername().getLastLogin());
        accountDTO.setPhone(userEntity.getTblUserInfoByUsername().getPhone());
        accountDTO.setRole(userEntity.getTblRoleByRoleId().getName());
        accountDTO.setStatus(userEntity.isStatus());
        accountDTO.setUsername(userEntity.getUsername());
        List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();

        for (int i = 0; i < 7; i++) {
            ClassroomDTO classroom = new ClassroomDTO();
            classroom.setClassId(i + 1022);
            classroom.setClassroomName("Phòng 30" + i);
            classroom.setTimeFrom("1355245200000");
            classroom.setTimeTo("1355245200000");
            classrooms.add(classroom);
        }
        accountDTO.setClassrooms(classrooms);
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
        reportDTO.setClassId(reportEntity.getClassRoomId());
        reportDTO.setCreateTime("1355245200000");
        List<TblReportDetailEntity> detail = reportEntity.getTblReportDetailsById();
        reportDTO.setEquipments(convertFromReportEntityToDTO(detail));
        reportDTO.setEvaluate(reportEntity.getEvaluate());
        reportDTO.setReportId(reportEntity.getId());
        reportDTO.setStatus(reportEntity.getStatus());
        reportDTO.setDamageLevel(88);

        return reportDTO;
    }

    public static List<ReportDetailDTO> convertFromReportEntityToDTO(List<TblReportDetailEntity> list) {
        List<ReportDetailDTO> result = new ArrayList<ReportDetailDTO>();
        for (int i = 0; i < list.size(); i++) {
            TblReportDetailEntity entity = list.get(i);
            ReportDetailDTO dto = new ReportDetailDTO();
            dto.setEquipmentName(entity.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName());
            dto.setDamaged(entity.getDamagedLevel());
            dto.setDescription(entity.getSolution());
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
        //Kiểm trả thời điểm hiện tại
        //So sánh với từng múi giờ nếu từ thời điểm hiện tại đến cuối tiết ít hơn 20 phút thì bỏ
        //Nếu nhiều hơn 20p thì vẫn lấy.
        Date now = new Date();
        for (int i = 0; i < time.size(); i++) {
            long from = (now.getTime() - time.get(i).getTime()) / 60000;
            if (from >= 70) {
                time.remove(i);
            }
        }
        //Tìm những phòng có chỗ ngồi phù hợp
        List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
        for (int i = 0; i < tblClassroomEntities.size(); i++) {
            int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
            if (numberOfStudent >= currentSlots) {
                fitClassroom.add(tblClassroomEntities.get(i));
            }
        }

        tblClassroomEntities.clear();

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
}
