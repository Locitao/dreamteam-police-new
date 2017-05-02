package com.dreamteam.police.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @OneToMany
    private List<Ownership> ownerships;

    //region Constructor, getters & setters
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

    public List<Ownership> getOwnerships() {
        return Collections.unmodifiableList(ownerships);
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }
    //endregion
}
