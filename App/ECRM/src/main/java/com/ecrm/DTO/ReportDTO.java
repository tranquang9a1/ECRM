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
    private List<ReportDetailDTO> equipments;

    public ReportDTO(int reportId, String username, String fullname, int classId, String createTime, String evaluate,
                     List<ReportDetailDTO> equipmentId) {
        this.reportId = reportId;
        this.username = username;
        this.fullname = fullname;
        this.classId = classId;
        this.createTime = createTime;
        this.evaluate = evaluate;
        this.equipments = equipmentId;
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


    public List<ReportDetailDTO> getEquipment() {
        return equipments;
    }

    public void setEquipment(List<ReportDetailDTO> equipmentId) {
        this.equipments = equipmentId;
    }
}
