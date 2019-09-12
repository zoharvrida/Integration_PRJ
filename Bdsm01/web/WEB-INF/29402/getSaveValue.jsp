<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        $("#frmMain_trxRefNo").val('<s:property value="%{refNetworkNo}" />');
        $("#frmMain_actNo").val('<s:property value="%{codAccNo}" />');
        $("#frmMain_namCurrencyDesc").val('<s:property value="%{nameCurrency}" />');
        $("#frmMain_oriAmount").val('<s:property value="%{amtTxnLcy}" />');
        $("#frmMain_usdEquival").val('<s:property value="%{amtTxnTcy}" />');
        $("#frmMain_currencyCode").val('<s:property value="%{currencyCode}" />');
        $("#frmMain_destAmount").val('<s:property value="%{destAmount}" />');  
    });
</script>