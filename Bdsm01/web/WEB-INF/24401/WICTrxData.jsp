<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:set var="isSuccess" value="false" />

<script type="text/javascript">
	<s:if test="%{WICNo == null}">
		messageBox(2, '<s:text name="24301.error.wic.data.notfound"><s:param><s:text name="%{objData.customerType}" /></s:param></s:text>'
			, function() { rdb.clear(); });
	</s:if>
	<s:elseif test="%{WICTrx.refNo == null}">
		messageBox(2, '<s:text name="24401.error.trx.ref.no.notfound" />', function() { rdb.clear(); });
	</s:elseif>
	<s:elseif test="%{WICTrx.refNo == 'disallowed'}">
		messageBox(2, '<s:text name="24401.error.trx.disallowed"><s:param><s:property value="%{WICTrx.narrative}" /></s:param></s:text>'
			, function() { rdb.clear(); });
	</s:elseif>
	<s:else>
		<s:set var="isSuccess" value="true" />
	</s:else>

	<s:if test="%{(#isSuccess == true) && ('inquiry|delete'.indexOf(strData.searchForType) > -1)}">
		$(function() {
			$("#frmMain_WICTrx_trxNo").val('<s:property value="%{WICTrx.trxNo}" />');
			$("#frmMain_strDate_dtmCreated").val('<s:property value="%{strDate.dtmCreated}" />');
		});
	</s:if>

	<s:if test="%{#isSuccess == true}">
		$(function() {
			$("#frmMain_strData_idType").val('<s:property value="%{objData.idType}" />');
			$("#frmMain_idNumber").val('<s:property value="%{idNumber}" />');
			$("#frmMain_idExpiredDate").val('<s:date name="%{idExpiredDate}" format="dd/MM/yyyy" />');
			$("#frmMain_birthDate").val('<s:date name="birthDate" format="dd/MM/yyyy" />');
			$("#frmMain_name").val('<s:property value="%{name}" escapeJavaScript="true" />');
			$("#frmMain_address1").val('<s:property value="%{address1}" escapeJavaScript="true" />');
			$("#frmMain_address2").val('<s:property value="%{address2}" escapeJavaScript="true" />');
			$("#frmMain_address3").val('<s:property value="%{address3}" escapeJavaScript="true" />');
			$("#frmMain_strData_city").val('<s:property value="%{objData.city}" />');
			$("#frmMain_strData_state").val('<s:property value="%{objData.state}" />');
			$("#frmMain_strData_country").val('<s:property value="%{objData.country}" />');
			
			$("#frmMain_highRiskWIC").val('<s:property value="%{highRiskWIC}" />');
			$("#frmMain_highRiskCountry").val('<s:property value="%{highRiskCountry}" />');
			
			var txt = '<s:property value="%{WICTrx.type}" />'; /* 01 or 02 */
			$("#frmMain_WICTrx_WICNo").val('<s:property value="%{WICNo}" />');
			$("#frmMain_WICTrx_refNo").val('<s:property value="%{WICTrx.refNo}" />');
			$("#frmMain_WICTrx_dateTime").val('<s:date name="%{WICTrx.dateTime}" format="dd/MM/yyyy HH:mm:ss" />');
			$("#frmMain_WICTrx_type").val(txt);
			$("#frmMain_WICTrx_fastPath").val('<s:property value="%{WICTrx.fastPath}" />');
			$("#frmMain_WICTrx_currencyCode").val('<s:property value="%{WICTrx.currencyCode}" />');
			$("#frmMain_currencyName").val('<s:property value="%{objData.namTxnCcy}" />');
			$("#frmMain_WICTrx_amount").val('<s:text name="format.money"><s:param name="value" value="%{WICTrx.amount}" /></s:text>');
			$("#frmMain_WICTrx_debitOrCredit").val('<s:property value="%{WICTrx.debitOrCredit}" />');
			$("#frmMain_WICTrx_narrative").val('<s:property value="%{WICTrx.narrative}" escapeJavaScript="true" />');
			$("#frmMain_WICTrx_branch").val('<s:property value="%{WICTrx.branch}" />');
			$("#frmMain_WICTrx_tellerId").val('<s:property value="%{WICTrx.tellerId}" />');
			$("#frmMain_WICTrx_CIFNo").val('<s:property value="%{WICTrx.CIFNo}" />');
			$("#frmMain_WICTrx_accountNo").val('<s:property value="%{WICTrx.accountNo}" />');
			$("#frmMain_strData_accountName").val('<s:property value="%{objData.namAcctNo}" escapeJavaScript="true" />');
			
			
			$("#fsDataPersonal").show();
			$("#fsTrx").show();
			$("#fsButtons").show();
			
			if (rdb.current == 'inquiry')
				$("#btnProcess").hide();
			else
				$("#btnProcess").show();
			
			$("#frmInquiry, #frmMain").find("input[type='text'], textarea")
				.css("opacity", '')
				.filter("[readonly]").css("opacity", "0.5");
			
			var txt = $("#frmMain_idExpiredDate").val();
			if (!isAfterDate(txt, dtBusiness))
				messageBox(1, '<s:text name="24301.id.id.expired" />');
	    });
	</s:if>

	o.restoreEvent();
</script>

