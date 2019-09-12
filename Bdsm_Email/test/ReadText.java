
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.processor.TXTReaderWorker;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.SysexMessage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class ReadText {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map context = new HashMap();
        context.put("1","A");
        context.put("2","B");
        context.put("3","C");
        Object[] test = context.entrySet().toArray();
        System.out.println(test[0]);
        System.out.println(test[1]);
        System.out.println(test[2]);
        
        System.out.println(context.entrySet().iterator().next());
        
    }
}
