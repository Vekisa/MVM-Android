package com.example.mvm.model;

public class Comment {

    private String id;
    private String userImage;
    private String userName;
    private String content;
    private String dateTime;
    private String userUsername;
    private String discussionId;
    private String userId;

    public String getUserImage() {
        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
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
