package com.example.mvm;

import java.util.Date;

public class Discussion {

    private long id;
    private int image_id;
    private String user;
    private Date posted;
    private String title;

    public Discussion(long id , int image_id, String user, Date posted, String title) {
        this.id = id;
        this.image_id = image_id;
        this.user = user;
        this.posted = posted;
        this.title = title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
