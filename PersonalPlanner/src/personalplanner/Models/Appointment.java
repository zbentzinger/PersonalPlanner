package personalplanner.Models;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Appointment() {
    }

    public int getAppointmentID() {

        return appointmentID;

    }

    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;

    }

    public int getCustomerID() {

        return customerID;

    }

    public void setCustomerID(int customerID) {

        this.customerID = customerID;

    }

    public int getUserID() {

        return userID;

    }

    public void setUserID(int userID) {

        this.userID = userID;

    }

    public String getTitle() {

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public String getLocation() {

        return location;

    }

    public void setLocation(String location) {

        this.location = location;

    }

    public String getContact() {

        return contact;

    }

    public void setContact(String contact) {

        this.contact = contact;

    }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public String getUrl() {

        return url;

    }

    public void setUrl(String url) {

        this.url = url;

    }

    public LocalDateTime getStart() {

        return start;

    }

    public void setStart(LocalDateTime start) {

        this.start = start;

    }

    public LocalDateTime getEnd() {

        return end;

    }

    public void setEnd(LocalDateTime end) {

        this.end = end;

    }

    public String getCreatedBy() {

        return createdBy;

    }

    public void setCreatedBy(String createdBy) {

        this.createdBy = createdBy;

    }

    public LocalDateTime getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;

    }

    public String getUpdatedBy() {

        return updatedBy;

    }

    public void setUpdatedBy(String updatedBy) {

        this.updatedBy = updatedBy;

    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;

    }

    public void setUpdatedAt(LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;

    }

}
