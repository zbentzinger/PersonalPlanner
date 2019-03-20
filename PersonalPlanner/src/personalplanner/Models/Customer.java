package personalplanner.Models;

import java.time.LocalDateTime;

public class Customer {

    private int customerID;
    private String customerName;
    private int addressID;
    private int active;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Customer() {
    }

    public int getCustomerID() {

        return customerID;

    }

    public void setCustomerID(int customerID) {

        this.customerID = customerID;

    }

    public String getCustomerName() {

        return customerName;

    }

    public void setCustomerName(String customerName) {

        this.customerName = customerName;

    }

    public int getAddressID() {

        return addressID;

    }

    public void setAddressID(int addressID) {

        this.addressID = addressID;

    }

    public int isActive() {

        return active;

    }

    public void setActive(int active) {

        this.active = active;

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
