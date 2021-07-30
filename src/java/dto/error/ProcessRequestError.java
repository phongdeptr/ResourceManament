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
public class ProcessRequestError {
    private String unavailableToProcess;

    public ProcessRequestError() {
    }

    public String getUnavailableToProcess() {
        return unavailableToProcess;
    }

    public void setUnavailableToProcess(String unavailableToProcess) {
        this.unavailableToProcess = unavailableToProcess;
    }
    
}
