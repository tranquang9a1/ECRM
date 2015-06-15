package com.fu.group10.apps.teacher.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QuangTV on 6/12/2015.
 */
public class EquipmentReportInfo implements Parcelable{
    private String equipmentName;
    private String description;
    private String damaged;
    private boolean status;
    private String solution;
    private String resolveTime;

    public EquipmentReportInfo(String equipmentName, String description, String damaged, boolean status, String solution, String resolveTime) {
        this.equipmentName = equipmentName;
        this.description = description;
        this.damaged = damaged;
        this.status = status;
        this.solution = solution;
        this.resolveTime = resolveTime;
    }

    public EquipmentReportInfo(Parcel in) {
        this.equipmentName = in.readString();
        this.description = in.readString();
        this.damaged = in.readString();
        this.status = in.readByte() != 0;
        this.solution = in.readString();
        this.resolveTime = in.readString();
    }

    public EquipmentReportInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(equipmentName);
        parcel.writeString(description);
        parcel.writeString(damaged);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(solution);
        parcel.writeString(resolveTime);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EquipmentReportInfo createFromParcel(Parcel in) {
            return new EquipmentReportInfo(in);
        }

        public EquipmentReportInfo[] newArray(int size) {
            return new EquipmentReportInfo[size];
        }
    };

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(String resolveTime) {
        this.resolveTime = resolveTime;
    }
}
