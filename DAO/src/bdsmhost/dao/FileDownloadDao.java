/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class FileDownloadDao extends BaseDao {

	public FileDownloadDao(Session session) {
        super(session);
    }
	public String getList(String reportType) {
		Map JsonFile;
		String jsonTag;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT JSON_TAG ");
            sb.append("from WEBEXTENSION ");
            sb.append("where EXTENSION = :extension");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("extension", reportType);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		JsonFile =(Map) q.list().get(0);
		jsonTag = JsonFile.get("JSON_TAG").toString();
		return jsonTag;
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
