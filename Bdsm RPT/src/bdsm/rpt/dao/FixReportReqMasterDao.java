/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.model.FixReportReqMaster;
import bdsm.rpt.model.FixReportReqMasterPK;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class FixReportReqMasterDao extends BaseDao{
        public FixReportReqMasterDao(Session session) {
        super(session);
    }

@Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save((FixReportReqMaster) baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update((FixReportReqMaster) baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete((FixReportReqMaster) baseModel);
        return true;
    }
    public FixReportReqMaster get(FixReportReqMasterPK pk) {
        return (FixReportReqMaster) getSession().get(FixReportReqMaster.class, pk);
    }
}
