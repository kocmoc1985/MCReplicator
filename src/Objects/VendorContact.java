/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class VendorContact {
    
    private String row_id;
    private String vendor_id;
    private String contactName;
    private String position;
    private String phone;
    private String email;
    private String dateExport;
    private String dateProcessed;
    private String status;

    public VendorContact(String row_id, String vendor_id, String contactName, String position, String phone, String email, String dateExport, String dateProcessed, String status) {
        this.row_id = row_id;
        this.vendor_id = vendor_id;
        this.contactName = contactName;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.dateExport = dateExport;
        this.dateProcessed = dateProcessed;
        this.status = status;
    }

    public String getRow_id() {
        return row_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
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
