<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	$(function() {
		if (tmpParamsCcy.onClose != undefined)
			tmpParamsCcy.onClose('<s:property value="%{ratFcyIdr}" />');
	});
</script>