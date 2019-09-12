/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author bdsm
 */
public class EncryptionUtil {

    public static final String AESALGO = "AES";
    public static final String DESALGO = "3DES";
    public static final String DES3ALGO = "3DES/CBC";
    public static final String ZIPALGO = "ZIP";
    public static final String RC4ALGO = "RC4";
    public static final String RC4ALGO2 = "RC42";
    public static final String HASHFALGO = "HASHF";
    //FCR Default Salt Key for AES
    static byte[] arrayOfByte2 = {22, 37, 47, 125, 116, 86, 41, 115, 57, 100, 83, 18, 101, 86, 71, 43};
    private static final Logger logger = Logger.getLogger(EncryptionUtil.class);
    private final int[] sBox = new int[256];
    private int x;
    private int y;
    /*
     * public static void RC4Algo(String source, String dest, String password)
     * throws IOException, FileNotFoundException { byte[] key =
     * password.getBytes(); int keylen = key.length; byte[] text = null; if
     * (keylen < 1 || keylen > 256) { throw new IllegalArgumentException("Key
     * must between 1 and 256 bytes"); } else { byte S[] = new byte[256]; byte
     * T[] = new byte[256]; for (int i = 0; i < 256; i++) { S[i] = (byte) i;
     * T[i] = key[i % keylen]; }
     *
     * int j = 0;
     *
     * for (int i = 0; i < 256; i++) { j = (j + S[i] + T[i]) & 0xFF; S[i] ^=
     * S[j]; S[j] ^= S[i]; S[i] ^= S[j]; }
     *
     * FileInputStream fis = new FileInputStream(source); try { text =
     * IOUtils.toByteArray(fis); } finally { if (fis != null) { fis.close(); } }
     *
     * int i = 0, k, t; j = 0; for (int counter = 0; counter < text.length;
     * counter++) { i = (i + 1) & 0xFF; j = (j + S[i]) & 0xFF; S[i] ^= S[j];
     * S[j] ^= S[i]; S[i] ^= S[j]; t = (S[i] + S[j]) & 0xff; k = S[t];
     * text[counter] = (byte) (text[counter] ^ k); }
     *
     * FileOutputStream fos = new FileOutputStream(dest); try {
     * IOUtils.write(text, fos); } finally { if (fos != null) { fos.close(); } }
     * } }
     */

    private void rc4_crypt(int[] paramArrayOfInt, DataOutputStream FileOut)
            throws IOException {
        int m = paramArrayOfInt.length;
        for (int n = 0; n < m; n++) {
            this.x = (this.x + 1 & 0xFF);
            this.y = (this.sBox[this.x] + this.y & 0xFF);

            int j = this.sBox[this.x];
            this.sBox[this.x] = this.sBox[this.y];
            this.sBox[this.y] = j;

            int i = this.sBox[this.x] + this.sBox[this.y] & 0xFF;
            int k = paramArrayOfInt[n] ^ this.sBox[i];
            FileOut.writeByte(k);
        }
    }

    private void makeKey(String paramString) {
        byte[] arrayOfByte = paramString.getBytes();

        int i = arrayOfByte.length;

        this.x = (this.y = 0);
        for (int j = 0; j < 256; j++) {
            this.sBox[j] = j;
        }
        int j = 0;
        int k = 0;

        for (int n = 0; n < 256; n++) {
            k = (arrayOfByte[j] & 0xFF) + this.sBox[n] + k & 0xFF;

            int m = this.sBox[n];
            this.sBox[n] = this.sBox[k];
            this.sBox[k] = m;

            j = (j + 1) % i;
        }
    }

    public static void RC4Algo2(String paramString1, String paramString2, String Key)
            throws IOException {
        EncryptionUtil newRC4 = new EncryptionUtil();
        FileInputStream localFileInputStream = new FileInputStream(paramString1);
        int i = localFileInputStream.available();

        DataOutputStream FileOut = new DataOutputStream(new FileOutputStream(paramString2));

        int[] arrayOfInt = new int[i];

        newRC4.makeKey(Key);

        for (int j = 0; j < i; j++) {
            arrayOfInt[j] = localFileInputStream.read();
        }
        newRC4.rc4_crypt(arrayOfInt, FileOut);

        localFileInputStream.close();
        FileOut.close();
    }

    public static void RC4Algo(String source, String dest, String password) throws IOException, FileNotFoundException {
        byte[] key = password.getBytes();
        int keylen = key.length;
        byte[] text = null;
        if (keylen < 1 || keylen > 256) {
            throw new IllegalArgumentException("Key must between 1 and 256 bytes");
        } else {
            int[] sbox = new int[256];
            for (int j = 0; j < 256; j++) {
                sbox[j] = j;
            }
            int j = 0;
            int k = 0;
            for (int n = 0; n < 256; n++) {
                k = (key[j] & 0xFF) + sbox[n] + k & 0xFF;
                int m = sbox[n];
                sbox[n] = sbox[k];
                sbox[k] = m;

                j = (j + 1) % keylen;
            }

            FileInputStream fis = new FileInputStream(source);
            try {
                text = IOUtils.toByteArray(fis);
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }

            int m = text.length;
            int x = 0;
            int y = 0;
            FileOutputStream fos = new FileOutputStream(dest);
            try {
                for (int n = 0; n < m; n++) {
                    x = (x + 1 & 0xFF);
                    y = (sbox[x] + y & 0xFF);

                    int j1 = sbox[x];
                    sbox[x] = sbox[y];
                    sbox[y] = j1;

                    int i = sbox[x] + sbox[y] & 0xFF;
                    int k1 = text[n] ^ sbox[i];
                    try {
                        fos.write(k1);
                    } catch (Exception e) {
                    }
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public static void AESAlgo(String source, String dest, String password, int mode) throws IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, FileNotFoundException, InvalidAlgorithmParameterException {
        Key key;
        byte[] fileContent = null;
        FileInputStream fis = new FileInputStream(source);
        IvParameterSpec ivParam = new IvParameterSpec(arrayOfByte2);
        try {
            key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (mode == Cipher.ENCRYPT_MODE) {
                c.init(Cipher.ENCRYPT_MODE, key, ivParam);
            } else if (mode == Cipher.DECRYPT_MODE) {
                c.init(Cipher.DECRYPT_MODE, key, ivParam);
            }
            fileContent = IOUtils.toByteArray(fis);
            fileContent = c.doFinal(fileContent);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        FileOutputStream fos = new FileOutputStream(dest);

        try {
            IOUtils.write(fileContent, fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

    }

    public static void DESAlgo(String source, String dest, String key, int mode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, InvalidAlgorithmParameterException {
        byte[] fileContent = null;
        FileInputStream fis = new FileInputStream(source);
        try {
            SecretKey skey = readDESkey(key);
            Cipher c = Cipher.getInstance("DESede");
            if (mode == Cipher.ENCRYPT_MODE) {
                c.init(Cipher.ENCRYPT_MODE, skey);
            } else if (mode == Cipher.DECRYPT_MODE) {
                c.init(Cipher.DECRYPT_MODE, skey);
            }
            fileContent = IOUtils.toByteArray(fis);
            fileContent = c.doFinal(fileContent);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        FileOutputStream fos = new FileOutputStream(dest);

        try {
            IOUtils.write(fileContent, fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte data[] = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public static String byteArrayToHexString(byte[] rawBytes) {
        StringBuffer sb = new StringBuffer(rawBytes.length * 2);
        for (int i = 0; i < rawBytes.length; i++) {
            String s = Integer.toHexString(rawBytes[i] & 0xFF);
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static String LPad(String str, Integer length, char car) {
        return (new StringBuilder(String.valueOf(str))).append(String.format((new StringBuilder("%")).append(length.intValue() - str.length()).append("s").toString(), new Object[]{
                    ""
                }).replace(" ", String.valueOf(car))).toString();
    }

    public static String RPad(String str, Integer length, char car) {
        return (new StringBuilder(String.valueOf(String.format((new StringBuilder("%")).append(length.intValue() - str.length()).append("s").toString(), new Object[]{
                    ""
                }).replace(" ", String.valueOf(car))))).append(str).toString();
    }

    /**
     *
     * if stringMode = true, message is String, return type is String <p> if
     * stringMode = false and mode = 1 (encrypt) then message is String, return
     * type is byte[] <p> if stringMode = false and mode = 2 (decrypt) then
     * message is byte[], return type is String
     *
     *
     */
    public static Object getDES3Algo(Object message, String keyEncDec, int mode, boolean stringMode) throws Exception {
        byte[] bResult;

        if (mode == Cipher.ENCRYPT_MODE) {
            bResult = encryptDES3Algo(message.toString(), keyEncDec);
            return (stringMode) ? byteArrayToHexString(bResult) : bResult;
        } else if (mode == Cipher.DECRYPT_MODE) {
            bResult = (stringMode) ? hexStringToByteArray(message.toString()) : (byte[]) message;
            return decryptDES3Algo(bResult, keyEncDec);
        }

        throw new Exception("Error mode: " + mode);
    }

    public static byte[] encryptDES3Algo(String message, String keyM) throws Exception {
        byte keyBytes[] = hexStringToByteArray(keyM);
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        cipher.init(1, key, iv);
        if (message.length() % 8 != 0) {
            message = LPad(message, Integer.valueOf((message.length() + 8) - message.length() % 8), ' ');
            System.out.println("MESSAGE :" + message);
        }
        System.out.println("MESSAGE TEST:" + message);
        byte plainTextBytes[] = message.getBytes("utf-8");
        byte cipherText[] = cipher.doFinal(plainTextBytes);
        return cipherText;
    }

    public static String decryptDES3Algo(byte message[], String keyM) throws Exception {
        byte keyBytes[] = hexStringToByteArray(keyM);
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher decipher = Cipher.getInstance("DESede/CBC/NoPadding");
        decipher.init(2, key, iv);
        byte plainText[] = decipher.doFinal(message);
        return new String(plainText, "UTF-8");
    }

    public static File DES3AlgoEnc(String files, String outs, String key) {
        //System.out.println("[BEGIN] SknNgHelper.encryptFile()");
        File infile = new File(files);
        File outfile = new File(outs);
        try {
            FileReader fr = new FileReader(infile.getAbsolutePath());
            BufferedReader in = new BufferedReader(fr);
            FileWriter writer = new FileWriter(outfile);
            try {
                String str;
                while ((str = in.readLine()) != null) {
                    //System.out.println("[DEBUG] SknNgHelper.encryptFile(): plaintext: " + str.length() + ", " + str);
                    byte[] hasil = encryptDES3Algo(str, key);
                    String hasilString = byteArrayToHexString(hasil);
                    //System.out.println("[DEBUG] SknNgHelper.encryptFile(): encrypted: " + hasilString.length() + ", " + hasilString);
                    writer.write(hasilString);
                    writer.write("\n");
                }
                //System.out.println("[ INFO] SknNgHelper.encryptFile(): Success: " + outfile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("[ERROR] SknNgHelper.encryptFile(): " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fr != null) {
                    fr.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            //System.out.println("[  END] SknNgHelper.encryptFile()");
        }
        return outfile;
    }

    public static void DES3AlgoDec(String files, String outs, String key) {
        File infile = new File(files);
        File outfile = new File(outs);
        try {
            FileReader fr = new FileReader(infile.getAbsolutePath());
            BufferedReader in = new BufferedReader(fr);
            FileWriter writer = null;
            writer = new FileWriter(outfile);
            try {
                String str;
                while ((str = in.readLine()) != null) {
                    //System.out.println("[DEBUG] SknNgHelper.decryptFile(): encrypted: " + str.length() + ", " + str);
                    byte[] bitArray = hexStringToByteArray(str);
                    String hasil = decryptDES3Algo(bitArray, key);
                    //System.out.println("[DEBUG] SknNgHelper.decryptFile(): plaintext: " + hasil.length() + ", " + hasil);

                    writer.write(hasil);
                    writer.write("\n");
                }
                //System.out.println("[ INFO] SknNgHelper.decryptFile(): Success: " + outfile.getAbsolutePath());
            } catch (Exception e) {
                //System.out.println("[ERROR] SknNgHelper.encryptFile(): " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fr != null) {
                    fr.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            //System.out.println("[  END] SknNgHelper.decryptFile()");
        }
    }

    private static SecretKey readDESkey(String fileKey) throws FileNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String dataKey;
        byte[] key;
        File files = new File(fileKey);
        DataInputStream in = new DataInputStream(new FileInputStream(files));
        dataKey = getAES(IOUtils.toString(in), "3DES@@@@@@@@@@@@", Cipher.DECRYPT_MODE);
        key = dataKey.getBytes();
        in.close();

        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        return keyFactory.generateSecret(keySpec);
    }

    public static String ZIPcompress(String source, String destination, String password) throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        ZipFile zipFile = null;
        if (password != null) {
            zipParameters.setPassword(password);
            zipParameters.setEncryptionMethod(Zip4jConstants.COMP_AES_ENC);
            zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            zipParameters.setEncryptFiles(true);
        }
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        logger.debug("ZIP DEST : " + destination);
        List zipList = SchedulerUtil.getParameter(destination, "|");
        List nameList = SchedulerUtil.getParameter(destination, "|");
        logger.debug("ZIP SIZE : " + zipList.size());
        if (zipList.size() == 1) {
            zipFile = new ZipFile(destination);
        } else {
            String zipName = zipList.get(1).toString();
            logger.debug("NAME ZIP :" + zipName);
            if(new File(zipName).exists()){
                FileUtils.deleteQuietly(new File(zipName));
            }
            zipFile = new ZipFile(zipName);
        }
        logger.debug("SOURCE delim : " + nameList.get(0).toString());
        List sourceList = SchedulerUtil.getParameter(nameList.get(0).toString(), ";");
        
        logger.debug("SOURCE SIZE : " + sourceList.size());
        if (sourceList.size() > 1) {
            ArrayList fileNames = new ArrayList();
            Iterator<String> it = sourceList.iterator();
            
            while (it.hasNext()) {
                String fileN = it.next().toString();
                logger.debug("STRING FILE : " + fileN);
                fileNames.add(new File(fileN));
            }
            zipFile.addFiles(fileNames, zipParameters);
            logger.debug("ZIP ARRAY : " + fileNames);
            // clean file after Zip Success
            Iterator<File> fit = fileNames.iterator();
            while (fit.hasNext()){
                try {
                    File fileD = fit.next();
                    try {
                        FileUtils.deleteQuietly(fileD);
                    } catch (Exception e) {
                        logger.debug("FILE NOT EXIST : " + fileD);
                    }
                } catch (Exception e) {
                    logger.debug("CAST FAILED: " + e,e);
                }
            }
        } else {
            zipFile.addFile(new File(source), zipParameters);
        }
        logger.info("ZIP FILE ENC : " + zipFile.getFile().getName());
        return zipFile.getFile().getName();
    }

    public static String ZIPdecompress(String source, String dest, String password) throws ZipException, IOException {
        ZipFile zipFile = new ZipFile(source);

        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }

        List<FileHeader> list = zipFile.getFileHeaders();
        zipFile.extractAll(dest);
        return list.get(0).getFileName();
    }

    public static String hashSHA256(String source) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(source);
    }

    /*
     * [START] Add DES and AES Encryption / Decryption with String returned
     * Value
     */
    public static String getAES(String source, String password, int mode) throws IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, FileNotFoundException, InvalidAlgorithmParameterException {
        String retVal = "";
        Key key;
        byte[] fileContent = null;
        key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParam = new IvParameterSpec(arrayOfByte2);
        if (mode == Cipher.ENCRYPT_MODE) {
            c.init(Cipher.ENCRYPT_MODE, key, ivParam);
            //Encrypting source with AES
            fileContent = c.doFinal(source.getBytes());
            //Encode Base 64 Required to get Formated String
            retVal = new BASE64Encoder().encode(fileContent);
        } else if (mode == Cipher.DECRYPT_MODE) {
            c.init(Cipher.DECRYPT_MODE, key, ivParam);
            //Decode Base 64 from Encrypted String
            fileContent = new BASE64Decoder().decodeBuffer(source);
            //Decrypting with AES
            fileContent = c.doFinal(fileContent);
            retVal = new String(fileContent);
        }
        return retVal;
    }

    public static String getDES(String source, String key, int mode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException {
        String retVal = "";
        byte[] fileContent = null;
        SecretKey skey = readDESkeyString(key);
        Cipher c = Cipher.getInstance("DESede");
        if (mode == Cipher.ENCRYPT_MODE) {
            c.init(Cipher.ENCRYPT_MODE, skey);
            //Encrypting source with DES
            fileContent = c.doFinal(source.getBytes());
            //Encode Base 64 Required to get Formated String
            retVal = new BASE64Encoder().encode(fileContent);
        } else if (mode == Cipher.DECRYPT_MODE) {
            c.init(Cipher.DECRYPT_MODE, skey);
            //Decode Base 64 from Encrypted String
            fileContent = new BASE64Decoder().decodeBuffer(source);
            //Decrypting with DES
            fileContent = c.doFinal(fileContent);
            retVal = new String(fileContent);
        }
        return retVal;
    }

    private static SecretKey readDESkeyString(String fileKey) throws FileNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] key = fileKey.getBytes();
        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        return keyFactory.generateSecret(keySpec);
    }
    /*
     * [START] Add DES and AES Encryption / Decryption with String returned
     * Value
     */
}
