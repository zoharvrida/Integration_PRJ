package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author bdsm
 */
public class UpdateCIFWorker extends BaseProcessor {
    private int maxDataUPDCIF = PropertyPersister.maxDataUPDCIF;
    private boolean isMax = false;
    
    private String emailRejectMaxData = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested to Update CIF can't be process, maximal upload " + maxDataUPDCIF + " data.<br/>"            
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    
    private String emailApproval = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your approval is required for the attached file to be processed further. <br/>"
            + "<br/>"
            + "Please reply this email with:<br/>"
            + "<b>Ok</b>, if you approve the file to be processed, or<br/>"
            + "<b>Not ok</b>, if otherwise.<br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    
    private String emailDone = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Process Update CIF has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
 
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process Update CIF Update has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public UpdateCIFWorker(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        String configFile;
        configFile = "excelutil_update_cif.properties";

        UpdateCIFTmpDao updateCIFTmpDao = new UpdateCIFTmpDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

        UpdateCIFTmp updateCIFTmp = null;
        FixClassConfig fClassConfig = null;
        FixSchedulerXtract fixSchedulerXtract = null;

        String extFile = "";
        String BatchID = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String param1 = context.get(MapKey.param1).toString();
        String sourceProcess = context.get(MapKey.processSource).toString();
        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());

        getLogger().info("Done Prepare before execute status U/A");
        
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            getLogger().info("Status : UNAUTHORIZED");
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("UPDATECIF ", "")) + "{hhmmss}.xls");
            getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            getLogger().info("Param 5 : " + param5);

            readExcel(param5, configFile, updateCIFTmp, updateCIFTmpDao);
            updateCIFTmpDao.get(BatchID);
            getLogger().info("Remove Bug");
            updateCIFTmpDao.runValidate(BatchID);
            getLogger().info("Run validate Update CIF");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.UNAUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            outFileName = outFileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extension Done : " + outFileName);

            if (!sourceProcess.equalsIgnoreCase("sftp")) {
                if (!isMax) {
                FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                fixQXtract = new FixQXtract();
                fixQXtract.setIdScheduler(idScheduler);
                fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                fixQXtract.setParam1("RE: " + param1);               
                fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
                fixQXtract.setParam4(this.emailApproval);
                fixQXtract.setParam5(outFileName);
                fixQXtract.setParam6(BatchID);
                } else {
                    fixQXtract = new FixQXtract();
                    fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
                    idScheduler = fClassConfig.getIdScheduler();
                    fixQXtract.setIdScheduler(idScheduler);
                    fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                    fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                    FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                    fixQXtract.setParam1("RE: " + param1);                    
                    fixQXtract.setParam2(fixEmailAccessDao.getReq(context.get(MapKey.grpId).toString()));
                    fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
                    fixQXtract.setParam4(emailRejectMaxData);
                    fixQXtract.setParam5("");
                }
                
                getLogger().info("UpdateCIFWorker Register FixQXtract Done");
            }

            return true;
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            getLogger().info("Status : AUTHORIZED");
            updateCIFTmpDao.runUpdate(BatchID);
            getLogger().info("Run update to Update CIF User Done");
            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("RE: UPDATECIF ", "")) + "{hhmmss}.xls");
            getLogger().info("UpdateCIFWorker Register FixQXtract");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.AUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            out2FileName = out2FileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extention DONE : " + out2FileName);
            
            FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(idScheduler);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);            
            fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
            FixInboxDao fixInboxDao = new FixInboxDao(session);

            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }

            if (context.get(MapKey.spvAuth).toString().equals("N")) {
                fixQXtract.setParam2(context.get(MapKey.param2).toString());
            }

            fixQXtract.setParam4(this.emailDone);
            fixQXtract.setParam5(out2FileName);
            fixQXtract.setParam6(BatchID);
            getLogger().info("UpdateCIFWorker Register FixQXtract Done");
            
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            getLogger().info("Status : REJECTED");
            getLogger().info("UpdateCIFWorker Register FixQXtract");
            updateCIFTmpDao.runReject(BatchID);
            
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            
            fixQXtract = new FixQXtract();            
            fixQXtract.setIdScheduler(idScheduler);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);

            FixInboxDao fixInboxDao = new FixInboxDao(session);
            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }
            
            fixQXtract.setParam4(this.emailRejected);
            fixQXtract.setParam5("");
            getLogger().info("UpdateCIFWorker Register FixQXtract");
            
            return true;
        } else {
            getLogger().info("Status : IGNORED");
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
            
            return true;
        }
    }

    private void readExcel(String param5, String configFile, UpdateCIFTmp updateCIFTmp, UpdateCIFTmpDao updateCIFTmpDao) throws FIXException, FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        loadConfig(configFile);
        
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, updateCIFTmp, updateCIFTmpDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            getLogger().info("xlsx not support for UPDCIF.");
        }
    }

    private void readXLS(String param5, UpdateCIFTmp updateCIFTmp, UpdateCIFTmpDao updateCIFTmpDao) throws FIXException, FileNotFoundException, IOException {
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        getLogger().info("Start Reading Update CIF File.");
        getLogger().info("Column Count : " + columnCnt);
        getLogger().info("Row Count : " + rowCnt);
        getLogger().info("Cell Data Start On : " + cellDataStartOn);
        getLogger().info("Row Data Start On : " + rowDataStartOn);
        getLogger().info("Header Row : " + headerRow);
        getLogger().info("Row Number Cell : " + rowNumCell);
        getLogger().info("Max Data : " + maxDataUPDCIF);
        
        try {            
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);           

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
                getLogger().info("[Change] Row Count : " + rowCnt);
            }
                                                
            if (rowCnt <= maxDataUPDCIF) {
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);

                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                    getLogger().info("[Change] Column Count : " + columnCnt);
                }

                int j = cellDataStartOn - rowNumCell;
                String codCustID = getStringCellVal(row.getCell(j + 1));                                                
                String bdiNIK = getStringCellVal(row.getCell(j + 2));
                String bdiFullName = getStringCellVal(row.getCell(j + 3));
                String bdiDateOfBirth = getStringCellVal(row.getCell(j + 4));
                String bdiPlaceOfBirth = getStringCellVal(row.getCell(j + 5));
                String bdiMothName = getStringCellVal(row.getCell(j + 6));                
                String dukNIK = getStringCellVal(row.getCell(j + 7));
                String dukFullName = getStringCellVal(row.getCell(j + 8));
                String dukDateOfBirth = getStringCellVal(row.getCell(j + 9));
                String dukPlaceOfBirth = getStringCellVal(row.getCell(j + 10));
                String dukMothName = getStringCellVal(row.getCell(j + 11));                
                String noBatch = context.get(MapKey.batchNo).toString();

                getLogger().info("Cod Cust ID : " + codCustID);
                getLogger().info("BDI NIK : " + bdiNIK);
                getLogger().info("BDI Full Name : " + bdiFullName);
                getLogger().info("BDI Date Of Birth : " + bdiDateOfBirth);
                getLogger().info("BDI Place Of Birth : " + bdiPlaceOfBirth);
                getLogger().info("BDI Moth Name : " + bdiMothName);
                getLogger().info("DUK NIK : " + dukNIK);
                getLogger().info("DUK Full Name : " + dukFullName);
                getLogger().info("DUK Date Of Birth : " + dukDateOfBirth);
                getLogger().info("DUK Place Of Birth : " + dukPlaceOfBirth);
                getLogger().info("DUK Moth Name : " + dukMothName);

                if (codCustID == null || "".equals(codCustID)) {
                    continue;
                }
                
                updateCIFTmp = new UpdateCIFTmp();
                updateCIFTmp.setCodCustID(Integer.parseInt(codCustID));                
                updateCIFTmp.setBdiNIK(bdiNIK);
                updateCIFTmp.setBdiFullName(bdiFullName);
                updateCIFTmp.setBdiDateOfBirth(bdiDateOfBirth);
                updateCIFTmp.setBdiPlaceOfBirth(bdiPlaceOfBirth);
                updateCIFTmp.setBdiMothName(bdiMothName);                
                updateCIFTmp.setDukNIK(dukNIK);
                updateCIFTmp.setDukFullName(dukFullName);
                updateCIFTmp.setDukDateOfBirth(dukDateOfBirth);
                updateCIFTmp.setDukPlaceOfBirth(dukPlaceOfBirth);
                updateCIFTmp.setDukMothName(dukMothName);                
                updateCIFTmp.setNoBatch(noBatch);
                updateCIFTmpDao.insert(updateCIFTmp);
            }
            } else {
                isMax = true;
            }                                                            
        } catch (Exception e) {
            getLogger().info("EXCEPTION : " + e,e);
        } finally {
            file.close();
        }
    }

    private void loadConfig(String confFile) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        InputStream in = UpdateCIFWorker.class.getClassLoader().getResourceAsStream(confFile);
        properties.load(in);        
        headerRow = Integer.parseInt(properties.getProperty("row_header_position"));
        rowNumCell = Integer.parseInt(properties.getProperty("row_num_cell"));
        rowDataStartOn = Integer.parseInt(properties.getProperty("row_data_start_on"));
        cellDataStartOn = Integer.parseInt(properties.getProperty("cell_data_start_on"));
        columnCnt = Integer.parseInt(properties.getProperty("column_count"));
        rowCnt = Integer.parseInt(properties.getProperty("row_count"));
        sheetData = Integer.parseInt(properties.getProperty("sheet_data"));
        in.close();
    }

    private String getStringCellVal(Cell cell) {
        String ret = "";
        if (cell != null) {
            if (cell.getCellType() == 0) {
                ret = String.valueOf((long) cell.getNumericCellValue());
            } else if (cell.getCellType() == 1) {
                ret = cell.getStringCellValue();
            }
        }
        return ret.trim();
    }

    private int headerRow;
    private int rowNumCell;
    private int rowDataStartOn;
    private int columnCnt;
    private int rowCnt;
    private int cellDataStartOn;
    private int sheetData;

    class MyXSSFSheetHandler extends DefaultHandler {
        private StylesTable stylesTable;
        private ReadOnlySharedStringsTable sharedStringsTable;
        private final PrintStream output;
        private final int minColumnCount;
        private boolean vIsOpen;
        private xssfDataType nextDataType;
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;
        private int thisColumn = -1;
        private int lastColumnNumber = -1;
        private StringBuffer value;
        private int ctr;
        private int minColumns;
        private UpdateCIFTmp ufcr;
        private UpdateCIFTmpDao ufcrDao;

        public MyXSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols, PrintStream target, UpdateCIFTmp updateCIFTmp, UpdateCIFTmpDao updateCIFTmpDao) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            
            ctr = 0;
            this.minColumns = cols;
            this.ufcr = updateCIFTmp;
            this.ufcrDao = updateCIFTmpDao;
            this.ufcr = new UpdateCIFTmp();
        }
    }       
    
    enum xssfDataType {
        BOOL,
        ERROR,
        FORMULA,
        INLINESTR,
        SSTINDEX,
        NUMBER,
    }
}
