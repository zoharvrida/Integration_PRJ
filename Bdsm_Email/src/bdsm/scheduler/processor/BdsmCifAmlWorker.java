/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.BdsmCifAmlDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.BdsmCifAml;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00020841
 */
public class BdsmCifAmlWorker extends BaseProcessor {

    private List<BdsmCifAml> listCifAml;
    private String getProperties;
    private String separator; 
    private String lineBreak;
    private String param1;
    private String patternDate;

    public BdsmCifAmlWorker(Map context) {
        super(context);
    }

    @Override
    protected boolean doExecute() throws Exception {
        this.getLogger().info("get data parameter to insert variable");
        getCifAmlParam(StatusDefinition.delimiter);
        this.getLogger().info("param1 : "+param1);
        this.getLogger().info("pattern date : "+patternDate);
        this.getLogger().info("separator : "+separator);
        this.getLogger().info("get properties : "+getProperties);
        this.getLogger().info("get data parameter to insert variable success");
        BdsmCifAmlDao cifAmlDao = new BdsmCifAmlDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);

        //Run Insert to tmp_cif_aml
		this.getLogger().info("Run Insert CIF");
        cifAmlDao.runUpload();
		this.getLogger().info("Run Insert CIF Finished");
        String outputFile;
        FixQXtract qXtract = (FixQXtract) this.context.get(MapKey.fixQXtract);
        try {
            getLogger().info("idscheduler :" + context.get(MapKey.param2).toString());
            int idScheduler = Integer.parseInt(context.get(MapKey.param2).toString());
            FixSchedulerXtract xTract = xtractDao.get(idScheduler);
            outputFile = xTract.getFilePattern() + SchedulerUtil.getDate(patternDate) + "." + xTract.getFileFormat();
        } catch (Exception e) {
            getLogger().info("exception make outfile : " + e);
            outputFile = null;
        }
        
        this.getLogger().info("prepare object for make cifaml.txt");
        StringWriter out = new StringWriter();
        writerCifAml writer = new writerCifAml(out);
        try {
            this.getLogger().info("get list cif aml ");
            cifAmlDao = new BdsmCifAmlDao(session);
            this.listCifAml = cifAmlDao.getListCifAml();
            int countRecord = 0;
            this.getLogger().info("start insert to cifaml.txt ");
            for (BdsmCifAml bdsmCifAml : listCifAml) {
                String cifAml = cifTextArrange(bdsmCifAml);
                writer.write(cifAml);
                //writer.newLine();
                writer.write(System.getProperty("line.separator"));
                countRecord++;
				this.getLogger().info("data : "+cifAml);
            }
            this.getLogger().info("end insert to cifaml.txt");
            writer.flush();
            this.getLogger().info("write.flush cifaml " + countRecord + " data succes");
            FileUtil.writeWithFileChannerDMA(out.getBuffer().toString(), PropertyPersister.dirFixOut + outputFile);
            this.context.put(MapKey.param5, outputFile);
            out.getBuffer().delete(0, out.getBuffer().length());
            getLogger().info("line delete : " + out.getBuffer().toString());
            session.clear();
        } catch (Exception e) {
            this.getLogger().info("exception make cifaml.txt : " + e);
        } finally {
            this.getLogger().info("make cifaml.txt finished");
            writer.close();
        }
            getLogger().info("NAME FILE :" + context.get(MapKey.param5));
            qXtract.setParam5(context.get(MapKey.param5).toString());
        return true;
    }

    private void getCifAmlParam(String delimiter) {
        try {
            List parameter = SchedulerUtil.getParameter(PropertyPersister.paramCifAml, delimiter); // get cifaml parameter
            this.param1 = parameter.get(0).toString();
            this.patternDate = parameter.get(1).toString();
            this.separator = parameter.get(2).toString();
            this.getProperties = parameter.get(3).toString();
            this.lineBreak = parameter.get(4).toString();

        } catch (Exception e) {
            getLogger().info("EXCEPTION : FAILED GET JAVA PARAM - " + e, e);
            this.getProperties = "cif_aml_field.properties";
            this.separator = "|"; // separator
            this.param1 = "CIFAML";
            this.patternDate = "yyyyMMddHHmmss";
            this.lineBreak = "LINEWINDOWS";
        }
    }

    private String cifTextArrange(BdsmCifAml ballObj) {
        StringBuilder cif = new StringBuilder();
        cif.append(ballObj.rearrangeString(this.separator));
        return cif.toString();
    }
}
// class buat write
class writerCifAml extends BufferedWriter {

    public writerCifAml(Writer w) {
        super(w);
    }

    @Override
    public void newLine() throws IOException {
        this.write("\n");
    }
}
