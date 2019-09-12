package bdsm.service;


import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import bdsm.model.FCRCustomerCardMaster;
import bdsm.model.FCR_CMPreembossCardInventory;
import bdsm.model.TPIN;
import bdsm.model.TPINError;
import bdsm.model.TPINStatusMaster;
import bdsm.scheduler.exception.FIXException;
import bdsmhost.dao.TPINDao;
import bdsmhost.dao.TPINStatusMasterDAO;


/**
 * 
 * @author bdsm
 */
public class TPINService {
	private static Logger logger = Logger.getLogger(TPINService.class);
	private static final DateFormat dateFormatter = new SimpleDateFormat("yyMMddHHmmss");
	private TPINDao tpinDAO;
	private TPINStatusMasterDAO tpinStatusMasterDAO;
	private FCRCustomerCardService custCardService;
	private FCR_CMPreembossCardInventoryService cardInventoryService;
	
	
    /**
     * 
     */
    public TPINService() {}
	
	
	/* == Setter Injection == */
	
    /**
     * 
     * @param tpinDAO
     */
    public void setTpinDAO(TPINDao tpinDAO) {
		this.tpinDAO = tpinDAO;
	}
	
    /**
     * 
     * @param tpinStatusMasterDAO
     */
    public void setTpinStatusMasterDAO(TPINStatusMasterDAO tpinStatusMasterDAO) {
		this.tpinStatusMasterDAO = tpinStatusMasterDAO;
	}
	
    /**
     * 
     * @param custCardService
     */
    public void setFCRCustomerCardService(FCRCustomerCardService custCardService) {
		this.custCardService = custCardService;
	}
	
    /**
     * 
     * @param cardInventoryService
     */
    public void setFCR_CMPreembossCardInventoryService(FCR_CMPreembossCardInventoryService cardInventoryService) {
		this.cardInventoryService = cardInventoryService;
	}
	
	
    /**
     * 
     * @param cardNo
     * @return
     */
    public TPIN getTPINByCardNo(String cardNo) {
		return this.tpinDAO.get(cardNo);
	}
	
    /**
     * 
     * @param code
     * @return
     */
    public TPINStatusMaster getTPINStatusByCode(String code) {
		return ((code!=null)? this.getTPINStatusByCode(code.charAt(0)): null);
	}
	
    /**
     * 
     * @param code
     * @return
     */
    public TPINStatusMaster getTPINStatusByCode(Character code) {
		return this.tpinStatusMasterDAO.get(code);
	}
	
	
    /**
     * 
     * @param buffer
     * @throws Exception
     */
    public void importTPIN(char[] buffer) throws Exception {
		this.importTPIN(buffer, null);
	}
	
    /**
     * 
     * @param buffer
     * @param filename
     * @return
     * @throws Exception
     */
    public int importTPIN(char[] buffer, String filename) throws Exception {
		TPIN tpin = null;
		String line = null;
		int ctrLine;
		int ctrDetail;
		boolean endOfRecords = false;
		boolean fatalError = false;
		long startTime, endTime;
		
		startTime = System.currentTimeMillis();
		logger.info("[BEGIN] importTPIN execution");
		if (filename != null)
			logger.debug("filename: " + filename);
		
		ctrLine = ctrDetail = 0;
		BufferedReader reader = new BufferedReader(new CharArrayReader(buffer));
		
		/* Validate the number of detail line must equal with number on EOF line */
		try {
			while ((line=reader.readLine()) != null) {
				ctrLine++;
				
				if (line.trim().length() == 0)
					continue;
				
				if (endOfRecords)
					throw new FIXException("Error, row/line is after EOF row/line");
				
				if (line.substring(0, 4).equalsIgnoreCase("EOF:")) { // end of line
					if (Integer.parseInt(line.substring(4).trim()) != ctrDetail)
						throw new FIXException("Total detail record is not match with footer");
					else
						endOfRecords = true;
				}
				else
					ctrDetail++;
			}
			
			if (!endOfRecords)
				throw new FIXException("There is no EOF line in the file content");
		}
		catch (Exception ex) {
			logger.error(ex, ex);
			this.tpinDAO.insert(new TPINError(filename, ctrLine, ex.getMessage()));
			
			// commit if using localSession
			if (tpinDAO.isInterceptorExist())
				this.tpinDAO.commitTransaction();
			
			return -1;
			//throw ex;
		}
		
		
		ctrLine = ctrDetail = 0;
		reader = new BufferedReader(new CharArrayReader(buffer));
		
		// Parsing file to be processed
		while (((line=reader.readLine()) != null) && (!fatalError)) {
			ctrLine++;
			
			if ((line.trim().length() == 0) || (line.substring(0, 4).equals("EOF:")))
				continue;
			else { // detail record
				try {
					tpin = parseLineToTPIN(line);
					logger.debug("object TPIN: " + tpin.getCardNo() + " | " + 
							((tpin.getB24UpdatedTime()!=null)? dateFormatter.format(tpin.getB24UpdatedTime()): null) + " | " + 
							tpin.getIVRStatus());
					
					FCRCustomerCardMaster custCard = this.custCardService.getByCardNo(tpin.getCardNo());
					
					if ((custCard != null) || (this.cardInventoryService.getCards(tpin.getCardNo()).size() > 0)) {
						if (custCard != null)
							this.tpinDAO.evictObjectFromPersistenceContext(custCard);
						
						this.tpinDAO.save(tpin);
					}
					else
						throw new FIXException(tpin.getCardNo() + " is invalid card no!!!");
				}
				catch (Exception ex) {
					logger.error("line TPIN ERROR: " + line);
					try {
						if (ex instanceof ParseException)
							throw new FIXException("Error when parsing date field");
						else if (ex instanceof IndexOutOfBoundsException)
							throw new FIXException("Error length of row/line");
						else if (ex instanceof FIXException)
							throw ex;
						else {
							fatalError = true;
							throw new FIXException("Error " + ex.getMessage());
						}
					}
					catch (FIXException iex) {
						this.tpinDAO.insert(new TPINError(filename, ctrLine, ex.getMessage()));
						
						if (fatalError) {
							logger.error(ex, ex);
							
							// commit if using localSession
							if (tpinDAO.isInterceptorExist())
								this.tpinDAO.commitTransaction();
							
							return -1;
							//throw ex;
						}
						else
							logger.error(ex);
					}
				}
				finally {
					ctrDetail++;
					logger.debug("ctrDetail: " + ctrDetail);
				}
				
				/* commit every 1000 records */
				if (ctrDetail % 1000 == 0) {
					logger.debug("Commit Transaction!!!");
					this.tpinDAO.commitTransaction();
					this.tpinDAO.clearPersistenceContext();
					this.tpinDAO.beginTransaction();
				}
			}
		}
		
		// commit if using localSession
		if (tpinDAO.isInterceptorExist())
			this.tpinDAO.commitTransaction();
		
		logger.info("[END] importTPIN execution");
		endTime = System.currentTimeMillis();
		logger.debug("Processing Time: " + (endTime-startTime) + " ms");
		
		return ctrDetail;
	}
	
    /**
     * 
     * @param line
     * @return
     * @throws ParseException
     * @throws FIXException
     */
    public TPIN parseLineToTPIN(String line) throws ParseException, FIXException {
		int ctrColumn = 0;
		String cardNo = line.substring(ctrColumn, ctrColumn+=16);
		String sDate = line.substring(ctrColumn, ctrColumn+=12);
		String ivrStatus = line.substring(ctrColumn, ctrColumn+=1);
		String filler = line.substring(ctrColumn, ctrColumn+=1);
		
		if (cardNo.trim().length() == 0)
			throw new FIXException("Card No Empty!!!");
		
		if ((line.length() > ctrColumn) && (line.substring(ctrColumn).trim().length() > 0))
			throw new IndexOutOfBoundsException(String.valueOf(line.length()));
		
		return (new TPIN(cardNo, (sDate.trim().length()!=0)? dateFormatter.parse(sDate): null, ivrStatus, filler));
	}
}
