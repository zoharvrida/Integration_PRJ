<%@page import="com.opensymphony.xwork2.ActionSupport" %>
<%@page import="com.opensymphony.xwork2.ActionContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<script type="text/javascript">
	$(function() {
		<s:if test="authorized==1">
			$("#" + dlgParams.idMaintainedSpv).val("<s:property value='%{idUser}' />");
			
			<s:if test="%{#parameters.divName == null}">$('#ph-dlg')</s:if>
			<s:else>$('#<s:property value="%{#parameters.divName}" />')</s:else>
				.dialog("close");
			
			if (dlgParams.onSubmit != undefined)
				dlgParams.onSubmit();
			
			<%((ActionSupport) ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
		</s:if>
		<s:else>
			dlgParams.event.originalEvent.options.submit = false;
			alert("<s:property value='%{errorMessage}' />");
			$("#frmDlg_idUser").focus();
		</s:else>
	});
</script>
