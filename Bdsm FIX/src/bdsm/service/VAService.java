package bdsm.service;


import static bdsm.util.BdsmUtil.convertMoneyToString;
import static bdsm.util.BdsmUtil.convertStringToMoney;
import static bdsm.util.BdsmUtil.copyBeanProperties;
import static bdsm.util.BdsmUtil.getLeadingZeroStringFromNumber;
import static bdsm.util.BdsmUtil.rightPad;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrChAcctMast;
import bdsm.model.FcrChAcctMastPK;
import bdsm.model.TransactionParameter;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.TmpBdgwDao;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpVADao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.TmpBdgwDetail;
import bdsm.scheduler.model.TmpBdgwFooter;
import bdsm.scheduler.model.TmpBdgwHeader;
import bdsm.scheduler.model.TmpBdgwPK;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.scheduler.model.TmpGefuResponsPK;
import bdsm.scheduler.model.TmpVADetail;
import bdsm.scheduler.model.TmpVAFooter;
import bdsm.scheduler.model.TmpVAHeader;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.FcrChAcctMastDao;
import bdsmhost.dao.TransactionParameterDao;


/**
 * 
 * @author bdsm
 */
public class VAService {
	private   static final Logger logger = Logger.getLogger(VAService.class);
    /**
     * 
     */
    protected static final DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
    /**
     * 
     */
    protected static final DateFormat formatter2 = new SimpleDateFormat("yyMMdd");
	private   static final Map<String, String> binDummy = PropertyPersister.binDummy;
	
	private   static final String MODULE_NAME = "GEFU_VA";
	private   static final String MODULE_DESCRIPTION = "GEFU VA";
	private   static final String LINE_HEADER_CODE = "00";
	private   static final String LINE_DETAIL_CODE = "01";
	private   static final String LINE_FOOTER_CODE = "99";
	private   static final String DB_PACKAGE_NAME = "PKG_GEFU_VA";
	private   static final String DB_UPDATE_METHOD_NAME = "VA_UPDATE";
	private   static final String FILE_EXTENTION = "hit";
	
	private   static final String FCR_TABLE_HEADER = "ext_cawpayserv_headerrecdto";
	private   static final String FCR_TABLE_DETAIL = "ext_cawpayserv_detailrecdto";
	private   static final String FCR_TABLE_FOOTER = "ext_cawpayserv_footerrecdto";
	
	private   static final String INVALID_BIN_NO_PARAMETER = "Invalid BIN No in Parameter Setting"; 
	private   static final String INVALID_FILE_EXTENSION = "Invalid Filename Extension, It Must Be .hit";
	private   static final String INVALID_DATE = "Invalid Date In Filename";
	private   static final String DUPLICATE_CHECKSUM = "This File Content Has Been Uploaded Before!!";
	private   static final String INVALID_KEY_ENCRYPTION = "Invalid Key Encryption";
	private   static final String INVALID_DATE_HEADER = "Invalid Date In Header Record";
	private   static final String INVALID_HEADER_ACCT_NO = "Invalid Header Account No";
	private   static final String INVALID_HEADER_CCY = "Invalid Header Currency"; 
	private   static final String INVALID_HEADER_AMOUNT = "Header Amount Must Equals With Total Amount Of Details";
	private   static final String INVALID_HEADER_AMOUNT_FEE = "Header Amount Fee Must Equals With Total Amount Fee Of Details";
	private   static final String INVALID_GL_NO_AND_ACCT_NO = "GL No and Account No Must Be Same";
	private   static final String INVALID_GL_NO_MUST_ZERO = "GL No Must Zero";
	private   static final String FAILED_TRX_PARAM_AND_BUSS_DATE = "Failed Getting Trx Param Or Bussines Date";
	private   static final String REJECTED_BY_SPV = "Rejected By Supervisor";
	private   static final String WRONG_CONTENT = "Wrong Content File";
	
	
	private Session session;
	private TransactionParameterDao trxParamDAO;
	private FcrBaBankMastDao fcrBaBankMasterDAO;
	private FcrChAcctMastDao fcrChAcctMastDAO;
	private TmpVADao tmpVADAO;
	private TmpGefuResponsDao tmpGefuResponseDAO;
	private boolean crossCurrencyAllowed;
	private boolean nonRegisteredVAAllowed = true;
	
	
	/* === Overridable methods === */
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     * @param inputString
     * @param oCharRead
     * @return
     * @throws Exception
     */
    protected TmpVAHeader parseToVAHeader(String batchNo, int rowNo, String inputString, int[] oCharRead) throws Exception {
		int ctrColumn = 0;
		
		ctrColumn+=2; // Skip line indicator
		TmpVAHeader vaHeader = new TmpVAHeader(batchNo, rowNo);
		vaHeader.setProfileName(inputString.substring(ctrColumn, ctrColumn += 8).trim());
		vaHeader.setBusinessDate(VAService.formatter.parse(inputString.substring(ctrColumn, ctrColumn += 8).trim()));
		vaHeader.setInterfaceType(inputString.substring(ctrColumn, ctrColumn += 2).trim());
		vaHeader.setGLNo(inputString.substring(ctrColumn, ctrColumn += 12).trim());
		vaHeader.setCustomerCenter(inputString.substring(ctrColumn, ctrColumn += 5).trim());
		vaHeader.setApplicationCode(inputString.substring(ctrColumn, ctrColumn += 2).trim());
		vaHeader.setAccountNo(inputString.substring(ctrColumn, ctrColumn += 12).trim());
		vaHeader.setTransactionCurrency(inputString.substring(ctrColumn, ctrColumn += 3).trim());
		vaHeader.setAmount(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaHeader.setLocalCurrencyAmountOfTransaction(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaHeader.setAmountFee(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaHeader.setDescription1(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaHeader.setDescription2(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaHeader.setStatus(inputString.substring(ctrColumn, ctrColumn += 1).trim());
		vaHeader.setRejectCode(inputString.substring(ctrColumn, ctrColumn += 4).trim());
		vaHeader.setReferenceNo(inputString.substring(ctrColumn, ctrColumn += 20).trim());
		vaHeader.setX(inputString.charAt(ctrColumn++));
		vaHeader.setStatus(StatusDefinition.UNAUTHORIZED);
		
		oCharRead[0] = ctrColumn;
		
		return vaHeader;
	}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     * @param inputString
     * @param oCharRead
     * @return
     * @throws Exception
     */
    protected TmpVADetail parseToVADetail(String batchNo, int rowNo, String inputString, int[] oCharRead) throws Exception {
		int ctrColumn = 0;
		
		ctrColumn+=2; // Skip line indicator
		TmpVADetail vaDetail = new TmpVADetail(batchNo, rowNo);
		vaDetail.setTransactionCode(inputString.substring(ctrColumn, ctrColumn += 4).trim());
		vaDetail.setApplicationCode(inputString.substring(ctrColumn, ctrColumn += 2).trim());
		vaDetail.setAccountNo(inputString.substring(ctrColumn, ctrColumn += 12).trim());
		vaDetail.setTransactionAmount(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaDetail.setTransactionCurrency(inputString.substring(ctrColumn, ctrColumn += 3).trim());
		vaDetail.setLocalCurrencyAmountOfTransaction(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaDetail.setForeignExchangeRate(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 13), 7));
		vaDetail.setCustomerCenter(inputString.substring(ctrColumn, ctrColumn += 5).trim());
		vaDetail.setReferenceNo(inputString.substring(ctrColumn, ctrColumn += 20).trim());
		vaDetail.setDescription1(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaDetail.setDescription2(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaDetail.setAmountFee(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaDetail.setStatus(inputString.substring(ctrColumn, ctrColumn += 1).trim());
		vaDetail.setRejectCode(inputString.substring(ctrColumn, ctrColumn += 4).trim());
		vaDetail.setShortName(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaDetail.setVirtualAccountNo(inputString.substring(ctrColumn, ctrColumn += 16).trim());
		
		oCharRead[0] = ctrColumn;
		
		return vaDetail;
	}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     * @param inputString
     * @param oCharRead
     * @return
     * @throws Exception
     */
    protected TmpVAFooter parseToVAFooter(String batchNo, int rowNo, String inputString, int[] oCharRead) throws Exception {
		int ctrColumn = 0;
		
		ctrColumn+=2; // Skip line indicator
		TmpVAFooter vaFooter = new TmpVAFooter(batchNo, rowNo);
		vaFooter.setNoOfRecord(Integer.parseInt(inputString.substring(ctrColumn, ctrColumn += 10)));
		
		oCharRead[0] = ctrColumn;
		
		return vaFooter;
	}
	
	
	
	/* === Injection setters === */
	
    /**
     * 
     * @param session
     */
    public void setSession(Session session) {
		this.session = session;
	}
	
    /**
     * 
     * @param trxParamDAO
     */
    public void setTrxParamDAO(TransactionParameterDao trxParamDAO) {
		this.trxParamDAO = trxParamDAO;
	}
	
    /**
     * 
     * @param fcrBaBankMasterDAO
     */
    public void setFcrBaBankMasterDAO(FcrBaBankMastDao fcrBaBankMasterDAO) {
		this.fcrBaBankMasterDAO = fcrBaBankMasterDAO; 
	}
	
    /**
     * 
     * @param fcrChAcctMastDAO
     */
    public void setFcrChAcctMastDAO(FcrChAcctMastDao fcrChAcctMastDAO) {
		this.fcrChAcctMastDAO = fcrChAcctMastDAO; 
	}
	
    /**
     * 
     * @param tmpVADAO
     */
    public void setTmpVADAO(TmpVADao tmpVADAO) {
		this.tmpVADAO = tmpVADAO;
	}
	
    /**
     * 
     * @param tmpGefuResponseDAO
     */
    public void setTmpGefuResponseDAO(TmpGefuResponsDao tmpGefuResponseDAO) {
		this.tmpGefuResponseDAO = tmpGefuResponseDAO;
	}
	
	
    /**
     * 
     * @return
     */
    public boolean isCrossCurrencyAllowed() {
		return this.crossCurrencyAllowed;
	}
    /**
     * 
     * @param crossCurrencyAllowed
     */
    public void setCrossCurrencyAllowed(boolean crossCurrencyAllowed) {
		this.crossCurrencyAllowed = crossCurrencyAllowed;
	}
	
    /**
     * 
     * @return
     */
    public boolean isNonRegisteredVAAllowed() {
		return this.nonRegisteredVAAllowed;
	}
    /**
     * 
     * @param nonRegisteredVAAllowed
     */
    public void setNonRegisteredVAAllowed(boolean nonRegisteredVAAllowed) {
		this.nonRegisteredVAAllowed = nonRegisteredVAAllowed;
	}
	
	
	
	
	/* === Business service functions === */
	
    /**
     * 
     * @param batchNo
     * @param dataMap
     * @throws Exception
     */
    public void importGEFU(String batchNo, Map<String, Object> dataMap) throws Exception {
		logger.info("[BEGIN] Import GEFU VA");

		TmpVAHeader vaHeader = null;
		TmpVADetail vaDetail = null;
		TmpVAFooter vaFooter = null;
		BufferedReader br = null;
		String line, lineCode, virtualAccountNo, reason;
		BigDecimal totalDetailsAmount, totalDetailsAmountFee;
		int rowNoHeader = 0;
		int ctrLine, ctrRow, ctrColumn, ctrDetail;
		boolean hasHeader, hasDetail;
		
		totalDetailsAmount = totalDetailsAmountFee = BigDecimal.ZERO;
		ctrLine = ctrRow = ctrDetail = 0;
		hasHeader = hasDetail = false;
		
		String filename = (String) dataMap.get("filename");
		char[] buffer = (char[]) dataMap.get("buffer");
		boolean flagChecksum = (Boolean) dataMap.get("flagChecksum");
		String strChecksum = (String) dataMap.get("strChecksum");
		String templateName = (String) dataMap.get("templateName");
		
		FcrBaBankMast baBankMaster = this.fcrBaBankMasterDAO.get();
		TransactionParameter trxParam = this.trxParamDAO.getByModuleName(templateName);
		
		
		if (!FilenameUtils.getExtension(filename).equalsIgnoreCase(FILE_EXTENTION))
			throw new FIXException(INVALID_FILE_EXTENSION);
		
		// Validation business date and transaction parameter
		if ((baBankMaster == null) || (baBankMaster.getDatProcess() == null) || (trxParam == null))
			throw new FIXException(FAILED_TRX_PARAM_AND_BUSS_DATE);
		
		String dateFromFilename = filename.substring(6, 12);
		logger.debug("filename: " + filename);
		logger.debug("date File: " + formatter2.parse(dateFromFilename));
		logger.debug("datProcess: " + baBankMaster.getDatProcess());

		if (formatter2.parse(dateFromFilename).compareTo(baBankMaster.getDatProcess()) != 0) {
			logger.error("Invalid Time on Filename '" + filename + "'");
			throw new FIXException(INVALID_DATE);
		}
		
		// Validation checksum
		if (flagChecksum) {
			List<TmpVAHeader> vaHeaders = tmpVADAO.getHeadersByChecksum(strChecksum);
			if (vaHeaders.size() > 0)
				throw new FIXException(DUPLICATE_CHECKSUM);
		}
		
		// Validation BIN Dummy
		String binDummyNo = binDummy.get(templateName);
		if (binDummyNo == null)
			throw new FIXException(INVALID_BIN_NO_PARAMETER);
		
		// Process file parsing
		CharArrayReader car = new CharArrayReader(buffer);
		br = new BufferedReader(car);
		while ((line = br.readLine()) != null) {
			ctrLine++;
			if (StringUtils.isBlank(line))
				continue;
			else
				ctrRow++;
			
			if ((ctrRow == 1) && !("00IFSD2MC |00IFSC2MD ".indexOf(line.substring(0, 10)) > -1))
				throw new FIXException(INVALID_KEY_ENCRYPTION);
			
			ctrColumn = 0;
			lineCode = line.substring(0, ctrColumn += 2);

			try {
				int[] ctrOutput = new int[] {ctrColumn};
				
				// Header
				if (lineCode.equals(LINE_HEADER_CODE)) {
					if (hasHeader)
						throwWrongFormatFileException(ctrLine, "Wrong position header line in file");
					
					vaHeader = this.parseToVAHeader(batchNo, ctrRow, line, ctrOutput);
					
					if (ctrRow == 1)
						vaHeader.setChecksum(strChecksum);
					
					if (vaHeader.getBusinessDate().compareTo(baBankMaster.getDatProcess()) != 0)
						throwWrongFormatFileException(ctrLine, INVALID_DATE_HEADER);
					
					// Validation account no
					if (StringUtils.isNumeric(vaHeader.getGLNo() + vaHeader.getAccountNo()) == false)
						throwWrongFormatFileException(ctrLine, "GLNo and/or AccountNo contain(s) non numeric character(s)");
					
					// Validation application code (40 = GL)
					if ("40".equals(vaHeader.getApplicationCode())) {
						if (vaHeader.getGLNo().equals(vaHeader.getAccountNo()) == false)
							throwWrongFormatFileException(ctrLine, INVALID_GL_NO_AND_ACCT_NO);
					}
					else {
						if (areAllCharactersZero(vaHeader.getGLNo()) == false)
							throwWrongFormatFileException(ctrLine, INVALID_GL_NO_MUST_ZERO);
						else {
							// Validation BIN No
							Object[] binDetail = this.getBinDetailByBinNo(binDummyNo);
							if (binDetail == null)
								throw new FIXException(INVALID_BIN_NO_PARAMETER);
							else if ((binDetail[0].toString().trim()).equals(vaHeader.getAccountNo().trim()) == false)
								throw new FIXException(INVALID_HEADER_ACCT_NO);
								
							// Validation Header Account
							FcrChAcctMastPK pk = new FcrChAcctMastPK();
							pk.setCodAcctNo(vaHeader.getAccountNo());
							pk.setFlgMntStatus("A");
							FcrChAcctMast acct = this.fcrChAcctMastDAO.get(pk);
							
							if (acct == null)
								throw new FIXException(INVALID_HEADER_ACCT_NO + " (Doesn't Exist Account No.)");
							if (acct.getCodCcy().equals(
										vaHeader.getTransactionCurrency().equals("000")? 360: Integer.valueOf(vaHeader.getTransactionCurrency()))
									== false)
								throw new FIXException(INVALID_HEADER_CCY);
						}
					}
					
					vaHeader.setLengthInFile(line.length());
					
					this.tmpVADAO.insert(vaHeader);
					rowNoHeader = ctrRow;
					hasHeader = true;
				}
				// Detail
				else if (lineCode.equals(LINE_DETAIL_CODE)) {
					if (!hasHeader)
						throwWrongFormatFileException(ctrLine, "Detail line must be after header line and before footer line");
					
					ctrDetail++;
					vaDetail = this.parseToVADetail(batchNo, ctrRow, line, ctrOutput);
					
					boolean isValid = true;
					reason = null;
					
					// Validation currency code
					if ((this.crossCurrencyAllowed == false) && 
							(vaHeader.getTransactionCurrency().equals(vaDetail.getTransactionCurrency()) == false)) {
						isValid = false;
						reason = "Invalid Currency Code";
					}
					
					virtualAccountNo = vaDetail.getVirtualAccountNo();
					// Validation account no
					if (StringUtils.isNumeric(vaDetail.getAccountNo() + virtualAccountNo) == false)
						throwWrongFormatFileException(ctrLine, "AccountNo and/or VirtualAccountNo contain(s) non numeric character(s)");
						
					
					if (areAllCharactersZero(virtualAccountNo)) { // real account area
						if (areAllCharactersZero(vaDetail.getAccountNo())) {
							isValid = false;
							reason = "Real and Virtual Account No are all zeroes";
						}
						else if (this.isRealAccountValid(vaDetail.getAccountNo(), binDummy.get(templateName)) == false) {
							isValid = false;
							reason = "Invalid Real Account No";
						}
						
						if (isValid)
							vaDetail.setAccountNoToTransfer(vaDetail.getAccountNo());
					}
					else { // virtual account area
						if (!areAllCharactersZero(vaDetail.getAccountNo())) {
							isValid = false;
							reason = "Real and Virtual Account No are all non zeroes";
						}
						else {
							Object[] data = this.getInformationDetailFromVirtualAccount(virtualAccountNo);
							if ((data != null) && (data[0] != null) && (data[0].toString().trim().length() > 0)) {
								if (("N".equals(data[2])) && !(this.isNonRegisteredVAAllowed())) {
									isValid = false;
									reason = "Non Registered VA is not allowed";
								}
								else
									vaDetail.setAccountNoToTransfer(data[0].toString().trim());
							}
							else {
								isValid = false;
								reason = "Invalid Virtual Account No";
							}
						}
					}
					
					// Checking status account no
					if (vaDetail.getAccountNoToTransfer() != null) {
						Object[] accountStatus = this.fcrChAcctMastDAO.getAccountStatus(vaDetail.getAccountNoToTransfer());
						
						if (accountStatus != null) {
							if ("1|2".indexOf(String.valueOf(accountStatus[0])) > -1) {
								isValid = false;
								reason = String.valueOf(accountStatus[1]);
							}
						}
						else {
							isValid = false;
							reason = "Real Account No Does'nt Exist in FCR";
						}
					}
					
					totalDetailsAmount = totalDetailsAmount.add(vaDetail.getLocalCurrencyAmountOfTransaction());
					totalDetailsAmountFee = totalDetailsAmountFee.add(vaDetail.getAmountFee());
					
					if (isValid)
						vaDetail.setStatus(StatusDefinition.UNAUTHORIZED);
					else {
						vaDetail.setStatus(StatusDefinition.REJECT);
						vaDetail.setReason(reason);
					}
					vaDetail.setRowNoHeader(rowNoHeader);
					vaDetail.setLengthInFile(line.length());
						
					this.tmpVADAO.insert(vaDetail);
					hasDetail = true;
				}
				// Footer
				else if (lineCode.equals(LINE_FOOTER_CODE)) {
					if (!hasDetail)
						throwWrongFormatFileException(ctrLine, "Footer line must after header and detail line");
	
					vaFooter = this.parseToVAFooter(batchNo, ctrRow, line, ctrOutput);
					
					if (vaFooter.getNoOfRecord() != (ctrDetail+2))
						throwWrongFormatFileException(ctrLine, "NoOfRecord in footer line must be equal with total row's number in file");
					
					// Checking totalDetailsAmount whether is equal with amount in header
					if (totalDetailsAmount.compareTo(vaHeader.getLocalCurrencyAmountOfTransaction()) != 0)
						throw new FIXException(INVALID_HEADER_AMOUNT);
					
					if (totalDetailsAmountFee.compareTo(vaHeader.getAmountFee()) != 0)
						throw new FIXException(INVALID_HEADER_AMOUNT_FEE);
					
					vaFooter.setRowNoHeader(rowNoHeader);
					vaFooter.setLengthInFile(line.length());
					
					this.tmpVADAO.insert(vaFooter);
					
					// Reset
					ctrDetail = 0;
					totalDetailsAmount = BigDecimal.ZERO;
					hasHeader = hasDetail = false;
					
				} else
					throwWrongFormatFileException(ctrLine, "Invalid record type: " + lineCode);
				
				ctrColumn = ctrOutput[0];
				if (line.length() > ctrColumn)
					throw new StringIndexOutOfBoundsException();
			}
			catch (IndexOutOfBoundsException obEx) {
				logger.error(obEx, obEx);
				throwWrongFormatFileException(ctrLine, "Length of line is incorrect: " + line.length());
			}
			catch (NumberFormatException nfEx) {
				logger.error(nfEx, nfEx);
				throwWrongFormatFileException(ctrLine, "Invalid when formatting number/money. " + nfEx.getMessage());
			}
			catch (java.text.ParseException pEx) {
				logger.error(pEx, pEx);
				throwWrongFormatFileException(ctrLine, "Invalid Date format. " + pEx.getMessage());
			}
		}
		
		if (hasDetail)
			throwWrongFormatFileException(ctrLine, "Footer line not found");
		else if (hasHeader)
			throwWrongFormatFileException(ctrLine, "Detail and Footer line not found");
		
		br.close();
		logger.info("[END] Import GEFU VA");
	}
	
    /**
     * 
     * @param batchNo
     * @param dataMap
     */
    public void authorizeGEFU(String batchNo, Map<String, Object> dataMap) {
		logger.info("[BEGIN] Authorize GEFU VA");
		
		VAServiceDAO vaServiceDAO = new VAServiceDAO(this.session);
		List<TmpVAHeader> vaHeaderList = null;
		List<TmpVADetail> vaDetailList = null;
		Date currentDate = new Date();
		
		String inboxIdRequester = (String) dataMap.get("itemIdLink");
		Integer idSchedulerXtract = (Integer) dataMap.get("idSchedulerXtract");
		String templateName = (String) dataMap.get("templateName");
		
		try {
			// Create new session hibernate
			Session newSession = HibernateUtil.getSessionFactory().openSession();
			Transaction newTransaction = newSession.beginTransaction();
			this.createBdgwFromVA(newSession, batchNo);
			newTransaction.commit();
			newSession.close();
			
			// Get VA header, detail and footer (managed object by Hibernate persistence context)
			vaHeaderList = this.tmpVADAO.getHeadersByBatchNo(batchNo);
			vaDetailList = this.tmpVADAO.getDetailsByBatchNo(batchNo);
			
			// Update VA start process time
			for (TmpVAHeader vaHeader : vaHeaderList)
				vaHeader.setDtmProcessStart(currentDate);
			
			// Call package method
			TransactionParameter trxParam = this.trxParamDAO.getByModuleName(templateName);
			if (trxParam == null)
				throw new FIXException(FAILED_TRX_PARAM_AND_BUSS_DATE);
			else
				vaServiceDAO.runApprove(batchNo, trxParam.getAccountBranch());
			
			// Update VA header and detail status
			for (TmpVAHeader vaHeader : vaHeaderList)
				vaHeader.setStatus(StatusDefinition.AUTHORIZED);
			
			for (TmpVADetail vaDetail: vaDetailList)
				if ((vaDetail.getStatus().equals(StatusDefinition.UNAUTHORIZED)) && 
						(vaDetail.getFlagTransaction() == null)) {
					vaDetail.setStatus(StatusDefinition.PROCESS);
				}
			
			// Insert gefu response
			TmpGefuResponsPK pk = new TmpGefuResponsPK();
			pk.setOcpackage(DB_PACKAGE_NAME);
			pk.setOcfunction(DB_UPDATE_METHOD_NAME);
			pk.setBatchNo(batchNo);
			TmpGefuRespons GEFUResponse = new TmpGefuRespons();
			GEFUResponse.setCompositeId(pk);
			GEFUResponse.setInboxid(inboxIdRequester);
			GEFUResponse.setIdScheduler(idSchedulerXtract);
			GEFUResponse.setModuleDesc(MODULE_DESCRIPTION);
			this.tmpGefuResponseDAO.insert(GEFUResponse);
		}
		catch (Exception ex) {
			logger.error(ex, ex);
			
			if (vaHeaderList == null)
				vaHeaderList = this.tmpVADAO.getHeadersByBatchNo(batchNo);
			for (TmpVAHeader vaHeader: vaHeaderList)
				vaHeader.setStatus(StatusDefinition.AUTHORIZED);
			
			if (vaDetailList == null)
				vaDetailList = this.tmpVADAO.getDetailsByBatchNo(batchNo);
			for (TmpVADetail vaDetail: vaDetailList)
				if ((vaDetail.getStatus().equals(StatusDefinition.UNAUTHORIZED)) && 
						(vaDetail.getFlagTransaction() == null)) {
					vaDetail.setStatus(StatusDefinition.ERROR);
					vaDetail.setReason("Error When Approving Transaction");
				}
		}
		
		logger.info("[END] Authorize GEFU VA");
	}
	
    /**
     * 
     * @param batchNo
     */
    public void rejectGEFU(String batchNo) {
		logger.info("[BEGIN] Reject GEFU VA");
		
		List<TmpVAHeader> vaHeaderList = this.tmpVADAO.getHeadersByBatchNo(batchNo);
		for (TmpVAHeader vaHeader : vaHeaderList)
			vaHeader.setStatus(StatusDefinition.REJECT);
		
		List<TmpVADetail> vaDetails = this.tmpVADAO.getDetailsByBatchNo(batchNo);
		for (TmpVADetail vd : vaDetails) 
			if ((vd.getStatus().equals(StatusDefinition.UNAUTHORIZED)) && (vd.getFlagTransaction() == null)) {
				vd.setStatus(StatusDefinition.REJECT);
				vd.setReason(REJECTED_BY_SPV);
			}
			
		logger.info("[END] Reject GEFU VA");
	}
	
    /**
     * 
     * @param binNo
     * @return
     */
    public Object[] getBinDetailByBinNo(String binNo) {
		Object[] result = this.tmpVADAO.getBinDetailByBinNo(binNo);
		
		/*
		 * result[0] = Account No
		 * result[1] = BIN Type [F = Registered, N = Non Registered] 
		 * result[2] = BIN Title
		 */
		
		return result;
	}
	
    /**
     * 
     * @param VANo
     * @return
     */
    public Object[] getInformationDetailFromVirtualAccount(String VANo) {
		Object[] result = this.tmpVADAO.getRealAccountFromVirtualAccount(VANo);
		
		/*
		 * result[0] = Real Account
		 * result[1] = Virtual Account Name
		 * result[2] = BIN Type [F = Registered, N = Non Registered]
		 */
		
		return result;
	}
	
    /**
     * 
     * @param accountNo
     * @param binDummyNo
     * @return
     */
    public Object[] getInformationDetailFromRealAccount(String accountNo, String binDummyNo) {
		Object[] result = this.tmpVADAO.getVirtualAccountFromRealAccount(accountNo, binDummyNo);
		
		/*
		 * result[0] = Virtual No
		 * result[1] = Virtual Own Name
		 */
		
		return result;
	}
	
    /**
     * 
     * @param VANo
     * @return
     */
    public boolean isVirtualAccountValid(String VANo) {
		Object[] result = this.getInformationDetailFromVirtualAccount(VANo);
		
		return ((result != null) && (result[0] != null) && (result[0].toString().trim().length() > 0));
	}
	
    /**
     * 
     * @param realAccountNo
     * @param binDummyNo
     * @return
     */
    public boolean isRealAccountValid(String realAccountNo, String binDummyNo) {
		Object[] result = this.getInformationDetailFromRealAccount(realAccountNo, (binDummyNo!=null)? binDummyNo: "");
		
		return ((result != null) && (result[0] != null) && (result[0].toString().trim().length() > 0));
	}
	
	
	
	/* === Other functions === */
	
    /**
     * 
     * @param session
     * @param batchNo
     * @throws Exception
     */
    protected void createBdgwFromVA(Session session, String batchNo) throws Exception {
		TmpVADao vaDAO = new TmpVADao(session);
		TmpBdgwDao bdgwDAO = new TmpBdgwDao(session);
		String[] headerPropertyNames, detailPropertyNames;
		long recordId = 1;
		Date currentDate = new Date();
		boolean isAllDetailsRejected = false;
		String sDescription;
		
		headerPropertyNames = new String[] {"interfaceType", "GLNo", "customerCenter", "applicationCode", "accountNo", "transactionCurrency"};
		detailPropertyNames = new String[] {"transactionCode", "applicationCode", "transactionCurrency", "customerCenter"};
		
		List<TmpVAHeader> vaHeaderList = vaDAO.getHeadersByBatchNo(batchNo);
		List<TmpVADetail> vaDetailList;
		TmpBdgwPK pk;
		TmpBdgwHeader bdgwHeader;
		TmpBdgwDetail bdgwDetail;
		TmpBdgwFooter bdgwFooter;
		BigDecimal totalAmount, totalAmountFee, totalLCE;
		
		for (TmpVAHeader vaHeader : vaHeaderList) {
			// Scenario 1 header, multiple detail and 1 footer
			
			// Reset variable value
			totalAmount = totalAmountFee = totalLCE = BigDecimal.ZERO;
			isAllDetailsRejected = false;
			
			// Get non rejected detail
			vaDetailList = vaDAO.getDetailsByHeaderAndInverseStatus(vaHeader, StatusDefinition.REJECT);
			TmpVAFooter vaFooter = vaDAO.getFooterByHeader(vaHeader);
					
			if (vaDetailList.isEmpty()) {
				TmpVADetail fake = new TmpVADetail(null, 0);
				fake.setTransactionCurrency("000");
				fake.setTransactionAmount(BigDecimal.ZERO);
				fake.setLocalCurrencyAmountOfTransaction(BigDecimal.ZERO);
				fake.setAmountFee(BigDecimal.ZERO);
				vaDetailList = new java.util.ArrayList<TmpVADetail>();
				vaDetailList.add(fake);
				
				isAllDetailsRejected = true;
			}
			
			/* Header - begin */
			pk = new TmpBdgwPK(recordId++, vaHeader.getBatchNo());
			bdgwHeader = new TmpBdgwHeader();
			bdgwHeader.setId(pk);
			bdgwHeader.setModuleName(MODULE_NAME);
			bdgwHeader.setTarget(FCR_TABLE_HEADER);
			bdgwHeader.setDateTimeProcessStart(currentDate);
			bdgwHeader.setProfileName(rightPad(vaHeader.getProfileName(), 8));
			bdgwHeader.setBusinessDate(formatter.format(vaHeader.getBusinessDate()));
			bdgwHeader.setRecordStatus("1");
			bdgwHeader.setFeeProcessingIndicator("s");
			bdgwHeader.setDescription1(rightPad(vaHeader.getDescription1(), 30));
			bdgwHeader.setDescription2(rightPad(vaHeader.getDescription2(), 30));
			bdgwHeader.setStatus(rightPad(vaHeader.getStatus(), 1));
			bdgwHeader.setRejectCode(rightPad(vaHeader.getRejectCode(), 4));
			bdgwHeader.setDescription3(rightPad(vaHeader.getReferenceNo(), 20));
			bdgwHeader.setFeeProcessingIndicator(vaHeader.getX() == null? " ": String.valueOf(vaHeader.getX()));
			copyBeanProperties(vaHeader, bdgwHeader, headerPropertyNames);
			
			bdgwDAO.insert(bdgwHeader);
			vaHeader.setBDGWRecordId(bdgwHeader.getRecordId());
			/* Header - end */
			
			for (TmpVADetail vaDetail : vaDetailList) {
				/* Detail - begin */
				if (!isAllDetailsRejected) {
					pk = new TmpBdgwPK(recordId++, vaDetail.getBatchNo());
					bdgwDetail = new TmpBdgwDetail();
					bdgwDetail.setId(pk);
					bdgwDetail.setModuleName(MODULE_NAME);
					bdgwDetail.setTarget(FCR_TABLE_DETAIL);
					bdgwDetail.setDateTimeProcessStart(currentDate);
					bdgwDetail.setAccountNo(vaDetail.getAccountNoToTransfer());
					bdgwDetail.setTransactionAmount(convertMoneyToString(vaDetail.getTransactionAmount(), 16));
					bdgwDetail.setLocalCurrencyAmountOfTransaction(
							convertMoneyToString(vaDetail.getLocalCurrencyAmountOfTransaction(), 16));
					bdgwDetail.setForeignExchangeRate(getLeadingZeroStringFromNumber(vaDetail.getForeignExchangeRate(), 13, 7, false));
					bdgwDetail.setReference(vaDetail.getReferenceNo());
					
					// Description field
					sDescription = vaDetail.getDescription1()!=null? vaDetail.getDescription1(): "";
					sDescription += vaDetail.getDescription2()!=null? vaDetail.getDescription2(): "";
					if (areAllCharactersZero(vaDetail.getVirtualAccountNo()) == false) // in case virtual account no 
						sDescription = rightPad(vaDetail.getVirtualAccountNo() + (sDescription.equals("")? "": "-") + sDescription, 60);
					else
						sDescription = rightPad(sDescription, 60);
					
					bdgwDetail.setDescription1(sDescription.substring(0, 30));
					bdgwDetail.setDescription2(sDescription.substring(30));
					
					bdgwDetail.setAmountFee(convertMoneyToString(vaDetail.getAmountFee(), 16));
					bdgwDetail.setStatus(rightPad(vaDetail.getStatus(), 1));
					bdgwDetail.setRejectCode(rightPad(vaDetail.getRejectCode(), 4));
					bdgwDetail.setShortName(rightPad(vaDetail.getShortName(), 30));
					bdgwDetail.setLength(Long.parseLong(vaDetail.getLengthInFile().toString()));
					bdgwDetail.setRecordStatus("1");
					bdgwDetail.setParentRecordId(bdgwHeader.getRecordId());
					copyBeanProperties(vaDetail, bdgwDetail, detailPropertyNames);
					
					bdgwDetail.setLength(Long.valueOf(bdgwDetail.getDataLength()));
					
					// For FCR can process, then trim these data
					bdgwDetail.setAmountFee(bdgwDetail.getAmountFee().trim());
					
					bdgwDAO.insert(bdgwDetail);
					vaDetail.setBDGWRecordId(bdgwDetail.getRecordId());
					
					totalAmount = totalAmount.add(vaDetail.getTransactionAmount());
					totalAmountFee = totalAmountFee.add(vaDetail.getAmountFee());
					totalLCE = totalLCE.add(vaDetail.getLocalCurrencyAmountOfTransaction());
				}
				/* Detail - end */
			}
			
			/* Footer - begin */
			pk = new TmpBdgwPK(recordId++, vaFooter.getBatchNo());
			bdgwFooter = new TmpBdgwFooter();
			bdgwFooter.setId(pk);
			bdgwFooter.setModuleName(MODULE_NAME);
			bdgwFooter.setTarget(FCR_TABLE_FOOTER);
			bdgwFooter.setDateTimeProcessStart(currentDate);
			bdgwFooter.setNoRecord(getLeadingZeroStringFromNumber(isAllDetailsRejected? 2: (vaDetailList.size()+2), 10, 0, false));
			bdgwFooter.setLength(Long.parseLong(vaFooter.getLengthInFile().toString()));
			bdgwFooter.setRecordStatus("1");
			bdgwFooter.setParentRecordId(bdgwHeader.getRecordId());
			
			bdgwFooter.setLength(Long.valueOf(bdgwFooter.getDataLength()));
			bdgwDAO.insert(bdgwFooter);
			/* Footer - end */
			
			/* Customized Header - begin */
			bdgwHeader.setAmount(convertMoneyToString(totalAmount, 16));
			/* Change by vevi totalAmount ==> totalLCE */ bdgwHeader.setLocalCurrencyAmountOfTransaction(convertMoneyToString(totalLCE, 16));
			bdgwHeader.setAmountFee(convertMoneyToString(totalAmountFee, 16));
			
			bdgwHeader.setLength(Long.valueOf(bdgwHeader.getDataLength()));
			
			// For FCR can process, then trim these data
			bdgwHeader.setProfileName(bdgwHeader.getProfileName().trim());
			bdgwHeader.setAmountFee(bdgwHeader.getAmountFee().trim());
			/* Customized Header - end */
		}		
	}
	
    /**
     * 
     * @param input
     * @return
     */
    protected static boolean areAllCharactersZero(String input) {
		return (input.replace('0', ' ').trim().length() == 0);
	}
	
    /**
     * 
     * @param line
     * @param errorMessage
     * @throws Exception
     */
    protected static void throwWrongFormatFileException(int line, String errorMessage) throws Exception {
		throw new FIXException(WRONG_CONTENT + " - Line " + line + ". Error = " + errorMessage + "!!!");
	}

}


/* VAServiceDAO */
final class VAServiceDAO implements Work {
	private static Logger logger = Logger.getLogger(VAServiceDAO.class);
	
	private static final String CALL_PKG_GEFU_VA_APPROVE = "{CALL PKG_GEFU_VA.VA_APPROVE(?, ?)}";
	private Session session;
	private String batchNo;
	private String accountBranch;
	
	
	public VAServiceDAO(Session session) {
		this.session = session;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		logger.debug("call package method: " + CALL_PKG_GEFU_VA_APPROVE);
		
		CallableStatement stmt = connection.prepareCall(CALL_PKG_GEFU_VA_APPROVE);
		stmt.setString(1, this.batchNo);
		stmt.setString(2, this.accountBranch);
		
		stmt.executeUpdate();
	}
	
	public void runApprove(String batchNo, String accountBranch) {
		this.batchNo = batchNo;
		this.accountBranch = accountBranch;
		this.session.doWork(this);
	}
}