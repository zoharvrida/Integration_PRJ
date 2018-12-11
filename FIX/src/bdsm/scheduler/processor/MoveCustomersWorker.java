/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.fcr.model.*;
import bdsm.scheduler.model.TmpMoveCustomers;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsmhost.fcr.dao.BaMoveCustomersDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpMoveCustomersDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaccBrnMastDAO;
import bdsmhost.fcr.dao.CiCustMastDao;
import bdsmhost.fcr.dao.CmCustcardMastDao;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
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
 * @author v00020841
 */
public class MoveCustomersWorker extends BaseProcessor{

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    private static final String INVALIDDATE = "Invalid date in file name";
    private static final String EXISTED = "[data already exists]";
    private static final String NOTFOUND = "'data not found'";
    private static final String REJECT = "REJECT";
    private static final String DONE = "DONE";
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
            + "Process Move Customers Data has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process to Move Customer Data has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public MoveCustomersWorker(Map context) {
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
        configFile = "excelutil_movecustomers.properties";
        TmpMoveCustomersDao tmpMoveCustomersDao = new TmpMoveCustomersDao(session);
        FixClassConfigDao fixClassConfigDao = new FixClassConfigDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        TmpMoveCustomers tmpMoveCustomers = null;
        FixClassConfig fClassConfig;
        FixSchedulerXtract fSchedulerXtract;
        String extFile;
        String BatchId = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String param1 = context.get(MapKey.param1).toString();
        String sourceProcess = context.get(MapKey.processSource).toString();
        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());

        this.getLogger().info("Done Prepare Before Execute Status U/A");
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            this.getLogger().info("Status : UNAUTHORIZED");
            String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(7)) + "{hhmmss}.xls");
            this.getLogger().info("Out File Name : " + outFileName);
            String param5 = context.get(MapKey.param5).toString();
            this.getLogger().info("Param5 : " + param5);
            //VALIDASI DATE
            FcrBaBankMastDao baBankMaster = new FcrBaBankMastDao(session);
            String tempDate = baBankMaster.get().getDatProcess().toString();
            String dates = outFileName.substring(6, 14);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            Date tempDatey = simpledateformat.parse(dates);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dProcess = outputDateFormat.format(tempDatey);
            if (dProcess.compareTo(tempDate) != 0) {
                throw new FIXException(INVALIDDATE);
                }
            
            readExcel(param5, configFile, tmpMoveCustomers, tmpMoveCustomersDao);
            this.getLogger().info("Import File Excel From Requestor Succes");
            fClassConfig = fixClassConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting Id Scheduler " + idScheduler + " for Source : " + sourceProcess + " and status " + StatusDefinition.UNAUTHORIZED + " Done");
            fSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fSchedulerXtract.getFileFormat();
            outFileName = outFileName.replace(".xls", "." + extFile);
            this.getLogger().info("getting File Extension Done :" + outFileName);

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
                fixQXtract.setParam6(BatchId);
                this.getLogger().info("Register Fixqxtract Done");
            }
            return true;

        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            this.getLogger().info("Status : Authorizhed");
            //tmpMoveCustomerDao.runInsert(BatchID)
            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(11)) + "{hhmmss}.xls");
            this.getLogger().info("Register FixQXtract");
            fClassConfig = fixClassConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idScheduler = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.AUTHORIZED + "DONE");
            fSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
            extFile = fSchedulerXtract.getFileFormat();
            out2FileName = out2FileName.replace(".xls", "." + extFile);
            this.getLogger().info("Getting file Extention DONE : " + out2FileName);

            //INSERT KE ORIGIN TABLE
            BaMoveCustomers moveCustomers;
            tmpMoveCustomersDao = new TmpMoveCustomersDao(session);
            BaMoveCustomersDao baMoveCustomersDao = new BaMoveCustomersDao(session);
            try {
                this.getLogger().info("Start Proccess");
				List<TmpMoveCustomers> tmcs = tmpMoveCustomersDao.getBybatchNo(BatchId);
            for (TmpMoveCustomers moveCustomersTmp : tmcs) {
                Integer codCustid = moveCustomersTmp.getCodCustId();
                Integer codCurBrn = moveCustomersTmp.getCodCurBrn();
                Integer codXferBrn = moveCustomersTmp.getCodXferBrn();
                BaBankMastDAO baBankMastDAO = new BaBankMastDAO(session);
                CmCustcardMastDao cmCustcardMastDao = new CmCustcardMastDao(session);
                    BaccBrnMastDAO baccBrnMastDAO = new BaccBrnMastDAO(session);
                Date datProcess = baBankMastDAO.get().getDatProcess();
                dateFormatter.format(datProcess);
                BaMoveCustomersPK baMoveCustomersPK = new BaMoveCustomersPK();
                baMoveCustomersPK.setCodCustId(codCustid);
                baMoveCustomersPK.setFlagMntStatus("A");
                baMoveCustomersPK.setDatProcess(datProcess);
                Object check = null;
                
                    if (codCustid.equals(0)) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                        moveCustomersTmp.setStatusReason(REJECT + ", [cod_cust_id is null]");
                        moveCustomersTmp.setMoveCard(REJECT);
                    } else if (codCurBrn.equals(0)) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                        moveCustomersTmp.setStatusReason(REJECT + ", [cod_cur_brn is null]");
                        moveCustomersTmp.setMoveCard(REJECT);
                    } else if (codXferBrn.equals(0)) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                        moveCustomersTmp.setStatusReason(REJECT + ", [cod_xfer_brn is null]");
                        moveCustomersTmp.setMoveCard(REJECT);
                    } else {
                        // area ba_move_customer
                try {
                            BaCcBrnMastPK baCcBrnMastPK = new BaCcBrnMastPK();
                            baCcBrnMastPK.setCodccBrn(codXferBrn);
                            BaCcBrnMast baCcBrnMast = baccBrnMastDAO.getBranch(baCcBrnMastPK);
                        String namBranch = baCcBrnMast.getNamBranch();
                        try { // data already exists
                    moveCustomers = baMoveCustomersDao.get(baMoveCustomersPK);
                    if (!moveCustomers.equals(check)) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                                    moveCustomersTmp.setStatusReason(REJECT + ", [" + EXISTED + "]");
                        moveCustomersTmp.setMoveCard(REJECT);
                    }
  				} catch (Exception e) { // insert ba_move_customer
                    try {                
                        CiCustMastDao ciCustMastDao = new CiCustMastDao(session);
                        CiCustMastPK ciCustMastPK = new CiCustMastPK();
                        ciCustMastPK.setCodCustId(codCustid);
                                    ciCustMastPK.setCodEntityVpd(11);
                        ciCustMastPK.setFlgMntStatus("A");
                        CiCustMast ciCustMast = ciCustMastDao.get(ciCustMastPK);
                                    Integer tempCodCcHomeBrn = codCurBrn;
                                    codCurBrn = ciCustMast.getCodCcHomeBrn();
                                    if (codCurBrn.equals(tempCodCcHomeBrn)) {
                        moveCustomers = new BaMoveCustomers();
                        moveCustomers.setBaMoveCustomersPK(baMoveCustomersPK);
                        moveCustomers.setCodCcBrn(codCurBrn);
                        moveCustomers.setCodXferBrn(codXferBrn);
                        moveCustomers.setDatLastMnt(SchedulerUtil.getTime());
                        moveCustomers.setCodMntAction("");
                        moveCustomers.setFlagProcess("P");
                        moveCustomers.setFlagBranch("Y");
                        moveCustomers.setCodLastMntMakerId("SETUP");
                        moveCustomers.setCodLastMntChkrId("SETUP");
                        moveCustomers.setCtrUpdtSrlNo(0);
                        baMoveCustomersDao.insert(moveCustomers);
                        moveCustomersTmp.setStatusReason(DONE);
                        moveCustomersTmp.setFlagStatus(StatusDefinition.DONE);
                        // update cm_custcard_mast
                        try {
							CmCustcardMastPK cmCustcardMastPK = new CmCustcardMastPK();
                            cmCustcardMastPK.setCodCustId(codCustid);
                            cmCustcardMastPK.setFlgMntStatus("A");
                                        cmCustcardMastPK.setCodEntityVpd(11);
                            CmCustcardMast cmCustcardMast = cmCustcardMastDao.get(cmCustcardMastPK);
                                        cmCustcardMast.setCodCcHomeBrn(codXferBrn);
                            cmCustcardMast.setDatLastMnt(SchedulerUtil.getTime());
                                            cmCustcardMast.setCodLastMntMakerid("SETUP");
                                            cmCustcardMast.setCodLastMntChkrid("SETUP");
                            cmCustcardMastDao.update(cmCustcardMast);
                            moveCustomersTmp.setMoveCard(DONE);
                        } catch (Exception exc) {
                            moveCustomersTmp.setMoveCard("NOT HAVE");
                                    } // end update cm custcard_mast
                                    } else {
                                        moveCustomersTmp.setStatusReason(REJECT+", [cod_cur_brn invalid]");
                                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                                        moveCustomersTmp.setMoveCard(REJECT);
                                    }
				} catch (Exception ex) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                                    moveCustomersTmp.setStatusReason("REJECT, [" + NOTFOUND+"]");
                        moveCustomersTmp.setMoveCard("REJECT");
                    }
                        } // end insert ba_move_customers
                    } catch (Exception e) {
                        moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                            moveCustomersTmp.setStatusReason(REJECT + ", [branch destination invalid]");
                        moveCustomersTmp.setMoveCard(REJECT);
                    } // end validasi branch destination
                }
                } // end try load

            } catch (Exception e) {
                this.getLogger().info("failed : "+e);
            }
            this.getLogger().info("End Proccess");
                       
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
            fixQXtract.setParam6(BatchId);
            this.getLogger().info("Register FixQtract2 Done ");
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            this.getLogger().info("Status : REJECTED");
            this.getLogger().info("Register FixQXtract");
            // tmpMoveCustomerDao.runReject(BatchID)
            try {
                List<TmpMoveCustomers> tmcs = tmpMoveCustomersDao.getBybatchNo(BatchId);
                for (TmpMoveCustomers moveCustomersTmp : tmcs) {
                    moveCustomersTmp.setFlagStatus(StatusDefinition.REJECT);
                    moveCustomersTmp.setStatusReason("[reject by supervisor]");
                    moveCustomersTmp.setMoveCard("[reject by supervisor]");
                }
            } catch (Exception e) {
                this.getLogger().info("Try Catch [reject by supervisor] Error :"+e);
            }
            
            fixQXtract = new FixQXtract();
            fClassConfig = fixClassConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
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
            this.getLogger().info("Register FixQXtract");
            return true;
        } else {
            this.getLogger().info("Status : IGNORED");
            // Maybe Ignored
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
            return true;
        }

    }

    private void readExcel(String param5, String configFile, TmpMoveCustomers tmpMoveCustomers, TmpMoveCustomersDao tmpMoveCustomersDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
        loadConfig(configFile);
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, tmpMoveCustomers, tmpMoveCustomersDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, tmpMoveCustomers, tmpMoveCustomersDao);
        }
    }

    private void readXLS(String param5, TmpMoveCustomers tmpMoveCustomers, TmpMoveCustomersDao tmpMoveCustomersDao) throws FileNotFoundException, IOException, ParseException {
        BaccBrnMastDAO baccBrnMastDAO = new BaccBrnMastDAO(session);
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                int valid = 0;
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }

                int j = cellDataStartOn - rowNumCell;

                tmpMoveCustomers = new TmpMoveCustomers();
                tmpMoveCustomers.setBatchNo(context.get(MapKey.batchNo).toString());
                try {
                tmpMoveCustomers.setCodCustId(Integer.valueOf(getStringCellVal(row.getCell(j))));
                    valid++;
                } catch (Exception e) {
                    tmpMoveCustomers.setCodCustId(0);
                }
                try {
                tmpMoveCustomers.setCodCurBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 1))));
                    valid++;
                } catch (Exception e) {
                    tmpMoveCustomers.setCodCurBrn(0);
                }
                try {
                    Integer checkBrn = Integer.valueOf(getStringCellVal(row.getCell(j + 2)));
                    try {
                        BaCcBrnMastPK baCcBrnMastPK = new BaCcBrnMastPK();
                        baCcBrnMastPK.setCodccBrn(checkBrn);
                        BaCcBrnMast baCcBrnMast = baccBrnMastDAO.getBranch(baCcBrnMastPK);
                        Integer codXferBrn = baCcBrnMast.getCompositeId().getCodccBrn();
                        tmpMoveCustomers.setCodXferBrn(codXferBrn);
                    valid++;
                } catch (Exception e) {
                        tmpMoveCustomers.setCodXferBrn(checkBrn);
                    }
                } catch (Exception e) {
                    tmpMoveCustomers.setCodXferBrn(0);
                }
                if (valid == 3) {
                tmpMoveCustomers.setFlagStatus(StatusDefinition.UNAUTHORIZED);
                } else {
                    tmpMoveCustomers.setFlagStatus(StatusDefinition.REJECT);
                }
                if (valid > 1) {
                tmpMoveCustomersDao.insert(tmpMoveCustomers);
                }
                getLogger().info("Insert data from excel to temporary table Succes, valid : " + valid);
                }
        } finally {
            file.close();
        }
    }

    private void readXLSX(String param5, TmpMoveCustomers tmpMoveCustomers, TmpMoveCustomersDao tmpMoveCustomersDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        BaccBrnMastDAO baccBrnMastDAO = new BaccBrnMastDAO(session);
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(sheetData - 1);

            if (rowCnt == 0) {
                rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
            }
            for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
                Row row = sheet.getRow(i);
                int valid = 0;
                if (columnCnt == 0) {
                    columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
                }

                int j = cellDataStartOn - rowNumCell;

                tmpMoveCustomers = new TmpMoveCustomers();
                tmpMoveCustomers.setBatchNo(context.get(MapKey.batchNo).toString());
                try {
                tmpMoveCustomers.setCodCustId(Integer.valueOf(getStringCellVal(row.getCell(j))));
                    valid++;
                } catch (Exception e) {
                    tmpMoveCustomers.setCodCustId(0);
                }
                try {
                tmpMoveCustomers.setCodCurBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 1))));
                    valid++;
                } catch (Exception e) {
                    tmpMoveCustomers.setCodCurBrn(0);
                }
                try {
                    Integer checkBrn = Integer.valueOf(getStringCellVal(row.getCell(j + 2)));
                    try {
                        BaCcBrnMastPK baCcBrnMastPK = new BaCcBrnMastPK();
                        baCcBrnMastPK.setCodccBrn(checkBrn);
                        BaCcBrnMast baCcBrnMast = baccBrnMastDAO.getBranch(baCcBrnMastPK);
                        Integer codXferBrn = baCcBrnMast.getCompositeId().getCodccBrn();
                        tmpMoveCustomers.setCodXferBrn(codXferBrn);
                    valid++;
                } catch (Exception e) {
                        tmpMoveCustomers.setCodXferBrn(checkBrn);
                    }
                } catch (Exception e) {
                    tmpMoveCustomers.setCodXferBrn(0);
                }
                if (valid == 3) {
                tmpMoveCustomers.setFlagStatus(StatusDefinition.UNAUTHORIZED);
                } else {
                    tmpMoveCustomers.setFlagStatus(StatusDefinition.REJECT);
                }
                if (valid > 1) {
                tmpMoveCustomersDao.insert(tmpMoveCustomers);
            }
            }
        } finally {
            file.close();
        }
    }

    private void loadConfig(String confFile) throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        InputStream in = MoveCustomersWorker.class.getClassLoader().getResourceAsStream(confFile);
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
        } else {
            int cellNol = 0;
            ret = String.valueOf(cellNol);
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
}
