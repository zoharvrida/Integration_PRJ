<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:form id="frmInquiryAccount">
    <s:token name="tokenInquiryAccount" />
</s:form>

<script type="text/javascript">
    $(function() {
        if (inqAcct.onClose != undefined) {
            var acct = <s:property value="%{contentData}" escapeHtml="false" />;
            inqAcct.onClose(acct);
        }
    });
</script>