<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	$(function() {
		if (msgParams.onClose != undefined)
			msgParams.onClose('<s:property value="%{tagMessage}" escapeHtml="false" />');
	});
</script>