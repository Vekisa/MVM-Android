package com.example.mvm;

import java.util.Date;

public class Comment {

    private String user;
    private Long id;
    private int image_id;
    private Date posted;
    private String content;

    public Comment(String user, Long id, int image_id, Date posted, String content) {
        this.user = user;
        this.id = id;
        this.image_id = image_id;
        this.posted = posted;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
