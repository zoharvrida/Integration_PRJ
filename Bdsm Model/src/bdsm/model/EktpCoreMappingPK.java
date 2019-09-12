/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class EktpCoreMappingPK implements Serializable{
    private String fieldCode;
    private String ektpVal;
    private String flgMntStatus;

    /**
     * @return the fieldCode
     */
    
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * @param fieldCode the fieldCode to set
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * @return the ektpVal
     */
    public String getEktpVal() {
        return ektpVal;
    }

    /**
     * @param ektpVal the ektpVal to set
     */
    public void setEktpVal(String ektpVal) {
        this.ektpVal = ektpVal;
    }

    /**
     * @return the flgMntStatus
     */
    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param flgMntStatus the flgMntStatus to set
     */
    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("valueCode=").append(this.fieldCode)
    		.append(",KtpVal=").append(this.ektpVal)
    		.append(",flg=").append(this.flgMntStatus)
				.append("}");  	
    	return sb.toString();
    }
}
