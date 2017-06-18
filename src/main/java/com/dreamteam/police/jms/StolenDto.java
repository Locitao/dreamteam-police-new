package com.dreamteam.police.jms;

/**
 * Created by Loci on 2-6-2017.
 */
class StolenDto {
    private String ican;
    private String licensePlate;
    private Long timestamp;
    private boolean stolenValue;

    public StolenDto() {
    }

    public StolenDto(String ican, String licensePlate, Long timestamp, boolean stolenValue) {
        this.ican = ican;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
        this.stolenValue = stolenValue;
    }

    public String getIcan() {
        return ican;
    }

    public void setIcan(String ican) {
        this.ican = ican;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getStolenValue() {
        return stolenValue;
    }

    public void setStolenValue(boolean stolenValue) {
        this.stolenValue = stolenValue;
    }
}
