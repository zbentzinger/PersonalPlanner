package personalplanner.Models;

import java.time.LocalDateTime;

public class Country {

    private int countryID;
    private String countryName;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Country() {

        this.countryID = -1;
        this.countryName = "";
        this.createdBy = "";
        this.createdAt = LocalDateTime.now();
        this.updatedBy = "";
        this.updatedAt = LocalDateTime.now();

    }

    public int getCountryID() {

        return countryID;

    }

    public void setCountryID(int countryID) {

        this.countryID = countryID;

    }

    public String getCountryName() {

        return countryName;

    }

    public void setCountryName(String countryName) {

        this.countryName = countryName;

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
    // If the countryID is equal, I am assuming that the object is equal.
    @Override public String toString() {

        return this.countryName;

    }

    @Override public int hashCode() {

        int hash = 7;
        hash = 47 * hash + this.countryID;

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

        final Country other = (Country) obj;

        if (this.countryID != other.countryID) {
            return false;
        }

        return true;

    }

}
