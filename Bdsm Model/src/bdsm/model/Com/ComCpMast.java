/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComCpMast extends BaseModel {
    private String txnId;
    private Integer idBranch;
    private Timestamp dtmRequest;
    private String codCcy;
    private String idVendor;
    private String idCust;
    private String status;
    private BigDecimal totalAmtReq;
    private BigDecimal totalAmtConf;
}
