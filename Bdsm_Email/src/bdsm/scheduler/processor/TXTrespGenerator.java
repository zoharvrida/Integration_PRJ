/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.model.FixQXtract;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 *
 * @author v00019722
 */
public abstract class TXTrespGenerator extends BaseProcessor {

    protected String namTemplate;
    protected String BatchID;
    protected Integer checknumHeader;
    protected Integer checknumDetails;
    protected String delimiter;
    protected String reason;
    protected String ErrValue;

    public TXTrespGenerator(Map context) {
        super(context);
    }

    public abstract Integer checkNumHeader();

    public abstract Integer checkNumDetail(Integer count);


    public abstract StringBuilder headerS(Integer count);

    public abstract StringBuilder detailS(Integer count);

    //public abstract List getList();

    @Override
    public boolean doExecute() throws Exception {

        Integer loopCount = 0;
        Integer k;
        try {

            ErrorCodeDao errDao = new ErrorCodeDao(session);
            
            String Id_Scheduler = (String) context.get(MapKey.idScheduler);
            //String namTemplate = (String) context.get(MapKey.templateName);
            fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);
            String outputFile = fixQXtract.getParam5();
            String flagOne = fixQXtract.getParam1();
            this.BatchID = fixQXtract.getParam6();
            this.delimiter = fixQXtract.getParam2();
            this.namTemplate = fixQXtract.getParam4();
            String ErrValue = fixQXtract.getParam3();
            String reason = fixQXtract.getReason();

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
            StringBuilder HeaderS = new StringBuilder();

            Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
            getLogger().info("BEGIN CHECK NUM");

            getLogger().info("ENTERING CHECKNUM CLASS");
                        //out.write(System.getProperty("line.separator"));

                            //out.write(System.getProperty("line.separator"));
                                //out.write(System.getProperty("line.separator"));


                            //interfaceParameter = mit.get(2);
                            //interfaceDetails = arDao.paramDetailsInterface(idSchedImport, "O", interfaceParameter.getRecType(),0);
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
            //out.write("6");
            out.close();

            getLogger().debug("Finished Generate File Response");
            System.out.println("Finished Generate File Response");
            //}
        } catch (Exception e) {
                getLogger().info("FAILED GENERATED RESPOND :" + e);
        }
        return true;
    }
}
