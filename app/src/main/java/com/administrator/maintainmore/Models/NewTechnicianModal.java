package com.administrator.maintainmore.Models;

public class NewTechnicianModal {

    String technicianID, technicianName,technicianEmail, technicianPhoneNumber;
    String technicianImageUrl;

    public NewTechnicianModal(String technicianId, String technicianName, String technicianEmail, String technicianPhoneNumber,
    String technicianImageUrl) {
        this.technicianID = technicianId;
        this.technicianName = technicianName;
        this.technicianEmail = technicianEmail;
        this.technicianPhoneNumber = technicianPhoneNumber;
        this.technicianImageUrl = technicianImageUrl;
    }

    public String getTechnicianID() {
        return technicianID;
    }

    public void setTechnicianID(String technicianID) {
        this.technicianID = technicianID;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianEmail() {
        return technicianEmail;
    }

    public void setTechnicianEmail(String technicianEmail) {
        this.technicianEmail = technicianEmail;
    }

    public String getTechnicianPhoneNumber() {
        return technicianPhoneNumber;
    }

    public void setTechnicianPhoneNumber(String technicianPhoneNumber) {
        this.technicianPhoneNumber = technicianPhoneNumber;
    }

    public String getTechnicianImageUrl() {
        return technicianImageUrl;
    }

    public void setTechnicianImageUrl(String technicianImageUrl) {
        this.technicianImageUrl = technicianImageUrl;
    }
}
