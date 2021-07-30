/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.error;

/**
 *
 * @author ADMIN
 */
public class AccountError {

    private String emailError;
    private String passwordError;
    private String phoneError;

    public AccountError() {
    }

    public AccountError(String emailError, String passwordError, String phoneError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.phoneError = phoneError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }
    
    

}
