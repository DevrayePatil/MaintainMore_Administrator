package com.administrator.maintainmore.Models;

public class TechniciansModal {
    String userID, name, email, imageUrl;

    public TechniciansModal(String userID, String name, String email, String imageUrl) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
