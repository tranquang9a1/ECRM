package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Htang on 5/29/2015.
 */
@Entity
@Table(name = "tblReportDetail", schema = "dbo", catalog = "ecrm")
@IdClass(TblReportDetailEntityPK.class)
public class TblReportDetailEntity {
    private int equipmentId;
    private int reportId;
    private String damagedLevel;
    private boolean status;
    private String position;
    private String solution;
    private Timestamp resolveTime;
    private TblEquipmentEntity tblEquipmentByEquipmentId;
    private TblReportEntity tblReportByReportId;

    @Id
    @Column(name = "EquipmentId")
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Id
    @Column(name = "ReportId")
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "DamagedLevel")
    public String getDamagedLevel() {
        return damagedLevel;
    }

    public void setDamagedLevel(String damagedLevel) {
        this.damagedLevel = damagedLevel;
    }

    @Basic
    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "Position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "Solution")
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Basic
    @Column(name = "ResolveTime")
    public Timestamp getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(Timestamp resolveTime) {
        this.resolveTime = resolveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblReportDetailEntity that = (TblReportDetailEntity) o;

        if (equipmentId != that.equipmentId) return false;
        if (reportId != that.reportId) return false;
        if (status != that.status) return false;
        if (damagedLevel != null ? !damagedLevel.equals(that.damagedLevel) : that.damagedLevel != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (solution != null ? !solution.equals(that.solution) : that.solution != null) return false;
        if (resolveTime != null ? !resolveTime.equals(that.resolveTime) : that.resolveTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipmentId;
        result = 31 * result + reportId;
        result = 31 * result + (damagedLevel != null ? damagedLevel.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (solution != null ? solution.hashCode() : 0);
        result = 31 * result + (resolveTime != null ? resolveTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "EquipmentId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblEquipmentEntity getTblEquipmentByEquipmentId() {
        return tblEquipmentByEquipmentId;
    }

    public void setTblEquipmentByEquipmentId(TblEquipmentEntity tblEquipmentByEquipmentId) {
        this.tblEquipmentByEquipmentId = tblEquipmentByEquipmentId;
    }

    @ManyToOne
    @JoinColumn(name = "ReportId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblReportEntity getTblReportByReportId() {
        return tblReportByReportId;
    }

    public void setTblReportByReportId(TblReportEntity tblReportByReportId) {
        this.tblReportByReportId = tblReportByReportId;
    }
}
