package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblReport", schema = "dbo", catalog = "ecrm")
public class TblReportEntity {
    private int id;
    private String userId;
    private int classRoomId;
    private Timestamp createTime;
    private String evaluate;
    private String description;
    private boolean status;
    private TblClassroomEntity tblClassroomByClassRoomId;
    private TblUserEntity tblUserByUserId;
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
    @Column(name = "UserId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ClassRoomId")
    public int getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(int classRoomId) {
        this.classRoomId = classRoomId;
    }

    @Basic
    @Column(name = "CreateTime")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "Evaluate")
    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblReportEntity that = (TblReportEntity) o;

        if (id != that.id) return false;
        if (classRoomId != that.classRoomId) return false;
        if (status != that.status) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (evaluate != null ? !evaluate.equals(that.evaluate) : that.evaluate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + classRoomId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (evaluate != null ? evaluate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ClassRoomId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblClassroomEntity getTblClassroomByClassRoomId() {
        return tblClassroomByClassRoomId;
    }

    public void setTblClassroomByClassRoomId(TblClassroomEntity tblClassroomByClassRoomId) {
        this.tblClassroomByClassRoomId = tblClassroomByClassRoomId;
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Username", nullable = false, insertable = false, updatable = false)
    public TblUserEntity getTblUserByUserId() {
        return tblUserByUserId;
    }

    public void setTblUserByUserId(TblUserEntity tblUserByUserId) {
        this.tblUserByUserId = tblUserByUserId;
    }

    @OneToMany(mappedBy = "tblReportByReportId")
    public Collection<TblReportDetailEntity> getTblReportDetailsById() {
        return tblReportDetailsById;
    }

    public void setTblReportDetailsById(Collection<TblReportDetailEntity> tblReportDetailsById) {
        this.tblReportDetailsById = tblReportDetailsById;
    }
}
