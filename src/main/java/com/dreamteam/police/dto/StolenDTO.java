package com.dreamteam.police.dto;

/**
 * Created by Loci on 15-5-2017.
 */
public class StolenDTO {

    private CarDTO carDTO;
    private String carStatus;
    private String comments;

    //region getters setters constructor
    public StolenDTO() {
    }

    public StolenDTO(CarDTO carDTO, String carStatus, String comments) {
        this.carDTO = carDTO;
        this.carStatus = carStatus;
        this.comments = comments;
    }

    public CarDTO getCarDTO() {
        return carDTO;
    }

    public void setCarDTO(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    //endregion
}
