<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	$(function() {
		$("#" + tmpObj.hdFlagCard).val('<s:property value="%{flagCard}" />');
		
		if ($("#" + tmpObj.hdFlagCard).val() == '-1')
			alert("Invalid Card No '" + $("#" + tmpObj.cardNo).val() + "'");
		//else if ($("#" + tmpObj.hdFlagCard).val() == '-2')
		//	alert("Card No '" + $("#" + tmpObj.cardNo).val() + "' is not registered in BDSM System");
    });
</script>