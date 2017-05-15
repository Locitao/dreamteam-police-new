package com.dreamteam.police.dto;

/**
 * Created by Loci on 15-5-2017.
 */
public class StatusDto {

    private CarDto carDto;
    private String status;
    private String comment;

    //region getters setters constructor
    public StatusDto() {
    }

    public StatusDto(CarDto carDto, String status, String comment) {
        this.carDto = carDto;
        this.status = status;
        this.comment = comment;
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
