/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

/**
 *
 * @author v00019722
 */
public class DirectoryUtil {
    
    private static final Logger logger = Logger.getLogger(DirectoryUtil.class);
    
    public static void Begin(File directoryZip, File newDirectory, String format) {
        logger.info("directoryZIP_B :" + directoryZip);
        logger.info("NewZIP_B :" + newDirectory);
        logger.info("format :" + format);
        List<File> Filelist = new ArrayList<File>();
        getAllFiles(directoryZip, Filelist);
        try {
            writeZipFiles(directoryZip, Filelist, newDirectory);
        } catch (ZipException zipException) {
            logger.info("ZIP EXCEPTION :" + zipException);
        }
    }

    public static void getSpecificFiles(File directoryZip,String format, File Destination){
        
        File[] listAfter = Destination.listFiles();
        File[] list = directoryZip.listFiles();
        File gFile = new File(format);
        int counter = 0;
        if(list!=null)
        for (File fil : list)
        {
            counter++;
            if (fil.isDirectory())
            {
                getSpecificFiles(fil,format,Destination);
            }
            else if (("\\" + format + "*").matches(fil.getName()))
            {
                try {
                    listAfter[counter] = fil;
                    //FileUtils.copyFile(fil,Destination);
                    FileUtils.copyFileToDirectory(fil, Destination, true);
                    File destFile = new File(Destination.toString() + "/" + fil.toString());
                    destFile.renameTo(gFile);
                    System.out.println("File to copy :" + fil);
                    System.out.println("File to Rename :" + gFile);
                } catch (IOException ex) {
                    
                }
                    
            }
        }
    }
    
    public static File RegularFile(File directoryZip, String format, File Destination) throws IOException {

        String fileName = null;
        File toDownload = directoryZip;
        boolean bName = false;
        int iCount = 0;
        int dCount = 0;
        File dir = directoryZip;
        File[] files = dir.listFiles();
        File[] destination = Destination.listFiles();
        
        System.out.println("List Of Files ::");

        for (File f : files) {

            fileName = f.getName();
            System.out.println(fileName);

            Pattern uName = Pattern.compile(format);
            Matcher mUname = uName.matcher(fileName);
            bName = mUname.matches();
            if (bName) {
                iCount++;
                FileUtils.copyFileToDirectory(f, Destination, true);
            }
        }
        System.out.println("File Count In Folder ::" + iCount);
        
        for(File g : destination) {
            
            fileName = g.getName();
            System.out.println(fileName);

            Pattern uName = Pattern.compile(format+"zip");
            Matcher mUname = uName.matcher(fileName);
            bName = mUname.matches();
            if (bName) {
                dCount++;
                File renameG = new File(g.getAbsoluteFile().getName());
                g.renameTo(renameG);
            }
        }
        
        System.out.println("File Count In Folder ::" + destination + dCount);
        return toDownload;
    }
    
   private static void getAllFiles(File dir, List<File> Filelist) {
        try{
          File[] files = dir.listFiles();
          logger.info("All_files :" + files);
          for (File file : files){
              Filelist.add(file);
              if(file.isDirectory()){
                  System.out.println("directory :" + file.getCanonicalPath());
                  logger.info("format :" + file);
                  getAllFiles(file, Filelist);
              } else {
                  System.out.println("File : "+ file.getCanonicalPath());
              }
          }     
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    private static void writeZipFiles(File directoryZip, List<File> Filelist, File newDirectory) throws ZipException {
        try{
            FileOutputStream fos = new FileOutputStream(newDirectory + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            System.out.println("check file : " + directoryZip.getName() + ".zip");
            
            for(File file : Filelist ){
                if(!file.isDirectory()) {
                    System.out.println("directory :" + directoryZip);
                    System.out.println("file :" + file);
                    addToZip(directoryZip,file,zos);            
                }
            }
            zos.close();
            fos.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void addToZip(File directoryZip, File file, ZipOutputStream zos)  {
        
        try {
            FileInputStream fis = new FileInputStream(file);
            String zipFilepath = file.getCanonicalPath().substring(directoryZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
            ZipEntry zipEntry = new ZipEntry(zipFilepath);
            System.out.println("Zip path :" + zipFilepath);
            zos.putNextEntry(zipEntry);
            
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            fis.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        
    }

	public File WebZipping(String Filename, File source) throws Exception {
        String kEYEncrypt = null;
        String encryptedContent = null;
		File readyFile = null;
		getLogger().info("SOURCE DEC :" + source.getParent());
		getLogger().info("SOURCE FILE :" + Filename);
        try {
			readyFile = new File(source.getParent(), Filename + ".zip");
			EncryptionUtil.ZIPcompress(source.getPath(), readyFile.getPath(), kEYEncrypt);
			
            encryptedContent = readyFile.getPath();
			getLogger().info("HASIL COMPRESS : " + encryptedContent);
            //encryptedContent = this.getTheFileAsString(readyFile);
        } catch (Exception zipException) {
            getLogger().error("ERROR REASON   : " + zipException);
            throw zipException;
        }
		return readyFile;
    }

	public String unzipHost(String fileTemp, String FileFix, String Filename) throws Exception {
        String newKey = null;
		String Hasil = null;
		try {
			Hasil = EncryptionUtil.ZIPdecompress(fileTemp, FileFix, newKey);

			File dstination = new File(FileFix + "/" + Hasil);
			getLogger().info("MPOVE : " + dstination.getPath());
			File process = new File(Filename);
			FileUtils.moveFile(dstination, process);
		} catch (ZipException zipException) {
			getLogger().error(zipException,zipException);
		} catch (IOException iOException) {
			getLogger().error(iOException,iOException);
		}
        delFile(fileTemp);
        return Hasil;
    }

    public static String getTheFileAsString(File theFile) {
        try {
            return new BASE64Encoder().encode(IOUtils.toByteArray(new FileInputStream(theFile)));
        } catch (IOException ex) {
            return null;
        }
    }

    public static void delFile(String filename) throws Exception {
        File file = new File(filename);
        FileUtils.deleteQuietly(file);
    }

    protected Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }
}
