package bdsmhost.fcr.dao;


import java.util.List;

import bdsm.util.SchedulerUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;


/**
 * 
 * @author v00019372
 *
 */
public class DataMasterDAO extends bdsmhost.dao.BaseDao {

	public DataMasterDAO(Session session) {
		super(session);
	}

	 protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllData(String data, String like) {
		Query query = this.getSession().getNamedQuery("fcrDataMaster#" + data);
		if (like.indexOf("|") > -1) {
			List params = SchedulerUtil.getParameter(like, "|");
			this.getLogger().info("Split " + params);
			query.setString("like", (params.get(0).toString() == null) ? "" : (params.get(0).toString() == "%") ? "" : params.get(0).toString());
			query.setInteger("cdbranch", Integer.parseInt((params.get(1).toString() == null) ? "" : params.get(1).toString()));
			if (params.size() == 3) {
				query.setString("codTag", (params.get(2).toString() == null) ? "" : params.get(2).toString());
			}
		}
		else {
			query.setString("like", (like == null) ? "" : like);
		}
		
		return ((List<Object[]>) query.list());
	}

	@SuppressWarnings("unchecked")
	public List<Map> getAllDataMap(String data, String like) {
		Query query = this.getSession().getNamedQuery(data);
		query.setString("like", (like==null)? "": like);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return ((List<Map>) query.list());
	}

	public Object[] getDataById(String data, Object id) {
		Query query = this.getSession().getNamedQuery("fcrDataMasterById#" + data);
		
		if (id instanceof String)
			query.setString("id", (String)id);
		else if (id instanceof Integer)
			query.setInteger("id", (Integer)id);
		
		return ((Object[]) query.uniqueResult());
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
