/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author v00019237
 */
public class ExcelUtil {

    private String filename;
    private String configFile;
    private int headerRow;
    private int rowNumCell;
    private int rowDataStartOn;
    private int columnCnt;
    private int rowCnt;
    private int cellDataStartOn;
    private int sheetData;
    private List<String[]> excelData;
    private Logger logger = Logger.getLogger(ExcelUtil.class);

    public ExcelUtil(String fname, String cfg) throws FileNotFoundException, IOException {
        filename = fname;
        configFile = cfg;
        loadConfig();
        logger.info("Class Instantiated");
    }

    public List<String[]> getData() throws FileNotFoundException, IOException {
        logger.info("Begin");
        String ext = FilenameUtils.getExtension(filename);
        if (ext.equalsIgnoreCase("xls")) {
            readExcel();
        }
        if (ext.equalsIgnoreCase("xlsx")) {
            readExcelXlsx();
        }
        return excelData;
    }

    public void writeData(List<String[]> theData, String newFilename) throws FileNotFoundException, IOException {
        String ext = FilenameUtils.getExtension(newFilename);
        if (ext.equalsIgnoreCase("xls")) {
            //writeExcel(theData, newFilename);
        }
        if (ext.equalsIgnoreCase("xlsx")) {
            //writeExcelXlsx(theData, newFilename);
        }
    }

    private void loadConfig() throws FileNotFoundException, IOException {
        logger.info("Begin Load Excel Config");
        Properties properties = new Properties();
        InputStream in = ExcelUtil.class.getClassLoader().getResourceAsStream(configFile);
        //InputStream in = new FileInputStream(new File(configFile));
        properties.load(in);
        logger.info("Done Loading Excel Config");
        headerRow = Integer.parseInt(properties.getProperty("row_header_position"));
        rowNumCell = Integer.parseInt(properties.getProperty("row_num_cell"));
        rowDataStartOn = Integer.parseInt(properties.getProperty("row_data_start_on"));
        cellDataStartOn = Integer.parseInt(properties.getProperty("cell_data_start_on"));
        columnCnt = Integer.parseInt(properties.getProperty("column_count"));
        rowCnt = Integer.parseInt(properties.getProperty("row_count"));
        sheetData = Integer.parseInt(properties.getProperty("sheet_data"));
        logger.info("Done Reading Config");
        in.close();
    }

    private void readExcel() throws FileNotFoundException, IOException {
        
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(filename)));
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            excelData = new ArrayList<String[]>(sheet.getPhysicalNumberOfRows() - headerRow);
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }
                String[] rowArray = new String[row.getPhysicalNumberOfCells() - rowNumCell];
                int rowArrayCounter = 0;
                for (int j = cellDataStartOn - rowNumCell; j < columnCnt - rowNumCell; j++) {
                    Cell cell = row.getCell(j);
                    rowArray[rowArrayCounter] = getStringCellVal(cell);
                    rowArrayCounter++;
                }
                excelData.add(rowArray);
                
            }
        } finally {
            file.close();
        }
    }

    private void readExcelXlsx() throws FileNotFoundException, IOException {
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(filename)));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(sheetData - 1);

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            excelData = new ArrayList<String[]>(sheet.getPhysicalNumberOfRows() - headerRow);
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }
                String[] rowArray = new String[row.getPhysicalNumberOfCells() - rowNumCell];
                int rowArrayCounter = 0;
                for (int j = cellDataStartOn - rowNumCell; j < columnCnt - rowNumCell; j++) {
                    Cell cell = row.getCell(j);
                    rowArray[rowArrayCounter] = getStringCellVal(cell);
                    rowArrayCounter++;
                }
                excelData.add(rowArray);
            }
        } finally {
            file.close();
        }
    }

    private void writeExcel(List<String[]> data, String newFilename) throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle style = workbook.createCellStyle();

        style.setFont(headerFont);
        FileOutputStream fos = new FileOutputStream(new File(newFilename));
        Row row;
        Cell cell;
        String[] cellData = null;
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i);
            cellData = data.get(i);
            for (int j = 0; j < cellData.length; j++) {
                cell = row.createCell(j);
                if (i == headerRow - 1) {
                    cell.setCellStyle(style);
                }
                if (cellData[j] != null) {
                    cell.setCellValue(cellData[j].toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }
        for (int j = 0; j < cellData.length; j++) {
            sheet.autoSizeColumn(j);
        }
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    private void writeExcelXlsx(List<String[]> data, String newFilename) throws FileNotFoundException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle style = workbook.createCellStyle();

        style.setFont(headerFont);
        FileOutputStream fos = new FileOutputStream(new File(newFilename));
        Row row;
        Cell cell;
        String[] cellData = null;
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i);
            cellData = data.get(i);
            for (int j = 0; j < cellData.length; j++) {
                cell = row.createCell(j);
                if (i == headerRow - 1) {
                    cell.setCellStyle(style);
                }
                if (cellData[j] != null) {
                    cell.setCellValue(cellData[j].toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }
        for (int j = 0; j < cellData.length; j++) {
            sheet.autoSizeColumn(j);
        }
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    private static String getStringCellVal(Cell cell) {
        String ret = null;
        if (cell == null) {
            ret = "";
        } else if (cell.getCellType() == 0) {
            ret = String.valueOf((long) cell.getNumericCellValue());
        } else if (cell.getCellType() == 1) {
            ret = cell.getStringCellValue();
        }
        return ret.trim();
    }
}
