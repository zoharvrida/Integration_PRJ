package bdsm.scheduler.processor;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bdsm.model.ARParamDetails;
import bdsm.model.ARParamMaster;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSWriter;


/**
 * 
 * @author SDM
 */
public class GeneralAutoReplyWriter extends BaseProcessor {
	private static String GENERALPROP = "autoReply.properties";
	private FixQXtract fixQxtractData;
	private Integer idImportSched;
	private String batchNo;
	private String sheetName;


	public GeneralAutoReplyWriter(Map<String, Object> context) {
		super(context);
	}


	@Override
	protected boolean doExecute() throws Exception {
		boolean return_ = false;
		this.fixQxtractData = (FixQXtract) this.context.get(MapKey.fixQXtract);
		this.getLogger().debug("WRITER LOGGER :" + this.fixQxtractData.toString());
		
		List<String> param1 = SchedulerUtil.getParameter(this.fixQxtractData.getReason(), "\\|");
		this.getLogger().debug("LIST PARAM: " + param1);

		if (!param1.isEmpty()) {
			try {
				this.idImportSched = Integer.parseInt(param1.get(0));
				this.batchNo = this.fixQxtractData.getParam6();
				this.sheetName = param1.get(1);
				
				return_ = xlsWriter(PropertyPersister.dirFixOut + this.fixQxtractData.getParam5());
			}
			catch (Exception ex) {
				this.getLogger().info("ERROR WRITER :" + ex, ex);
			}
		}
		return return_;
	}

	public boolean xlsWriter(String filename) {
		GeneralARDao arDao = new GeneralARDao(this.session);
		ARParamMaster arMaster = arDao.paramProfile(this.idImportSched);
		List<ARParamDetails> arDetails = arDao.paramDetails(this.idImportSched, "O");
		
		XLSWriter xw = XLSWriter.getInstance(filename, GENERALPROP);
		xw.reset();
		xw.setSheetName(this.sheetName);

		StringBuilder hedField = new StringBuilder();
		String[] headerNames = new String[arDetails.size()];
		for (int i = 0; i < arDetails.size(); i++) {
			headerNames[i] = arDetails.get(i).getFieldName();
			hedField.append(arDetails.get(i).getFieldName()).append(",");
		}

		hedField.deleteCharAt(hedField.length() - 1);
		this.getLogger().info("HEADER: " + java.util.Arrays.toString(headerNames));
		xw.setHeaderNames(headerNames);
		
		if (arMaster.getQueryRet().contains(":idBatch")) {
			String realQuery = arMaster.getQueryRet().replaceAll(":idBatch", "'" + this.batchNo + "'");
			List<Map<String, Object>> resMapList = arDao.resultCall(arMaster.getTableName(), realQuery, hedField.toString());
			this.getLogger().debug("LIST QUERY: " + resMapList);

			if (!resMapList.isEmpty()) {
				for (Map<String, Object> respObj : resMapList) {
					List<Object> writer = new ArrayList<Object>();
					if (respObj != null) {
						Map<String, Object> fieldValue = (Map<String, Object>) respObj;
						for (int i = 0; i < headerNames.length; i++) {
							writer.add(fieldValue.get(headerNames[i]));
							this.getLogger().debug("LIST DATA: " + headerNames[i] + ":" + fieldValue.get(headerNames[i]));
						}
						xw.putRow(writer);
					}
				}
				xw.autoSizeColumn(headerNames.length);
				xw.writeToFile();
			}
		}
		else {
			return false;
		}
		
		return true;
	}
}
