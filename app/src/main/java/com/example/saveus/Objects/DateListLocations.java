package com.example.saveus.Objects;

import java.util.ArrayList;

public class DateListLocations {

    private String titelDate;
    private int year;
    private int month;
    private int day;
    private ArrayList<LocationUser> mLocations = new ArrayList<>();
    private boolean isOpen = false;



    public DateListLocations(String titelDate, int year, int month, int day, LocationUser locationUser) {
        this.titelDate = titelDate;
        this.year = year;
        this.month = month;
        this.day = day;
        mLocations.add(locationUser);
    }

    public String getTitelDate() {
        return titelDate;
    }

    public void setTitelDate(String titelDate) {
        this.titelDate = titelDate;
    }

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

    public ArrayList<LocationUser> getmLocations() {
        return mLocations;
    }

    public void setmLocations(ArrayList<LocationUser> mLocations) {
        this.mLocations = mLocations;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
