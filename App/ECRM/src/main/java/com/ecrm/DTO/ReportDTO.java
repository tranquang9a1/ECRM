package com.ecrm.DTO;

import com.ecrm.Entity.TblReportDetailEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Thuy_Tien on 6/9/2015.
 */
public class ReportDTO {

    private int reportId;
    private String username;
    private String fullname;
    private int classId;
    private String createTime;
    private String evaluate;
    private int status;
    private int damageLevel;
    private List<ReportDetailDTO> equipments;

    public ReportDTO(int reportId, String username, String fullname, int classId, String createTime, String evaluate,
                     int status, int damageLevel, List<ReportDetailDTO> equipmentId) {
        this.reportId = reportId;
        this.username = username;
        this.fullname = fullname;
        this.classId = classId;
        this.createTime = createTime;
        this.evaluate = evaluate;
        this.status = status;
        this.equipments = equipmentId;
        this.damageLevel = damageLevel;
    }

    public ReportDTO() {
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ReportDetailDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<ReportDetailDTO> equipments) {
        this.equipments = equipments;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }
}
