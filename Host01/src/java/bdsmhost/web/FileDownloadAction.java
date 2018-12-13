/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FileDownload;
import bdsmhost.dao.FileDownloadDao;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author v00019237
 */
public class FileDownloadAction extends BaseHostAction {

    private FileDownload model;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
		
		FileDownloadDao fDao = new FileDownloadDao(getHSession());
        String reportPath = getModel().getReportPath();
        String reportType = getModel().getReportType();
        String reportContent="";
		String reportJSON = null;
		try {
			reportJSON = fDao.getList(reportType.toUpperCase());
		} catch (Exception e) {
			getLogger().info("FAILED GET JSON_TAG");
			reportJSON = "failed";
		}
        FileDownload returnModel = new FileDownload();
        try {
            FileInputStream fs = new FileInputStream(new File(reportPath));
            byte[] tmp = IOUtils.toByteArray(fs);
            reportContent = new BASE64Encoder().encode(tmp);
        } catch (Exception ex) {
            getLogger().error(ex,ex);
            reportContent=ex.getMessage();
        }
        returnModel.setReportPath(reportPath);
        returnModel.setReportType(reportType);
        returnModel.setReportContent(reportContent);
		returnModel.setReportJSON(reportJSON);
        setModel(returnModel);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * @return the model
     */
    public FileDownload getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FileDownload model) {
        this.model = model;
    }
}
