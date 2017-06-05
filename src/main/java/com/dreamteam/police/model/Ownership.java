package com.dreamteam.police.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Loci on 1-5-2017.
 */
//@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ownership {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("owner")
    private Citizen owner;

    @JsonProperty("owned")
    private Car owned;

    @JsonProperty("startOwnership")
    //@JsonIgnore
    private Date startOwnership;

    @JsonProperty("endOwnership")
    //@JsonIgnore
    private Date endOwnership;

    public Ownership() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Citizen getOwner() {
        return owner;
    }

    public void setOwner(Citizen owner) {
        this.owner = owner;
    }

    public Car getOwned() {
        return owned;
    }

    public void setOwned(Car owned) {
        this.owned = owned;
    }

    public Date getStartOwnership() {
        return startOwnership;
    }

    public void setStartOwnership(Date startOwnership) {
        this.startOwnership = startOwnership;
    }

    public Date getEndOwnership() {
        return endOwnership;
    }

    public void setEndOwnership(Date endOwnership) {
        this.endOwnership = endOwnership;
    }
}
