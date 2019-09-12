/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComVaultDtlsHistPK implements Serializable{
    private String txnId;
    private String txndtlId;
    private Timestamp dtmLog;
    private String denomId;
}
