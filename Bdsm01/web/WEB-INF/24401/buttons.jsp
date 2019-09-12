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
		
		
		rdb.setOnChange(
			function() {
				changeAction("frmMain", "24401", "_inquiry");
			}, 
			function() {
				changeAction("frmMain", "24401", "_add");
				$("#btnProcess").attr("value", '<s:text name="button.save" />');
			}, 
			null, 
			function() {
				changeAction("frmMain", "24401", "_delete");
				$("#btnProcess").attr("value", '<s:text name="button.delete" />');
			}, 
			function(removeMsg) {
				$("#fsDataPersonal").hide();
				$("#fsTrx").hide();
				$("#fsButtons").hide();
				
				$("#frmInquiry_strData_idTypeInqDesc").attr("value", '');
				$("#frmInquiry_strData_idTypeInq").attr("value", '');
				$("#frmInquiry_strData_ctrBatchNo").attr("value", '');
				$("#frmInquiry_strData_codUserNo").attr("value", '');
				$("#frmInquiry_strData_ctrSeqNo").attr("value", '');
				$("#frmInquiry_strData_trxRefNoInq").attr("value", '');
				$("#trxDateInq").attr("value", '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />');
				$("#frmInquiry_strData_idNumberInq").attr("value", '');
				$("#frmInquiry_strData_trxRefNoInq").attr("value", '');
				
				$("#frmMain_name").attr("value", '');
				$("#frmMain_address1").attr("value", '');
				$("#frmMain_address2").attr("value", '');
				$("#frmMain_address3").attr("value", '');
				$("#frmMain_strData_city").attr("value", '');
				$("#frmMain_strData_state").attr("value", '');
				$("#frmMain_strData_country").attr("value", '');
				$("#frmMain_strData_citizenship").attr("value", '');
				
				$("#frmMain_strData_trxType").attr("value", '');
				$("#frmMain_strData_trxCurrency").attr("value", '');
				$("#frmMain_objData_trxAmount").attr("value", '');
				$("#frmMain_strData_CIFNo").attr("value", '');
				$("#frmMain_strData_acctNo").attr("value", '');
				$("#frmMain_strData_acctName").attr("value", '');
				
				$("#frmInquiry_strData_searchForType").attr("value", rdb.current);
				
				/* Date inquiry show/hide */
				if (rdb.current == 'add') {
					$("#wwgrp_lblDateInq").hide();
					$("#wwgrp_trxDateInq").hide();
				}
				else {
					$("#wwgrp_lblDateInq").show();
					$("#wwgrp_trxDateInq").show();
				}
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("div.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			} 
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1101');
	});
	
</script>