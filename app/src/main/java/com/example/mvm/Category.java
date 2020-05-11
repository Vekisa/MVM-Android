package com.example.mvm;

public class Category {
    private String name;
    private int image_id;

    public Category(String name, int image_url) {
        this.name = name;
        this.image_id = image_url;
    }

    public String getName() {
        return name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
