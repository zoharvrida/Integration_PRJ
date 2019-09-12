<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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

        rdb.setDisabled("gRPID", "0000");
        rdb.setReadonly("gRPID", "0000");
        rdb.setDisableButton("gRPID", "0000");

        rdb.setOnChange(
		function() {
			$("#modes").val("1");
			$.publish("codeNoReload");
		},
		null,
		null,
		null,
		function(removeMsg) {
			$("#gRPID").attr("value", null);
			$("#gRPID").focus();
			$.publish("codeNoReload");
			$('#tabbedPanel').hide();
			if (removeMsg) {
				$("#actionMessage").remove();
				$("#actionError").remove();
				$("span.errorMessage").remove();
				$("label").removeClass("errorLabel");
			}
		},function() {
			$("#modes").val("2");
			$.publish("codeNoReload");
		}
	);
        rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
    });
</script>