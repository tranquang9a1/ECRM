package com.fu.group10.apps.staff.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QuangTV on 6/13/2015.
 */
public class Equipment implements Parcelable {

    private int reportId;
    private int equipmentId;
    private String equipmentName;
    private int quantity;
    private boolean status;
    private String evaluate;
    private String damaged;




    public Equipment() {

    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Equipment(int reportId, String equipmentName, int quantity, boolean status, String evaluate, String damaged) {
        this.reportId = reportId;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.status = status;
        this.evaluate = evaluate;
        this.damaged = damaged;
    }

    public Equipment(Parcel in) {
        this.reportId = in.readInt();
        this.equipmentId = in.readInt();
        this.equipmentName = in.readString();
        this.quantity = in.readInt();
        this.damaged = in.readString();
        this.status = in.readByte() != 0;
        this.evaluate = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(reportId);
        parcel.writeInt(equipmentId);
        parcel.writeString(equipmentName);
        parcel.writeInt(quantity);
        parcel.writeString(damaged);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(evaluate);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }
}
