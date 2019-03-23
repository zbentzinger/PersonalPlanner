package personalplanner.Models;

import java.time.LocalDateTime;

public class Customer {

    private int customerID;
    private String customerName;
    private Address address;
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

    public Address getAddress() {

        return address;

    }

    public void setAddress(Address address) {

        this.address = address;

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

    @Override public String toString() {

        return "Customer{" 
                        + "customerID=" + customerID 
                        + ", customerName=" + customerName 
                        + ", address=" + address 
                        + ", active=" + active 
                        + ", createdBy=" + createdBy 
                        + ", createdAt=" + createdAt 
                        + ", updatedBy=" + updatedBy 
                        + ", updatedAt=" + updatedAt 
                + '}';

    }

}
