package com.fu.group10.apps.teacher.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QuangTV on 6/7/2015.
 */
public class DamagedEquipment implements Parcelable{

    public String name;
    public String position;


    public DamagedEquipment(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public DamagedEquipment(Parcel in) {
        this.name = in.readString();
        this.position = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(position);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DamagedEquipment createFromParcel(Parcel in) {
            return new DamagedEquipment(in);
        }

        public DamagedEquipment[] newArray(int size) {
            return new DamagedEquipment[size];
        }
    };
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
