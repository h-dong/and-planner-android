package com.andigital.apps.andplanner;

/**
 * Created by hdong on 17/07/2016.
 */
public class Assignment {
    private Integer id;
    private String startDate;
    private String endDate;
    private Integer percentage;
    private String type;

    public void setId(Integer value) {
        id = value;
    }

    public Integer getId() {
        return id;
    }

    public void setStartDate(String value) {
        startDate = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String value) {
        endDate = value;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setPercentage(Integer value) { percentage = value; }

    public Integer getPercentage() { return percentage; }

    public void setType(String value) { type = value; }

    public String getType() { return type; }

}
