package com.dreamteam.police.dto;

/**
 * Created by Loci on 15-5-2017.
 */
public class StolenDTO {

    private CarDTO carDTO;
    private String status;
    private String comment;

    //region getters setters constructor
    public StolenDTO() {
    }

    public StolenDTO(CarDTO carDTO, String status, String comment) {
        this.carDTO = carDTO;
        this.status = status;
        this.comment = comment;
    }

    public CarDTO getCarDTO() {
        return carDTO;
    }

    public void setCarDTO(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    //endregion
}
