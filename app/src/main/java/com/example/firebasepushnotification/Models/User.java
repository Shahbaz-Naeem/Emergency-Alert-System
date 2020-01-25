package com.example.firebasepushnotification.Models;

import com.example.firebasepushnotification.Utils.UserId;

public class User extends UserId {
    String name, image;

    public User() {
    }

    public User(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
