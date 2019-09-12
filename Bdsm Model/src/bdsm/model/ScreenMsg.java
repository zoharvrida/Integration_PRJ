/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author 00110310
 */
public class ScreenMsg extends BaseModel implements Serializable {
    private String msgTag;
    private String message;
    private String typParam;
    private String cdParam;
    private Integer numParam;
    private String delimiter;
    
    
    public ScreenMsg() {}
    
    public ScreenMsg(String message) {
        this.message = message;
    }
    
    
    @Override
    public String toString() {
        return "ScreenMsg{" + "msgTag=" + msgTag + ", message=" + message + ", typParam=" + typParam + ", cdParam=" + cdParam + ", numParam=" + numParam + ", delimiter=" + delimiter + '}';
    }

    /**
     * @return the msgTag
     */
    
    public String getMsgTag() {
        return msgTag;
    }

    /**
     * @param msgTag the msgTag to set
     */
    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the typParam
     */
    public String getTypParam() {
        return typParam;
    }

    /**
     * @param typParam the typParam to set
     */
    public void setTypParam(String typParam) {
        this.typParam = typParam;
    }

    /**
     * @return the cdParam
     */
    public String getCdParam() {
        return cdParam;
    }

    /**
     * @param cdParam the cdParam to set
     */
    public void setCdParam(String cdParam) {
        this.cdParam = cdParam;
    }

    /**
     * @return the numParam
     */
    public Integer getNumParam() {
        return numParam;
    }

    /**
     * @param numParam the numParam to set
     */
    public void setNumParam(Integer numParam) {
        this.numParam = numParam;
    }

    /**
     * @return the delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
