package com.fu.group10.capstone.apps.teachermobileapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QuangTV on 6/11/2015.
 */
public class ClassroomInfo  implements Parcelable{

    private int classid;
    private String className;
    private String timeFrom;
    private String timeTo;
    private String date;

    public ClassroomInfo(int classid, String className, String timeFrom, String timeTo, String date) {
        this.classid = classid;
        this.className = className;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date = date;
    }

    public ClassroomInfo() {
        
    }

    public ClassroomInfo(Parcel in) {
        this.classid = in.readInt();
        this.className = in.readString();
        this.timeFrom = in.readString();
        this.timeTo = in.readString();
        this.date = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(classid);
        parcel.writeString(className);
        parcel.writeString(timeFrom);
        parcel.writeString(timeTo);
        parcel.writeString(date);
    }

    public static final Creator CREATOR = new Creator() {
        public ClassroomInfo createFromParcel(Parcel in) {
            return new ClassroomInfo(in);
        }

        public ClassroomInfo[] newArray(int size) {
            return new ClassroomInfo[size];
        }
    };

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
