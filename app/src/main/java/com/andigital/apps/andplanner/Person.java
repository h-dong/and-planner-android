package com.andigital.apps.andplanner;

import java.util.ArrayList;

/**
 * Created by hdong on 17/07/2016.
 */
public class Person {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String thumbnail;
    private ArrayList<Assignment> assignments;

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

    public void setEmail(String value) {
        email = value;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(String value) {
        role = value;
    }

    public String getRole() {
        return role;
    }

    public void setThumbnail(String url) {
        thumbnail = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setAssignments(ArrayList value) { assignments = value; }

    public ArrayList getAssignments() { return assignments; }
}
