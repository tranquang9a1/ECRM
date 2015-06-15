package com.fu.group10.apps.teacher.model;

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
    private String classId;
    private String createTime;
    private String evaluate;
    private int status;
    private int damageLevel;
    private List<EquipmentReportInfo> equipments;

    public ReportInfo(int reportId, String username, String fullname, String classId, String createTime, String evaluate,
                      int status, int damageLevel,  List<EquipmentReportInfo> equipments) {
        this.reportId = reportId;
        this.username = username;
        this.fullname = fullname;
        this.classId = classId;
        this.createTime = createTime;
        this.evaluate = evaluate;
        this.status = status;
        this.equipments = equipments;
        this.damageLevel = damageLevel;

    }

    public ReportInfo(Parcel in) {
        this.reportId = in.readInt();
        this.username = in.readString();
        this.fullname = in.readString();
        this.classId = in.readString();
        this.createTime = in.readString();
        this.evaluate = in.readString();
        this.status = in.readInt();
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
        parcel.writeString(classId);
        parcel.writeString(createTime);
        parcel.writeString(evaluate);
        parcel.writeInt(status);
        parcel.writeTypedList(equipments);
        parcel.writeInt(damageLevel);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
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
}
