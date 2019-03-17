package personalplanner.Models;

import java.time.LocalDateTime;

public class Country {

    private int countryID;
    private String countryName;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Country(
            int countryID,
            String countryName,
            String createdBy,
            LocalDateTime createdAt,
            String updatedBy,
            LocalDateTime updatedAt
    ) {

        this.countryID = countryID;
        this.countryName = countryName;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;

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

}
