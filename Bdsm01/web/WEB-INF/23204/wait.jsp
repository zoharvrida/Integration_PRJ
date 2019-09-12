<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:form id="frmMain" name="frmMain" action="23204_waiting" method="post" theme="simple">
		Please Wait .... 
		<sj:submit
        id="btnSave"
        name="btnSave"
        formIds="frmMain"
        disabled="false"
        targets="" cssClass="display:none"
        />
	</s:form>
	<script type="text/javascript">
		$(function() {
			setTimeout(function(){
				$("#btnSave").click();
			}, 5000);
		});
	
	</script>
</s:if>