package bdsm.scheduler.processor;


import bdsm.model.ARParamDetails;
import bdsm.model.ARParamMaster;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;


/**
 *
 * @author SDM
 */
public class GeneralAutoReplyReader extends BaseProcessor {

	private String emailApproval = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your approval is required for the attached file to be processed further. <br/>"
			+ "<br/>"
			+ "Please reply this email with:<br/>"
			+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
			+ "<b>Not ok</b>, if otherwise.<br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String emailDone = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Process " + this.context.get(MapKey.templateName).toString() + " has been processed. <br/>"
			+ "Please see result report in attachment. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String emailRejected = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process to " + context.get(MapKey.templateName).toString() + " has been Rejected by Supervisor. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String query;
	List<ARParamDetails> record = new ArrayList<ARParamDetails>();
	private static String GENERALPROP = "autoReply.properties";
	private Integer sizeField;
	private String batchNo;


    /**
     * 
     * @param context
     */
    public GeneralAutoReplyReader(Map<String, Object> context) {
		super(context);
	}


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String status = context.get(MapKey.status).toString();
		String profileName = context.get(MapKey.templateName).toString();

		ARParamMaster masterRec = null;

		String param5 = (String) context.get(MapKey.param5);
		batchNo = context.get(MapKey.batchNo).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		String param1 = context.get(MapKey.param1).toString();

		// prepare All Dao Statement
		FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
		GeneralARDao arDao = new GeneralARDao(session);
		FixSchedulerImportDao impDao = new FixSchedulerImportDao(session);
		FixEmailAccessDao emDao = new FixEmailAccessDao(session);

		
		this.getLogger().debug("Class Name : " + getClass().getName());
		this.getLogger().debug("Source Process : " + sourceProcess);
		this.getLogger().debug("Status Definition : " + StatusDefinition.UNAUTHORIZED);
		this.getLogger().debug("Class Config Dao Length : " + Integer.toString(classConfigDao.get(getClass().getName(), sourceProcess, status).size()));

		FixClassConfig fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, status).get(0);
		Integer idSchedulerXtract = fClassConfig.getIdScheduler();
		Integer idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
		this.getLogger().debug("ID Scheduler Xtract : " + Integer.toString(idSchedulerXtract));
		this.getLogger().debug("ID Scheduler : " + Integer.toString(idScheduler));
		record = arDao.paramDetailsReader(idScheduler, "I", "2");
		masterRec = arDao.paramProfile(idScheduler);
		sizeField = record.size();
		this.getLogger().debug("Record Size : " + Integer.toString(sizeField));

		this.getLogger().debug("ARParamDetails List");
		for (ARParamDetails o : record) {
			this.getLogger().debug("ID Order : " + o.getIdOrder());
			this.getLogger().debug("Type : " + o.getType());
			this.getLogger().debug("Field Name : " + o.getFieldName());
			this.getLogger().debug("Field Type : " + o.getFieldType());
			this.getLogger().debug("Field Format : " + o.getFieldFormat());
			this.getLogger().debug("Max Length : " + o.getMaxLength());
			this.getLogger().debug("Def Val : " + o.getDeffVal());
		}

		if (masterRec != null) {
			LinkedList<String> fieldOPR = new LinkedList<String>();
			LinkedList<String> fieldSPV = new LinkedList<String>();
			String fieldBatch, fieldStatusReason;
			fixQXtract = new FixQXtract();
			
			fieldBatch = fieldStatusReason = "";
			this.getLogger().debug("Validation : " + masterRec.getValidationFunction());
			this.getLogger().debug("Status : " + status);

			String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace(context.get(MapKey.templateName)+" ", "").replaceFirst("[Rr][Ee]: ", "")) + "{HHmmss}");
			this.getLogger().debug("OUT FILE : " + outFileName);
			this.getLogger().debug("Tracking Flag : " + masterRec.getTrackingFlag());
			
			FixSchedulerImport imp = impDao.get(idScheduler);
			if ("Y".equalsIgnoreCase(masterRec.getTrackingFlag())) {
				if (imp != null) {
					Map<String, String> fieldSplit = null;
					List<Map<Object, Map<String, Object>>> getVal = PropertyPersister.getRealParam(session, imp.getFixSchedulerPK().getIdTemplate(), "I");
					Map<Object, Map<String, Object>> mapParam = (Map<Object, Map<String, Object>>) getVal.get(0);
					Map<String, Object> lv1 = (Map<String, Object>) mapParam.get("STRING");
					
					if (lv1.isEmpty()) {
						throw new FIXException("PARAMETER INVALID");
					} else {
						String paramString = lv1.get("1").toString();
						getLogger().debug("PARAM1 : " + paramString);
						fieldSplit = PropertyPersister.parseKeyAndValueToMap(paramString);
						getLogger().debug("fieldParam : " + fieldSplit);
					}
					if (getVal != null) {
						for (Integer j = 0; j < 4; j++) {
							Map<String, String> fieldParam = PropertyPersister.parseKeyAndValueToMap(fieldSplit.get(j.toString()).toString().replaceAll("\\|", ";").replaceAll(":", "="));
							getLogger().debug("fieldParam INNER: " + fieldParam);
							if ("OPR".equalsIgnoreCase(fieldParam.get("LEVEL").toString())) {
								fieldOPR.add(fieldParam.get("FIELDNAME").toString());
							} else if ("SPV".equalsIgnoreCase(fieldParam.get("LEVEL").toString())) {
								fieldSPV.add(fieldParam.get("FIELDNAME").toString());
							}
							getLogger().debug("fieldParam : " + fieldParam.get("FIELDNAME"));
						}
					}
					
					// get status reason column name
					List<ARParamDetails> statusReason = arDao.paramDetails(idScheduler, "1");
					fieldStatusReason = statusReason.get(0).getFieldName();
					
					// get batch column name 
					for (ARParamDetails o : record) {
						if (o.getType().contains("2")) {
							fieldBatch = o.getFieldName();
							break;
						}
					}
				}
			}
			
			// Preparing FixQXtract
			boolean isUnAuthorized = StatusDefinition.UNAUTHORIZED.equals(status);
			Integer idSchedulerAfterProcess = isUnAuthorized? masterRec.getIdBefore(): masterRec.getIdAfter();
			this.getLogger().debug("fixQXtract set ID Scheduler : " + idSchedulerAfterProcess);
			
			if (idSchedulerAfterProcess != null) {
				FixSchedulerXtractDao fsxDAO = new FixSchedulerXtractDao(this.session);
				FixSchedulerXtract fsx = fsxDAO.get(idSchedulerAfterProcess);
				if (fsx != null)
					outFileName += ("." + fsx.getFileFormat().toLowerCase());
			}
			
			fixQXtract.setIdScheduler(idSchedulerAfterProcess);
			fixQXtract.setReason(idScheduler.toString() + "|" + profileName);
			
			String emailSpvs = emDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler);
			fixQXtract.setParam1(isUnAuthorized? ("RE: " + param1): param1); // email subject
			fixQXtract.setParam5(outFileName); // email file attachment
			
			if (isUnAuthorized) {
				// Read excel and insert into temporary table
				this.query = arDao.restructure(record, masterRec.getTableName());
				readExcel(param5, GENERALPROP, arDao);
				this.tx = this.session.beginTransaction();
				
				if ("Y".equalsIgnoreCase(masterRec.getTrackingFlag()))
					arDao.checkMark(this.paramFill(masterRec.getTableName(), fieldStatusReason, fieldBatch, fieldOPR), "OPERATOR");
				
				fixQXtract.setParam2(emailSpvs); // email to
			}
			else {
				if (StatusDefinition.YES.equals(this.context.get(MapKey.spvAuth))) {
					if ("Y".equalsIgnoreCase(masterRec.getTrackingFlag()))
						arDao.checkMark(this.paramFill(masterRec.getTableName(), fieldStatusReason, fieldBatch, fieldSPV), "SPV");
					
					FixInboxDao fixInboxDao = new FixInboxDao(session);
					if (StringUtils.isNotBlank((String) context.get(MapKey.itemIdLink)))
						fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender()); // email to
					fixQXtract.setParam3(emailSpvs); // email cc
				}
				else // auto approved (no supervisor)
					fixQXtract.setParam2(this.context.get(MapKey.emailSender).toString()); // email to
			}
			

			if (StatusDefinition.UNAUTHORIZED.equals(status)) {
				if (masterRec.getValidationFunction() != null) {
					this.getLogger().debug("Package Name : " + masterRec.getValidationFunction());
					this.getLogger().debug("Batch ID : " + batchNo);
					arDao.callPackage(masterRec.getValidationFunction(), batchNo);
				}
				fixQXtract.setParam4(emailApproval);
			}
			else if (StatusDefinition.AUTHORIZED.equals(status)) {
				arDao.callPackage(masterRec.getProcessFunction(), batchNo);
				fixQXtract.setParam4(emailDone);
			}
			else if (StatusDefinition.REJECTED.equals(status)) {
				arDao.callPackage(masterRec.getRejectFunction(), batchNo);
				fixQXtract.setParam4(emailRejected);
			}
			else {
				this.fixQXtract = null;
			}
			
			if ((this.fixQXtract != null) && (!sourceProcess.equalsIgnoreCase("sftp"))) {
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				//fixQXtract.setReason(status); // State
				fixQXtract.setParam6(batchNo);
				
				this.getLogger().info("Register FixQXtract Done");
			}
			
			return true;
		} else {
			this.getLogger().debug("Param Profile is empty");
			return false;
		}
	}

	private void readExcel(String param5, String configFile, GeneralARDao arDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException, Exception {
		if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
			readXLS(param5, configFile, arDao);
		} else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
			readXLSX(param5, configFile, arDao);
		}
		
		this.tx.commit();
	}

	private void readXLS(String param5, String configFile, GeneralARDao arDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, Exception {
		XLSReader xr = XLSReader.getInstance(param5, configFile);
		
		while (xr.hasNextRow()) {
			try {
				Object[] arrRecord = xr.nextRow();
				LinkedList<Object> arrContainer = new LinkedList<Object>(java.util.Arrays.asList(arrRecord));
				arrContainer.addFirst(this.batchNo);
				
				arrRecord = arrContainer.toArray();
				arDao.dumpTable(putintoMap(arrRecord), this.query);
			} catch (Exception ex) {
				this.getLogger().error(ex, ex);
			}
		}
	}

	private void readXLSX(String param5, String configFile, GeneralARDao arDao) throws Exception {
		XLSXReader xr = XLSXReader.getInstance(param5, configFile);
		
		while (xr.hasNextRow()) {
			try {
				Object[] arrRecord = xr.nextRow();
				if (arrRecord != null) {
					LinkedList<Object> arrContainer = new LinkedList<Object>(java.util.Arrays.asList(arrRecord));
					arrContainer.addFirst(this.batchNo);
					
					arrRecord = arrContainer.toArray();
					arDao.dumpTable(putintoMap(arrRecord), this.query);
				}
			} catch (Exception ex) {
				this.getLogger().error(ex, ex);
			}
		}
	}

	private List<Map<String, Object>> putintoMap(Object[] arrRecord) {
		this.getLogger().debug("arrRecord : " + Arrays.toString(arrRecord));
		Map<String, Object> typeMap;
		List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < this.sizeField; i++) {
			ARParamDetails detail = record.get(i);
			
			this.getLogger().debug("arrRecord index : " + i);
			this.getLogger().debug("fieldValue : " + ((i <= (arrRecord.length - 1))? arrRecord[i]: null));
			this.getLogger().debug("fieldName : " + detail.getFieldName());
			this.getLogger().debug("fieldType : " + detail.getFieldType() == null ? "" : detail.getFieldType());
			this.getLogger().debug("fieldFormat : " + detail.getFieldFormat() == null ? "" : detail.getFieldFormat());
			this.getLogger().debug("maxLength :" + detail.getMaxLength());
			this.getLogger().debug("defVal : " + detail.getDeffVal());
			
			typeMap = new HashMap<String, Object>();
			if (i == 0) // idBatch
				typeMap.put("fieldVal", this.batchNo);
			else
				typeMap.put("fieldVal", (i <= (arrRecord.length - 1))? arrRecord[i]: null);
			
			typeMap.put("fieldName", detail.getFieldName());
			typeMap.put("fieldType", detail.getFieldType() == null ? "" : detail.getFieldType());
			typeMap.put("fieldformat", detail.getFieldFormat() == null ? "" : detail.getFieldFormat());
			typeMap.put("maxLength", detail.getMaxLength());
			typeMap.put("defVal", detail.getDeffVal());
			
			recordList.add(typeMap);
		}
		
		return recordList;
	}
	
	private LinkedList<String> paramFill(String tableName, String statusReasonField, String batchField, java.util.Collection<String> addedCollection) {
		LinkedList<String> paramFill = new LinkedList<String>();
		LinkedList<String> fieldStd = new LinkedList<String>();
		
		fieldStd.push("STATUS");
		fieldStd.add(statusReasonField);
		fieldStd.add(batchField);

		paramFill.addFirst(tableName);
		paramFill.addAll(fieldStd);
		paramFill.addAll(addedCollection);
		paramFill.add(this.batchNo);
		
		this.getLogger().debug("Param Fill : " + paramFill);
		return paramFill;
	}

}
