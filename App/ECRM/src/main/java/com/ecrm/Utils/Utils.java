package com.ecrm.Utils;

import com.ecrm.DTO.AccountDTO;
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
        accountDTO.setClassId(1);

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
        reportDTO.setCreateTime("213123123123");
        List<TblReportDetailEntity> detail = reportEntity.getTblReportDetailsById();
        reportDTO.setEquipment(convertFromReportEntityToDTO(detail));
        reportDTO.setEvaluate(reportEntity.getEvaluate());
        reportDTO.setReportId(reportEntity.getId());

        return reportDTO;
    }

    public static List<ReportDetailDTO> convertFromReportEntityToDTO(List<TblReportDetailEntity> list) {
        List<ReportDetailDTO> result = new ArrayList<ReportDetailDTO>();
        for (int i = 0; i< list.size(); i++) {
            TblReportDetailEntity entity = list.get(i);
            ReportDetailDTO dto = new ReportDetailDTO();
            dto.setEquipmentName(entity.getTblEquipmentByEquipmentId().getTblEquipmentCategoryByCategoryId().getName());
            dto.setDamaged(entity.getDamagedLevel());
            dto.setDesription(entity.getSolution());
            result.add(dto);
        }

        return result;
    }
}
