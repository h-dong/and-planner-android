package com.andigital.apps.andplanner;

/**
 * Created by hdong on 13/07/2016.
 */
public class Project {

    private Integer id;
    private String name;
    private String startDate;
    private String endDate;
    private String thumbnail;

    public void setId(Integer value) {
        id = value;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
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

    public void setThumbnail(String url) {
        thumbnail = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
