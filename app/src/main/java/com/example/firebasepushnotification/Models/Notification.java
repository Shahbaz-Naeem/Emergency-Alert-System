package com.example.firebasepushnotification.Models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Notification {

    private String from;
    private String message;
    private String latitude;
    private String longitude;
    private Timestamp timestamp;

    public Notification() {
    }

    public Notification(String from, String message, String latitude, String longitude, Timestamp timestamp) {
        this.from = from;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
