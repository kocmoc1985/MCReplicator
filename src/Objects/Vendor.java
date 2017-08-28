/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class Vendor {
    private String row_id;
    private String vendor_id;
    private String vendor_name;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String phone;
    private String fax;
    private String email;
    private String website;
    private String dateExport;
    private String dateProcessed = "";
    private String status;

    public Vendor(String row_id,String vendor_id, String vendor_name, String address, String zipCode, String city, String country, String phone, String fax, String email, String website, String dateExport, String dateProcessed, String status) {
        this.row_id = row_id;
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.website = website;
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

    public String getVendor_name() {
        return vendor_name;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
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
