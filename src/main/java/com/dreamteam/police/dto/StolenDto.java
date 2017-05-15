package com.dreamteam.police.dto;

/**
 * Created by Loci on 15-5-2017.
 */
public class StolenDto {

    private CarDto carDto;
    private String status;
    private String comment;

    //region getters setters constructor
    public StolenDto() {
    }

    public CarDto getCarDto() {
        return carDto;
    }

    public void setCarDto(CarDto carDto) {
        this.carDto = carDto;
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
