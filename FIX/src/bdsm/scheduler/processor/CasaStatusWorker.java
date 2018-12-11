/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

/**
 *
 * @author v00019722
 */

import bdsm.scheduler.MapKey;
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
public class CasaStatusWorker extends BaseProcessor{
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
             + "Process Casa Status has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
 
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
             + "Your requested process Casa Status Update has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public CasaStatusWorker(Map context) {
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
        configFile = "excelutil_casastatus.properties";

        CasaStatusTmpDao casaStatusTmpDao = new CasaStatusTmpDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

        CasaStatusTmp casaStatusTmp = null;
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
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("CASASTATUS ", "")) + "{hhmmss}.xls");
            getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            getLogger().info("Param 5 : " + param5);

            readExcel(param5, configFile, casaStatusTmp, casaStatusTmpDao);
            tx.commit();
            this.tx = session.beginTransaction();
            casaStatusTmpDao.get(BatchID);
            getLogger().info("Remove Bug");
            casaStatusTmpDao.runValidate(BatchID);
            getLogger().info("Run validate Casa Status");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.UNAUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            outFileName = outFileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extension Done : " + outFileName);

            if (!sourceProcess.equalsIgnoreCase("sftp")) {
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
                getLogger().info("CasaStatusWorker Register FixQXtract Done");
            }

            return true;
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            getLogger().info("Status : AUTHORIZED");
            casaStatusTmpDao.runUpdate(BatchID);
            getLogger().info("Run update to Casa Status User Done");
            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("RE: CASASTATUS ", "")) + "{hhmmss}.xls");
            getLogger().info("CasaStatusWorker Register FixQXtract");
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
            getLogger().info("CasaStatusWorker Register FixQXtract Done");
            
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            getLogger().info("Status : REJECTED");
            getLogger().info("CasaStatusWorker Register FixQXtract");
            casaStatusTmpDao.runReject(BatchID);
            
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
            getLogger().info("CasaStatusWorker Register FixQXtract");
            
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

    private void readExcel(String param5, String configFile, CasaStatusTmp casaStatusTmp, CasaStatusTmpDao casaStatusTmpDao) throws FIXException, FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        loadConfig(configFile);
        
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, casaStatusTmp, casaStatusTmpDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, casaStatusTmp, casaStatusTmpDao);
        }
    }

    private void readXLS(String param5, CasaStatusTmp casaStatusTmp, CasaStatusTmpDao casaStatusTmpDao) throws FIXException, FileNotFoundException, IOException {
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        
        try {
            getLogger().info("Read XLS");
            getLogger().info("Column Count : " + columnCnt);
            getLogger().info("Row Count : " + rowCnt);
            getLogger().info("Cell Data Start On : " + cellDataStartOn);
            getLogger().info("Row Data Start On : " + rowDataStartOn);
            getLogger().info("Header Row : " + headerRow);
            getLogger().info("Row Number Cell : " + rowNumCell);
            
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
                getLogger().info("[Change] Row Count : " + rowCnt);
            }
            
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);

                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                    getLogger().info("[Change] Column Count : " + columnCnt);
                }

                int j = cellDataStartOn - rowNumCell;
                String codAcctNo = getStringCellVal(row.getCell(j + 1));                
                String codCCBrn = getStringCellVal(row.getCell(j + 2));
                int codAcctStatCurr = getIntegerCellVal(row.getCell(j + 3));
                String noBatch = context.get(MapKey.batchNo).toString();

                getLogger().info("Cod Acct No : " + codAcctNo);
                getLogger().info("Cod CC Brn : " + codCCBrn);
                getLogger().info("Cod Acct Stat Curr : " + codAcctStatCurr);
                getLogger().info("No Batch : " + noBatch);

                if (codAcctNo == null || "".equals(codAcctNo)) {
                    break;
                }
                
                casaStatusTmp = new CasaStatusTmp();
                casaStatusTmp.setCodAcctNo(codAcctNo);
                casaStatusTmp.setCodCCBrn(codCCBrn);
                casaStatusTmp.setCodAcctStatCurr(codAcctStatCurr);
                casaStatusTmp.setNoBatch(noBatch);                                                
                casaStatusTmpDao.insert(casaStatusTmp);
            }
        } catch (Exception e) {
            getLogger().info("EXCEPTION : " + e,e);
        } finally {
            file.close();
        }
    }

    private void readXLSX(String param5, CasaStatusTmp casaStatusTmp, CasaStatusTmpDao casaStatusTmpDao) throws FIXException, FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        File xlsxFile = new File(param5);
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        XSSFWorkbook wb = new XSSFWorkbook(p);
        XSSFSheet s = wb.getSheetAt(sheetData - 1);

        Row r = s.getRow(rowDataStartOn - headerRow);
        if (r.getPhysicalNumberOfCells() != 3) {
            throw new FIXException("INVALID COLUMN COUNT");
        }
        
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(p);
        XSSFReader xssfReader = new XSSFReader(p);
        StylesTable styles = xssfReader.getStylesTable();
        InputStream stream = xssfReader.getSheet("rId1");
        processSheet(styles, strings, stream, casaStatusTmp, casaStatusTmpDao);
        stream.close();
    }

    /**
     * 
     * @param styles
     * @param strings
     * @param sheetInputStream
     * @param casaStatusTmp
     * @param casaStatusTmpDao
     * @throws FIXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream, CasaStatusTmp casaStatusTmp, CasaStatusTmpDao casaStatusTmpDao) throws FIXException, IOException, ParserConfigurationException, SAXException {
        int minColumns = -1;
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, minColumns, System.out, casaStatusTmp, casaStatusTmpDao);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }

    private void loadConfig(String confFile) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        InputStream in = CasaStatusWorker.class.getClassLoader().getResourceAsStream(confFile);
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

    private int getIntegerCellVal(Cell cell) {
        int ret = 0;
        if (cell != null) {
            if (cell.getCellType() == 0) {
                ret = (int) cell.getNumericCellValue();
            } else if (cell.getCellType() == 1) {
                ret = (int) cell.getNumericCellValue();
            }
        }
        return ret;
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
        private CasaStatusTmp ufcr;
        private CasaStatusTmpDao ufcrDao;

        public MyXSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols, PrintStream target, CasaStatusTmp casaStatusTmp, CasaStatusTmpDao casaStatusTmpDao) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            
            ctr = 0;
            this.minColumns = cols;
            this.ufcr = casaStatusTmp;
            this.ufcrDao = casaStatusTmpDao;
            this.ufcr = new CasaStatusTmp();
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
