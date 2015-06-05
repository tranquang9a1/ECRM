package com.ecrm.Entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblEquipment", schema = "dbo", catalog = "ecrm")
public class TblEquipmentEntity {
    private int id;
    private int categoryId;
    private int classroomId;
    private int usingTime;
    private String position;
    private String status;
    private TblClassroomEntity tblClassroomByClassroomId;
    private TblEquipmentCategoryEntity tblEquipmentCategoryByCategoryId;
    private Collection<TblReportDetailEntity> tblReportDetailsById;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CategoryId")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "ClassroomId")
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    @Basic
    @Column(name = "UsingTime")
    public int getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(int usingTime) {
        this.usingTime = usingTime;
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
    @Column(name = "Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblEquipmentEntity that = (TblEquipmentEntity) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (classroomId != that.classroomId) return false;
        if (usingTime != that.usingTime) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + categoryId;
        result = 31 * result + classroomId;
        result = 31 * result + usingTime;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ClassroomId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblClassroomEntity getTblClassroomByClassroomId() {
        return tblClassroomByClassroomId;
    }

    public void setTblClassroomByClassroomId(TblClassroomEntity tblClassroomByClassroomId) {
        this.tblClassroomByClassroomId = tblClassroomByClassroomId;
    }

    @ManyToOne
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblEquipmentCategoryEntity getTblEquipmentCategoryByCategoryId() {
        return tblEquipmentCategoryByCategoryId;
    }

    public void setTblEquipmentCategoryByCategoryId(TblEquipmentCategoryEntity tblEquipmentCategoryByCategoryId) {
        this.tblEquipmentCategoryByCategoryId = tblEquipmentCategoryByCategoryId;
    }

    @OneToMany(mappedBy = "tblEquipmentByEquipmentId")
    public Collection<TblReportDetailEntity> getTblReportDetailsById() {
        return tblReportDetailsById;
    }

    public void setTblReportDetailsById(Collection<TblReportDetailEntity> tblReportDetailsById) {
        this.tblReportDetailsById = tblReportDetailsById;
    }
}
