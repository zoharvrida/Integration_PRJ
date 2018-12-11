/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author USER
 */
public class FCREncryption {

    private static final String THIS_COMPONENT_NAME = FCREncryption.class.getName();
    private static final String ERR_DES_KEY_LENGTH_MAX_BYTES_EXCEED = "Key Length For Algorithm Type DES Should Not Exceed 8 Bytes";
    private static final String ERR_KEY_LENGTH_MAX_BYTES_EXCEED = "Key Length Should Not Exceed 16 Bytes";
    private static final String ERR_FILE_RENAME_FAILED = "Renaming of file during Encryption/Decryption Operation Failed";
    private static final String MODE_CBC = "CBC";
    private static final String MODE_CFB = "CFB";
    private static final String MODE_OFB = "OFB";
    private static final String MODE_PCBC = "PCBC";
    private String algorithm;
    private String encryptionStyle = null;
    private String mode;
    private String padding;
    private int bufferSize = 1;
    Cipher decrCipher;
    Cipher encrCipher;
    BufferedReader buffReader = null;
    OutputStream out = null;
    PrintStream printStream = null;

    /**
     * 
     * @param paramString
     */
    public FCREncryption(String paramString) {
        String[] arrayOfString = paramString.split("/");
        this.encryptionStyle = paramString;
        this.algorithm = arrayOfString[0];

        if (arrayOfString.length > 1) {
            this.mode = arrayOfString[1];
        }
        if (arrayOfString.length > 2) {
            this.padding = arrayOfString[2];
        }
    }

    /**
     * 
     * @param paramString
     * @throws Exception
     */
    public void init(String paramString) throws Exception {
        SecretKey localSecretKey = generateSymmetricKey(paramString);
        try {
            this.encrCipher = Cipher.getInstance(this.encryptionStyle);
            this.decrCipher = Cipher.getInstance(this.encryptionStyle);

            if (this.mode != null) {
                if ((this.mode.equalsIgnoreCase("CBC")) || (this.mode.equalsIgnoreCase("CFB")) || (this.mode.equalsIgnoreCase("OFB")) || (this.mode.equalsIgnoreCase("PCBC"))) {
                    AlgorithmParameterSpec localAlgorithmParameterSpec = createInitVector(this.mode, this.algorithm);

                    this.encrCipher.init(1, localSecretKey, localAlgorithmParameterSpec);

                    this.decrCipher.init(2, localSecretKey, localAlgorithmParameterSpec);
                }

            } else {
                this.encrCipher.init(1, localSecretKey);
                this.decrCipher.init(2, localSecretKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SecretKey generateSymmetricKey(String paramString) throws Exception {
        byte[] arrayOfByte = paramString.getBytes();

        if ((this.algorithm.equalsIgnoreCase("DES")) && (paramString.length() > 8)) {
            throw new Exception("Key Length For Algorithm Type DES Should Not Exceed 8 Bytes");
        }

        if ((!(this.algorithm.equalsIgnoreCase("DES"))) && (paramString.length() > 16)) {
            throw new Exception("Key Length Should Not Exceed 16 Bytes");
        }

        SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte, this.algorithm);
        return localSecretKeySpec;
    }

    private AlgorithmParameterSpec createInitVector(String paramString1, String paramString2) {
        Object localObject = null;

        byte[] arrayOfByte1 = {52, 83, 78, 44, 16, 117, 7, 41};

        byte[] arrayOfByte2 = {22, 37, 47, 125, 116, 86, 41, 115, 57, 100, 83, 18, 101, 86, 71, 43};

        if ("AES".equals(this.algorithm)) {
            if (paramString1.equalsIgnoreCase("PCBC")) {
                localObject = new PBEParameterSpec(arrayOfByte2, 20);
            } else {
                localObject = new IvParameterSpec(arrayOfByte2);
            }
        } else if (paramString1.equalsIgnoreCase("PCBC")) {
            localObject = new PBEParameterSpec(arrayOfByte1, 20);
        } else {
            localObject = new IvParameterSpec(arrayOfByte1);
        }

        return ((AlgorithmParameterSpec) localObject);
    }

    /**
     * 
     * @param paramArrayOfByte
     * @param paramOutputStream
     * @throws Exception
     */
    public void decrypt(byte[] paramArrayOfByte, OutputStream paramOutputStream)
            throws Exception {
        try {
            paramOutputStream = new CipherOutputStream(paramOutputStream, this.decrCipher);
            paramOutputStream.write(paramArrayOfByte);
            paramOutputStream.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 
     * @param paramInputStream
     * @param paramOutputStream
     * @throws Exception
     */
    public void decrypt(InputStream paramInputStream, OutputStream paramOutputStream)
            throws Exception {
        byte[] arrayOfByte = new byte[this.bufferSize];
        try {
            int i = paramInputStream.available();
            paramInputStream = new CipherInputStream(paramInputStream, this.decrCipher);

            int j = 0;
            if (i > 0) {
                while ((j = paramInputStream.read(arrayOfByte)) >= 0) {
                    paramOutputStream.write(arrayOfByte, 0, j);
                }
            }
            paramOutputStream.close();
            paramInputStream.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 
     * @param paramString1
     * @param paramString2
     * @return
     * @throws Exception
     */
    public String decrypt(String paramString1, String paramString2)
            throws Exception {
        init(paramString1);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        decrypt(new BASE64Decoder().decodeBuffer(paramString2), localByteArrayOutputStream);
        return localByteArrayOutputStream.toString();
    }

    /**
     * 
     * @param paramString
     * @param paramFile1
     * @param paramFile2
     * @throws Exception
     */
    public void decryptFile(String paramString, File paramFile1, File paramFile2)
            throws Exception {
        init(paramString);
        File[] arrayOfFile = initiliazeDecryption(paramFile1, paramFile2);

        paramFile1 = arrayOfFile[0];
        paramFile2 = arrayOfFile[1];

        setBufferSize(paramFile1);
        try {
            String str = paramFile2.getParent();

            boolean bool = new File(str).mkdirs();

            if (bool) {
                
            }

            decrypt(new FileInputStream(paramFile1), new FileOutputStream(paramFile2));
        } catch (FileNotFoundException localFileNotFoundException) {
        } finally {
            if (paramFile1.getName().endsWith("~1")) {
                paramFile1.delete();
            }
        }
    }

    /**
     * 
     * @param paramArrayOfByte
     * @param paramOutputStream
     * @throws Exception
     */
    public void encrypt(byte[] paramArrayOfByte, OutputStream paramOutputStream)
            throws Exception {
        try {
            paramOutputStream = new CipherOutputStream(paramOutputStream, this.encrCipher);
            paramOutputStream.write(paramArrayOfByte);
            paramOutputStream.close();
        } catch (Exception localIOException) {
            throw new Exception(localIOException);
        }
    }

    /**
     * 
     * @param paramInputStream
     * @param paramOutputStream
     * @throws Exception
     */
    public void encrypt(InputStream paramInputStream, OutputStream paramOutputStream)
            throws Exception {
        byte[] arrayOfByte = new byte[this.bufferSize];
        try {
            paramOutputStream = new CipherOutputStream(paramOutputStream, this.encrCipher);

            int i = 0;
            int j = paramInputStream.available();
            if (j > 0) {
                while ((i = paramInputStream.read(arrayOfByte)) >= 0) {
                    paramOutputStream.write(arrayOfByte, 0, i);
                }
            }
            paramOutputStream.close();
            paramInputStream.close();
        } catch (Exception localIOException) {
            throw new Exception(localIOException);
        }
    }

    /**
     * 
     * @param paramString1
     * @param paramString2
     * @return
     * @throws Exception
     */
    public String encrypt(String paramString1, String paramString2)
            throws Exception {
        init(paramString1);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        encrypt(paramString2.getBytes(), localByteArrayOutputStream);
        return new BASE64Encoder().encode(localByteArrayOutputStream.toByteArray());
    }

    /**
     * 
     * @param paramString
     * @param paramFile1
     * @param paramFile2
     * @throws Exception
     */
    public void encryptFile(String paramString, File paramFile1, File paramFile2)
            throws Exception {
        init(paramString);
        File[] arrayOfFile = initializeEncryption(paramFile1, paramFile2);

        paramFile1 = arrayOfFile[0];
        paramFile2 = arrayOfFile[1];

        setBufferSize(paramFile1);
        try {
            String str = paramFile2.getParent();

            boolean bool = new File(str).mkdirs();

            if (bool) {
                
            }

            encrypt(new FileInputStream(paramFile1), new FileOutputStream(paramFile2));
        } catch (FileNotFoundException localFileNotFoundException) {
            throw new Exception(localFileNotFoundException);
        } finally {
            if (paramFile1.getName().endsWith("~1")) {
                paramFile1.delete();
            }
        }
    }

    private void setBufferSize(File paramFile) {
        long l = paramFile.length();
        this.bufferSize = (int) ((l > 8192L) ? 8192L : l);
    }

    /**
     * 
     * @param paramString
     * @param paramFile
     * @return
     * @throws Exception
     */
    public BufferedReader fetchEncryptedFileReader(String paramString, File paramFile)
            throws Exception {
        init(paramString);
        try {
            FileInputStream localFileInputStream = new FileInputStream(paramFile);
            CipherInputStream localCipherInputStream = new CipherInputStream(localFileInputStream, this.decrCipher);
            this.buffReader = new BufferedReader(new InputStreamReader(localCipherInputStream));
        } catch (FileNotFoundException localFileNotFoundException) {
            throw new Exception(localFileNotFoundException);
        }

        return this.buffReader;
    }

    /**
     * 
     * @param paramString
     * @param paramFile
     * @return
     * @throws Exception
     */
    public PrintStream fetchEncryptedOutputFileStream(String paramString, File paramFile)
            throws Exception {
        try {
            FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
            this.printStream = new PrintStream(new CipherOutputStream(localFileOutputStream, this.encrCipher));
        } catch (FileNotFoundException localFileNotFoundException) {
            throw new Exception(localFileNotFoundException);
        }

        return this.printStream;
    }

    private File[] initializeEncryption(File paramFile1, File paramFile2)
            throws Exception {
        File[] arrayOfFile = {paramFile1, paramFile2};
        if (checkIfSameFile(paramFile1, paramFile2)) {
            if (paramFile2 == null) {
                arrayOfFile[1] = new File(paramFile1.getAbsolutePath());
            }

            File localFile = new File(paramFile1.getAbsolutePath() + "~1");

            boolean bool = paramFile1.renameTo(localFile);

            if (!(bool)) {
                throw new Exception("Renaming of file during Encryption/Decryption Operation Failed");
            }

            arrayOfFile[0] = localFile;
        }

        return arrayOfFile;
    }

    private boolean checkIfSameFile(File paramFile1, File paramFile2) {
        return ((paramFile2 == null) || (paramFile1.getName().equalsIgnoreCase(paramFile2.getName())));
    }

    private File[] initiliazeDecryption(File paramFile1, File paramFile2)
            throws Exception {
        File[] arrayOfFile = {paramFile1, paramFile2};

        if (checkIfSameFile(paramFile1, paramFile2)) {
            if (paramFile2 == null) {
                arrayOfFile[1] = new File(paramFile1.getAbsolutePath());
            }

            String str = "";

            File localFile = new File(str + File.separator + paramFile1.getName() + "~1");

            boolean bool = paramFile1.renameTo(localFile);

            if (!(bool)) {
                throw new Exception("Renaming of file during Encryption/Decryption Operation Failed");
            }

            arrayOfFile[0] = localFile;
        }
        return arrayOfFile;
    }
}
