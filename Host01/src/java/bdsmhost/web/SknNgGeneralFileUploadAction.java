package bdsmhost.web;


import java.io.File;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.RegexFileFilter;

import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.DirectoryUtil;


/**
 *
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgGeneralFileUploadAction extends FileUploadAction {
	private FixLogDao fixLogDAO = new FixLogDao(null);

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		String[] stArr = this.getModel().getFileName().split("%"); // stArr[0] = "SKNIND", stArr[1] = fileName
		File file = new File(DIR_FIX_IN);
		String[] filenames = file.list(new RegexFileFilter(stArr[0] + ".*" + this.getModel().getIdMaintainedBy() + "_" + stArr[1], IOCase.INSENSITIVE));

		if (filenames.length > 0) {
			this.getModel().setFilePath("processing");
			this.setErrorCode("error.file.process");
		}
		else {
			fixLogDAO.setSession(this.getHSession());
			List<FixLog> fixLogList = fixLogDAO.getByFilenameAndDatePost(this.getModel().getFileName(), null);

			if (fixLogList.size() > 0) {
				this.getModel().setFilePath("uploaded");
				this.setErrorCode("error.file.already");
			}
		}

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		this.getLogger().info("FileName : " + this.getModel().getFileName());
		//this.getLogger().info("FileName String : " + this.getModel().getTheFileString());

		String result = SUCCESS;
		FixLog fixLog = null, fixLogClone;
		try {
			boolean flagProcess = true;
			File writeFile = new File(DIR_FIX_TEMP, this.getModel().getFileName());
			File writeFileToFix = new File(DIR_FIX_IN, this.getModel().getFileName());
			String realName = null;
			
			if (this.getModel().getSizeStatus().equalsIgnoreCase("LARGE")) {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(writeFile, new sun.misc.BASE64Decoder().decodeBuffer(this.getModel().getTheFileString()));
				String pathFile = FileUtil.getOriginalName(writeFileToFix.getPath());
				this.getLogger().info("PATH FILES : " + pathFile);
				DirectoryUtil myDir = new DirectoryUtil();
				try {
					myDir.unzipHost(writeFile.getPath(), writeFile.getParent(), pathFile);
					this.getLogger().info("SUCCESS UNZIP");
				}
				catch (Exception ex) {
					this.getLogger().error(ex, ex);
					flagProcess = false;
				}
				realName = FileUtil.getOriginalName(this.getModel().getFileName());
			}
			else {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(writeFileToFix, new sun.misc.BASE64Decoder().decodeBuffer(this.getModel().getTheFileString()));
				realName = this.getModel().getFileName();
			}
			
			if (flagProcess) {
				fixLogDAO.setSession(this.getHSession());
				List<FixLog> listFixLog;
				int counter = 0;

				if (!"LARGE".equalsIgnoreCase(this.getModel().getSizeStatus())) {
					do {
						if (counter == 30) {
							if (!StatusDefinition.REQUEST.equals(fixLog.getFlgProcess())) {
								counter = 0;
							}
							else {
								result = ERROR;
								this.setErrorCode("error.file.timeout");
								break;
							}
						}
						if (fixLog != null) {
							fixLogDAO.evictObjectFromPersistenceContext(fixLog);
						}
	
						//this.getLogger().info("GET FILE NAME : " + realName);
						listFixLog = fixLogDAO.getByFilenameAndDatePost(realName, null);
						if (listFixLog.size() > 0) {
							fixLog = listFixLog.get(0);
							if (StatusDefinition.DONE.equals(fixLog.getFlgProcess())) {
								break;
							}
							else if (StatusDefinition.ERROR.equals(fixLog.getFlgProcess())) {
								result = ERROR;
								this.setErrorCode(fixLog.getReason());
								break;
							}
						}
						
						try {
							counter++;
							Thread.sleep(10000L); // wait for 10 seconds
						}
						catch (InterruptedException iex) {}
					}
					while (true);
				} 
				
				/*
				 * Change fixLog inboxId
				 */
				if (fixLog != null) {
					fixLogDAO.evictObjectFromPersistenceContext(fixLog);
					String inboxId = fixLog.getFixLogPK().getInboxId();

					String[] stArr = realName.split("_"); // stArr[0] = "SKNIND", stArr[1] = batchNo, strArr[2] = userId, str[3] = real file name
					fixLogClone = (FixLog) BeanUtils.cloneBean(fixLog);
					fixLogClone.getFixLogPK().setInboxId(stArr[1]);
					fixLogDAO.insert(fixLogClone);

					fixLog = fixLogDAO.get(inboxId);
					fixLogDAO.delete(fixLog);
				}
			}

		}
		catch (Exception ex) {
			this.getLogger().error("Error Write File Upload for Filename : " + this.getModel().getFileName());
			this.getLogger().error(ex, ex);

			result = ERROR;
			this.setErrorCode(ex.getMessage());
		}

		return result;
	}
}
