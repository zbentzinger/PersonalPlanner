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

        this.customerID = -1;
        this.customerName = "";
        this.address = new Address();
        this.active = 1;
        this.createdBy = "";
        this.createdAt = LocalDateTime.now();
        this.updatedBy = "";
        this.updatedAt = LocalDateTime.now();

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

    // Ive overriden the following three methods: hashCode(), equals() and toString()
    // for use with ChoiceBoxes in the AddCustomer and EditCustomer views.
    //
    // If the customerID is equal, I am assuming that the object is equal.
    @Override public String toString() {

        return this.customerName;

    }

    @Override public int hashCode() {

        int hash = 7;
        hash = 67 * hash + this.customerID;

        return hash;

    }

    @Override public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Customer other = (Customer) obj;

        if (this.customerID != other.customerID) {
            return false;
        }

        return true;

    }

}
