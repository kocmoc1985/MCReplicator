/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class RawMaterial {

    private String itemCode = "";
    private String itemName = "";
    private String prefferedLocation = "";
    private int shelfLifeDays = 0;
    private String groupTechnologyCode = "";
    private String familyItemNumber = "";
    private String rawMaterialStatus = "";
    //==========================================================================
    private String dateExport = "";
    private String dateProcessed = "";
    private String status = "";

    //===============================================
    public RawMaterial(
            String itemCode,
            String itemName,
            String prefferedLocation,
            int shelfLifeDays,
            String groupTechnologyCode,
            String familyItemNumber,
            String rawMaterialStatus,
            String dateExport,
            String dateProcessed,
            String status) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.prefferedLocation = prefferedLocation;
        this.shelfLifeDays = shelfLifeDays;
        this.groupTechnologyCode = groupTechnologyCode;
        this.familyItemNumber = familyItemNumber;
        this.rawMaterialStatus = rawMaterialStatus;
        this.dateExport = dateExport;
        this.dateProcessed = dateProcessed;
        this.status = status;
    }
    
   
    public String getItemCode() {
        return itemCode == null ? "" : itemCode;
    }

    public String getItemName() {
        return itemName == null ? "" : itemName;
    }

    public String getPrefferedLocation() {
        return prefferedLocation == null ? "" : prefferedLocation;
    }

    public int getShelfLifeDays_CALC() {
        return shelfLifeDays * 30;
    }

    public String getGroupTechnologyCode() {
        return groupTechnologyCode == null ? "" : groupTechnologyCode;
    }

    public String getFamilyItemNumber() {
        return familyItemNumber == null ? "" : familyItemNumber;
    }

    public String getRawMaterialStatus() {
        return rawMaterialStatus == null ? "" : rawMaterialStatus;
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
