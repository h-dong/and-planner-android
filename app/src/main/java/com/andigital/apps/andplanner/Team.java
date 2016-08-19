package com.andigital.apps.andplanner;

/**
 * Created by hdong on 14/07/2016.
 */
public class Team {
    private Integer id;
    private String name;
    private String startDate;
    private String endDate;

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
}
