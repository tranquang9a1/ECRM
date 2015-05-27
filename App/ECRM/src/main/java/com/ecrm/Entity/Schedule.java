package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Htang on 5/27/2015.
 */
@Entity(name = "tblSchedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="UserId", nullable = false)
    private
    User user;

    @ManyToOne
    @JoinColumn(name="ClassroomId", nullable = false)
    private
    Classroom classroom;

    @Basic
    @Column(name = "NoStudents", nullable = false, insertable = false, updatable = true)
    private int noStudents;

    @Basic
    @Column(name = "Note", nullable = true, insertable = false, updatable = true, length = 45)
    private String note;

    @Basic
    @Column(name = "TimeFrom", nullable = false, insertable = false, updatable = true)
    private Time timeFrom;

    @Basic
    @Column(name = "TimeTo", nullable = false, insertable = false, updatable = true)
    private Time timeTo;

    @Basic
    @Column(name = "DateFrom", nullable = false, insertable = false, updatable = true)
    private Date dateFrom;

    @Basic
    @Column(name = "DateTo", nullable = false, insertable = false, updatable = true)
    private Date dateTo;

    public Schedule() {
    }

    public Schedule(User user, Classroom classroom, int noStudents, String note, Time timeFrom, Time timeTo, Date dateFrom, Date dateTo) {
        this.user = user;
        this.classroom = classroom;
        this.noStudents = noStudents;
        this.note = note;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getNoStudents() {
        return noStudents;
    }

    public void setNoStudents(int noStudents) {
        this.noStudents = noStudents;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Time getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
