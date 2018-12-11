/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.ARParamDetailsInterface;
import bdsm.model.ARParamMasterInterface;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.dao.GeneralINTDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.RespModel;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bdsm
 */
public abstract class NewTxtRespGenerator extends BaseProcessor {

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
    protected Integer checknumFooters = 0;
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
    protected Integer loopNum;
    /**
     * 
     */
    protected boolean rec = true;
    private Integer idSchedImport;
    /**
     * 
     */
    protected ARParamMasterInterface interfaceParameter;
    /**
     * 
     */
    protected List<ARParamDetailsInterface> interfaceDetails;
    private boolean masterProc = false;
    private boolean flgProcess = false;

    /**
     * 
     * @param context
     */
    public NewTxtRespGenerator(Map context) {
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
    public abstract Integer checkNumFooter();

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

        Integer loopCount = 0;
        Integer k;
        try {

            ErrorCodeDao errDao = new ErrorCodeDao(session);
            getLogger().debug("CONTEXT_WRITE :" + this.context);
            String Id_Scheduler = (String) context.get(MapKey.idScheduler);
            //String namTemplate = (String) context.get(MapKey.templateName);
            fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);
            String outputFile = fixQXtract.getParam5();
            String flagOne = fixQXtract.getParam1();
            this.BatchID = fixQXtract.getParam6();
            this.delimiter = fixQXtract.getParam2();
            this.namTemplate = fixQXtract.getParam4();
            this.ErrValue = fixQXtract.getParam3();
            this.reason = fixQXtract.getReason();

            getLogger().info("Start Generate File Response" + namTemplate);
            getLogger().debug("Get FIXQ  1:" + flagOne);
            getLogger().debug("Get FIXQ  2:" + this.delimiter);
            getLogger().debug("Get FIXQ  3:" + this.ErrValue);
            getLogger().debug("Get FIXQ  4:" + this.namTemplate);
            getLogger().debug("Get FIXQ  5:" + outputFile);
            getLogger().debug("Get FIXQ  6:" + this.BatchID);
            getLogger().debug("Get FIXQ  Reason:" + this.reason);

            getLogger().debug("GET VALUE : " + Id_Scheduler + " " + BatchID + " " + namTemplate);
            //this.context.put(MapKey.batchNo, BatchID);
            //this.context.put(MapKey.templateName,namTemplate);
            GeneralINTDao arDao = new GeneralINTDao(session);
            try {
                List getImport = SchedulerUtil.getParameter(flagOne, delimiter);
                idSchedImport = Integer.parseInt(getImport.get(1).toString());
            } catch (Exception e) {
                getLogger().debug("wEx : " + e, e);
                idSchedImport = 0;
            }
            List<ARParamMasterInterface> mit = arDao.listparamProfile(idSchedImport);
            List<RespModel> structDepend = new ArrayList();
            if (mit != null) {
                int i = 0;
                RespModel rm = new RespModel();
                for (ARParamMasterInterface pi : mit) {
                    rm.setDependNo(pi.getDependNo());
                    rm.setTypRecord(pi.getRecType());
                    rm.setOrderNo(pi.getOrderNo());
                    structDepend.add(rm);
                }
                masterProc = true;
            } else {
                // fixing for xtract only
                mit = arDao.listparamProfile(fixQXtract.getIdScheduler(), BatchID);
                if (mit != null) {
                    idSchedImport = fixQXtract.getIdScheduler();
                    flgProcess = true;
                    masterProc = true;
                }
            }
            if (masterProc) {
                interfaceParameter = mit.get(0);
                interfaceDetails = arDao.detailsList(idSchedImport, interfaceParameter.getTypProcess());
            }
            StringBuilder HeaderS = new StringBuilder();
            getLogger().debug("FULLPATH :" + outputFile);
            getLogger().debug("GETNAME :" + FileUtil.getRealFileName(outputFile));
            String nameofFile = FileUtil.getRealFileName(outputFile);

            Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + nameofFile));

            this.fixQXtract.setParam5(nameofFile);
            this.context.put(MapKey.fixQXtract, this.fixQXtract);
            getLogger().debug("BEGIN CHECK NUM :" + context);

            Integer headerRec = 0;
            this.context.put(MapKey.batchNo, this.BatchID);
            this.loopNum = headerRec;
            this.checknumHeader = checkNumHeader();
            int valFlag = 4;

            if (!mit.isEmpty()) {
                if (this.checknumHeader > 0) { // have header or End of HEader
                    for (Integer i = 1; i < checknumHeader + 1; i++) {
                        getLogger().debug("GETLIST OF 1ST PHASE");
                        interfaceParameter = mit.get(0);
                        if ("Y".equalsIgnoreCase(interfaceParameter.getFlgValidation())) {
                            valFlag = 3;
                        } else {
                            valFlag = 4;
                        }
                        getLogger().debug("INT_MASTER :" + interfaceParameter);
                        if (flgProcess) {
                            interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag, "X");
                        } else {
                            interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag);
                        }

                        HeaderS = headerS(i);
                        out.write(HeaderS.toString());
                        //out.write(System.getProperty("line.separator"));

                        interfaceParameter = mit.get(1);
                        if ("Y".equalsIgnoreCase(interfaceParameter.getFlgValidation())) {
                            valFlag = 3;
                        } else {
                            valFlag = 4;
                        }
                        interfaceDetails = arDao.detailsList(idSchedImport, interfaceParameter.getTypProcess());
                        this.checknumDetails = checkNumDetail(loopNum);
                        StringBuilder sbDetails = new StringBuilder();
                        for (k = 1; k < this.checknumDetails + 1; k++) {
                            getLogger().debug("Get Row details : " + k + " " + loopCount);

                            if (flgProcess) {
                                interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag, "X");
                            } else {
                                interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag);
                            }
                            sbDetails = detailS(loopCount + k);
                            out.write(sbDetails.toString());
                            //out.write(System.getProperty("line.separator"));
                            getLogger().debug("DETAILS :" + sbDetails.toString());
                            if (loopCount == 10000) {
                                loopCount = 0;
                                out.flush();
                            }
                        }
                        interfaceParameter = mit.get(2);
                        if ("Y".equalsIgnoreCase(interfaceParameter.getFlgValidation())) {
                            valFlag = 3;
                        } else {
                            valFlag = 4;
                        }
                        if (flgProcess) {
                            interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag, "X");
                        } else {
                            interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag);
                        }
                        this.checknumFooters = checkNumFooter();
                        if (this.checknumFooters > 0) {
                            for (int j = 0; j < this.checknumFooters; j++) {
                                StringBuilder sbFooter = new StringBuilder();
                                sbFooter = footerS(i);
                                out.write(sbFooter.toString());
                                //out.write(System.getProperty("line.separator"));
                            }
                        }
                    }
                } else {
                    interfaceParameter = mit.get(1);
                    if (flgProcess) {
                        interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag, "X");
                    } else {
                        interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag);
                    }

                    this.checknumDetails = checkNumDetail(loopNum);
                    StringBuilder sbDetails = new StringBuilder();
                    for (k = 1; k < this.checknumDetails + 1; k++) {
                        getLogger().debug("Get Row details : " + k + " " + loopCount);
                        sbDetails = detailS(loopCount + k);
                        out.write(sbDetails.toString());
                        out.write(System.getProperty("line.separator"));
                        getLogger().debug("DETAILS :" + sbDetails.toString());
                        if (loopCount == 10000) {
                            loopCount = 0;
                            out.flush();
                        }
                    }
                    interfaceParameter = mit.get(2);
                    if ("Y".equalsIgnoreCase(interfaceParameter.getFlgValidation())) {
                        valFlag = 3;
                    } else {
                        valFlag = 4;
                    }
                    if (flgProcess) {
                        interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag, "X");
                    } else {
                        interfaceDetails = arDao.paramDetailsInterface(idSchedImport, interfaceParameter.getTypProcess(), "O|Q", valFlag);
                    }

                    this.checknumFooters = checkNumFooter();
                    if (checknumFooters.compareTo(0) >= 0) {
                        for (int i = 0; i < this.checknumFooters; i++) {
                            StringBuilder sbFooter = new StringBuilder();
                            out.write(sbFooter.toString());
                            out.write(System.getProperty("line.separator"));
                        }
                    }
                }
            } else { // existing process
                this.checknumHeader = checkNumHeader();
                if (this.checknumHeader == 0) { // don't have header
                    this.checknumDetails = checkNumDetail(0);
                    StringBuilder sbDetails = new StringBuilder();
                    //listDetails = getList();
                    for (k = 1; k < this.checknumDetails + 1; k++) {
                        getLogger().debug("Get Row details : " + k + " " + loopCount);

                        sbDetails = detailS(loopCount + k);
                        out.write(sbDetails.toString());
                        out.write(System.getProperty("line.separator"));
                        getLogger().debug("DETAILS :" + sbDetails.toString());

                        //sbDetails.delete(0, sbDetails.length());

                        if (loopCount == 10000) {
                            loopCount = 0;
                            out.flush();
                        }
                    }
                } else {
                    for (Integer i = 1; i < checknumHeader + 1; i++) {
                        getLogger().info("GETLIST OF 1ST PHASE");
                        HeaderS = headerS(i);
                        out.write(HeaderS.toString());
                        out.write(System.getProperty("line.separator"));
                        getLogger().info("GETLIST OF 2ND PHASE");
                        //List listDetails = getList();
                        this.checknumDetails = checkNumDetail(i);
                        StringBuilder sbDetails = new StringBuilder();
                        //listDetails = getList();
                        for (k = 1; k < this.checknumDetails + 1; k++) {
                            getLogger().debug("Get Row details : " + k + " " + loopCount);

                            sbDetails = detailS(loopCount + k);
                            out.write(sbDetails.toString());
                            out.write(System.getProperty("line.separator"));
                            getLogger().debug("DETAILS :" + sbDetails.toString());

                            //sbDetails.delete(0, sbDetails.length());

                            if (loopCount == 10000) {
                                loopCount = 0;
                                out.flush();
                            }
                        }
                        loopCount = loopCount + k;
                    }
                }
            }
            //out.write("6");
            out.close();

            getLogger().debug("Finished Generate File Response");
            System.out.println("Finished Generate File Response");
            //}
        } catch (Exception e) {
            getLogger().info("FAILED GENERATED RESPOND :" + e, e);
        }
        return true;
    }
}
