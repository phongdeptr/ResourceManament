/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author ADMIN
 */
public class Request {

    private UserDTO requester;
    private String requestID;
    private RequestResource requestResource;
    private String requestDate;
    private String requestStatus;
    private String requestContent;

    public Request() {
    }

    public Request(UserDTO requester, String requestID, RequestResource requestResource, String requestDate, String requestStatus, String requestContent) {
        this.requester = requester;
        this.requestID = requestID;
        this.requestResource = requestResource;
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
        this.requestContent = requestContent;
    }

    public UserDTO getRequester() {
        return requester;
    }

    public void setRequester(UserDTO requester) {
        this.requester = requester;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public RequestResource getRequestResource() {
        return requestResource;
    }

    public void setRequestResource(RequestResource requestResource) {
        this.requestResource = requestResource;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

}
