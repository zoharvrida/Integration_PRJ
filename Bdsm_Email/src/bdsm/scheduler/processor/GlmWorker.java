/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.GLCostCentreXref;
import bdsm.model.GLCostCentreXrefPK;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.GLCostCentreXrefDao;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;
/**
 *
 * @author NCBS
 */
public class GlmWorker extends BaseProcessor {
    
    private static final String invalidDate ="Invalid date in file name";
    
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
            + "Process Delete GLM37D Data has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process to Delete GLM37D Data has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    
    public GlmWorker(Map context) {
        super(context);
    }
    
    @Override
    protected boolean doExecute() throws Exception {
        String configFile;
        configFile = "excelutilglm37d.properties";    
        TmpGlmDao tmpGlmDao = new TmpGlmDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        TmpGlm tmpGlm = null;
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
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(7)) + "{hhmmss}.xls");
            getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            getLogger().info("Param 5 : " + param5); 
            
            FcrBaBankMastDao baBankMaster = new FcrBaBankMastDao(session); 
          /*  Date date =baBankMaster.get().getDatProcess();
            String dateFile = outFileName.substring(6, 14);
        
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd"); 
            Date dateInFile =  outputDateFormat.parse(dateFile);
            if (date.compareTo(dateInFile) !=0) throw new FIXException(invalidDate);
            */
            String tempDate=baBankMaster.get().getDatProcess().toString();            
            String dates = outFileName.substring(6, 14);
           
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            Date tempDatey=simpledateformat.parse(dates);  
           
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");   
            String dProcess = outputDateFormat.format(tempDatey);   
            if (dProcess.compareTo(tempDate) !=0)
			throw new FIXException(invalidDate);
		
            readExcel(param5, configFile, tmpGlm, tmpGlmDao);
            getLogger().info("Import Excel file from Requestor Success");
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
            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(11)) + "{hhmmss}.xls");
            getLogger().info("Register FixQXtract");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.AUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fixSchedulerXtract.getFileFormat();
            out2FileName = out2FileName.replace(".xls", "." + extFile);
            getLogger().info("Getting file Extention DONE : " + out2FileName);
            
            List<TmpGlm> glm = tmpGlmDao.getByBachNo(BatchID);
            for (TmpGlm glmQuery : glm){
               String codGl = glmQuery.getCodGLAcct();
               Integer codCC = glmQuery.getCodCCBrn();
               Integer codLob = glmQuery.getCodLob();
              
               GLCostCentreXrefPK  xref = new GLCostCentreXrefPK();
               xref.setCodGLAcct(codGl);
               xref.setCodCCBrn(codCC);
               xref.setCodLob(codLob);
               xref.setFlgMntStatus("A");
               xref.setCodEntityVpd(11);
               GLCostCentreXrefDao xrefGlm = new GLCostCentreXrefDao(session); 
               GLCostCentreXref fGlm = xrefGlm.get(xref);
               xrefGlm.delete(fGlm);  
               glmQuery.setFlagStatus("D");
            }	
            
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
           // tmpGlmDao.runRejectUaj(BatchID);
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
    
    private void readExcel(String param5, String configFile, TmpGlm tmpGlm, TmpGlmDao tmpGlmDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
        loadConfig(configFile);
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, tmpGlm, tmpGlmDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, tmpGlm, tmpGlmDao);
        }
    }
    
    private void readXLS(String param5, TmpGlm tmpGlm, TmpGlmDao tmpGlmDao) throws FileNotFoundException, IOException, ParseException {
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
                
                tmpGlm = new TmpGlm();
                tmpGlm.setBatchNo(context.get(MapKey.batchNo).toString());
                tmpGlm.setCodGLAcct(getStringCellVal(row.getCell(j)));
                tmpGlm.setCodCCBrn(Integer.valueOf(getStringCellVal(row.getCell(j+1))));
                tmpGlm.setCodLob(Integer.valueOf(getStringCellVal(row.getCell(j+2))));
                tmpGlm.setIdMaintainedBy(context.get(MapKey.param2).toString());
            //    tmpGlm.setIdCreatedBy(context.get(MapKey.param2).toString());                
                GLCostCentreXrefPK glCostCentreXrefPK = new GLCostCentreXrefPK();
                glCostCentreXrefPK.setCodGLAcct(getStringCellVal(row.getCell(j)));
                glCostCentreXrefPK.setCodCCBrn(Integer.valueOf(getStringCellVal(row.getCell(j+1))));                
                glCostCentreXrefPK.setCodLob(Integer.valueOf(getStringCellVal(row.getCell(j+2))));
                glCostCentreXrefPK.setFlgMntStatus("A");
                glCostCentreXrefPK.setCodEntityVpd(11);
                GLCostCentreXrefDao xref = new GLCostCentreXrefDao(session);
                if(xref.get(glCostCentreXrefPK)== null){
                    tmpGlm.setFlagStatus(StatusDefinition.REJECTED);
                }  
                if(xref.get(glCostCentreXrefPK)!= null){
                    tmpGlm.setFlagStatus(StatusDefinition.UNAUTHORIZED);
                }
                    tmpGlmDao.insert(tmpGlm);
            }
        } finally {
            file.close();
        }
    }
    
    private void readXLSX(String param5, TmpGlm tmpGlm, TmpGlmDao tmpGlmDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(sheetData - 1);
            
            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }
                
                int j = cellDataStartOn - rowNumCell;
                
                tmpGlm = new TmpGlm();
                tmpGlm.setBatchNo(context.get(MapKey.batchNo).toString());
                tmpGlm.setCodGLAcct(getStringCellVal(row.getCell(j)));
                tmpGlm.setCodCCBrn(Integer.valueOf(getStringCellVal(row.getCell(j+1))));
                tmpGlm.setCodLob(Integer.valueOf(getStringCellVal(row.getCell(j+2))));
                tmpGlm.setIdMaintainedBy(context.get(MapKey.param2).toString());
                // tmpGlm.setIdCreatedBy(context.get(MapKey.param2).toString());                
                GLCostCentreXrefPK glCostCentreXrefPK = new GLCostCentreXrefPK();
                glCostCentreXrefPK.setCodGLAcct(getStringCellVal(row.getCell(j)));
                glCostCentreXrefPK.setCodCCBrn(Integer.valueOf(getStringCellVal(row.getCell(j+1))));                
                glCostCentreXrefPK.setCodLob(Integer.valueOf(getStringCellVal(row.getCell(j+2))));
                glCostCentreXrefPK.setFlgMntStatus("A");
                glCostCentreXrefPK.setCodEntityVpd(11);
                GLCostCentreXrefDao xref = new GLCostCentreXrefDao(session);
                if(xref.get(glCostCentreXrefPK)== null){
                    tmpGlm.setFlagStatus(StatusDefinition.REJECTED);
                } else {
                    tmpGlm.setFlagStatus(StatusDefinition.UNAUTHORIZED);                    
                }
               // tmpGlm.getIdCreatedBy();
               // tmpGlm.getDtmCreated();
             /*   uajTmpSrc = new UajTmpSrc();
                uajTmpSrc.setDealerNo(getStringCellVal(row.getCell(j)));
                uajTmpSrc.setAajiCert(getStringCellVal(row.getCell(j + 1)));
                uajTmpSrc.setNip(getStringCellVal(row.getCell(j + 2)));
                uajTmpSrc.setStatus(getStringCellVal(row.getCell(j + 3)));
                try {
                    uajTmpSrc.setCertExpiryDate(formatter.parse(getStringCellVal(row.getCell(j + 4))));
                } catch (ParseException ee) {
                    if (!"".equals(getStringCellVal(row.getCell(j + 3)))) {
                        uajTmpSrc.setFlgStatus("R");
                        uajTmpSrc.setStatusReason("Cert Expiry - Invalid Date Format (DD/MM/YYYY) for " + getStringCellVal(row.getCell(j + 4)));
                    }
                    getLogger().error(ee, ee);
                }
                uajTmpSrc.setNoBatch(context.get(MapKey.batchNo).toString()); */
                tmpGlmDao.insert(tmpGlm);
            }
        } finally {
            file.close();
        }
    }
    
  /*  public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            InputStream sheetInputStream, TmpGlm uajTmpSrc, TmpGlmDao tmpGlmDao)
            throws IOException, ParserConfigurationException, SAXException {
        int minColumns = -1;
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, minColumns, System.out, uajTmpSrc, tmpGlmDao);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }
    */
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
    
   /* class MyXSSFSheetHandler extends DefaultHandler {
        
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
        private TmpGlm uaj;
        private TmpGlmDao uajDao;
        
        public MyXSSFSheetHandler(
                StylesTable styles,
                ReadOnlySharedStringsTable strings,
                int cols,
                PrintStream target, TmpGlm uajTmpSrc, UajTmpSrcDao uajTmpSrcDao) {
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
            this.uajDao = tmpGlmDao;
            this.uaj = new TmpGlm();
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
                uaj = new TmpGlm();
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
    */
    enum xssfDataType {
        
        BOOL,
        ERROR,
        FORMULA,
        INLINESTR,
        SSTINDEX,
        NUMBER,
    }
   /* public static void main(String[] args){
    String outFileName="GLM037110820141221.XLS";
    String dFile = outFileName.substring(6, 14);
    System.out.print(dFile);
    } */
}
