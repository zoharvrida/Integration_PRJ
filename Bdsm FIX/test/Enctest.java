
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class Enctest {

    /**
     * @param args the command line arguments
     */
    static byte[] arrayOfByte2 = {22, 37, 47, 125, 116, 86, 41, 115, 57, 100, 83, 18, 101, 86, 71, 43};
    /**
     * 
     * @param args
     * @throws IOException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws IOException, InvalidKeyException {
        try {
            // TODO code application logic here
            Integer none = 2;
            System.out.println("HASIL :" + getAES("D@n@m0n!NTF5","AES@@@@@@@@@@@@@", 1));
            System.out.println("HASIL 2:" + getAES("+Oig2mX8cXJkQr+E50BM5g==","AES@@@@@@@@@@@@@", 2));
            System.out.println("PRINT :" + none.compareTo(2));
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Enctest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * 
     * @param source
     * @param password
     * @param mode
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     * @throws InvalidAlgorithmParameterException
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
}
