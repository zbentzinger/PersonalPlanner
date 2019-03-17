package personalplanner.Models;

import java.time.LocalDateTime;

public class Customer {

    private int customerID;
    private String customerName;
    private Address address;
    private boolean active;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Customer(
            int customerID,
            String customerName,
            Address address,
            boolean active,
            String createdBy,
            LocalDateTime createdAt,
            String updatedBy,
            LocalDateTime updatedAt
    ) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.active = active;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;

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

    public Address getAddress() {

        return address;

    }

    public void setAddress(Address address) {

        this.address = address;

    }

    public boolean isActive() {

        return active;

    }

    public void setActive(boolean active) {

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
