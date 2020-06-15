package com.example.saveus.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    private String userName = "";
    private String fonNumber = "";
    private String dayOfBerth = "";
    private String email = "";
    private boolean receiveAlerts = true;
    private String language = "";

    public UserProfile(boolean receiveAlerts) {
        this.receiveAlerts = receiveAlerts;
    }

    protected UserProfile(Parcel in) {
        userName = in.readString();
        fonNumber = in.readString();
        dayOfBerth = in.readString();
        email = in.readString();
        receiveAlerts = in.readByte() != 0;
        language = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFonNumber() {
        return fonNumber;
    }

    public void setFonNumber(String fonNumber) {
        this.fonNumber = fonNumber;
    }

    public String getDayOfBerth() {
        return dayOfBerth;
    }

    public void setDayOfBerth(String dayOfBerth) {
        this.dayOfBerth = dayOfBerth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReceiveAlerts() {
        return receiveAlerts;
    }

    public void setReceiveAlerts(boolean receiveAlerts) {
        this.receiveAlerts = receiveAlerts;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(fonNumber);
        dest.writeString(dayOfBerth);
        dest.writeString(email);
        dest.writeByte((byte) (receiveAlerts ? 1 : 0));
        dest.writeString(language);
    }
}
