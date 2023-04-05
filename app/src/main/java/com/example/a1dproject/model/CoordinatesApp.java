package com.example.a1dproject.model;

public class CoordinatesApp {
    private double lat;
    private double lng;

    public CoordinatesApp(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }
}
