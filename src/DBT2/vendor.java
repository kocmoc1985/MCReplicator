/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBT2;

/**
 *
 * @author KOCMOC
 */
public class vendor {

    public static final String tableName = "vendor";
    public static final String tableName_BACK = "vendor_b";
    //==
    public static final String row_id = "ID";
    /**
     * @deprecated because eclipse db doesn't contain "vendorId/vendorNo"
     */
    public static final String vendorId = "vendorNo";
    public static final String vendor_name = "vendorName";
    public static final String address = "address";
    public static final String zipCode = "zipCode";
    public static final String city = "city";
    public static final String country = "country";
    public static final String phone = "phone";
    public static final String fax = "fax";
    public static final String email = "email";
    public static final String website = "website";
    //=====
    public static final String dateExport = "dateExport";
    public static final String dateProcessed = "dateProcessed";
    public static final String status = "status";
}
