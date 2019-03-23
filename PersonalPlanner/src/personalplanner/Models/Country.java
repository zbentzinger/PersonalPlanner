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

    @Override public String toString() {

        return "Country{" 
                        + "countryID=" + countryID 
                        + ", countryName=" + countryName 
                        + ", createdBy=" + createdBy 
                        + ", createdAt=" + createdAt 
                        + ", updatedBy=" + updatedBy 
                        + ", updatedAt=" + updatedAt 
                + '}';

    }

}
