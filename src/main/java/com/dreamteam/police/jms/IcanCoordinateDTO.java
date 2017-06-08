package com.dreamteam.police.jms;

/**
 * Created by Loci on 7-6-2017.
 */
public class IcanCoordinateDTO {

    private String ICAN;
    private double lat;
    private double lng;

    public IcanCoordinateDTO() {
    }

    public IcanCoordinateDTO(String ICAN, double lat, double lng) {
        this.ICAN = ICAN;
        this.lat = lat;
        this.lng = lng;
    }

    public String getICAN() {
        return ICAN;
    }

    public void setICAN(String ICAN) {
        this.ICAN = ICAN;
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
