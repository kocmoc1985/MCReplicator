/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects2;

/**
 *
 * @author KOCMOC
 */
public class PointOfUseEntry {

    private String parentItemCode;
    private String itemCode;
    private String siloId;

    public PointOfUseEntry(String parentItemCode, String itemCode, String siloId) {
        this.parentItemCode = parentItemCode;
        this.itemCode = itemCode;
        this.siloId = siloId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getParentItemCode() {
        return parentItemCode;
    }

    public String getSiloId() {
        return siloId;
    }
}
