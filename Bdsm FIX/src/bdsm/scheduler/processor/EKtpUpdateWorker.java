/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

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
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.crypto.*;
import bdsm.util.EncryptionUtil;

/**
 *
 * @author NCBS
 */
public class EKtpUpdateWorker extends BaseProcessor {

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
            + "Process E-KTP has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process E-KTP Update has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public EKtpUpdateWorker(Map context) {
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
        configFile = "excelutil_Ektp.properties";
        EKtpUpdateTmpDao uEKtpUpdateTmpDao = new EKtpUpdateTmpDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

        FixClassConfig fClassConfig = null;
        FixSchedulerXtract fixSchedulerXtract = null;
        String extFile = "";
        String BatchID = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String param1 = context.get(MapKey.param1).toString();
        String sourceProcess = context.get(MapKey.processSource).toString();
        String filepattern = context.get(MapKey.templateName).toString();
        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
        getLogger().info("Done Prepare before execute status U/A");
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            getLogger().info("Status : UNAUTHORIZED");
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace(filepattern+" ", "")) + "{hhmmss}.xls");
            getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            getLogger().info("Param 5 : " + param5);

            EKtpUpdateTmp EKtpUpdateTmp = new EKtpUpdateTmp();

            readExcel(param5, configFile, EKtpUpdateTmp, uEKtpUpdateTmpDao);

            uEKtpUpdateTmpDao.get(BatchID);
            getLogger().info("Remove Bug");
            uEKtpUpdateTmpDao.runValidateEKtpUpdate(BatchID);
            getLogger().info("Run validate EKtp Update");
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
                getLogger().info("EKtpUpdateWorker Register FixQXtract Done");
            }
            return true;
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            getLogger().info("Status : AUTHORIZED");
            uEKtpUpdateTmpDao.runUpdateEKtpUpdate(BatchID);
            getLogger().info("Run update to EKtp User Done");

            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("RE: EKTPREG ", "")) + "{hhmmss}.xls");
            getLogger().info("EKtpUpdateWorker Register FixQXtract");
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
            getLogger().info("EKtpUpdateWorker Register FixQXtract Done");
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            getLogger().info("Status : REJECTED");
            getLogger().info("EKtpUpdateWorker Register FixQXtract");
            uEKtpUpdateTmpDao.runRejectEKtpUpdate(BatchID);
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
            getLogger().info("EKtpUpdateWorker Register FixQXtract");
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

    private void readExcel(String param5, String configFile, EKtpUpdateTmp uEKtpUpdateTmp, EKtpUpdateTmpDao uEKtpUpdateTmpDao) throws FIXException, FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        loadConfig(configFile);
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, uEKtpUpdateTmp, uEKtpUpdateTmpDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, uEKtpUpdateTmp, uEKtpUpdateTmpDao);
        }
    }

    private void readXLS(String param5,EKtpUpdateTmp uEKtpUpdateTmp, EKtpUpdateTmpDao uEKtpUpdateTmpDao) throws FIXException, FileNotFoundException, IOException {
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
				//getLogger().info("PHYSICAL NUMBER : " + row.getPhysicalNumberOfCells());
                /*if (row.getPhysicalNumberOfCells() != 7) {
                    throw new FIXException("INVALID COLUMN COUNT");
                }*/
                String acctNo = getStringCellVal(row.getCell(j));
                if (acctNo == null || "".equals(acctNo)) {
                    break;
                }
                uEKtpUpdateTmp = new EKtpUpdateTmp();
                uEKtpUpdateTmp.setUserId(getStringCellVal(row.getCell(j)));
                uEKtpUpdateTmp.setNamuser(getStringCellVal(row.getCell(j + 1)));
                uEKtpUpdateTmp.setNikuser(getStringCellVal(row.getCell(j + 2)));  
                uEKtpUpdateTmp.setKtpuser(getStringCellVal(row.getCell(j + 3)));
				String KtpPWD = EncryptKtp(getStringCellVal(row.getCell(j + 3)), getStringCellVal(row.getCell(j + 4)));
				String KtpPWDdEC = DecryptKtp(KtpPWD, getStringCellVal(row.getCell(j + 3)));
				getLogger().info("KTPPWD : " + KtpPWD);
				getLogger().info("KTPPWDDEC : " + KtpPWDdEC);
                uEKtpUpdateTmp.setKtppwd(KtpPWD);                                
                uEKtpUpdateTmp.setKtppwddecrypt(KtpPWDdEC);                                
                uEKtpUpdateTmp.setIpuser(getStringCellVal(row.getCell(j + 5)));                
                uEKtpUpdateTmp.setCmd(getStringCellVal(row.getCell(j+6)));
                uEKtpUpdateTmp.setKtpMaxQuery(getStringCellVal(row.getCell(j+7)));
                uEKtpUpdateTmp.setNobatch(context.get(MapKey.batchNo).toString());                
                uEKtpUpdateTmp.setSource(context.get(MapKey.templateName).toString());
                uEKtpUpdateTmpDao.insert(uEKtpUpdateTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
			getLogger().info("EXCEPTION : " + e,e);
        }finally {
            file.close();
        }
    }


    private void readXLSX(String param5, EKtpUpdateTmp uEKtpUpdateTmp, EKtpUpdateTmpDao uEKtpUpdateTmpDao) throws FIXException, FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        File xlsxFile = new File(param5);
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        XSSFWorkbook wb = new XSSFWorkbook(p);
        XSSFSheet s = wb.getSheetAt(sheetData - 1);
        Row r = s.getRow(rowDataStartOn - headerRow);
        if (r.getPhysicalNumberOfCells() != 7) {
            throw new FIXException("INVALID COLUMN COUNT");
        }
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(p);
        XSSFReader xssfReader = new XSSFReader(p);
        StylesTable styles = xssfReader.getStylesTable();
        InputStream stream = xssfReader.getSheet("rId1");
        processSheet(styles, strings, stream, uEKtpUpdateTmp, uEKtpUpdateTmpDao);
        stream.close();
    }

    /**
     * 
     * @param styles
     * @param strings
     * @param sheetInputStream
     * @param uEKtpUpdateTmp
     * @param uEKtpUpdateTmpDao
     * @throws FIXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            InputStream sheetInputStream, EKtpUpdateTmp uEKtpUpdateTmp, EKtpUpdateTmpDao uEKtpUpdateTmpDao)
            throws FIXException, IOException, ParserConfigurationException, SAXException {
        int minColumns = -1;
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, minColumns, System.out, uEKtpUpdateTmp, uEKtpUpdateTmpDao);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }

    private void loadConfig(String confFile) throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        InputStream in = EKtpUpdateWorker.class.getClassLoader().getResourceAsStream(confFile);
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

        /**
         * Table with styles
         */
        private StylesTable stylesTable;
        /**
         * Table with unique strings
         */
        private ReadOnlySharedStringsTable sharedStringsTable;
        /**
         * Destination for data
         */
        private final PrintStream output;
        /**
         * Number of columns to read starting with leftmost
         */
        private final int minColumnCount;
        // Set when V start element is seen
        private boolean vIsOpen;
        // Set when cell start element is seen;
        // used when cell close element is seen.
        private xssfDataType nextDataType;
        // Used to format numeric cell values.
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;
        private int thisColumn = -1;
        // The last column printed to the output stream
        private int lastColumnNumber = -1;
        // Gathers characters as they are seen.
        private StringBuffer value;
        private int ctr;
        private int minColumns;
        private EKtpUpdateTmp ufcr;
        private EKtpUpdateTmpDao ufcrDao;

        /**
         * Accepts objects needed while parsing.
         *
         * @param styles Table of styles
         * @param strings Table of shared strings
         * @param cols Minimum number of columns to show
         * @param target Sink for output
         */
        public MyXSSFSheetHandler(
                StylesTable styles,
                ReadOnlySharedStringsTable strings,
                int cols,
                PrintStream target, EKtpUpdateTmp uEKtpUpdateTmp, EKtpUpdateTmpDao uEKtpUpdateTmpDao) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            ctr = 0;
            this.minColumns = cols;
            this.ufcr = uEKtpUpdateTmp;
            this.ufcrDao = uEKtpUpdateTmpDao;
            this.ufcr = new EKtpUpdateTmp();
        }

        /*
         * (non-Javadoc) @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {

            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                // Clear contents cache
                value.setLength(0);
            } // c =&gt; cell
            else if ("c".equals(name)) {
                // Get the cell reference
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

                // Set up defaults.
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
                    // It's a number, but almost certainly one
                    //  with a special style or format 
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

        /*
         * (non-Javadoc) @see
         * org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            String thisStr = null;
            String iktpuser = null;

            // v =&gt; contents of a cell
            if ("v".equals(name)) {
                // Process the value contents as required.
                // Do now, as characters() may be called more than once
                switch (nextDataType) {

                    case BOOL:
                        char first = value.charAt(0);
                        thisStr = first == '0' ? "FALSE" : "TRUE";
                        break;

                    case ERROR:
                        thisStr = "\"ERROR:" + value.toString() + '"';
                        break;

                    case FORMULA:
                        // A formula could result in a string value,
                        // so always add double-quote characters.
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
                            output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
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

                // Output after we've seen the string contents
                // Emit commas for any fields that were missing on this row
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }

                if (thisColumn == 1) {
                    ufcr.setUserId(thisStr);
                }

                if (thisColumn == 2) {
                    ufcr.setNamuser(thisStr);
                }

                if (thisColumn == 3) {
                    ufcr.setNikuser(thisStr);
                }
                                
                if (thisColumn == 4) {
                    iktpuser=thisStr;
                    ufcr.setKtpuser(thisStr);
                }
/*                                
                if (thisColumn == 5) {
                    ufcr.setktppwd(thisStr);
                }
*/
                if (thisColumn == 5) {                    
                    ufcr.setKtppwd(EncryptKtp(thisStr,iktpuser));  
                    ufcr.setKtppwddecrypt(DecryptKtp(ufcr.getKtppwd(),iktpuser));  
                    
                }

                if (thisColumn == 7) {
                    ufcr.setIpuser(thisStr);
                }
                
                ufcr.setNobatch(context.get(MapKey.batchNo).toString());
                ufcr.setSource(context.get(MapKey.templateName).toString());
                // Update column
                if (thisColumn
                        > -1) {
                    lastColumnNumber = thisColumn;
                }

            } else if ("row".equals(name)) {

                // Print out any missing commas if needed
                if (minColumns
                        > 0) {
                    // Columns are 0 based
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    for (int i = lastColumnNumber; i < (this.minColumnCount);
                            i++) {
                        //output.print(',');
                    }
                }

                // We're onto a new row
                //output.println();
                if (ctr != 0) {
                    ufcrDao.insert(ufcr);
                }
                ctr++;
                lastColumnNumber = -1;
                ufcr = new EKtpUpdateTmp();
            }

        }

        /**
         * Captures characters only if a suitable element is open. Originally
         * was just "v"; extended for inlineStr also.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen) {
                value.append(ch, start, length);
            }
        }

        /**
         * Converts an Excel column name like "C" to a zero-based index.
         *
         * @param name
         * @return Index corresponding to the specified name
         */
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
    
    // Encrypt password
    /**
     * 
     * @param xktpuser
     * @param Xktppwd
     * @return
     */
    public String EncryptKtp(String xktpuser, String Xktppwd) 
    {
    	String ktpUser = xktpuser; 
    	int m = ktpUser.length()%16;
    	if (m!=0) {
    		m=16-m;
    		for (int i=0; i<m; i++) {
    			ktpUser = ktpUser + "_" ;
    		}
    		// ktpUser become "_________danamon"
    	}
        if (ktpUser.length()>16) ktpUser = ktpUser.substring(0, 16);
        
        String encPasswd = null;
        try { encPasswd = EncryptionUtil.getAES(Xktppwd, ktpUser, Cipher.ENCRYPT_MODE); } catch (Exception e) {
            e.printStackTrace();
			getLogger().info("FAILED TO ENCRYPT :" + e,e);
        }
        return encPasswd ;
           
    }
      
    /**
     * 
     * @param encrypt
     * @param xktpuser
     * @return
     */
    public String DecryptKtp(String encrypt, String xktpuser ) 
    {
    	String ktpUser = xktpuser; 
		//ktpUser = BdsmUtil.leftPad(ktpUser, 16, '_');
    	int m = ktpUser.length()%16;
    	if (m!=0) {
    		m=16-m;
    		for (int i=0; i<m; i++) {
    			ktpUser = ktpUser + "_";
    		}
    		// ktpUser become "_________danamon"
    	}
        if (ktpUser.length()>16) ktpUser = ktpUser.substring(0, 16);
        
        String decPasswd = null;
		getLogger().info("PASSWORD :" + ktpUser);
        try { decPasswd = EncryptionUtil.getAES(encrypt, ktpUser, Cipher.DECRYPT_MODE); } catch (Exception e) {
            e.printStackTrace();
			getLogger().info("FAILED TO DECRYPT :" + e,e);
        }
        return decPasswd ;
           
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
