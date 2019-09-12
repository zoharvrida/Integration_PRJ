/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.fcr.model.*;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpMoveAccountsDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpMoveAccounts;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaCollHdrDAO;
import bdsmhost.fcr.dao.BaHollAcctXrefDAO;
import bdsmhost.fcr.dao.BaMoveAccountsDao;
import bdsmhost.fcr.dao.BaProdBrnXrefDAO;
import bdsmhost.fcr.dao.BaccBrnMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.ChRdInsurDetailDAO;
import bdsmhost.fcr.dao.CiCustMastDao;
import bdsmhost.fcr.dao.LnAcctDtlsDAO;
import bdsmhost.fcr.dao.TdAcctMastDao;
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
public class MoveAccountsWorker extends BaseProcessor {

	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	private static final String invalidDate = "Invalid date in file name";
	private static final String EXISTED = "[data already exists]";
	private static final String NOTFOUND = "[data not found]";
	private static final String REJECT = "REJECT";
	private static final String NH = "NOT HAVE";
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
			+ "Process Move Accounts has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String emailRejected = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process to Move Accounts has been Rejected by Supervisor. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";

    /**
     * 
     * @param context
     */
    public MoveAccountsWorker(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String configFile = "excelutil_moveaccounts.properties";
		TmpMoveAccountsDao tmpMoveAccountsDao = new TmpMoveAccountsDao(session);
		FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
		FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
		FixClassConfig fClassConfig;
		TmpMoveAccounts tmpMoveAccounts = null;
		FixSchedulerXtract fSchedulerXtract;
		String extFile;
		String BatchId = context.get(MapKey.batchNo).toString();
		String status = context.get(MapKey.status).toString();
		String param1 = context.get(MapKey.param1).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());

		getLogger().info("Done Prepare Before Execute Status U/A");
		if (status.equals(StatusDefinition.UNAUTHORIZED)) {
			this.getLogger().info("Status : UNAUTHORIZED");
			String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(7)) + "{hhmmss}.xls");
			getLogger().info("Out File Name : " + outFileName);
			String param5 = context.get(MapKey.param5).toString();
			getLogger().info("Param 5 : " + param5);

            /* VALIDASI DATE */
			FcrBaBankMastDao baBankMaster = new FcrBaBankMastDao(session);
			String tempDate = baBankMaster.get().getDatProcess().toString();
			String dates = outFileName.substring(6, 14);
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
			Date tempDatey = simpledateformat.parse(dates);
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dProcess = outputDateFormat.format(tempDatey);
			if (dProcess.compareTo(tempDate) != 0) {
				throw new FIXException(invalidDate); //END VALIDASI
			}
			// Validasi Running Day Scheduler
			boolean runToday = false;
			int existsData = tmpMoveAccountsDao.countTable();
            int maxData = PropertyPersister.quotaMax;
			String today = bdsm.scheduler.util.SchedulerUtil.getDate("EEEE");
			List paramScheduler = bdsm.scheduler.util.SchedulerUtil.getParameter(PropertyPersister.schedulerDay, ";");
			String listDay = PropertyPersister.schedulerDay;
			String days = listDay.replaceAll(";", ",");
			for (int i = 0; i < paramScheduler.size(); i++) {
				String schedulerDay = paramScheduler.get(i).toString();
				if (today.equals(schedulerDay)) {
					runToday = true;
				}
			}
			//body email
			String emailRejectDay = "Dear Sir/Madam,<br/>"
					+ "<br/>"
					+ "Your requested to move accounts can't be process, because today is not scheduled upload<br/>"
					+ "day scheduler upload : " + days + ".<br>"
					+ "<br/>"
					+ "Thanks & regards,<br/>"
					+ "- BDSM -";
			String emailRejectkuota = "Dear Sir/Madam,<br/>"
					+ "<br/>"
					+ "Your requested to move accounts can't be process, maximal upload today " + maxData + " data.<br/>"
					+ "existed upload " + existsData + " data + your upload data now, so exceeds maximal upload today.<br/>"
					+ "<br/>"
					+ "Thanks & regards,<br/>"
					+ "- BDSM -";
			this.getLogger().info("run : " + runToday + " today : " + today + " scheduler : " + days);
			if (runToday) {// cek scheduler running
				boolean runMBM = readExcel(param5, configFile, tmpMoveAccounts, tmpMoveAccountsDao); // read excel
				this.getLogger().info("Import File Excel From Requestor Succes");
				if (runMBM) {// cek melebihi data max upload
					fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
					idScheduler = fClassConfig.getIdScheduler();
					this.getLogger().info("Getting Id Scheduler " + idScheduler + " for Source : " + sourceProcess + " and status " + StatusDefinition.UNAUTHORIZED + " done");
					fSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
					extFile = fSchedulerXtract.getFileFormat();
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
						fixQXtract.setParam6(BatchId);
						this.getLogger().info("Register Fixqxtract Done");
					}
				} else {// melebihi max upload hari ini
					fixQXtract = new FixQXtract();
					fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
					idScheduler = fClassConfig.getIdScheduler();
					fixQXtract.setIdScheduler(idScheduler);
					fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
					fixQXtract.setDtmRequest(SchedulerUtil.getTime());
					fixQXtract.setParam1("RE: " + param1);
					FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
					fixQXtract.setParam2(fixEmailAccessDao.getReq(context.get(MapKey.grpId).toString()));
					fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
					fixQXtract.setParam4(emailRejectkuota);
					fixQXtract.setParam5("");
				}
			} else { // bukan hari scheduler upload
				fixQXtract = new FixQXtract();
				fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
				idScheduler = fClassConfig.getIdScheduler();
				fixQXtract.setIdScheduler(idScheduler);
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				fixQXtract.setParam1("RE: " + param1);
				FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
				fixQXtract.setParam2(fixEmailAccessDao.getReq(context.get(MapKey.grpId).toString()));
				fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
				fixQXtract.setParam4(emailRejectDay);
				fixQXtract.setParam5("");
			}

			return true;

		} else if (status.equals(StatusDefinition.AUTHORIZED)) {
			this.getLogger().info("Status : Authorizhed");
			String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(11)) + "{hhmmss}.xls");
			getLogger().info("Register FixQXtract");
			fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
			idScheduler = fClassConfig.getIdScheduler();
			this.getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: " + sourceProcess + " and Status : " + StatusDefinition.AUTHORIZED + " DONE");
			fSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
			extFile = fSchedulerXtract.getFileFormat();
			out2FileName = out2FileName.replace(".xls", "." + extFile);
			getLogger().info("Getting file Extention DONE : " + out2FileName);

			//INSERT INTO BA_MOVE_ACCOUNTS
			try {
				List<TmpMoveAccounts> tmas = tmpMoveAccountsDao.getByBatchNo(BatchId);
				this.getLogger().info("START PROCESS");
				for (TmpMoveAccounts moveAccountsTmp : tmas) {
					String codAcctNo = moveAccountsTmp.getCodAcctNo();
					String codModule = moveAccountsTmp.getCodModule();
					Integer codCurBrn = moveAccountsTmp.getCodCurBrn();
					Integer codProd = moveAccountsTmp.getCodProd();
					Integer codXferBrn = moveAccountsTmp.getCodXferBrn();
					BaMoveAccountsPK baMoveAccountsPK;
					BaMoveAccounts moveAccounts;
					BaMoveAccountsDao baMoveAccountsDao = new BaMoveAccountsDao(session);
					ChAcctMastDAO chAcctMastDAO = new ChAcctMastDAO(session);
					LnAcctDtlsDAO lnAcctDtlsDAO = new LnAcctDtlsDAO(session);
					TdAcctMastDao tdAcctMastDao = new TdAcctMastDao(session);
					BaBankMastDAO bankMastDAO = new BaBankMastDAO(session);
					BaccBrnMastDAO baccBrnMastDAO = new BaccBrnMastDAO(session);
					Date dateProcess = bankMastDAO.get().getDatProcess();
					dateFormatter.format(dateProcess);
					Object obj = null;
					//VALIDASI DATA NULL
					try {
						if (codAcctNo.isEmpty()) {
							moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
						}
						try {
							if (codModule.isEmpty()) {
								moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
							}
							if (codProd.equals(0)) {
								moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
								moveAccountsTmp.setStatusReason(REJECT + ", [cod_prod is null]");
								moveAccountsTmp.setMoveRdAccounts(REJECT);
								moveAccountsTmp.setMoveCollateral(REJECT);
							} else if (codCurBrn.equals(0)) {
								moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
								moveAccountsTmp.setStatusReason(REJECT + ", [cod_cur_brn is null]");
								moveAccountsTmp.setMoveRdAccounts(REJECT);
								moveAccountsTmp.setMoveCollateral(REJECT);
							} else if (codXferBrn.equals(0)) {
								moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
								moveAccountsTmp.setStatusReason(REJECT + ", [cod_xfer_brn is null]");
								moveAccountsTmp.setMoveRdAccounts(REJECT);
								moveAccountsTmp.setMoveCollateral(REJECT);

							} else { // END VALIDASI DATA NULL
								// VALIDASI BRANCH AND START INSERT BA_MOVE_ACCOUNT
								try {
									BaCcBrnMastPK baCcBrnMastPK = new BaCcBrnMastPK();
									baCcBrnMastPK.setCodccBrn(codXferBrn);
									BaCcBrnMast baCcBrnMast = baccBrnMastDAO.getBranch(baCcBrnMastPK);
									String namBranch = baCcBrnMast.getNamBranch();
									String conditionModule = codModule.toUpperCase();
									//Validasi Branch Product
									BaProdBrnXrefDAO baProdBrnXrefDAO = new BaProdBrnXrefDAO(session);
									List<BaProdBrnXref> branchProduct = baProdBrnXrefDAO.getBranchProd(codXferBrn, codProd, codModule);
									if (branchProduct.isEmpty()) {
										moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
										moveAccountsTmp.setStatusReason(REJECT + ", [branch destination not have cod_prod : " + codProd + "]");
										moveAccountsTmp.setMoveRdAccounts(REJECT);
										moveAccountsTmp.setMoveCollateral(REJECT);
									} else {
										if (conditionModule.equalsIgnoreCase("CH")) {
											baMoveAccountsPK = new BaMoveAccountsPK();
											baMoveAccountsPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
											baMoveAccountsPK.setFlgMntStatus("A");
											baMoveAccountsPK.setCodEntityVpd(11);

											try {
												moveAccounts = baMoveAccountsDao.get(baMoveAccountsPK);
												if (!moveAccounts.equals(obj)) {
													moveAccountsTmp.setStatusReason(REJECT + ", [" + EXISTED + "]");
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
												}

											} catch (Exception e) { // CH INSERT BA_MOVE_ACCOUNT
												try {
													ChAcctMastPK chAcctMastPK = new ChAcctMastPK();
													chAcctMastPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
													chAcctMastPK.setFlgMntStatus("A");
													chAcctMastPK.setCodEntityVpd(11);
													ChAcctMast chAcctMast = chAcctMastDAO.get(chAcctMastPK);
													Integer tempCodCcBrn = codCurBrn;
													Integer tempCodProd = codProd;
													codCurBrn = chAcctMast.getCodCcBrn();
													codProd = chAcctMast.getCodProd();
													Integer codCust = chAcctMast.getCodCust(); //cod_cust
													try { // get flg_cust_typ from ci_cust_mast for validasi cust_typ in branch destination
														CiCustMastDao ciCustMastDao = new CiCustMastDao(session);
														CiCustMastPK ciCustMastPK = new CiCustMastPK();
														ciCustMastPK.setCodCustId(codCust);
														ciCustMastPK.setCodEntityVpd(11);
														ciCustMastPK.setFlgMntStatus("A");
														CiCustMast ciCustMast = ciCustMastDao.get(ciCustMastPK);
														String codCustTyp = ciCustMast.getFlgCustTyp();
														branchProduct = baProdBrnXrefDAO.getBranchProdCH(codXferBrn, codProd, codModule, codCustTyp);
														if (branchProduct.isEmpty()) {
															moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
															moveAccountsTmp.setStatusReason(REJECT + ", [branch destination not have product type : '" + codCustTyp + "']");
															moveAccountsTmp.setMoveRdAccounts(REJECT);
															moveAccountsTmp.setMoveCollateral(REJECT);
														} else {
															if (codCurBrn.equals(tempCodCcBrn) && codProd.equals(tempCodProd)) {
																moveAccounts = new BaMoveAccounts();
																moveAccounts.setBaMoveAccountsPK(baMoveAccountsPK);
																moveAccounts.setCodModule(codModule);
																moveAccounts.setCodCcBrn(codCurBrn);
																moveAccounts.setCodXferBrn(codXferBrn);
																moveAccounts.setCodProd(codProd);
																moveAccounts.setDatProcess(dateProcess);
																moveAccounts.setDatLastMnt(SchedulerUtil.getTime());
																moveAccounts.setCodLastMntMakerid("SETUP");
																moveAccounts.setCodLastMntChkrid("SETUP");
																moveAccounts.setFlgProcess("P");
																moveAccounts.setFlgBranch("N");
																moveAccounts.setCodMntAction("");
																moveAccounts.setCtrUpdSrlNo(0);
																baMoveAccountsDao.insert(moveAccounts);
																moveAccountsTmp.setFlagStatus(StatusDefinition.DONE);
																moveAccountsTmp.setStatusReason(DONE);

																//UPDATE CH_RD_INSUR_DETAIL
																try {
																	ChRdInsurDetailDAO insurDetailDAO = new ChRdInsurDetailDAO(session);
																	ChRdInsurDetailPK insurDetailPK = new ChRdInsurDetailPK();
																	insurDetailPK.setCodInsurAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
																	insurDetailPK.setFlgMntStatus("A");
																	insurDetailPK.setCodEntityVpd(11);
																	ChRdInsurDetail insurDetail = insurDetailDAO.get(insurDetailPK);
																	insurDetail.setCodCcBrn(codXferBrn);
																	insurDetail.setDatLastMnt(SchedulerUtil.getTime());
																	insurDetailDAO.update(insurDetail);
																	moveAccountsTmp.setMoveRdAccounts(DONE);
																} catch (Exception exc) {
																	moveAccountsTmp.setMoveRdAccounts(NH);
																} // END UPDATE RD INSUR DETAIL

																// UPDATE BA_COLL_HDR
																BaHollAcctXrefDAO xrefDAO = new BaHollAcctXrefDAO(session);
																List<BaHoCollAcctXref> xrefs = xrefDAO.listAcctXref(BdsmUtil.rightPad(codAcctNo, 16));
																if (xrefs.isEmpty()) {
																	moveAccountsTmp.setMoveCollateral(NH);
																} else {
																	BaCollHdrDAO baCollHdrDAO = new BaCollHdrDAO(session);
																	baCollHdrDAO.runProcessUpdate(BdsmUtil.rightPad(codAcctNo, 16), codXferBrn);
																	moveAccountsTmp.setMoveCollateral(DONE);
																} // END UPDATE BA_COLL_HDR
															} else {
																moveAccountsTmp.setMoveRdAccounts(REJECT);
																moveAccountsTmp.setMoveCollateral(REJECT);
																moveAccountsTmp.setStatusReason(REJECT + ", [cod_cur_brn/cod_prod invalid]");
																moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
															} //END IF BRANCH & PRODUCT SAME WITH CH_ACCT_MAST
														}
													} catch (Exception er) {
														moveAccountsTmp.setStatusReason(REJECT + ", [Customer Inactive]");
														moveAccountsTmp.setMoveRdAccounts(REJECT);
														moveAccountsTmp.setMoveCollateral(REJECT);
														moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
													} // VALIDASI CUSTOMER FROM CH_ACCT_MAST FOR BRANCH DESTINATION
												} catch (Exception ex) {
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
													moveAccountsTmp.setStatusReason(REJECT + ", [" + NOTFOUND + "]");
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
												}
											} // END INSERT BA_MOVE_ACCOUNT CH

										} else if (conditionModule.equalsIgnoreCase("LN")) {
											baMoveAccountsPK = new BaMoveAccountsPK();
											baMoveAccountsPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
											baMoveAccountsPK.setFlgMntStatus("A");
											baMoveAccountsPK.setCodEntityVpd(11);
											try {
												moveAccounts = baMoveAccountsDao.get(baMoveAccountsPK);
												if (!moveAccounts.equals(obj)) {
													moveAccountsTmp.setStatusReason(REJECT + ", [" + EXISTED + "]");
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
												}

											} catch (Exception e) {//INSERT LN BA_MOVE_ACCOUNT
												this.getLogger().info("------------AVAILABLE----------");
												try {
													LnAcctDtlsPK lnAcctDtlsPK = new LnAcctDtlsPK();
													lnAcctDtlsPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
													lnAcctDtlsPK.setCodEntityVpd(11);
													lnAcctDtlsPK.setFlgMntStatus("A");
													LnAcctDtls lnAcctDtls = lnAcctDtlsDAO.get(lnAcctDtlsPK);
													Integer tempCodCcBrn = codCurBrn;
													Integer tempCodProd = codProd;
													codCurBrn = lnAcctDtls.getCodCcBrn();
													codProd = lnAcctDtls.getCodProd();
													if (codCurBrn.equals(tempCodCcBrn) && codProd.equals(tempCodProd)) {
														baMoveAccountsDao = new BaMoveAccountsDao(session);
														moveAccounts = new BaMoveAccounts();
														moveAccounts.setBaMoveAccountsPK(baMoveAccountsPK);
														moveAccounts.setCodModule(codModule);
														moveAccounts.setCodCcBrn(codCurBrn);
														moveAccounts.setCodXferBrn(codXferBrn);
														moveAccounts.setCodProd(codProd);
														moveAccounts.setDatProcess(dateProcess);
														moveAccounts.setDatLastMnt(SchedulerUtil.getTime());
														moveAccounts.setCodLastMntMakerid("SETUP");
														moveAccounts.setCodLastMntChkrid("SETUP");
														moveAccounts.setCodMntAction("");
														moveAccounts.setFlgProcess("P");
														moveAccounts.setFlgBranch("N");
														moveAccounts.setCtrUpdSrlNo(0);
														moveAccountsTmp.setStatusReason(DONE);
														moveAccountsTmp.setMoveRdAccounts(NH);
														moveAccountsTmp.setFlagStatus(StatusDefinition.DONE);
														baMoveAccountsDao.insert(moveAccounts);

														// UPDATE BA_COLL_HDR
														BaHollAcctXrefDAO xrefDAO = new BaHollAcctXrefDAO(session);
														List<BaHoCollAcctXref> xrefs = xrefDAO.listAcctXref(BdsmUtil.rightPad(codAcctNo, 16));
														if (xrefs.isEmpty()) {
															moveAccountsTmp.setMoveCollateral(NH);
														} else {
															BaCollHdrDAO baCollHdrDAO = new BaCollHdrDAO(session);
															baCollHdrDAO.runProcessUpdate(BdsmUtil.rightPad(codAcctNo, 16), codXferBrn);
															moveAccountsTmp.setMoveCollateral(DONE);
														}//END UPDATE BA_COLL_HDR
													} else {
														moveAccountsTmp.setMoveRdAccounts(REJECT);
														moveAccountsTmp.setMoveCollateral(REJECT);
														moveAccountsTmp.setStatusReason(REJECT + ",  [cod_cur_brn/cod_prod, invalid]");
														moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
													}//END IF BRANCH & PRODUCT SAME WITH LN_ACCT_DETAILS
												} catch (Exception ex) {
													this.getLogger().debug(ex, ex);
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
													moveAccountsTmp.setStatusReason(REJECT + ", [" + NOTFOUND + "]");
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
												}
											} //END INSERT BA_MOVE_ACCOUNT LN

										} else if (conditionModule.equalsIgnoreCase("TD")) {
											baMoveAccountsPK = new BaMoveAccountsPK();
											baMoveAccountsPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
											baMoveAccountsPK.setFlgMntStatus("A");
											baMoveAccountsPK.setCodEntityVpd(11);
											try {
												moveAccounts = baMoveAccountsDao.get(baMoveAccountsPK);
												if (!moveAccounts.equals(obj)) {
													moveAccountsTmp.setStatusReason(REJECT + ", [" + EXISTED + "]");
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
												}
											} catch (Exception e) {// INSERT TD BA_MOVE_ACCOUNT
												try {
													TdAcctMastPK tdAcctMastPK = new TdAcctMastPK();
													tdAcctMastPK.setCodAcctNo(BdsmUtil.rightPad(codAcctNo, 16));
													tdAcctMastPK.setCodEntityVpd(11);
													tdAcctMastPK.setFlgMntStatus("A");
													TdAcctMast tdAcctMast = tdAcctMastDao.get(tdAcctMastPK);
													Integer tempCodCcBrn = codCurBrn;
													Integer tempCodProd = codProd;
													codCurBrn = tdAcctMast.getCodCcBrn();
													codProd = tdAcctMast.getCodProd();
													if (codCurBrn.equals(tempCodCcBrn) && codProd.equals(tempCodProd)) {
														baMoveAccountsDao = new BaMoveAccountsDao(session);
														moveAccounts = new BaMoveAccounts();
														moveAccounts.setBaMoveAccountsPK(baMoveAccountsPK);
														moveAccounts.setCodModule(codModule);
														moveAccounts.setCodCcBrn(codCurBrn);
														moveAccounts.setCodXferBrn(codXferBrn);
														moveAccounts.setCodProd(codProd);
														moveAccounts.setDatProcess(dateProcess);
														moveAccounts.setDatLastMnt(SchedulerUtil.getTime());
														moveAccounts.setCodLastMntMakerid("SETUP");
														moveAccounts.setCodLastMntChkrid("SETUP");
														moveAccounts.setFlgProcess("P");
														moveAccounts.setFlgBranch("N");
														moveAccounts.setCodMntAction("");
														moveAccounts.setCtrUpdSrlNo(0);
														baMoveAccountsDao.insert(moveAccounts);
														moveAccountsTmp.setFlagStatus(StatusDefinition.DONE);
														moveAccountsTmp.setStatusReason(DONE);
														moveAccountsTmp.setMoveRdAccounts(NH);
														moveAccountsTmp.setMoveCollateral(NH);
													} else {
														moveAccountsTmp.setMoveRdAccounts(REJECT);
														moveAccountsTmp.setMoveCollateral(REJECT);
														moveAccountsTmp.setStatusReason(REJECT + ", [cod_cur_brn/cod_prod, invalid]");
														moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
													}//END IF BRANCH & PRODUCT SAME WITH TD_ACCT_MAST

												} catch (Exception ex) {
													moveAccountsTmp.setStatusReason(REJECT + ", [" + NOTFOUND + "]");
													moveAccountsTmp.setMoveRdAccounts(REJECT);
													moveAccountsTmp.setMoveCollateral(REJECT);
													moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
												}
											} //END INSERT BA_MOVE_ACCOUNT TD

										} else {
											moveAccountsTmp.setStatusReason(REJECT + ", ' cod_module invalid '");
											moveAccountsTmp.setMoveRdAccounts(REJECT);
											moveAccountsTmp.setMoveCollateral(REJECT);
											moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
										} // END CHECK COD MODULE
									} // END CHECK COD_PROD IN COD_XFER_BRN
								} catch (Exception e) {
									moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
									moveAccountsTmp.setStatusReason(REJECT + ", [branch destination not found or inactived]");
									moveAccountsTmp.setMoveRdAccounts(REJECT);
									moveAccountsTmp.setMoveCollateral(REJECT);
								} // END VALIDASI BRANCH NOT FOUND
							} // END INSERT BA_MOVE_ACCOUNTS
						} catch (Exception e) {
							moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
							moveAccountsTmp.setStatusReason(REJECT + ", [cod_module is null]");
							moveAccountsTmp.setMoveRdAccounts(REJECT);
							moveAccountsTmp.setMoveCollateral(REJECT);
						}// END VALIDASI MODULE NULL
					} catch (Exception e) {
						moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
						moveAccountsTmp.setStatusReason(REJECT + ", [cod_acct_no is null]");
						moveAccountsTmp.setMoveRdAccounts(REJECT);
						moveAccountsTmp.setMoveCollateral(REJECT);
					}// END VALIDASI ACCOUNT NULL
				} // END FOR LOAD TEMPORARY
			} catch (Exception e) {
				this.getLogger().info("FAILED  : " + e);
			}
			this.getLogger().info("END PROCCESS");

			fixQXtract = new FixQXtract();
			fixQXtract.setIdScheduler(idScheduler);
			fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			fixQXtract.setParam1(param1);
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			if (!context.get(MapKey.itemIdLink).toString().equals("")) {
				fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
			}
			if (context.get(MapKey.spvAuth).toString().equals("N")) {
				fixQXtract.setParam2(context.get(MapKey.param2).toString());
			}
			FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
			fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
			fixQXtract.setParam4(this.emailDone);
			fixQXtract.setParam5(out2FileName);
			fixQXtract.setParam6(BatchId);
			this.getLogger().info("Register FixQXtract2 Done");
			return true;

		} else if (status.equals(StatusDefinition.REJECTED)) {
			this.getLogger().info("Status : REJECTED");
			this.getLogger().info("Register FixQXtract");
			try {
				List<TmpMoveAccounts> tmas = tmpMoveAccountsDao.getByBatchNo(BatchId);
				for (TmpMoveAccounts moveAccountsTmp : tmas) {
					moveAccountsTmp.setFlagStatus(StatusDefinition.REJECT);
					moveAccountsTmp.setStatusReason("[reject by supervisor]");
					moveAccountsTmp.setMoveRdAccounts("[reject by supervisor]");
					moveAccountsTmp.setMoveCollateral("[reject by supervisor]");
				}
			} catch (Exception e) {
				this.getLogger().info("Try Catch [reject by supervisor] Error : " + e);
			}

			fixQXtract = new FixQXtract();
			fClassConfig = classConfigDao.get(this.getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
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
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
			fixInbox.setFlgProcess(StatusDefinition.IGNORED);
			fixInboxDao.update(fixInbox);
			return true;
		}

	}

	private boolean readExcel(String param5, String configFile, TmpMoveAccounts tmpMoveAccounts, TmpMoveAccountsDao tmpMoveAccountsDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
		loadConfig(configFile);
		boolean result = true;
		if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
			result = readXLS(param5, tmpMoveAccounts, tmpMoveAccountsDao);
		} else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
			result = readXLSX(param5, tmpMoveAccounts, tmpMoveAccountsDao);
		}
		return result;
	}

	private boolean readXLS(String param5, TmpMoveAccounts tmpMoveAccounts, TmpMoveAccountsDao tmpMoveAccountsDao) throws FileNotFoundException, IOException, ParseException {
		BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
		boolean pas = true;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(sheetData - 1);
            int maxData = PropertyPersister.quotaMax; // maksimal kuota dari parameter
			int existData = tmpMoveAccountsDao.countTable(); // jumlah data dari tmp_move_accounts
			int count = 0;//jumlah upload data

			if (rowCnt == 0) {
				rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
			}
			for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
				Row row = sheet.getRow(i);
				int checkData = 0;
				if (columnCnt == 0) {
					columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
				}
				int j = cellDataStartOn - rowNumCell;

				if (getStringCellVal(row.getCell(j)).isEmpty() && getStringCellVal(row.getCell(j + 1)).isEmpty()) {
					checkData++;
				}
				if (checkData > 0) {
					count -= 1;
				}
				count += 1;
			}
			this.getLogger().info("[XLS] Max Data : " + maxData + ",  Exists Data Done : " + existData + ",  Upload Data : " + count);
			if (count + existData <= maxData) {
				int i = rowDataStartOn - headerRow;
				while (i < rowCnt - headerRow) {
					Row row = sheet.getRow(i);
					int checkData = 0;

					if (columnCnt == 0) {
						columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
					}
					int j = cellDataStartOn - rowNumCell;

					tmpMoveAccounts = new TmpMoveAccounts();
					tmpMoveAccounts.setBatchNo(context.get(MapKey.batchNo).toString());
					if (!getStringCellVal(row.getCell(j)).isEmpty()) {
						tmpMoveAccounts.setCodAcctNo(getStringCellVal(row.getCell(j)));
					} else {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData += 2;
					}
					if (!getStringCellVal(row.getCell(j + 1)).isEmpty()) {
						tmpMoveAccounts.setCodModule(getStringCellVal(row.getCell(j + 1)));
					} else {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodProd(Integer.valueOf(getStringCellVal(row.getCell(j + 2))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodProd(0);
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodCurBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 3))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodCurBrn(0);
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodXferBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 4))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodXferBrn(0);
						checkData++;
					}
					if (checkData < 2) {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.UNAUTHORIZED);
					}
					if (checkData < 3) {
						tmpMoveAccountsDao.insert(tmpMoveAccounts);
					}
					i++;
				}
			} else {
				this.getLogger().info("upload + existed upload today melebihi kapasitas : " + maxData);
				pas = false;
			}
		} catch (Exception e) {
			this.getLogger().info("stop : " + e);
		} finally {
			file.close();
		}
		return pas;
	}

	private boolean readXLSX(String param5, TmpMoveAccounts tmpMoveAccounts, TmpMoveAccountsDao tmpMoveAccountsDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
		BufferedInputStream file = new BufferedInputStream(new FileInputStream(new File(param5)));
		boolean pas = true;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(sheetData - 1);
            int maxData = PropertyPersister.quotaMax; // maksimal kuota dari parameter
			int existData = tmpMoveAccountsDao.countTable(); // jumlah data dari tmp_move_accounts
			int count = 0;//jumlah upload data

			if (rowCnt == 0) {
				rowCnt = sheet.getPhysicalNumberOfRows() + rowDataStartOn - headerRow;
			}
			for (int i = rowDataStartOn - headerRow; i < rowCnt - headerRow; i++) {
				Row row = sheet.getRow(i);
				int checkData = 0;
				if (columnCnt == 0) {
					columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
				}
				int j = cellDataStartOn - rowNumCell;

				if (getStringCellVal(row.getCell(j)).isEmpty() && getStringCellVal(row.getCell(j + 1)).isEmpty()) {
					checkData++;
				}
				if (checkData > 0) {
					count -= 1;
				}
				count += 1;
			}
			this.getLogger().info("[XLSX] Max Data : " + maxData + ",  Exists Data Done : " + existData + ",  Upload Data : " + count);
			if (count + existData <= maxData) {
				int i = rowDataStartOn - headerRow;
				while (i < rowCnt - headerRow) {
					Row row = sheet.getRow(i);
					int checkData = 0;

					if (columnCnt == 0) {
						columnCnt = row.getPhysicalNumberOfCells() + cellDataStartOn - rowNumCell;
					}
					int j = cellDataStartOn - rowNumCell;

					tmpMoveAccounts = new TmpMoveAccounts();
					tmpMoveAccounts.setBatchNo(context.get(MapKey.batchNo).toString());
					if (!getStringCellVal(row.getCell(j)).isEmpty()) {
						tmpMoveAccounts.setCodAcctNo(getStringCellVal(row.getCell(j)));
					} else {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData += 2;
					}
					if (!getStringCellVal(row.getCell(j + 1)).isEmpty()) {
						tmpMoveAccounts.setCodModule(getStringCellVal(row.getCell(j + 1)));
					} else {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodProd(Integer.valueOf(getStringCellVal(row.getCell(j + 2))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodProd(0);
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodCurBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 3))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodCurBrn(0);
						tmpMoveAccounts.setFlagStatus(StatusDefinition.REJECT);
						checkData++;
					}
					try {
						tmpMoveAccounts.setCodXferBrn(Integer.valueOf(getStringCellVal(row.getCell(j + 4))));
					} catch (Exception e) {
						tmpMoveAccounts.setCodXferBrn(0);
						checkData++;
					}
					if (checkData < 2) {
						tmpMoveAccounts.setFlagStatus(StatusDefinition.UNAUTHORIZED);
					}
					if (checkData < 3) {
						tmpMoveAccountsDao.insert(tmpMoveAccounts);
					}
					i++;
				}
			} else {
				pas = false;
			}
		} catch (Exception e) {
			this.getLogger().info("stop : " + e);
		} finally {
			file.close();
		}
		return pas;
	}

	private void loadConfig(String confFile) throws FileNotFoundException, IOException {

		Properties properties = new Properties();
		InputStream in = MoveAccountsWorker.class.getClassLoader().getResourceAsStream(confFile);
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
}
