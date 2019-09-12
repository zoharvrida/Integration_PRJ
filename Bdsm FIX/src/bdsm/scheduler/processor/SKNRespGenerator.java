/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import java.util.HashMap;
import java.util.Map;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.ErrorCodeDao;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.StringTokenizer;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public abstract class SKNRespGenerator extends BaseProcessor {

    /**
     * 
     */
    protected String namTemplate;
    /**
     * 
     */
    protected String BatchID;
    /**
     * 
     */
    protected Integer checknumHeader;
    /**
     * 
     */
    protected Integer checknumDetails;
    /**
     * 
     */
    protected Integer checknumFooter;
    /**
     * 
     */
    protected Integer checknumHeaderFooter;
    /**
     * 
     */
    protected String delimiter;
    /**
     * 
     */
    protected String reason;
    /**
     * 
     */
    protected String ErrValue;
    /**
     * 
     */
    protected StringBuilder CrcCheck = new StringBuilder();
    /**
     * 
     */
    protected Integer CheckSum;

    /**
     * 
     * @param context
     */
    public SKNRespGenerator(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     */
    public abstract Integer checkNumHeader();

    /**
     * 
     * @param count
     * @return
     */
    public abstract Integer checkNumDetail(Integer count);

    /**
     * 
     * @return
     */
    public abstract Integer checkHeaderFooterSum();

    /**
     * 
     * @return
     */
    public abstract Integer checkNumFooter();

    /**
     * 
     * @param context
     * @return
     */
    public abstract boolean validate(Map context);

    /**
     * 
     * @param count
     * @return
     */
    public abstract StringBuilder headerS(Integer count);

    /**
     * 
     * @param count
     * @return
     */
    public abstract StringBuilder detailS(Integer count);

    /**
     * 
     * @param count
     * @return
     */
    public abstract StringBuilder footerS(Integer count);
	//public abstract List getList();

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public boolean doExecute() throws Exception {
		HashMap cont = new HashMap();
		Integer loopCount = 0;
		Integer k;
		String outputFile;
		try {

			ErrorCodeDao errDao = new ErrorCodeDao(session);
            SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
            String dateTime = null;
			FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		try {
			dateTime = sdf.format(fcrDao.get().getDatProcess()).toString();
			//dateTime  = SchedulerUtil.getDate("yyMMdd");
		} catch (Exception ex) {
			Logger.getLogger(SKNTXTmapping.class.getName()).log(Level.SEVERE, null, ex);
		}
            StringBuilder batchNo = new StringBuilder();
			batchNo.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')).append(dateTime).append(context.get(MapKey.param6));
			
			errDao.cleans(batchNo.toString());
			
			getLogger().info("Start Generate File Response" + context.get(MapKey.reportFileName));
			getLogger().debug("Get Parameter1 :" + context.get(MapKey.param1));
			getLogger().debug("Get Parameter2 :" + context.get(MapKey.param2));
			getLogger().debug("Get Parameter3 :" + context.get(MapKey.param3));
			getLogger().debug("Get Parameter4 :" + context.get(MapKey.param4));
			getLogger().debug("Get Parameter5 :" + context.get(MapKey.param5));
			getLogger().debug("Get parameter6 :" + context.get(MapKey.param6));
			getLogger().debug("Get Hidden Parameter 1 :" + context.get(MapKey.hdUserid));
			getLogger().debug("Get Hidden Parameter 2 :" + context.get(MapKey.hdcdBranch));
			getLogger().debug("Get Hidden Parameter 3 :" + context.get(MapKey.hddtProcess));
			getLogger().debug("Get Hidden Parameter 4 :" + context.get(MapKey.hdidTemplate));
			getLogger().debug("Get Hidden Parameter 5 :" + context.get(MapKey.hdnamUser));
			getLogger().debug("Get Java Class :" + context.get(MapKey.javaClass));
			getLogger().debug("Get Report ID  :" + context.get(MapKey.reportId));
			getLogger().debug("Get Report Format:" + context.get(MapKey.reportFormat));
			getLogger().debug("Get Type Fix :" + context.get(MapKey.typeFix));
			outputFile = context.get(MapKey.reportFileName).toString();
			//this.context.put(MapKey.batchNo, BatchID);
			//this.context.put(MapKey.templateName,namTemplate);
			StringBuilder HeaderS = new StringBuilder();
			StringBuilder FooterS = new StringBuilder();

			Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
			if (validate(context)) { // validation report
				getLogger().debug("ENTERING CHECKNUM CLASS");
				this.checknumHeader = checkNumHeader();
				getLogger().info("CHECK NUM HEADER :" + this.checknumHeader);
                if (this.checknumHeader.compareTo(0) == 0) { // don't have header
					this.checknumDetails = checkNumDetail(0);
					StringBuilder sbDetails = new StringBuilder();
					//listDetails = getList();
					if (this.checknumDetails != 0) {
						for (k = 1; k < this.checknumDetails + 1; k++) {
							getLogger().debug("Get Row details : " + k + " " + loopCount);

							sbDetails = detailS(loopCount + k);
							Integer jk = 0;
							if (!"".equalsIgnoreCase(sbDetails.toString())) {
								getLogger().debug("REASON :" + reason);
								if ("SINGLE".equals(reason)) {
									StringTokenizer token = new StringTokenizer(sbDetails.toString(), ",");
									while (token.hasMoreTokens()) {
										String Entry = token.nextToken();
										//getLogger().info("ENTRY :" + Entry);
										out.write(Entry);
										//getLogger().info("NEWLINE :" + jk);
										out.write(System.getProperty("line.separator"));
										jk++;
										if (jk == 10000) {
											loopCount = 0;
											out.flush();
										}
									}
									sbDetails.delete(0, sbDetails.length());
									jk = 0;
								} else {
									out.write(sbDetails.toString());
									//getLogger().info("NEWLINE :" + k);
									out.write(System.getProperty("line.separator"));
									sbDetails.delete(0, sbDetails.length());
								}
							}
							getLogger().debug("DETAILS :" + sbDetails.toString());

							if (loopCount == 10000) {
								loopCount = 0;
								out.flush();
							}
						}
					}
					this.checknumFooter = checkNumFooter();
					this.checknumHeaderFooter = checkHeaderFooterSum();
					if (this.checknumFooter != 0 && this.checknumHeaderFooter != 0) {
						getLogger().info("GETLIST OF 1ST PHASE : SINGLE FOOTER & NO HEADER");
						FooterS = footerS(1);
						out.write(FooterS.toString());
						out.write(System.getProperty("line.separator"));
						getLogger().info("GETLIST OF 2ND PHASE");
						//List listDetails = getList();
					} else if (this.checknumFooter != 0 && this.checknumHeaderFooter == 0) {
						getLogger().info("GETLIST OF 1ST PHASE : MULTIPLE FOOTER & NO HEADER");
						for (k = 1; k < this.checknumDetails + 1; k++) {
							getLogger().debug("Get Row details : " + k + " " + loopCount);

							FooterS = footerS(loopCount + k);
							out.write(FooterS.toString());
							out.write(System.getProperty("line.separator"));
							getLogger().info("GETLIST OF 2ND PHASE");

							FooterS.delete(0, sbDetails.length());

							if (loopCount == 10000) {
								loopCount = 0;
								out.flush();
							}
						}
					} else {
						out.write("DATA NOT FOUND!!");
					}
				} else {
					getLogger().info("MULTIPLE HEADER,DETAILS,FOOTER BEGIN");
					for (Integer i = 1; i < checknumHeader + 1; i++) {
						getLogger().info("GETLIST OF 1ST PHASE");
						HeaderS = headerS(i);
						if (!"".equalsIgnoreCase(HeaderS.toString())) {
							out.write(HeaderS.toString());
							out.write(System.getProperty("line.separator"));
						}
						getLogger().info("GETLIST OF 2ND PHASE");
						//List listDetails = getList();
						this.checknumDetails = checkNumDetail(i);
						StringBuilder sbDetails = new StringBuilder();
						//listDetails = getList();
						for (k = 1; k < this.checknumDetails + 1; k++) {
							getLogger().debug("Get Row details : " + k + " " + loopCount);

							sbDetails = detailS(loopCount + k);
							Integer jk = 0;
							if (!"".equalsIgnoreCase(sbDetails.toString())) {
								getLogger().debug("REASON :" + reason);
								if ("SINGLE".equals(reason)) {
									StringTokenizer token = new StringTokenizer(sbDetails.toString(), ";");
									while (token.hasMoreTokens()) {
										String Entry = token.nextToken();
										//getLogger().info("ENTRY :" + Entry);
										out.write(Entry);
										//getLogger().info("NEWLINE :" + jk);
										out.write(System.getProperty("line.separator"));
										jk++;
										if (jk == 10000) {
											loopCount = 0;
											out.flush();
										}
									}
									sbDetails.delete(0, sbDetails.length());
									jk = 0;
								} else {
									out.write(sbDetails.toString());
									//getLogger().info("NEWLINE :" + k);
									out.write(System.getProperty("line.separator"));
									sbDetails.delete(0, sbDetails.length());
								}
							}
							getLogger().debug("DETAILS :" + sbDetails.toString());

							if (loopCount == 10000) {
								loopCount = 0;
								out.flush();
							}
						}
						loopCount = loopCount + k;
						this.checknumFooter = checkNumFooter();
						this.checknumHeaderFooter = checkHeaderFooterSum();
						if (this.checknumFooter != 0 && this.checknumHeaderFooter == 0) {
							getLogger().info("GETLIST OF 1ST PHASE : MULTIPLE FOOTER & MULTIPLE HEADER");
							FooterS = footerS(i);

							if (!"".equalsIgnoreCase(FooterS.toString())) {
								out.write(FooterS.toString());
								//getLogger().info("NEWLINE :" + k);
								out.write(System.getProperty("line.separator"));
								FooterS.delete(0, FooterS.length());
							}
							getLogger().info("GETLIST OF 2ND PHASE");
							//List listDetails = getList();
							loopCount = 0;
						}
					}
					this.checknumFooter = checkNumFooter();
					this.checknumHeaderFooter = checkHeaderFooterSum();
					if (this.checknumFooter != 0 && this.checknumHeaderFooter != 0) {
						getLogger().info("GETLIST OF 1ST PHASE : SINGLE FOOTER & MULTIPLE HEADER");
						FooterS = footerS(1);

						if (!"".equalsIgnoreCase(FooterS.toString())) {
							out.write(FooterS.toString());
							//getLogger().info("NEWLINE :" + 1);
							out.write(System.getProperty("line.separator"));
							FooterS.delete(0, FooterS.length());
						}
						getLogger().info("GETLIST OF 2ND PHASE");
						//List listDetails = getList();
					}

				}
				//out.write("6");
				out.close();

				getLogger().debug("Finished Generate File Response");
				System.out.println("Finished Generate File Response");
			} else {
                out.write("VALIDATION FAILED");
			}

			//}
		} catch (Exception e) {
			getLogger().info("FAILED GENERATED RESPOND :" + e,e);
			outputFile = context.get(MapKey.reportFileName).toString();
			Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
			out.write(e.getMessage());
			out.close();
		}
		return true;
	}
}
