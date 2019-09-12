/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import java.io.File;
import java.io.IOException;

import bdsm.model.FileUpload;
import bdsm.scheduler.PropertyPersister;

/**
 *
 * @author v00019372
 */
public class FileUploadAction extends BaseHostAction {

    /**
     * 
     */
    protected static final String DIR_FIX_IN;
    /**
     * 
     */
    protected static final String DIR_FIX_TEMP;
	private FileUpload model;

	static {
		DIR_FIX_IN = PropertyPersister.dirFixIn;
		DIR_FIX_TEMP = PropertyPersister.dirFixInTemp;
	}

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		Integer filesize = PropertyPersister.FILESIZEMAX;
		getLogger().info("FILESIZE :" + filesize);
		this.model.setSizeStatus(filesize.toString());
		return SUCCESS;// To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet."); // // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		this.getLogger().info("FileName : " + this.model.getFileName());
		this.getLogger().info("FileName String : " + this.model.getTheFileString());

		try {
			File writeFile = new File(DIR_FIX_IN, this.model.getFileName());
			org.apache.commons.io.FileUtils.writeByteArrayToFile(writeFile, new sun.misc.BASE64Decoder().decodeBuffer(this.model.getTheFileString()));
		} catch (IOException ex) {
			this.getLogger().error("Error Write File Upload for Filename : " + this.model.getFileName());
			this.getLogger().error(ex, ex);

			return ERROR;
		}

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * @return the model
	 */
	public FileUpload getModel() {
		return this.model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(FileUpload model) {
		this.model = model;
	}
}
