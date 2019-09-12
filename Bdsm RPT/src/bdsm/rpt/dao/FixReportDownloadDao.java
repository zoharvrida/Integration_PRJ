/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.model.FixReportReqMaster;
import bdsm.rpt.model.FixReportReqMasterPK;
import bdsm.rpt.model.FixStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class FixReportDownloadDao extends BaseDao {

	public FixReportDownloadDao(Session session) {
		super(session);
	}

	protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<FixReportReqMaster> list(String user, Date now, Date Yesterday) {
		List<FixReportReqMaster> objFixEmail = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("from FixReportReqMaster where idRequest = :pUser");
		sb.append(" AND Status <> 'Download'");
		sb.append(" AND DtmRequest <= :pdate ");
		sb.append(" AND DtmRequest > :pdateyes ");
		sb.append(" order by QID");
		Query s = getSession().createQuery(sb.toString());
		getLogger().info("User : " + user);
		s.setString("pUser", user);
		s.setTimestamp("pdate", now);
		s.setTimestamp("pdateyes", Yesterday);

		List<FixReportReqMaster> partial = s.list();

		String[] namesFile;
		int index = 0;

		for (FixReportReqMaster additional : partial) {
			FixStatus statAdd = new FixStatus();
			//if (additional.getStatus().equalsIgnoreCase("Ready To Download")) {
				if (additional.getFilePath().contains("/")) {
					namesFile = additional.getFilePath().split("\\/");
					index = namesFile.length - 1;
					statAdd.setNameFile(namesFile[index]);
					//getLogger().info("NAMEFILE:" + statAdd.getNameFile());
				} else {
					statAdd.setNameFile(additional.getFilePath());
				}
				additional.setTempStat(statAdd);
		}
		getLogger().info("TOTAL LIST AFTER ADD :" + partial.size());

		StringBuilder q = new StringBuilder();
		q.append("SELECT a.QID,a.FLGPROCESS,a.REASON ");
		q.append("FROM FIXQXTRACT a,fixreportreqmaster b ");
		q.append("WHERE a.PARAM6=b.idbatch ");
		q.append("AND a.QID = b.QID ");
		q.append("AND b.status <> 'Download' ");
		q.append("AND b.idRequest = :pUser ");
		q.append("AND b.DtmRequest <= :pdate ");
		q.append("AND b.DtmRequest > :pdateyes ");
		q.append("ORDER BY a.DTMREQUEST ");

		Query last = getSession().createSQLQuery(q.toString());
		last.setString("pUser", user);
		last.setTimestamp("pdate", now);
		last.setTimestamp("pdateyes", Yesterday);
		List JoinQuery = last.list();
		objFixEmail = new ArrayList<FixReportReqMaster>();

		getLogger().info("TOTAL LIST JOIN:" + JoinQuery.size());
		if (partial.size() == JoinQuery.size()) {
			for (FixReportReqMaster finalComplete : partial) {
				if (finalComplete != null) {
					for (int y = 0; y < JoinQuery.size(); y++) {
						Object[] objFixreportreqmaster = (Object[]) JoinQuery.get(y);

						if (finalComplete.getQid().compareTo(Integer.parseInt(objFixreportreqmaster[0].toString())) == 0) {
							// if QID are match, get Additional field into FinalComplete
							//getLogger().info("MATCHED:" + finalComplete.getCompositeId().getIdBatch());
							finalComplete.getTempStat().setReason((String) objFixreportreqmaster[2]);

							if ("P".equals(String.valueOf(objFixreportreqmaster[1]))) {
								finalComplete.setStatus("Process");
							} else if ("D".equals(String.valueOf(objFixreportreqmaster[1]))) {
								finalComplete.setStatus("Ready To Download");
							} else if ("E".equals(String.valueOf(objFixreportreqmaster[1]))) {
								finalComplete.setStatus("Error");
							} else {
								finalComplete.setStatus("Queing");
							}
							break;
						}
					}
				}
			}
		}
		return partial;
	}

	public List<FixReportReqMaster> listDownload(String user, Date now, Date Yesterday) {

		StringBuilder sc = new StringBuilder();
		sc.append("from FixReportReqMaster where idRequest = :pUser");
		sc.append(" AND Status = 'Download'");
		sc.append(" AND DtmRequest <= :pdate ");
		sc.append(" AND DtmRequest > :pdateyes ");
		sc.append(" order by QID");
		Query begin = getSession().createQuery(sc.toString());
		getLogger().info("User : " + user);
		begin.setString("pUser", user);
		begin.setTimestamp("pdate", now);
		begin.setTimestamp("pdateyes", Yesterday);

		List<FixReportReqMaster> first = begin.list();
		
		String[] namesFile;
		int index = 0;

		for (FixReportReqMaster additional : first) {
			FixStatus statAdd = new FixStatus();
			//if (additional.getStatus().equalsIgnoreCase("Ready To Download")) {
			if (additional.getFilePath().contains("/")) {
				namesFile = additional.getFilePath().split("\\/");
				index = namesFile.length - 1;
				statAdd.setNameFile(namesFile[index]);
				//getLogger().info("NAMEFILE:" + statAdd.getNameFile());
			} else {
				statAdd.setNameFile(additional.getFilePath());
			}
			additional.setTempStat(statAdd);
		}
		getLogger().info("TOTAL LIST AFTER ADD :" + first.size());

		return first;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	public FixReportReqMaster get(FixReportReqMasterPK idBatch) {
		return (FixReportReqMaster) getSession().get(FixReportReqMaster.class, idBatch);
	}
}
