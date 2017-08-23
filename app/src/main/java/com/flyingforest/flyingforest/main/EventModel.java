package com.flyingforest.flyingforest.main;

/**
 * Created by fata on 8/17/2017.
 */

public class EventModel {
    private String id;
    private String title;
    private String location;
    private long time;
    private double lat;
    private double lng;

    public EventModel() {
    }

    public EventModel(String id, String title, String location, long time, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
