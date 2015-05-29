package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Htang on 5/29/2015.
 */
@Entity
@Table(name = "tblSchedule", schema = "dbo", catalog = "ecrm")
public class TblScheduleEntity {
    private int id;
    private int noStudents;
    private String note;
    private Time timeFrom;
    private Time timeTo;
    private Date dateFrom;
    private Date dateTo;
    private TblClassroomEntity tblClassroomByClassroomId;
    private int userId;
    private int classroomId;
    private TblUserEntity tblUserByUserId;

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
    @Column(name = "NoStudents")
    public int getNoStudents() {
        return noStudents;
    }

    public void setNoStudents(int noStudents) {
        this.noStudents = noStudents;
    }

    @Basic
    @Column(name = "Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "TimeFrom")
    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    @Basic
    @Column(name = "TimeTo")
    public Time getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
    }

    @Basic
    @Column(name = "DateFrom")
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Basic
    @Column(name = "DateTo")
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblScheduleEntity that = (TblScheduleEntity) o;

        if (id != that.id) return false;
        if (noStudents != that.noStudents) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (timeFrom != null ? !timeFrom.equals(that.timeFrom) : that.timeFrom != null) return false;
        if (timeTo != null ? !timeTo.equals(that.timeTo) : that.timeTo != null) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + noStudents;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (timeFrom != null ? timeFrom.hashCode() : 0);
        result = 31 * result + (timeTo != null ? timeTo.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
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

    @Basic
    @Column(name = "UserId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ClassroomId")
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    public TblUserEntity getTblUserByUserId() {
        return tblUserByUserId;
    }

    public void setTblUserByUserId(TblUserEntity tblUserByUserId) {
        this.tblUserByUserId = tblUserByUserId;
    }
}
