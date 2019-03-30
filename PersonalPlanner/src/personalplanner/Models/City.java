package personalplanner.Models;

import java.time.LocalDateTime;

public class City {

    private int cityID;
    private Country country;
    private String cityName;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public City() {

        this.cityID = -1;
        this.country = new Country();
        this.cityName = "";
        this.createdBy = "";
        this.createdAt = LocalDateTime.now();
        this.updatedBy = "";
        this.updatedAt = LocalDateTime.now();

    }

    public int getCityID() {

        return cityID;

    }

    public void setCityID(int cityID) {

        this.cityID = cityID;

    }

    public Country getCountry() {

        return country;

    }

    public void setCountry(Country country) {

        this.country = country;

    }

    public String getCityName() {

        return cityName;

    }

    public void setCityName(String cityName) {

        this.cityName = cityName;

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
    // If the cityID is equal, I am assuming that the object is equal.
    @Override public String toString() {

        return this.cityName;

    }

    @Override public int hashCode() {

        int hash = 7;
        hash = 67 * hash + this.cityID;

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

        final City other = (City) obj;

        if (this.cityID != other.cityID) {
            return false;
        }

        return true;

    }

}
