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
		
		
		rdb.setDisableButton("btnFindAcct", "1011");
		rdb.setDisableButton("btnProcess", "1111");
		rdb.setDisableButton("btnGeneratePDF", "1111");
		
		rdb.setReadonly("frmMain_accountNo", "1011");
		rdb.setReadonly("frmMain_billingNo", "1011");
		rdb.setReadonly("frmMain_billerName", "1011");
		rdb.setReadonly("billerBankName_widget", "1011");
		rdb.setReadonly("frmMain_billerAccountNo", "1011");
		rdb.setReadonly("frmMain_billingPurpose", "1001");
		rdb.setReadonly("frmMain_nominal", "1001");
		
		rdb.setDisabled("frmMain_nominalType", "1001");
		rdb.setDisabled("frmMain_paymentMinDate", "1001");
		rdb.setDisabled("frmMain_paymentMaxDate", "1001");
		rdb.setDisabled("frmMain_paymentPeriodicType", "1001");
		rdb.setDisabled("frmMain_validUntil", "1001");
				
		rdb.setOnChange(
			function() { // inquiry
				$("#fsInquiry").show();
				$("#btnProcess").hide();
				$("#btnGeneratePDF").show();
			},
			function() { // add
				changeAction("frmMain", "23301", "_add");
				
				$("#fsInquiry").hide();
				$("#btnProcess").show(); $("#btnProcess").attr("value", '<s:text name="button.save" />');
				$("#btnGeneratePDF").hide();
			},
			function() { // edit
				changeAction("frmMain", "23301", "_edit");
				
				$("#fsInquiry").show();
				$("#btnProcess").show(); $("#btnProcess").attr("value", '<s:text name="button.update" />');
				$("#btnGeneratePDF").hide();
			},
			function() { // delete
				changeAction("frmMain", "23301", "_delete");
				
				$("#fsInquiry").show();
				$("#btnProcess").show(); $("#btnProcess").attr("value", '<s:text name="button.delete" />');
				$("#btnGeneratePDF").hide();
			},
			function(removeMsg) { // clear
				$("#frmInquiry_SI_BIC").val("").attr("readonly", false);
				$("#frmInquiry_SI_CODE").val("").attr("readonly", false);
				$("#frmInquiry_strData_mode").val((rdb.current == "delete")? "delete": "");
				$("#btnSearch").button("disable");
				
				$("#frmMain_id").attr("value", '');
				$("#frmMain_accountNo").val('');
				$("#frmMain_strData_customerName").attr("value", '');
				$("#frmMain_strData_address1").attr("value", '');
				$("#frmMain_strData_address2").attr("value", '');
				$("#frmMain_strData_address3").attr("value", '');
				$("#frmMain_strData_bankName").attr("value", '');
				$("#frmMain_strData_customerType").attr("value", '');
				$("#frmMain_strData_residentialStatus").attr("value", '');
				
				$("#frmMain_billingNo").attr("value", '');
				$("#frmMain_billerName").attr("value", '');
				$("#billerBankName_widget").attr("value", '');
				$("#frmMain_billerAccountNo").attr("value", '');
				
				$("#frmMain_billingPurpose").attr("value", '');
				$("#frmMain_paymentPeriodicType").val('M').change();
				$("#frmMain_nominalType").val('F');
				$("#frmMain_nominal").val('');
				$("#frmMain_paymentMinDate").val($("#frmMain_paymentMinDate option:first-child").val());
				$("#frmMain_paymentMaxDate").val($("#frmMain_paymentMaxDate option:last-child").val());
				$("#frmMain_validUntil").val('');
				$("#frmMain_flagStatus").val('A');
				$("#frmMain_idMaintainedSpv").val('');
				
				
				if (rdb.current == 'add') {
					$("#frmMain_validUntil").val('<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />');
					$("form#frmMain img.ui-datepicker-trigger").removeClass("ui-helper-hidden");
					$("form#frmMain span.required").removeClass("ui-helper-hidden");
				}
				else {
					$("form#frmMain img.ui-datepicker-trigger").addClass("ui-helper-hidden");
					$("form#frmMain span.required").addClass("ui-helper-hidden");
				}
				
				
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
	
</script>