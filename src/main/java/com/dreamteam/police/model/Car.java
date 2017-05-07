package com.dreamteam.police.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;

    private String ICAN;
    @NotNull
    private String VIN;
    private String licenceplate;

    protected Car() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != null ? !id.equals(car.id) : car.id != null) return false;
        if (ICAN != null ? !ICAN.equals(car.ICAN) : car.ICAN != null) return false;
        if (VIN != null ? !VIN.equals(car.VIN) : car.VIN != null) return false;
        return licenceplate != null ? licenceplate.equals(car.licenceplate) : car.licenceplate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ICAN != null ? ICAN.hashCode() : 0);
        result = 31 * result + (VIN != null ? VIN.hashCode() : 0);
        result = 31 * result + (licenceplate != null ? licenceplate.hashCode() : 0);
        return result;
    }
}

