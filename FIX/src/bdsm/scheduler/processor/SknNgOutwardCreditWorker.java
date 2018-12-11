package bdsm.scheduler.processor;


import static bdsm.service.SknNgCreditService.CODE_DKE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import bdsm.fcr.model.BaBankMast;
import bdsm.model.SknNgInOutCreditDetail;
import bdsm.model.SknNgInOutCreditFooter;
import bdsm.model.SknNgInOutCreditHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.exception.SkipProcessException;
import bdsm.service.SknNgOutwardCreditService;
import bdsm.service.SknNgSPKWebService;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.SknNgInOutCreditDetailDAO;
import bdsmhost.dao.SknNgInOutCreditFooterDAO;
import bdsmhost.dao.SknNgInOutCreditHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaSiteDAO;


/**
 * @author v00019372
 */
public class SknNgOutwardCreditWorker extends BaseProcessor {
    /**
     * 
     */
    protected static final String OUT = "O";
    /**
     * 
     */
    protected SknNgInOutCreditHeaderDAO headerInOutDAO = new SknNgInOutCreditHeaderDAO(null);
    /**
     * 
     */
    protected SknNgInOutCreditDetailDAO detailInOutDAO = new SknNgInOutCreditDetailDAO(null);
    /**
     * 
     */
    protected SknNgInOutCreditFooterDAO footerInOutDAO = new SknNgInOutCreditFooterDAO(null);
	
	
    /**
     * 
     * @param context
     * @throws Exception
     */
    public SknNgOutwardCreditWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.session);
		BaSiteDAO baSiteDAO = new BaSiteDAO(this.session);
		headerInOutDAO.setSession(this.session);
		detailInOutDAO.setSession(this.session);
		footerInOutDAO.setSession(this.session);
		SknNgOutwardCreditService service = new SknNgOutwardCreditService();
		
		if ((StatusDefinition.IN.equals(this.context.get(MapKey.typeFix))) && (StatusDefinition.UNAUTHORIZED.equals(this.context.get(MapKey.status)))) {
			String fileFullPathname = this.context.get(MapKey.param5).toString();
			String batchNo = this.context.get(MapKey.batchNo).toString();
			String idUser = "SYSTEM";
			
			SknNgInOutCreditHeader modelHeader = null;
			SknNgInOutCreditDetail modelDetail = null;
			SknNgInOutCreditFooter modelFooter = null;
			
			String line = "";
			StringBuilder sbCrcCheck = new StringBuilder();
			int crcValue = 0;
			int ctrRow = 1;
			boolean valid = false;
			
			Date batchDate;
			BigDecimal amountHeader, totalAmountDetail;
			BufferedReader rd = new BufferedReader(new FileReader(fileFullPathname));
			
			this.getLogger().info("[START] Data Processing for File : " + fileFullPathname);
			try {
				amountHeader = totalAmountDetail = BigDecimal.ZERO;
				BaBankMast baBankMast = baBankMastDAO.get();
				
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
									modelHeader = service.parseToInOutHeader(line, batchNo, Integer.valueOf(ctrRow));
									modelHeader.setIdMaintainedBy(idUser);
									
									
									/* === validation === */
									// validate against SPK Date
									if (ctrRow == 1) {
										bdsm.fcr.model.BaSite baSite = 
												baSiteDAO.getByMacroName(
													SknNgSPKWebService.CONVENT_BIC.equals(modelHeader.getIdentitasPesertaPengirimPenerus())?
															"ALLOW_SKN_CREDIT_CONVENT": 
															"ALLOW_SKN_CREDIT_SYARIA"
												);
							
										if (baSite != null) {
											synchronized(DateUtility.DATE_FORMAT_YYYYMMDD) {
												Date SPKDate = DateUtils.truncate(
																	DateUtility.DATE_FORMAT_YYYYMMDD.parse(baSite.getCompositeId().getMacroValue()),
																	java.util.Calendar.DATE
																);
												
												if (DateUtils.isSameDay(SPKDate, baBankMast.getBusinessDate()) == false)
													throw new SkipProcessException("SKIP FOR REPEAT");
											}
										}
									}
									
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
									
									if (DateUtils.isSameDay(batchDate, baBankMast.getBusinessDate()) == false) {
										rejectHeader(modelHeader, "Not Today Batch Date : " + modelHeader.getTanggalBatch());
										valid = true; continue;
									}
									
									
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
									
									if (!modelHeader.getKodeDke().equals(CODE_DKE))
										rejectHeader(modelHeader, "Invalid Code DKE");
									
									
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
									modelDetail = service.parseToInOutDetail(line, batchNo, Integer.valueOf(ctrRow), modelHeader);
									modelDetail.setIdMaintainedBy(idUser);
									
									
									/* === validation === */
									sbCrcCheck.append(line.substring(0, service.getLenG2Detail()));
									
									try {
										totalAmountDetail = totalAmountDetail.add(new BigDecimal(modelDetail.getNominal().trim().replaceFirst(",", ".")));
									}
									catch (NumberFormatException nfe) {
										if (!Boolean.FALSE.equals(modelHeader.getApproved()))
											rejectHeader(modelHeader, "Invalid Amount Format (Line " + ctrRow + ")");
										valid = true; continue;
									}
									
									
									if (!modelDetail.getKodeDke().equals(CODE_DKE)) {
										if (!Boolean.FALSE.equals(modelHeader.getApproved()))
											rejectHeader(modelHeader, "Invalid Code DKE (Line " + ctrRow + ")");
										valid = true; continue;
									}
									
									valid = true;
								}
								while (!valid);
								
								this.detailInOutDAO.insert(modelDetail);
							}
							break;
						case '3': {
								do {
									if (modelHeader == null)
										throwFIXException("HEADER IS MISSING", ctrRow);
									
									/* Footer */
									crcValue = bdsm.util.CRC16.CRC16(sbCrcCheck.toString(), 0);
									modelFooter = service.parseToInOutFooter(line, batchNo, Integer.valueOf(ctrRow), modelHeader);
									modelFooter.setIdMaintainedBy(idUser);
									
									if (crcValue != Integer.parseInt(modelFooter.getCRC())) {
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
									
									this.headerInOutDAO.insert(modelHeader);
									this.footerInOutDAO.insert(modelFooter);
									
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
		}
		
		return true;
	}
	
	
	private static void rejectHeader(SknNgInOutCreditHeader header, String reason) {
		if (header.getComments() == null) {
			header.setComments(reason);
			header.setApproved(Boolean.FALSE); // Rejected
		}
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
