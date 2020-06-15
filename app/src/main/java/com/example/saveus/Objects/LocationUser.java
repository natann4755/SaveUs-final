package com.example.saveus.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class LocationUser implements Parcelable , Comparable <LocationUser> {

    private double latitude = 0;
    private double longitude = 0;
    private String countryName = null;
    private String adsress = null ;
    private String endTime = null;
    private int endSecond = 0;
    private String time = null;
    private int yaer = 0;
    private int manth = 0;
    private int day = 0;
    private int startHour = 0;
    private int startMinit = 0;
    private int startSecond = 0;
    private String titelDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationUser that = (LocationUser) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                endSecond == that.endSecond &&
                yaer == that.yaer &&
                manth == that.manth &&
                day == that.day &&
                startHour == that.startHour &&
                startMinit == that.startMinit &&
                startSecond == that.startSecond &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(adsress, that.adsress) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(time, that.time) &&
                Objects.equals(titelDay, that.titelDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, countryName, adsress, endTime, endSecond, time, yaer, manth, day, startHour, startMinit, startSecond, titelDay);
    }

    public LocationUser  (LocationUser locationUser1) {
        this.latitude = locationUser1.getLatitude();
        this.longitude = locationUser1.getLongitude();
        this.countryName = locationUser1.getCountryName();
        this.adsress = locationUser1.getAdsress();
        this.endTime = locationUser1.getEndTime();
        this.endSecond = locationUser1.getEndSecond();
        this.time = locationUser1.getTime();
        this.yaer = locationUser1.getYaer();
        this.manth = locationUser1.getManth();
        this.day = locationUser1.getDay();
        this.startHour = locationUser1.getStartHour();
        this.startMinit = locationUser1.getStartMinit();
        this.startSecond = locationUser1.getStartSecond();
    }

    public LocationUser(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected LocationUser(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        countryName = in.readString();
        adsress = in.readString();
        endTime = in.readString();
        endSecond = in.readInt();
        time = in.readString();
        yaer = in.readInt();
        manth = in.readInt();
        day = in.readInt();
        startHour = in.readInt();
        startMinit = in.readInt();
        startSecond = in.readInt();
    }

    public static final Creator<LocationUser> CREATOR = new Creator<LocationUser>() {
        @Override
        public LocationUser createFromParcel(Parcel in) {
            return new LocationUser(in);
        }

        @Override
        public LocationUser[] newArray(int size) {
            return new LocationUser[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAdsress() {
        return adsress;
    }

    public void setAdsress(String adsress) {
        this.adsress = adsress;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEndSecond() {
        return endSecond;
    }

    public void setEndSecond(int endSecond) {
        this.endSecond = endSecond;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYaer() {
        return yaer;
    }

    public void setYaer(int yaer) {
        this.yaer = yaer;
    }

    public int getManth() {
        return manth;
    }

    public void setManth(int manth) {
        this.manth = manth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinit() {
        return startMinit;
    }

    public void setStartMinit(int startMinit) {
        this.startMinit = startMinit;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(int startSecond) {
        this.startSecond = startSecond;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(countryName);
        dest.writeString(adsress);
        dest.writeString(endTime);
        dest.writeInt(endSecond);
        dest.writeString(time);
        dest.writeInt(yaer);
        dest.writeInt(manth);
        dest.writeInt(day);
        dest.writeInt(startHour);
        dest.writeInt(startMinit);
        dest.writeInt(startSecond);
    }

    @Override
    public int compareTo(LocationUser element) {
        if (        element.getYaer() > this.yaer
                || (element.getYaer() == this.yaer && element.getManth() > this.manth)
                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() > this.day)
                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
                &&  element.getStartHour() > this.startHour)
                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
                && element.getStartHour() == this.startHour && element.getStartMinit() > this.getStartMinit())
                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
                && element.getStartHour() == this.startHour && element.getStartMinit() == this.startMinit && element.getStartSecond() > this.startSecond)){
            return 1;
        }
//        if (        element.getYaer() == this.yaer
//                || (element.getYaer() == this.yaer && element.getManth() == this.manth)
//                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day)
//                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
//                &&  element.getStartHour() == this.startHour)
//                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
//                && element.getStartHour() == this.startHour && element.getStartMinit() == this.getStartMinit())
//                || (element.getYaer() == this.yaer && element.getManth() == this.manth && element.getDay() == this.day
//                && element.getStartHour() == this.startHour && element.getStartMinit() == this.startMinit && element.getStartSecond() == this.startSecond)){
//            return 0;
//        }
        else {
            return -1;
        }
    }
}
