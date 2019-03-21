package personalplanner.Models;

import java.time.LocalDateTime;

public class Address {

    private int addressID;
    private String address;
    private String address2;
    private City city;
    private String zip;
    private String phone;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Address() {
    }

    public int getAddressID() {

        return addressID;

    }

    public void setAddressID(int addressID) {

        this.addressID = addressID;

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }

    public String getAddress2() {

        return address2;

    }

    public void setAddress2(String address2) {

        this.address2 = address2;

    }

    public City getCity() {

        return city;

    }

    public void setCity(City city) {

        this.city = city;

    }

    public String getZip() {

        return zip;

    }

    public void setZip(String zip) {

        this.zip = zip;

    }

    public String getPhone() {

        return phone;

    }

    public void setPhone(String phone) {

        this.phone = phone;

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
