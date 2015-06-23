package com.ecrm.Entity;

import com.ecrm.Utils.Enumerable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblReport")
public class TblReportEntity {
    private int id;
    private String username;
    private int classRoomId;
    private Timestamp createTime;
    private String evaluate;
    private int status;
    private TblClassroomEntity tblClassroomByClassRoomId;
    private TblUserEntity tblUserByUserId;
    private List<TblReportDetailEntity> tblReportDetailsById;

    public TblReportEntity(){}

    public TblReportEntity(String username, int roomId, String evaluate){
        this.username = username;
        this.classRoomId = roomId;
        this.createTime = new Timestamp(new Date().getTime());
        this.evaluate = evaluate;
        this.status = Enumerable.ReportStatus.NEW.getValue();
    }

    public TblReportEntity(String username, int classRoomId, Timestamp createTime, String evaluate, int status) {
        this.username = username;
        this.classRoomId = classRoomId;
        this.createTime = createTime;
        this.evaluate = evaluate;
        this.status = status;
    }

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
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "Status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (evaluate != null ? !evaluate.equals(that.evaluate) : that.evaluate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + classRoomId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (evaluate != null ? evaluate.hashCode() : 0);
        result = 31 * result + status;
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
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false, insertable = false, updatable = false)
    public TblUserEntity getTblUserByUserId() {
        return tblUserByUserId;
    }

    public void setTblUserByUserId(TblUserEntity tblUserByUserId) {
        this.tblUserByUserId = tblUserByUserId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tblReportByReportId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<TblReportDetailEntity> getTblReportDetailsById() {
        return tblReportDetailsById;
    }

    public void setTblReportDetailsById(List<TblReportDetailEntity> tblReportDetailsById) {
        this.tblReportDetailsById = tblReportDetailsById;
    }
}
