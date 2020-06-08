package com.example.mvm.model;

public class Image {

    protected Long id;

    private String path;

    public Image(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
