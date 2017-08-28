/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sap_to_cpmd_rep;

import DBT2.item_price;
import DBT2.update_back;
import DBT2.vendor;
import DBT2.vendor_contact;
import Objects.ItemPrice;
import Objects.Vendor;
import Objects.VendorContact;
import Supplementary.HelpM;

/**
 *
 * @author KOCMOC
 */
public class QueryAll {

    /**
     *
     * @return
     */
    public static String get_vendors_from_mcsap() {
        String rst = "select "
                + vendor.row_id
                + "," + vendor.vendorId
                + "," + vendor.vendor_name
                + "," + vendor.address
                + "," + vendor.zipCode
                + "," + vendor.city
                + "," + vendor.country
                + "," + vendor.phone
                + "," + vendor.fax
                + "," + vendor.email
                + "," + vendor.website
                + " from " + vendor.tableName
                + " where " + vendor.dateProcessed + " is NULL";//-------> OBS!
        return rst;
    }

    public static String get_vendors_contacts_from_mcsap() {
        String rst = "select "
                + vendor_contact.row_id
                + "," + vendor_contact.vendor_id
                + "," + vendor_contact.contactName
                + "," + vendor_contact.position
                + "," + vendor_contact.phone
                + "," + vendor_contact.email
                + " from " + vendor_contact.tableName
                + " where " + vendor_contact.dateProcessed + " is NULL";//-------> OBS!
        return rst;
    }

    public static String get_item_price_from_mcsap() {
        String rst = "select "
                + item_price.row_id
                + "," + item_price.item_code
                + "," + item_price.item_price
                + "," + item_price.date_last_rolled
                + " from " + item_price.tableName
                + " where " + item_price.dateProcessed + " is NULL";//-------> OBS!
        return rst;
    }

    //=================================================
    public static String insert_into_vendor(Vendor v) {
        String rst = "insert into " + vendor.tableName_BACK + " values ("
                + "'" + v.getVendor_id() + "'"
                + ",'" + v.getVendor_name() + "'"
                + ",'" + v.getAddress() + "'"
                + ",'" + v.getZipCode() + "'"
                + ",'" + v.getCity() + "'"
                + ",'" + v.getCountry() + "'"
                + ",'" + v.getPhone() + "'"
                + ",'" + v.getFax() + "'"
                + ",'" + v.getEmail() + "'"
                + ",'" + v.getWebsite() + "'"
                + ",'" + v.getDateExport() + "'"
                + "," + v.getDateProcessed() + ""
                + ",'" + v.getStatus() + "')";
        return rst;
    }

    public static String insert_into_vendor_contact(VendorContact vc) {
        String rst = "insert into " + vendor_contact.tableName_BACK + " values ("
                + "'" + vc.getVendor_id() + "'"
                + ",'" + vc.getContactName() + "'"
                + ",'" + vc.getPosition() + "'"
                + ",'" + vc.getPhone() + "'"
                + ",'" + vc.getEmail() + "'"
                + ",'" + vc.getDateExport() + "'"
                + "," + vc.getDateProcessed() + ""
                + ",'" + vc.getStatus() + "')";
        return rst;
    }

    public static String insert_into_item_price(ItemPrice ip) {
        String rst = "insert into " + item_price.tableName_BACK + " values ("
                + "'" + ip.getItem_code() + "'"
                + ",'" + ip.getItem_price() + "'"
                + "," + ip.getDateLastRolled() + ""
                + ",'" + ip.getDateExport() + "'"
                + "," + ip.getDateProcessed() + ""
                + ",'" + ip.getStatus() + "')";
        return rst;
    }

    public static String insert_last_check_into_interface_trigger(String table_name) {
        String rst = "insert into " + table_name + " values ("
                + "'-'"
                + ",'mc'"
                + ",'-'"
                + ",'" + HelpM.get_proper_date_time_same_format_on_all_computers() + "'"
                + ",null"
                + ",'last_check')";
        return rst;
    }

    //=======================================================================
    public static String update_vendor(Vendor v) {
        String rst = "update " + vendor.tableName_BACK + " set "
                + vendor.vendorId + " ='" + v.getVendor_id() + "',"
                + vendor.vendor_name + "='" + v.getVendor_name() + "',"
                + vendor.address + "='" + v.getAddress() + "',"
                + vendor.zipCode + "='" + v.getZipCode() + "',"
                + vendor.city + "='" + v.getCity() + "',"
                + vendor.country + "='" + v.getCountry() + "',"
                + vendor.phone + "='" + v.getPhone() + "',"
                + vendor.fax + "='" + v.getFax() + "',"
                + vendor.email + "='" + v.getEmail() + "',"
                + vendor.website + "='" + v.getWebsite() + "',"
                + vendor.dateExport + "='" + v.getDateExport() + "',"
                + vendor.dateProcessed + "=" + v.getDateProcessed() + ","
                + vendor.status + "='" + v.getStatus() + "'"
                + " where " + vendor.vendorId + "='" + v.getVendor_id() + "'";

        return rst;
    }

    public static String update_vendor_contact(VendorContact vc) {
        String rst = "update " + vendor_contact.tableName_BACK + " set "
                + vendor_contact.vendor_id + " ='" + vc.getVendor_id() + "',"
                + vendor_contact.contactName + "='" + vc.getContactName() + "',"
                + vendor_contact.position + "='" + vc.getPosition() + "',"
                + vendor_contact.phone + "='" + vc.getPhone() + "',"
                + vendor_contact.email + "='" + vc.getEmail() + "',"
                + vendor_contact.dateExport + "='" + vc.getDateExport() + "',"
                + vendor_contact.dateProcessed + "=" + vc.getDateProcessed() + ","
                + vendor_contact.status + "='" + vc.getStatus() + "'"
                + " where " + vendor_contact.vendor_id + "='" + vc.getVendor_id() + "'"
                + " and " + vendor_contact.contactName + " like '%" + vc.getContactName() + "%'";
        return rst;
    }

    public static String update_item_price(ItemPrice ip) {
        String rst = "update " + item_price.tableName_BACK + " set "
                + item_price.item_code + " ='" + ip.getItem_code() + "',"
                + item_price.item_price + "='" + ip.getItem_price() + "',"
                + item_price.date_last_rolled + "=" + ip.getDateLastRolled() + ","
                + item_price.dateExport + "='" + ip.getDateExport() + "',"
                + item_price.dateProcessed + "=" + ip.getDateProcessed() + ","
                + item_price.status + "='" + ip.getStatus() + "'"
                + " where " + item_price.item_code + "='" + ip.getItem_code() + "'";

        return rst;
    }

    public static String update_last_check_interface_trigger(String table_name) {
        String rst = "update " + table_name + " set "
                + update_back.dataStream + "= '-',"
                + update_back.sender + "= 'mc',"
                + update_back.reciever + "= '-',"
                + update_back.dateSend + "='" + HelpM.get_proper_date_time_same_format_on_all_computers() + "'"
                + " where " + update_back.status + " like '%" + "last_check" + "%'";
        return rst;
    }

    //=================================================
    public static String table_contains_vendor(String vendor_id) {
        String rst = "select * from " + vendor.tableName_BACK
                + " where " + vendor.vendorId + " = '" + vendor_id + "'";
        return rst;
    }

    public static String table_contains_vendor_contact(String vendor_id, String contact_name) {
        String rst = "select * from " + vendor_contact.tableName_BACK
                + " where " + vendor_contact.vendor_id + " = '" + vendor_id + "'"
                + " and " + vendor_contact.contactName + " like '%" + contact_name + "%'";
        return rst;
    }

    public static String table_contains_item_price(String item_code) {
        String rst = "select * from " + item_price.tableName_BACK
                + " where " + item_price.item_code + " = '" + item_code + "'";
        return rst;
    }

    public static String table_contains_last_check(String table_name) {
        String rst = "select * from " + table_name
                + " where " + update_back.status + " = '" + "last_check" + "'";
        return rst;
    }
    //=================================================
//    public static void main(String[] args) {
//        System.out.println("" + get_vendors_from_mcsap());
//        String city = "'HAA";
//        city = city.replaceAll("'", "");
//        System.out.println("" + city);
//    }
}
