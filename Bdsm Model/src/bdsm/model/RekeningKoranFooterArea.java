package bdsm.model;

import java.math.BigDecimal;

/**
 *
 * @author Jaka
 */
public class RekeningKoranFooterArea {
    
    private int totalCount;
    private BigDecimal totalAmount;

    public RekeningKoranFooterArea() {
    }

    public RekeningKoranFooterArea(int totalCount, BigDecimal totalAmount) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
