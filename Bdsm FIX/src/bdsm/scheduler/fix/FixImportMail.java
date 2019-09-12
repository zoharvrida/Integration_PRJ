/*
 * CHANGE LOG
 * [ENH01] by Titus : Add Process Source to Context
 */
package bdsm.scheduler.fix;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import microsoft.exchange.webservices.data.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sun.misc.BASE64Encoder;
import bdsm.model.BaseModel;
import bdsm.scheduler.*;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.scheduler.processor.MailObjectReflection;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.PatternUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.BaseDao;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author bdsm
 */
public class FixImportMail extends BDSMScheduler2 {

	private static final String filePath = PropertyPersister.dirFixInProc;
	private static final String adminEmail = PropertyPersister.adminEmail;

    /**
     * 
     * @param timeout
     * @param sleep
     * @param btsleep
     * @param threadcount
     */
    public FixImportMail(long timeout, long sleep, long btsleep, int threadcount) {
		super(timeout, sleep, btsleep, threadcount);
        getLogger().info("--->>> Import Watcher <<<---");
		this.maxItem = threadcount;
	}

    /**
     * 
     */
    @Override
	protected void execute() {
		try {
            try {
                getLogger().trace(System.getenv());
            } catch (Exception e) {
                getLogger().debug("ENVIRONMENT VARIABLE DOESN'T EXIST : EMAIL");
            }
            
			FindItemsResults<Item> findResults = getUnreadMessages();
            getLogger().info("--->>> UNREAD MESSAGE : " + findResults);
			if (findResults != null) {
				try {
					this.session = HibernateUtil.getSession();
					this.fixInboxDao = new FixInboxDao(this.session);
					this.fixLogDao = new FixLogDao(this.session);

					getLogger().info("--->>> Begin Processing New Messages <<<---");
					int i = 1;
					for (Item item : findResults.getItems()) {
						getLogger().info("Processing New Message no : " + i);
						try {
							processMail(item);
						} catch (Exception ex) {
							getLogger().info("Error Processing New Message no : " + i);
							getLogger().error(ex);
						}
						getLogger().info("Finish Processing New Message no : " + i);
						i++;
					}
				} catch (Exception ee) {
					getLogger().error(ee, ee);
				} finally {
					HibernateUtil.closeSession(this.session);
					getLogger().info("--->>> Finish Processing New Messages <<<---");
				}
			} else {
				getLogger().info("--->>> No New Message <<<---");
			}
		} catch (Exception ex) {
			getLogger().error("Failed Getting Messages");
			getLogger().error(ex, ex);
			try {
				MailUtil.sentNewMessage(
						adminEmail,
						"",
						"[FIX ALERT]Error Getting Email",
						"Error Getting Email <br/><br/>Thanks & regards,<br/>- BDSM -<br/><br/>--Begin Message--<br/>"
								+ reformatErrorMessage(ex)
								+ "<br/>--End Message--", 
						null,
						session,
						ScheduleDefinition.emailOnly);
			} catch (Exception ex1) {
				getLogger().error("Error Sending Message to Fix Admin");
				getLogger().error(ex1, ex1);
			}
		}
	}

	// get unread message
	private FindItemsResults getUnreadMessages() throws Exception {
		ItemView itemView = new ItemView(this.maxItem);
		SearchFilter sf = new SearchFilter.SearchFilterCollection(LogicalOperator.And, 
				new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false)
		);
		getLogger().info("Begin search inbox");
		if (MailServiceInstance.getService() == null) {
			getLogger().info("getService Instance Null");
		}
		FindItemsResults<Item> findResults = null;
		try {
			findResults = MailServiceInstance.getService().findItems(WellKnownFolderName.Inbox, sf, itemView);
		} catch (Exception ee) {
			getLogger().error("Find Items Error");
			getLogger().error(ee, ee);
		}
		getLogger().info("Search inbox success");
		int unread = 0;
		if (findResults != null) {
			unread = findResults.getTotalCount();
		}
		getLogger().info("Search Result Finish with total unread : " + unread);
		if (unread == 0) {
			return null;
		}
		return findResults;
	}

	// process mail
	private void processMail(Item item) throws Exception {
		this.exist = false;
		this.emailMessage = (EmailMessage) item;
		this.fixInbox = new FixInbox();
		this.customResult1 = new CustomResult1();
		this.fixLog = new FixLog();

		try {
			this.emailMessage.load();
			getLogger().info("Message ID " + emailMessage.getInternetMessageId());
			// save to inbox
			try {
				saveFixInbox();
			} catch (HibernateException he) {
				registerToFixQXtract(he);
				throw new Exception(he);
			}
			// set email as read
			// this.emailMessage.setIsRead(true);
			// this.emailMessage.update(ConflictResolutionMode.AlwaysOverwrite);

		} catch (Exception ex) {
			getLogger().error("Load Message : " + ex, ex);
		}
		FixEmailAccess fixEmailAccess = null;
		try {
			FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(this.session);
			FixEmailAccessPK fixEmailAccessPK = new FixEmailAccessPK();
			fixEmailAccessPK.setSender(this.emailMessage.getSender().getAddress());
			List<FixEmailAccess> list = fixEmailAccessDao.get(fixEmailAccessPK);
			if (list.size() == 0) {
				getLogger().error("Sender not registered");
				getLogger().error("Address : " + this.emailMessage.getSender().getAddress());
				getLogger().error("Subject : " + this.emailMessage.getSubject());

				if (!this.emailMessage.getSender().getAddress().toLowerCase().startsWith("microsoftexchange")) {
					MailUtil.sentNewMessage(adminEmail, 
								"", 
								"[FIX ALERT] UnRegistered Sender", 
								"Sender Not Registered : " + this.emailMessage.getSender().getAddress(), 
								null, 
								session, 
								ScheduleDefinition.emailOnly);
				}
				this.emailMessage.delete(DeleteMode.HardDelete);
				throw new Exception("Sender not registered");
			}
			fixEmailAccess = list.get(0);
		} catch (Exception ex) {
			this.fixInbox.setFlgProcess(StatusDefinition.ERROR);
			this.fixInbox.setReason(ex.getMessage());
			update(this.fixInboxDao, this.fixInbox);
			throw new Exception(ex);
		}

		// validasi dan execute class
		try {
			// validasi
			validateEmail(this.fixInbox.getSender(), this.fixInbox.getFixInboxPK().getSubject(), this.fixInbox.getEmailAttachment());

			if (!this.customResult1.getItemIdLink().equals("")) {
				this.fixInbox.setItemIdLink(this.customResult1.getItemIdLink());
				this.exist = true;
			}
			// save to log
			saveFixLog(this.exist);

			HashMap hashMap = new HashMap();

			// assign class to back thread
			MailObjectReflection objectReflection = new MailObjectReflection();
			// put necessary parameter
			hashMap.put(MapKey.javaClass, this.customResult1.getJavaClass());
			getLogger().debug("Java Class : " + this.customResult1.getJavaClass());
			hashMap.put(MapKey.templateName, this.customResult1.getNamTemplate());
			getLogger().debug("Template Name : " + this.customResult1.getNamTemplate());
			hashMap.put(MapKey.idScheduler, this.customResult1.getIdScheduler());
			getLogger().debug("ID Scheduler : " + this.customResult1.getIdScheduler());
			hashMap.put(MapKey.status, this.customResult1.getParam1());
			getLogger().debug("Status : " + this.customResult1.getParam1());
			hashMap.put(MapKey.param1, this.fixInbox.getFixInboxPK().getSubject());
			getLogger().debug("Subject/Param2 : " + this.fixInbox.getFixInboxPK().getSubject());
			hashMap.put(MapKey.param2, this.fixInbox.getSender());
			if (fixInbox.getFlgAttach() != null && this.fixInbox.getFlgAttach().equals("Y")) {
				if (customResult1.getNewFilename() != null) {
					hashMap.put(MapKey.param5, this.filePath + this.customResult1.getNewFilename());
					getLogger().debug("Param5/Filename : " + this.filePath + this.customResult1.getNewFilename());
					hashMap.put(MapKey.fileName, this.customResult1.getNewFilename());
				} else {
					hashMap.put(MapKey.param5, this.filePath + this.fixInbox.getEmailAttachment());
					getLogger().debug("Param5/Filename : " + this.filePath + this.fixInbox.getEmailAttachment());
					hashMap.put(MapKey.fileName, this.fixInbox.getEmailAttachment());
				}
			}
			hashMap.put(MapKey.filePath, this.filePath);
			hashMap.put(MapKey.inboxId, this.fixInbox.getFixInboxPK().getInboxId());
			getLogger().debug("Inbox ID : " + this.fixInbox.getFixInboxPK().getInboxId());
			hashMap.put(MapKey.itemIdLink, this.customResult1.getItemIdLink());
			getLogger().debug("Item ID Link : " + this.customResult1.getItemIdLink());
			if (this.customResult1.getCdAccess().equals("100")) {
				hashMap.put(MapKey.batchNo, this.fixInbox.getNoBatch());
				getLogger().debug("Batch No : " + this.fixInbox.getNoBatch());
			} else if (this.customResult1.getCdAccess().equals("010")) {
				hashMap.put(MapKey.batchNo, this.customResult1.getBatchNo());
				getLogger().debug("Batch No : " + this.customResult1.getBatchNo());
			}
			hashMap.put(MapKey.grpId, this.customResult1.getGrpId());
			getLogger().debug("Group ID : " + this.customResult1.getGrpId());
			hashMap.put(MapKey.cdAccess, this.customResult1.getCdAccess());
			getLogger().debug("Access Code : " + this.customResult1.getCdAccess());
			hashMap.put(MapKey.spvAuth, this.customResult1.getSpvAuth());
			getLogger().debug("SPV AUTH : " + this.customResult1.getSpvAuth());
			// Set Source Process to Context [ENH01]
			hashMap.put(MapKey.processSource, "EMAIL");
			getLogger().debug("PROCESS SOURCE : EMAIL");
			// set type fix
			hashMap.put(MapKey.typeFix, StatusDefinition.IN);
			getLogger().debug("Type FIX :" + StatusDefinition.IN);
			// set email sender
			hashMap.put(MapKey.emailSender, this.fixInbox.getSender());
			getLogger().debug("Email sender :" + this.fixInbox.getSender());
			// set flg checksum
			hashMap.put(MapKey.flgChecksum, this.customResult1.getFlgChecksum());
			getLogger().debug("Flg Checksum :" + this.customResult1.getFlgChecksum());
			// set file pattern
			hashMap.put(MapKey.filePattern, this.customResult1.getFilePattern());
			getLogger().debug("File Pattern :" + this.customResult1.getFilePattern());
			// set file extension
			hashMap.put(MapKey.importFileExtension, this.customResult1.getFileExtension());
			getLogger().debug("File Extension :" + this.customResult1.getFileExtension());
			
			
			// assign to back thread
			getLogger().debug("Assign to back thread");
			if (this.assign(hashMap, objectReflection) != null) {
				// delete exchange inbox if assign backthread null
				getLogger().info("Delete exchange inbox");
				this.emailMessage.delete(DeleteMode.HardDelete);
			} else {
				getLogger().info("Not Enough Thread, message set to unread to be process in next loop");
				this.emailMessage.setIsRead(false);
			}
		} catch (FIXException ex) {
			// delete email
			if (ex.isDeleteSourceData()) {
				this.emailMessage.delete(DeleteMode.HardDelete);
			} else {
				this.emailMessage.setIsRead(false);
			}
			// Error jika validasi gagal.
			getLogger().error("Validation failed for " + this.fixInbox.getFixInboxPK().getSubject() + " reason : " + ex.getMessage(), ex);
			doWhenError(ex, true);
		} catch (Exception ex) {
			// Global Java Exception
			getLogger().error(ex, ex);
			doWhenError(ex, false);
		}

	}

	private void saveFixInbox() throws ServiceLocalException {
		AttachmentCollection attachmentCollection;
		String batchNo = SchedulerUtil.generateUUID();
		getLogger().info("Save to FixInbox");
		this.fixInbox.setSender(this.emailMessage.getSender().getAddress());
		try {
			this.fixInbox.setFixInboxPK(new FixInboxPK(this.emailMessage.getInternetMessageId(), 
					this.emailMessage.getSubject().replaceAll(" {2,}", " ").trim(), SchedulerUtil.getDate("dd/MM/yyyy")));
		} catch (ParseException ex) {
			getLogger().error(ex, ex);
		}
		try {
			this.fixInbox.setFlgProcess(StatusDefinition.REQUEST);
			this.fixInbox.setDtmReceive(new Timestamp(this.emailMessage.getDateTimeReceived().getTime()));
		} catch (Exception ex) {
			getLogger().error(ex.toString(), ex);
		}
		if (this.emailMessage.getHasAttachments()) {
			this.fixInbox.setFlgAttach("Y");
			attachmentCollection = this.emailMessage.getAttachments();
			this.fileAttachment = (FileAttachment) attachmentCollection.getPropertyAtIndex(0);
			this.fixInbox.setEmailAttachment(this.fileAttachment.getName());
		} else {
			this.fixInbox.setFlgAttach("N");
		}
		this.fixInbox.setNoBatch(batchNo);
		save(this.fixInboxDao, this.fixInbox);
		getLogger().info("Successfully save to FixInbox");
	}

	private void validateEmail(String sender, String subject, String filename) throws FIXException, Exception {
		getLogger().info("Validating...");
		// String[] ss = subject.replaceAll(" ", "\"").split("\"",2);
		String splitSubject = subject.replaceFirst("[R|r][E|e]: ", "");
		String[] ss = splitSubject.split(" ", 2);
		if (ss.length < 2) {
			// throw new FIXException("INVALID EMAIL");
			throw new FIXException("Subject Invalid");
		}

		String template;

		try {
			template = ss[ss.length - 2].trim().substring(0, 6);
		} catch (StringIndexOutOfBoundsException ex) {
			// throw new FIXException("INVALID EMAIL");
			throw new FIXException(ex);
		}

		// validate duplicate batch in same day
		getLogger().info("Cek Duplicate for Subject : " + subject);
		FixInbox fb = this.fixInboxDao.getDuplicateSameDay(subject, this.emailMessage.getInternetMessageId());
		if (fb != null) {
			getLogger().info("Duplicate Batch File " + fb.getFixInboxPK().getSubject());
			// throw new FIXException("INVALID EMAIL");
			throw new FIXException("Duplicate Batch File " + fb.getFixInboxPK().getSubject());
		}

		FixTemplateMasterDao fixTemplateMasterDao = new FixTemplateMasterDao(this.session);

		getLogger().info("Sender : " + sender);
		getLogger().info("Template : " + template);
		// Validate Template
		try {
			this.customResult1 = fixTemplateMasterDao.getAccessTemplate(sender, template, "I");
		} catch (Exception e) {
			getLogger().error(e, e);
			getLogger().info("INVALID ACCESS, FAILED GET SCHEDULER IMPORT");
			throw new FIXException("INVALID ACCESS, FAILED GET SCHEDULER IMPORT");
		}

		if (this.customResult1 == null) {
			getLogger().info("INVALID ACCESS, NO SCHEDULER DEFINED FOR THIS EMAIL");
			// throw new FIXException("INVALID EMAIL");
			throw new FIXException("INVALID ACCESS, NO SCHEDULER DEFINED FOR THIS EMAIL");
		}

		if ("I".equalsIgnoreCase(this.customResult1.getFlgStatus())) {
			getLogger().info("SCHEDULER FOR THIS EMAIL INACTIVE");
			// throw new FIXException("INVALID EMAIL");
			throw new FIXException("SCHEDULER FOR THIS EMAIL INACTIVE", false);
		}

		// Access Validation
		// REQUESTER
		if (this.customResult1.getCdAccess().equals("100")) {
			this.customResult1.setRequester(sender);
			this.customResult1.setParam1(StatusDefinition.UNAUTHORIZED);
			this.customResult1.setFlgAuth(StatusDefinition.UNAUTHORIZED);
			this.customResult1.setItemIdLink("");
			// file validation
			if ("Y".equalsIgnoreCase(this.fixInbox.getFlgAttach())) {
				String sbjFnameDecoded = URLDecoder.decode(ss[ss.length - 1].trim(), "UTF-8");
				String filenameDecoded = URLDecoder.decode(filename, "UTF-8");
				String patternDecoded = URLDecoder.decode(this.customResult1.getFilePattern() + ".{0,}", "UTF-8");
				getLogger().info("sbjFnameDecoded : " + sbjFnameDecoded);
				getLogger().info("filenameDecoded : " + filenameDecoded);
				getLogger().info("patternDecoded : " + patternDecoded);
				if (!sbjFnameDecoded.equals(filenameDecoded)) {
					getLogger().info("INVALID FILENAME CHECK 1");
					// throw new FIXException("INVALID EMAIL");
					throw new FIXException("INVALID FILENAME CHECK 1");
				}

				PatternUtil ptrn = new PatternUtil(patternDecoded);
				if (!ptrn.match(filenameDecoded)) {
					getLogger().info("INVALID FILENAME CHECK 2");
					// throw new FIXException("INVALID EMAIL");
					throw new FIXException("INVALID FILENAME CHECK 2");
				}
			}
		} // SPV
		else if (this.customResult1.getCdAccess().equals("010")) {
			this.fixInboxDao = new FixInboxDao(this.session);
			this.customResult1.setSpv(sender);

			FixInbox fixInbox1 = this.fixInboxDao.getItemIdLink(subject.replaceFirst("[R|r][E|e]: ", ""));
			if (fixInbox1 == null) {
				getLogger().info("INVALID LINK");
				// throw new FIXException("INVALID EMAIL");
				throw new FIXException("INVALID LINK");
			}

			String itemId = fixInbox1.getFixInboxPK().getInboxId();

			String body = "";
			try {
				if (this.emailMessage.getBody() != null) {
					String result = MessageBody.getStringFromMessageBody(this.emailMessage.getBody());
					getLogger().debug("Email Body Type : " + this.emailMessage.getBody().getBodyType());
					getLogger().debug("Body Email : " + result);
					if (this.emailMessage.getBody().getBodyType().equals(BodyType.HTML)) {
						result = result
								.replaceAll("\n", "~")
								.replaceAll("</p", "~</p")
								.replaceAll("</div", "~</div")
								.replaceFirst(".{0,}/head>", "")
								.replaceFirst("</html>.{0,}", "\"")
								.replaceAll("<[^>]*>", "\t")
								.replaceAll("\"", "");
					} else {
						result = result.replaceAll("\n", "~");
					}
					result = result.replaceAll("[\\t {2,}]", " ");
					getLogger().debug("Email Result : " + result);
					StringTokenizer st = new StringTokenizer(result, "~");
					getLogger().debug("ST Count Token : " + st.countTokens());

					String tmpBody = "";
					for (int stCount = 0; stCount < st.countTokens(); stCount++) {
						tmpBody = st.nextToken().trim();
						if (tmpBody.length() > 0) {
							body = tmpBody;
							break;
						}
					}
					
					/* eliminate byte -96 and space from string body  */
					byte[] bArr = body.getBytes();
					List<Byte> bList = new java.util.ArrayList<Byte>();
					for (int i=0; i<bArr.length; i++)
						if (bArr[i] > 0)
							bList.add(bArr[i]);
					bArr = new byte[bList.size()];
					for (int i=0; i<bArr.length; i++)
						bArr[i] = bList.get(i);
					body = new String(bArr).trim();
					
					getLogger().debug("Body : " + body);
				}
			} catch (ServiceLocalException ex) {
				getLogger().error(ex);
			}

			this.customResult1.setFlgAuth(StatusDefinition.AUTHORIZED);
			this.customResult1.setItemIdLink(itemId);
			this.customResult1.setBatchNo(fixInbox1.getNoBatch());
			// spv approval

			if (body.toLowerCase().equalsIgnoreCase(StatusDefinition.getOkWords(body))) {
				getLogger().debug(">>> " + this.customResult1.getItemIdLink()+ " Authorized <<<");
				this.customResult1.setParam1(StatusDefinition.AUTHORIZED);
			} else if (body.toLowerCase().equalsIgnoreCase(StatusDefinition.getNotOkWords(body))) {
				getLogger().debug(">>> " + this.customResult1.getItemIdLink() + " Rejected <<<");
				this.customResult1.setParam1(StatusDefinition.REJECTED);
			} else {
				getLogger().debug(">>> " + this.customResult1.getItemIdLink() + "Ignored <<<");
				this.customResult1.setParam1(StatusDefinition.IGNORED);
			}

		} else {
			getLogger().info("INVALID ACCESS");
			throw new FIXException("INVALID ACCESS");
		}
		getLogger().info("Done validating.");
	}

	// if exists will update fixlog
	private void saveFixLog(boolean exists) throws FIXException, ParseException {
		getLogger().info("Save to FixLog");
		if (exists) {
			this.fixInbox.setItemIdLink(this.customResult1.getItemIdLink());
			update(this.fixInboxDao, this.fixInbox);
			this.fixLog = this.fixLogDao.get(this.fixInbox.getItemIdLink());
			this.fixLog.setFlgAuth(this.customResult1.getFlgAuth());
			this.fixLog.setSenderSpv(this.fixInbox.getSender());
			this.fixLog.setDtmAuth(this.fixInbox.getDtmReceive());
			update(this.fixLogDao, this.fixLog);
		} else {
			this.fixLog.setFlgProcess(StatusDefinition.REQUEST);
			this.fixLog.setIdScheduler(this.customResult1.getIdScheduler());
			if ("Y".equalsIgnoreCase(this.fixInbox.getFlgAttach())) {
				this.fixLog.setFixLogPK(new FixLogPK(this.fixInbox.getEmailAttachment(), SchedulerUtil.getDate("dd/MM/yyyy")));
			} else {
				this.fixLog.setFixLogPK(new FixLogPK(this.fixInbox.getFixInboxPK().getSubject().split(" ", 2)[1], SchedulerUtil.getDate("dd/MM/yyyy")));
			}
			this.fixLog.getFixLogPK().setInboxId(this.fixInbox.getFixInboxPK().getInboxId());
			this.fixLog.setGrpId(this.customResult1.getGrpId());
			this.fixLog.getFixLogPK().setTypFix(StatusDefinition.IN);
			this.fixLog.setSender(this.fixInbox.getSender());
			this.fixLog.setFlgAuth(this.customResult1.getFlgAuth());
			this.fixLog.setDtmReceive(this.fixInbox.getDtmReceive());

			if (this.fixInbox.getFlgAttach() != null) {
				if (this.fixInbox.getFlgAttach().equals("Y")) {
					getLogger().info("Getting Attachment " + this.fileAttachment.getName());
					try {
						File fileAttachment = new File(filePath, this.fixInbox.getEmailAttachment());
						FileOutputStream fos = new FileOutputStream(fileAttachment);
						this.fileAttachment.load(fos);
						fos.close();
						getLogger().info("Size : " + fileAttachment.length());
						this.fixLog.setFileSize((int) fileAttachment.length());

						// decrypt file
						if (this.customResult1.getFlgEncrypt().equalsIgnoreCase("Y")) {
							getLogger().info("Decrypting " + this.fixInbox.getEmailAttachment());
							String newFile = FileUtil.decrypt(this.filePath + this.fixInbox.getEmailAttachment(),
										this.customResult1.getModDecrypt(), this.customResult1.getKeyDecrypt(), "EM."
									);
							this.customResult1.setNewFilename(newFile);
							if (!FilenameUtils.getBaseName(newFile).equalsIgnoreCase(FilenameUtils.getBaseName(this.fixInbox.getEmailAttachment()))) {
								throw new Exception("Filename Before and After Decrypt Different");
							}
							
							// File extension validation
							String importFileExtension = (String) this.customResult1.getFileExtension();
							if (StringUtils.isNotBlank(importFileExtension)) {
								importFileExtension = (";" + importFileExtension + ";").toLowerCase();
								String userFileExtension = (";" + FilenameUtils.getExtension(newFile) + ";").toLowerCase();
								
								if (importFileExtension.indexOf(userFileExtension) == -1)
									throw new Exception("Invalid File Extension!!!");
							}
						}
					} catch (IOException ex) {
						getLogger().error("IO Error When Download Attachment " + ex, ex);
						// throw new FIXException("INVALID EMAIL");
						throw new FIXException(ex);
					} catch (Exception ex) {
						getLogger().error("Error Download Attachment " + ex, ex);
						this.fixLog.setReason(ex.getMessage());
						// throw new FIXException("INVALID EMAIL");
						throw new FIXException(ex);
					}
					getLogger().info("Done Getting and Decrypt Attachment " + this.fileAttachment.getName());
				}
			}
			save(this.fixLogDao, this.fixLog);
		}
		getLogger().info("Successfully save to FixLog");
	}

	// individual save
	private void save(BaseDao baseDao, BaseModel baseModel) {
		this.transaction = this.session.beginTransaction();
		baseDao.insert(baseModel);
		this.transaction.commit();
	}

	// individual update
	private void update(BaseDao baseDao, BaseModel baseModel) {
		this.transaction = this.session.beginTransaction();
		baseDao.update(baseModel);
		this.transaction.commit();
	}

	// if reg = true will register to fixqxtract
	private void doWhenError(Exception ex, boolean reg) {
		this.transaction = this.session.beginTransaction();
		// update fixInbox
		this.fixInbox.setFlgProcess(StatusDefinition.ERROR);
		this.fixInbox.setReason(ex.getMessage());
		this.fixInboxDao.update(this.fixInbox);

		// update fixLog
		this.fixLog.setFlgProcess(StatusDefinition.ERROR);
		this.fixLog.setReason(ex.getMessage());
		this.fixLogDao.update(this.fixLog);

		if (reg) {
			// register to fixqxtract
			registerToFixQXtract(ex);
		}
		this.transaction.commit();
	}

	private void registerToFixQXtract(Exception ex) {
		FixQXtract fixQXtract = new FixQXtract();
		fixQXtract.setIdScheduler(ScheduleDefinition.emailOnly);
		fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
		fixQXtract.setDtmRequest(SchedulerUtil.getTime());
		fixQXtract.setReason(ex.getMessage());
		fixQXtract.setParam1(MailUtil.createEmailSubject(this.fixInbox.getFixInboxPK().getSubject()));
		fixQXtract.setParam2(this.fixInbox.getSender());
		try {
			fixQXtract.setParam4("INVALID EMAIL<br/><br/>-- Begin Message --<br/>" + reformatErrorMessage(ex) + "<br/>-- End Message --");
		} catch (Exception ex1) {
			getLogger().error(ex1, ex1);
			fixQXtract.setParam4("INVALID EMAIL");
		}

		FixQXtractDao fixQXtractDao = new FixQXtractDao(this.session);

		// check duplicate
		getLogger().debug("FixQXtract Check Dupplicate, Reason : " + fixQXtract.getReason());
		getLogger().debug("FixQXtract Check Dupplicate, Param1 : " + fixQXtract.getParam1());
		getLogger().debug("FixQXtract Check Dupplicate, Param2 : " + fixQXtract.getParam2());
		FixQXtract persister = new FixQXtract();
		persister = fixQXtractDao.checkDupplicate(fixQXtract.getReason(), fixQXtract.getParam1(), fixQXtract.getParam2());

		// insert to fixqxtract
		if (persister == null) {
			getLogger().debug("FixQXtract is Null, Insert OK");
			fixQXtractDao.insert(fixQXtract);
		}
	}

	private String reformatErrorMessage(Exception ex) throws Exception {
		String errFile = PropertyPersister.dirFixOut + "ImportMail.err";
		String errMsg = "";

		PrintStream p = new PrintStream(new File(errFile));
		ex.printStackTrace(p);
		EncryptionUtil.ZIPcompress(errFile, errFile + ".zip", null);

		InputStream is = new FileInputStream(errFile + ".zip");
		errMsg = new BASE64Encoder().encode(IOUtils.toByteArray(is));
		FileUtils.deleteQuietly(new File(errFile));
		FileUtils.deleteQuietly(new File(errFile + ".zip"));
		return errMsg;
	}

	private int maxItem;
	private Session session;
	private Transaction transaction;
	private EmailMessage emailMessage;
	private FixInboxDao fixInboxDao;
	private FixInbox fixInbox;
	private FixLog fixLog;
	private FixLogDao fixLogDao;
	private FileAttachment fileAttachment;
	private CustomResult1 customResult1;
	private boolean exist;
}
