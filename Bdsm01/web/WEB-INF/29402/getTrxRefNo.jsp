<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        $("#frmMain_codAccNo").val('<s:property value="%{pmTxnLog.codAccNo}" />');
        $("#frmMain_nameCurrency").val('<s:property value="%{nameCurrency}" />');
        $("#frmMain_amtTxnAcy").val('<s:property value="%{pmTxnLog.amtTxnAcy}" />');
        $("#frmMain_amtTxnTcy").val('<s:property value="%{pmTxnLog.amtTxnTcy}" />');
        $("#frmMain_currencyCode").val('<s:property value="%{currencyCode}" />');
        $("#frmMain_destAmount").val('<s:property value="%{destAmount}" />');
		$("#frmMain_sellRate").val('<s:property value="%{destRate}" />');
        $("#frmMain_codAcctTitle").val('<s:property value="%{codAcctTitle}" />');
		
		var cur = '<s:property value="%{pmTxnLog.codAccNo}" />';
		if (cur == ""){
			alert("Transaction Ref No. is not available");
		};
        
        var flag = '<s:property value="%{flag}" />';
		if (flag=="M"){
				$("#btnGeneratePDF").show();
				$("#btnSave").hide();
				$("#btnLookupDestCcyInq").button("disable");
				$("#btnCalc").button("disable");
		};
		if (flag=="P"){
				$("#btnSave").hide();
				$("#btnGeneratePDF").hide();
		};
    });
    
	
</script>