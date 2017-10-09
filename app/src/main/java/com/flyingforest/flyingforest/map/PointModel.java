package com.flyingforest.flyingforest.map;

/**
 * Created by fata on 8/17/2017.
 */

public class PointModel {
    private String id;
    private String idEvent;
    private double lat;
    private double lng;
    private int temp;

    public PointModel() {
    }

    public PointModel(String id, String idEvent, double lat, double lng, int temp) {
        this.id = id;
        this.idEvent = idEvent;
        this.lat = lat;
        this.lng = lng;
        this.temp = temp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
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

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
