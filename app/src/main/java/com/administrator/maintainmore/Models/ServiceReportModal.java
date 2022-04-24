package com.administrator.maintainmore.Models;

public class ServiceReportModal {

    String serviceID, whoBookedService, assignedTechnician;

    public ServiceReportModal(String serviceID, String whoBookedService, String assignedTechnician) {
        this.serviceID = serviceID;
        this.whoBookedService = whoBookedService;
        this.assignedTechnician = assignedTechnician;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getWhoBookedService() {
        return whoBookedService;
    }

    public void setWhoBookedService(String whoBookedService) {
        this.whoBookedService = whoBookedService;
    }

    public String getAssignedTechnician() {
        return assignedTechnician;
    }

    public void setAssignedTechnician(String assignedTechnician) {
        this.assignedTechnician = assignedTechnician;
    }
}
