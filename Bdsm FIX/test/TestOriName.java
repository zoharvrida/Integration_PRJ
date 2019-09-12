
import bdsm.scheduler.util.FileUtil;
import org.apache.commons.io.FilenameUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class TestOriName {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String stA = "/flexcube/cntuat/BatchData/FIX/fixinproc/FCCCDT20170801133810BCID.txt.resp";
        System.out.println(stA);
        System.out.println(FilenameUtils.getBaseName(stA));
        System.out.println(FilenameUtils.getName(stA));
        System.out.println(FileUtil.getRealFileName(stA));
    }
}
