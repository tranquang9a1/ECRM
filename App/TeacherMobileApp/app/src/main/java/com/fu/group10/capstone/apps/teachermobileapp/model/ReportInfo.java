package com.fu.group10.capstone.apps.teachermobileapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangTV on 6/4/2015.
 */
public class ReportInfo  implements Parcelable {
    private int reportId;
    private String username;
    private String fullname;
    private int classId;
    private String className;
    private String createTime;
    private String evaluate;
    private int status;
    private int damageLevel;
    private String changedRoom;
    private List<EquipmentReportInfo> equipments;

    public ReportInfo(int reportId, String username, String fullname, int classId, String className, String createTime, String evaluate,
                      int status, int damageLevel, String changedRoom, List<EquipmentReportInfo> equipments) {
        this.reportId = reportId;
        this.username = username;
        this.fullname = fullname;
        this.classId = classId;
        this.className = className;
        this.createTime = createTime;
        this.evaluate = evaluate;
        this.status = status;
        this.changedRoom = changedRoom;
        this.equipments = equipments;
        this.damageLevel = damageLevel;

    }

    public ReportInfo(Parcel in) {
        this.reportId = in.readInt();
        this.username = in.readString();
        this.fullname = in.readString();
        this.classId = in.readInt();
        this.className = in.readString();
        this.createTime = in.readString();
        this.evaluate = in.readString();
        this.status = in.readInt();
        this.changedRoom = in.readString();
        if (equipments == null) {
            equipments = new ArrayList<EquipmentReportInfo>();
        }
        in.readTypedList(equipments, EquipmentReportInfo.CREATOR);
        this.damageLevel = in.readInt();
    }

    public ReportInfo() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(reportId);
        parcel.writeString(username);
        parcel.writeString(fullname);
        parcel.writeInt(classId);
        parcel.writeString(className);
        parcel.writeString(createTime);
        parcel.writeString(evaluate);
        parcel.writeInt(status);
        parcel.writeString(changedRoom);
        parcel.writeTypedList(equipments);
        parcel.writeInt(damageLevel);
    }

    public static final Creator CREATOR = new Creator() {
        public ReportInfo createFromParcel(Parcel in) {
            return new ReportInfo(in);
        }

        public ReportInfo[] newArray(int size) {
            return new ReportInfo[size];
        }
    };

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public List<EquipmentReportInfo> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentReportInfo> equipments) {
        this.equipments = equipments;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getChangedRoom() {
        return changedRoom;
    }

    public void setChangedRoom(String changedRoom) {
        this.changedRoom = changedRoom;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
