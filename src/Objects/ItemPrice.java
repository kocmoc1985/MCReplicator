/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author KOCMOC
 */
public class ItemPrice {

    private String row_id;
    private String item_code;
    private String item_price;
    private String dateLastRolled;
    private String dateExport;
    private String dateProcessed;
    private String status;

    public ItemPrice(String row_id, String item_code, String item_price, String dateLastRolled, String dateExport, String dateProcessed, String status) {
        this.row_id = row_id;
        this.item_code = item_code;
        this.item_price = item_price;
        this.dateLastRolled = dateLastRolled;
        this.dateExport = dateExport;
        this.dateProcessed = dateProcessed;
        this.status = status;
    }

    public String getRow_id() {
        return row_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getItem_price() {
        return item_price;
    }

    public String getDateLastRolled() {
        return dateLastRolled;
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
