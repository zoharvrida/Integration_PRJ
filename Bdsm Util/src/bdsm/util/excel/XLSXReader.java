package bdsm.util.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 
 * @author v00019372
 */
public final class XLSXReader {
	private static final Logger LOGGER = Logger.getLogger(XLSXReader.class);
	private static final String YES ="Y";
	
	private String filename;
	private String configFilename;
	
	private boolean isHeaderOnFirstRowData;
	private boolean isSequenceNumberOnFirstColumnData;
	private int rowsRead; // 0 means until last data row
	private int columnsRead; // 0 means until last data column
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private int sheetNo = 1;
	private int physicallyFirstRowDataNum = -1;
	private int physicallyFirstColumnDataNum = -1;
	private int rowCount = -1;
	private int columnCount = -1;
	private int position = -1;
	
	
	private XLSXReader(String filename, String configFilename) {
		this.filename = filename;
		this.configFilename = configFilename;
		
		if (StringUtils.isNotBlank(this.configFilename)) 
			this.loadConfig();
	}
	
	
	public static XLSXReader getInstance(String filename) {
		return new XLSXReader(filename, null);
	}
	
	public static XLSXReader getInstance(String filename, String configFilename) {
		return new XLSXReader(filename, configFilename);
	}
	
	public static XLSXReader getInstance(String filename, boolean isHeaderOnFirstRowData, boolean isSequenceNumberOnFirstColumnData,
			int rowsRead, int columnsRead) {
		
		XLSXReader object = getInstance(filename);
		object.isHeaderOnFirstRowData = isHeaderOnFirstRowData;
		object.isSequenceNumberOnFirstColumnData = isSequenceNumberOnFirstColumnData;
		object.rowsRead = rowsRead;
		object.columnsRead = columnsRead;
		
		return object;
	}
	
	
	private void loadConfig() {
		LOGGER.info("[BEGIN] loadConfig");
		
		try {
			Properties properties = new Properties();
			InputStream in = XLSXReader.class.getClassLoader().getResourceAsStream(this.configFilename);
			properties.load(in);
			LOGGER.info("Done Loading Excel Config");
			
			this.isHeaderOnFirstRowData = YES.equals(properties.getProperty("header_on_first_row_data"))? true: false;
			this.isSequenceNumberOnFirstColumnData = YES.equals(properties.getProperty("sequence_number_on_first_column_data"))? true: false;
			this.rowsRead = Integer.parseInt(properties.getProperty("rows_read", "0"));
			this.columnsRead = Integer.parseInt(properties.getProperty("columns_read", "0"));
			
			in.close();
		}
		catch(IOException ie) {
			LOGGER.error(ie, ie);
		}
		
		LOGGER.info("[END] loadConfig");
	}
	
	private void initiateRead() {
		InputStream file = null;
		try {
			file = new FileInputStream(new File(this.filename));
			this.workbook = new XSSFWorkbook(file);
			this.sheet = this.workbook.getSheetAt(this.sheetNo - 1);
			this.physicallyFirstRowDataNum = this.sheet.getFirstRowNum();
			this.position = 0;
			this.rowCount = (this.rowsRead == 0)? this.sheet.getPhysicalNumberOfRows(): this.rowsRead;
			
			if (this.isHeaderOnFirstRowData)
				this.position++;
		}
		catch (IOException ie) {
			LOGGER.error(ie, ie);
		}
		finally {
			try {
				if (file != null)
					file.close();
			}
			catch (IOException ie) {
				LOGGER.error("Error When Closing File: " + this.filename, ie);
			}
		}
	}
	
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	
	public void reset() {
		this.sheet = null;
		this.sheetNo = 1;
		this.physicallyFirstRowDataNum = -1;
		this.physicallyFirstColumnDataNum = -1;
		this.rowCount = -1;
		this.columnCount = -1;
		this.position = -1;
	}
	
	public boolean hasNextRow() {
		if (this.position == -1)
			this.initiateRead();
		
		return (this.position < this.rowCount);
	}
	
	public Object[] nextRow() {
		Object[] result = null;
		Row row = this.sheet.getRow(this.physicallyFirstRowDataNum + this.position);
		
		
		if (isEmptyRow(row)) {
			this.rowCount = this.position;
			return result;
		}
		
		if (this.physicallyFirstColumnDataNum == -1)
			this.physicallyFirstColumnDataNum = row.getFirstCellNum();
		
		if (this.columnCount == -1)
			this.columnCount = this.columnsRead;

		int logicalColStart = (this.isSequenceNumberOnFirstColumnData)? 1: 0;
		result = new Object[((this.columnCount==0)? row.getLastCellNum(): this.columnCount) - logicalColStart];
		
		for (int i=0; i<result.length; i++)
			result[i] = getValueFromCell(row.getCell(this.physicallyFirstColumnDataNum + logicalColStart + i));
		
		this.position++;
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean nextRow(Object target, String[] propertyNames) throws Exception {
		Row row = this.sheet.getRow(this.physicallyFirstRowDataNum + this.position);
		Object value;
		Class type;
		java.lang.reflect.Constructor const_;
		
		
		if (isEmptyRow(row)) {
			this.rowCount = this.position;
			return false;
		}
		
		if (this.physicallyFirstColumnDataNum == -1)
			this.physicallyFirstColumnDataNum = row.getFirstCellNum();
			
		if (this.columnCount == -1)
			this.columnCount = this.columnsRead;

		int logicalColStart = (this.isSequenceNumberOnFirstColumnData)? 1: 0;
		for (int i=0; i<(((this.columnCount==0)? row.getLastCellNum(): this.columnCount) - logicalColStart); i++) {
			if ((i+1) > propertyNames.length)
				break;
			
			if (propertyNames[i] == null)
				continue;
			
			try {
				type = PropertyUtils.getPropertyType(target, propertyNames[i]);
				value = getValueFromCell(row.getCell(this.physicallyFirstColumnDataNum + logicalColStart + i));
				
				if (type == null) throw new NoSuchMethodException();
			}
			catch (NoSuchMethodException nsme) {
				LOGGER.error("Invalid Field Name '" + propertyNames[i] + "'");
				continue;
			}
			
			if (value != null) {
				if (type.equals(String.class))
					if (value instanceof Double)
						value = XLSReader.DEC_FORMAT.format(((Double) value).doubleValue());
					else
						value = value.toString();
				else if (type.isPrimitive()) {
					type = ClassUtils.primitiveToWrapper(type);
				}
				else if ((java.util.Date.class.isAssignableFrom(type)) && (type.isAssignableFrom(value.getClass()) == false)) {
					LOGGER.error("Invalid Data Type for Field '" + propertyNames[i] + "'");
					continue;
				}
				
				if (Number.class.isAssignableFrom(type)) {
					value = value.toString();
					if (value.toString().indexOf("E") > -1)
						value = XLSReader.convertToNonExponential(value.toString());
					const_ = type.getConstructor(String.class);
					try {
						value = const_.newInstance(value);
					}
					catch (Exception ex) {
						LOGGER.error("Invalid Data Type for Field '" + propertyNames[i] + "'", ex);
						continue;
					}
				}
			}
			
			PropertyUtils.setProperty(target, propertyNames[i], value);
		}
		
		this.position++;
		return true;
	}
	
	private static boolean isEmptyRow(Row row) {
		boolean result = true;
		
		if (row != null) {
			int lastCellNum = row.getLastCellNum();
			Object value = null;
			
			for (int i=row.getFirstCellNum(); i<lastCellNum; i++) {
				value = getValueFromCell(row.getCell(i)); 
				if ((value != null) && (value.toString().trim().length() > 0)) {
					result = false;
					break;
				}
			}
		}
		
		return result;
	}
	
	private static Object getValueFromCell(Cell cell) {
		Object ret = null;
		int intVal;
		double dblVal;
		
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(cell))
					ret = cell.getDateCellValue();
				else {
					dblVal = cell.getNumericCellValue();
					intVal = (int) dblVal;
					if (dblVal == intVal) // integer
						ret = Integer.valueOf(intVal);
					else // double
						ret = Double.valueOf(dblVal);
				}
			}
			else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				ret = cell.getStringCellValue().trim();
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK);
		}
		
		return ret;
	}
}
