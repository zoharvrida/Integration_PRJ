package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 * 
 * @author bdsm
 */
public class FixSchedulerXtract extends BaseModel{
    private FixSchedulerPK fixSchedulerPK;
    private String flgStatus;
    private Date dtEffStart;
    private Date dtEffEnd;
    private String typScheduler;
    private String minute;
    private String hour;
    private String monthDay;
    private String month;
    private String weekDay;
    private String typDest;
    private String fileFormat;
    private String filePattern;
    private String flgEncrypt;
    private String modEncrypt;
    private String keyEncrypt;
    private String sftpIP;
    private String sftpPort;
    private String sftpDestPath;
    private String sftpUserId;
    private String sftpPass;
    private String sftpKeyPath;
    private String emailTo;
    private String emailCC;
    private String emailSubjectPattern;
    private String emailBody;
    private String emailAttachment;
    private String dataSource;
    private String flgCalendar;
    private String schTimerProfile;
    private String parentCode;

    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

    /**
     * @return the dtEffStart
     */
    public Date getDtEffStart() {
        return dtEffStart;
    }

    /**
     * @param dtEffStart the dtEffStart to set
     */
    public void setDtEffStart(Date dtEffStart) {
        this.dtEffStart = dtEffStart;
    }

    /**
     * @return the dtEffEnd
     */
    public Date getDtEffEnd() {
        return dtEffEnd;
    }

    /**
     * @param dtEffEnd the dtEffEnd to set
     */
    public void setDtEffEnd(Date dtEffEnd) {
        this.dtEffEnd = dtEffEnd;
    }

    /**
     * @return the typScheduler
     */
    public String getTypScheduler() {
        return typScheduler;
    }

    /**
     * @param typScheduler the typScheduler to set
     */
    public void setTypScheduler(String typScheduler) {
        this.typScheduler = typScheduler;
    }

    /**
     * @return the min
     */
    public String getMinute() {
        return minute;
    }

    /**
     * @param minute 
     */
    public void setMinute(String minute) {
        this.minute = minute;
    }

    /**
     * @return the hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * @return the monthDay
     */
    public String getMonthDay() {
        return monthDay;
    }

    /**
     * @param monthDay the monthDay to set
     */
    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the weekDay
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * @param weekDay the weekDay to set
     */
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    /**
     * @return the typDest
     */
    public String getTypDest() {
        return typDest;
    }

    /**
     * @param typDest the typDest to set
     */
    public void setTypDest(String typDest) {
        this.typDest = typDest;
    }

    /**
     * @return the fileFormat
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * @param fileFormat the fileFormat to set
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * @return the filePattern
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * @param filePattern the filePattern to set
     */
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * @return the flgEncrypt
     */
    public String getFlgEncrypt() {
        return flgEncrypt;
    }

    /**
     * @param flgEncrypt the flgEncrypt to set
     */
    public void setFlgEncrypt(String flgEncrypt) {
        this.flgEncrypt = flgEncrypt;
    }

    /**
     * @return the modeEncrypt
     */
    public String getModEncrypt() {
        return modEncrypt;
    }

    /**
     * @param modEncrypt 
     */
    public void setModEncrypt(String modEncrypt) {
        this.modEncrypt = modEncrypt;
    }

    /**
     * @return the keyEncrypt
     */
    public String getKeyEncrypt() {
        return keyEncrypt;
    }

    /**
     * @param keyEncrypt the keyEncrypt to set
     */
    public void setKeyEncrypt(String keyEncrypt) {
        this.keyEncrypt = keyEncrypt;
    }

    /**
     * @return the sftpIP
     */
    public String getSftpIP() {
        return sftpIP;
    }

    /**
     * @param sftpIP the sftpIP to set
     */
    public void setSftpIP(String sftpIP) {
        this.sftpIP = sftpIP;
    }

    /**
     * @return the sftpPort
     */
    public String getSftpPort() {
        return sftpPort;
    }

    /**
     * @param sftpPort the sftpPort to set
     */
    public void setSftpPort(String sftpPort) {
        this.sftpPort = sftpPort;
    }

    /**
     * @return the sftpDestPath
     */
    public String getSftpDestPath() {
        return sftpDestPath;
    }

    /**
     * @param sftpDestPath the sftpDestPath to set
     */
    public void setSftpDestPath(String sftpDestPath) {
        this.sftpDestPath = sftpDestPath;
    }

    /**
     * @return the sftpUserId
     */
    public String getSftpUserId() {
        return sftpUserId;
    }

    /**
     * @param sftpUserId the sftpUserId to set
     */
    public void setSftpUserId(String sftpUserId) {
        this.sftpUserId = sftpUserId;
    }

    /**
     * @return the sftpPass
     */
    public String getSftpPass() {
        return sftpPass;
    }

    /**
     * @param sftpPass the sftpPass to set
     */
    public void setSftpPass(String sftpPass) {
        this.sftpPass = sftpPass;
    }

    /**
     * @return the sftpKeyPath
     */
    public String getSftpKeyPath() {
        return sftpKeyPath;
    }

    /**
     * @param sftpKeyPath the sftpKeyPath to set
     */
    public void setSftpKeyPath(String sftpKeyPath) {
        this.sftpKeyPath = sftpKeyPath;
    }

    /**
     * @return the emailTo
     */
    public String getEmailTo() {
        return emailTo;
    }

    /**
     * @param emailTo the emailTo to set
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    /**
     * @return the emailCC
     */
    public String getEmailCC() {
        return emailCC;
    }

    /**
     * @param emailCC the emailCC to set
     */
    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    /**
     * @return the emailSubjectPattern
     */
    public String getEmailSubjectPattern() {
        return emailSubjectPattern;
    }

    /**
     * @param emailSubjectPattern the emailSubjectPattern to set
     */
    public void setEmailSubjectPattern(String emailSubjectPattern) {
        this.emailSubjectPattern = emailSubjectPattern;
    }

    /**
     * @return the emailBody
     */
    public String getEmailBody() {
        return emailBody;
    }

    /**
     * @param emailBody the emailBody to set
     */
    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    /**
     * @return the emailAttachment
     */
    public String getEmailAttachment() {
        return emailAttachment;
    }

    /**
     * @param emailAttachment the emailAttachment to set
     */
    public void setEmailAttachment(String emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    /**
     * @return the fixSchedulerPK
     */
    public FixSchedulerPK getFixSchedulerPK() {
        return fixSchedulerPK;
    }

    /**
     * @param fixSchedulerPK the fixSchedulerPK to set
     */
    public void setFixSchedulerPK(FixSchedulerPK fixSchedulerPK) {
        this.fixSchedulerPK = fixSchedulerPK;
    }
    
    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * @return the flgCalendar
     */
    public String getFlgCalendar() {
        return flgCalendar;
    }

    /**
     * @param flgCalendar the flgCalendar to set
     */
    public void setFlgCalendar(String flgCalendar) {
        this.flgCalendar = flgCalendar;
    }

    /**
     * @return the schTimerProfile
     */
    public String getSchTimerProfile() {
        return schTimerProfile;
    }

    /**
     * @param schTimerProfile the schTimerProfile to set
     */
    public void setSchTimerProfile(String schTimerProfile) {
        this.schTimerProfile = schTimerProfile;
    }
    
    /**
     * 
     * @return
     */
    public String getParentCode() {
        return parentCode;
    }
    /**
     * 
     * @param parentCode
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
