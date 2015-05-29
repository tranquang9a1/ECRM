package com.ecrm.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Htang on 5/29/2015.
 */
public class TblReportDetailEntityPK implements Serializable {
    private int equipmentId;
    private int reportId;

    @Column(name = "EquipmentId")
    @Id
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Column(name = "ReportId")
    @Id
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblReportDetailEntityPK that = (TblReportDetailEntityPK) o;

        if (equipmentId != that.equipmentId) return false;
        if (reportId != that.reportId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipmentId;
        result = 31 * result + reportId;
        return result;
    }
}
