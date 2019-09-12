package bdsm.scheduler.processor;


import static bdsm.service.SknNgDebitService.CODE_DKE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import bdsm.fcr.model.BaBankMast;
import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.SknNgInwardDebitWorkerDAO;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixLogPK;
import bdsm.scheduler.model.FixQXtract;
import bdsm.service.SknNgInwardDebitService;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.SknNgClearingRegionDAO;
import bdsmhost.dao.SknNgInOutDebitDetailDAO;
import bdsmhost.dao.SknNgInOutDebitFooterDAO;
import bdsmhost.dao.SknNgInOutDebitHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("unchecked")
public class SknNgInwardDebitWorker extends BaseProcessor {
	
    /**
     * 
     * @param context
     * @throws Exception
     */
    public SknNgInwardDebitWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		Integer HPlus = 0;
		String userBatchNo = null;
		String branchNo = null;
		String idUser = null;
		
		SknNgInOutDebitHeaderDAO headerDAO = new SknNgInOutDebitHeaderDAO(session);
		SknNgInOutDebitDetailDAO detailDAO = new SknNgInOutDebitDetailDAO(session);
		SknNgInOutDebitFooterDAO footerDAO = new SknNgInOutDebitFooterDAO(session);
		SknNgInwardDebitWorkerDAO workerDAO = new SknNgInwardDebitWorkerDAO(session);
		BaBankMastDAO bankMastDAO = new BaBankMastDAO(session, PropertyPersister.codEntityVPD);
		SknNgInwardDebitService service = new SknNgInwardDebitService();
		StringBuilder sbCrcCheck = new StringBuilder();
		BaBankMast bankMaster = bankMastDAO.get();
		
		
		/* === typeFix is Extract === */
		if (StatusDefinition.XTRACT.equals(context.get(MapKey.typeFix))) {
			String param6 = (String) this.context.get(MapKey.param6);
			
			if ((param6 != null) && (param6.startsWith("CHANGE"))) { // Change FIXLOG INBOXID
				String[] arrParam6 = param6.split("\\|"); // 0=CHANGE; 1=InboxId; 2=userBatchNo
				FixLogDao fixLogDAO = new FixLogDao(this.session);
				FixLog fixLogClone, fixLog;
				
				fixLog = fixLogDAO.get(arrParam6[1]);
				if (fixLog != null) {
					// Insert New FixLog
					fixLogClone = (FixLog) BeanUtils.cloneBean(fixLog);
					fixLogClone.setFixLogPK((FixLogPK) BeanUtils.cloneBean(fixLog.getFixLogPK()));
					fixLogClone.getFixLogPK().setInboxId(arrParam6[2]);
					fixLogDAO.insert(fixLogClone);
					
					// Delete Old FixLog
					fixLogDAO.delete(fixLog);
				}
			}
			else { // GEFU Txn Upload
				this.getLogger().info("Cek for Approval or Reject Message");
				
				HPlus = (context.get(MapKey.param1) != null)? Integer.parseInt(context.get(MapKey.param1).toString()): HPlus;
				branchNo = (String) context.get(MapKey.param2);
				idUser = (String) context.get(MapKey.param3);
				userBatchNo = param6;
				
				List<String> lstBatchNo;
				if (userBatchNo != null)
					lstBatchNo = java.util.Arrays.asList(userBatchNo);
				else {
					String batchDate = (context.get(MapKey.param4) != null)? 
											context.get(MapKey.param4).toString():
											new StringBuilder(DateUtility.DATE_FORMAT_YYYYMMDD.format(bankMaster.getBusinessDate()))
												.insert(4, "-").insert(7, "-").toString();
					lstBatchNo = headerDAO.listApprovedBatchNoByHPlus(HPlus, "NULL", batchDate);
				}
				
				if (lstBatchNo.size() > 0) {
					headerDAO.updateStatusByBatchNoCollectionAndHPlus(lstBatchNo, HPlus, 'P');
					this.tx.commit();
					this.tx = this.session.beginTransaction();
					
					for (String batchNo: lstBatchNo)
						service.createProcessG2ToG1AndRun(HPlus, batchNo, branchNo, idUser);
				}
			}
			
			return true;
		}
		
		
		/* === typeFix is IN === */
		
		/* Status is Authorised - Create FixQXtract to change FIXLOG INBOXID */
		if (StatusDefinition.AUTHORIZED.equals(context.get(MapKey.status))) {
			if (((Long) this.context.get("fileSize")).compareTo(Long.valueOf(PropertyPersister.FILESIZEMAX.intValue())) >= 0) {
				/* Create FixQXtract to change FIXLOG INBOXID */
				FixClassConfigDao fccDAO = new FixClassConfigDao(this.session);
				List<FixClassConfig> list = fccDAO.get(this.getClass().getName(), (String) this.context.get(MapKey.processSource), StatusDefinition.AUTHORIZED);
				
				if (list.size() > 0) {
					this.fixQXtract = new FixQXtract();
					fixQXtract.setIdScheduler(list.get(0).getIdScheduler());
					fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
					fixQXtract.setDtmRequest(SchedulerUtil.getTime());
					fixQXtract.setParam6("CHANGE|" + context.get(MapKey.batchNo).toString() + "|" + context.get("userBatchNo").toString());
				}
			}
			
			return true;
		}
		
		
		// Status is Unauthorised
		this.getLogger().info("[BEGIN] Read Incoming DPI");
		
		String param5 = context.get(MapKey.param5).toString();
		String[] param5Arr = FilenameUtils.getName(param5).split("_", 4);
		userBatchNo = param5Arr[1];
		idUser = param5Arr[2];
		branchNo = userBatchNo.split("-")[0];
		
		SknNgClearingRegionDAO clrgRegDAO = new SknNgClearingRegionDAO(session);
		FixLogDao fixLogDAO = new FixLogDao(session);
		SknNgInOutDebitHeader modelHeader = null;
		SknNgInOutDebitDetail modelDetail = null;
		SknNgInOutDebitFooter modelFooter = null;
		
		String line = "";
		int crcValue = 0;
		int ctrRow = 1;
		boolean valid = false;
		
		Date batchDate, nextWorkingDate;
		BigDecimal amountHeader, totalAmountDetail;
		String routingId;
		BufferedReader rd = new BufferedReader(new FileReader(param5));
		
		this.getLogger().info("[START] Data Processing for File : " + param5);
		try {
			amountHeader = totalAmountDetail = BigDecimal.ZERO;
			
			try {
				nextWorkingDate = DateUtils.truncate(workerDAO.getClearingBranchNextWorkingDate(Integer.valueOf(branchNo), bankMaster.getBusinessDate())
										, java.util.Calendar.DATE);
			}
			catch (java.text.ParseException pe) {
				this.getLogger().error(pe, pe);
				throw new FIXException("Next Working Clearing Date Branch Not Found");
			}
			
			while ((line = rd.readLine()) != null) {
				if (line.trim().length() == 0)
					continue;
				
				valid = false;
				switch (line.charAt(0)) {
					case '0': {
							do {
								if (modelHeader != null)
									throwFIXException("FOOTER IS MISSING", ctrRow);
								
								/* Header */
								modelHeader = service.parseToInOutHeader(line, userBatchNo, Integer.valueOf(ctrRow));
								modelHeader.setIdMaintainedBy(idUser);
								
								/* === validation === */
								// batch date validation
								try {
									synchronized(DateUtility.DATE_FORMAT_YYYYMMDD) {
										batchDate = DateUtils.truncate(DateUtility.DATE_FORMAT_YYYYMMDD.parse(modelHeader.getTanggalBatch().replaceAll("-", ""))
														, java.util.Calendar.DATE);
									}
								}
								catch (java.text.ParseException pe) {
									rejectHeader(modelHeader, "Invalid 'Tanggal Batch' Format: '" + modelHeader.getTanggalBatch() + "'");
									valid = true; continue;
								}
								
								if (batchDate.before(bankMaster.getBusinessDate())) {
									rejectHeader(modelHeader, "Back Batch Date : " + DateUtility.DATE_TIME_FORMAT_YYYYMMDD.format(batchDate)
											+ " of " + DateUtility.DATE_TIME_FORMAT_YYYYMMDD.format(bankMaster.getBusinessDate()));
									valid = true; continue;
								}
								else if (batchDate.after(nextWorkingDate)) {
									rejectHeader(modelHeader, "Future Batch Date : " + DateUtility.DATE_TIME_FORMAT_YYYYMMDD.format(batchDate)
											+ " of " + DateUtility.DATE_TIME_FORMAT_YYYYMMDD.format(nextWorkingDate));
									valid = true; continue;
								}
								else if ((DateUtils.isSameDay(batchDate, bankMaster.getBusinessDate()) ||
										(DateUtils.isSameDay(batchDate, nextWorkingDate))) == false) {
									rejectHeader(modelHeader, "Not Working Batch Date : " + modelHeader.getTanggalBatch());
									valid = true; continue;
								}
								
								/*
								if (DateUtils.isSameDay(batchDate, bankMaster.getBusinessDate()) == false) {
									rejectHeader(modelHeader, "Not Today Batch Date : " + modelHeader.getTanggalBatch());
									valid = true; continue;
								}
								*/
								
								
								// batch id validation
								if (StringUtils.isBlank(modelHeader.getBatchId()))
									throwFIXException("MISSING BATCH ID", ctrRow);
								
								
								// number of detail records format validation
								try {
									Short.parseShort(modelHeader.getJumlahRecords().trim());
								}
								catch (NumberFormatException nfe) {
									rejectHeader(modelHeader, "Invalid 'Jumlah Records' Format: '" + modelHeader.getJumlahRecords() + "'");
									valid = true; continue;
								}
								
								// total nominal format validation
								try {
									amountHeader = new BigDecimal(modelHeader.getTotalNominal().trim().replaceFirst(",", "."));
								}
								catch (NumberFormatException nfe) {
									rejectHeader(modelHeader, "Invalid 'Total Nominal' Format: '" + modelHeader.getTotalNominal() + "'");
									valid = true; continue;
								}
								
								// Routing Validation
								/* -- SKIP
								routingId = workerDAO.getRoutingId(modelHeader.getIdentitasPesertaPengirim(), modelHeader.getSandiKotaPengirim());
								if (routingId == null) {
									rejectHeader(modelHeader, "Invalid when Get Routing No.");
									valid = true; continue;
								}
								*/
								
								if (!modelHeader.getKodeDke().equals(CODE_DKE))
									rejectHeader(modelHeader, "Invalid Code DKE");
								
								
								// Checking whether H+1 or not
								if (DateUtils.isSameDay(batchDate, nextWorkingDate))
									modelHeader.setHPlus1(Boolean.TRUE);
								
								/*
								if (clrgRegDAO.get(Integer.valueOf(modelHeader.getSandiKotaPengirim())) != null)
									modelHeader.setHPlus1(Boolean.TRUE);
								*/
								
								
								modelFooter = null;
								valid = true;
							}
							while(!valid);
						}
						break;
					case '1': {
							do {
								if (modelHeader == null)
									throwFIXException("HEADER IS MISSING", ctrRow);
								
								/* Detail */
								modelDetail = service.parseToInOutDetail(line, userBatchNo, Integer.valueOf(ctrRow), modelHeader);
								modelDetail.setIdMaintainedBy(idUser);
								
								
								/* === validation === */
								sbCrcCheck.append(line.substring(0, service.getLenG2Detail()));
								
								try {
									totalAmountDetail = totalAmountDetail.add(new BigDecimal(modelDetail.getNominal().trim().replaceFirst(",", ".")));
								}
								catch (NumberFormatException nfe) {
									rejectDetail(modelDetail, "Invalid Amount Format");
									valid = true; continue;
								}
								
								
								try {
									Integer.parseInt(modelDetail.getNomorWarkat().trim());
								}
								catch (NumberFormatException nfe) {
									rejectDetail(modelDetail, "Cheque No. Must Be Number");
									valid = true; continue;
								}
								
								// Routing Validation
								/* -- SKIP
								routingId = workerDAO.getRoutingId(modelDetail.getIdentitasPesertaPenerima(), modelDetail.getSandiKotaPenerbit());
								if (routingId == null) {
									rejectDetail(modelDetail, "Invalid when Get Routing No.");
									valid = true; continue;
								}
								*/
								
								if (!modelDetail.getKodeDke().equals(CODE_DKE)) {
									rejectDetail(modelDetail, "Invalid Code DKE");
									valid = true; continue;
								}
								
								if (modelDetail.getSor().trim().length() == 0)
									rejectDetail(modelDetail, "Empty SOR");
								
								valid = true;
							}
							while (!valid);
							
							detailDAO.insert(modelDetail);
						}
						break;
					case '3': {
							do {
								if (modelHeader == null)
									throwFIXException("HEADER IS MISSING", ctrRow);
								
								/* Footer */
								crcValue = bdsm.util.CRC16.CRC16(sbCrcCheck.toString(), 0);
								modelFooter = service.parseToInOutFooter(line, userBatchNo, Integer.valueOf(ctrRow), modelHeader);
								modelFooter.setIdMaintainedBy(idUser);
								
								if (crcValue != Integer.parseInt(modelFooter.getCrc())) {
									rejectHeader(modelHeader, "CRC Check Not Match");
								}
								else if (Short.parseShort(modelHeader.getJumlahRecords().trim()) != 
										(ctrRow - modelHeader.getCompositeId().getRecordId() - 1)) {
									rejectHeader(modelHeader, "Total Records in Header Must Equals With Number of Details");
								}
								else if (amountHeader.compareTo(totalAmountDetail) != 0) {
									rejectHeader(modelHeader, "Header Amount Must Equals With Total Amount Of Details");
								}
									
								sbCrcCheck.delete(0, sbCrcCheck.length());
								totalAmountDetail = BigDecimal.ZERO;
								
								headerDAO.insert(modelHeader);
								footerDAO.insert(modelFooter);
								
								modelHeader = null; modelDetail = null;
								valid = true;
							}
							while (!valid);
						}
						break;
					default:
						throwFIXException("FIRST CHARACTER MUST BE 0(HEADER), 1(DETAIL) OR 3(FOOTER)", ctrRow);
				}
	
				ctrRow++;
			}
			
			if (!valid)
				throwFIXException("BLANK FILE", -1);
			else if (modelFooter == null)
				throwFIXException("FOOTER IS MISSING IN THE END LINE OF FILE", -1);
		}
		catch (IndexOutOfBoundsException ie) {
			this.getLogger().error(ie, ie);
			throwFIXException("LINE LENGTH ERROR: " + line.length(), ctrRow);
		}
		finally {
			rd.close();
		}
		
		FixLog fixLog = fixLogDAO.get(context.get(MapKey.batchNo).toString());
		fixLog.setFcrFileName(param5Arr[3]);
		this.getLogger().info("[FINISH] Data Processing for File : " + param5);
		this.getLogger().info("[END] Read Incoming DPI");
		
		this.context.put("fileSize", Long.valueOf(fixLog.getFileSize().intValue()));
		this.context.put("userBatchNo", userBatchNo);
		
		return true;
	}
	
	private static void rejectHeader(SknNgInOutDebitHeader header, String reason) {
		if (header.getComments() == null) {
			header.setComments(reason);
			header.setApproved(Boolean.FALSE); // Rejected
		}
	}
	
	private static void rejectDetail(SknNgInOutDebitDetail detail, String reason) {
		detail.setComments(reason);
		detail.setRecordStatus("7"); // BDSM Rejected
	}
	
    /**
     * 
     * @param message
     * @param line
     * @throws Exception
     */
    protected static void throwFIXException(String message, int line) throws Exception {
		if (line == -1)
			throw new FIXException(message);
		else
			throw new FIXException(message + " (Line " + line + ")");
	}
	
}
 
