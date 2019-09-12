/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.FileUtil;
import java.io.*;
import java.util.Map;

/**
 *
 * @author bdsm
 */
public class EBCDICConverter extends BaseProcessor {

    protected String BatchID;
    protected String delimiter;
    protected String namTemplate;
    public EBCDICConverter(Map context) {
        super(context);
    }
    @Override
    protected boolean doExecute() throws Exception {
        Integer loopCount = 0;
        Integer k;
        try {


            String Id_Scheduler = (String) context.get(MapKey.idScheduler);
            String namTemplate = (String) context.get(MapKey.templateName);
            FixQXtract fixQXtract = new FixQXtract();//(FixQXtract) context.get(MapKey.fixQXtract);
            //fixQXtract.setParam5("GCO.HIT");
            String outputFile = fixQXtract.getParam5();
            String flagOne = fixQXtract.getParam1();
            this.BatchID = fixQXtract.getParam6();
            this.delimiter = fixQXtract.getParam2();
            this.namTemplate = fixQXtract.getParam4();
            String ErrValue = fixQXtract.getParam3();
            String reason = fixQXtract.getReason();
            String text = "";

            getLogger().info("Start Generate File Response" + namTemplate);
            getLogger().debug("Get FIXQ  1:" + flagOne);
            getLogger().debug("Get FIXQ  2:" + this.delimiter);
            getLogger().debug("Get FIXQ  4:" + this.namTemplate);
            getLogger().debug("Get FIXQ  5:" + outputFile);
            getLogger().debug("Get FIXQ  6:" + this.BatchID);
            
            getLogger().debug("GET VALUE : " + Id_Scheduler + " " + BatchID + " " + namTemplate);
            //this.context.put(MapKey.batchNo, BatchID);
            //this.context.put(MapKey.templateName,namTemplate);
            //File file = new File("D:\\web\\GCO.HIT");//(PropertyPersister.dirFixIn + outputFile);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(outputFile));
            } catch (FileNotFoundException ex) {
                getLogger().info("FILE NOT FOUND :" + ex);
            }
            //reader = new BufferedReader(new FileReader(file));
            Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile), "ISO-8859-1");
            String PrimVal = "";
            String Value = "";
            String temper = "011C";
            String Bitmap = "";
            while ((text = reader.readLine()) != null) {
                PrimVal = text.substring(0, 4);
                Bitmap = text.substring(4, 36);
                Value = text.substring(36, text.length());
                Value = FileUtil.bytesToHex(Value.getBytes("Cp1047"));
                PrimVal = FileUtil.bytesToHex(PrimVal.getBytes("Cp1047"));
                String Result = (temper + PrimVal + Bitmap + Value);
                while (Result.length() < 16196) {
                    Result = Result + "40";
                }
                Result = FileUtil.hexToAscii(Result);
                out.write(Result);
                Result = "";
            }

            //out.write("6");
            out.close();

            getLogger().debug("Finished Generate File Response");
            System.out.println("Finished Generate File Response");
            //}
        } catch (Exception e) {
            getLogger().info("FAILED GENERATED RESPOND :" + e,e);
        }
        return true;
    }
    
}
