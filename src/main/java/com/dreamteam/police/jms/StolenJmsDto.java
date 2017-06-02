package com.dreamteam.police.jms;

/**
 * Created by loci on 30-5-17.
 */
public class StolenJmsDto {

    private String ican;
    private String licenseplate;
    private Long timestamp;
    private boolean stolenValue;

    public StolenJmsDto() {
    }

    public StolenJmsDto(String ican, String licenseplate, Long timestamp, boolean stolenValue) {
        this.ican = ican;
        this.licenseplate = licenseplate;
        this.timestamp = timestamp;
        this.stolenValue = stolenValue;
    }

    public String getIcan() {
        return ican;
    }

    public void setIcan(String ican) {
        this.ican = ican;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
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
