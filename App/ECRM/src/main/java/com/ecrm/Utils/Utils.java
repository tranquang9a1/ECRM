package com.ecrm.Utils;

import com.ecrm.DTO.AccountDTO;
import com.ecrm.DTO.ClassroomDTO;
import com.ecrm.DTO.ReportDTO;
import com.ecrm.DTO.ReportDetailDTO;
import com.ecrm.Entity.TblReportDetailEntity;
import com.ecrm.Entity.TblReportEntity;
import com.ecrm.Entity.TblScheduleEntity;
import com.ecrm.Entity.TblUserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thuy_Tien on 6/8/2015.
 */
public class Utils {

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
}
