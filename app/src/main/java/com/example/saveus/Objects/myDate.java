package com.example.saveus.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class myDate extends Calendar implements Parcelable {
   private int year;
   private int month;
   private int day;


    protected myDate(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
    }

    public static final Creator<myDate> CREATOR = new Creator<myDate>() {
        @Override
        public myDate createFromParcel(Parcel in) {
            return new myDate(in);
        }

        @Override
        public myDate[] newArray(int size) {
            return new myDate[size];
        }
    };

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    protected void computeTime() {

    }

    @Override
    protected void computeFields() {

    }

    @Override
    public void add(int field, int amount) {

    }

    @Override
    public void roll(int field, boolean up) {

    }

    @Override
    public int getMinimum(int field) {
        return 0;
    }

    @Override
    public int getMaximum(int field) {
        return 0;
    }

    @Override
    public int getGreatestMinimum(int field) {
        return 0;
    }

    @Override
    public int getLeastMaximum(int field) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }
}
