package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import bdsm.model.BigBillTrx;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.BigBillService;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.BigBillTrxDAO;


/**
 * @author v00019372
 */
public class BigBillExtractWorker extends BaseProcessor {
    /**
     * 
     */
    public static final String TYPE_INQUIRY = "1";
    /**
     * 
     */
    public static final String TYPE_FLAGGING = "2";
	
    /**
     * 
     */
    public static final String INQUIRY_REQUEST = "1";
    /**
     * 
     */
    public static final String INQUIRY_PROCESS = "2";
    /**
     * 
     */
    public static final String INQUIRY_RESPONDED = "3";
    /**
     * 
     */
    public static final String FLAGGING_REQUEST = "4";
    /**
     * 
     */
    public static final String FLAGGING_PROCESS = "5";
    /**
     * 
     */
    public static final String FLAGGING_RESPONDED = "6";
	
    /**
     * 
     */
    protected static final String FOLDER = "BIG_BILL";
    /**
     * 
     */
    protected static final String PREFIX_FILENAME = "BIGBILL";
    /**
     * 
     */
    protected static String PROCEDURE_NAME = "PK_BDSM_BIGBILL.afterGettingResponse";
	
	
    /**
     * 
     * @param context
     */
    public BigBillExtractWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		BigBillTrxDAO bigBillTrxDAO = new BigBillTrxDAO(this.session);
		File parentFolder = new File(PropertyPersister.dirFixOut, FOLDER);
		
		
		/* Check whether there is a file with it's name start with BIG_BILL, if there is then skip process */
		/*
		String[] arrFilename = parentFolder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(PREFIX_FILENAME);
			}
		});
		if (arrFilename.length > 0) return true;
		*/
		
		//String batchNo = bigBillTrxDAO.pickBatchNo(INQUIRY_REQUEST.charAt(0), FLAGGING_REQUEST.charAt(0));
		String batchNo = this.context.get(MapKey.param6).toString();
		this.getLogger().info("Processing batch no: " + batchNo);
		List<BigBillTrx> list = null;
		Date date = new Date();
		int iteration = 0;
		
		while ((batchNo != null) 
					&& ((list = bigBillTrxDAO.listRequestData(
									batchNo, 
									PropertyPersister.BIGBILL_MAX_TO_MIDDLEWARE, 
									PropertyPersister.BIGBILL_RETRY_TO_MIDDLEWARE,  
									INQUIRY_REQUEST.charAt(0), 
									FLAGGING_REQUEST.charAt(0)
								)) != null) 
					&& (list.size() > 0)) {
			String timestamp = DateUtility.DATE_TIME_FORMAT_YYYYMMDD.format(new java.util.Date());
			timestamp = timestamp.replaceAll("[:-]", "").replace(' ', '_');
			File newFile = new File(parentFolder, PREFIX_FILENAME + "_" + timestamp +  "_" + batchNo + "_" + ++iteration + ".req");
			StringBuffer sb = new StringBuffer();
			
			newFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
			
			for (BigBillTrx trx : list) {
				sb.delete(0, sb.length())
					.append(trx.getCustomerIdPelanggan())
					.append(';').append(trx.getTypeTransaction())
					.append(';').append(trx.getRecordId())
					.append(';').append(TYPE_FLAGGING.equals(trx.getTypeTransaction())? trx.getAmountFlagging().toString(): "")
					;
				
				writer.write(sb.toString());
				writer.newLine();
				
				if (TYPE_INQUIRY.equals(trx.getTypeTransaction()))
					trx.setRecordStatus(INQUIRY_PROCESS);
				else if (TYPE_FLAGGING.equals(trx.getTypeTransaction()))
					trx.setRecordStatus(FLAGGING_PROCESS);
				
				trx.setDateTimeProcess(new java.sql.Timestamp(date.getTime()));
			}
			
			writer.flush();
			writer.close();
			
			/* Commit */
			this.commitTransaction();
			
			/* Send to Middleware */
			callDataProcessor(newFile, new File(PropertyPersister.dirFixInTemp, FilenameUtils.getBaseName(newFile.getName()) + ".resp"));
			
			/* Backup File */
			FileUtils.moveFile(newFile, new File(PropertyPersister.dirFixOutOk, FOLDER + newFile.getName()));
		}
		
		if (batchNo != null)
			this.session.createSQLQuery("{ CALL " + PROCEDURE_NAME + "(?) }").setParameter(0, batchNo).executeUpdate();
		
		return true;
	}
	
	
	/**
     * @param fileSource 
     * @param fileResult 
     * @throws Exception 
     * @author 00000394
	 */
	protected void callDataProcessor(File fileSource, File fileResult) throws Exception {
		String[] aCmdArgs = { PropertyPersister.CMD_PERL, PropertyPersister.BIGBILL_PERL, fileSource.getPath(), fileResult.getPath() };

		Runtime oRuntime = Runtime.getRuntime();
		Process oProcess = null;

		try {
			oProcess = oRuntime.exec(aCmdArgs, null, new File(PropertyPersister.dirFixHome));
			oProcess.waitFor();
		}
		catch (Exception ex) {
			this.getLogger().error("Error executing " + aCmdArgs[0]);
			throw ex;
		}
		
		
		/* print final result of process */
		int exitStatus = oProcess.exitValue();
		this.getLogger().info("Exit status = " + exitStatus);
		
		BufferedReader readerResult;
		if (exitStatus == 0) {
			readerResult = new BufferedReader(new FileReader(fileResult));
			BigBillService.doParsingResult(readerResult, this.session);
			
			this.commitTransaction();
			readerResult.close();
			
			FileUtils.moveFile(fileResult, new File(PropertyPersister.dirFixOutOk, FOLDER + fileResult.getName()));
		}
		else {
			readerResult = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
			StringBuffer strErr = new StringBuffer("Exit status = " + Integer.valueOf(exitStatus).toString() + ". ");
			
			String sLine;
			while ((sLine = readerResult.readLine()) != null)
				strErr.append(sLine + "\n");
			
			readerResult.close();
			
			throw new Exception(strErr.toString());
		}
	}
	
	private void commitTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}

}
