/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

/**
 *
 * @author SDM
 */
public class ComDenomReq {
    private String denomId;
    private String namDenom;
    private String amount;
    private String piece;

    /**
     * @return the denomId
     */
    public String getDenomId() {
        return denomId;
    }

    /**
     * @param denomId the denomId to set
     */
    public void setDenomId(String denomId) {
        this.denomId = denomId;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the piece
     */
    public String getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    public void setPiece(String piece) {
        this.piece = piece;
    }

    /**
     * @return the namDenom
     */
    public String getNamDenom() {
        return namDenom;
    }

    /**
     * @param namDenom the namDenom to set
     */
    public void setNamDenom(String namDenom) {
        this.namDenom = namDenom;
    }
}
