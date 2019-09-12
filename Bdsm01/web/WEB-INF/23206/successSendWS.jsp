<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>


<s:form id="frmSuccess" theme="css_xhtml">
	<s:token />
</s:form>

<script type="text/javascript">
	$(function() {
		$("#headerGridTable").jqGrid("setSelection", $("#headerGridTable").jqGrid("getGridParam", "selrow"));
		
		$("#btnSubmit").subscribe("onBeforeSubmit", funcOnBeforeSubmit);
		$("#frmData_idMaintainedSpv").attr("value", "");
		
		var $tokenData = $("#frmData > input[name='token']")[0];
		var $tokenSuccess = $("#frmSuccess > input[name='token']")[0];
		
		$($tokenData).attr("value", $($tokenSuccess).attr("value"));
    });
</script>