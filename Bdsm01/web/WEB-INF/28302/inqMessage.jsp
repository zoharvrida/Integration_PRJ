<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	$(function() {
		if (mesParams.onClose != undefined)
			mesParams.onClose('<s:property value="%{msgString}" escapeHtml="false" />');
	});
</script>