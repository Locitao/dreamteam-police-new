package com.dreamteam.police.dto;

import com.dreamteam.police.model.Citizen;

import java.util.Date;

/**
 * Created by loci on 30-5-17.
 */
public class OwnershipDto {
    private CarDTO carDTO;
    private Citizen citizenDTO;
    private Date startOwnership;

    public OwnershipDto() {
    }

    public CarDTO getCarDTO() {
        return carDTO;
    }

    public void setCarDTO(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    public Citizen getCitizenDTO() {
        return citizenDTO;
    }

    public void setCitizenDTO(Citizen citizenDTO) {
        this.citizenDTO = citizenDTO;
    }

    public Date getStartOwnership() {
        return startOwnership;
    }

    public void setStartOwnership(Date startOwnership) {
        this.startOwnership = startOwnership;
    }
}
