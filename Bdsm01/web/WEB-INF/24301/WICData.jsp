<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	<s:if test="%{WICNo != null}">
		$(function() {
			$("#rowWICNo").show();
			
			$("#frmMain_WICNo").val('<s:property value="%{WICNo}" />');
			$("#WICNo").html('<s:property value="%{WICNo}" />');
			$("#frmMain_customerType").val('<s:property value="%{customerType}" />');
			$("#frmMain_strData_customerType").val('<s:text name="%{objData.customerType}" />');
			$("#frmMain_idType").val('<s:property value="%{idType}" />');
			$("#frmMain_strData_idType").val('<s:text name="%{objData.idType}" />');
			$("#frmMain_idNumber").val('<s:property value="%{idNumber}" />');
			$("#frmMain_idExpiredDate").val('<s:date name="%{idExpiredDate}" format="dd/MM/yyyy" />');
			
			if (($("#frmMain_idType").val() == 'KTP') && (rdb.current == 'edit'))
				$("#btnLookupDukcapil").css("visibility", "visible");
			
			$("#frmMain_name").val('<s:property value="%{name}" escapeJavaScript="true" />');
			$("#frmMain_gender").val('<s:property value="%{gender}" />');
			$("#frmMain_strData_gender").val('<s:text name="%{objData.gender}" />');
			$("#frmMain_address1").val('<s:property value="%{address1}" escapeJavaScript="true" />');
			$("#frmMain_maritalStatus").val('<s:property value="%{maritalStatus}" />');
			$("#frmMain_strData_maritalStatus").val('<s:text name="%{objData.maritalStatus}" />');
			$("#frmMain_address2").val('<s:property value="%{address2}" escapeJavaScript="true" />');
			$("#frmMain_address3").val('<s:property value="%{address3}" escapeJavaScript="true" />');
			$("#frmMain_postalCode").val('<s:property value="%{postalCode}" />');
			$("#frmMain_birthPlace").val('<s:property value="%{birthPlace}" />');
			$("#city").val('<s:property value="%{city}" />');
			$("#city_widget").val('<s:property value="%{objData.city}" />');
			$("#frmMain_birthDate").val('<s:date name="birthDate" format="dd/MM/yyyy" />');
			$("#state").val('<s:property value="%{state}" />');
			$("#state_widget").val('<s:property value="%{objData.state}" />');
			$("#frmMain_notes").val('<s:property value="%{notes}" escapeJavaScript="true" />');
			$("#country").val('<s:property value="%{country}" />');
			$("#country_widget").val('<s:property value="%{objData.country}" />');
			$("#citizenship").val('<s:property value="%{citizenship}" />');
			$("#citizenship_widget").val('<s:property value="%{objData.citizenship}" />');
			$("#occupation").val('<s:property value="%{occupation}" />');
			$("#occupation_widget").val('<s:property value="%{objData.occupation}" />');
			
			$("#chkResSameAsId").get(0).checked = ('<s:property value="%{residentialEqualIdentity}" />' == 'true');
			if ($("#chkResSameAsId").get(0).checked)
				$("#chkResSameAsId").trigger("change");
			else {
				$("#frmMain_residentialAddress1").val('<s:property value="%{residentialAddress1}" escapeJavaScript="true" />');
				$("#frmMain_residentialAddress2").val('<s:property value="%{residentialAddress2}" escapeJavaScript="true" />');
				$("#frmMain_residentialAddress3").val('<s:property value="%{residentialAddress3}" escapeJavaScript="true" />');
				$("#frmMain_residentialPostalCode").val('<s:property value="%{residentialPostalCode}" />');
				$("#residentialCity").val('<s:property value="%{residentialCity}" />');
				$("#residentialCity_widget").val('<s:property value="%{objData.residentialCity}" />');
				$("#residentialState").val('<s:property value="%{residentialState}" />');
				$("#residentialState_widget").val('<s:property value="%{objData.residentialState}" />');
				$("#residentialCountry").val('<s:property value="%{residentialCountry}" />');
				$("#residentialCountry_widget").val('<s:property value="%{objData.residentialCountry}" />');
			}
			
			$("#frmMain_businessType").val('<s:property value="%{businessType}" />');
			$("#frmMain_strData_businessType").val('<s:property value="%{objData.businessType}" />');
			$("#frmMain_sourceOfFunds").val('<s:property value="%{sourceOfFunds}" />');
			$("#frmMain_strData_sourceOfFunds").val('<s:property value="%{objData.sourceOfFunds}" />');
			$("#frmMain_sourceOfFundsOthers").val('<s:property value="%{sourceOfFundsOthers}" />');
			$("#frmMain_trxPurpose").val('<s:property value="%{trxPurpose}" />');
			
			$("#frmMain_jobTitle").val('<s:property value="%{jobTitle}" escapeJavaScript="true" />');
			$("#frmMain_homePhoneNo").val('<s:property value="%{homePhoneNo}" />');
			$("#frmMain_officeName").val('<s:property value="%{officeName}" escapeJavaScript="true" />');
			$("#frmMain_officePhoneNo").val('<s:property value="%{officePhoneNo}" />');
			$("#frmMain_officeAddress1").val('<s:property value="%{officeAddress1}" escapeJavaScript="true" />');
			$("#frmMain_mobilePhoneNo").val('<s:property value="%{mobilePhoneNo}" />');
			$("#frmMain_officeAddress2").val('<s:property value="%{officeAddress2}" escapeJavaScript="true" />');
			$("#frmMain_officeAddress3").val('<s:property value="%{officeAddress3}" escapeJavaScript="true" />');
			$("#frmMain_incomePerMonthType").val('<s:property value="%{incomePerMonthType}" />');
			$("#frmMain_strData_incomePerMonthType").val('<s:property value="%{objData.incomePerMonthType}" escapeJavaScript="true" />');
			
			$("#frmMain_highRiskWIC").val('<s:property value="%{highRiskWIC}" />');
			$("#frmMain_highRiskCountry").val('<s:property value="%{highRiskCountry}" />');
			
			
			$("#frmMain_instanceRepresented").val('<s:property value="%{instanceRepresented}" escapeJavaScript="true" />');
			$("#frmMain_authLetterNo").val('<s:property value="%{authLetterNo}" />');
			$("#frmMain_accountRepresented").val('<s:property value="%{accountRepresented}" />');
			$("#frmMain_accountBranch").val('<s:property value="%{accountBranch}" />');
			
			$("#frmMain_branch").val('<s:property value="%{branch}" />');
			$("#frmMain_requester").val('<s:property value="%{requester}" />');
			$("#frmMain_approver").val('<s:property value="%{approver}" />');
			$("#frmMain_dtmCreated").val('<s:date name="dtmCreated" format="dd/MM/yyyy HH:mm:ss" />');
			$("#frmMain_dtmUpdated").val('<s:date name="dtmUpdated" format="dd/MM/yyyy HH:mm:ss" />');
			
			var txt = $("#frmMain_idExpiredDate").val();
			if ((txt != undefined) && (txt != '') && !isAfterDate(txt, dtBusiness))
				messageBox(1, '<s:text name="24301.id.id.expired" />');
			
			$("#frmMain_flagStatus").val('<s:property value="%{flagStatus}" />');
			
			if (((rdb.current == 'edit') || (rdb.current == 'delete')) && (isDataInquiryExists())) {
				$("#btnProcess").button("enable");
				if (rdb.current == 'edit') {
					$("form#frmMain img.ui-datepicker-trigger").removeClass("ui-helper-hidden");
					$("form#frmMain span.required").removeClass("ui-helper-hidden");
					
					$("#btnLookupIdType").button("enable");
					$("#btnLookupGender").button("enable");
					$("#btnLookupBusinessType").button("enable");
					$("#btnLookupSourceOfFunds").button("enable");
					$("#btnLookupMaritalStatus").button("enable");
					$("#btnLookupIncomePerMonthType").button("enable");
					
					$("#city_widget").attr("readonly", null);
					$("#state_widget").attr("readonly", null);
					$("#country_widget").attr("readonly", null);
					$("#citizenship_widget").attr("readonly", null);
					$("#occupation_widget").attr("readonly", null);
					
					$("#frmMain_idNumber").attr("readonly", null);
					$("#frmMain_name").attr("readonly", null);
					$("#frmMain_address1").attr("readonly", null);
					$("#frmMain_address2").attr("readonly", null);
					$("#frmMain_address3").attr("readonly", null);
					$("#frmMain_postalCode").attr("readonly", null);
					$("#frmMain_phoneNo").attr("readonly", null);
					$("#frmMain_birthPlace").attr("readonly", null);
					$("#frmMain_notes").attr("readonly", null);
					
					$("#chkResSameAsId").attr("disabled", null);
					if ($("#chkResSameAsId").get(0).checked == false) {
						$("#frmMain_residentialAddress1").attr("readonly", null);
						$("#frmMain_residentialAddress2").attr("readonly", null);
						$("#frmMain_residentialAddress3").attr("readonly", null);
						$("#frmMain_residentialPostalCode").attr("readonly", null);
						$("#residentialCity_widget").attr("readonly", null);
						$("#residentialState_widget").attr("readonly", null);
						$("#residentialCountry_widget").attr("readonly", null);
					}
					$("#frmMain_businessType").attr("readonly", null);
					$("#frmMain_sourceOfFundsOthers").attr("readonly", null);
					$("#frmMain_trxPurpose").attr("readonly", null);
					
					$("#frmMain_jobTitle").attr("readonly", null);
					$("#frmMain_homePhoneNo").attr("readonly", null);
					$("#frmMain_mobilePhoneNo").attr("readonly", null);
					$("#frmMain_officeName").attr("readonly", null);
					$("#frmMain_officePhoneNo").attr("readonly", null);
					$("#frmMain_officeAddress1").attr("readonly", null);
					$("#frmMain_officeAddress2").attr("readonly", null);
					$("#frmMain_officeAddress3").attr("readonly", null);
					
					$("#frmMain_instanceRepresented").attr("readonly", null);
					$("#frmMain_accountRepresented").attr("readonly", null);
					$("#frmMain_authLetterNo").attr("readonly", null);
					$("#frmMain_accountBranch").attr("readonly", null);
					
					$("#frmInquiry, #frmMain").find("input[type='text'], textarea")
						.css("opacity", '')
						.filter("[readonly]").css("opacity", "0.5");
				}
			}
	    });
	</s:if>
	<s:else>
			messageBox(2, '<s:text name="24301.error.wic.data.notfound"><s:param><s:text name="%{objData.customerType}" /></s:param></s:text>');
	</s:else>
	
	o.restoreEvent();
</script>

