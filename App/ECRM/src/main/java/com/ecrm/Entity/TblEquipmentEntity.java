package com.ecrm.Entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblEquipment")
public class TblEquipmentEntity {
    private int id;
    private int categoryId;
    private Integer classroomId;
    private String position;
    private Double timeRemain;
    private String name;
    private String serialNumber;
    private boolean status;
    private Boolean isDelete;
    private Double usingTime;
    private TblClassroomEntity tblClassroomByClassroomId;
    private TblEquipmentCategoryEntity tblEquipmentCategoryByCategoryId;
    private Collection<TblReportDetailEntity> tblReportDetailsById;

    @Id
    @GeneratedValue
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
    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
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
    @Column(name = "TimeRemain")
    public Double getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(Double timeRemain) {
        this.timeRemain = timeRemain;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SerialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
    @Column(name = "IsDelete")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }


    @Basic
    @Column(name = "UsingTIme")
    public Double getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Double usingTime) {
        this.usingTime = usingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblEquipmentEntity that = (TblEquipmentEntity) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (classroomId != null ? !classroomId.equals(that.classroomId) : that.classroomId != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (timeRemain != null ? !timeRemain.equals(that.timeRemain) : that.timeRemain != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + categoryId;
        result = 31 * result + (classroomId != null ? classroomId.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (timeRemain != null ? timeRemain.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ClassroomId", referencedColumnName = "Id", insertable = false, updatable = false)
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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblEquipmentByEquipmentId")
    public Collection<TblReportDetailEntity> getTblReportDetailsById() {
        return tblReportDetailsById;
    }

    public void setTblReportDetailsById(Collection<TblReportDetailEntity> tblReportDetailsById) {
        this.tblReportDetailsById = tblReportDetailsById;
    }

    public TblEquipmentEntity() {
    }

    public TblEquipmentEntity(int categoryId, Integer classroomId, String name, String serialNumber, String position, Double timeRemain, boolean status) {
        this.categoryId = categoryId;
        this.classroomId = classroomId;
        this.name = name;
        this.serialNumber = serialNumber;
        this.position = position;
        this.timeRemain = timeRemain;
        this.status = status;
    }

    public TblEquipmentEntity(int categoryId, Integer classroomId, String position, Double timeRemain, String name,
                              String serialNumber, boolean status, Boolean isDelete, Double usingTime) {
        this.categoryId = categoryId;
        this.classroomId = classroomId;
        this.position = position;
        this.timeRemain = timeRemain;
        this.name = name;
        this.serialNumber = serialNumber;
        this.status = status;
        this.isDelete = isDelete;
        this.usingTime = usingTime;
    }
}
