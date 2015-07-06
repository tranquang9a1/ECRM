package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;

/**
 * Created by Htang on 7/10/2015.
 */
@Entity
@Table(name = "tblScheduleConfig")
public class TblScheduleConfigEntity {
    private int id;
    private int slot;
    private Time timeFrom;
    private Time timeTo;
    private Collection<TblScheduleEntity> tblSchedulesById;

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
    @Column(name = "Slot")
    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblScheduleConfigEntity that = (TblScheduleConfigEntity) o;

        if (id != that.id) return false;
        if (slot != that.slot) return false;
        if (timeFrom != null ? !timeFrom.equals(that.timeFrom) : that.timeFrom != null) return false;
        if (timeTo != null ? !timeTo.equals(that.timeTo) : that.timeTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + slot;
        result = 31 * result + (timeFrom != null ? timeFrom.hashCode() : 0);
        result = 31 * result + (timeTo != null ? timeTo.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tblScheduleConfigByScheduleConfigId")
    public Collection<TblScheduleEntity> getTblSchedulesById() {
        return tblSchedulesById;
    }

    public void setTblSchedulesById(Collection<TblScheduleEntity> tblSchedulesById) {
        this.tblSchedulesById = tblSchedulesById;
    }
}
