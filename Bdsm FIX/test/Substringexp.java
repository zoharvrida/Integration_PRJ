
import bdsm.model.ARParamDetailsInterface;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author bdsm
 */
public class Substringexp {

    /**
     * @param args the command line arguments
     */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Substringexp.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String ldString = "01020100310301093109310039103910391031001";
        List values = new ArrayList();
        System.out.println(ldString);
        System.out.println(ldString.substring(0, 1));
        System.out.println(ldString.substring(1, 6 + 1));
        System.out.println(ldString.substring(6 + 1, 6 + 1 + 10));
        System.out.println(ldString.substring(6 + 1 + 10, 6 + 1 + 10 + 5));
        System.out.println(ldString.substring(10, 15));
        System.out.println(ldString);

        ARParamDetailsInterface ar = new ARParamDetailsInterface();
        ARParamDetailsInterface ar1 = new ARParamDetailsInterface();
        ARParamDetailsInterface ar2 = new ARParamDetailsInterface();
        ARParamDetailsInterface ar3 = new ARParamDetailsInterface();

        List<ARParamDetailsInterface> lParam = new ArrayList<ARParamDetailsInterface>();
        ar.setMaxLength(1);
        ar.setIdOrder(1);
        ar.setType("I");
        lParam.add(ar);

        ar1.setMaxLength(6);
        ar1.setIdOrder(2);
        ar1.setType("I");
        lParam.add(ar1);

        ar2.setIdOrder(3);
        ar2.setMaxLength(10);
        ar2.setType("I");
        lParam.add(ar2);

        ar3.setMaxLength(7);
        ar3.setIdOrder(4);
        ar3.setType("I");
        lParam.add(ar3);

        logger.debug(lParam);
        List fixLtoList = fixLtoList(ldString, lParam);

    }

    private static List fixLtoList(String lineDetails, List<ARParamDetailsInterface> dt) {
        List values = new ArrayList();
        Integer nextval = 0;
        for (ARParamDetailsInterface d : dt) {
            if (d.getIdOrder().compareTo(1) == 0) {
                logger.debug("1a");
                values.add(lineDetails.substring(0, d.getMaxLength()));
                nextval = d.getMaxLength();
            } else {
                logger.debug("1b");
                if (d.getType().contains("R")) {
                    //values.add(getReferral(d));
                    logger.debug("2a");
                } else {
                    logger.debug("2b");
                    if ("".equalsIgnoreCase(d.getCustomRef()) && "".equalsIgnoreCase(d.getContextRef()) && "".equalsIgnoreCase(d.getDeffVal())) {
                        logger.debug("3a");
                        values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                    } else {
                        logger.debug("3b");
                        if (d.getCustomRef() == null && d.getContextRef() == null) {
                            logger.debug("4a");
                            if (d.getDeffVal() == null) {
                                logger.debug("5a");
                                values.add(lineDetails.substring(nextval,nextval + d.getMaxLength()));
                            } else {
                                logger.debug("5b");
                                if ("QUERY".equalsIgnoreCase(d.getDeffVal())) {
                                    //values.add(getReferral(d));
                                    logger.debug("6a");
                                } else {
                                    logger.debug("6b");
                                    values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                                }
                            }
                        } else {
                            logger.debug("4b");
                            if ("VALIDATION".equalsIgnoreCase(d.getDeffVal())) {
                                logger.debug("4b1");
                                values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                            } else {
                                logger.debug("4b2");
                                //values.add(getReferral(d));	
                            }

                        }
                    }

                }
                nextval = nextval + d.getMaxLength();
            }
            try {
                String split = lineDetails.substring(nextval, d.getMaxLength());
                logger.debug("NEXTVAL :" + nextval);
                logger.debug("VALUES :" + split);
                System.out.println(split);
            } catch (Exception ex) {
                logger.debug("NOT DATA");
            }
        }
        logger.debug("LIST :" + values);
        return values;
    }
}
