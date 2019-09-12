<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	<s:if test="%{customer != null}">
		$(function() {
			if (rdb.current == "add")
				$("#frmMain_id").attr("value", '<s:property value="%{objData.BIC}" />');
				
			$("#frmMain_strData_customerName").attr("value", '<s:property value="%{customer.namCustShrt}" />');
			$("#frmMain_strData_address1").attr("value", '<s:property value="%{customer.txtCustAdrAdd1}" />');
			$("#frmMain_strData_address2").attr("value", '<s:property value="%{customer.txtCustAdrAdd2}" />');
			$("#frmMain_strData_address3").attr("value", '<s:property value="%{customer.txtCustAdrAdd3}" />');
			$("#frmMain_strData_bankName").attr("value", '<s:property value="%{objData.txtBankName}" />');
			$("#frmMain_strData_customerType").attr("value", '<s:property value="%{objData.txtCustomerType}" />');
			$("#frmMain_strData_residentialStatus").attr("value", '<s:property value="%{objData.txtResidentialStatus}" />');
			
			$("#btnProcess").button("enable");
	    });
	</s:if>
	<s:else>
			messageBox(2, '<s:text name="23301.account.no.invalid"><s:param><s:property value="%{accountNo}" /></s:param></s:text>');
	</s:else>
</script>

