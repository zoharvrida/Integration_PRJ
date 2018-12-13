<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:actionerror />

<s:form id="frmDlg" name="frmDlg" action="EKTPReaderAuth_execute.action" method="post" validate="true">
	<s:textfield 
		name="idUser" 
		requiredLabel="true" 
		size="30"
		maxlength="20" 
		cssClass="ui-widget ui-widget-content"
		key="label.idUser" 
		labelSeparator="" 
		disabled="false"
		cssStyle="text-transform:uppercase"
		onblur="javascript:this.value=this.value.toUpperCase();" 
	/>
	<s:password 
		name="password" 
		requiredLabel="true" 
		size="30"
		maxlength="20" 
		cssClass="ui-widget ui-widget-content"
		key="label.password" 
		disabled="false" 
		autocomplete="off" 
	/>
	<s:hidden name="idMenu" value="%{#parameters.idMenu}" />
	<s:hidden name="deviceIP" value="%{#parameters.deviceIP}" />
	<s:hidden name="divName" value="%{#parameters.divName}" />
	
	<sj:submit 
		id="btnAuth" 
		buttonIcon="ui-icon-gear" 
		button="true"
		key="button.auth" 
		disabled="false" 
		targets="%{#parameters.divName}" 
	/>
	<s:token name="tokenEKTPReaderAuth" />
</s:form>

<script type="text/javascript">
	$(function() {
		$("#" + '<s:property value="%{#parameters.divName}" />')
			.dialog("option", "width", "320")
			.dialog("option", "height", "180");
		$("#frmDlg_idUser").focus();
	});
</script>
