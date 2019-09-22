<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:form id="frmReInquiryBilling">
	<s:token name="tokenReInquiryBilling" />
</s:form>

<script type="text/javascript">
    $(function() {
        if (inqParam.onClose != undefined) {
            var etax = <s:property value="%{contentData}" escapeHtml="false" />;
            inqParam.onClose(etax);
        }
    });
</script>