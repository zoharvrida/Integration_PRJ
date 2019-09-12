/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 *
 * @author bdsm
 */
public class BdsmUtil {

    private static Logger logger = Logger.getLogger(BdsmUtil.class);
    private static String PRIVATE_KEY = "3ffeffff";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    private static String MAGIC1 = "b15A61147aca27a1";
    private static String MAGIC2 = "1234567890ABCDEF";
    private static TimeZone defTzToken = TimeZone.getTimeZone("GMT+08:00");
    private static final byte[] keyValue =
            new byte[]{'A', 'B', 'D', 'S', 'M', 'E', 'N', 'C', 'R', 'Y', 'P', 'T', 'K', 'E', 'Y', 'S'};
    private static final String algo = "AES";

    public static String generateToken(String tokenKey, Date dt) {
        if (tokenKey == null) {
            return null;
        }
        if ("0".equals(tokenKey)) {
            return "";
        }

        String sDt = sdf.format(dt);

        return xor(sDt, tokenKey);
    }

    /*
     * public static String generateToken(String tokenKey) { Date dt =
     * Calendar.getInstance(defTzToken).getTime(); logger.debug("dt : " +
     * sdf.format(dt)); logger.debug("Timezone: " +
     * Calendar.getInstance().getTimeZone()); return generateToken(tokenKey,
     * dt); }
     */
    public static int isADValid(String username, String password, String adServer, String adRootDN) {
        int valid = 0;
        Properties env = new Properties();
        String principal = (new StringBuilder().append(username).append(adRootDN)).toString();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("java.naming.security.authentication", "simple");
        env.put("java.naming.provider.url", adServer);
        env.put("java.naming.security.principal", principal);
        if (password == null) {
            env.put("java.naming.security.credentials", "");
        } else {
            env.put("java.naming.security.credentials", password);
        }
        try {
            logger.debug("...VALIDATING AD USER...");
            logger.debug("Username : " + principal);
            DirContext ctx = new InitialDirContext(env);
            ctx.close();
        } catch (Exception e) {
            logger.error(e);
            return valid;
        }
        valid = 1;
        logger.debug("VALID : " + valid);
        return valid;
    }

    public static DirContext getDirContext(String username, String password, String adServer, String adRootDN) {
        DirContext ctx = null;
        Properties env = new Properties();
        String principal = (new StringBuilder().append(username).append(adRootDN)).toString();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("java.naming.security.authentication", "simple");
        env.put("java.naming.provider.url", adServer);
        env.put("java.naming.security.principal", principal);
        if (password == null) {
            env.put("java.naming.security.credentials", "");
        } else {
            env.put("java.naming.security.credentials", password);
        }
        try {
            logger.debug("...Getting AD Context...");
            ctx = new InitialDirContext(env);
        } catch (Exception e) {
            logger.error(e);
        }
        return ctx;
    }

    public static String getNameFromAD(DirContext ctx, String searchBase, String idUser) {
        String result = "";
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchFilter = "(&(objectClass=person)(sAMAccountName=" + idUser + "))";
        NamingEnumeration answer;
        try {
            answer = ctx.search(searchBase, searchFilter, constraints);
            if (answer.hasMore()) {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();
                String split[] = (attrs.get("displayName").toString()).replaceFirst(":", "#").split("#");
                result = split[split.length - 1].trim();
                return result;
            }
        } catch (NamingException ex) {
            logger.debug(ex);
            return result;
        }
        return result;

    }

    public static String generateToken(String tokenKey, String tzId) {
        logger.info("[Begin] generateToken()");
        try {
            //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tzId));
            //Date dt = Calendar.getInstance(TimeZone.getTimeZone(tzId)).getTime();
            Calendar cal = Calendar.getInstance();
            Date dt = cal.getTime();
            logger.debug("dt      : " + sdf.format(dt));
            logger.debug("Timezone: " + Calendar.getInstance().getTimeZone());
            logger.debug("Timezone TZID: " + tzId);
            return generateToken(tokenKey, dt);
        } finally {
            logger.info("[ End ] generateToken()");
        }
    }

    public static Date parseToken(String tokenKey, String token) {
        String sDt = xor(token, tokenKey);
        try {
            sDt = sDt.substring(0, 12);
            Date dt = sdf.parse(sDt);
            return dt;
        } catch (ParseException ex) {
            logger.fatal(ex, ex);
        }
        return null;
    }

    private static String xor(String n1, String n2) {
        if (n2.length() > n1.length()) {
            String temp = n1;
            n1 = n2;
            n2 = temp;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n1.length(); i++) {
            byte b1 = (byte) Character.digit(n1.charAt(i), 16);
            byte b2 = (byte) Character.digit(n2.charAt(i % n2.length()), 16);
            byte b3 = (byte) (b1 ^ b2);
            sb.append(Character.forDigit(b3, 16));
        }
        return sb.toString();
    }

    private static String toBin(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            byte b = (byte) s.charAt(i);
            sb.append(Character.forDigit(b >> 4 & 0xf, 16));
            sb.append(Character.forDigit(b & 0xf, 16));
        }
        return sb.toString();
    }

    private static String pack(String s) {
        StringBuilder sb = new StringBuilder();
        int lm = MAGIC1.length();
        int l = (lm - (s.length() % lm)) % lm;
        for (int i = 0; i < l; i++) {
            sb.append(Character.forDigit((byte) i, 16));
        }
        sb.append(s);
        return sb.toString();
    }

    public static String enc(String pwd) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(algo);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(pwd.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    public static Key generateKey() {
        return new SecretKeySpec(keyValue, algo);
    }

    public static String dec(String encData) {
        String decryptResult = null;
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(algo);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedVal = new BASE64Decoder().decodeBuffer(encData);
            byte[] decVal;
            decVal = c.doFinal(decodedVal);
            decryptResult = new String(decVal);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
        } catch (NoSuchPaddingException ex) {
            logger.error(ex);
        } catch (InvalidKeyException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        } catch (IllegalBlockSizeException ex) {
            logger.error(ex);
        } catch (BadPaddingException ex) {
            logger.error(ex);
        }
        return decryptResult;
    }

    public static String enc(String user, String pwd) {
        String sUser = pack(toBin(user));
        String sPwd = pack(toBin(pwd));

        String s4 = xor(sUser, sPwd);

        int lm = MAGIC1.length();
        int l = s4.length() / lm;
        String s5 = s4.substring(0, lm);
        s5 = xor(s5, MAGIC1);
        if (l > 0) {
            for (int j = 1; j < l; j++) {
                String s6 = s4.substring(j * lm, (j + 1) * lm);
                s6 = xor(s6, MAGIC2);
                s5 = xor(s6, s5);
            }
        }

        return s5.toUpperCase();
    }

    public static String generateToken_old(String tokenKey, Date dt) {
        if (tokenKey == null) {
            return null;
        }
        if ("0".equals(tokenKey)) {
            return "";
        }

        long seed = 0;
        try {
            seed = Long.parseLong(sdf.format(dt));
        } catch (NumberFormatException e) {
        }
        if (seed == 0) {
            return "";
        }

        SecureRandom random = new SecureRandom();
        random.setSeed(seed);
        byte[] bytes = new byte[10];
        random.nextBytes(bytes);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toHexString(bytes[i]));
        }
        return xor(sb.toString(), tokenKey);
    }

    public static String getChecksum(String datafile) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        FileInputStream fis = new FileInputStream(datafile);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public static void copyBeanProperties(Object source, Object destination, String ... propertyNames) throws Exception {
        Class clsSource, clsDestination;
        java.lang.reflect.Method getMethod, setMethod;
        
        clsSource = source.getClass();
        clsDestination = destination.getClass();
        
        if (propertyNames == null) {
            java.lang.reflect.Method[] methods = clsSource.getMethods();
            List<String> propertyNamesList = new java.util.ArrayList<String>();
            
            for (java.lang.reflect.Method m : methods) {
                if ((m.getName().startsWith("get")) && (Character.isUpperCase(m.getName().charAt(3))))
                    propertyNamesList.add(m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4));
                else if ((m.getName().startsWith("is")) && (Character.isUpperCase(m.getName().charAt(2))))
                	propertyNamesList.add(m.getName().substring(2, 3).toLowerCase() + m.getName().substring(3));
            }
            
            Object[] obj = propertyNamesList.toArray();
            propertyNames = new String[obj.length];
            System.arraycopy(obj, 0, propertyNames, 0, obj.length);
        }
        
        for (String m : propertyNames) {
        	try {
        		getMethod = clsSource.getMethod("get" + m.substring(0, 1).toUpperCase() + m.substring(1), new Class[0]);
        	}
        	catch (NoSuchMethodException nsme) {
        		try {
        			getMethod = clsSource.getMethod("is" + m.substring(0, 1).toUpperCase() + m.substring(1), new Class[0]);
        		}
        		catch (NoSuchMethodException nsme2) {
        			logger.error(nsme);
        			continue;
        		}
        	}
            Object value = getMethod.invoke(source, new Object[0]);
            
            try {
                setMethod = clsDestination.getMethod("set" + m.substring(0, 1).toUpperCase() + m.substring(1), getMethod.getReturnType());
                setMethod.invoke(destination, value);
            }
            catch (NoSuchMethodException nsme) {}
        }
    }
    
    public static String convertMoneyToString(java.math.BigDecimal money, Integer length) {
        return convertMoneyToString(money, length, 2, false);
    }
    
    public static String convertMoneyToString(java.math.BigDecimal money, Integer length, Integer decimalPoint, boolean preserveDecimalPoint) {
        return getLeadingZeroStringFromNumber(money, length - 1, decimalPoint, preserveDecimalPoint) 
                + ((money.doubleValue() >= 0.0)? " ": "-");
    }
    
    public static String getLeadingZeroStringFromNumber(Number number, Integer length, Integer scale, boolean preserveDecimalPoint) {
        StringBuilder resultBuilder = new StringBuilder(number.toString());
        int decimalPointPos = resultBuilder.indexOf(".");
        int totalDecimalPoint;
        
        if (decimalPointPos < 0) {
            resultBuilder.append("."); 
            decimalPointPos = resultBuilder.indexOf(".");
        }
        
        if (scale != null) {
            totalDecimalPoint = (resultBuilder.length()-1) - decimalPointPos; 
            if (totalDecimalPoint < scale)
                resultBuilder.append(StringUtils.leftPad("", scale-totalDecimalPoint, '0'));
            else if (totalDecimalPoint > scale) {
                resultBuilder.delete(decimalPointPos + scale + 1, resultBuilder.length());
            }
        }
            
        if (!preserveDecimalPoint)
            resultBuilder.deleteCharAt(decimalPointPos);
        
        return StringUtils.leftPad(resultBuilder.toString(), length, '0');
    }
    
    public static java.math.BigDecimal convertStringToMoney(String strNumber) {
        return convertStringToMoney(strNumber, 0);
    }
    
    public static java.math.BigDecimal convertStringToMoney(String strNumber, int decimalPoint) {
        if (strNumber == null)
            strNumber = "0";
        
        int strLength = strNumber.length();
        char lastCharacter = strNumber.charAt(strLength-1);
        String tempStr = null;
        
        if (" +-".indexOf(lastCharacter) > -1) {
            tempStr = strNumber.substring(0, strLength-1);
            if (lastCharacter == '-')
                tempStr = '-' + tempStr;
        }
        else
            tempStr = strNumber;
        
        if (tempStr.length() < decimalPoint)
            throw new IllegalArgumentException("Decimal point greater than the length of String number!!!");
        
        if (decimalPoint > 0)
            tempStr = new StringBuilder(tempStr).insert(tempStr.length()-decimalPoint, '.').toString();
            
        return new java.math.BigDecimal(tempStr);
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }
    
    public static String leftPad(String str, int size, char c) {
        return StringUtils.leftPad((str == null? "": str), size, c);
    }
    
    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }
    
    public static String rightPad(String str, int size, char c) {
        return StringUtils.rightPad((str == null? "": str), size, c);
    }

    public static boolean isContainsIn(String str, String... strings) {
        StringBuilder strAll = new StringBuilder();
        for(String s: strings)
            strAll.append('|').append(s).append('|');
        
        return (strAll.indexOf('|' + str + '|') > -1);
    }
    
    public static Map<String, String> parseKeyAndValueToMap(String input) {
        String[] arr = input
                            .replaceAll("[ ]*;[ ]*", ";")
                            .replaceAll("[ ]*=[ ]*", "=")
                            .split(";");
        
        
        List<String> l = new java.util.ArrayList<String>(java.util.Arrays.asList(arr));
        Map<String, String> result;
        String s;
        
        // Eliminate zero length string
        for (int i = 0; i < l.size(); i++) {
            s = l.get(i);
            if (s.trim().length() == 0)
                l.remove(i--);
        }
        
        result = new java.util.HashMap<String, String>(l.size());
        // Put to map
        for (String s2 : l) {
            arr = s2.split("=", 2);
            
            switch (arr.length) {
            case 1:
                if (arr[0].trim().length() > 0)
                    result.put(arr[0], "");
                break;
            case 2:
                if ((arr[0].trim().length() > 0) && (arr[1].trim().length() > 0))
                    result.put(arr[0], arr[1]);
            }
        }
        
        return result;
    }
    
    public static String convertDecimalSeparator(String amtTxn){
        int count = StringUtils.countMatches(amtTxn, ",");
        int countLess = StringUtils.countMatches(amtTxn, ".");
            if (count > countLess) {
                if ((countLess == 1 || countLess == 0)) {
                    amtTxn = amtTxn.replace(",", "");
                } else {
                    amtTxn = "INVALID";
                }
            } else if (count < countLess) {
                if (count == 1) {
                    amtTxn = amtTxn.replace(".", "");
                    amtTxn = amtTxn.replace(",", ".");
                } else {
                    amtTxn = "INVALID";
                }
            } else if (count == countLess) {
                if((countLess == 1 || countLess == 0)){
                    amtTxn = amtTxn.replace(",", "");
                } else {
                    amtTxn = "INVALID";
                }
            } else {
                amtTxn = "INVALID";
            }
        return amtTxn;
    }
    
    public static String angkaToTerbilang(Long angka) {
        String[] huruf={"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
        
        if (angka < 12)
           return huruf[angka.intValue()];
        if ((angka >=12) && (angka <= 19))
           return huruf[angka.intValue() % 10] + " Belas";
        if ((angka >= 20) && (angka <= 99))
           return angkaToTerbilang(angka / 10) + " Puluh " + huruf[angka.intValue() % 10];
        if ((angka >= 100) && (angka <= 199))
           return "Seratus " + angkaToTerbilang(angka % 100);
        if ((angka >= 200) && (angka <= 999))
           return angkaToTerbilang(angka / 100) + " Ratus " + angkaToTerbilang(angka % 100);
        if ((angka >= 1000) && (angka) <= (1999))
           return "Seribu " + angkaToTerbilang(angka % 1000);
        if ((angka >= 2000) && (angka <= 999999))
           return angkaToTerbilang(angka / 1000) + " Ribu " + angkaToTerbilang(angka % 1000);
        if ((angka >= 1000000) && (angka <= 999999999))
           return angkaToTerbilang(angka / 1000000) + " Juta " + angkaToTerbilang(angka % 1000000);
        if ((angka >= 1000000000) && (angka <= 999999999999L))
           return angkaToTerbilang(angka / 1000000000) + " Milyar " + angkaToTerbilang(angka % 1000000000);
        if ((angka >= 1000000000000L) && (angka <= 999999999999999L))
           return angkaToTerbilang(angka / 1000000000000L) + " Triliun " + angkaToTerbilang(angka % 1000000000000L);
        if ((angka >= 1000000000000000L) && (angka <= 999999999999999999L))
           return angkaToTerbilang(angka / 1000000000000000L) + " Quadrilyun " + angkaToTerbilang(angka % 1000000000000000L);
        
        return "";
    }
    
    public static void main(String[] args) {
        System.out.println(enc("SSO DEMO 03", "P@ssw0rd"));
    }
}
