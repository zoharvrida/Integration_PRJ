package bdsm.service;


import static bdsm.util.BdsmUtil.leftPad;
import static bdsm.util.BdsmUtil.rightPad;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import bdsm.model.SknNgInOutReturBulkHeader;
import bdsm.model.SknNgPK;
import bdsm.model.SknNgWSAuditTrailBulkBatch;
import bdsm.model.SknNgWSAuditTrailBulkDKE;
import bdsm.model.SknNgWSAuditTrailBulkDetail;
import bdsm.model.SknNgWSAuditTrailBulkHeader;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.util.CRC16;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.SknNgInOutReturBulkHeaderDAO;
import bdsmhost.dao.SknNgWSAuditTrailBulkBatchDAO;
import bdsmhost.dao.SknNgWSAuditTrailBulkDKEDAO;
import bdsmhost.dao.SknNgWSAuditTrailBulkDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailBulkHeaderDAO;

import com.msn.sknbi.tpk.webservice.Spkintf;


/**
 * @author v00019372
 */
public abstract class SknNgBulkService {
	public static final String ACCEPT_NAME = "ACTC";
	public static final String ACCEPT_CODE = "0000";
	public static final String REJECT_CODE = "RJCT";
	
	protected static final String DATABASE_ERROR_2000 = "2000";
	protected static final DateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator");
	protected static String FORMAT_RESPONSE_BULK_G2_FILENAME = "sknng_response_bulk.properties";
	protected static Map<String, Object[]> formatResponseBulkG2;
	protected static int posG2ResponseBulkAfterCommon; // position after key "TIPE_RECORD" and "KODE_DKE"
	
	private static Logger LOGGER = Logger.getLogger(SknNgBulkService.class);
	
	
	static {
		int[] ctrColumn = {0};
		
		try {
			loadResponseBulkG2Format();
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		
		skipWithG2ResponseBulkFormat("TIPE_RECORD", ctrColumn);
		skipWithG2ResponseBulkFormat("KODE_DKE", ctrColumn);
		posG2ResponseBulkAfterCommon = ctrColumn[0];
	}

	
	public abstract int getLenG2Header();
	public abstract int getLenG2DKE();
	public abstract int getLenG2Detail();
	public abstract int getLenG2Footer();
	
	
	
	protected static void setFormatResponseBulkG2Filename(String formatFilename) {
		FORMAT_RESPONSE_BULK_G2_FILENAME = formatFilename;
	}
	
	protected static synchronized void loadResponseBulkG2Format() throws Exception {
		if (formatResponseBulkG2 == null)
			formatResponseBulkG2 = loadFixLengthFormat(FORMAT_RESPONSE_BULK_G2_FILENAME);
	}
	
	
	protected static Map<String, Object[]> loadFixLengthFormat(String propertiesFilename) throws Exception {
		Properties properties = new Properties();
		Map<String, Object[]> result = new HashMap<String, Object[]>();
		InputStream in = SknNgService.class.getClassLoader().getResourceAsStream(propertiesFilename);
		properties.load(in);
		in.close();
		
		java.util.Enumeration<?> keys = properties.propertyNames();
		Object[] arrValue;
		Object[] arrFormat;
		String key, value;
		
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			value = properties.getProperty(key);
			
			if (value.trim().length() == 0)
				value = ":";
			arrValue = value.split(":");
			arrFormat = new Object[] {1, 'R', ' '};
			
			if ((arrValue.length > 0) && (StringUtils.isNotBlank(arrValue[0].toString()))) 
				arrFormat[0] = Integer.valueOf(arrValue[0].toString());
			if ((arrValue.length > 1) && (StringUtils.isNotBlank(arrValue[1].toString()))) 
				arrFormat[1] = arrValue[1].toString().charAt(0);
			if ((arrValue.length > 2) && (StringUtils.isNotBlank(arrValue[2].toString()))) 
				arrFormat[2] = arrValue[2].toString().charAt(0);
			
			result.put(key, arrFormat);
		}
		
		return result;
	}
	
	
	protected static String formatSKN(Map<String, Object[]> formatter, StringBuilder sb, Object input, String formatKey) {
		Object[] arr = formatter.get(formatKey); // [0]=length; [1]=alignment(L/R); [2]=padding character
		String result = formatField(convertToString(input), 
				((Character) arr[1]).charValue(), ((Integer) arr[0]).intValue(), ((Character) arr[2]).charValue());
		
		if (sb != null)
			sb.append(result);
		
		return result;
	}
	
	protected static String parseSKN(Map<String, Object[]> formatter, String input, String formatKey, int[] position) {
		Object[] arr = formatter.get(formatKey); // [0]=length; [1]=alignment(L/R); [2]=padding character
		
		return input.substring(position[0], position[0]+=(Integer)arr[0]);
	}
	
	protected static int skipSKN(Map<String, Object[]> formatter, String formatKey, int[] position) {
		Object[] arr = formatter.get(formatKey); // [0]=length; [1]=alignment(L/R); [2]=padding character
		int length = ((Integer)arr[0]).intValue();
		position[0]+=length; 
		
		return length;
	}
	
	protected static String convertToString(Object object) {
		return ((object == null)? "": object.toString().trim());
	}
	
	protected static String formatField(String input, char alignment, int length, char char_) {
		if (input.length() >= length)
			return input.substring(0, length);
		
		if ('L' == alignment)
			return rightPad(input, length, char_);
		else
			return leftPad(input, length, char_);
	}
	
	public static String formatNominal(String nominal) {
		return formatNominal(nominal, 2);
	}
	
	public static String formatNominal(String nominal, int decimalPoint) {
		StringBuilder sb = new StringBuilder(nominal);
		if (sb.indexOf(".") == -1)
			sb.insert(sb.length() - decimalPoint, ".");
		else {
			int currDecPoint = sb.length() - 1 - sb.indexOf(".");
			if (currDecPoint < decimalPoint)
				sb.append(rightPad("", decimalPoint - currDecPoint, '0'));
		}
		
		return sb.toString().replace('.', ',');
	}
	
	public static String parseNominal(String nominal) {
		return parseNominal(nominal, 2);
	}
	
	public static String parseNominal(String nominal, int decimalPoint) {
		StringBuilder sb = new StringBuilder(nominal.replace(',', '.'));
		if (sb.indexOf(".") > -1) {
			int currDecPoint = sb.length() - 1 - sb.indexOf(".");
			if (currDecPoint < decimalPoint)
				sb.append(rightPad("", decimalPoint - currDecPoint, '0'));
		}
		
		return sb.toString();
	}
	
	public static BigDecimal parseNominalToBigDecimal(String nominal) {
		return new BigDecimal(parseNominal(nominal).trim()); 
	}
	
	
	protected static String parseWithG2ResponseBulkFormat(String input, String formatKey, int[] position) {
		return parseSKN(formatResponseBulkG2, input, formatKey, position);
	}
	
	protected static int skipWithG2ResponseBulkFormat(String formatKey, int[] position) {
		Object[] arr = formatResponseBulkG2.get(formatKey); // [0]=length; [1]=alignment(L/R); [2]=padding character
		int length = ((Integer)arr[0]).intValue();
		position[0]+=length; 
		
		return length; 
	}
	
	public static String trimNewLine(String input) {
		StringBuffer sb = new StringBuffer(input);
		int lengthLS = LINE_SEPARATOR.length();
		
		/* Trim front */
		while(true) {
			if (sb.indexOf(LINE_SEPARATOR) == 0)
				sb.delete(0, lengthLS);
			else
				break;
		}
		
		/* Trim tail */
		while(true) {
			int pos = sb.length() - lengthLS;
			
			if (sb.lastIndexOf(LINE_SEPARATOR) == pos)
				sb.delete(pos, sb.length());
			else
				break;
		}
		
		return sb.toString();
	}
	
	public static String newLine() {
		return LINE_SEPARATOR;
	}
	
	
	
	/* Inner Class */
	protected abstract class SknNgBulkWSProcess extends Thread {
		private Logger logger = Logger.getLogger(SknNgBulkWSProcess.class);
		private SknNgBulkService parent;
		private String batchNo;
		private String data;
		private Integer headerRecordId;
		private org.hibernate.Session session;
		private org.hibernate.Transaction trx;
		
		
		public SknNgBulkWSProcess(SknNgBulkService parent, String name, String batchNo, Integer headerRecordId, String data) throws Exception {
			super(name);
			
			this.parent = parent;
			this.batchNo = batchNo;
			this.data = trimNewLine(data);
			this.headerRecordId = headerRecordId;
		}
		
		
		/* for Header */
		public abstract int getPositionHeaderJumlahDKEs();
		public abstract int getLengthHeaderJumlahDKEs();
		public abstract String formatHeaderJumlahDKEsWithG2Format(int jumlahRecords);
		
		public abstract int getPositionHeaderTotalNominal();
		public abstract int getLengthHeaderTotalNominal();
		public abstract String formatHeaderTotalNominalWithG2Format(String totalNominal);
		
		
		/* for DKE */
		public abstract int getPositionDKEJumlahRecords();
		public abstract int getLengthDKEJumlahRecords();
		public abstract String formatDKEJumlahRecordsWithG2Format(int jumlahRecords);
		
		public abstract int getPositionDKETotalNominal();
		public abstract int getLengthDKETotalNominal();
		public abstract String formatDKETotalNominalWithG2Format(String totalNominal);
		
		
		/* for Detail */
		public abstract int getPositionDetailNominal();
		public abstract int getLengthDetailNominal();
		
		
		/* for Footer */
		public abstract int getPositionFooterCRC();
		public abstract int getLengthFooterCRC();
		public abstract String formatCRCWithG2Format(int CRC);
		
		
		public abstract String getWebServiceOperationName();
		
		
		
		@Override
		public void run() {
			this.session = HibernateUtil.getSession();
			this.trx = this.session.beginTransaction();
			
			SknNgInOutReturBulkHeaderDAO returHeaderDAO = new SknNgInOutReturBulkHeaderDAO(this.session);
			SknNgWSAuditTrailBulkBatchDAO batchDAO = new SknNgWSAuditTrailBulkBatchDAO(this.session); 
			SknNgWSAuditTrailBulkHeaderDAO headerDAO = new SknNgWSAuditTrailBulkHeaderDAO(this.session);
			SknNgWSAuditTrailBulkDKEDAO DKEDAO = new SknNgWSAuditTrailBulkDKEDAO(this.session);
			SknNgWSAuditTrailBulkDetailDAO detailDAO = new SknNgWSAuditTrailBulkDetailDAO(this.session);
			SknNgWSAuditTrailBulkHeader header = null;
			SknNgWSAuditTrailBulkDKE DKE = null;
			SknNgWSAuditTrailBulkDetail detail = null;
			Spkintf service;
			String BIC, auth, line, lineOri = "";
			int[] ctrColumn = new int[1];
		
			
			/* Sending to SPK Web Service */
			SknNgWSAuditTrailBulkBatch batch = batchDAO.get(this.batchNo);
			header = headerDAO.get(new SknNgPK(this.batchNo, this.headerRecordId));
			SknNgInOutReturBulkHeader returHeader = returHeaderDAO.get(new SknNgPK(batch.getBatchNoOriginal(), this.headerRecordId));
			
			boolean isConventService = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("BIC").equals(returHeader.getIdentitasPesertaPengirim());
			if (isConventService) {
				service = SknNgSPKWebService.CONVENT_SERVICE;
				BIC = SknNgSPKWebService.CONVENT_BIC;
				auth = SknNgSPKWebService.CONVENT_AUTH;
			}
			else {
				service = SknNgSPKWebService.SYARIAH_SERVICE;
				BIC = SknNgSPKWebService.SYARIAH_BIC;
				auth = SknNgSPKWebService.SYARIAH_AUTH;
			}
			
			if (header.getDtmStart() == null) {
				header.setDtmStart(new java.util.Date());
				header.setDtmFinish(null);
				this.commitTransaction();
			}
			
			
			String result = null;
			try {
				java.lang.reflect.Method method = SknNgSPKWebService.class.getMethod(
						this.getWebServiceOperationName(), new Class[] {Spkintf.class, String.class, String.class, String.class}
				);
				
				result = (String) method.invoke(null, service, BIC, auth, this.data);
			}
			catch (Exception ex) {
				logger.error(ex, ex);
				return;
			}
			
			
			
			/* Parsing SPK Web Service result */
			BufferedReader brOri = new BufferedReader(new StringReader(this.data));
			BufferedReader brResult = new BufferedReader(new StringReader(result));
			StringBuffer sbHeader, sbDKE, sbDetails, sbFooter, sbBuffer;
			String headerStatus = null, headerRejectCode = null;
			String DKEStatus = null, DKERejectCode = null;
			String detailStatus = null, detailRejectCode = null;
			BigDecimal headerTotalNominal = BigDecimal.ZERO;
			BigDecimal DKETotalNominal = BigDecimal.ZERO;
			int headerDKEs = 0;
			int DKERecords = 0;
			int counter = 1;
			boolean isAnyDetailInvalid = false;
			boolean isAnyDKEValid = false;
			boolean isAnyDetailValid = false;
			boolean isCurrentDKEValid = true;
			boolean isFinish = true;
			
			try {
				loadResponseBulkG2Format();
				
				
				sbHeader = new StringBuffer();
				sbDKE = new StringBuffer();
				sbDetails = new StringBuffer();
				sbFooter = new StringBuffer();
				sbBuffer = new StringBuffer();
				
				while ((line = brResult.readLine()) != null) {
					if (isCurrentDKEValid)
						lineOri = brOri.readLine();
					
					ctrColumn[0] = posG2ResponseBulkAfterCommon;
					if (counter == 1) { // header
						if (line.trim().length() > 4) {
							skipWithG2ResponseBulkFormat("BATCH_ID", ctrColumn);
							skipWithG2ResponseBulkFormat("JAM_TANGGAL_MESSAGE", ctrColumn);
							skipWithG2ResponseBulkFormat("BATCH_ID_ORIGINAL", ctrColumn);
							skipWithG2ResponseBulkFormat("IDENTITAS_PESERTA_PENGIRIM_ORIGINAL", ctrColumn);
							skipWithG2ResponseBulkFormat("JUMLAH_RECORDS", ctrColumn);
							skipWithG2ResponseBulkFormat("TANGGAL_BATCH", ctrColumn);
							headerStatus = parseWithG2ResponseBulkFormat(line, "STATUS_BATCH", ctrColumn);
							headerRejectCode = parseWithG2ResponseBulkFormat(line, "KODE_ERROR_BATCH", ctrColumn);
							
							if (bdsm.util.BdsmUtil.isContainsIn(headerStatus, ACCEPT_NAME, REJECT_CODE) == false) {
								this.updateHeader(header, "ERR", null, true);
								break;
							}
							
							if ((REJECT_CODE.equals(headerStatus)) && (DATABASE_ERROR_2000.equals(headerRejectCode))) {
								logger.error(this.batchNo + " REJECT CODE: " + DATABASE_ERROR_2000);
								isAnyDetailInvalid = true;
								break;
							}
							
							
							sbHeader
								.delete(0, sbHeader.length())
								.append(lineOri)
								.append(newLine());
							
							headerDKEs = 0;
							headerTotalNominal = BigDecimal.ZERO;
						}
						else {
							this.updateHeader(header, null, line.trim(), true);
							break;
						}
					}
					else if (line.charAt(0) == '1') {
						String referenceNo = parseWithG2ResponseBulkFormat(line, "NOMOR_REFERENSI_DKE", ctrColumn);
						if (referenceNo.trim().length() == 0) {
							if (counter == 2) { // first DKE row
								this.updateHeader(header, headerStatus, headerRejectCode, true);
								break;
							}
							else {
								isCurrentDKEValid = false;
								do {} while ((lineOri = brOri.readLine()).charAt(0) != '1');
								continue;
							}
						}
						else {
							isCurrentDKEValid = true;
							isAnyDKEValid = true;
						}
												
						DKEStatus = parseWithG2ResponseBulkFormat(line, "STATUS_DKE", ctrColumn);
						DKERejectCode = parseWithG2ResponseBulkFormat(line, "KODE_ERROR_DKE", ctrColumn);
						DKETotalNominal = BigDecimal.ZERO;
						
						if (ACCEPT_CODE.equals(DKERejectCode) == false) {
							DKE = DKEDAO.getByBatchNoAndNomorReferensi(this.batchNo, referenceNo, header.getCompositeId().getRecordId());
							DKE.setStatus(DKEStatus);
							DKE.setRejectCode(DKERejectCode);
							DKE.setDtmUpdated(SchedulerUtil.getTime());
						}
						else {
							if (isAnyDetailValid) {
								if (counter > 2) { /* process previous DKE & it's records */
									sbDKE.replace(
											this.getPositionDKEJumlahRecords(), 
											this.getPositionDKEJumlahRecords() + this.getLengthDKEJumlahRecords(), 
											this.formatDKEJumlahRecordsWithG2Format(DKERecords));
									sbDKE.replace(
											this.getPositionDKETotalNominal(), 
											this.getPositionDKETotalNominal() + this.getLengthDKETotalNominal(), 
											this.formatDKETotalNominalWithG2Format(formatNominal(DKETotalNominal.toString())));
									
									sbBuffer
										.append(sbDKE)
										.append(sbDetails);
									
									headerDKEs++;
									headerTotalNominal = headerTotalNominal.add(DKETotalNominal); 
								}
							}
						}
						
						sbDKE
							.delete(0, sbDKE.length())
							.append(lineOri)
							.append(newLine());
						
						sbDetails
							.delete(0, sbDetails.length());
						
						DKETotalNominal = BigDecimal.ZERO;
						DKERecords = 0;
						isAnyDetailValid = false;
						isAnyDetailInvalid = false;
					}
					else if ((line.charAt(0) == '2') && (isCurrentDKEValid)) {
						String nomorUrut = parseWithG2ResponseBulkFormat(line, "NOMOR_URUT_TRANSAKSI", ctrColumn);
						detailStatus = parseWithG2ResponseBulkFormat(line, "STATUS_DETAIL", ctrColumn);
						detailRejectCode = parseWithG2ResponseBulkFormat(line, "KODE_ERROR_DETAIL", ctrColumn);
						
						if (ACCEPT_CODE.equals(detailRejectCode) == false) {
							detail = detailDAO.getByBatchNoAndNomorUrut(this.batchNo, nomorUrut, DKE.getCompositeId().getRecordId());
							detail.setStatus(detailStatus);
							detail.setRejectCode(detailRejectCode);
							detail.setDtmUpdated(SchedulerUtil.getTime());
							
							isAnyDetailInvalid = true;
						}
						else {
							DKERecords++;
							DKETotalNominal = DKETotalNominal.add(new BigDecimal(parseNominal(
									lineOri.substring(this.getPositionDetailNominal(), this.getPositionDetailNominal() + this.getLengthDetailNominal()).trim()))
								);
							
							sbDetails
								.append(lineOri)
								.append(newLine());
							
							isAnyDetailValid = true;
						}
					}
					else if (line.charAt(0) == '3') {
						sbFooter = sbFooter.append(lineOri);
						
						// Save last DKE read
						if (isAnyDetailValid) {
							sbDKE.replace(
									this.getPositionDKEJumlahRecords(), 
									this.getPositionDKEJumlahRecords() + this.getLengthDKEJumlahRecords(), 
									this.formatDKEJumlahRecordsWithG2Format(DKERecords));
							sbDKE.replace(
									this.getPositionDKETotalNominal(), 
									this.getPositionDKETotalNominal() + this.getLengthDKETotalNominal(), 
									this.formatDKETotalNominalWithG2Format(formatNominal(DKETotalNominal.toString())));
							
							sbBuffer
								.append(sbDKE)
								.append(sbDetails);
							
							headerDKEs++;
							headerTotalNominal = headerTotalNominal.add(DKETotalNominal);
						}
						
						// Inserting Header
						if ((isAnyDKEValid) & (headerDKEs > 0)) {
							sbHeader.replace(
									this.getPositionHeaderJumlahDKEs(), 
									this.getPositionHeaderJumlahDKEs() + this.getLengthHeaderJumlahDKEs(), 
									this.formatHeaderJumlahDKEsWithG2Format(headerDKEs));
							sbHeader.replace(
									this.getPositionHeaderTotalNominal(), 
									this.getPositionHeaderTotalNominal() + this.getLengthHeaderTotalNominal(), 
									this.formatHeaderTotalNominalWithG2Format(formatNominal(
										headerTotalNominal.toString().indexOf(".") == -1? 
												headerTotalNominal.toString() + ".00": 
												headerTotalNominal.toString()
									)));
						}
						
						// Processing Footer
						int temp = CRC16.CRC16(sbBuffer.toString(), 0);
						sbFooter.replace(
							this.getPositionFooterCRC(), 
							this.getPositionFooterCRC() + this.getLengthFooterCRC(), 
							this.formatCRCWithG2Format(temp)
						);
						
						
						// Merging all elements
						sbBuffer.insert(0, sbHeader);
						sbBuffer.append(sbFooter);
						this.data = sbBuffer.toString();
						
						break;
					}
					
					counter++;
				}
				
				
				/* Commit */
				this.commitTransaction();
				
				
				if ((header.getFlagError() == null) || (StatusDefinition.NO.equals(header.getFlagError()))) {
					if (isAnyDKEValid & (headerDKEs > 0) & isAnyDetailInvalid) { // if at least one detail record / one DKE, other than header and footer
						isFinish = false;
						this.doSendAgain();
					}
					else {
						DKEDAO.updateStatusAndRejectCode(this.batchNo, ACCEPT_NAME, ACCEPT_CODE, SchedulerUtil.getTime());
						detailDAO.updateStatusAndRejectCode(this.batchNo, ACCEPT_NAME, ACCEPT_CODE, SchedulerUtil.getTime());
					}
				}
				
				if (isFinish) {
					this.updateHeader(header, headerStatus, headerRejectCode, false);
					header.setDtmFinish(new java.util.Date());
				}
			}
			catch (Exception ex) {
				logger.error(ex, ex);
			}
			finally {
				try {
					brResult.close();
				}
				catch (java.io.IOException iex) {
					logger.error(iex, iex);
				}
			}
			
			/* Commit */
			this.commitTransaction();
		}
		
		
		private void updateHeader(SknNgWSAuditTrailBulkHeader header, String status, String rejectCode, boolean isFlagError) {
			header.setStatus(status);
			header.setRejectCode(rejectCode);
			if (isFlagError)
				header.setFlagError(StatusDefinition.YES);
		}
		
		private void doSendAgain() {
			try {
				String threadName = this.getName();
				int threadNumber = 1; 
				
				if (threadName.indexOf("*") > -1) {
					threadNumber = Integer.parseInt(threadName.substring(threadName.indexOf("*") + 1)) + 1; // get after character *
					threadName = threadName.substring(0, threadName.indexOf("*"));
				}
				
				/* Request to SPK Web Service in a new thread */
				java.lang.reflect.Constructor<? extends SknNgBulkWSProcess> c = 
						this.getClass().getConstructor(new Class[] {this.parent.getClass(), SknNgBulkService.class, String.class, String.class, Integer.class, String.class});
				
				c.newInstance(
					this.parent,
					this.parent,
					threadName + "*" + threadNumber,
					this.batchNo,
					this.headerRecordId,
					this.data.toString()
				).start();
			}
			catch (Exception ex) {
				this.logger.error(ex, ex);
			}
		}
		
		private void commitTransaction() {
			this.trx.commit();
			this.trx = this.session.beginTransaction();
		}
		
	}
	
	
	protected abstract class SknNgInwardWSProcess {
		private Logger logger = Logger.getLogger(SknNgInwardWSProcess.class);
		private String BIC;
		
		
		public SknNgInwardWSProcess(String BIC) {
			this.BIC = BIC;
		}
		
		
		public abstract String getWebServiceOperationName();
		
		public String requestInwardWSToSPK() {
			Spkintf service = null;
			String auth = null;
			boolean isConventService = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("BIC").equals(this.BIC);
			
			if (isConventService) {
				service = SknNgSPKWebService.CONVENT_SERVICE;
				auth = SknNgSPKWebService.CONVENT_AUTH;
			}
			else {
				service = SknNgSPKWebService.SYARIAH_SERVICE;
				auth = SknNgSPKWebService.SYARIAH_AUTH;
			}
			
			String result = null;
			try {
				java.lang.reflect.Method method = SknNgSPKWebService.class.getMethod(
						this.getWebServiceOperationName(), new Class[] {Spkintf.class, String.class, String.class, String.class}
				);
				
				result = (String) method.invoke(null, service, this.BIC, auth, this.BIC);
			}
			catch (Exception ex) {
				logger.error(ex, ex);
			}
			
			return result;
		}
	}

}
