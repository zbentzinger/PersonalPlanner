package personalplanner.Models;

import java.time.LocalDateTime;

public class User {

    private int userID;
    private String userName;
    private String password;
    private int active;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public User() {

        this.userID = -1;
        this.userName = "";
        this.password = "";
        this.active = 1;
        this.createdBy = "";
        this.createdAt = LocalDateTime.now();
        this.updatedBy = "";
        this.updatedAt = LocalDateTime.now();

    }

    public int getUserID() {

        return userID;

    }

    public void setUserID(int userID) {

        this.userID = userID;

    }

    public String getUserName() {

        return userName;

    }

    public void setUserName(String userName) {

        this.userName = userName;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

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

        return this.userName;

    }

}
