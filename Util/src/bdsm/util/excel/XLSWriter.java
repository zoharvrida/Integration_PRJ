package bdsm.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;


/**
 * 
 * @author v00019372
 */
public final class XLSWriter {
	private static final Logger LOGGER = Logger.getLogger(XLSWriter.class);
	private static final String YES ="Y";
	private String filename;
	private String configFilename;
	
	private boolean isHeaderOnFirstRowData;
	private boolean isSequenceNumberOnFirstColumnData;
	private int physicallyFirstRowDataPosition = 1;
	private int physicallyFirstColumnDataPosition = 1;
	
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private String sheetName;
	private String[] headerNames;
	private int position = -1;
	
	
	private XLSWriter(String filename, String configFilename) {
		this.filename = filename;
		this.configFilename = configFilename;
		
		if (StringUtils.isNotBlank(this.configFilename)) 
			this.loadConfig();
	}
	
	
	public static XLSWriter getInstance(String filename) {
		return new XLSWriter(filename, null);
	}
	
	public static XLSWriter getInstance(String filename, String configFilename) {
		return new XLSWriter(filename, configFilename);
	}
	
	public static XLSWriter getInstance(String filename, boolean isHeaderOnFirstRowData, boolean isSequenceNumberOnFirstColumnData,
			int firstRowDataPosition, int firstColumnDataPosition) {
		
		XLSWriter object = getInstance(filename);
		object.isHeaderOnFirstRowData = isHeaderOnFirstRowData;
		object.isSequenceNumberOnFirstColumnData = isSequenceNumberOnFirstColumnData;
		object.physicallyFirstRowDataPosition = firstRowDataPosition;
		object.physicallyFirstColumnDataPosition = firstColumnDataPosition;
		
		return object;
	}
	
	
	private void loadConfig() {
		LOGGER.info("[BEGIN] loadConfig");
		
		try {
			Properties properties = new Properties();
			InputStream in = XLSReader.class.getClassLoader().getResourceAsStream(this.configFilename);
			properties.load(in);
			LOGGER.info("Done Loading Excel Config");
			
			this.isHeaderOnFirstRowData = YES.equals(properties.getProperty("header_on_first_row_data"))? true: false;
			this.isSequenceNumberOnFirstColumnData = YES.equals(properties.getProperty("sequence_number_on_first_column_data"))? true: false;
			this.physicallyFirstRowDataPosition = Integer.parseInt(properties.getProperty("first_row_position", "1"));
			this.physicallyFirstColumnDataPosition = Integer.parseInt(properties.getProperty("first_column_position", "1"));
			
			in.close();
		}
		catch(IOException ie) {
			LOGGER.error(ie, ie);
		}
		
		LOGGER.info("[END] loadConfig");
	}
	
	private void initiateWrite() throws IOException {
		File file = new File(this.filename);
		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				this.workbook = new HSSFWorkbook(fis);
			}
			finally {
				try {
					fis.close();
				}
				catch (IOException iex2) {
					LOGGER.error("Error When Closing File: " + this.filename, iex2);
				}
			}
		}
		else 
			this.workbook = new HSSFWorkbook();
		
		this.sheet = (this.sheetName != null)? this.workbook.createSheet(this.sheetName): this.workbook.createSheet();
		this.position = 0;
		
		if (this.isHeaderOnFirstRowData) {
			if (this.headerNames != null)
				this.putRow(this.headerNames);
			else {
				LOGGER.error("Header Names Must be Specified!!!");
				this.reset();
			}
		}
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public void setHeaderNames(String[] headerNames) {
		this.headerNames = headerNames;
	}
	
	public void reset() {
		this.workbook = null;
		this.sheet = null;
		this.sheetName = null;
		this.position = -1;
	}
	
	
	public void putRow(java.util.List<Object> dataList) {
		this.putRow(dataList.toArray());
	}
	
	public void putRow(Object target, String[] propertyNames) {
		Object[] ret = new Object[propertyNames.length];
		
		for (int i=0; i<propertyNames.length; i++)
			try {
				ret[i] = PropertyUtils.getProperty(target, propertyNames[i]);
			}
			catch (Exception e) {
				LOGGER.error(e);
				ret[i] = null;
			}
		
		this.putRow(ret);
	}
	
	public void putRow(Object[] dataArray) {
		try {
			if (this.position == -1)
				this.initiateWrite();
		}
		catch(Exception ex) {
			LOGGER.error(ex, ex);
			return;
		}
		
		Row row = this.sheet.createRow(this.physicallyFirstRowDataPosition - 1 + this.position);
		Cell cell;
		
		CellStyle csHeader, csRowNumber;
		Font boldFont;
		int isSeqNo = 0;
		boolean isHeaderNow;
		
		csHeader = csRowNumber = null;
		if (isHeaderNow = ((this.isHeaderOnFirstRowData) && (this.position == 0))) {
			csHeader = this.workbook.createCellStyle();
			csHeader.setAlignment(CellStyle.ALIGN_CENTER);
			boldFont = this.workbook.createFont();
			boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			csHeader.setFont(boldFont);
		}
		
		
		if (this.isSequenceNumberOnFirstColumnData) {
			csRowNumber = this.workbook.createCellStyle();
			csRowNumber.setAlignment(CellStyle.ALIGN_RIGHT);
			
			cell = row.createCell(this.physicallyFirstColumnDataPosition - 1);
			if (isHeaderNow) {
				cell.setCellStyle(csHeader);
				cell.setCellValue("No.");
			}
			else {
				cell.setCellStyle(csRowNumber);
				
				if (this.isHeaderOnFirstRowData)
					cell.setCellValue(this.position + ".");
				else
					cell.setCellValue((this.position + 1) + ".");
			}
				
			
			isSeqNo = 1;
		}
		
		for (int i=0; i<dataArray.length; i++) {
			cell = row.createCell(this.physicallyFirstColumnDataPosition - 1 + isSeqNo + i);
			if (isHeaderNow)
				cell.setCellStyle(csHeader);
			
			putValueToCell(cell, dataArray[i]);
		}
		
		this.position++;
	}
	
	public void autoSizeColumn(int numberOfDataColumns) {
		// auto resize(expanded) column width
		if (isSequenceNumberOnFirstColumnData)
			numberOfDataColumns = numberOfDataColumns + 1;
		
		for (int i=0; i<numberOfDataColumns ; i++)
			this.sheet.autoSizeColumn(this.physicallyFirstColumnDataPosition - 1 + i);
	}
	
	public boolean writeToFile() {
		try {
			FileOutputStream fos = new FileOutputStream(this.filename);
			this.workbook.write(fos);
			fos.close();
			
			return true;
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
			return false;
		}
	}
	
	private static void putValueToCell(Cell cell, Object data) {
		if (data == null) data = "";
		LOGGER.debug("Data Instance: " + data.getClass().getName());
		if (data instanceof String)
			cell.setCellValue((String) data);
		else if (data instanceof Number)
			cell.setCellValue(((Number) data).doubleValue());
		else if (data instanceof Date)
			cell.setCellValue((Date) data);
		else if (data instanceof Character)
			cell.setCellValue(data.toString());
		else 
			LOGGER.error("Error put value: " + data);
	}
	
}
