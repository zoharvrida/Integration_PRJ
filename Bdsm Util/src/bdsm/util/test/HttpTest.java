/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util.test;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NCBS
 */
public class HttpTest {

    public static void main(String[] args) {
        try {
            String result;
            Map<String, String> map = new HashMap<String, String>();
            map.put("model.compositeId.codCustId", "296879");
            map.put("token", "21305564999dedfc");//BdsmUtil.generateToken("0123456789ABCDEF", "GMT+07:00"));
            result = HttpUtil.request("http://10.195.52.36:7781/bdsmapp/ciCustmast_get.action", map);
            System.out.println("Result Is : " + result);
            System.out.println("Token : " + BdsmUtil.generateToken("0123456789ABCDEF", "GMT+07:00"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


//21305564999dedfc
//213055648c8fedfc