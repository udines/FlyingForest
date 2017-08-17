package com.flyingforest.flyingforest.main;

/**
 * Created by fata on 8/17/2017.
 */

public class EventModel {
    private String id;
    private String title;
    private String location;
    private long time;
    private long lat;
    private long lng;

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

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
