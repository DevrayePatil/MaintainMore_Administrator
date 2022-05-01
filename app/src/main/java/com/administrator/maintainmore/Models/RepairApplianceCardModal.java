package com.administrator.maintainmore.Models;

public class RepairApplianceCardModal {

    String serviceID, serviceName, serviceDescription, requiredTime, servicePrice;
    String iconUrl, backgroundImageUrl;

    public RepairApplianceCardModal(String serviceID, String serviceName, String serviceDescription,
                                    String requiredTime, String servicePrice,
                                    String iconUrl, String backgroundImageUrl) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.requiredTime = requiredTime;
        this.servicePrice = servicePrice;
        this.iconUrl = iconUrl;
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }
}
