<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
	id="rbBS" 
	list="{'Inquiry', 'Add', 'Edit', 'Delete', 'Clear'}" 
	name="rbBS"
	disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$(window).resize();
		
		rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		
		rdb.setDisabled("frmLoadComponent_id", "0100");
		
		rdb.setOnChange(
			function() {
			}, 
			function() {
				$("#btnProcess").attr("value", '<s:text name="button.save" />');
			}, 
			function() {
				$("#btnProcess").attr("value", '<s:text name="button.update" />');
			}, 
			function() {
				$("#btnProcess").attr("value", '<s:text name="button.delete" />');
			}, 
			function(removeMsg) {
				$("#divComponent").empty();
				
				$("#frmMain_noAccount").attr("value", "");
				$("label[id^='lblTxt']").text("");
				$("#frmMain").find("img.ui-datepicker-trigger").removeClass("ui-helper-hidden");
				$("#frmMain_dateCommitment").attr("value", '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />');
				$("#frmMain_hdFlagChangePackage").attr("value", "0");
				$("#frmMain_noAccount").attr("readonly", null);
				$("#btnFind").button("enable");
				$(".clsAcctInformation").hide();
				$("#btnProcess").hide();
				$("#frmMain_noAccount").focus();
				
				if ((rdb.current == "inquiry") || (rdb.current == "delete")){
					$("#btnLookupProductMIS").hide();
					$("#btnLookupClassification").hide();
					$("#frmMain").find("img.ui-datepicker-trigger").addClass("ui-helper-hidden");
				}
				else {
					$("#btnLookupProductMIS").show();
					$("#btnLookupClassification").show();
					
					if (rdb.current == "edit") {
						$("#frmMain").find("img.ui-datepicker-trigger").addClass("ui-helper-hidden");
					}
				}
				
				
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