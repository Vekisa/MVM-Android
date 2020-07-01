package com.example.mvm.model;

public class Image {

    private String path;

    private String content;

    private String categoryId;

    private String userId;

    private String discussionId;

    private String commentId;

    public Image(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
