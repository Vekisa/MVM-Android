package com.example.mvm.model;

import java.io.Serializable;
import java.util.Date;

public class Discussion implements Serializable {
    private String id;
    private String userImage;
    private String userName;
    private String title;
    private String content;
    private String dateTime;
    private String forumId;
    private String userUsername;
    private String userId;

    public String getUserImage() {
        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getForumId() {
        return forumId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
