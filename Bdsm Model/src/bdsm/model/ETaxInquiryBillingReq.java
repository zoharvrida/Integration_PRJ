package bdsm.model;

/**
 * @author v00017250
 */
@SuppressWarnings("serial")
public class ETaxInquiryBillingReq extends BaseModel {

    private String refNo;
    private String billingId;
    private String branchCode;
    private String costCenter;
    private String userId;

    protected static String stringMax(String input, int max) {
        if ((input != null) && (input.length() > max)) {
            return input.substring(0, max);
        } else {
            return input;
        }
    }

    public String getRefNo() {
        return this.refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = stringMax(refNo, 40);
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
                .append("{")
                .append("refNo=").append(this.refNo)
                .append(",billingId=").append(this.billingId)
                .append(",branchCode=").append(this.branchCode)
                .append(",costCenter=").append(this.costCenter)
                .append(",userId=").append(this.userId)
                .append("}");

        return sb.toString();
    }

}
