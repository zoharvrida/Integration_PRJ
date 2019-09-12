<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>


<s:form id="frmValidateAccount">
	<s:token name="tokenFindAcct" />
</s:form>


<script type="text/javascript">
	$(function() {
		var flag = 0;
		var message = "";
		
		$("#" + tmpObj.hdFlagResult).val("<s:property value='%{flagResult}' />");
		flag = $("#" + tmpObj.hdFlagResult).val();
		
		if (flag == "-1")
			message = "<s:text name='error.account.invalid' />";
		else if (flag == "-2")
			message = "<s:text name='error.0' />";
		else if (flag == "-3")
			message = "<s:text name='error.1' />";
		else if (flag == "-4")
			message = "<s:text name='27401.error.product.invalid' />";
		else if (flag == "-5")
			message = "<s:text name='27401.error.productMIS.invalid' />";
		
		
		if (message != "")
			messageBox(2, message);
		else {
			$("#" + tmpObj.accountName).text('<s:property value="%{account.codAcctTitle}" escapeJavaScript="true" />');
			$("#" + tmpObj.productCode).text('<s:property value="%{product.compositeId.codProd}" />');
			$("#" + tmpObj.productName).text('<s:property value="%{product.namProduct}" escapeJavaScript="true" />');
			
			if (rdb.current != "add") {
				$("#" + tmpObj.productMISCode).text('<s:property value="%{productMIS.codeMIS}" />');
				$("#" + tmpObj.productMISName).text('<s:property value="%{productMIS.productMISName}" escapeJavaScript="true" />');
				$("#" + tmpObj.dateCommitment).text('<s:date name="%{dateCommitment}" format="dd/MM/yyyy" />');
				
				$("#frmMain_id").val('<s:property value="%{id}" />');
				$("#frmMain_idOld").val($("#frmMain_id").val());
				$("#frmMain_codeClass").val('<s:property value="%{MPClass.code}" />');
				$("#frmMain_codeClassOld").val($("#frmMain_codeClass").val());
				$("#lblTxtClassification").val('<s:property value="%{MPClass.name}" escapeJavaScript="true" />');
				$("#frmMain_dateCommitment").attr("value", $("#" + tmpObj.dateCommitment).text());
				
				dlgParams.onClose();
			}
			
			/* label and textbox date commitment */
			if ((rdb.current == "inquiry") || (rdb.current == "delete")) {
				$("#frmMain_dateCommitment").closest("div.wwgrp").hide();
				$("#" + tmpObj.dateCommitment).closest("div.wwgrp").show();
				
			}
			else {
				$("#frmMain_dateCommitment").closest("div.wwgrp").show();
				$("#" + tmpObj.dateCommitment).closest("div.wwgrp").hide();
			}
			
			$(".clsAcctInformation").show();
			$("#frmMain_noAccount").attr("readonly", "true");
			$("#btnFind").button("disable");
		}
		
		var newToken = $("#frmValidateAccount").find("input[name='tokenFindAcct']").val();
		$("#frmFindAcct").find("input[name='tokenFindAcct']").val(newToken);
    });
</script>