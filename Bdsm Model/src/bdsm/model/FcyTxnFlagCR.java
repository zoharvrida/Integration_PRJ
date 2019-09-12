/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.math.BigDecimal ;
/**
 *
 * @author user
 */
public class FcyTxnFlagCR extends BaseModel {
	private String cifNo;
	private String custName;
	private String acctCustRel;
	private String acctNo;

    /**
    * @return the cifNo
    */
    public String getCifNo() {
        return cifNo;
    }

    /**
     * @param cifNo the cifNo to set
     */
    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    /**
    * @return the custName
    */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
    * @return the acctCustRel
    */
    public String getAcctCustRel() {
        return acctCustRel;
    }

    /**
     * @param acctCustRel the acctCustRel to set
     */
    public void setAcctCustRel(String acctCustRel) {
        this.acctCustRel = acctCustRel;
    }

    /**
    * @return the acctNo
    */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * @param acctNo the acctNo to set
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }
}