/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public class UserDTO {

    private String userID;
    private String password;
    private String fullName;
    private boolean isActive;
    private boolean isAdmin;

    public UserDTO() {
    }

    public UserDTO(String userID, String password, String fullName, boolean isActive, boolean isAdmin) {
        this.userID = userID;
        this.password = password;
        this.fullName = fullName;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.userID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.userID, other.userID);
    }

}
