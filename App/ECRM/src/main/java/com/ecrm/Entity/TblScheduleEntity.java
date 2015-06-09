package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Htang on 6/5/2015.
 */
@Entity
@Table(name = "tblSchedule", schema = "dbo", catalog = "ecrm")
public class TblScheduleEntity {
    private int id;
    private String username;
    private int classroomId;
    private int numberOfStudents;
    private String note;
    private Time timeFrom;
    private int slots;
    private Date dateFrom;
    private Date dateTo;
    private TblClassroomEntity tblClassroomByClassroomId;
    private TblUserEntity tblUserByUserId;

    @Id
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
    @Column(name = "ClassroomId")
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    @Basic
    @Column(name = "NumberOfStudents")
    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
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
    @Column(name = "Slots")
    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
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
        if (classroomId != that.classroomId) return false;
        if (numberOfStudents != that.numberOfStudents) return false;
        if (slots != that.slots) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (timeFrom != null ? !timeFrom.equals(that.timeFrom) : that.timeFrom != null) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + classroomId;
        result = 31 * result + numberOfStudents;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (timeFrom != null ? timeFrom.hashCode() : 0);
        result = 31 * result + slots;
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

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false, insertable = false, updatable = false)
    public TblUserEntity getTblUserByUserId() {
        return tblUserByUserId;
    }

    public void setTblUserByUserId(TblUserEntity tblUserByUserId) {
        this.tblUserByUserId = tblUserByUserId;
    }
}
