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
            classroom.setClassId(i+1022);
            classroom.setClassroomName("Phòng 30" + i);
            classroom.setTimeFrom("1355245200000");
            classroom.setTimeTo("1355245200000");
            classrooms.add(classroom);
        }
        accountDTO.setClassrooms(classrooms);
        return accountDTO;
    }

    public static List<ReportDTO> convertFromReportToReportDTO(List<TblReportEntity> reportEntities) {
        List<ReportDTO> result = new ArrayList<ReportDTO>();
        for (int i = 0; i < reportEntities.size(); i++) {

        }
        return result;

    }

    public static List<ReportDTO> convertFromListEntityToListDTO(List<TblReportEntity> entities) {
        List<ReportDTO> result = new ArrayList<ReportDTO>();
        for (int i = 0; i < entities.size(); i++) {
            result.add(convertFromReportToReportDTO(entities.get(i)));
        }
        return  result;
    }

    public static ReportDTO convertFromReportToReportDTO(TblReportEntity reportEntity) {
        ReportDTO reportDTO  = new ReportDTO();
        reportDTO.setUsername(reportEntity.getUsername());
        reportDTO.setFullname(reportEntity.getTblUserByUserId().getTblUserInfoByUsername().getFullName());
        reportDTO.setClassId(reportEntity.getClassRoomId());
        reportDTO.setCreateTime("1355245200000");
        List<TblReportDetailEntity> detail = reportEntity.getTblReportDetailsById();
        reportDTO.setEquipments(convertFromReportEntityToDTO(detail));
        reportDTO.setEvaluate(reportEntity.getEvaluate());
        reportDTO.setReportId(reportEntity.getId());
        reportDTO.setStatus(reportEntity.isStatus());
        reportDTO.setDamageLevel(88);

        return reportDTO;
    }

    public static List<ReportDetailDTO> convertFromReportEntityToDTO(List<TblReportDetailEntity> list) {
        List<ReportDetailDTO> result = new ArrayList<ReportDetailDTO>();
        for (int i = 0; i< list.size(); i++) {
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

    //Check damaged level
    public static int checkDamagedLevel(TblReportEntity tblReportEntity) {
        int damagedLevel = 0;
        int projectorDamagedLevel = 0;
        int mayLanhDamagedLevel = 0;
        int tiviDamagedLevel = 0;
        int quatDamagedLevel = 0;
        int loaDamagedLevel = 0;
        int denDamagedLevel = 0;
        int banDamagedLevel = 0;
        int gheDamagedLevel = 0;
        int MayLanh = 0;
        int Quat = 0;
        int classroomId = tblReportEntity.getClassRoomId();
        TblClassroomEntity classroomEntity = classroomDAO.find(classroomId);
        TblRoomTypeEntity roomTypeEntity = classroomEntity.getTblRoomTypeByRoomTypeId();
        int chair = roomTypeEntity.getSlots();
        String[] row = roomTypeEntity.getHorizontalRows().split("-");
        int table = 0;
        for (int i = 0; i < row.length; i++) {
            table += Integer.parseInt(row[i]);
        }
        if (roomTypeEntity.getAirConditioning() > 0) {
            MayLanh = roomTypeEntity.getAirConditioning();
        }
        if (roomTypeEntity.getFan() > 0) {
            Quat = roomTypeEntity.getFan();
        }
        Collection<TblEquipmentEntity> damagedEquipment = new ArrayList<TblEquipmentEntity>();
        Collection<TblEquipmentEntity> tblEquipmentEntities = classroomEntity.getTblEquipmentsById();
        for (TblEquipmentEntity tblEquipmentEntity : tblEquipmentEntities) {
            if (!tblEquipmentEntity.isStatus()) {
                damagedEquipment.add(tblEquipmentEntity);
            }
        }
        if (!damagedEquipment.isEmpty()) {
            for (TblEquipmentEntity tblEquipmentEntity : damagedEquipment) {
                if (tblEquipmentEntity.getCategoryId() == 1) {
                    Collection<TblReportDetailEntity> projectors = tblEquipmentEntity.getTblReportDetailsById();
                    for (TblReportDetailEntity project : projectors) {
                        if (project.getDamagedLevel().equals("1")) {
                            projectorDamagedLevel = 20;
                        }
                        if (project.getDamagedLevel().equals("2")) {
                            projectorDamagedLevel = 30;
                        } else {
                            projectorDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 2) {
                    Collection<TblReportDetailEntity> tivis = tblEquipmentEntity.getTblReportDetailsById();
                    for (TblReportDetailEntity tivi : tivis) {
                        if (tivi.getDamagedLevel().equals("1")) {
                            tiviDamagedLevel = 20;
                        }
                        if (tivi.getDamagedLevel().equals("2")) {
                            tiviDamagedLevel = 30;
                        } else {
                            tiviDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 3) {
                    Collection<TblReportDetailEntity> mayLanhs = tblEquipmentEntity.getTblReportDetailsById();
                    for (TblReportDetailEntity mayLanh : mayLanhs) {
                        if (mayLanh.getDamagedLevel().equals("1")) {
                            mayLanhDamagedLevel += 10;
                        }
                        if (mayLanh.getDamagedLevel().equals("2")) {
                            mayLanhDamagedLevel += 15;
                        } else {
                            mayLanhDamagedLevel += 25;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 4) {
                    Collection<TblReportDetailEntity> quats = tblEquipmentEntity.getTblReportDetailsById();
                    if (MayLanh == 0) {
                        if ((quats.size()/Quat)*100 >=50) {
                            quatDamagedLevel = 50;
                        }
                    } else {
                        for (TblReportDetailEntity quat : quats) {
                            if (quat.getDamagedLevel().equals("1")) {
                                quatDamagedLevel += 1;
                            }
                            if (quat.getDamagedLevel().equals("2")) {
                                quatDamagedLevel += 3;
                            } else {
                                quatDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 5) {
                    Collection<TblReportDetailEntity> loas = tblEquipmentEntity.getTblReportDetailsById();
                    for (TblReportDetailEntity loa : loas) {
                        if (loa.getDamagedLevel().equals("1")) {
                            loaDamagedLevel = 1;
                        }
                        if (loa.getDamagedLevel().equals("2")) {
                            loaDamagedLevel = 3;
                        } else {
                            loaDamagedLevel = 5;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 6) {
                    Collection<TblReportDetailEntity> dens = tblEquipmentEntity.getTblReportDetailsById();
                    for (TblReportDetailEntity den : dens) {
                        if (den.getDamagedLevel().equals("1")) {
                            denDamagedLevel = 10;
                        }
                        if (den.getDamagedLevel().equals("2")) {
                            denDamagedLevel = 20;
                        } else {
                            denDamagedLevel = 50;
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 7) {
                    Collection<TblReportDetailEntity> bans = tblEquipmentEntity.getTblReportDetailsById();
                    if((bans.size()/table)/100>=50){
                        banDamagedLevel = 50;
                    }else {
                        for (TblReportDetailEntity ban : bans) {
                            if (ban.getDamagedLevel().equals("1")) {
                                banDamagedLevel += 2;
                            }
                            if (ban.getDamagedLevel().equals("2")) {
                                banDamagedLevel += 3;
                            } else {
                                banDamagedLevel += 5;
                            }
                        }
                    }
                }
                if (tblEquipmentEntity.getCategoryId() == 8) {
                    Collection<TblReportDetailEntity> ghes = tblEquipmentEntity.getTblReportDetailsById();
                    if((ghes.size()/chair)/100>=50){
                        gheDamagedLevel = 50;
                    }else {
                        for (TblReportDetailEntity ghe : ghes) {
                            if (ghe.getDamagedLevel().equals("1")) {
                                gheDamagedLevel += 1;
                            }
                            if (ghe.getDamagedLevel().equals("2")) {
                                gheDamagedLevel += 2;
                            } else {
                                gheDamagedLevel += 3;
                            }
                        }
                    }
                }
            }
        }
        damagedLevel = projectorDamagedLevel+mayLanhDamagedLevel+tiviDamagedLevel+loaDamagedLevel+quatDamagedLevel+denDamagedLevel
                +banDamagedLevel+gheDamagedLevel;
        if(damagedLevel>100){
            damagedLevel = 100;
        }
        return damagedLevel;
    }

    //Tìm phòng trống
    public List<TblClassroomEntity> getAvailableRoom(TblScheduleEntity tblScheduleEntity) {
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
        List<TblClassroomEntity> tblClassroomEntities = classroomDAO.findAll();
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


        String classroom = "";
        for (TblClassroomEntity classroomEntity : fitClassroom) {
            classroom += classroomEntity.getName();
        }
        System.out.println("--------Classroom: " + classroom);
        return fitClassroom;
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
