<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:set var="name_" scope="request">${param.name == ''? 'token': param.name}</s:set>

<s:form id="frmTokenGenerator" name="frmTokenGenerator">
	<s:token name="%{#request.name_}" />
</s:form>


<script type="text/javascript">
	$("#frmTokenGenerator").find('input[name="<s:property value="%{#request.name_}" />"]').attr("id", "frmTokenGenerator_hdToken");
	
	if (objCallback.function_ != undefined)
		objCallback.function_();
</script>

