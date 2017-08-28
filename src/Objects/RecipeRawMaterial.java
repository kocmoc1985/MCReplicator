/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class RecipeRawMaterial {

    private String parentItemCode = "";
    private String itemCode = "";
    private String pointOfUse = "";
    private double requiredQuantity = 0;
    private String dateExport = "";
    private String dateProcessed = "";
    private String status = "";

    public RecipeRawMaterial(String parentItemCode, String itemCode, String pointOfUse, double requiredQuantity, String dateExport, String status) {
        this.parentItemCode = parentItemCode;
        this.itemCode = itemCode;
        this.pointOfUse = pointOfUse;
        this.requiredQuantity = requiredQuantity;
        this.dateExport = dateExport;
        this.status = status;
    }

    public String getParentItemCode() {
        return parentItemCode == null ? "" : parentItemCode;
    }

    public String getItemCode() {
        return itemCode == null ? "" : itemCode;
    }

    public String getPointOfUse_CALC() {
        return pointOfUse == null ? "" : pointOfUse;
    }

    public double getRequiredQuantity_CALC() {
        return requiredQuantity;
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
