/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.model;

import bdsm.model.BaseModel;
/**
 *
 * @author v00019722
 */
public class FixReportReqParams extends BaseModel {
    private FixReportReqParamsPK compositeId;

    /**
     * @return the compositeId
     */
    public FixReportReqParamsPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FixReportReqParamsPK compositeId) {
        this.compositeId = compositeId;
    }
}
