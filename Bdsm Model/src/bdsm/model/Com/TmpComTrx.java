/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.math.BigDecimal;

/**
 *
 * @author SDM
 */
public class TmpComTrx extends BaseModel {
    private TmpComTrxPK pk;
    private Integer piece;
    private BigDecimal amount;

    /**
     * @return the pk
     */
    public TmpComTrxPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(TmpComTrxPK pk) {
        this.pk = pk;
    }

    /**
     * @return the piece
     */
    public Integer getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    public void setPiece(Integer piece) {
        this.piece = piece;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
