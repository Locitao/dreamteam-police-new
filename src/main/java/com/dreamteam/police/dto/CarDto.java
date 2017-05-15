package com.dreamteam.police.dto;

/**
 * Created by Loci on 14-5-2017.
 */
public class CarDto {

    private Long id;
    private String licensePlate;
    private String vin;
    private String ican;
    private String fuelType;
    private String vehicleColor;

    //region constructor getters and setters
    public CarDto() {
    }

    public CarDto(Long id, String licensePlate, String vin, String ican) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.ican = ican;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getIcan() {
        return ican;
    }

    public void setIcan(String ican) {
        this.ican = ican;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }
    //endregion
}
