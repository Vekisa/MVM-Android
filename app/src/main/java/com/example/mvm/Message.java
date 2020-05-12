package com.example.mvm;

import java.util.Date;

public class Message {

    private String user;
    private int image_id;
    private Date date;
    private String content;


    public Message(String user, int image_id, Date date, String content) {
        this.user = user;
        this.image_id = image_id;
        this.date = date;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
