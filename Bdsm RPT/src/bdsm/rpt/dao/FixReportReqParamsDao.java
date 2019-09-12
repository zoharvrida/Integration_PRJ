/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.model.FixReportReqParams;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class FixReportReqParamsDao extends BaseDao {

    public FixReportReqParamsDao(Session session) {
        super(session);
    }
    
    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save((FixReportReqParams) baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update((FixReportReqParams) baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete((FixReportReqParams) baseModel);
        return true;
    }
    public FixReportReqParams get(Integer idBatch) {
        return (FixReportReqParams) getSession().get(FixReportReqParams.class, idBatch);
    }
    
}
