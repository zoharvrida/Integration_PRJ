<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:actionerror />
<s:set var="actionConf_" scope="request">${param.actionConf}</s:set>
<s:form id="frmDlg" name="frmDlg" action="" method="post" validate="true">
    <s:text name="actionConf" />
    <s:a
        id="btnProcessConf"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.Ok"
        disabled="false"
        onBeforeTopics="beforeProcConf"
    />
    <s:a
        id="btnCancelConf"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.Cancel"
        disabled="false"
        onBeforeTopics="beforeCasaConf" 
    />
    <s:token name="tokenConf" />
</s:form>
<script type="text/javascript">
    jQuery(document).ready(function(){
        $("#ph-dlg").dialog("option", "width", "320");
        $("#ph-dlg").dialog("option", "height", "180");
        
        $("#btnCancelConf").click(function() {
            alert( "Account Creation Canceled" );
            $("#frmMain_verified").val(1);
            $("#ph-dlg").dialog("close");
            
        });
        
        $("#btnProcessConf").click(function() {
            alert( "Account Creation Continued" );
            $("#frmMain_verified").val(2);
            $("#ph-dlg").dialog("close");
            
        });
    });
</script>
