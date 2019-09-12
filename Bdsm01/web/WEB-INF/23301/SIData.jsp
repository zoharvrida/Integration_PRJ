<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:set var="check" scope="request" value="%{accountNo != null}" />

<script type="text/javascript">
	var check = <s:property value="%{#request.check}" />;
	<s:if test="%{#request.check}">
		$(function() {
			$("#frmMain_id").val('<s:property value="%{id}" />');
			$("#frmMain_accountNo").val('<s:property value="%{accountNo}" />');
			$("#frmMain_strData_customerName").attr("value", '<s:property value="%{customer.namCustShrt}" />');
			$("#frmMain_strData_address1").attr("value", '<s:property value="%{customer.txtCustAdrAdd1}" />');
			$("#frmMain_strData_address2").attr("value", '<s:property value="%{customer.txtCustAdrAdd2}" />');
			$("#frmMain_strData_address3").attr("value", '<s:property value="%{customer.txtCustAdrAdd3}" />');
			$("#frmMain_strData_bankName").attr("value", '<s:property value="%{objData.txtBankName}" />');
			$("#frmMain_strData_customerType").attr("value", '<s:property value="%{objData.txtCustomerType}" />');
			$("#frmMain_strData_residentialStatus").attr("value", '<s:property value="%{objData.txtResidentialStatus}" />');
			
			$("#frmMain_billingNo").attr("value", '<s:property value="%{billingNo}" />');
			$("#frmMain_billerName").attr("value", '<s:property value="%{billerName}" />');
			$("#billerBankName").attr("value", '<s:property value="%{billerBankName}" />');
			$("#billerBankName_widget").attr("value", '<s:property value="%{objData.billerBankName}" />');
			$("#frmMain_billerAccountNo").attr("value", '<s:property value="%{billerAccountNo}" />');
			
			$("#frmMain_billingPurpose").attr("value", '<s:property value="%{billingPurpose}" />');
			$("#frmMain_paymentPeriodicType").val('<s:property value="%{paymentPeriodicType}" />').change();
			$("#frmMain_nominalType").val('<s:property value="%{nominalType}" />');
			$("#frmMain_nominal").autoNumeric('set', '<s:property value="%{nominal}" />');
			$("#frmMain_paymentMinDate").val('<s:property value="%{paymentMinDate}" />');
			$("#frmMain_paymentMaxDate").val('<s:property value="%{paymentMaxDate}" />');
			$("#frmMain_validUntil").val('<s:date name="validUntil" format="dd/MM/yyyy" />');
			$("#frmMain_flagStatus").val('<s:property value="%{flagStatus}" />');
			$("#frmMain_lastDebited").val('<s:date name="lastDebited" format="dd/MM/yyyy" />');
			
			if ($("#frmMain_flagStatus").val() == "X") { // status deleted
				messageBox(3, '<s:text name="23301.standing.instruction.status.delete" /><br>' + 
								'Requester: ' + '<s:property value="%{requester}" />' + ', Approver: ' + '<s:property value="%{approver}" /><br>' +
								'Date: ' + '<s:date name="dtmUpdated" format="dd/MM/yyyy HH:mm:ss" />' 
								);
				if (rdb.current == "edit") {
					$("#frmMain").find("input, textarea").attr("readonly", true);
					$("#frmMain").find("select").attr("disabled", true);
				}
			}
			else { // status active
				var isExpired = isAfterDate(dtBusiness, $("#frmMain_validUntil").val());
				
				if (isExpired)
					messageBox(3, '<s:text name="23301.standing.instruction.status.expired" />');
				
				if (rdb.current == "inquiry")
					$("#btnGeneratePDF").button("enable");
				else { // edit
					if (isExpired) {
						$("#frmMain").find("img.ui-datepicker-trigger").prev().attr("disabled", true);
						$("#frmMain").find("input, textarea").attr("readonly", true);
						$("#frmMain").find("select").attr("disabled", true);
					}
					else
						$("#btnProcess").button("enable");
				}
			}
	    });
	</s:if>
	<s:else>
		messageBox(2, '<s:text name="23301.sid.invalid" />');
	</s:else>
	
	$("#frmInquiry_SI_BIC").attr("readonly", check);
	$("#frmInquiry_SI_CODE").attr("readonly", check);
	$("#btnSearch").button(check? "disable": "enable");
	
	o.restoreEvent();
</script>

