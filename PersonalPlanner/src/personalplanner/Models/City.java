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

}
