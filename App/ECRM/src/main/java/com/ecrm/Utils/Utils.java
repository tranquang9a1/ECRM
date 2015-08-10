package com.ecrm.Utils;

import com.ecrm.DAO.Impl.ClassroomDAOImpl;
import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ClassroomDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.DTO.ReportDetailDTO;
import com.ecrm.Entity.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
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
        reportDTO.setClassId(reportEntity.getTblClassroomByClassRoomId().getId());
        reportDTO.setClassName(reportEntity.getTblClassroomByClassRoomId().getName());
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
    public static List<String> getAvailableRoom(TblScheduleEntity tblScheduleEntity, TblClassroomEntity currentClassroom, List<TblClassroomEntity> tblClassroomEntities) {
        List<TblEquipmentQuantityEntity> currentEquipment = currentClassroom.getTblRoomTypeByRoomTypeId().getTblEquipmentQuantityById();
        //lay so cho ngoi
        int currentSlots = tblScheduleEntity.getNumberOfStudents();
        //lay so tiet hoc
        int currentSlot = tblScheduleEntity.getTblScheduleConfigByScheduleConfigId().getSlot();
        //Tìm những phòng có chỗ ngồi phù hợp
        List<TblClassroomEntity> fitClassroom = new ArrayList<TblClassroomEntity>();
        for (int i = 0; i < tblClassroomEntities.size(); i++) {
            boolean isOk = false;
            int numberOfStudent = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getSlots();
            if (numberOfStudent >= currentSlots * 20 / 100) {
                List<TblEquipmentQuantityEntity> changeEquipment = tblClassroomEntities.get(i).getTblRoomTypeByRoomTypeId().getTblEquipmentQuantityById();
                if (!changeEquipment.isEmpty()) {
                    for (int j = 0; j < currentEquipment.size(); j++) {
                        if(!currentEquipment.get(j).getIsDelete()){
                            int temp = 0;
                            int categoryId = currentEquipment.get(j).getEquipmentCategoryId();
                            for (int k = 0; k < changeEquipment.size(); k++) {
                                if(!changeEquipment.get(k).getIsDelete()){
                                    if (categoryId == changeEquipment.get(k).getEquipmentCategoryId()) {
                                        temp += 1;
                                    }
                                }
                            }
                            if (temp > 0) {
                                isOk = true;
                            } else {
                                isOk = false;
                                break;
                            }
                        }

                    }
                } else {
                    isOk = false;
                }
                if (isOk) {
                    fitClassroom.add(tblClassroomEntities.get(i));
                }
            }
        }

        //So sánh ngày giờ với những schedule khác
        Iterator<TblClassroomEntity> iterator = fitClassroom.iterator();
        while (iterator.hasNext()) {
            TblClassroomEntity classroomEntity = iterator.next();
            Collection<TblScheduleEntity> tblScheduleEntities = classroomEntity.getTblSchedulesById();
            if (tblScheduleEntities != null) {
                for (TblScheduleEntity tblScheduleEntity1 : tblScheduleEntities) {
                    //So sanh ngay
                    if (tblScheduleEntity1.getDate().getTime() == tblScheduleEntity.getDate().getTime() && tblScheduleEntity1.getIsActive()) {
                        //So sanh gio
                        int targetSlot = tblScheduleEntity1.getTblScheduleConfigByScheduleConfigId().getSlot();
                        if (targetSlot == currentSlot) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < fitClassroom.size(); i++) {

        }

        List<String> classroom = new ArrayList<String>();
        for (TblClassroomEntity classroomEntity : fitClassroom) {
            classroom.add(classroomEntity.getName());
        }

        return classroom;
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
            int floorL = Integer.parseInt(room) / 100;
            int floorR = Integer.parseInt(room) / 100;
            for (int i = 0; i < left.size(); i++) {
                int currentLeftFloor = left.get(i) / 100;
                if (floorL - currentLeftFloor == 0) {
                    int maxL = left.get(i);
                    lstRoom.add(Integer.toString(maxL));
                } else {
                    for (int j = temp; j < right.size(); j++) {
                        int currentRightFloor = right.get(j) / 100;
                        if (currentRightFloor - floorR == 0) {
                            int maxR = right.get(j);
                            lstRoom.add(Integer.toString(maxR));
                            temp += 1;
                        } else {
                            floorR += 1;
                            break;
                        }
                    }
                    floorL = floorL - 1;
                    i -= 1;
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
        for (int i = 0; i < lstRoom.size(); i++) {
            if (i + 1 < lstRoom.size()) {
                int currentFloor = Integer.parseInt(lstRoom.get(i)) / 100;
                int nextFloor = Integer.parseInt(lstRoom.get(i + 1)) / 100;
                if (nextFloor - currentFloor == 0) {
                    for (int j = i; j >= 0; j--) {
                        String s1 = lstRoom.get(j);
                        String s2 = lstRoom.get(j + 1);
                        if (Integer.parseInt(s1) > Integer.parseInt(s2) && Integer.parseInt(s1) / 100 == currentFloor) {
                            lstRoom.set(j, s2);
                            lstRoom.set(j + 1, s1);
                        }
                    }
                }
            }

        }
        return lstRoom;
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
            if (d <= 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
            if (d < 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static final String LAST_RUN_TXT = "LastRun.txt";

    public static boolean checkCronJob() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constant.FILE_PATH + LAST_RUN_TXT));
            String sCurrentLine = "";

            while ((sCurrentLine = br.readLine()) != null) {
                if (new Date().getTime() < Long.parseLong(sCurrentLine)) {
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void writeFile(String fileName, String message) {
        try {

            File file = new File(fileName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(message);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

