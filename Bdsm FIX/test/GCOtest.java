/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.processor.BaseProcessor;
import java.io.*;
import java.util.Map;
import org.apache.log4j.Logger;

public class GCOtest extends BaseProcessor {

    public static String BatchID;
    public static String delimiter;
    public static String namTemplate;
    private static final Logger LOGGER = Logger.getLogger(GCOtest.class.getName());

    /**
     * @param args the command line arguments
     */
    public GCOtest(Map context) {
        super(context);
    }

    public static void main(String[] args) {
        // TODO code application logic here

        Integer loopCount = 0;
        Integer k;
        try {


            String Id_Scheduler = null;//(String) context.get(MapKey.idScheduler);
            //String namTemplate = (String) context.get(MapKey.templateName);
            FixQXtract fixQXtract = new FixQXtract();//(FixQXtract) context.get(MapKey.fixQXtract);
            fixQXtract.setParam5("GCO.HIT");
            String outputFile = fixQXtract.getParam5();
           //String flagOne = fixQXtract.getParam1();
            //GCOtest.BatchID = fixQXtract.getParam6();
            //GCOtest.delimiter = fixQXtract.getParam2();
            //GCOtest.namTemplate = fixQXtract.getParam4();
            //String ErrValue = fixQXtract.getParam3();
            //String reason = fixQXtract.getReason();
            String text = "";

            LOGGER.debug("GET VALUE : " + Id_Scheduler + " " + BatchID + " " + namTemplate);
            //this.context.put(MapKey.batchNo, BatchID);
            //this.context.put(MapKey.templateName,namTemplate);
            File file = new File("D:\\web\\GCO.HIT");//(PropertyPersister.dirFixIn + outputFile);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            Writer out = new OutputStreamWriter(new FileOutputStream("D:\\web\\GCO_Conv.HIT"), "ISO-8859-1");
            String PrimVal = "";
            String Value = "";
            String temper = "011C";
            String Bitmap = "";
            while ((text = reader.readLine()) != null) {
                PrimVal = text.substring(0, 4);
                Bitmap = text.substring(4, 36);
                Value = text.substring(36, text.length());
                Value = bytesToHex(Value.getBytes("Cp1047"));
                PrimVal = bytesToHex(PrimVal.getBytes("Cp1047"));
                String Result = (temper + PrimVal + Bitmap + Value);
                while (Result.length() < 16196) {
                    Result = Result + "40";
                }
                Result = hexToAscii(Result);
                out.write(Result);
                Result = "";
            }

            //out.write("6");
            out.close();

            LOGGER.debug("Finished Generate File Response");
            System.out.println("Finished Generate File Response");
            //}
        } catch (Exception e) {
            LOGGER.info("FAILED GENERATED RESPOND :" + e,e);
        }
    }

    @Override
    protected boolean doExecute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            //hexChars[j * 2 + 1] = hexArray[v & 0xFF];
        }
        return new String(hexChars);
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }
}
