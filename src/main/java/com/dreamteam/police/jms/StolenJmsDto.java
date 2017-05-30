package com.dreamteam.police.jms;

/**
 * Created by loci on 30-5-17.
 */
public class StolenJmsDto {

    private String ican;
    private String licenseplate;
    private Long timestamp;
    private boolean stolenvalue;

    public StolenJmsDto() {
    }

    public StolenJmsDto(String ican, String licenseplate, Long timestamp, boolean stolenvalue) {
        this.ican = ican;
        this.licenseplate = licenseplate;
        this.timestamp = timestamp;
        this.stolenvalue = stolenvalue;
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

    public boolean isStolenvalue() {
        return stolenvalue;
    }

    public void setStolenvalue(boolean stolenvalue) {
        this.stolenvalue = stolenvalue;
    }

    @Override
    public String toString() {
        return "StolenJmsDto{" +
                "ican='" + ican + '\'' +
                ", licenseplate='" + licenseplate + '\'' +
                ", timestamp=" + timestamp +
                ", stolenvalue=" + stolenvalue +
                '}';
    }
}
