<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
	id="rbBS" 
	name="rbBS" 
	list="{'Inquiry', 'Add', 'Edit', 'Delete', 'Clear'}" 
	disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$(window).resize();
		rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		
		
		rdb.setDisableButton("btnSearch", "0100");
		rdb.setDisableButton("btnProcess", "1011");
		rdb.setDisableButton("btnLookupCustomerType", "1011");
		rdb.setDisableButton("btnLookupIdType", "1011");
		rdb.setDisableButton("btnLookupGender", "1011");
		rdb.setDisableButton("btnLookupMaritalStatus", "1011");
		rdb.setDisableButton("btnLookupBusinessType", "1011");
		rdb.setDisableButton("btnLookupSourceOfFunds", "1011");
		rdb.setDisableButton("btnLookupIncomePerMonthType", "1011");
		
		
		rdb.setReadonly("frmMain_idNumber", "1011");
		rdb.setReadonly("frmMain_name", "1011");
		rdb.setReadonly("frmMain_address1", "1011");
		rdb.setReadonly("frmMain_address2", "1011");
		rdb.setReadonly("frmMain_address3", "1011");
		rdb.setReadonly("frmMain_postalCode", "1011");
		rdb.setReadonly("city_widget", "1011");
		rdb.setReadonly("state_widget", "1011");
		rdb.setReadonly("country_widget", "1011");
		rdb.setReadonly("citizenship_widget", "1011");
		rdb.setReadonly("occupation_widget", "1011");
		rdb.setReadonly("frmMain_phoneNo", "1011");
		rdb.setReadonly("frmMain_birthPlace", "1011");
		rdb.setReadonly("frmMain_notes", "1011");
		
		rdb.setDisabled("chkResSameAsId", "1011");
		rdb.setReadonly("frmMain_residentialAddress1", "1011");
		rdb.setReadonly("frmMain_residentialAddress2", "1011");
		rdb.setReadonly("frmMain_residentialAddress3", "1011");
		rdb.setReadonly("frmMain_residentialPostalCode", "1011");
		rdb.setReadonly("residentialCity_widget", "1011");
		rdb.setReadonly("residentialState_widget", "1011");
		rdb.setReadonly("residentialCountry_widget", "1011");
		rdb.setReadonly("frmMain_sourceOfFundsOthers", "1011");
		rdb.setReadonly("frmMain_trxPurpose", "1011");
		
		
		rdb.setReadonly("frmMain_jobTitle", "1011");
		rdb.setReadonly("frmMain_homePhoneNo", "1011");
		rdb.setReadonly("frmMain_mobilePhoneNo", "1011");
		rdb.setReadonly("frmMain_officeName", "1011");
		rdb.setReadonly("frmMain_officePhoneNo", "1011");
		rdb.setReadonly("frmMain_officeAddress1", "1011");
		rdb.setReadonly("frmMain_officeAddress2", "1011");
		rdb.setReadonly("frmMain_officeAddress3", "1011");
		
		rdb.setReadonly("frmMain_instanceRepresented", "1011");
		rdb.setReadonly("frmMain_accountRepresented", "1011");
		rdb.setReadonly("frmMain_authLetterNo", "1011");
		rdb.setReadonly("frmMain_accountBranch", "1011");
		
		
		rdb.setOnChange(
			function() {
				$("#fsInquiry").show();
				$("#fsSystemInformation").show();
				$("#fsButtons").hide();
			}, 
			function() {
				changeAction("frmMain", "24301", "_add");
				
				$("#fsInquiry").hide();
				$("#fsSystemInformation").hide();
				$("#fsButtons").show();
				$("#btnProcess").attr("value", '<s:text name="button.save" />');
			}, 
			function() {
				changeAction("frmMain", "24301", "_edit");
				
				$("#fsInquiry").show();
				$("#fsSystemInformation").show();
				$("#fsButtons").show();
				$("#btnProcess").attr("value", '<s:text name="button.update" />');
			}, 
			function() {
				changeAction("frmMain", "24301", "_delete");
				
				$("#fsInquiry").show();
				$("#fsSystemInformation").show();
				$("#fsButtons").show();
				$("#btnProcess").attr("value", '<s:text name="button.delete" />');
			}, 
			function(removeMsg) {
				/* fixing refresh problem for IE */
				var $rowWICNo = $("#rowWICNo").detach();
				$rowWICNo.insertBefore($("#rowCustomerType"));
				$rowWICNo.hide();
				
				$("#frmInquiry_strData_customerTypeInq").attr('value', '');
				$("#frmInquiry_strData_customerTypeInqDesc").attr('value', '');
				$("#frmInquiry_strData_idTypeInq").attr('value', '');
				$("#frmInquiry_strData_idTypeInqDesc").attr('value', '');
				$("#frmInquiry_strData_idNumberInq").attr('value', '');
				
				$("#frmMain_WICNo").attr('value', '');
				$("#WICNo").html('');
				$("#frmMain_strData_customerType").attr('value', '');
				$("#frmMain_customerType").attr('value', '');
				$("#frmMain_strData_idType").attr('value', '');
				$("#frmMain_idType").attr('value', '');
				$("#frmMain_idNumber").attr('value', '');
				$("#frmMain_idExpiredDate").val('');
				
				$("#btnLookupDukcapil").css('visibility', 'hidden');
				
				$("#frmMain_name").attr('value', '');
				$("#frmMain_strData_gender").attr('value', '');
				$("#frmMain_gender").attr('value', '');
				$("#frmMain_address1").attr('value', '');
				$("#frmMain_strData_maritalStatus").attr('value', '');
				$("#frmMain_maritalStatus").attr('value', '');
				$("#frmMain_address2").attr('value', '');
				$("#frmMain_phoneNo").attr('value', '');
				$("#frmMain_address3").attr('value', '');
				$("#frmMain_birthPlace").attr('value', '');
				$("#frmMain_postalCode").attr('value', '');
				$("#city").attr('value', '');
				$("#city_widget").attr('value', '');
				$("#frmMain_birthDate").val('');
				$("#state").attr('value', '');
				$("#state_widget").attr('value', '');
				$("#frmMain_notes").attr('value', '');
				$("#country").attr('value', '');
				$("#country_widget").attr('value', '');
				$("#citizenship").attr('value', '');
				$("#citizenship_widget").attr('value', '');
				$("#occupation").attr('value', '');
				$("#occupation_widget").attr('value', '');
				
				$("#frmMain_residentialAddress1").attr('value', '');
				$("#frmMain_residentialAddress2").attr('value', '');
				$("#frmMain_residentialAddress3").attr('value', '');
				$("#frmMain_residentialPostalCode").attr('value', '');
				$("#residentialCity_widget").attr('value', '');
				$("#residentialState_widget").attr('value', '');
				$("#residentialCountry_widget").attr('value', '');
				$("#frmMain_strData_businessType").attr('value', '');
				$("#frmMain_strData_sourceOfFunds").attr('value', '');
				$("#frmMain_sourceOfFundsOthers").attr('value', '');
				$("#wwlbl_lblSourceOfFundsOthers").hide();
				$("#frmMain_trxPurpose").attr('value', '');
				if ($("#chkResSameAsId").get(0).checked)
					$("#chkResSameAsId").get(0).checked = false;
				
				$("#frmMain_jobTitle").attr('value', '');
				$("#frmMain_homePhoneNo").attr('value', '');
				$("#frmMain_officeName").attr('value', '');
				$("#frmMain_officePhoneNo").attr('value', '');
				$("#frmMain_officeAddress1").attr('value', '');
				$("#frmMain_mobilePhoneNo").attr('value', '');
				$("#frmMain_officeAddress2").attr('value', '');
				$("#frmMain_officeAddress3").attr('value', '');
				$("#frmMain_strData_incomePerMonthType").attr('value', '');
				$("#frmMain_incomePerMonthType").attr('value', '');
				
				$("#frmMain_highRiskWIC").attr('value', '');
				$("#frmMain_highRiskCountry").attr('value', '');
				$("#hdHighRiskCountry").attr("value", '');
				$("#hdHighRiskCitizenship").attr("value", '');
				
				$("#frmMain_instanceRepresented").attr('value', '');
				$("#frmMain_accountRepresented").attr('value', '');
				$("#frmMain_authLetterNo").attr('value', '');
				$("#frmMain_accountBranch").attr('value', '');
				
				$("#frmMain_requester").attr("value", '');
				$("#frmMain_approver").attr("value", '');
				$("#frmMain_dtmCreated").attr("value", '');
				$("#frmMain_dtmUpdated").attr("value", '');
				
				$("#fsAdditionalDataPersonal2").hide();
				$("#fsHighRiskInformation").hide();
				$("#fsCourierInformation").hide();
				
				if (rdb.current == 'add') {
					$("#frmMain_branch").val('<s:property value="%{#session.cdBranch}" />');
					$("form#frmMain img.ui-datepicker-trigger").removeClass("ui-helper-hidden");
					$("form#frmMain span.required").removeClass("ui-helper-hidden");
				}
				else {
					$("#frmMain_branch").val('');
					$("form#frmMain img.ui-datepicker-trigger").addClass("ui-helper-hidden");
					$("form#frmMain span.required").addClass("ui-helper-hidden");
				}
					
				
				$("#frmMain, #frmInquiry").find("input[type='text'], textarea")
					.css("opacity", '')
					.filter("[readonly]").css("opacity", "0.5");
				$("#ph-main").animate({scrollTop: 0}, "slow");
					
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("div.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			} 
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
	});
	
	function isDataInquiryExists() {
		var returnValue = (
			($("#frmInquiry_customerTypeInq").val() != "") &&
			($("#frmInquiry_idTypeInq").val() != "") &&
			($("#frmInquiry_idNumberInq").val() != "")
		);
		
		return returnValue;
	}
	
</script>