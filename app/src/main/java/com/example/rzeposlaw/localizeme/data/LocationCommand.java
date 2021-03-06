package com.example.rzeposlaw.localizeme.data;

public class LocationCommand {

    private int longitude;
    private int latitude;

    public LocationCommand(int longitude, int latitude, long userID) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.userID = userID;
    }

    private long userID;


    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

}
