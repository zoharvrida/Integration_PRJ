<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
	id="rbBS"
	name="rbBS" 
	list="{'Inquiry', 'Add', 'Edit', 'Clear'}"
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$(window).resize();
		rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		
		
		rdb.setDisableButton("btnLookupGiftCode", "0100");
		rdb.setReadonly("productCode_widget", "1011");
		rdb.setReadonly("frmMain_giftName", "1111");
		rdb.setDisabled("frmMain_status", "1111");
		rdb.setReadonly("frmMain_programId", "1111");
		rdb.setReadonly("frmMain_programName", "1111");
		rdb.setDisabled("frmMain_type", "1111");
		rdb.setDisabled("frmMain_amortizeMethod", "1111");
		rdb.setReadonly("frmMain_idAccrual", "1111");
		rdb.setReadonly("frmMain_taxPercent", "1111");
		rdb.setDisabled("frmMain_voucher", "1111");
		
		
		rdb.setOnChange(
			function() {
				changeAction("frmMain", "25301", "");
				
			}, 
			function() {
				changeAction("frmMain", "25301", "_add");
				$("#btnSubmit").attr("value", "<s:text name='button.save' />");
			}, 
			function() {
				changeAction("frmMain", "25301", "_edit");
				$("#btnSubmit").attr("value", "<s:text name='button.update' />");
			}, 
			null,
			function(removeMsg) {
				$("#frmMain_compositeId_giftCode").attr("value", "");
				$("#frmMain_compositeId_productCode").attr("value", "");
				$("#productCode").attr("value", "");
				$("#productCode_widget").attr("value", "");
				$("#frmMain_giftName").attr("value", "");
				$("#frmMain_status").val("1");
				$("#frmMain_programId").attr("value", "");
				$("#frmMain_programName").attr("value", "");
				$("#frmMain_type").val("1");
				$("#frmMain_amortizeMethod").val("1");
				$("#frmMain_idAccrual").attr("value", "");
				$("#frmMain_taxPercent").attr("value", "0");
				$("#frmMain_voucher").prop("checked", false);
				
				$("#frmMain_compositeId_giftCode").attr("readonly", false);
				
				
				$("#btnSubmit").button("disable");
				if (rdb.current == 'inquiry')
					$("#btnSubmit").hide();
				else
					$("#btnSubmit").show();
				
				if (rdb.current == 'add') {
					$("#btnLookupGiftCode").hide();
					$("#productCode_widget").attr("readonly", false);
					$("#frmMain_status option:not(:selected)").attr("disabled", true);
				}
				else {
					$("#btnLookupGiftCode").show();
					$("#frmMain_status option").attr("disabled", false);
				}
				
				changeViewOfReadOnlyTextElement("frmMain");
		
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("div.errorMessage").remove();
					$("label").removeClass("errorLabel");
					
					$('#tabbedPanel').hide();
				}
			} 
		);
		
		changeViewOfReadOnlyTextElement = function(containerElement) {
			/* readonly text */
			$("#" + containerElement).find("input[type='text']")
				.css("opacity", '')
				.filter("[readonly]").css("opacity", "0.5");
		};
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '11100');
	});

	
</script>