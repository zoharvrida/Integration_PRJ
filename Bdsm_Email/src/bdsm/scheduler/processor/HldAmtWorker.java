/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.AmtHldDetailsDAO;
import bdsm.scheduler.dao.AmtHldFooterDAO;
import bdsm.scheduler.dao.AmtHldHeaderDAO;
import bdsm.scheduler.model.AmtHldDetails;
import bdsm.scheduler.model.AmtHldFooter;
import bdsm.scheduler.model.AmtHldHeader;
import bdsm.scheduler.model.AmtHldKey;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author v00018192
 */
public class HldAmtWorker extends BaseProcessor {

	protected Map innerContext;
	protected String pattern;
	protected String idBatch;
	protected String status;
	protected String sourceProcess;

	public HldAmtWorker(Map context) {
		super(context);
	}

	@Override
	protected boolean doExecute() {
		getLogger().info("EXECUTE HOLD AMOUNT WORKER");
		HashMap hMap = new HashMap();
		innerContext = hMap;

		String fileName = null;
		String namTemplate = null;
		String idScheduler = null;
		String lineDetails;
		BufferedReader br = null;
		String[] FileArr = null;
		String[] TempArr = null;
		AmtHldKey modelPk = null;
		AmtHldHeaderDAO headerDAO = new AmtHldHeaderDAO(session);
		AmtHldHeader modelHeader = null;
		AmtHldDetails modelDetails = null;
		AmtHldDetailsDAO detailsDAO = new AmtHldDetailsDAO((session));
		AmtHldFooter modelFooter = null;
		AmtHldFooterDAO footerDAO = new AmtHldFooterDAO(session);
		String FileId = null;
		int Counter = 0;
		try {
			namTemplate = context.get(MapKey.templateName).toString(); // filepatern
			idScheduler = context.get(MapKey.idScheduler).toString();
			FileId = context.get(MapKey.batchNo).toString();
			try {
				fileName = context.get(MapKey.param5).toString();
				this.pattern = fileName;
			} catch (Exception e) {
				getLogger().info("CHANGE VALUE :" + e);
				fileName = this.pattern;
			}
			this.idBatch = context.get(MapKey.batchNo).toString();
			this.status = context.get(MapKey.status).toString();
			this.sourceProcess = context.get(MapKey.processSource).toString();

			getLogger().debug("Batch ID :" + this.idBatch);
			getLogger().debug("Source Process :" + sourceProcess);
			getLogger().debug("status :" + status);
			getLogger().debug("filename :" + fileName);
			getLogger().debug("Pattern :" + namTemplate);
			getLogger().debug("ID Scheduler :" + idScheduler);
		} catch (Exception e) {
			getLogger().info("Exception :" + e);
		}

		//Read File
		if (StatusDefinition.UNAUTHORIZED.equalsIgnoreCase(context.get(MapKey.status).toString())) {
			try {
				br = new BufferedReader(new FileReader(fileName));
			} catch (FileNotFoundException ex) {
				getLogger().info("FILE NOT FOUND :" + ex);
			}
			getLogger().info("Open File : " + fileName);
			try {

				while (((lineDetails = br.readLine()) != null)) {
					Counter++;
					if (lineDetails.substring(0, 1).equals(StatusDefinition.AmtHldHeader)) {
						modelPk = new AmtHldKey();
						modelPk.setFileid(FileId);
						modelHeader = new AmtHldHeader();
						getLogger().info("Preparing Value Header : " + lineDetails);
						TempArr = lineDetails.split("\\|");
						modelPk.setRecId(Counter);
						getLogger().info("setRecordId : " + Counter);

						modelHeader.setCompositeId(modelPk);
						getLogger().info("setFileId : " + FileId);
						modelHeader.setRectype(Integer.parseInt(TempArr[0].trim()));
						getLogger().info("setRecType : " + Integer.parseInt(TempArr[0].trim()));
						modelHeader.setFiledate(TempArr[1].trim());
						getLogger().info("setFileDate : " + TempArr[1].trim());
						modelHeader.setFlgHold(TempArr[2].trim());
						getLogger().info("setFlgHold : " + TempArr[2].trim());
						modelHeader.setRecordtype(TempArr[0].trim());
						getLogger().info("setRecordType : " + TempArr[0].trim());
						modelHeader.setRecordname("Header");
						getLogger().info("setRecordName : Header");
						modelHeader.setData(lineDetails);
						getLogger().info("setData : " + lineDetails);
						modelHeader.setLength(lineDetails.length());
						getLogger().info("setLength : " + lineDetails.length());
						modelHeader.setComments(null);
						getLogger().info("setComments : null");
						modelHeader.setRecordstatus("1");
						getLogger().info("setRecordStatus : 1");
						modelHeader.setParentrecid(0);
						getLogger().info("setParentRecId : 0");
						getLogger().info("Inserting");
                                                headerDAO.insert(modelHeader);
						getLogger().info("Inserting Done");
					} else if (lineDetails.substring(0, 1).equals(StatusDefinition.AmtHldDetail)) {
						modelPk = new AmtHldKey();
						modelPk.setFileid(FileId);
						TempArr = lineDetails.split("\\|");
						getLogger().info("Preparing Value Details :" + lineDetails);
						modelDetails = new AmtHldDetails();
						modelPk.setRecId(Counter);
						getLogger().info("setRecordId : " + Counter);
						modelDetails.setCompositeId(modelPk);
						getLogger().info("setFileId : " + FileId);
						modelDetails.setRectype(Integer.parseInt(TempArr[0].trim()));
						getLogger().info("setRecType : " + Integer.parseInt(TempArr[0].trim()));
						modelDetails.setDattxn(TempArr[1].trim());
						getLogger().info("setDatTxn : " + TempArr[1].trim());
						modelDetails.setCodacctno(TempArr[2].trim());
						getLogger().info("setCodAcctNo : " + TempArr[2].trim());
						modelDetails.setCodccbrn(Integer.parseInt(TempArr[3].trim()));
						getLogger().info("setCodCcBrn : " + TempArr[3].trim());
						modelDetails.setAmthold(TempArr[4].trim());
						getLogger().info("setAmtHold : " + TempArr[4].trim());
						modelDetails.setHolddesc(TempArr[5].trim());
						getLogger().info("setHoldDesc : " + TempArr[5].trim());
						modelDetails.setEarmarktype(Integer.parseInt(TempArr[6].trim()));
						getLogger().info("setEarMarkType : " + Integer.parseInt(TempArr[6].trim()));
						modelDetails.setEarmarkreason(Integer.parseInt(TempArr[7].trim()));
						getLogger().info("setEarMarkReason : " + Integer.parseInt(TempArr[7].trim()));
						modelDetails.setDatexpire(TempArr[8].trim());
						getLogger().info("setDatExpire : " + TempArr[8].trim());
						modelDetails.setRecordtype(TempArr[0].trim());
						getLogger().info("setRecordType : " + TempArr[0].trim());
						modelDetails.setRecordname("Detail");
						getLogger().info("setRecordName : Detail");
						modelDetails.setData(lineDetails);
						getLogger().info("setData : " + lineDetails);
						modelDetails.setLength(lineDetails.length());
						getLogger().info("setLength : " + lineDetails.length());
						modelDetails.setComments(null);
						getLogger().info("setComments : null");
						modelDetails.setRecordstatus("1");
						getLogger().info("setRecordStatus : 1");
						modelDetails.setParentrecid(0);
						getLogger().info("setParentRecId : 0");
						getLogger().info("Inserting");
						detailsDAO.insert(modelDetails);
						getLogger().info("Inserting Done");
					} else if (lineDetails.substring(0, 1).equals(StatusDefinition.AmtHldFooter)) {
						modelPk = new AmtHldKey();
						modelPk.setFileid(FileId);
						modelPk.setRecId(Counter);
						modelFooter = new AmtHldFooter();
						getLogger().info("setRecordId : " + Counter);
						TempArr = lineDetails.split("\\|");
						modelFooter.setCompositeId(modelPk);
						getLogger().info("setFileId : " + FileId);
						modelFooter.setRectype(Integer.parseInt(TempArr[0].trim()));
						getLogger().info("setRecType : " + Integer.parseInt(TempArr[0].trim()));
						modelFooter.setNoOfRec(Integer.parseInt(TempArr[1].trim()));
						getLogger().info("setNoOfRec : " + Integer.parseInt(TempArr[1].trim()));
						modelFooter.setRecordtype(TempArr[0]);
						getLogger().info("setRecordType : " + TempArr[0]);
						modelFooter.setRecordname("Footer");
						getLogger().info("setRecordName : Footer");
						modelFooter.setData(lineDetails);
						getLogger().info("setData : " + lineDetails);
						modelFooter.setLength(lineDetails.length());
						getLogger().info("setLength : " + lineDetails.length());
						modelFooter.setComments(null);
						getLogger().info("setComments : null");
						modelFooter.setRecordstatus("1");
						getLogger().info("setRecordStatus : 1");
						modelFooter.setParentrecid(0);
						getLogger().info("setParentRecId : 0");
						getLogger().info("Inserting");
						footerDAO.insert(modelFooter);
						getLogger().info("Inserting Done");
					} else {
						getLogger().info("Wrong Record Type : " + lineDetails);
					}
				}
			} catch (Exception exception) {
				getLogger().info("EXCEPTION :" + exception);
				exception.getStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException iOException) {
						getLogger().info("IO EXCEPTION :" + iOException);
					}
				}
			}
                        context.put(MapKey.param1, Counter);
                        context.put(MapKey.param6, FileId);
			return true;
		} else if (StatusDefinition.AUTHORIZED.equalsIgnoreCase(context.get(MapKey.status).toString())) {
			getLogger().info("Calling Procedure ");
                        fileName = FilenameUtils.getName(fileName);
                        Counter = Integer.parseInt(context.get(MapKey.param1).toString());
                        FileId = context.get(MapKey.param6).toString();
                        getLogger().info("FileName : ");
                        getLogger().info("File ID : "+FileId);
                        getLogger().info("Total Record : "+Counter);
			headerDAO.runAmtHold(fileName, FileId,Counter);
			getLogger().info("Calling Procedure Done");
			return true;
		} else {
			return false;
		}

	}
}
