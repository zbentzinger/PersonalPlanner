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

    public User(
            int userID,
            String userName,
            String password,
            int active,
            String createdBy,
            LocalDateTime createdAt,
            String updatedBy,
            LocalDateTime updatedAt
    ) {

        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;

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
        return "User{" 
             + "userID=" + userID 
             + ", userName=" + userName 
             + ", password=" + password 
             + ", active=" + active 
             + ", createdBy=" + createdBy 
             + ", createdAt=" + createdAt 
             + ", updatedBy=" + updatedBy 
             + ", updatedAt=" + updatedAt 
             + '}';
    }

}
