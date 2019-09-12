
import bdsm.model.ScreenOpening;
import bdsm.util.ClassConverterUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class ConverterMaptoClass {

    /**
     * @param args the command line arguments
     */
    private static final Logger LOGGER = Logger.getLogger(ClassConverterUtil.class);
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Map singleScreen = new HashMap();
        ScreenOpening so = new ScreenOpening();
        singleScreen.put("fatca", "1");
        so.setFatca("2");
        ClassConverterUtil.MapToClass(singleScreen, so);
        LOGGER.debug("MAP :" + singleScreen);
        LOGGER.debug("CLASS :" + so);
    }
}
