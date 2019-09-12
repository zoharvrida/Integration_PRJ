/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.scheduler.dao.UajTmpSrcDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.UajTmpSrc;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author NCBS
 */
public class AajDlrWorker extends BaseProcessor {
    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
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
            + "Process Update AAJI Data has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process to update AAJI Data has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    
    /**
     * 
     * @param context
     */
    public AajDlrWorker(Map context) {
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
        configFile = "excelutilaaji.properties";
        UajTmpSrcDao uajTmpSrcDao = new UajTmpSrcDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        String filePattern = (String) context.get(MapKey.filePattern);
        UajTmpSrc uajTmpSrc = null;
        FixClassConfig fClassConfig = null;
        FixSchedulerXtract fixSchedulerXtract = null;
        String extFile = "";
        String BatchID = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String param1 = context.get(MapKey.param1).toString();
        String sourceProcess = context.get(MapKey.processSource).toString();
        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
        FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(session);
        getLogger().info("Done Prepare before execute status U/A");
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            getLogger().info("Status : UNAUTHORIZED");
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("AAJDLR ", "")) + "{hhmmss}.xls");
            String filename = (String) context.get(MapKey.fileName);
            getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            getLogger().info("Param 5 : " + param5);
             /* Pattern filename */
            try {
                String xtractFilename;
                String dateInFilename;
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(filePattern);
                java.util.regex.Matcher matcher = pattern.matcher(filename);
                if (matcher.find()) {
                    this.getLogger().info("pattern match start at: " + matcher.start());
                    this.getLogger().info("pattern match end at: " + matcher.end());

                    xtractFilename = filename.substring(matcher.start(), matcher.end());
                    dateInFilename = filename.substring(6, 14);
                    this.getLogger().info("dateInFilename: " + dateInFilename);
                    if (!dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(dateInFilename)) {
                        throw new FIXException("date in filename must be same with business date");
                    }
                }
            } catch (Exception pe) {
                throw new FIXException("Invalid date in filename");
            }
            readExcel(param5, configFile, uajTmpSrc, uajTmpSrcDao);
            //buat ilangin bug
            uajTmpSrcDao.get(BatchID);
            getLogger().info("Import Excel file from Requestor Success");
            // do filter and validation
            uajTmpSrcDao.runUploadUaj(BatchID);
            getLogger().info("Filter and Validate Source Data Done");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.UNAUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            outFileName = outFileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extension Done : " + outFileName);
            if (!sourceProcess.equalsIgnoreCase("sftp")) {
                fixQXtract = new FixQXtract();
                fixQXtract.setIdScheduler(idScheduler);
                fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                fixQXtract.setParam1("RE: " + param1);
                FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
                fixQXtract.setParam4(this.emailApproval);
                fixQXtract.setParam5(outFileName);
                fixQXtract.setParam6(BatchID);
                getLogger().info("Register FixQXtract Done");
            }
            return true;
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            getLogger().info("Status : AUTHORIZED");
            uajTmpSrcDao.runUpdateUaj(BatchID);
            getLogger().info("Run update to FCR Done");
            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("RE: AAJDLR ", "")) + "{hhmmss}.xls");
            getLogger().info("Register FixQXtract");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.AUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            out2FileName = out2FileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extention DONE : " + out2FileName);
            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(idScheduler);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);
            FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
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
            getLogger().info("Register FixQXtract Done");
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            getLogger().info("Status : REJECTED");
            getLogger().info("Register FixQXtract");
            uajTmpSrcDao.runRejectUaj(BatchID);
            fixQXtract = new FixQXtract();
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
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
            getLogger().info("Register FixQXtract");
            return true;
            
        } else {
            getLogger().info("Status : IGNORED");
            //maybe ignored
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
            return true;
        }
    }
    
    private void readExcel(String param5, String configFile, UajTmpSrc uajTmpSrc, UajTmpSrcDao uajTmpSrcDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
        loadConfig(configFile);
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, uajTmpSrc, uajTmpSrcDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, uajTmpSrc, uajTmpSrcDao);
        }
    }
    
    private void readXLS(String param5, UajTmpSrc uajTmpSrc, UajTmpSrcDao uajTmpSrcDao) throws FileNotFoundException, IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);
            
            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }
                
                int j = cellDataStartOn - rowNumCell;

                uajTmpSrc = new UajTmpSrc();
                uajTmpSrc.setDealerNo(getStringCellVal(row.getCell(j)));
                uajTmpSrc.setAajiCert(getStringCellVal(row.getCell(j + 1)));
                uajTmpSrc.setNip(getStringCellVal(row.getCell(j + 2)));
                uajTmpSrc.setStatus(getStringCellVal(row.getCell(j + 3)));
                uajTmpSrc.setCertType(getStringCellVal(row.getCell(j + 5)));
                String acctNo = getStringCellVal(row.getCell(j));
                if (acctNo == null || "".equals(acctNo)) {
                    uajTmpSrc.setFlgStatus("R");
                    uajTmpSrc.setStatusReason("Invalid Dealer No - is Null");
                }
                try {
                    uajTmpSrc.setCertExpiryDate(formatter.parse(getStringCellVal(row.getCell(j + 4))));
                } catch (ParseException ee) {
                    if (!"".equals(getStringCellVal(row.getCell(j + 3)))) {
                        uajTmpSrc.setFlgStatus("R");
                        uajTmpSrc.setStatusReason("Cert Expiry - Invalid Date Format (DD/MM/YYYY) for " + getStringCellVal(row.getCell(j + 4)));
                    }
                    getLogger().error(ee, ee);
                }
                uajTmpSrc.setNoBatch(context.get(MapKey.batchNo).toString());
                uajTmpSrcDao.insert(uajTmpSrc);
            }
        } finally {
            file.close();
        }
    }
    
    private void readXLSX(String param5, UajTmpSrc uajTmpSrc, UajTmpSrcDao uajTmpSrcDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        File xlsxFile = new File(param5);
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(p);
        XSSFReader xssfReader = new XSSFReader(p);
        StylesTable styles = xssfReader.getStylesTable();
        InputStream stream = xssfReader.getSheet("rId1");
        processSheet(styles, strings, stream, uajTmpSrc, uajTmpSrcDao);
        stream.close();
    }
    
    /**
     * 
     * @param styles
     * @param strings
     * @param sheetInputStream
     * @param uajTmpSrc
     * @param uajTmpSrcDao
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            InputStream sheetInputStream, UajTmpSrc uajTmpSrc, UajTmpSrcDao uajTmpSrcDao)
            throws IOException, ParserConfigurationException, SAXException {
        int minColumns = -1;
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, minColumns, System.out, uajTmpSrc, uajTmpSrcDao);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }
    
    private void loadConfig(String confFile) throws FileNotFoundException, IOException {
        
        Properties properties = new Properties();
        InputStream in = AajDlrWorker.class.getClassLoader().getResourceAsStream(confFile);
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
        private UajTmpSrc uaj;
        private UajTmpSrcDao uajDao;
        
        public MyXSSFSheetHandler(
                StylesTable styles,
                ReadOnlySharedStringsTable strings,
                int cols,
                PrintStream target, UajTmpSrc uajTmpSrc, UajTmpSrcDao uajTmpSrcDao) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            ctr = 0;
            this.minColumns = cols;
            this.uaj = uajTmpSrc;
            this.uajDao = uajTmpSrcDao;
            this.uaj = new UajTmpSrc();
        }
        
        @Override
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {
            
            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                value.setLength(0);
            } else if ("c".equals(name)) {
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length();
                        ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                thisColumn = nameToColumn(r.substring(0, firstDigit));
                this.nextDataType = xssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if ("b".equals(cellType)) {
                    nextDataType = xssfDataType.BOOL;
                } else if ("e".equals(cellType)) {
                    nextDataType = xssfDataType.ERROR;
                } else if ("inlineStr".equals(cellType)) {
                    nextDataType = xssfDataType.INLINESTR;
                } else if ("s".equals(cellType)) {
                    nextDataType = xssfDataType.SSTINDEX;
                } else if ("str".equals(cellType)) {
                    nextDataType = xssfDataType.FORMULA;
                } else if (cellStyleStr != null) {
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    if (this.formatString == null) {
                        this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
                    }
                }
            }
            
        }
        
        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            
            String thisStr = null;
            if ("v".equals(name)) {
                switch (nextDataType) {
                    case BOOL:
                        char first = value.charAt(0);
                        thisStr = first == '0' ? "FALSE" : "TRUE";
                        break;
                    case ERROR:
                        thisStr = "\"ERROR:" + value.toString() + '"';
                        break;
                    case FORMULA:
                        thisStr = value.toString();
                        break;
                    case INLINESTR:
                        XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                        thisStr = '"' + rtsi.toString() + '"';
                        break;
                    case SSTINDEX:
                        String sstIndex = value.toString();
                        try {
                            int idx = Integer.parseInt(sstIndex);
                            XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
                            thisStr = rtss.toString();
                        } catch (NumberFormatException ex) {
                            getLogger().error(ex, ex);
                        }
                        break;
                    case NUMBER:
                        String n = value.toString();
                        if (this.formatString != null) {
                            thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex, this.formatString);
                        } else {
                            thisStr = n;
                        }
                        break;
                    default:
                        thisStr = "(Unexpected type: " + nextDataType + ")";
                        break;
                }
                
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                if (ctr >= (rowDataStartOn - 1)) {
                    if (thisColumn == 1) {
                        uaj.setDealerNo(thisStr);
                    }
                    if (thisColumn == 2) {
                        uaj.setAajiCert(thisStr);
                    }
                    if (thisColumn == 3) {
                        uaj.setNip(thisStr);
                    }
                    if (thisColumn == 4) {
                        uaj.setStatus(thisStr);
                    }
                    if (thisColumn == 5) {
                        try {
                            uaj.setCertExpiryDate(formatter.parse(thisStr));
                        } catch (ParseException ex) {
                            if (!"".equals(thisStr)) {
                                uaj.setFlgStatus("R");
                                uaj.setStatusReason("Cert Expiry - Invalid Date Format (DD/MM/YYYY) for " + thisStr);
                            }
                            getLogger().error(ex, ex);
                        }
                    }
                    if (thisColumn == 6) {
                        uaj.setCertType(thisStr);
                    }
                    uaj.setNoBatch(context.get(MapKey.batchNo).toString());
                }
                if (thisColumn
                        > -1) {
                    lastColumnNumber = thisColumn;
                }
                
            } else if ("row".equals(name)) {
                if (minColumns
                        > 0) {
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    for (int i = lastColumnNumber; i < (this.minColumnCount);
                            i++) {
                    }
                }
                
                if (ctr >= (rowDataStartOn - 1)) {
                    uajDao.insert(uaj);
                }
                ctr++;
                lastColumnNumber = -1;
                uaj = new UajTmpSrc();
            }
            
        }
        
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen) {
                value.append(ch, start, length);
            }
        }
        
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length();
                    ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
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
