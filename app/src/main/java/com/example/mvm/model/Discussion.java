package com.example.mvm.model;

import java.util.Date;

public class Discussion {

    private String name;
    private int image_id;
    private Date date;
    private String user;

    public Discussion(String name, int image_id, Date date, String user) {
        this.name = name;
        this.image_id = image_id;
        this.date = date;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
