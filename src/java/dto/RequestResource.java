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
public class RequestResource {

    private String resourceID;
    private String resourceName;
    private String resourceDirectory ;
    private ResourceCategory resourceCategory;
    public RequestResource() {
    }

    public RequestResource(String resourceID, String resourceName) {
        this.resourceID = resourceID;
        this.resourceName = resourceName;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDirectory() {
        return resourceDirectory;
    }

    public void setResourceDirectory(String resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
    }

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }
}
