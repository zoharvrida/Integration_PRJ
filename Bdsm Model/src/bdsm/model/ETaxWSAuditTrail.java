package bdsm.model;

/**
 * @author v00017250
 */
@SuppressWarnings("serial")
public class ETaxWSAuditTrail extends BaseModel {

    private String refNo;
    private String profileName;
    private String methodName;
    private String typeReq;
    private String content;

    public String getRefNo() {
        return this.refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getTypeReq() {
        return this.typeReq;
    }

    public void setTypeReq(String typeReq) {
        this.typeReq = typeReq;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /* Hibernate to Database properties */
    protected Character getTypeReqDB() {
        return ((this.typeReq == null) ? null : this.typeReq.charAt(0));
    }

    protected void setTypeReqDB(Character typeReqDB) {
        this.typeReq = (typeReqDB == null) ? null : typeReqDB.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
                .append("{")
                .append("refNo=").append(this.refNo)
                .append(",profileName=").append(this.profileName)
                .append(",methodName=").append(this.methodName)
                .append(",typeReq=").append(this.typeReq)
                .append(",content=").append(this.content)
                .append("}");

        return sb.toString();
    }

}
