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
public class XrefReportTemplate extends BaseModel {
    private XrefReportTemplatePK compositeId;
    private Integer idXref;

    /**
     * @return the idXref
     */
    public Integer getIdXref() {
        return idXref;
    }

    /**
     * @param idXref the idXref to set
     */
    public void setIdXref(Integer idXref) {
        this.idXref = idXref;
    }

    /**
     * @return the compositeId
     */
    public XrefReportTemplatePK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(XrefReportTemplatePK compositeId) {
        this.compositeId = compositeId;
    }
}
