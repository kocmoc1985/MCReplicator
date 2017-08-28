/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class Recipe {

    private String parentItemCode = "";
    private String familySubGroup = "";
    private String groupName = "";
    private String workInstructions = "";
    private String description = "";
    private String recipeStatus = "";
    private String developedOn = "";
    private String lastUpdate = "";
    private String dateExport = "";
    private String dateProcessed = "";
    private String status = "";

    public Recipe(String parentItemCode, String familySubGroup, String groupName, String workInstructions, String description, String recipeStatus, String developedOn, String lastUpdate, String dateExport, String status) {
        this.parentItemCode = parentItemCode;
        this.familySubGroup = familySubGroup;
        this.groupName = groupName;
        this.workInstructions = workInstructions;
        this.description = description;
        this.recipeStatus = recipeStatus;
        this.developedOn = developedOn;
        this.lastUpdate = lastUpdate;
        this.dateExport = dateExport;
        this.status = status;
    }

    public String getParentItemCode() {
        return parentItemCode == null ? "" : parentItemCode;
    }

    public String getFamilySubGroup() {
        return familySubGroup == null ? "" : familySubGroup;
    }

    public String getGroupName() {
        return groupName == null ? "" : groupName;
    }

    public String getWorkInstructions() {
        return workInstructions == null ? "" : workInstructions;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public String getRecipeStatus() {
        return recipeStatus == null ? "" : recipeStatus;
    }

    public String getDevelopedOn() {
        return developedOn == null ? "" : developedOn;
    }

    public String getLastUpdate() {
        return lastUpdate == null ? "" : lastUpdate;
    }

    public String getDateExport() {
        return dateExport;
    }

    public String getDateProcessed() {
        return dateProcessed;
    }

    public String getStatus() {
        return status;
    }
}
