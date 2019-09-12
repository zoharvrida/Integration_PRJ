/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019237
 */
public class FileDownload extends BaseModel{
    private String reportPath;
    private String reportType;
    private String reportContent;
	private String reportJSON;

    /**
     * @return the reportType
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * @return the reportContent
     */
    public String getReportContent() {
        return reportContent;
    }

    /**
     * @param reportContent the reportContent to set
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * @return the reportPath
     */
    public String getReportPath() {
        return reportPath;
    }

    /**
     * @param reportPath the reportPath to set
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

	/**
	 * @return the reportJSON
	 */
	public String getReportJSON() {
		return reportJSON;
	}

	/**
	 * @param reportJSON the reportJSON to set
	 */
	public void setReportJSON(String reportJSON) {
		this.reportJSON = reportJSON;
	}
}
