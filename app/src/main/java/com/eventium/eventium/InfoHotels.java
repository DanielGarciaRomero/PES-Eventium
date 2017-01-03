package com.eventium.eventium;

/**
 * Created by rober_000 on 03/01/2017.
 */

public class InfoHotels {
    private double lat;
    private double lng;
    private String name;
    private String direccion;


    public InfoHotels(double lat, double lng, String name, String direccion){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.direccion = direccion;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
