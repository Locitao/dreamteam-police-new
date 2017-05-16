package com.dreamteam.police.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Loci on 1-5-2017.
 */
//@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("ICAN")
    private String ICAN;
    @JsonProperty("VIN")
    private String VIN;
    @JsonProperty("licenceplate")
    private String licenceplate;
    private String fuelType;
    private String color;

    public Car() {
    }

    public Car(Long id, String ICAN, String VIN, String licenceplate) {
        this.id = id;
        this.ICAN = ICAN;
        this.VIN = VIN;
        this.licenceplate = licenceplate;
    }

    public Car(String ICAN, String VIN, String licenceplate) {
        this.ICAN = ICAN;
        this.VIN = VIN;
        this.licenceplate = licenceplate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getICAN() {
        return ICAN;
    }

    public void setICAN(String ICAN) {
        this.ICAN = ICAN;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getLicenceplate() {
        return licenceplate;
    }

    public void setLicenceplate(String licenceplate) {
        this.licenceplate = licenceplate;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != null ? !id.equals(car.id) : car.id != null) return false;
        if (ICAN != null ? !ICAN.equals(car.ICAN) : car.ICAN != null) return false;
        if (VIN != null ? !VIN.equals(car.VIN) : car.VIN != null) return false;
        if (licenceplate != null ? !licenceplate.equals(car.licenceplate) : car.licenceplate != null) return false;
        if (fuelType != null ? !fuelType.equals(car.fuelType) : car.fuelType != null) return false;
        return color != null ? color.equals(car.color) : car.color == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ICAN != null ? ICAN.hashCode() : 0);
        result = 31 * result + (VIN != null ? VIN.hashCode() : 0);
        result = 31 * result + (licenceplate != null ? licenceplate.hashCode() : 0);
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}

