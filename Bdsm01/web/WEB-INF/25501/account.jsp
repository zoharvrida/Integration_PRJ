<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		if (labelParams.onClose != undefined) {
			labelParams.onClose('<s:property value="%{acctSearch}" />',
			'<s:property value="%{codprod}" />',
			'<s:property value="%{prodDesc}" />',
			'<s:property value="%{reason}" />',
		'<s:property value="%{dateIf}" />');
        }
    });
</script>