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
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.msn.sknbi.tpk.webservice.Spkintf;

import bdsm.model.SknNgPK;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.util.CRC16;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.SknNgWSAuditTrailDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailHeaderDAO;


/**
 * @author v00019372
 */
public abstract class SknNgService {
	public static final String ACCEPT_NAME = "ACTC";
	public static final String ACCEPT_CODE = "0000";
	public static final String REJECT_CODE = "RJCT";
	
	protected static final String DATABASE_ERROR_2000 = "2000";
	protected static final DateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator");
	protected static String FORMAT_RESPONSE_G2_FILENAME = "sknng_response.properties";
	protected static Map<String, Object[]> formatResponseG2;
	protected static int posG2ResponseAfterCommon; // position after key "TIPE_RECORD" and "KODE_DKE"
	
	private static Logger LOGGER = Logger.getLogger(SknNgService.class);
	
	
	static {
		int[] ctrColumn = {0};
		
		try {
			loadResponseG2Format();
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		
		skipWithG2ResponseFormat("TIPE_RECORD", ctrColumn);
		skipWithG2ResponseFormat("KODE_DKE", ctrColumn);
		posG2ResponseAfterCommon = ctrColumn[0];
	}

	
	public abstract int getLenG2Header();
	public abstract int getLenG2Detail();
	public abstract int getLenG2Footer();
	
	
	
	protected static void setFormatResponseG2Filename(String formatFilename) {
		FORMAT_RESPONSE_G2_FILENAME = formatFilename;
	}
	
	protected static synchronized void loadResponseG2Format() throws Exception {
		if (formatResponseG2 == null)
			formatResponseG2 = loadFixLengthFormat(FORMAT_RESPONSE_G2_FILENAME);
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
	
	
	protected static String parseWithG2ResponseFormat(String input, String formatKey, int[] position) {
		return parseSKN(formatResponseG2, input, formatKey, position);
	}
	
	protected static int skipWithG2ResponseFormat(String formatKey, int[] position) {
		Object[] arr = formatResponseG2.get(formatKey); // [0]=length; [1]=alignment(L/R); [2]=padding character
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
	protected abstract class SknNgWSProcess extends Thread {
		private Logger logger = Logger.getLogger(SknNgWSProcess.class);
		private SknNgService parent;
		private String batchNo;
		private String data;
		private List<Integer> listRowNo;
		private org.hibernate.Session session;
		private org.hibernate.Transaction trx;
		
		
		public SknNgWSProcess(SknNgService parent, String name, String batchNo, String data, List<Integer> listRowNo) throws Exception {
			super(name);
			
			this.parent = parent;
			this.batchNo = batchNo;
			this.data = trimNewLine(data);
			this.listRowNo = listRowNo;
		}
		
		
		public abstract int getPositionHeaderJumlahRecords();
		public abstract int getLengthHeaderJumlahRecords();
		public abstract String formatHeaderJumlahRecordsWithG2Format(int jumlahRecords);
		
		public abstract int getPositionHeaderTotalNominal();
		public abstract int getLengthHeaderTotalNominal();
		public abstract String formatHeaderTotalNominalWithG2Format(String totalNominal);
		
		public abstract int getPositionDetailNominal();
		public abstract int getLengthDetailNominal();
		
		public abstract int getPositionFooterCRC();
		public abstract int getLengthFooterCRC();
		public abstract String formatCRCWithG2Format(int CRC);
		
		public abstract String getWebServiceOperationName();
		
		
		@Override
		public void run() {
			this.session = HibernateUtil.getSession();
			this.trx = this.session.beginTransaction();
			
			SknNgWSAuditTrailHeaderDAO headerDAO = new SknNgWSAuditTrailHeaderDAO(this.session);
			SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.session);
			SknNgWSAuditTrailHeader header = null;
			SknNgWSAuditTrailDetail detail = null;
			Spkintf service;
			String line, BIC, auth;
			int[] ctrColumn = new int[1];
		
			
			/* Sending to SPK Web Service */
			header = headerDAO.get(new SknNgPK(this.batchNo, this.listRowNo.get(0)));
			boolean isConventService = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("BIC").equals(header.getIdentitasPesertaPengirim());
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
			StringReader sr = new StringReader(result);
			BufferedReader br2 = new BufferedReader(sr);
			String headerStatus = null, headerRejectCode = null;
			String detailStatus = null, detailRejectCode = null;
			int counter = 0;
			boolean isAnyDetailInvalid = false;
			boolean isFinish = true;
			
			try {
				loadResponseG2Format();
			
				while ((line = br2.readLine()) != null) {
					ctrColumn[0] = posG2ResponseAfterCommon;
					
					if (counter == 0) { // header
						if (line.trim().length() > 4) {
							skipWithG2ResponseFormat("BATCH_ID", ctrColumn);
							skipWithG2ResponseFormat("JAM_TANGGAL_MESSAGE", ctrColumn);
							skipWithG2ResponseFormat("BATCH_ID_ORI", ctrColumn);
							skipWithG2ResponseFormat("IDENTITAS_PESERTA_PENGIRIM_ORI", ctrColumn);
							skipWithG2ResponseFormat("JUMLAH_RECORDS", ctrColumn);
							skipWithG2ResponseFormat("TANGGAL_BATCH", ctrColumn);
							headerStatus = parseWithG2ResponseFormat(line, "STATUS_BATCH", ctrColumn);
							headerRejectCode = parseWithG2ResponseFormat(line, "KODE_ERROR_BATCH", ctrColumn);
							
							if (bdsm.util.BdsmUtil.isContainsIn(headerStatus, ACCEPT_NAME, REJECT_CODE) == false) {
								this.updateHeader(header, "ERR", null, true);
								break;
							}
							
							if ((REJECT_CODE.equals(headerStatus)) && (DATABASE_ERROR_2000.equals(headerRejectCode))) {
								logger.error(this.batchNo + " REJECT CODE: " + DATABASE_ERROR_2000);
								isAnyDetailInvalid = true;
								break;
							}
						}
						else {
							this.updateHeader(header, null, line.trim(), true);
							break;
						}
					}
					else if (line.charAt(0) == '1') {
						String referenceNo = parseWithG2ResponseFormat(line, "NOMOR_REFERENSI", ctrColumn);
						if (counter == 1) // first detail row
							if (referenceNo.trim().length() == 0) { // Header Rejected
								this.updateHeader(header, headerStatus, headerRejectCode, true);
								break;
							}
						
						detailStatus = parseWithG2ResponseFormat(line, "STATUS_DKE", ctrColumn);
						detailRejectCode = parseWithG2ResponseFormat(line, "KODE_ERROR_DKE", ctrColumn);
						
						if (ACCEPT_CODE.equals(detailRejectCode) == false) {
							detail = detailDAO.get(new SknNgPK(this.batchNo, this.listRowNo.get(counter)));
							detail.setStatus(detailStatus);
							detail.setRejectCode(detailRejectCode);
							detail.setDtmUpdated(SchedulerUtil.getTime());
							
							this.listRowNo.set(counter, -1); // Mark, that record is invalid
							if (!isAnyDetailInvalid)
								isAnyDetailInvalid = true;
						}
					}
					
					counter++;
				}
				
				if ((header.getFlagError() == null) || (StatusDefinition.NO.equals(header.getFlagError()))) {
					if (isAnyDetailInvalid) {
						for (int i=0; i<this.listRowNo.size(); i++)
							if (this.listRowNo.get(i) == -1) {
								this.listRowNo.remove(i);
								this.deleteLineDetail(i--);
							}
						
						if (this.listRowNo.size() > 2) { // if at least one detail record, other than header and footer
							isFinish = false;
							this.doSendAgain();
						}
						else {
							this.updateHeader(header, headerStatus, headerRejectCode, false);
							//this.updateHeader(header, ACCEPT_NAME, ACCEPT_CODE, false);
						}
					}
					else {
						List<Integer> listRowNoDetails = this.listRowNo.subList(1, this.listRowNo.size() - 1);
						detailDAO.updateStatusAndRejectCode(this.batchNo, ACCEPT_NAME, ACCEPT_CODE, listRowNoDetails, SchedulerUtil.getTime());
						this.updateHeader(header, headerStatus, headerRejectCode, false);
					}
				}
				
				if (isFinish)
					header.setDtmFinish(new java.util.Date());
			}
			catch (Exception ex) {
				logger.error(ex, ex);
			}
			finally {
				try {
					br2.close();
				}
				catch (java.io.IOException iex) {
					logger.error(iex, iex);
				}
			}
			
			/* Commit */
			this.commitTransaction();
		}
		
		private void deleteLineDetail(int row) {
			StringBuilder sb = new StringBuilder(this.data);
			int l1Row = getLenG2Detail() + newLine().length();
			int position = getLenG2Header();
			
			for (int i=1; i<row; i++)
				position += l1Row;
			sb.delete(position, position + l1Row);
			
			this.data = sb.toString();
		}
		
		private void updateHeader(SknNgWSAuditTrailHeader header, String status, String rejectCode, boolean isFlagError) {
			header.setStatus(status);
			header.setRejectCode(rejectCode);
			if (isFlagError)
				header.setFlagError(StatusDefinition.YES);
		}
		
		
		private void doSendAgain() {
			BufferedReader br = new BufferedReader(new StringReader(this.data));
			String line = null;
			StringBuilder sb = new StringBuilder(), lineHeader = null, lineFooter = null;
			int posNominal, lengthNominal;
			BigDecimal totalNominal = BigDecimal.ZERO;
			
			posNominal = this.getPositionDetailNominal();
			lengthNominal = this.getLengthDetailNominal();
			
			try {
				while ((line = br.readLine()) != null) {
					if (line.charAt(0) == '0') // header
						lineHeader = new StringBuilder(line);
					else if (line.charAt(0) == '1') {
						sb.append(line).append(LINE_SEPARATOR);
						totalNominal = totalNominal.add(new BigDecimal(parseNominal(line.substring(posNominal, posNominal+lengthNominal).trim())));
					}
					else
						lineFooter = new StringBuilder(line);
				}
				
				/* Update Footer */
				int temp = CRC16.CRC16(sb.toString(), 0);
				lineFooter.replace(
						this.getPositionFooterCRC(), 
						this.getPositionFooterCRC() + this.getLengthFooterCRC(), 
						this.formatCRCWithG2Format(temp)
				);
				sb.append(lineFooter);
				
				/* Update Header */
				lineHeader.replace(
						this.getPositionHeaderJumlahRecords(),  
						this.getPositionHeaderJumlahRecords() + this.getLengthHeaderJumlahRecords(), 
						this.formatHeaderJumlahRecordsWithG2Format(this.listRowNo.size()-2) 
				);
				
				// Change Jumlah Nominal
				lineHeader.replace(
						this.getPositionHeaderTotalNominal(), 
						this.getPositionHeaderTotalNominal() + this.getLengthHeaderTotalNominal(), 
						this.formatHeaderTotalNominalWithG2Format(formatNominal(totalNominal.toString())) 
				);
				
				sb.insert(0, lineHeader.append(LINE_SEPARATOR));
				
				
				/* Request to SPK Web Service in a new thread */
				java.lang.reflect.Constructor<? extends SknNgWSProcess> c = 
						this.getClass().getConstructor(new Class[] {this.parent.getClass(), SknNgService.class, String.class, String.class, String.class, List.class});
				
				c.newInstance(
					this.parent,
					this.parent,
					this.getName() + "*",
					this.batchNo,
					sb.toString(), 
					new java.util.ArrayList<Integer>(this.listRowNo)
				).start();
			}
			catch (Exception ex) {
				logger.error(ex, ex);
			}
			finally {
				try {
					br.close();
				}
				catch (java.io.IOException iex) {
					logger.error(iex, iex);
				}
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
		private Object[] additionalParameter;
		
		
		public SknNgInwardWSProcess(String BIC, Object... additionalParameter) {
			this.BIC = BIC;
			this.additionalParameter = additionalParameter;
		}
		
		public SknNgInwardWSProcess(String BIC) {
			this(BIC, (Object[]) null);
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
			
			Object[] parameters = new Object[3 + this.additionalParameter.length];
			Class<?>[] parameterClasses = new Class<?>[parameters.length];
			parameters[0] = service;
			parameters[1] = this.BIC;
			parameters[2] = auth;
			for (int i = 0; i < parameters.length; i++) {
				if (i > 2) // parameter[0] -- [2] already defined
					parameters[i] = this.additionalParameter[i];
				parameterClasses[i] = parameters[i].getClass();
			}
			String result = null;
			try {
				java.lang.reflect.Method method = SknNgSPKWebService.class.getMethod(
						this.getWebServiceOperationName(), parameterClasses
				);
				
				result = (String) method.invoke(null, parameters);
			}
			catch (Exception ex) {
				logger.error(ex, ex);
			}
			
			return result;
		}
	}

}
