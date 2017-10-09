package com.flyingforest.flyingforest.data;

/**
 * Created by fata on 10/9/2017.
 */

public class DataModel {
    private double lat;
    private double lng;
    private int temp;

    public DataModel() {
    }

    public DataModel(double lat, double lng, int temp) {
        this.lat = lat;
        this.lng = lng;
        this.temp = temp;
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
