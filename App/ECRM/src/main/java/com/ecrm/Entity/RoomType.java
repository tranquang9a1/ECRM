package com.ecrm.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Htang on 5/26/2015.
 */
@Entity(name = "tblRoomType")
public class RoomType {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    private Integer id;

    @Basic
    @Column(name = "Slots", nullable = false, insertable = true, updatable = true, length = 45)
    private String slots;

    @Basic
    @Column(name = "VerticalRows", nullable = true, insertable = true, updatable = true)
    private Integer verticalRows;

    @Basic
    @Column(name = "HorizontalRows", nullable = true, insertable = true, updatable = true, length = 45)
    private String horizontalRows;

    @Basic
    @Column(name = "NoSlotsEachHRows", nullable = true, insertable = true, updatable = true, length = 45)
    private String noSlotsEachHRows;

    @Basic
    @Column(name = "AirConditioning", nullable = true, insertable = true, updatable = true)
    private boolean airConditioning;

    @Basic
    @Column(name = "Fan", nullable = true, insertable = true, updatable = true)
    private boolean fan;

    @Basic
    @Column(name = "Projector", nullable = true, insertable = true, updatable = true)
    private boolean projector;

    @Basic
    @Column(name = "speaker", nullable = true, insertable = true, updatable = true)
    private boolean speaker;

    @Basic
    @Column(name = "television", nullable = true, insertable = true, updatable = true)
    private boolean television;

    @Basic
    @Column(name = "CreateTime", nullable = true, insertable = true, updatable = true)
    private Timestamp createTime;

    public RoomType(){

    }

    public RoomType(String slots, Integer verticalRows, String horizontalRows, String noSlotsEachHRows,
                    boolean airConditioning, boolean fan, boolean projector, boolean speaker, boolean television, Timestamp createTime) {
        this.slots = slots;
        this.verticalRows = verticalRows;
        this.horizontalRows = horizontalRows;
        this.noSlotsEachHRows = noSlotsEachHRows;
        this.airConditioning = airConditioning;
        this.fan = fan;
        this.projector = projector;
        this.speaker = speaker;
        this.television = television;
        this.createTime = createTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public Integer getVerticalRows() {
        return verticalRows;
    }

    public void setVerticalRows(Integer verticalRows) {
        this.verticalRows = verticalRows;
    }

    public String getHorizontalRows() {
        return horizontalRows;
    }

    public void setHorizontalRows(String horizontalRows) {
        this.horizontalRows = horizontalRows;
    }

    public String getNoSlotsEachHRows() {
        return noSlotsEachHRows;
    }

    public void setNoSlotsEachHRows(String noSlotsEachHRows) {
        this.noSlotsEachHRows = noSlotsEachHRows;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public boolean isFan() {
        return fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public boolean isSpeaker() {
        return speaker;
    }

    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
