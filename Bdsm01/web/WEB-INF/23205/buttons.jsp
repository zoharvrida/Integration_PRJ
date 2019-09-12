<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
		id="rbBS" 
		list="{'Inquiry', 'Approve', 'Clear'}" 
		name="rbBS"
		disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$(window).resize();
		rdb.resetDisabled();
		rdb.resetReadonly();

		rdb.setDisabled("frmMain_codeNo", "0000");
		rdb.setReadonly("frmMain_codeNo", "0000");
		rdb.setDisableButton("btnFind", "0000");

		rdb.setOnChange(
			function() {
				$("#frmMain_mode").val(1);
			},
			null, 
			null, 
			null, 
			function(removeMsg) {
				$("#frmMain_codeNo").attr("value", null);
				$("#frmMain_codeNo").focus();
				$.publish("codeNoReload");
				$('#tabbedPanel').hide();
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			},
			function() {
				$("#frmMain_mode").val(2);
			}
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '10001');
	});

</script>