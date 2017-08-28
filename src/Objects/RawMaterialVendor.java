/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class RawMaterialVendor {

    private String vendor_table_row_id;
    private String itemCode;
    private String vendorName;
    private String tradeName;

    public RawMaterialVendor(String row_id, String itemCode, String vendorId, String tradeName) {
        this.vendor_table_row_id = row_id;
        this.itemCode = itemCode;
        this.vendorName = vendorId;
        this.tradeName = tradeName;
    }

    /**
     * This vendor_table_row_id belongs not to the "raw_material_vendor" table
     * but to the "vendor" table to be able to deal with "dateProcessed" field
     * in the "vendor" table
     *
     * @return
     */
    public String getVendor_table_row_id() {
        return vendor_table_row_id;
    }

    public String getItemCode() {
        return itemCode == null ? "" : itemCode;
    }

    public String getVendorName() {
        return vendorName == null ? "" : vendorName;
    }

    public String getTradeName() {
        return tradeName == null ? "" : tradeName;
    }
}
